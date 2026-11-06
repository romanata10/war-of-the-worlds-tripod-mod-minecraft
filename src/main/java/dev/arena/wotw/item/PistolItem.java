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
 * Sidearm: a plain service pistol that fires a fast, flat bullet.
 *
 * <p>Cheap to make, cheap to shoot, useless against a Goliath — but it kills a louse in one shot and it is the first thing a survivor builds after a
 * capsule lands.
 */
public class PistolItem extends Item {

    /** Ticks between shots; a pistol is fast but not a machine gun. */
    private static final int COOLDOWN = 10;
    private static final float DAMAGE = 5.0F;
    private static final double SPEED = 3.1D;

    public PistolItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (!world.isClientSide) {
            Vec3 look = user.getViewVector(1.0F);
            Vec3 origin = user.getEyePosition().add(look.scale(0.6D));
            ShellEntity.launchBullet(world, user, origin, look.scale(SPEED), DAMAGE);
            stack.hurtAndBreak(1, user, player -> player.broadcastBreakEvent(hand));
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.CROSSBOW_SHOOT,
                SoundSource.PLAYERS, 0.7F, 1.6F);
        user.getCooldowns().addCooldown(this, COOLDOWN);
        return InteractionResultHolder.success(stack);
    }
}
