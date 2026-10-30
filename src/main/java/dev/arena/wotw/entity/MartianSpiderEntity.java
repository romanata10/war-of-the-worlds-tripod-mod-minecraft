package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.PounceGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

    public MartianSpiderEntity(EntityType<? extends MartianSpiderEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(75.0D, 0.42D, 8.0D, 2.0D, 1.0D, 0.5D, 44.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new PounceGoal(this, 12.0D, 90));
    }

    @Override
    protected void serverTick() {
        LivingEntity target = this.getTarget();
        if (target == null || !this.horizontalCollision) {
            return;
        }
        if (target.getY() > this.getY() + 1.5D && this.squaredDistanceTo(target) < 25.0D) {
            Vec3d velocity = this.getVelocity();
            this.setVelocity(velocity.x * 0.9D, 0.24D, velocity.z * 0.9D);
            this.velocityModified = true;
        }
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        victim.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 0));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }
}
