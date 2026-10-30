package dev.arena.wotw.item;

import dev.arena.wotw.registry.ModSounds;
import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Reverse engineered Martian heat ray: a heavy handheld emitter that fires a real heat ray beam.
 * Uses the same {@link BeamEntity} as the tripods, so it burns and pierces exactly like theirs.
 */
public class HeatRayEmitterItem extends Item {

    private static final int COOLDOWN_TICKS = 24;
    private static final float DAMAGE = 7.0F;

    public HeatRayEmitterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Vec3d direction = user.getRotationVec(1.0F);
        Vec3d origin = user.getEyePos().add(direction.multiply(1.0D));

        if (!world.isClient) {
            BeamEntity beam = new BeamEntity(world, origin, direction, BeamKind.HEAT_RAY, user, DAMAGE);
            world.spawnEntity(beam);
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    ModSounds.HEAT_RAY, SoundCategory.PLAYERS, 1.4F, 1.3F);
        }

        user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        if (!user.getAbilities().creativeMode) {
            stack.damage(1, user, broken -> broken.sendToolBreakStatus(hand));
        }
        user.swingHand(hand);
        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.wotw.heat_ray_emitter.tooltip").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.wotw.heat_ray_emitter.tooltip2").formatted(Formatting.DARK_GRAY));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
