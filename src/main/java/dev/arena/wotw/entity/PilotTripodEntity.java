package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import dev.arena.wotw.registry.ModItems;
import dev.arena.wotw.registry.ModSounds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * The machine a human crew builds from the tripod blueprint: a steel tripod with a cockpit.
 *
 * <p>Right click to climb in, then walk with WASD like a horse and hit a mob to make the shoulder
 * cannon fire a heat ray at it. The hull has 160 hit points and mobs can absolutely destroy it —
 * when it dies it throws the pilot out.</p>
 */
public class PilotTripodEntity extends AbstractTripodEntity {

    private static final int BEAM_COOLDOWN = 26;
    private static final float BEAM_DAMAGE = 7.5F;
    private static final double TARGET_RANGE = 30.0D;

    private int beamCooldown;

    public PilotTripodEntity(EntityType<? extends PilotTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(160.0D, 0.32D, 10.0D, 12.0D, 6.0D, 1.0D, 32.0D);
    }

    /** Friendly machine: it hunts monsters instead of players, and only fights when provoked. */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true,
                entity -> !(entity instanceof PilotTripodEntity)));
    }

    /* ------------------------------------------------------------------ */
    /*  Riding                                                             */
    /* ------------------------------------------------------------------ */

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.getPassengers().isEmpty() && !player.isShiftKeyDown() && this.isAlive()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public void travel(Vec3 movementInput) {
        if (this.isControlledByLocalInstance() && this.getFirstPassenger() instanceof Player player) {
            this.setYRot(player.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(player.getXRot() * 0.4F);
            this.xRotO = this.getXRot();
            super.travel(new Vec3(player.xxa, 0.0D, player.zza));
            return;
        }
        super.travel(movementInput);
    }

    /* ------------------------------------------------------------------ */
    /*  Shooting                                                           */
    /* ------------------------------------------------------------------ */

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.spawnEyeSparks();
            return;
        }
        if (this.beamCooldown > 0) {
            this.beamCooldown--;
        }
        if (!(this.getFirstPassenger() instanceof Player player) || this.beamCooldown > 0) {
            return;
        }
        LivingEntity target = this.pickTarget(player);
        if (target != null) {
            this.fireBeam(target);
            this.beamCooldown = BEAM_COOLDOWN;
        }
    }

    @Nullable
    private LivingEntity pickTarget(Player player) {
        Entity attacking = player.getLastHurtMob();
        if (attacking instanceof LivingEntity living && living.isAlive() && this.hasLineOfSight(living)) {
            return living;
        }
        LivingEntity best = null;
        double bestDistance = TARGET_RANGE * TARGET_RANGE;
        for (LivingEntity candidate : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(TARGET_RANGE),
                entity -> entity instanceof Monster && entity.isAlive() && this.hasLineOfSight(entity))) {
            double distance = this.distanceToSqr(candidate);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }
        return best;
    }

    private void fireBeam(LivingEntity target) {
        Vec3 forward = this.getViewVector(1.0F);
        Vec3 origin = new Vec3(this.getX(), this.getY() + this.getBbHeight() * 0.78D, this.getZ())
                .add(forward.scale(2.2D));
        Vec3 delta = target.getEyePosition().subtract(origin);
        if (delta.lengthSqr() < 0.01D) {
            return;
        }
        BeamEntity beam = new BeamEntity(this.level(), origin, delta.normalize(), BeamKind.HEAT_RAY, this, BEAM_DAMAGE);
        this.level().addFreshEntity(beam);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.HEAT_RAY,
                SoundSource.PLAYERS, 1.6F, 1.15F);
    }

    private void spawnEyeSparks() {
        if (this.getPassengers().isEmpty() || this.beamCooldown > 0 || this.random.nextInt(3) != 0) {
            return;
        }
        Vec3 forward = this.getViewVector(1.0F);
        this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                this.getX() + forward.x * 1.8D,
                this.getY() + this.getBbHeight() * 0.78D,
                this.getZ() + forward.z * 1.8D, 0.0D, 0.0D, 0.0D);
    }

    /* ------------------------------------------------------------------ */
    /*  Body                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    public void die(DamageSource source) {
        this.ejectPassengers();
        super.die(source);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean causedByPlayer) {
        super.dropCustomDeathLoot(source, looting, causedByPlayer);
        this.spawnAtLocation(new ItemStack(ModItems.WHITE_MARTIAN_ALLOY, 1 + this.getRandom().nextInt(2)));
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean fireImmune() {
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
}
