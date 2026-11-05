package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.PounceGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * "Martian Spider" from <i>The Great Martian War 1913-1917</i>.
 *
 * <p>A low, fast, eight legged skirmisher that scuttles up walls and pounces on its prey.</p>
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Wall climb</b> — it crawls straight up blocks to reach a target above it.</li>
 *     <li><b>Pounce</b> — a charged leap that closes the distance instantly.</li>
 * </ul>
 */
public class MartianSpiderEntity extends AbstractTripodEntity {

    public MartianSpiderEntity(EntityType<? extends MartianSpiderEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(75.0D, 0.42D, 8.0D, 2.0D, 1.0D, 0.5D, 44.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new PounceGoal(this, 12.0D, 90));
    }

    @Override
    protected void serverTick() {
        LivingEntity target = this.getTarget();
        if (target == null || !this.horizontalCollision) {
            return;
        }
        if (target.getY() > this.getY() + 1.5D && this.distanceToSqr(target) < 25.0D) {
            Vec3 velocity = this.getDeltaMovement();
            this.setDeltaMovement(velocity.x * 0.9D, 0.24D, velocity.z * 0.9D);
            this.hurtMarked = true;
        }
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        victim.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }
}
