package dev.arena.wotw.entity;

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
 * The XBLA machine: a faceted hull with a lens of coloured panes, chains and pincers.
 *
 * <p><b>Signature ability</b>: the <b>prism</b>. The lens is never the same twice — it cycles
 * through three settings, and the beam changes with it: fire that sets the victim alight, a killing
 * lance, or a green beam that poisons. Nothing else in the invasion does all three.</p>
 */
public class PrismTripodEntity extends AbstractTripodEntity {

    private static final int BEAM_INTERVAL = 70;

    private int mode;

    public PrismTripodEntity(EntityType<? extends PrismTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.30D, 12.0D, 7.0D, 5.0D, 0.8D, 52.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(BEAM_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !this.hasLineOfSight(target)) {
            return;
        }
        this.setAbilityCharging(true);
        this.mode = (this.mode + 1) % 3;
        switch (this.mode) {
            case 0 -> {
                this.fireBeamAt(target, BeamKind.HEAT_RAY, 8.0F);
                target.setSecondsOnFire(6);
            }
            case 1 -> this.fireBeamAt(target, BeamKind.LANCE, 10.0F);
            default -> {
                this.fireBeamAt(target, BeamKind.GREEN_BEAM, 6.0F);
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 140, 1, false, true, true));
                target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0, false, true, true));
            }
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.END_ROD, this.getX(),
                    this.getY() + this.getBbHeight() * 0.66D, this.getZ(), 14, 0.8D, 0.5D, 0.8D, 0.05D);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        ParticleTypes[] panes = {ParticleTypes.FLAME, ParticleTypes.END_ROD, ParticleTypes.HAPPY_VILLAGER};
        this.level().addParticle(panes[this.mode % panes.length],
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D,
                this.getY() + this.getBbHeight() * 0.62D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D, 0.0D, 0.0D, 0.0D);
    }
}
