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
 * Rocket launcher: one rocket, one very large hole.
 *
 * <p>Slow to reload and heavy to carry, but it is the only hand held weapon in the mod that reliably
 * takes a leg off a Martian walker.</p>
 */
public class RocketLauncherItem extends Item {

    private static final int COOLDOWN = 70;
    private static final float POWER = 4.4F;
    private static final double SPEED = 1.55D;

    public RocketLauncherItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (!world.isClientSide) {
            Vec3 look = user.getViewVector(1.0F);
            Vec3 origin = user.getEyePosition().add(look.scale(0.9D)).subtract(0.0D, 0.15D, 0.0D);
            ShellEntity.launch(world, user, origin, look.scale(SPEED).add(0.0D, 0.04D, 0.0D), POWER, false);
            stack.hurtAndBreak(1, user, player -> player.broadcastBreakEvent(hand));
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH,
                SoundSource.PLAYERS, 1.4F, 0.8F);
        user.getCooldowns().addCooldown(this, COOLDOWN);
        return InteractionResultHolder.success(stack);
    }
}
