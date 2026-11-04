package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import dev.arena.wotw.registry.ModItems;
import dev.arena.wotw.registry.ModSounds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * Shared behaviour of every tripod in the mod.
 *
 * <p>Subclasses only have to describe their stats ({@link #createTripodAttributes}) and plug in
 * their signature ability through the goal system plus {@link #serverTick()}.</p>
 */
public abstract class AbstractTripodEntity extends Monster {

    /** Synced flag used by the model to make the "eye" and the legs quiver while charging. */
    private static final EntityDataAccessor<Boolean> CHARGING =
            SynchedEntityData.defineId(AbstractTripodEntity.class, EntityDataSerializers.BOOLEAN);
    /** Synced flag: the energy shield is up, incoming projectiles are deflected. */
    private static final EntityDataAccessor<Boolean> SHIELDED =
            SynchedEntityData.defineId(AbstractTripodEntity.class, EntityDataSerializers.BOOLEAN);

    private int abilityCooldown;
    private int shieldTicks;
    private int lifeTicks;
    /** When true this machine plants red roots behind it while it walks. */
    private boolean rootTrail;

    protected AbstractTripodEntity(EntityType<? extends AbstractTripodEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public int getExperienceReward() {
        return 60;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHARGING, Boolean.FALSE);
        this.entityData.define(SHIELDED, Boolean.FALSE);
    }

    /* ------------------------------------------------------------------ */
    /*  Stats                                                              */
    /* ------------------------------------------------------------------ */

    protected static AttributeSupplier.Builder tripodAttributes(double health, double speed,
                                                                        double attackDamage, double armor,
                                                                        double toughness, double knockbackResistance,
                                                                        double followRange) {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, health)
                .add(Attributes.MOVEMENT_SPEED, speed)
                .add(Attributes.ATTACK_DAMAGE, attackDamage)
                .add(Attributes.ARMOR, armor)
                .add(Attributes.ARMOR_TOUGHNESS, toughness)
                .add(Attributes.KNOCKBACK_RESISTANCE, knockbackResistance)
                .add(Attributes.FOLLOW_RANGE, followRange);
    }

    /* ------------------------------------------------------------------ */
    /*  AI                                                                 */
    /* ------------------------------------------------------------------ */

    @Override
    protected void initGoals() {
        if (!isFlyer()) {
            this.goalSelector.add(0, new FloatGoal(this));
            this.goalSelector.add(6, new MeleeAttackGoal(this, 1.0D, true));
            this.goalSelector.add(8, new WaterAvoidingRandomStrollGoal(this, 0.7D));
            this.goalSelector.add(9, new LookAtPlayerGoal(this, Player.class, 40.0F));
            this.goalSelector.add(9, new RandomLookAroundGoal(this));
        }
        this.targetSelector.add(1, new HurtByTargetGoal(this));
        // A player already locked in the cage of a Handling Machine is not prey any more.
        this.targetSelector.add(2, new NearestAttackableTargetGoal<>(this, Player.class, true,
                entity -> !(entity instanceof Player player
                        && player.hasEffect(dev.arena.wotw.registry.ModStatusEffects.CAGED))));
        // the human army shoots at us, so we shoot back
        this.targetSelector.add(3, new NearestAttackableTargetGoal<>(this, AbstractHumanCombatantEntity.class, true));
        this.targetSelector.add(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.add(4, new NearestAttackableTargetGoal<>(this, Villager.class, true));
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
            if (this.level().isClientSide) {
                this.clientShieldParticles();
            } else if (this.shieldTicks == 0) {
                this.entityData.set(SHIELDED, Boolean.FALSE);
            }
        }
        if (this.level().isClientSide) {
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
        if (!(this.level() instanceof ServerLevel world)) {
            return;
        }
        BlockPos feet = this.blockPosition();
        if (world.getRandom().nextFloat() > 0.5F) {
            return;
        }
        if (dev.arena.wotw.block.RedRootBlock.plant(world, feet)) {
            world.sendParticles(ParticleTypes.CRIMSON_SPORE, feet.getX() + 0.5D, feet.getY() + 0.4D,
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
        return this.entityData.get(CHARGING);
    }

    public void setAbilityCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    public boolean isShielded() {
        return this.entityData.get(SHIELDED);
    }

    public void startShield(int ticks) {
        this.shieldTicks = ticks;
        this.entityData.set(SHIELDED, Boolean.TRUE);
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
    public Vec3 getBeamOrigin() {
        return new Vec3(this.getX(), this.getY() + this.getBbHeight() * 0.72D, this.getZ())
                .add(this.getViewVector(1.0F).scale(this.getBbWidth() * 0.45D));
    }

    public void fireBeamAt(LivingEntity target, BeamKind kind, float damage) {
        if (target == null) {
            return;
        }
        this.fireBeamAt(target.getEyePosition(), kind, damage);
    }

    public void fireBeamAt(Vec3 target, BeamKind kind, float damage) {
        Vec3 origin = this.getBeamOrigin();
        Vec3 delta = target.subtract(origin);
        if (delta.lengthSquared() < 0.05D) {
            return;
        }
        this.fireBeam(origin, delta.normalize(), kind, damage);
    }

    public void fireBeam(Vec3 origin, Vec3 direction, BeamKind kind, float damage) {
        if (this.level().isClientSide) {
            return;
        }
        BeamEntity beam = new BeamEntity(this.level(), origin, direction, kind, this, damage);
        this.level().addFreshEntity(beam);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.HEAT_RAY, SoundSource.HOSTILE, 2.2F, kind.getSoundPitch());
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    origin.x, origin.y, origin.z, 12, 0.3D, 0.3D, 0.3D, 0.25D);
        }
    }

    /** Charge up sound, played when a beam attack starts. */
    public void playChargeSound() {
        // The siren rising before the shot is half of the scare factor.
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.TRIPOD_HORN, SoundSource.HOSTILE, 1.8F, 1.05F);
    }

    /* ------------------------------------------------------------------ */
    /*  Damage / physics quirks                                            */
    /* ------------------------------------------------------------------ */

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isShielded() && source.is(DamageTypeTags.IS_PROJECTILE)) {
            if (!this.level().isClientSide) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.HOSTILE, 1.4F, 1.7F);
                if (this.level() instanceof ServerLevel serverWorld) {
                    serverWorld.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            this.getX(), this.getY() + this.getBbHeight() * 0.6D, this.getZ(),
                            22, this.getBbWidth() * 0.5D, this.getBbHeight() * 0.4D, this.getBbWidth() * 0.5D, 0.2D);
                }
            }
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            this.onMeleeHit(living);
        }
        return hit;
    }

    /** Hook for on-hit riders (levitation claws, poison bites, ...). */
    protected void onMeleeHit(LivingEntity victim) {
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, boolean causedByPlayer) {
        int amount = 1 + this.getRandom().nextInt(1 + (int) (this.getMaxHealth() / 100.0F));
        this.spawnAtLocation(new ItemStack(ModItems.MARTIAN_ALLOY, amount));
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide && this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.EXPLOSION,
                    this.getX(), this.getY() + this.getBbHeight() * 0.5D, this.getZ(),
                    8, this.getBbWidth() * 0.4D, this.getBbHeight() * 0.3D, this.getBbWidth() * 0.4D, 0.02D);
            serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE,
                    this.getX(), this.getY() + this.getBbHeight() * 0.4D, this.getZ(),
                    40, this.getBbWidth() * 0.4D, this.getBbHeight() * 0.4D, this.getBbWidth() * 0.4D, 0.03D);
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.IRON_GOLEM_DEATH, SoundSource.HOSTILE, 2.4F, 0.55F);
    }

    private void clientShieldParticles() {
        double radius = this.getBbWidth() * 0.65D;
        for (int i = 0; i < 3; i++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double y = this.random.nextDouble() * this.getBbHeight() * 0.8D;
            this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
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
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public float maxUpStep() {
        return 2.5F;
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
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
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
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
    public int getAmbientSoundInterval() {
        // The siren is meant to be a rare, unsettling event, not a metronome.
        return 400 + this.random.nextInt(500);
    }

    /* ------------------------------------------------------------------ */
    /*  Convenience                                                        */
    /* ------------------------------------------------------------------ */

    public boolean canSeeTarget(LivingEntity target) {
        return target != null && this.hasLineOfSight(target);
    }

    protected double squaredDistanceToTarget(LivingEntity target) {
        return target == null ? Double.MAX_VALUE : this.distanceToSqr(target);
    }

}
