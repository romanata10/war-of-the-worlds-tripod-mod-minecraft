package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * The living machine: a carapace of bone and bark with a spine of plates, barbs on the legs and a
 * pair of mandibles.
 *
 * <p><b>Signature ability</b>: the <b>brood</b>. It knits its own wounds shut while it fights and,
 * when it is badly hurt, it tears a hole in its belly and lets the lice out — the only machine whose
 * reinforcements are born in the middle of a battle.</p>
 */
public class LivingTripodEntity extends AbstractTripodEntity {

    private static final int HEAL_INTERVAL = 100;
    private static final int BROOD_INTERVAL = 240;
    private static final int BROOD_LIMIT = 6;

    public LivingTripodEntity(EntityType<? extends LivingTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(400.0D, 0.28D, 14.0D, 6.0D, 4.0D, 0.7D, 50.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (this.onIntervalZero(HEAL_INTERVAL) && this.getHealth() < this.getMaxHealth()) {
            this.heal(6.0F);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(),
                    this.getY() + this.getBbHeight() * 0.5D, this.getZ(), 10, 0.8D, 1.0D, 0.8D, 0.02D);
        }
        if (!this.onIntervalZero(BROOD_INTERVAL) || this.getHealth() > this.getMaxHealth() * 0.6F) {
            return;
        }
        List<MartianLouseEntity> brood = server.getEntitiesOfClass(MartianLouseEntity.class,
                this.getBoundingBox().inflate(18.0D));
        if (brood.size() >= BROOD_LIMIT) {
            return;
        }
        for (int index = 0; index < 2; index++) {
            MartianLouseEntity louse = ModEntities.MARTIAN_LOUSE.create(server);
            if (louse == null) {
                continue;
            }
            double angle = this.random.nextDouble() * 6.283185307179586D;
            louse.moveTo(this.getX() + Math.cos(angle) * 2.5D, this.getY(), this.getZ() + Math.sin(angle) * 2.5D,
                    this.random.nextFloat() * 360.0F, 0.0F);
            louse.finalizeSpawn(server, server.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            server.addFreshEntity(louse);
        }
        server.sendParticles(ParticleTypes.ITEM_SLIME, this.getX(), this.getY() + this.getBbHeight() * 0.4D,
                this.getZ(), 24, 1.2D, 0.5D, 1.2D, 0.05D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SPIDER_AMBIENT,
                SoundSource.HOSTILE, 1.6F, 0.6F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.ITEM_SLIME,
                this.getX() + (this.random.nextDouble() - 0.5D) * 1.6D,
                this.getY() + 0.6D + this.random.nextDouble() * 2.0D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 1.6D, 0.0D, -0.02D, 0.0D);
    }
}
