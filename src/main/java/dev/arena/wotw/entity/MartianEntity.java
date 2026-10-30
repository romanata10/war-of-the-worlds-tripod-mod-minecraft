package dev.arena.wotw.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * The Martian itself — the creature from H. G. Wells' novel: a leathery, oily blob the size of a
 * bear, with a beak and a ring of feelers instead of legs.
 *
 * <p><b>Signature ability — black smoke.</b> Every few seconds it spits a cloud of the stuff
 * described in the book: victims are blinded and poisoned while they stumble out of it. In melee
 * its feelers slap hard enough to throw you back.</p>
 */
public class MartianEntity extends AbstractTripodEntity {

    public MartianEntity(EntityType<? extends MartianEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
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
        if (this.squaredDistanceTo(target) > 12.0D * 12.0D) {
            return;
        }
        this.spitBlackSmoke(target);
        this.setAbilityCooldown(this.getSmokeCooldown());
    }

    protected void spitBlackSmoke(LivingEntity target) {
        if (!(this.getWorld() instanceof ServerWorld world)) {
            return;
        }
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world,
                target.getX(), target.getY() + 0.4D, target.getZ());
        cloud.setOwner(this);
        cloud.setRadius(3.2F);
        cloud.setRadiusGrowth(0.02F);
        cloud.setDuration(160);
        cloud.addEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 150, 0));
        cloud.addEffect(new StatusEffectInstance(StatusEffects.POISON, 120, 1));
        world.spawnEntity(cloud);
        world.spawnParticles(ParticleTypes.SQUID_INK, target.getX(), target.getY() + 0.8D, target.getZ(),
                30, 1.2D, 0.8D, 1.2D, 0.05D);
        this.playSound(SoundEvents.ENTITY_LLAMA_SPIT, 1.4F, 0.6F);
    }

    /** The master is the one that commands the war machines. */
    public boolean isCommander() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PHANTOM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PHANTOM_DEATH;
    }

    @Override
    public float getSoundVolume() {
        return 1.1F;
    }

    @Override
    public int getXpToDrop() {
        return 24;
    }

}
