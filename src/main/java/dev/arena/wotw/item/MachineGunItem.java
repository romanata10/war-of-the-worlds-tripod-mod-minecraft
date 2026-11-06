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
 * Machine gun: what the pistol wants to be when it grows up.
 *
 * <p>Three shots a second with a little spread, which chews through a Martian spider before it
 * closes in and is still fast enough to answer a drone. The barrel wears out accordingly.</p>
 */
public class MachineGunItem extends Item {

    /** Ticks between shots: about three rounds per second. */
    private static final int COOLDOWN = 4;
    private static final float DAMAGE = 4.5F;
    private static final double SPEED = 3.4D;
    /** Radians of random cone, small enough to still hit a louse at ten blocks. */
    private static final double SPREAD = 0.022D;

    public MachineGunItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (!world.isClientSide) {
            Vec3 look = user.getViewVector(1.0F);
            Vec3 origin = user.getEyePosition().add(look.scale(0.7D));
            Vec3 velocity = look.scale(SPEED).add(
                    (world.random.nextDouble() - 0.5D) * SPREAD * 4.0D,
                    (world.random.nextDouble() - 0.5D) * SPREAD * 4.0D,
                    (world.random.nextDouble() - 0.5D) * SPREAD * 4.0D);
            ShellEntity.launchBullet(world, user, origin, velocity, DAMAGE);
            stack.hurtAndBreak(1, user, player -> player.broadcastBreakEvent(hand));
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.CROSSBOW_SHOOT,
                SoundSource.PLAYERS, 0.6F, 1.2F);
        user.getCooldowns().addCooldown(this, COOLDOWN);
        return InteractionResultHolder.success(stack);
    }
}
