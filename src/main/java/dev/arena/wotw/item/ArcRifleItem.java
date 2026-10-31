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
 * Arc rifle: the human copy of the Electric Machine's coil.
 *
 * <p>Right click to throw a crackling charge that arcs away and bursts into a small, burning
 * explosion. Cheap to shoot, so it is the answer to a drone charging at you.</p>
 */
public class ArcRifleItem extends Item {

    private static final int COOLDOWN = 22;
    private static final float POWER = 1.8F;

    public ArcRifleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world instanceof ServerWorld serverWorld) {
            Vec3d origin = user.getEyePos().add(user.getRotationVec(1.0F).multiply(0.8D));
            Vec3d velocity = user.getRotationVec(1.0F).multiply(1.85D);
            ShellEntity.launch(serverWorld, user, origin, velocity, POWER, true);
            stack.damage(1, user, player -> player.sendToolBreakStatus(hand));
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THROW,
                SoundCategory.PLAYERS, 1.0F, 1.7F);
        user.getItemCooldownManager().set(this, COOLDOWN);
        return TypedActionResult.success(stack);
    }
}
