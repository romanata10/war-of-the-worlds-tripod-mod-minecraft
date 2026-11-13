package dev.arena.wotw.block;

import dev.arena.wotw.entity.MartianLouseEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

/**
 * The louse spore sack: a leathery pod the machines leave behind.
 *
 * <p>It does nothing at all until someone breaks it open — and then three lice come out of it. Right
 * click with an empty hand (or a pickaxe) and it bursts.</p>
 */
public class SporeSackBlock extends Block {

    public SporeSackBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (!(world instanceof ServerLevel server)) {
            return InteractionResult.PASS;
        }
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            MartianLouseEntity louse = ModEntities.MARTIAN_LOUSE.create(server);
            if (louse == null) {
                break;
            }
            louse.moveTo(pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 2.0D,
                    pos.getY() + 0.1D,
                    pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 2.0D,
                    random.nextFloat() * 360.0F, 0.0F);
            server.addFreshEntity(louse);
        }
        server.sendParticles(ParticleTypes.ITEM_SLIME, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                30, 0.5D, 0.4D, 0.5D, 0.05D);
        server.playSound(null, pos, SoundEvents.SLIME_BLOCK_BREAK, SoundSource.BLOCKS, 1.4F, 0.7F);
        server.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        return InteractionResult.CONSUME;
    }
}
