package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;

/**
 * An interceptor: it never lands, it circles the Martian machines and it stitches them with
 * cannon fire from above.
 */
public class HumanFighterEntity extends AbstractHumanCombatantEntity {

    public HumanFighterEntity(EntityType<? extends HumanFighterEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(70.0D, 0.42D, 4.0D, 6.0D, 2.0D, 56.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.add(3, new GunAttackGoal(this, 44.0D, 24, 0.0F, 6.0F));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        double ground = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (int) this.getX(), (int) this.getZ());
        double patrolHeight = ground + 14.0D;
        LivingEntity target = this.getTarget();
        if (target != null) {
            Vec3 aim = new Vec3(target.getX(), Math.max(target.getY() + 9.0D, patrolHeight), target.getZ());
            Vec3 delta = aim.subtract(this.position());
            if (delta.lengthSquared() > 4.0D) {
                Vec3 velocity = delta.normalize().scale(0.34D);
                this.setDeltaMovement(velocity);
            }
            this.getLookControl().lookAt(target, 40.0F, 40.0F);
        } else {
            double lift = Math.max(-0.12D, Math.min(0.12D, (patrolHeight - this.getY()) * 0.05D));
            this.setDeltaMovement(this.getDeltaMovement().x, lift, this.getDeltaMovement().z);
        }
        this.fallDistance = 0.0F;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier,
                                    net.minecraft.world.damagesource.DamageSource damageSource) {
        return false;
    }
}
