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
 * A gunship of the human army: it hovers over the battlefield, keeps its distance from the walkers
 * and hoses them with the door guns.
 *
 * <p>Unlike the interceptor it does not fly high — it hangs about nine blocks up, low enough to use
 * the trees and the roofs, and it backs off when a tripod turns towards it.</p>
 */
public class HumanHelicopterEntity extends AbstractHumanCombatantEntity {

    /** Height above the terrain the gunship likes to hold. */
    private static final double HOVER = 9.0D;

    public HumanHelicopterEntity(EntityType<? extends HumanHelicopterEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(90.0D, 0.40D, 5.0D, 8.0D, 3.0D, 52.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 36.0D, 16, 0.0F, 5.0F));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        double ground = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (int) this.getX(), (int) this.getZ());
        double hover = ground + HOVER;
        LivingEntity target = this.getTarget();
        Vec3 wish;
        if (target != null) {
            Vec3 aim = new Vec3(target.getX(), Math.max(target.getY() + 7.0D, hover), target.getZ());
            Vec3 delta = aim.subtract(this.position());
            double distance = delta.length();
            if (distance > 20.0D) {
                wish = delta.normalize().scale(0.30D);
            } else if (distance < 11.0D) {
                wish = delta.normalize().scale(-0.24D);
            } else {
                // circle the target instead of flying straight at it
                wish = new Vec3(-delta.z, delta.y * 0.3D, delta.x).normalize().scale(0.24D);
            }
            this.getLookControl().setLookAt(target, 45.0F, 45.0F);
        } else {
            wish = new Vec3(0.0D, Math.max(-0.1D, Math.min(0.1D, (hover - this.getY()) * 0.06D)), 0.0D);
        }
        this.setDeltaMovement(this.getDeltaMovement().scale(0.6D).add(wish.scale(0.4D)));
        this.fallDistance = 0.0F;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }
}
