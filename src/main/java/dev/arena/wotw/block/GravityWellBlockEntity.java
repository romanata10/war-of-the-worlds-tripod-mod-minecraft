package dev.arena.wotw.block;

import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Gravity well: a humming lens that bends the local gravity. Everything loose inside nine blocks is
 * dragged towards the pillar and lifted a little, so the player cannot walk or jump away from it.
 * Tripods are heavy enough to ignore it — this one is a trap for people, not a weapon against metal.
 */
public class GravityWellBlockEntity extends AbstractMartianDeviceBlockEntity {

    private static final double RADIUS = 9.0D;

    public GravityWellBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRAVITY_WELL, pos, state, 12);
    }

    @Override
    protected void pulse(Level world) {
        Vec3 center = new Vec3(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 1.5D,
                this.worldPosition.getZ() + 0.5D);
        boolean caught = false;
        for (Entity entity : world.getEntities(null, this.area(RADIUS))) {
            if (!(entity instanceof LivingEntity living) || living instanceof dev.arena.wotw.entity.AbstractTripodEntity) {
                continue;
            }
            Vec3 pull = center.subtract(living.position());
            double length = pull.length();
            if (length < 0.6D || length > RADIUS) {
                continue;
            }
            Vec3 direction = pull.scale(1.0D / length);
            double strength = 0.075D * (1.0D - length / RADIUS) + 0.02D;
            living.push(direction.x * strength, 0.06D, direction.z * strength);
            living.hurtMarked = true;
            if (living.isPassenger()) {
                living.stopRiding();
            }
            caught = true;
        }
        if (!caught) {
            return;
        }
        if (world instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.PORTAL, center.x, center.y, center.z, 18, 0.6D, 0.6D, 0.6D, 0.3D);
        }
        world.playSound(null, this.worldPosition, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.7F, 0.55F);
    }
}
