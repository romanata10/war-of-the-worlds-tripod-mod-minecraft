package dev.arena.wotw.block;

import dev.arena.wotw.entity.PilotTripodEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * The tripod blueprint: a human made schematic of a Martian walker.
 *
 * <p>Put the block down and a {@link PilotTripodEntity} is assembled on top of it — a steel tripod
 * with a cockpit. Right click to climb in, right click again to build another one (the blueprint
 * itself stays where it is, it is a plan, not a consumable).</p>
 */
public class TripodBlueprintBlock extends Block {

    public TripodBlueprintBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        if (!world.isClientSide) {
            this.build(world, pos);
        }
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                              BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        // one workshop, one machine: a blueprint will not assemble a second tripod while the
        // first one is still standing next to it
        if (!world.getEntitiesOfClass(PilotTripodEntity.class, new AABB(pos).inflate(14.0D),
                PilotTripodEntity::isAlive).isEmpty()) {
            world.playSound(null, pos, SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.BLOCKS, 0.7F, 0.6F);
            return InteractionResult.FAIL;
        }
        this.build(world, pos);
        return InteractionResult.CONSUME;
    }

    private void build(Level world, BlockPos pos) {
        if (!(world instanceof ServerLevel serverWorld)) {
            return;
        }
        PilotTripodEntity tripod = ModEntities.PILOT_TRIPOD.create(serverWorld);
        if (tripod == null) {
            return;
        }
        BlockPos spawn = pos.up();
        tripod.moveTo(spawn.getX() + 0.5D, spawn.getY(), spawn.getZ() + 0.5D,
                world.getRandom().nextFloat() * 360.0F, 0.0F);
        tripod.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(spawn), MobSpawnType.SPAWN_EGG, null, null);
        tripod.setPersistenceRequired();
        serverWorld.addFreshEntity(tripod);

        serverWorld.sendParticles(ParticleTypes.ELECTRIC_SPARK, spawn.getX() + 0.5D, spawn.getY() + 1.5D,
                spawn.getZ() + 0.5D, 60, 1.2D, 1.2D, 1.2D, 0.2D);
        world.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.6F, 1.1F);
    }
}
