package dev.arena.wotw.block;

import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
        World world = this.getWorld();
        if (world == null || world.isClient) {
            return;
        }
        if (this.cooldown > 0) {
            this.cooldown--;
            return;
        }
        PlayerEntity player = world.getClosestPlayer(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D,
                this.pos.getZ() + 0.5D, RANGE, false);
        if (player == null || !player.isAlive()) {
            this.cooldown = 10;
            return;
        }
        Vec3d origin = new Vec3d(this.pos.getX() + 0.5D, this.pos.getY() + 1.9D, this.pos.getZ() + 0.5D);
        Vec3d direction = player.getEyePos().subtract(origin);
        if (direction.lengthSquared() < 0.1D) {
            return;
        }
        BeamEntity beam = new BeamEntity(world, origin, direction.normalize(), BeamKind.LANCE, null, 5.0F);
        world.spawnEntity(beam);
        world.playSound(null, this.pos, net.minecraft.sound.SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.BLOCKS, 1.2F, 1.6F);
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, origin.x, origin.y, origin.z,
                    14, 0.2D, 0.2D, 0.2D, 0.2D);
        }
        this.cooldown = COOLDOWN;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Cooldown", this.cooldown);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.cooldown = nbt.getInt("Cooldown");
    }
}
