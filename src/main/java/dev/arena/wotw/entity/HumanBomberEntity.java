package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

/**
 * A heavy bomber: it flies a long, high orbit over the invasion and drops a stick of bombs on the
 * machine below.
 *
 * <p>It has no guns at all — the only thing it does is open the bay doors every couple of seconds
 * while it passes over a Martian walker, and get out of the way of the heat rays in between.</p>
 */
public class HumanBomberEntity extends AbstractHumanCombatantEntity {

    /** Cruising height above the terrain: high enough that the tripods cannot reach it. */
    private static final double CEILING = 24.0D;
    /** Ticks between two bombs in a stick. */
    private static final int BOMB_INTERVAL = 50;
    private static final float BOMB_POWER = 3.2F;

    public HumanBomberEntity(EntityType<? extends HumanBomberEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(140.0D, 0.34D, 4.0D, 8.0D, 2.0D, 64.0D);
    }

    @Override
    protected void addCombatGoals() {
        // no guns: the bombing run is driven from tick()
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        double ground = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (int) this.getX(), (int) this.getZ());
        double ceiling = ground + CEILING;
        LivingEntity target = this.getTarget();

        Vec3 wish;
        if (target != null) {
            Vec3 aim = new Vec3(target.getX(), ceiling, target.getZ());
            Vec3 delta = aim.subtract(this.position());
            if (delta.lengthSqr() > 9.0D) {
                wish = delta.normalize().scale(0.32D);
            } else {
                wish = new Vec3(-delta.z, 0.0D, delta.x).normalize().scale(0.20D);
            }
            this.dropBombIfOverTarget(target);
        } else {
            wish = new Vec3(0.0D, Math.max(-0.1D, Math.min(0.1D, (ceiling - this.getY()) * 0.06D)), 0.0D);
        }
        this.setDeltaMovement(this.getDeltaMovement().scale(0.7D).add(wish.scale(0.3D)));
        this.fallDistance = 0.0F;
    }

    private void dropBombIfOverTarget(LivingEntity target) {
        if (this.tickCount % BOMB_INTERVAL != 0) {
            return;
        }
        double horizontal = Math.sqrt(this.distanceToSqr(target.getX(), this.getY(), target.getZ()));
        if (horizontal > 14.0D || this.getY() - target.getY() < 6.0D) {
            return;
        }
        Vec3 origin = new Vec3(this.getX(), this.getY() - 1.2D, this.getZ());
        ShellEntity.launch(this.level(), this, origin, new Vec3(0.0D, -0.55D, 0.0D), BOMB_POWER, false);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH,
                SoundSource.NEUTRAL, 1.2F, 0.6F);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }
}
