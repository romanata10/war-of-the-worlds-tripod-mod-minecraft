package dev.arena.wotw.item;

import dev.arena.wotw.entity.CapsuleEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A sealed Martian capsule. Used from the hand it is launched forty blocks straight up and comes
 * down where the player is standing — which is exactly what the first invasion wave does on its own.
 */
public class MartianCapsuleItem extends Item {

    public MartianCapsuleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world instanceof ServerWorld serverWorld) {
            BlockPos target = user.getBlockPos();
            CapsuleEntity capsule = CapsuleEntity.drop(serverWorld, target.getX() + 0.5D, target.getZ() + 0.5D, true);
            if (capsule == null) {
                return TypedActionResult.fail(stack);
            }
            stack.decrement(1);
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WITHER_SHOOT,
                SoundCategory.PLAYERS, 1.2F, 0.5F);
        return TypedActionResult.success(stack);
    }
}
