package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The cage walker: a wireframe sphere of Martian ribs with one red eye burning inside, striding on
 * four long angular legs.
 *
 * <p><b>Signature ability</b>: the ribs are an <b>antenna array</b>. Every energy weapon that hits
 * the cage is caught between the ribs and thrown straight back at whoever fired it. Shoot it with a
 * bow from the side, not with a beam from the front.</p>
 */
public class CageTripodEntity extends AbstractTripodEntity {

    /** Minimum ticks between two reflections, so a beam volley cannot bounce forever. */
    private static final int REFLECT_COOLDOWN = 50;
    private static final double REFLECT_RANGE = 64.0D;

    private int reflectTimer;

    public CageTripodEntity(EntityType<? extends CageTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(360.0D, 0.31D, 13.0D, 12.0D, 8.0D, 0.95D, 56.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (this.reflectTimer > 0) {
            this.reflectTimer--;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hit = super.hurt(source, amount);
        if (!hit || this.level().isClientSide || this.reflectTimer > 0) {
            return hit;
        }
        if (!source.is(DamageTypeTags.IS_PROJECTILE)) {
            return hit;
        }
        Entity attacker = source.getEntity();
        if (!(attacker instanceof LivingEntity shooter) || this.distanceToSqr(shooter) > REFLECT_RANGE * REFLECT_RANGE) {
            return hit;
        }
        this.reflectTimer = REFLECT_COOLDOWN;
        this.fireBeamAt(shooter, BeamKind.HEAT_RAY, Math.max(5.0F, amount));
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(),
                    this.getY() + this.getBbHeight() * 0.8D, this.getZ(), 30, 1.2D, 1.2D, 1.2D, 0.2D);
        }
        return hit;
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                this.getY() + this.getBbHeight() * 0.8D + this.random.nextDouble() * 1.4D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(), 0.0D, 0.0D, 0.0D);
    }
}
