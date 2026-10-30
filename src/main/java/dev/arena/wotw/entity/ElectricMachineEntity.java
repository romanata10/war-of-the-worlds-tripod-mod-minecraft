package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Electric Machine — a walking capacitor that throws lightning instead of heat.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Storm charge</b> — a ball of lightning arcs at the target; where it lands it blows a
 *     small crater, sets the ground on fire and crackles with sparks.</li>
 * </ul>
 */
public class ElectricMachineEntity extends AbstractTripodEntity {

    private static final int CHARGE_INTERVAL = 80;

    public ElectricMachineEntity(EntityType<? extends ElectricMachineEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(170.0D, 0.30D, 10.0D, 8.0D, 4.0D, 0.9D, 48.0D);
    }

    @Override
    protected void serverTick() {
        if (!this.isAbilityReady()) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        double squared = this.squaredDistanceTo(target);
        if (squared > 40.0D * 40.0D || squared < 16.0D || !this.canSeeTarget(target)) {
            return;
        }
        this.throwCharge(target);
        this.setAbilityCooldown(CHARGE_INTERVAL);
    }

    private void throwCharge(LivingEntity target) {
        Vec3d origin = this.getBeamOrigin();
        Vec3d velocity = arcVelocity(origin, target.getPos().add(0.0D, 0.4D, 0.0D));
        ShellEntity.launch(this.getWorld(), this, origin, velocity, 2.2F, true);

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.HOSTILE, 1.6F, 1.5F);
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, origin.x, origin.y, origin.z,
                    40, 0.6D, 0.6D, 0.6D, 0.35D);
        }
    }

    /** Ballistic solution so the charge lands on the target instead of at its feet. */
    private Vec3d arcVelocity(Vec3d origin, Vec3d target) {
        double dx = target.x - origin.x;
        double dy = target.y - origin.y;
        double dz = target.z - origin.z;
        double distance = Math.sqrt(dx * dx + dz * dz);
        double speed = 0.9D + Math.min(0.8D, distance * 0.02D);
        double time = Math.max(0.6D, distance / speed);
        double vy = (dy + 0.5D * 0.055D * time * time) / time;
        return new Vec3d(dx / time, MathHelper.clamp(vy, -1.5D, 2.0D), dz / time);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        double y = this.getY() + this.getHeight() * 0.9D;
        this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK,
                this.getX() + (this.random.nextDouble() - 0.5D) * 0.8D, y,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 0.8D, 0.0D, 0.03D, 0.0D);
    }
}
