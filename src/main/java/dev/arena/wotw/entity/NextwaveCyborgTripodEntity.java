package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Comparator;
import java.util.List;

/**
 * The Asylum cyborg: a charred head with orange eyes and a molten core, on three scavenged legs.
 *
 * <p><b>Signature ability</b>: the core <b>overheats</b> and dumps its whole charge at once — one
 * volley, up to four targets, and then the machine has to cool down for ten seconds.</p>
 */
public class NextwaveCyborgTripodEntity extends AbstractTripodEntity {

    public NextwaveCyborgTripodEntity(EntityType<? extends NextwaveCyborgTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(250.0D, 0.25D, 12.0D, 12.0D, 8.0D, 0.9D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.LASER, 6.0F, 34.0D, 18, 80));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(200) || !this.isAbilityReady()) {
            return;
        }
        List<Mob> targets = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(28.0D),
                entity -> entity != this && entity.isAlive() && !(entity instanceof AbstractTripodEntity)
                        && entity.getTarget() == this);
        if (targets.isEmpty()) {
            return;
        }
        targets.sort(Comparator.comparingDouble(this::distanceToSqr));
        this.setAbilityCooldown(200);
        int shots = 0;
        for (Mob victim : targets) {
            if (shots++ >= 4) {
                break;
            }
            this.fireBeamAt(victim, BeamKind.LASER, 6.0F);
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.LAVA, this.getX(), this.getY() + this.getBbHeight() * 0.7D,
                    this.getZ(), 18, 1.0D, 1.0D, 1.0D, 0.02D);
        }
    }
}
