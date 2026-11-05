package dev.arena.wotw.entity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * Ram tripod — squat, ugly, and it does not shoot at all.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Ram</b> — it sights a player, winds up, then sprints at him at a mad speed and smashes
 *     every block in its path (it leaves a three wide, three high tunnel of ruin behind it).</li>
 * </ul>
 */
public class RamTripodEntity extends AbstractTripodEntity {

    private static final double CHARGE_RANGE = 26.0D;
    private static final int CHARGE_TICKS = 70;

    private boolean charging;
    private int chargeTicks;
    private int cooldown;

    public RamTripodEntity(EntityType<? extends RamTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(180.0D, 0.36D, 18.0D, 8.0D, 5.0D, 1.0D, 56.0D);
    }

    @Override
    protected void serverTick() {
        LivingEntity target = this.getTarget();

        if (this.charging) {
            this.charge(target);
            return;
        }
        if (this.cooldown > 0) {
            this.cooldown--;
            return;
        }
        if (target == null || !target.isAlive()) {
            return;
        }
        if (this.distanceToSqr(target) > CHARGE_RANGE * CHARGE_RANGE || !this.hasLineOfSight(target)) {
            return;
        }
        this.charging = true;
        this.chargeTicks = CHARGE_TICKS;
        this.playChargeSound();
    }

    private void charge(LivingEntity target) {
        if (target == null || !target.isAlive() || --this.chargeTicks <= 0) {
            this.charging = false;
            this.cooldown = 160;
            return;
        }
        Vec3 flat = new Vec3(target.getX() - this.getX(), 0.0D, target.getZ() - this.getZ());
        if (flat.lengthSqr() < 0.01D) {
            return;
        }
        Vec3 forward = flat.normalize();
        this.setDeltaMovement(forward.x * 0.95D, this.getDeltaMovement().y, forward.z * 0.95D);
        this.hurtMarked = true;
        this.getLookControl().setLookAt(target.getX(), target.getY() + 1.0D, target.getZ(), 60.0F, 60.0F);
        this.smashAhead(forward);

        if (this.distanceToSqr(target) < 12.0D) {
            target.hurt(this.damageSources().mobAttack(this), 18.0F);
            target.push(forward.x * 1.8D, 0.45D, forward.z * 1.8D);
            target.hurtMarked = true;
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.8F, 0.7F);
            if (this.level() instanceof ServerLevel serverWorld) {
                serverWorld.sendParticles(ParticleTypes.EXPLOSION, target.getX(), target.getY() + 1.0D,
                        target.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
            }
            this.charging = false;
            this.cooldown = 160;
        }
    }

    private void smashAhead(Vec3 forward) {
        if (!(this.level() instanceof ServerLevel world)) {
            return;
        }
        BlockPos front = BlockPos.containing(this.getX() + forward.x * 2.6D, this.getY(), this.getZ() + forward.z * 2.6D);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = 0; dy <= 2; dy++) {
                    BlockPos pos = front.offset(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);
                    if (state.isAir()) {
                        continue;
                    }
                    float hardness = state.getDestroySpeed(world, pos);
                    if (hardness < 0.0F || hardness > 40.0F) {
                        continue;   // bedrock, obsidian walls and other things a ram should respect
                    }
                    world.destroyBlock(pos, false, this);
                }
            }
        }
    }

    @Override
    public void playChargeSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.RAVAGER_ROAR, SoundSource.HOSTILE, 2.4F, 0.6F);
    }
}
