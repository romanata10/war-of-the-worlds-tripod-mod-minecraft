package dev.arena.wotw.item;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Alloy cannon: a hand held copy of the Bombarding Machine's mortar.
 *
 * <p>Right click to lob a heavy shell. It travels slower than the arc rifle but the crater it
 * leaves is the size of a block of TNT, which makes it the tool for taking down a Handling Machine
 * before it grabs you.</p>
 */
public class AlloyCannonItem extends Item {

    private static final int COOLDOWN = 60;
    private static final float POWER = 3.2F;

    public AlloyCannonItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world instanceof ServerWorld serverWorld) {
            Vec3d origin = user.getEyePos().add(user.getRotationVec(1.0F).multiply(0.9D));
            Vec3d velocity = user.getRotationVec(1.0F).multiply(1.35D).add(0.0D, 0.18D, 0.0D);
            ShellEntity.launch(serverWorld, user, origin, velocity, POWER, false);
            stack.damage(1, user, player -> player.sendToolBreakStatus(hand));
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.PLAYERS, 1.2F, 0.7F);
        user.getItemCooldownManager().set(this, COOLDOWN);
        return TypedActionResult.success(stack);
    }
}
