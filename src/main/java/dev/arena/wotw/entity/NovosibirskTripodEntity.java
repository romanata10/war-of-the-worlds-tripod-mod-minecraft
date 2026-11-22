package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * The Novosibirsk machine: the pale umbrella on five long tentacles that comes out of the fog.
 *
 * <p><b>Signature ability</b>: the <b>fog</b>. It walks inside its own weather — anything close to
 * it loses its sight, and the heat ray comes out of the white without a warning.</p>
 */
public class NovosibirskTripodEntity extends AbstractTripodEntity {

    private static final int FOG_INTERVAL = 60;
    private static final double FOG_RANGE = 14.0D;
    private static final int RAY_INTERVAL = 130;

    public NovosibirskTripodEntity(EntityType<? extends NovosibirskTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(380.0D, 0.26D, 12.0D, 9.0D, 6.0D, 0.9D, 50.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (this.onIntervalZero(FOG_INTERVAL)) {
            server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY() + 1.0D, this.getZ(),
                    40, FOG_RANGE * 0.5D, 1.0D, FOG_RANGE * 0.5D, 0.01D);
            for (Player player : server.getEntitiesOfClass(Player.class,
                    this.getBoundingBox().inflate(FOG_RANGE), player -> player.isAlive())) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0), this);
            }
        }
        if (!this.onIntervalZero(RAY_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        this.setAbilityCharging(true);
        this.fireBeamAt(target, BeamKind.HEAT_RAY, 11.0F);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLAZE_SHOOT,
                SoundSource.HOSTILE, 2.0F, 0.6F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 10.0D,
                this.getY() + this.random.nextDouble() * 3.0D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 10.0D, 0.0D, 0.01D, 0.0D);
    }
}
