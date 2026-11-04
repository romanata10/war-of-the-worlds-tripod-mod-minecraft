package dev.arena.wotw.entity;

import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

/**
 * The Martian itself — the creature from H. G. Wells' novel: a leathery, oily blob the size of a
 * bear, with a beak and a ring of feelers instead of legs.
 *
 * <p><b>Signature ability — black smoke.</b> Every few seconds it spits a cloud of the stuff
 * described in the book: victims are blinded and poisoned while they stumble out of it. In melee
 * its feelers slap hard enough to throw you back.</p>
 */
public class MartianEntity extends AbstractTripodEntity {

    public MartianEntity(EntityType<? extends MartianEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(30.0D, 0.34D, 6.0D, 0.0D, 0.0D, 0.0D, 26.0D);
    }

    @Override
    protected void addExtraGoals() {
        // Martians carry no heat ray: they spit and they claw.
    }

    protected int getSmokeCooldown() {
        return 160;
    }

    @Override
    protected void serverTick() {
        if (!this.isAbilityReady()) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !this.canSeeTarget(target)) {
            return;
        }
        if (this.distanceToSqr(target) > 12.0D * 12.0D) {
            return;
        }
        this.spitBlackSmoke(target);
        this.setAbilityCooldown(this.getSmokeCooldown());
    }

    protected void spitBlackSmoke(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel world)) {
            return;
        }
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world,
                target.getX(), target.getY() + 0.4D, target.getZ());
        cloud.setOwner(this);
        cloud.setRadius(3.2F);
        cloud.setRadiusPerTick(0.02F);
        cloud.setDuration(160);
        cloud.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 150, 0));
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 1));
        world.addFreshEntity(cloud);
        world.sendParticles(ParticleTypes.SQUID_INK, target.getX(), target.getY() + 0.8D, target.getZ(),
                30, 1.2D, 0.8D, 1.2D, 0.05D);
        this.playSound(SoundEvents.LLAMA_SPIT, 1.4F, 0.6F);
    }

    /** The master is the one that commands the war machines. */
    public boolean isCommander() {
        return false;
    }

    /* The creatures are flesh and blood, unlike their machines. */

    @Override
    public boolean fireImmune() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return super.calculateFallDamage(fallDistance, damageMultiplier);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PHANTOM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PHANTOM_DEATH;
    }

    @Override
    public float getSoundVolume() {
        return 1.1F;
    }

    @Override
    public int getExperienceReward() {
        return 24;
    }

}
