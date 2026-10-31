package dev.arena.wotw.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

    public RamTripodEntity(EntityType<? extends RamTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
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
        if (this.squaredDistanceTo(target) > CHARGE_RANGE * CHARGE_RANGE || !this.canSee(target)) {
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
        Vec3d flat = new Vec3d(target.getX() - this.getX(), 0.0D, target.getZ() - this.getZ());
        if (flat.lengthSquared() < 0.01D) {
            return;
        }
        Vec3d forward = flat.normalize();
        this.setVelocity(forward.x * 0.95D, this.getVelocity().y, forward.z * 0.95D);
        this.velocityModified = true;
        this.getLookControl().lookAt(target.getX(), target.getY() + 1.0D, target.getZ(), 60.0F, 60.0F);
        this.smashAhead(forward);

        if (this.squaredDistanceTo(target) < 12.0D) {
            target.damage(this.getDamageSources().mobAttack(this), 18.0F);
            target.addVelocity(forward.x * 1.8D, 0.45D, forward.z * 1.8D);
            target.velocityModified = true;
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.8F, 0.7F);
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.EXPLOSION, target.getX(), target.getY() + 1.0D,
                        target.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
            }
            this.charging = false;
            this.cooldown = 160;
        }
    }

    private void smashAhead(Vec3d forward) {
        if (!(this.getWorld() instanceof ServerWorld world)) {
            return;
        }
        BlockPos front = BlockPos.ofFloored(this.getX() + forward.x * 2.6D, this.getY(), this.getZ() + forward.z * 2.6D);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = 0; dy <= 2; dy++) {
                    BlockPos pos = front.add(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);
                    if (state.isAir()) {
                        continue;
                    }
                    float hardness = state.getHardness(world, pos);
                    if (hardness < 0.0F || hardness > 40.0F) {
                        continue;   // bedrock, obsidian walls and other things a ram should respect
                    }
                    world.breakBlock(pos, false, this);
                }
            }
        }
    }

    @Override
    public void playChargeSound() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_RAVAGER_ROAR, SoundCategory.HOSTILE, 2.4F, 0.6F);
    }
}
