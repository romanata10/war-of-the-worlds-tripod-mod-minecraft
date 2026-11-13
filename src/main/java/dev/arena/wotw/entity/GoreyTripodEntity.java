package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The flat black saucer of the Edward Gorey drawing: a thin disc on six spindly legs.
 *
 * <p><b>Signature ability</b>: it is a brood mother. Instead of a big gun it keeps dropping
 * <b>Martian lice</b> out of its underside, so killing it is only half the job — the swarm it left
 * behind keeps coming. Its own beam is weak and short.</p>
 */
public class GoreyTripodEntity extends AbstractTripodEntity {

    /** Ticks between two broods. */
    private static final int BROOD_INTERVAL = 240;
    /** Lice it will not push past in its own neighbourhood. */
    private static final int MAX_BROOD = 10;

    public GoreyTripodEntity(EntityType<? extends GoreyTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        // fast and flimsy: it runs away while its children do the biting
        return tripodAttributes(150.0D, 0.36D, 6.0D, 6.0D, 4.0D, 0.7D, 40.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.GREEN_BEAM, 4.0F, 26.0D, 16, 120));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(BROOD_INTERVAL)) {
            return;
        }
        int nearby = this.level().getEntitiesOfClass(MartianLouseEntity.class,
                this.getBoundingBox().inflate(12.0D)).size();
        if (nearby >= MAX_BROOD) {
            return;
        }
        LivingEntity target = this.getTarget();
        for (int i = 0; i < 3; i++) {
            MartianLouseEntity louse = ModEntities.MARTIAN_LOUSE.create(this.level());
            if (louse == null) {
                return;
            }
            double x = this.getX() + (this.random.nextDouble() - 0.5D) * 4.0D;
            double z = this.getZ() + (this.random.nextDouble() - 0.5D) * 4.0D;
            louse.moveTo(x, this.getY(), z, this.random.nextFloat() * 360.0F, 0.0F);
            if (target != null) {
                louse.setTarget(target);
            }
            this.level().addFreshEntity(louse);
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                net.minecraft.sounds.SoundEvents.SPIDER_AMBIENT, net.minecraft.sounds.SoundSource.HOSTILE, 1.6F, 0.6F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D, this.getY() + 0.3D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D, 0.0D, 0.01D, 0.0D);
    }
}
