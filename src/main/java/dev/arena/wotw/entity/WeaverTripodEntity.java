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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

/**
 * The weaver: a low chitin abdomen on six arched legs with a bank of spinnerets at the back.
 *
 * <p><b>Signature ability</b>: the <b>web</b>. It does not chase very hard — it spits a net of
 * Martian silk over its prey instead. The cobwebs slow the victim to a crawl and stick them in
 * place, so the rest of the pack can walk over and finish the job.</p>
 */
public class WeaverTripodEntity extends AbstractTripodEntity {

    private static final int WEB_INTERVAL = 150;
    private static final double WEB_RANGE = 20.0D;
    private static final int MAX_WEBS = 14;

    public WeaverTripodEntity(EntityType<? extends WeaverTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(260.0D, 0.27D, 11.0D, 7.0D, 5.0D, 0.8D, 48.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(WEB_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > WEB_RANGE * WEB_RANGE) {
            return;
        }
        BlockPos center = target.blockPosition();
        int placed = 0;
        for (int attempt = 0; attempt < 16 && placed < 4; attempt++) {
            BlockPos spot = center.offset(this.random.nextInt(7) - 3, this.random.nextInt(3) - 1,
                    this.random.nextInt(7) - 3);
            if (!server.getBlockState(spot).isAir()) {
                continue;
            }
            server.setBlockAndUpdate(spot, Blocks.COBWEB.defaultBlockState());
            placed++;
        }
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1, false, true, true));
        if (placed > 0) {
            server.sendParticles(ParticleTypes.ITEM_SLIME, target.getX(), target.getY() + 0.8D,
                    target.getZ(), 20, 1.2D, 0.8D, 1.2D, 0.02D);
            server.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.SPIDER_AMBIENT, SoundSource.HOSTILE, 2.0F, 0.7F);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.WHITE_ASH,
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D,
                this.getY() + 0.4D + this.random.nextDouble() * 1.2D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D, 0.0D, 0.01D, 0.0D);
    }
}
