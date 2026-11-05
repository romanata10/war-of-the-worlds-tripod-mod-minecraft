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
 * Alloy cannon: a hand held copy of the Bombarding Machine's mortar.
 *
 * <p>Right click to lob a heavy shell. It travels slower than the arc rifle but the crater it
 * leaves is the size of a block of TNT, which makes it the tool for taking down a Handling Machine
 * before it grabs you.</p>
 */
public class AlloyCannonItem extends Item {

    private static final int COOLDOWN = 60;
    private static final float POWER = 3.2F;

    public AlloyCannonItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (world instanceof ServerLevel serverWorld) {
            Vec3 origin = user.getEyePosition().add(user.getViewVector(1.0F).scale(0.9D));
            Vec3 velocity = user.getViewVector(1.0F).scale(1.35D).add(0.0D, 0.18D, 0.0D);
            ShellEntity.launch(serverWorld, user, origin, velocity, POWER, false);
            stack.hurtAndBreak(1, user, player -> player.broadcastBreakEvent(hand));
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.PLAYERS, 1.2F, 0.7F);
        user.getCooldowns().addCooldown(this, COOLDOWN);
        return InteractionResultHolder.success(stack);
    }
}
