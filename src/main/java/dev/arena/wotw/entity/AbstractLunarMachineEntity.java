package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

/**
 * The fleet of the Moon.
 *
 * <p>It is not the Martian invasion. The lunar machines arrive on the fifth night, they come down
 * from the sky in ships instead of capsules, most of them walk on more than three legs, and they
 * are at war with everybody: the player, the human army and the Martians alike. Their weapon is a
 * laser that sets fire to whatever it touches.</p>
 */
public abstract class AbstractLunarMachineEntity extends AbstractTripodEntity {

    protected AbstractLunarMachineEntity(EntityType<? extends AbstractLunarMachineEntity> type, Level world) {
        super(type, world);
    }

    /** The lunar fleet does not trail the red weed: it is not the Martian ecology. */
    @Override
    public boolean hasRootTrail() {
        return false;
    }

    @Override
    protected void addExtraGoals() {
        // the Moon is at war with the Martians as much as with the humans
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractTripodEntity.class, true,
                entity -> !(entity instanceof AbstractLunarMachineEntity)));
    }

    /** Fires the laser and leaves the ground around the victim burning. */
    protected void fireLunarLaser(LivingEntity target, float damage) {
        this.setAbilityCharging(true);
        this.fireBeamAt(target, BeamKind.LUNAR_LASER, damage);
        target.setSecondsOnFire(9);
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        BlockPos feet = target.blockPosition();
        for (int index = 0; index < 4; index++) {
            BlockPos spot = feet.offset(this.random.nextInt(5) - 2, 0, this.random.nextInt(5) - 2);
            if (!server.getBlockState(spot).isAir()) {
                continue;
            }
            if (!server.getBlockState(spot.below()).canOcclude()) {
                continue;
            }
            if (this.random.nextInt(3) != 0) {
                continue;
            }
            server.setBlockAndUpdate(spot, Blocks.FIRE.defaultBlockState());
        }
        server.sendParticles(ParticleTypes.FLAME, target.getX(), target.getY() + 0.6D, target.getZ(),
                18, 0.8D, 0.6D, 0.8D, 0.05D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLAZE_SHOOT,
                SoundSource.HOSTILE, 1.6F, 1.4F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D,
                this.getY() + this.getBbHeight() * 0.55D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D, 0.0D, 0.0D, 0.0D);
    }
}
