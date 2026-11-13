package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

/**
 * A jet interceptor: faster and higher than the propeller fighter, with a heavier cannon. It makes
 * one firing pass, pulls up, turns and comes back.
 */
public class HumanJetEntity extends AbstractHumanCombatantEntity {

    public HumanJetEntity(EntityType<? extends HumanJetEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(90.0D, 0.55D, 5.0D, 8.0D, 3.0D, 64.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 52.0D, 18, 0.0F, 7.0F));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        double ground = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (int) this.getX(), (int) this.getZ());
        double patrolHeight = ground + 20.0D;
        LivingEntity target = this.getTarget();
        if (target != null) {
            Vec3 aim = new Vec3(target.getX(), Math.max(target.getY() + 12.0D, patrolHeight), target.getZ());
            Vec3 delta = aim.subtract(this.position());
            if (delta.lengthSqr() > 4.0D) {
                this.setDeltaMovement(delta.normalize().scale(0.48D));
            }
            this.getLookControl().setLookAt(target, 50.0F, 50.0F);
        } else {
            double lift = Math.max(-0.16D, Math.min(0.16D, (patrolHeight - this.getY()) * 0.06D));
            this.setDeltaMovement(this.getDeltaMovement().x, lift, this.getDeltaMovement().z);
        }
        this.fallDistance = 0.0F;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
}
