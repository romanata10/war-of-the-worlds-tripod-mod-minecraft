package dev.arena.wotw.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

/**
 * The frost walker: a ribbed barrel of vent fins and ice prongs that walks on three needle legs.
 *
 * <p><b>Signature ability</b>: the <b>cold vent</b>. Every few seconds it breathes a cone of chilled
 * gas over its prey — the victim's blood freezes solid (the vanilla freeze meter fills right up),
 * their legs stop obeying them and the ground under their boots turns to snow and ice. Fire does
 * not help: the machine simply freezes the fire out.</p>
 */
public class FrostTripodEntity extends AbstractTripodEntity {

    private static final int PULSE_INTERVAL = 160;
    private static final double CHILL_RANGE = 10.0D;

    public FrostTripodEntity(EntityType<? extends FrostTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.29D, 12.0D, 9.0D, 6.0D, 0.85D, 52.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(PULSE_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > CHILL_RANGE * CHILL_RANGE) {
            return;
        }
        target.setTicksFrozen(Math.max(target.getTicksFrozen(), 320));
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 2, false, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 140, 1, false, true, true));
        target.hurt(this.damageSources().freeze(), 4.0F);
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        // rime on the ground around the victim
        BlockPos feet = target.blockPosition();
        for (int index = 0; index < 5; index++) {
            BlockPos spot = feet.offset(this.random.nextInt(5) - 2, 0, this.random.nextInt(5) - 2);
            if (!server.getBlockState(spot).isAir()) {
                continue;
            }
            BlockPos below = spot.below();
            if (!server.getBlockState(below).canOcclude()) {
                continue;
            }
            server.setBlockAndUpdate(spot, this.random.nextInt(4) == 0
                    ? Blocks.ICE.defaultBlockState()
                    : Blocks.SNOW.defaultBlockState());
        }
        server.sendParticles(ParticleTypes.SNOWFLAKE, target.getX(), target.getY() + 1.0D, target.getZ(),
                30, 1.4D, 1.0D, 1.4D, 0.02D);
        server.sendParticles(ParticleTypes.ITEM_SNOWBALL, target.getX(), target.getY() + 0.6D, target.getZ(),
                18, 1.0D, 0.6D, 1.0D, 0.05D);
        server.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.GLASS_BREAK,
                SoundSource.HOSTILE, 1.4F, 1.6F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.SNOWFLAKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                this.getY() + this.getBbHeight() * 0.7D + this.random.nextDouble() * 1.5D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(), 0.0D, -0.03D, 0.0D);
    }

    @Override
    public boolean isSensitiveToWater() {
        return false;
    }

    /** Frost machines do not freeze themselves, and a player in water is no safer. */
    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        victim.setTicksFrozen(Math.max(victim.getTicksFrozen(), 200));
        if (victim instanceof Player) {
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1, false, true, true));
        }
    }
}
