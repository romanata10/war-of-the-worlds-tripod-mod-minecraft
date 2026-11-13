package dev.arena.wotw.block;

import dev.arena.wotw.entity.projectile.ShellEntity;
import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Mortar emplacement: it does not shoot straight, it lobs a shell in an arc, so cover does not help
 * against it. Range 42 blocks, one shell every five seconds.
 */
public class MartianMortarBlockEntity extends AbstractMartianDeviceBlockEntity {

    private static final double RANGE = 42.0D;

    public MartianMortarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MARTIAN_MORTAR, pos, state, 100);
    }

    @Override
    protected void pulse(Level world) {
        Player player = world.getNearestPlayer(this.worldPosition.getX() + 0.5D,
                this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D, RANGE, false);
        if (player == null || !player.isAlive() || player.isCreative() || player.isSpectator()) {
            return;
        }
        if (!world.hasChunkAt(player.blockPosition())) {
            return;
        }
        Vec3 origin = new Vec3(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 1.1D,
                this.worldPosition.getZ() + 0.5D);
        Vec3 delta = player.position().subtract(origin);
        double distance = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        if (distance < 3.0D) {
            return;
        }
        // a lobbed shot: the horizontal speed is fixed and the vertical part makes up for the drop
        double speed = 0.62D;
        double flight = distance / speed;
        double vy = 0.36D + flight * 0.014D;
        Vec3 velocity = new Vec3(delta.x / distance * speed, vy, delta.z / distance * speed);
        ShellEntity.launch(world, null, origin, velocity, 3.4F, false);
        world.playSound(null, this.worldPosition, SoundEvents.FIREWORK_ROCKET_LAUNCH,
                SoundSource.BLOCKS, 1.6F, 0.7F);
        if (world instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, origin.x, origin.y, origin.z,
                    12, 0.2D, 0.2D, 0.2D, 0.02D);
        }
    }
}
