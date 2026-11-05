package dev.arena.wotw.item;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * Arc rifle: the human copy of the Electric Machine's coil.
 *
 * <p>Right click to throw a crackling charge that arcs away and bursts into a small, burning
 * explosion. Cheap to shoot, so it is the answer to a drone charging at you.</p>
 */
public class ArcRifleItem extends Item {

    private static final int COOLDOWN = 22;
    private static final float POWER = 1.8F;

    public ArcRifleItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (world instanceof ServerLevel serverWorld) {
            Vec3 origin = user.getEyePosition().add(user.getViewVector(1.0F).scale(0.8D));
            Vec3 velocity = user.getViewVector(1.0F).scale(1.85D);
            ShellEntity.launch(serverWorld, user, origin, velocity, POWER, true);
            stack.hurtAndBreak(1, user, player -> player.broadcastBreakEvent(hand));
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.TRIDENT_THROW,
                SoundSource.PLAYERS, 1.0F, 1.7F);
        user.getCooldowns().addCooldown(this, COOLDOWN);
        return InteractionResultHolder.success(stack);
    }
}
