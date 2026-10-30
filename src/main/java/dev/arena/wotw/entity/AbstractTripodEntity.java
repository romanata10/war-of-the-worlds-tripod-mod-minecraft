package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import dev.arena.wotw.registry.ModItems;
import dev.arena.wotw.registry.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Shared behaviour of every tripod in the mod.
 *
 * <p>Subclasses only have to describe their stats ({@link #createTripodAttributes}) and plug in
 * their signature ability through the goal system plus {@link #serverTick()}.</p>
 */
public abstract class AbstractTripodEntity extends HostileEntity {

    /** Synced flag used by the model to make the "eye" and the legs quiver while charging. */
    private static final TrackedData<Boolean> CHARGING =
            DataTracker.registerData(AbstractTripodEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    /** Synced flag: the energy shield is up, incoming projectiles are deflected. */
    private static final TrackedData<Boolean> SHIELDED =
            DataTracker.registerData(AbstractTripodEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int abilityCooldown;
    private int shieldTicks;
    private int lifeTicks;
    /** When true this machine plants red roots behind it while it walks. */
    private boolean rootTrail;

    protected AbstractTripodEntity(EntityType<? extends AbstractTripodEntity> type, World world) {
        super(type, world);
    }

    @Override
    public int getXpToDrop() {
        return 60;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHARGING, Boolean.FALSE);
        this.dataTracker.startTracking(SHIELDED, Boolean.FALSE);
    }

    /* ------------------------------------------------------------------ */
    /*  Stats                                                              */
    /* ------------------------------------------------------------------ */

    protected static DefaultAttributeContainer.Builder tripodAttributes(double health, double speed,
                                                                        double attackDamage, double armor,
                                                                        double toughness, double knockbackResistance,
                                                                        double followRange) {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, health)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attackDamage)
                .add(EntityAttributes.GENERIC_ARMOR, armor)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, toughness)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistance)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, followRange);
    }

    /* ------------------------------------------------------------------ */
    /*  AI                                                                 */
    /* ------------------------------------------------------------------ */

    @Override
    protected void initGoals() {
        if (!isFlyer()) {
            this.goalSelector.add(0, new SwimGoal(this));
            this.goalSelector.add(6, new MeleeAttackGoal(this, 1.0D, true));
            this.goalSelector.add(8, new WanderAroundFarGoal(this, 0.7D));
            this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 40.0F));
            this.goalSelector.add(9, new LookAroundGoal(this));
        }
        this.targetSelector.add(1, new RevengeGoal(this));
        // A player already locked in the cage of a Handling Machine is not prey any more.
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true,
                entity -> !(entity instanceof PlayerEntity player
                        && player.hasStatusEffect(dev.arena.wotw.registry.ModStatusEffects.CAGED))));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
        this.addExtraGoals();
    }

    /** Hook for subclasses: add their own ability goal here. */
    protected void addExtraGoals() {
    }

    /** Flying tripods skip every walking goal. */
    public boolean isFlyer() {
        return false;
    }

    /* ------------------------------------------------------------------ */
    /*  Ticking                                                            */
    /* ------------------------------------------------------------------ */

    @Override
    public void tick() {
        super.tick();
        if (this.abilityCooldown > 0) {
            this.abilityCooldown--;
        }
        if (this.shieldTicks > 0) {
            this.shieldTicks--;
            if (this.getWorld().isClient) {
                this.clientShieldParticles();
            } else if (this.shieldTicks == 0) {
                this.dataTracker.set(SHIELDED, Boolean.FALSE);
            }
        }
        if (this.getWorld().isClient) {
            this.clientTick();
        } else {
            this.lifeTicks++;
            this.serverTick();
            if (this.rootTrail && this.lifeTicks % 8 == 0) {
                this.plantRoot();
            }
        }
    }

    /** True when this machine was grown from a capsule that carried a red root seed bed. */
    public boolean hasRootTrail() {
        return this.rootTrail;
    }

    public void setRootTrail(boolean rootTrail) {
        this.rootTrail = rootTrail;
    }

    /** Plants a root on the tile this machine is standing on, if the ground allows it. */
    private void plantRoot() {
        if (!(this.getWorld() instanceof ServerWorld world)) {
            return;
        }
        BlockPos feet = this.getBlockPos();
        if (world.getRandom().nextFloat() > 0.5F) {
            return;
        }
        if (dev.arena.wotw.block.RedRootBlock.plant(world, feet)) {
            world.spawnParticles(ParticleTypes.CRIMSON_SPORE, feet.getX() + 0.5D, feet.getY() + 0.4D,
                    feet.getZ() + 0.5D, 6, 0.3D, 0.2D, 0.3D, 0.0D);
        }
    }

    /** Server side per tick logic for ability timing. */
    protected void serverTick() {
    }

    /** Client side per tick logic (particles around the eye, etc.). */
    protected void clientTick() {
    }

    /* ------------------------------------------------------------------ */
    /*  Ability plumbing                                                   */
    /* ------------------------------------------------------------------ */

    public boolean isAbilityCharging() {
        return this.dataTracker.get(CHARGING);
    }

    public void setAbilityCharging(boolean charging) {
        this.dataTracker.set(CHARGING, charging);
    }

    public boolean isShielded() {
        return this.dataTracker.get(SHIELDED);
    }

    public void startShield(int ticks) {
        this.shieldTicks = ticks;
        this.dataTracker.set(SHIELDED, Boolean.TRUE);
    }

    public boolean isAbilityReady() {
        return this.abilityCooldown <= 0;
    }

    public void setAbilityCooldown(int ticks) {
        this.abilityCooldown = ticks;
    }

    public int getAbilityCooldown() {
        return this.abilityCooldown;
    }

    public int getLifeTicks() {
        return this.lifeTicks;
    }

    /** True exactly on every {@code period}-th server tick (0 is skipped). */
    public boolean onIntervalZero(int period) {
        return this.lifeTicks > 0 && this.lifeTicks % period == 0;
    }

    /* ------------------------------------------------------------------ */
    /*  Beam weapons                                                       */
    /* ------------------------------------------------------------------ */

    /** Where a heat ray leaves the machine. */
    public Vec3d getBeamOrigin() {
        return new Vec3d(this.getX(), this.getY() + this.getHeight() * 0.72D, this.getZ())
                .add(this.getRotationVec(1.0F).multiply(this.getWidth() * 0.45D));
    }

    public void fireBeamAt(LivingEntity target, BeamKind kind, float damage) {
        if (target == null) {
            return;
        }
        this.fireBeamAt(target.getEyePos(), kind, damage);
    }

    public void fireBeamAt(Vec3d target, BeamKind kind, float damage) {
        Vec3d origin = this.getBeamOrigin();
        Vec3d delta = target.subtract(origin);
        if (delta.lengthSquared() < 0.05D) {
            return;
        }
        this.fireBeam(origin, delta.normalize(), kind, damage);
    }

    public void fireBeam(Vec3d origin, Vec3d direction, BeamKind kind, float damage) {
        if (this.getWorld().isClient) {
            return;
        }
        BeamEntity beam = new BeamEntity(this.getWorld(), origin, direction, kind, this, damage);
        this.getWorld().spawnEntity(beam);
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.HEAT_RAY, SoundCategory.HOSTILE, 2.2F, kind.getSoundPitch());
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK,
                    origin.x, origin.y, origin.z, 12, 0.3D, 0.3D, 0.3D, 0.25D);
        }
    }

    /** Charge up sound, played when a beam attack starts. */
    public void playChargeSound() {
        // The siren rising before the shot is half of the scare factor.
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.TRIPOD_HORN, SoundCategory.HOSTILE, 1.8F, 1.05F);
    }

    /* ------------------------------------------------------------------ */
    /*  Damage / physics quirks                                            */
    /* ------------------------------------------------------------------ */

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isShielded() && source.isIn(DamageTypeTags.IS_PROJECTILE)) {
            if (!this.getWorld().isClient) {
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.HOSTILE, 1.4F, 1.7F);
                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK,
                            this.getX(), this.getY() + this.getHeight() * 0.6D, this.getZ(),
                            22, this.getWidth() * 0.5D, this.getHeight() * 0.4D, this.getWidth() * 0.5D, 0.2D);
                }
            }
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean hit = super.tryAttack(target);
        if (hit && target instanceof LivingEntity living) {
            this.onMeleeHit(living);
        }
        return hit;
    }

    /** Hook for on-hit riders (levitation claws, poison bites, ...). */
    protected void onMeleeHit(LivingEntity victim) {
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        int amount = 1 + this.getRandom().nextInt(1 + (int) (this.getMaxHealth() / 100.0F));
        this.dropStack(new ItemStack(ModItems.MARTIAN_ALLOY, amount));
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (!this.getWorld().isClient && this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.EXPLOSION,
                    this.getX(), this.getY() + this.getHeight() * 0.5D, this.getZ(),
                    8, this.getWidth() * 0.4D, this.getHeight() * 0.3D, this.getWidth() * 0.4D, 0.02D);
            serverWorld.spawnParticles(ParticleTypes.LARGE_SMOKE,
                    this.getX(), this.getY() + this.getHeight() * 0.4D, this.getZ(),
                    40, this.getWidth() * 0.4D, this.getHeight() * 0.4D, this.getWidth() * 0.4D, 0.03D);
        }
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_IRON_GOLEM_DEATH, SoundCategory.HOSTILE, 2.4F, 0.55F);
    }

    private void clientShieldParticles() {
        double radius = this.getWidth() * 0.65D;
        for (int i = 0; i < 3; i++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double y = this.random.nextDouble() * this.getHeight() * 0.8D;
            this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK,
                    this.getX() + Math.cos(angle) * radius, this.getY() + y, this.getZ() + Math.sin(angle) * radius,
                    0.0D, 0.02D, 0.0D);
        }
    }

    /* ------------------------------------------------------------------ */
    /*  Misc overrides                                                     */
    /* ------------------------------------------------------------------ */

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public float getStepHeight() {
        return 2.5F;
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (this.random.nextFloat() < 0.4F) {
            this.playSound(ModSounds.TRIPOD_STEP, 1.6F, 0.55F + this.random.nextFloat() * 0.12F);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.TRIPOD_HORN;
    }

    @Override
    public float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public int getMinAmbientSoundDelay() {
        // The siren is meant to be a rare, unsettling event, not a metronome.
        return 400 + this.random.nextInt(500);
    }

    /* ------------------------------------------------------------------ */
    /*  Convenience                                                        */
    /* ------------------------------------------------------------------ */

    public boolean canSeeTarget(LivingEntity target) {
        return target != null && this.canSee(target);
    }

    protected double squaredDistanceToTarget(LivingEntity target) {
        return target == null ? Double.MAX_VALUE : this.squaredDistanceTo(target);
    }

}
