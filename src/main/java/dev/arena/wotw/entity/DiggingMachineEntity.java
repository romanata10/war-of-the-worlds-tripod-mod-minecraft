package dev.arena.wotw.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Digging Machine — the borer of the Martian army.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Tunnelling</b> — the drill eats a corridor through the world: it carves the blocks in
 *     front of it (and, when its prey hides underground, straight down). It leaves trenches behind
 *     and it can reach players who think a roof over their head is enough.</li>
 * </ul>
 */
public class DiggingMachineEntity extends AbstractTripodEntity {

    private static final int DIG_INTERVAL = 8;
    private static final int DIG_RADIUS = 1;

    private int digTicks;

    public DiggingMachineEntity(EntityType<? extends DiggingMachineEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(210.0D, 0.27D, 12.0D, 14.0D, 6.0D, 1.0D, 48.0D);
    }

    @Override
    protected void serverTick() {
        this.digTicks++;
        if (this.digTicks < DIG_INTERVAL) {
            return;
        }
        this.digTicks = 0;
        if (!(this.getWorld() instanceof ServerWorld world)) {
            return;
        }

        LivingEntity target = this.getTarget();
        boolean digDown = target != null && target.getY() < this.getY() - 3.0D;
        Vec3d direction = digDown ? new Vec3d(0.0D, -1.0D, 0.0D) : this.getRotationVec(1.0F);

        boolean carved = false;
        for (int step = 2; step <= 4; step++) {
            BlockPos center = BlockPos.ofFloored(
                    this.getX() + direction.x * step,
                    this.getY() + this.getHeight() * 0.25D + direction.y * step,
                    this.getZ() + direction.z * step);
            carved |= this.carve(world, center);
        }
        if (carved) {
            world.spawnParticles(ParticleTypes.POOF, this.getX(), this.getY() + 0.6D, this.getZ(),
                    10, 0.8D, 0.4D, 0.8D, 0.04D);
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.HOSTILE, 1.4F, 0.6F);
        }
    }

    /** Removes the diggable blocks around the given centre. Returns true if anything was removed. */
    private boolean carve(ServerWorld world, BlockPos center) {
        boolean carved = false;
        for (BlockPos pos : BlockPos.iterate(center.add(-DIG_RADIUS, -DIG_RADIUS, -DIG_RADIUS),
                center.add(DIG_RADIUS, DIG_RADIUS, DIG_RADIUS))) {
            BlockState state = world.getBlockState(pos);
            if (state.isAir() || state.isOf(Blocks.BEDROCK) || state.isIn(BlockTags.PORTALS)) {
                continue;
            }
            if (state.getHardness(world, pos) < 0.0F) {
                continue;
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), net.minecraft.block.Block.NOTIFY_ALL);
            carved = true;
        }
        return carved;
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) == 0) {
            this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + this.getRotationVec(1.0F).x * 1.6D,
                    this.getY() + this.getHeight() * 0.3D,
                    this.getZ() + this.getRotationVec(1.0F).z * 1.6D, 0.0D, 0.02D, 0.0D);
        }
    }
}
