package dev.arena.wotw.item;

import dev.arena.wotw.entity.CapsuleEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * A sealed Martian capsule. Used from the hand it is launched forty blocks straight up and comes
 * down where the player is standing — which is exactly what the first invasion wave does on its own.
 */
public class MartianCapsuleItem extends Item {

    public MartianCapsuleItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (world instanceof ServerLevel serverWorld) {
            BlockPos target = user.blockPosition();
            CapsuleEntity capsule = CapsuleEntity.drop(serverWorld, target.getX() + 0.5D, target.getZ() + 0.5D, true);
            if (capsule == null) {
                return InteractionResultHolder.fail(stack);
            }
            stack.shrink(1);
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WITHER_SHOOT,
                SoundSource.PLAYERS, 1.2F, 0.5F);
        return InteractionResultHolder.success(stack);
    }
}
