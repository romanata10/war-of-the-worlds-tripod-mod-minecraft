package dev.arena.wotw.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

import java.util.Comparator;

/**
 * The battle machine of the Moon: a crab of dark plate on six girders with two great arms and a
 * furnace under the hull.
 *
 * <p><b>Signature ability</b>: the <b>crossfire</b> — two lasers at once, at two different targets,
 * and both of them leave the ground burning.</p>
 */
public class LunarWarMachineEntity extends AbstractLunarMachineEntity {

    private static final int SALVO_INTERVAL = 85;

    public LunarWarMachineEntity(EntityType<? extends LunarWarMachineEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(560.0D, 0.24D, 20.0D, 12.0D, 8.0D, 1.0D, 60.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(SALVO_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !this.hasLineOfSight(target)) {
            return;
        }
        this.fireLunarLaser(target, 11.0F);
        LivingEntity second = server.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(36.0D),
                        entity -> entity != this && entity != target && entity.isAlive() && this.canAttack(entity))
                .stream()
                .min(Comparator.comparingDouble(this::distanceToSqr))
                .orElse(null);
        if (second != null) {
            this.fireLunarLaser(second, 8.0F);
            server.sendParticles(ParticleTypes.END_ROD, this.getX(),
                    this.getY() + this.getBbHeight() * 0.6D, this.getZ(), 20, 1.6D, 0.6D, 1.6D, 0.05D);
        }
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BEACON_ACTIVATE,
                SoundSource.HOSTILE, 1.6F, 0.7F);
    }

    @Override
    protected void clientTick() {
        super.clientTick();
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.FLAME,
                this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D,
                this.getY() + 1.0D + this.random.nextDouble() * 2.0D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D, 0.0D, 0.0D, 0.0D);
    }
}
