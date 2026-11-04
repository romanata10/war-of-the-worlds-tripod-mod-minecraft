package dev.arena.wotw.entity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

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

    public DiggingMachineEntity(EntityType<? extends DiggingMachineEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(210.0D, 0.27D, 12.0D, 14.0D, 6.0D, 1.0D, 48.0D);
    }

    @Override
    protected void serverTick() {
        this.digTicks++;
        if (this.digTicks < DIG_INTERVAL) {
            return;
        }
        this.digTicks = 0;
        if (!(this.level() instanceof ServerLevel world)) {
            return;
        }

        LivingEntity target = this.getTarget();
        boolean digDown = target != null && target.getY() < this.getY() - 3.0D;
        Vec3 direction = digDown ? new Vec3(0.0D, -1.0D, 0.0D) : this.getViewVector(1.0F);

        boolean carved = false;
        for (int step = 2; step <= 4; step++) {
            BlockPos center = BlockPos.containing(
                    this.getX() + direction.x * step,
                    this.getY() + this.getBbHeight() * 0.25D + direction.y * step,
                    this.getZ() + direction.z * step);
            carved |= this.carve(world, center);
        }
        if (carved) {
            world.sendParticles(ParticleTypes.POOF, this.getX(), this.getY() + 0.6D, this.getZ(),
                    10, 0.8D, 0.4D, 0.8D, 0.04D);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.HOSTILE, 1.4F, 0.6F);
        }
    }

    /** Removes the diggable blocks around the given centre. Returns true if anything was removed. */
    private boolean carve(ServerLevel world, BlockPos center) {
        boolean carved = false;
        for (BlockPos pos : BlockPos.betweenClosed(center.add(-DIG_RADIUS, -DIG_RADIUS, -DIG_RADIUS),
                center.add(DIG_RADIUS, DIG_RADIUS, DIG_RADIUS))) {
            BlockState state = world.getBlockState(pos);
            if (state.isAir() || state.isOf(Blocks.BEDROCK) || state.is(BlockTags.PORTALS)) {
                continue;
            }
            if (state.getDestroySpeed(world, pos) < 0.0F) {
                continue;
            }
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), net.minecraft.world.level.block.Block.UPDATE_ALL);
            carved = true;
        }
        return carved;
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) == 0) {
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + this.getViewVector(1.0F).x * 1.6D,
                    this.getY() + this.getBbHeight() * 0.3D,
                    this.getZ() + this.getViewVector(1.0F).z * 1.6D, 0.0D, 0.02D, 0.0D);
        }
    }
}
