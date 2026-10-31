package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * "Martian Heron" from the docudrama <i>The Great Martian War 1913-1917</i>.
 *
 * <p>The tallest and most elegant walking machine of the invasion: a thin heron-like body on
 * stilt legs with a long forward beak that houses a precision heat lance.</p>
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Precision lance</b> — the longest range weapon in the mod (52 blocks), a single
 *     devastating pinpoint shot with a long cooldown.</li>
 * </ul>
 */
public class MartianHeronEntity extends AbstractTripodEntity {

    public MartianHeronEntity(EntityType<? extends MartianHeronEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(150.0D, 0.38D, 12.0D, 4.0D, 2.0D, 0.85D, 64.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.LANCE, 16.0F, 52.0D, 40, 150));
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        victim.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 120, 1));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PHANTOM_HURT;
    }
}
