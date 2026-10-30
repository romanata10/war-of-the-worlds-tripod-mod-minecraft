package dev.arena.wotw.block;

import dev.arena.wotw.registry.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * The Martian cylinder. Right-click it and the hatch opens: one random tripod climbs out
 * (or, more accurately, is teleported out because the world is not ready for Martian logistics).
 */
public class MartianCylinderBlock extends Block {

    public MartianCylinderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                              BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        EntityType<?> type = ModEntities.randomTripod(world.getRandom());
        Entity entity = type.create(world);
        if (entity == null) {
            return ActionResult.FAIL;
        }

        entity.refreshPositionAndAngles(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D,
                world.getRandom().nextFloat() * 360.0F, 0.0F);
        if (entity instanceof MobEntity mob) {
            mob.setPersistent();
            if (world instanceof ServerWorld serverWorld) {
                mob.initialize(serverWorld, serverWorld.getLocalDifficulty(pos), SpawnReason.SPAWN_EGG, null, null);
            }
        }
        world.spawnEntity(entity);

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            serverWorld.spawnParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D,
                    60, 1.2D, 1.2D, 1.2D, 0.05D);
        }
        world.playSound(null, pos, SoundEvents.ENTITY_WARDEN_ROAR, SoundCategory.HOSTILE, 3.0F, 0.5F);
        world.playSound(null, pos, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 2.0F, 0.6F);
        world.removeBlock(pos, false);
        return ActionResult.CONSUME;
    }
}
