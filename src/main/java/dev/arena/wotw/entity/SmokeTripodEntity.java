package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.BlackSmokeGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * The Smoke Machine — a walking boiler that never stops venting.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Fog</b> — the chimneys keep a rolling bank of smoke around the machine. Anyone standing
 *     in it is blinded and cannot see the sky, so the heat ray comes out of nowhere.</li>
 *     <li><b>Killing smoke</b> — every few seconds it dumps a wave of the 1898 black smoke: poison,
 *     wither and a bite of direct damage to everything that is not Martian.</li>
 * </ul>
 */
public class SmokeTripodEntity extends AbstractTripodEntity {

    /** Radius of the permanent fog bank. */
    private static final double FOG_RADIUS = 13.0D;

    public SmokeTripodEntity(EntityType<? extends SmokeTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        // slow, middling tough, but it cannot be seen coming
        return tripodAttributes(250.0D, 0.26D, 10.0D, 12.0D, 6.0D, 0.6D, 44.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BlackSmokeGoal(this, 28.0D, 190));
        this.goalSelector.addGoal(3, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 7.0F, 26.0D, 22, 100));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (this.onIntervalZero(20)) {
            for (Player player : this.level().getEntitiesOfClass(Player.class,
                    this.getBoundingBox().inflate(FOG_RADIUS))) {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 70, 0, false, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 45, 0, false, false, true));
            }
        }
        if (this.onIntervalZero(80)) {
            boolean hurtSomeone = false;
            for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(FOG_RADIUS))) {
                if (victim instanceof AbstractTripodEntity) {
                    continue;           // its own kind walks through the smoke unharmed
                }
                victim.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));
                victim.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0));
                victim.hurt(this.damageSources().indirectMagic(this, this), 4.0F);
                hurtSomeone = true;
            }
            if (hurtSomeone && this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0D, this.getZ(),
                        60, FOG_RADIUS * 0.7D, 1.5D, FOG_RADIUS * 0.7D, 0.02D);
            }
        }
    }

    @Override
    protected void clientTick() {
        for (int i = 0; i < 3; i++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double radius = this.random.nextDouble() * FOG_RADIUS * 0.8D;
            this.level().addParticle(this.random.nextBoolean() ? ParticleTypes.LARGE_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + Math.cos(angle) * radius, this.getY() + 0.4D + this.random.nextDouble() * 2.5D,
                    this.getZ() + Math.sin(angle) * radius, 0.0D, 0.02D, 0.0D);
        }
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX(), this.getY() + this.getBbHeight() * 0.95D, this.getZ(), 0.0D, 0.08D, 0.0D);
    }
}
