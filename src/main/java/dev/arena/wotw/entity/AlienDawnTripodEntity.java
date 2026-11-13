package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.MarkAndStrikeGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Alien Dawn: a huge ribbed drum with a blue light bar across the face, riding on four long legs.
 *
 * <p><b>Signature ability</b>: the spotlight. The bar marks a victim with a hard blue light; while the
 * mark holds, the machine's cutter eats straight through armour and the victim cannot hide.</p>
 */
public class AlienDawnTripodEntity extends AbstractTripodEntity {

    public AlienDawnTripodEntity(EntityType<? extends AlienDawnTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(260.0D, 0.27D, 12.0D, 9.0D, 6.0D, 0.8D, 50.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new MarkAndStrikeGoal(this, BeamKind.LASER, 8.0F, 36.0D, 16, 110));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        if (this.onIntervalZero(30)) {
            this.level().addParticle(ParticleTypes.END_ROD,
                    this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D,
                    this.getY() + this.getBbHeight() * 0.55D,
                    this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D, 0.0D, 0.0D, 0.0D);
        }
        if (!target.hasEffect(MobEffects.GLOWING) || !this.onIntervalZero(40)) {
            return;
        }
        // armour does not stop the cutter: magic damage ignores it entirely
        target.hurt(this.damageSources().magic(), 2.5F);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(),
                    target.getY() + target.getBbHeight() * 0.6D, target.getZ(), 12, 0.4D, 0.6D, 0.4D, 0.05D);
        }
    }
}
