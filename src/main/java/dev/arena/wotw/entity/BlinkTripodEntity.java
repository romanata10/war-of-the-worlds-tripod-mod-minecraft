package dev.arena.wotw.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The blinker: a slim hexagonal core inside a frame of drifting vanes, one huge violet lens.
 *
 * <p><b>Signature ability</b>: the <b>blink</b>. It does not run — it steps through space, folding
 * itself out of existence and reappearing eight to fourteen blocks closer to its prey. Running away
 * does not work; the only way to break contact is to break line of sight or to kill it before it
 * blinks again.</p>
 */
public class BlinkTripodEntity extends AbstractTripodEntity {

    private static final int BLINK_INTERVAL = 110;
    private static final double MIN_HOP = 8.0D;
    private static final double MAX_HOP = 14.0D;

    public BlinkTripodEntity(EntityType<? extends BlinkTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(240.0D, 0.24D, 14.0D, 6.0D, 4.0D, 0.7D, 64.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(BLINK_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !this.hasLineOfSight(target)) {
            return;
        }
        Vec3 toTarget = target.position().subtract(this.position());
        double distance = Math.sqrt(toTarget.x * toTarget.x + toTarget.z * toTarget.z);
        if (distance < MIN_HOP) {
            return;
        }
        double hop = Math.min(MAX_HOP, distance - 4.0D);
        Vec3 direction = new Vec3(toTarget.x, 0.0D, toTarget.z).normalize();
        Vec3 destination = this.position().add(direction.scale(hop));
        BlockPos landing = BlockPos.containing(destination.x, this.getY(), destination.z);
        BlockPos feet = this.findGround(server, landing);
        if (feet == null) {
            return;
        }
        server.sendParticles(ParticleTypes.REVERSE_PORTAL, this.getX(), this.getY() + this.getBbHeight() * 0.5D,
                this.getZ(), 40, 0.8D, this.getBbHeight() * 0.35D, 0.8D, 0.05D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.HOSTILE, 1.8F, 1.3F);
        this.moveTo(feet.getX() + 0.5D, feet.getY(), feet.getZ() + 0.5D, this.getYRot(), this.getXRot());
        this.getNavigation().stop();
        server.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + this.getBbHeight() * 0.5D,
                this.getZ(), 40, 0.8D, this.getBbHeight() * 0.35D, 0.8D, 0.15D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.HOSTILE, 1.4F, 1.6F);
    }

    /** Finds a standing spot near `pos`, searching a few blocks up and down. */
    private BlockPos findGround(ServerLevel world, BlockPos pos) {
        for (int dy = 3; dy >= -6; dy--) {
            BlockPos candidate = pos.offset(0, dy, 0);
            if (world.getBlockState(candidate).isAir()
                    && world.getBlockState(candidate.above()).isAir()
                    && world.getBlockState(candidate.below()).canOcclude()) {
                return candidate;
            }
        }
        return null;
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.REVERSE_PORTAL,
                this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                this.getY() + 0.5D + this.getBbHeight() * 0.6D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(), 0.0D, 0.01D, 0.0D);
    }
}
