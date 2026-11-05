package dev.arena.wotw.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

/**
 * Drone — a small grey eight legged scout with a single violet eye.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Search and detonate</b> — it has no weapon at all. It hunts players and villagers and
 *     when it gets close it simply explodes.</li>
 * </ul>
 */
public class DroneEntity extends AbstractTripodEntity {

    private static final double DETONATE_RANGE = 3.2D;

    public DroneEntity(EntityType<? extends DroneEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(26.0D, 0.46D, 4.0D, 0.0D, 0.0D, 0.0D, 40.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.PanicGoal(this, 1.2D));
    }

    @Override
    protected void serverTick() {
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        if (this.distanceToSqr(target) <= DETONATE_RANGE * DETONATE_RANGE) {
            this.detonate();
        }
    }

    private void detonate() {
        if (this.level() instanceof ServerLevel world) {
            world.explode(this, this.getX(), this.getY() + 0.4D, this.getZ(), 3.2F, true,
                    Level.ExplosionInteraction.MOB);
            world.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY() + 0.5D, this.getZ(),
                    2, 0.3D, 0.2D, 0.3D, 0.0D);
            world.playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 2.4F, 1.0F);
        }
        this.discard();
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        this.detonate();
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) == 0) {
            this.level().addParticle(ParticleTypes.END_ROD,
                    this.getX() + (this.random.nextDouble() - 0.5D) * 0.5D,
                    this.getY() + this.getBbHeight() * 0.7D,
                    this.getZ() + (this.random.nextDouble() - 0.5D) * 0.5D, 0.0D, 0.01D, 0.0D);
        }
    }

    /** Drones hunt villagers as eagerly as players. */
    @Override
    protected void addExtraGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Villager.class, true));
    }
}
