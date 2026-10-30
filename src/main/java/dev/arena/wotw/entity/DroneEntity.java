package dev.arena.wotw.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

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

    public DroneEntity(EntityType<? extends DroneEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(26.0D, 0.46D, 4.0D, 0.0D, 0.0D, 0.0D, 40.0D);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new net.minecraft.entity.ai.goal.EscapeDangerGoal(this, 1.2D));
    }

    @Override
    protected void serverTick() {
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        if (this.squaredDistanceTo(target) <= DETONATE_RANGE * DETONATE_RANGE) {
            this.detonate();
        }
    }

    private void detonate() {
        if (this.getWorld() instanceof ServerWorld world) {
            world.createExplosion(this, this.getX(), this.getY() + 0.4D, this.getZ(), 3.2F, true,
                    World.ExplosionSourceType.MOB);
            world.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY() + 0.5D, this.getZ(),
                    2, 0.3D, 0.2D, 0.3D, 0.0D);
            world.playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 2.4F, 1.0F);
        }
        this.discard();
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        this.detonate();
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) == 0) {
            this.getWorld().addParticle(ParticleTypes.END_ROD,
                    this.getX() + (this.random.nextDouble() - 0.5D) * 0.5D,
                    this.getY() + this.getHeight() * 0.7D,
                    this.getZ() + (this.random.nextDouble() - 0.5D) * 0.5D, 0.0D, 0.01D, 0.0D);
        }
    }

    /** Drones hunt villagers as eagerly as players. */
    @Override
    protected void addExtraGoals() {
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
    }
}
