package dev.arena.wotw.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

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

    public TaxiTripodEntity(EntityType<? extends TaxiTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(120.0D, 0.34D, 0.0D, 6.0D, 2.0D, 0.6D, 40.0D);
    }

    /** The shuttle has no weapons and never picks a fight, so it gets no target goals at all. */
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7D));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    @Override
    protected void serverTick() {
        if (this.pickupCooldown > 0) {
            this.pickupCooldown--;
        }
        if (this.pickupCooldown == 0 && this.getPassengerList().size() < MAX_PASSENGERS) {
            this.boardPassengers();
        }
        if (this.getPassengerList().isEmpty()) {
            return;
        }
        boolean nearPlayer = !this.getWorld().getEntitiesByClass(PlayerEntity.class,
                this.getBoundingBox().expand(6.0D), player -> player.isAlive()).isEmpty();
        if (nearPlayer || this.getLifeTicks() % 900 == 0) {
            this.dropPassengers();
        }
    }

    private void boardPassengers() {
        for (MartianEntity martian : this.getWorld().getEntitiesByClass(MartianEntity.class,
                this.getBoundingBox().expand(PICKUP_RANGE),
                martian -> martian.isAlive() && !martian.hasVehicle() && martian.getPassengerList().isEmpty())) {
            martian.startRiding(this, true);
            if (this.getPassengerList().size() >= MAX_PASSENGERS) {
                break;
            }
        }
        if (!this.getPassengerList().isEmpty()) {
            this.pickupCooldown = 60;
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.HOSTILE, 1.6F, 1.3F);
        } else {
            this.pickupCooldown = 40;
        }
    }

    private void dropPassengers() {
        for (Entity passenger : this.getPassengerList()) {
            passenger.stopRiding();
        }
        this.pickupCooldown = 200;
        if (this.getWorld() instanceof net.minecraft.server.world.ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY() + 1.0D, this.getZ(),
                    20, 1.2D, 0.4D, 1.2D, 0.01D);
        }
    }

    /** The shuttle is a transport: it does not even try to fight back. */
    @Override
    public boolean isPushable() {
        return false;
    }
}
