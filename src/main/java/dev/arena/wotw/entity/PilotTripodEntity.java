package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import dev.arena.wotw.registry.ModItems;
import dev.arena.wotw.registry.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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

    public PilotTripodEntity(EntityType<? extends PilotTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(160.0D, 0.32D, 10.0D, 12.0D, 6.0D, 1.0D, 32.0D);
    }

    /** Friendly machine: it hunts monsters instead of players, and only fights when provoked. */
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 0.7D));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, HostileEntity.class, true,
                entity -> !(entity instanceof PilotTripodEntity)));
    }

    /* ------------------------------------------------------------------ */
    /*  Riding                                                             */
    /* ------------------------------------------------------------------ */

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.getPassengerList().isEmpty() && !player.isSneaking() && this.isAlive()) {
            if (!this.getWorld().isClient) {
                player.startRiding(this);
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().isEmpty();
    }

    @Override
    public double getMountedHeightOffset() {
        return 1.55D;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement() && this.getFirstPassenger() instanceof PlayerEntity player) {
            this.setYaw(player.getYaw());
            this.prevYaw = this.getYaw();
            this.setPitch(player.getPitch() * 0.4F);
            this.prevPitch = this.getPitch();
            super.travel(new Vec3d(player.sidewaysSpeed, 0.0D, player.forwardSpeed));
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
        if (this.getWorld().isClient) {
            this.spawnEyeSparks();
            return;
        }
        if (this.beamCooldown > 0) {
            this.beamCooldown--;
        }
        if (!(this.getFirstPassenger() instanceof PlayerEntity player) || this.beamCooldown > 0) {
            return;
        }
        LivingEntity target = this.pickTarget(player);
        if (target != null) {
            this.fireBeam(target);
            this.beamCooldown = BEAM_COOLDOWN;
        }
    }

    @Nullable
    private LivingEntity pickTarget(PlayerEntity player) {
        Entity attacking = player.getAttacking();
        if (attacking instanceof LivingEntity living && living.isAlive() && this.canSee(living)) {
            return living;
        }
        LivingEntity best = null;
        double bestDistance = TARGET_RANGE * TARGET_RANGE;
        for (LivingEntity candidate : this.getWorld().getEntitiesByClass(LivingEntity.class,
                this.getBoundingBox().expand(TARGET_RANGE),
                entity -> entity instanceof HostileEntity && entity.isAlive() && this.canSee(entity))) {
            double distance = this.squaredDistanceTo(candidate);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }
        return best;
    }

    private void fireBeam(LivingEntity target) {
        Vec3d forward = this.getRotationVec(1.0F);
        Vec3d origin = new Vec3d(this.getX(), this.getY() + this.getHeight() * 0.78D, this.getZ())
                .add(forward.multiply(2.2D));
        Vec3d delta = target.getEyePos().subtract(origin);
        if (delta.lengthSquared() < 0.01D) {
            return;
        }
        BeamEntity beam = new BeamEntity(this.getWorld(), origin, delta.normalize(), BeamKind.HEAT_RAY, this, BEAM_DAMAGE);
        this.getWorld().spawnEntity(beam);
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.HEAT_RAY,
                SoundCategory.PLAYERS, 1.6F, 1.15F);
    }

    private void spawnEyeSparks() {
        if (this.getPassengerList().isEmpty() || this.beamCooldown > 0 || this.random.nextInt(3) != 0) {
            return;
        }
        Vec3d forward = this.getRotationVec(1.0F);
        this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK,
                this.getX() + forward.x * 1.8D,
                this.getY() + this.getHeight() * 0.78D,
                this.getZ() + forward.z * 1.8D, 0.0D, 0.0D, 0.0D);
    }

    /* ------------------------------------------------------------------ */
    /*  Body                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    public void onDeath(DamageSource source) {
        this.removeAllPassengers();
        super.onDeath(source);
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);
        this.dropStack(new ItemStack(ModItems.WHITE_MARTIAN_ALLOY, 1 + this.getRandom().nextInt(2)));
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isFireImmune() {
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
}
