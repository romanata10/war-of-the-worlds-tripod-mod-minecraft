package dev.arena.wotw.entity;

import dev.arena.wotw.block.BloodRootsBlock;
import dev.arena.wotw.block.RedRootBlock;
import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The "vegetable" machine: a pale, segmented cone with a pointed cap and buds on three thin legs.
 *
 * <p><b>Signature ability</b>: it <b>feeds</b>. Wherever it walks it leaves a trail of red roots,
 * and while it stands on its own roots it knits itself back together — one point of health every
 * couple of seconds. Burn the ground under it, or it never dies.</p>
 */
public class VegetableTripodEntity extends AbstractTripodEntity {

    public VegetableTripodEntity(EntityType<? extends VegetableTripodEntity> type, Level world) {
        super(type, world);
        this.setRootTrail(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        // slow, but very hard to finish off while it stands on its roots
        return tripodAttributes(220.0D, 0.22D, 10.0D, 10.0D, 6.0D, 0.8D, 44.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.GREEN_BEAM, 6.0F, 32.0D, 22, 110));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(40) || this.getHealth() >= this.getMaxHealth()) {
            return;
        }
        BlockState below = this.level().getBlockState(this.blockPosition().below());
        boolean rooted = below.getBlock() instanceof RedRootBlock || below.getBlock() instanceof BloodRootsBlock;
        if (!rooted) {
            return;
        }
        this.heal(2.0F);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 0.6D, this.getZ(),
                    12, 1.2D, 0.4D, 1.2D, 0.02D);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.SPORE_BLOSSOM_AIR,
                this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D,
                this.getY() + this.getBbHeight() * 0.8D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D, 0.0D, 0.01D, 0.0D);
    }
}
