package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.BlackSmokeGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * The original 1898 H.G. Wells tripod: a hooded milking-stool of metal striding on three legs,
 * with no head at all — just a hood, a heat ray and the infamous black smoke.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Heat ray</b> — short ranged but devastating.</li>
 *     <li><b>Black smoke</b> — a lingering cloud of the black powder that poisons, blinds, darkens
 *     and withers everything caught inside.</li>
 * </ul>
 */
public class BookTripodEntity extends AbstractTripodEntity {

    public BookTripodEntity(EntityType<? extends BookTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(170.0D, 0.22D, 14.0D, 6.0D, 3.0D, 1.0D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 7.0F, 32.0D, 34, 110));
        this.goalSelector.add(3, new BlackSmokeGoal(this, 26.0D, 320));
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        victim.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 0));
        victim.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 80, 0));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
    }
}
