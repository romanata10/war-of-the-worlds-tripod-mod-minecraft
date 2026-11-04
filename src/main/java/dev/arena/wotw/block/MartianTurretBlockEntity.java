package dev.arena.wotw.block;

import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * The gun of the tall Martian turret: it sweeps for players and shoots a lance beam at the closest
 * one every couple of seconds.
 */
public class MartianTurretBlockEntity extends BlockEntity {

    private static final double RANGE = 24.0D;
    private static final int COOLDOWN = 50;

    private int cooldown = 20;

    public MartianTurretBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MARTIAN_TURRET, pos, state);
    }

    /** Called from the block's ticker every game tick. */
    public void tick() {
        Level world = this.level();
        if (world == null || world.isClientSide) {
            return;
        }
        if (this.cooldown > 0) {
            this.cooldown--;
            return;
        }
        Player player = world.getNearestPlayer(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D,
                this.pos.getZ() + 0.5D, RANGE, false);
        if (player == null || !player.isAlive()) {
            this.cooldown = 10;
            return;
        }
        Vec3 origin = new Vec3(this.pos.getX() + 0.5D, this.pos.getY() + 1.9D, this.pos.getZ() + 0.5D);
        Vec3 direction = player.getEyePosition().subtract(origin);
        if (direction.lengthSqr() < 0.1D) {
            return;
        }
        BeamEntity beam = new BeamEntity(world, origin, direction.normalize(), BeamKind.LANCE, null, 5.0F);
        world.addFreshEntity(beam);
        world.playSound(null, this.pos, net.minecraft.sounds.SoundEvents.BEACON_ACTIVATE,
                SoundSource.BLOCKS, 1.2F, 1.6F);
        if (world instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.ELECTRIC_SPARK, origin.x, origin.y, origin.z,
                    14, 0.2D, 0.2D, 0.2D, 0.2D);
        }
        this.cooldown = COOLDOWN;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("Cooldown", this.cooldown);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.cooldown = nbt.getInt("Cooldown");
    }
}
