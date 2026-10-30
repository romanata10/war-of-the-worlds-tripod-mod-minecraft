package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * The tripod of "War of the Worlds: Goliath" (2012) — the dieselpunk, armour plated colossus.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Heat ray barrage</b> — three heavy rays in quick succession.</li>
 *     <li><b>Shield dome</b> — raises a dome that deflects projectiles and slowly repairs the hull
 *     while it lasts.</li>
 * </ul>
 */
public class GoliathTripodEntity extends AbstractTripodEntity {

    public GoliathTripodEntity(EntityType<? extends GoliathTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(320.0D, 0.22D, 24.0D, 20.0D, 12.0D, 1.0D, 56.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 9.5F, 44.0D, 26, 80, 3, 8, true));
    }

    @Override
    protected void serverTick() {
        if (this.getTarget() != null && !this.isShielded() && this.onIntervalZero(300)) {
            this.startShield(120);
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.HOSTILE, 3.0F, 0.5F);
        }
        if (this.isShielded() && this.onIntervalZero(20) && this.getHealth() < this.getMaxHealth()) {
            this.heal(3.0F);
        }
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        Vec3d push = victim.getPos().subtract(this.getPos());
        double length = Math.max(0.5D, push.length());
        victim.setVelocity(push.x / length * 1.1D, 0.6D, push.z / length * 1.1D);
        victim.velocityModified = true;
    }

    @Override
    protected void clientTick() {
        if (!this.isShielded()) {
            return;
        }
        if (this.world instanceof ServerWorld) {
            return;
        }
        double radius = this.getWidth() * 0.9D;
        for (int i = 0; i < 2; i++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double y = this.random.nextDouble() * this.getHeight();
            this.world.addParticle(ParticleTypes.ELECTRIC_SPARK,
                    this.getX() + Math.cos(angle) * radius, this.getY() + y, this.getZ() + Math.sin(angle) * radius,
                    0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RAVAGER_ROAR;
    }
}
