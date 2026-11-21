package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.HoverGoal;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The flying ship: a long hull with swept fins and a bundle of tentacle drives.
 *
 * <p><b>Signature ability</b>: the <b>landing party</b> — when the ship goes down it does not fall
 * quietly, it opens and lets two nomads out on the spot.</p>
 */
public class LunarFlyerEntity extends AbstractLunarMachineEntity {

    private static final int LASER_INTERVAL = 110;

    public LunarFlyerEntity(EntityType<? extends LunarFlyerEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(200.0D, 0.32D, 10.0D, 6.0D, 4.0D, 0.7D, 62.0D);
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    protected void addExtraGoals() {
        super.addExtraGoals();
        this.goalSelector.addGoal(3, new HoverGoal(this, 12.0D, 16.0D, 0.9D));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(LASER_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target != null && this.hasLineOfSight(target) && this.distanceToSqr(target) < 40.0D * 40.0D) {
            this.fireLunarLaser(target, 6.0F);
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        for (int index = 0; index < 2; index++) {
            LunarNomadEntity nomad = ModEntities.LUNAR_NOMAD.create(server);
            if (nomad == null) {
                continue;
            }
            double angle = this.random.nextDouble() * 6.283185307179586D;
            nomad.moveTo(this.getX() + Math.cos(angle) * 2.0D, this.getY() + 1.0D,
                    this.getZ() + Math.sin(angle) * 2.0D, this.random.nextFloat() * 360.0F, 0.0F);
            nomad.finalizeSpawn(server, server.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            server.addFreshEntity(nomad);
        }
        server.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 1.0D, this.getZ(),
                10, 2.0D, 1.0D, 2.0D, 0.05D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.HOSTILE, 2.0F, 0.7F);
    }
}
