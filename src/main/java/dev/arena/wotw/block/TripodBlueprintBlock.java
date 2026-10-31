package dev.arena.wotw.block;

import dev.arena.wotw.entity.PilotTripodEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * The tripod blueprint: a human made schematic of a Martian walker.
 *
 * <p>Put the block down and a {@link PilotTripodEntity} is assembled on top of it — a steel tripod
 * with a cockpit. Right click to climb in, right click again to build another one (the blueprint
 * itself stays where it is, it is a plan, not a consumable).</p>
 */
public class TripodBlueprintBlock extends Block {

    public TripodBlueprintBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            this.build(world, pos);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                              BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        // one workshop, one machine: a blueprint will not assemble a second tripod while the
        // first one is still standing next to it
        if (!world.getEntitiesByClass(PilotTripodEntity.class, new Box(pos).expand(14.0D),
                PilotTripodEntity::isAlive).isEmpty()) {
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), SoundCategory.BLOCKS, 0.7F, 0.6F);
            return ActionResult.FAIL;
        }
        this.build(world, pos);
        return ActionResult.CONSUME;
    }

    private void build(World world, BlockPos pos) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }
        PilotTripodEntity tripod = ModEntities.PILOT_TRIPOD.create(serverWorld);
        if (tripod == null) {
            return;
        }
        BlockPos spawn = pos.up();
        tripod.refreshPositionAndAngles(spawn.getX() + 0.5D, spawn.getY(), spawn.getZ() + 0.5D,
                world.getRandom().nextFloat() * 360.0F, 0.0F);
        tripod.initialize(serverWorld, serverWorld.getLocalDifficulty(spawn), SpawnReason.SPAWN_EGG, null, null);
        tripod.setPersistent();
        serverWorld.spawnEntity(tripod);

        serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, spawn.getX() + 0.5D, spawn.getY() + 1.5D,
                spawn.getZ() + 0.5D, 60, 1.2D, 1.2D, 1.2D, 0.2D);
        world.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.6F, 1.1F);
    }
}
