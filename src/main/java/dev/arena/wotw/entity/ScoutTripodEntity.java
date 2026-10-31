package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

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

    public ScoutTripodEntity(EntityType<? extends ScoutTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
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
        if (!(target instanceof PlayerEntity player) || !this.canSee(player)) {
            return;
        }
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 400, 0, false, false, false));

        int alerted = 0;
        for (AbstractTripodEntity other : this.getWorld().getEntitiesByClass(AbstractTripodEntity.class,
                this.getBoundingBox().expand(PING_RANGE), other -> other != this && other.isAlive())) {
            if (other.getTarget() == null) {
                other.setTarget(player);
                alerted++;
            }
        }

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.HOSTILE, 1.4F, 1.9F);
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + this.getHeight() * 0.8D,
                    this.getZ(), 24, 0.6D, 0.6D, 0.6D, 0.15D);
        }
        this.setAbilityCooldown(alerted > 0 ? 160 : 60);
    }
}
