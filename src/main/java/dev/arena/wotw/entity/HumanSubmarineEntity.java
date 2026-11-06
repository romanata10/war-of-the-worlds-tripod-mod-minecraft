package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

/**
 * A coastal submarine of the human navy: it runs under the surface, keeps its bows towards the
 * nearest Martian and launches torpedoes — a slow, heavy shell that leaves a real hole.
 *
 * <p>It breathes water, it is not pushed around by the current and it rides a few blocks above the
 * sea floor instead of floating like a boat.</p>
 */
public class HumanSubmarineEntity extends AbstractHumanCombatantEntity {

    /** Depth of the hull above the sea floor. */
    private static final double CLEARANCE = 3.0D;
    private static final float TORPEDO_POWER = 3.4F;

    public HumanSubmarineEntity(EntityType<? extends HumanSubmarineEntity> type, Level world) {
        super(type, world);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(150.0D, 0.30D, 6.0D, 12.0D, 4.0D, 44.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 40.0D, 40, TORPEDO_POWER, 0.0F));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.4D));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        int floorY = this.level().getHeight(Heightmap.Types.OCEAN_FLOOR, (int) this.getX(), (int) this.getZ());
        double wantedY = floorY + CLEARANCE;
        boolean submerged = this.isInWater();
        LivingEntity target = this.getTarget();
        Vec3 wish = new Vec3(0.0D, (wantedY - this.getY()) * 0.05D, 0.0D);
        if (target != null && submerged) {
            Vec3 delta = new Vec3(target.getX() - this.getX(), 0.0D, target.getZ() - this.getZ());
            if (delta.lengthSqr() > 16.0D) {
                wish = wish.add(delta.normalize().scale(0.16D));
            }
            this.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
        wish = new Vec3(wish.x, Math.max(-0.14D, Math.min(0.14D, wish.y)), wish.z);
        this.setDeltaMovement(this.getDeltaMovement().scale(0.72D).add(wish.scale(0.28D)));
        // a submarine that ends up on dry land slides back into the water
        if (!submerged && this.onGround() && !this.level().getBlockState(this.blockPosition().below()).is(Blocks.WATER)) {
            BlockPos pos = this.blockPosition();
            if (!this.level().getFluidState(pos).isEmpty() || this.level().getFluidState(pos.above()).isEmpty()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.02D, 0.0D));
            }
        }
    }
}
