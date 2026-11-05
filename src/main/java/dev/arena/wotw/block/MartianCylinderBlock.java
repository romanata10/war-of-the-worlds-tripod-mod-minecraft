package dev.arena.wotw.block;

import dev.arena.wotw.registry.ModSounds;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * The Martian cylinder. Right-click it and the hatch opens: one random tripod climbs out
 * (or, more accurately, is teleported out because the world is not ready for Martian logistics).
 */
public class MartianCylinderBlock extends Block {

    public MartianCylinderBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                              BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        EntityType<?> type = ModEntities.randomTripod(world.getRandom());
        Entity entity = type.create(world);
        if (entity == null) {
            return InteractionResult.FAIL;
        }

        entity.moveTo(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D,
                world.getRandom().nextFloat() * 360.0F, 0.0F);
        if (entity instanceof Mob mob) {
            mob.setPersistenceRequired();
            if (world instanceof ServerLevel serverWorld) {
                mob.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(pos), MobSpawnType.SPAWN_EGG, null, null);
            }
        }
        world.addFreshEntity(entity);

        if (world instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.EXPLOSION_EMITTER, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D,
                    60, 1.2D, 1.2D, 1.2D, 0.05D);
        }
        world.playSound(null, pos, ModSounds.TRIPOD_HORN, SoundSource.HOSTILE, 3.0F, 0.5F);
        world.playSound(null, pos, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 2.0F, 0.6F);
        world.removeBlock(pos, false);
        return InteractionResult.CONSUME;
    }
}
