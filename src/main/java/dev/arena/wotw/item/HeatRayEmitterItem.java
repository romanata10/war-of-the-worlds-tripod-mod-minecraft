package dev.arena.wotw.item;

import dev.arena.wotw.registry.ModSounds;
import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Reverse engineered Martian heat ray: a heavy handheld emitter that fires a real heat ray beam.
 * Uses the same {@link BeamEntity} as the tripods, so it burns and pierces exactly like theirs.
 */
public class HeatRayEmitterItem extends Item {

    private static final int COOLDOWN_TICKS = 24;
    private static final float DAMAGE = 7.0F;

    public HeatRayEmitterItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        Vec3 direction = user.getViewVector(1.0F);
        Vec3 origin = user.getEyePosition().add(direction.scale(1.0D));

        if (!world.isClientSide) {
            BeamEntity beam = new BeamEntity(world, origin, direction, BeamKind.HEAT_RAY, user, DAMAGE);
            world.addFreshEntity(beam);
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    ModSounds.HEAT_RAY, SoundSource.PLAYERS, 1.4F, 1.3F);
        }

        user.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        if (!user.getAbilities().instabuild) {
            stack.hurtAndBreak(1, user, broken -> broken.broadcastBreakEvent(hand));
        }
        user.swing(hand);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.wotw.heat_ray_emitter.tooltip").formatted(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("item.wotw.heat_ray_emitter.tooltip2").formatted(ChatFormatting.DARK_GRAY));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
