package dev.arena.wotw.item;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The nuclear bomb: the last argument of the human army.
 *
 * <p>Throw it and run. The blast flattens a twenty block crater, sets the ground on fire and leaves
 * a cloud of fallout that withers everything that breathes inside it for another fifteen seconds.</p>
 */
public class NuclearBombItem extends Item {

    /** One bomb per four minutes, so a stash of them cannot be spammed. */
    private static final int COOLDOWN = 240;
    private static final double SPEED = 1.05D;

    public NuclearBombItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (!world.isClientSide) {
            Vec3 look = user.getViewVector(1.0F);
            Vec3 origin = user.getEyePosition().add(look.scale(0.8D));
            ShellEntity.launchNuke(world, user, origin, look.scale(SPEED).add(0.0D, 0.42D, 0.0D));
            if (!user.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH,
                SoundSource.PLAYERS, 1.6F, 0.45F);
        user.getCooldowns().addCooldown(this, COOLDOWN);
        return InteractionResultHolder.success(stack);
    }
}
