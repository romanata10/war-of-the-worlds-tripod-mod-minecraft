package dev.arena.wotw.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

/**
 * Taxi tripod — a low, unarmed carrier.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Martian shuttle</b> — it walks up to a group of Martians, takes up to three of them on
 *     its back and carries them across the map, dropping them off near the next human settlement it
 *     wanders past (or when a player gets close). It has no weapon and never fights.</li>
 * </ul>
 */
public class TaxiTripodEntity extends AbstractTripodEntity {

    private static final int MAX_PASSENGERS = 3;
    private static final double PICKUP_RANGE = 12.0D;

    private int pickupCooldown;

    public TaxiTripodEntity(EntityType<? extends TaxiTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(120.0D, 0.34D, 0.0D, 6.0D, 2.0D, 0.6D, 40.0D);
    }

    /** The shuttle has no weapons and never picks a fight, so it gets no target goals at all. */
    @Override
    protected void initGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    protected void serverTick() {
        if (this.pickupCooldown > 0) {
            this.pickupCooldown--;
        }
        if (this.pickupCooldown == 0 && this.getPassengers().size() < MAX_PASSENGERS) {
            this.boardPassengers();
        }
        if (this.getPassengers().isEmpty()) {
            return;
        }
        boolean nearPlayer = !this.level().getEntitiesOfClass(Player.class,
                this.getBoundingBox().inflate(6.0D), player -> player.isAlive()).isEmpty();
        if (nearPlayer || this.getLifeTicks() % 900 == 0) {
            this.dropPassengers();
        }
    }

    private void boardPassengers() {
        for (MartianEntity martian : this.level().getEntitiesOfClass(MartianEntity.class,
                this.getBoundingBox().inflate(PICKUP_RANGE),
                martian -> martian.isAlive() && !martian.isPassenger() && martian.getPassengers().isEmpty())) {
            martian.startRiding(this, true);
            if (this.getPassengers().size() >= MAX_PASSENGERS) {
                break;
            }
        }
        if (!this.getPassengers().isEmpty()) {
            this.pickupCooldown = 60;
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.IRON_DOOR_OPEN, SoundSource.HOSTILE, 1.6F, 1.3F);
        } else {
            this.pickupCooldown = 40;
        }
    }

    private void dropPassengers() {
        for (Entity passenger : this.getPassengers()) {
            passenger.stopRiding();
        }
        this.pickupCooldown = 200;
        if (this.level() instanceof net.minecraft.server.level.ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY() + 1.0D, this.getZ(),
                    20, 1.2D, 0.4D, 1.2D, 0.01D);
        }
    }

    /** The shuttle is a transport: it does not even try to fight back. */
    @Override
    public boolean isPushable() {
        return false;
    }
}
