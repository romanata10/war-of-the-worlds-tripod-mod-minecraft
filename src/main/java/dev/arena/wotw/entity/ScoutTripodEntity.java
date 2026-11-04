package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

/**
 * Scout tripod — knee high, fast, and carrying a machine gun instead of a heat ray.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Target ping</b> — the moment it sees a player it marks him with Glowing and shouts his
 *     position to every Martian machine within 48 blocks, which then all turn towards him.</li>
 * </ul>
 */
public class ScoutTripodEntity extends AbstractTripodEntity {

    private static final double PING_RANGE = 48.0D;

    public ScoutTripodEntity(EntityType<? extends ScoutTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(90.0D, 0.42D, 6.0D, 4.0D, 2.0D, 0.5D, 64.0D);
    }

    @Override
    protected void addExtraGoals() {
        // machine gun: short bursts, small damage, no charge up
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.LANCE, 3.0F, 42.0D, 6, 34, 4, 4, true));
    }

    @Override
    protected void serverTick() {
        if (!this.isAbilityReady()) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (!(target instanceof Player player) || !this.hasLineOfSight(player)) {
            return;
        }
        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0, false, false, false));

        int alerted = 0;
        for (AbstractTripodEntity other : this.level().getEntitiesOfClass(AbstractTripodEntity.class,
                this.getBoundingBox().inflate(PING_RANGE), other -> other != this && other.isAlive())) {
            if (other.getTarget() == null) {
                other.setTarget(player);
                alerted++;
            }
        }

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 1.4F, 1.9F);
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + this.getBbHeight() * 0.8D,
                    this.getZ(), 24, 0.6D, 0.6D, 0.6D, 0.15D);
        }
        this.setAbilityCooldown(alerted > 0 ? 160 : 60);
    }
}
