package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * The red-eyed machine of the old YouTube films: a glossy dark shell with one huge red lens in the
 * brow and three needle legs.
 *
 * <p><b>Signature abilities</b>: the heat ray that blows holes in whatever it hits, and the
 * <b>kick</b> — the machine plants a leg and brings the other one down on a wall, and the column of
 * blocks above the blow comes down with it.</p>
 */
public class RedEyeTripodEntity extends AbstractTripodEntity {

    private static final int KICK_INTERVAL = 140;
    private static final double KICK_RANGE = 9.0D;

    public RedEyeTripodEntity(EntityType<? extends RedEyeTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(340.0D, 0.30D, 14.0D, 8.0D, 5.0D, 0.85D, 54.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 9.0F, 40.0D, 60, 100));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(KICK_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > KICK_RANGE * KICK_RANGE) {
            return;
        }
        this.setAbilityCharging(true);
        this.kick(server, target);
    }

    /** The stomp: the base of a column is smashed and everything above it comes down. */
    private void kick(ServerLevel server, LivingEntity target) {
        Vec3 forward = target.position().subtract(this.position());
        if (forward.lengthSqr() < 0.01D) {
            return;
        }
        forward = forward.normalize();
        BlockPos origin = this.blockPosition();
        int shattered = 0;
        for (int step = 2; step <= 5; step++) {
            for (int side = -1; side <= 1; side++) {
                double dx = forward.x * step + forward.z * side;
                double dz = forward.z * step - forward.x * side;
                BlockPos base = origin.offset((int) Math.round(dx), 0, (int) Math.round(dz));
                for (int height = 0; height < 10; height++) {
                    BlockPos pos = base.above(height);
                    BlockState state = server.getBlockState(pos);
                    if (state.isAir()) {
                        continue;
                    }
                    float hardness = state.getDestroySpeed(server, pos);
                    if (hardness < 0.0F || hardness > 30.0F) {
                        continue;
                    }
                    if (height > 2 + this.random.nextInt(3)) {
                        continue;
                    }
                    server.destroyBlock(pos, true, this);
                    shattered++;
                }
            }
        }
        target.hurt(this.damageSources().mobAttack(this), 11.0F);
        Vec3 push = forward.scale(0.8D);
        target.push(push.x, 0.45D, push.z);
        target.hurtMarked = true;
        server.sendParticles(ParticleTypes.EXPLOSION,
                this.getX() + forward.x * 3.0D, this.getY() + 0.4D, this.getZ() + forward.z * 3.0D,
                Math.min(14, 3 + shattered / 3), 1.2D, 0.4D, 1.2D, 0.05D);
        server.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY() + 1.0D, target.getZ(),
                18, 0.6D, 0.6D, 0.6D, 0.2D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.HOSTILE, 1.6F, 0.7F);
        if (shattered > 0) {
            server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.STONE_BREAK,
                    SoundSource.HOSTILE, 1.8F, 0.6F);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 1.4D,
                this.getY() + this.getBbHeight() * 0.62D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 1.4D, 0.0D, 0.01D, 0.0D);
    }
}
