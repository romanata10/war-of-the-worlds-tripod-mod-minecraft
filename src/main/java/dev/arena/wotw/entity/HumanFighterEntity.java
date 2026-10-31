package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

/**
 * An interceptor: it never lands, it circles the Martian machines and it stitches them with
 * cannon fire from above.
 */
public class HumanFighterEntity extends AbstractHumanCombatantEntity {

    public HumanFighterEntity(EntityType<? extends HumanFighterEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return humanAttributes(70.0D, 0.42D, 4.0D, 6.0D, 2.0D, 56.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.add(3, new GunAttackGoal(this, 44.0D, 24, 0.0F, 6.0F));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            return;
        }
        double ground = this.getWorld().getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                (int) this.getX(), (int) this.getZ());
        double patrolHeight = ground + 14.0D;
        LivingEntity target = this.getTarget();
        if (target != null) {
            Vec3d aim = new Vec3d(target.getX(), Math.max(target.getY() + 9.0D, patrolHeight), target.getZ());
            Vec3d delta = aim.subtract(this.getPos());
            if (delta.lengthSquared() > 4.0D) {
                Vec3d velocity = delta.normalize().multiply(0.34D);
                this.setVelocity(velocity);
            }
            this.getLookControl().lookAt(target, 40.0F, 40.0F);
        } else {
            double lift = Math.max(-0.12D, Math.min(0.12D, (patrolHeight - this.getY()) * 0.05D));
            this.setVelocity(this.getVelocity().x, lift, this.getVelocity().z);
        }
        this.fallDistance = 0.0F;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier,
                                    net.minecraft.entity.damage.DamageSource damageSource) {
        return false;
    }
}
