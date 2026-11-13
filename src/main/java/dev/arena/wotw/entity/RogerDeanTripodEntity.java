package dev.arena.wotw.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Roger Dean's machine: a copper bird of prey — a crested skull with a hooked beak on an S-bent
 * neck, a slim pelvis and long thin legs.
 *
 * <p><b>Signature ability</b>: the <b>magpie strike</b>. The beak is built to snatch things, and a
 * hit knocks whatever the victim is holding out of their hands and onto the ground. Fight it with an
 * empty hand, or fight it twice.</p>
 */
public class RogerDeanTripodEntity extends AbstractTripodEntity {

    public RogerDeanTripodEntity(EntityType<? extends RogerDeanTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(230.0D, 0.38D, 13.0D, 6.0D, 4.0D, 0.55D, 52.0D);
    }

    @Override
    protected void addExtraGoals() {
        // a beak, not a cannon
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        super.onMeleeHit(victim);
        if (!(victim instanceof Player player) || this.level().isClientSide) {
            return;
        }
        if (this.random.nextFloat() > 0.7F) {
            return;
        }
        ItemStack held = player.getMainHandItem();
        if (held.isEmpty()) {
            return;
        }
        ItemStack snatched = held.copy();
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        player.drop(snatched, false, true);
        this.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ITEM_PICKUP, SoundSource.HOSTILE, 1.2F, 0.6F);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT, player.getX(), player.getY() + 1.2D, player.getZ(),
                    14, 0.4D, 0.4D, 0.4D, 0.2D);
        }
    }
}
