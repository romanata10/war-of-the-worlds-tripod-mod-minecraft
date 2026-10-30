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
 * Bombarding Machine — two legs, a small hull and one absurdly large mortar on the roof.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Mortar shell</b> — lobs a shell on a high arc; the landing is a TNT sized blast. It has
 *     no heat ray at all, so closing the distance is the way to survive it.</li>
 * </ul>
 */
public class BombardingMachineEntity extends AbstractTripodEntity {

    private static final int FIRE_INTERVAL = 90;

    public BombardingMachineEntity(EntityType<? extends BombardingMachineEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(140.0D, 0.34D, 8.0D, 6.0D, 3.0D, 0.8D, 64.0D);
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
        if (squared > 52.0D * 52.0D || squared < 25.0D) {
            return;
        }
        this.lobShell(target);
        this.setAbilityCooldown(FIRE_INTERVAL);
    }

    private void lobShell(LivingEntity target) {
        Vec3d origin = new Vec3d(this.getX(), this.getY() + this.getHeight() * 0.95D, this.getZ());
        Vec3d aim = target.getPos();
        double dx = aim.x - origin.x;
        double dy = aim.y - origin.y;
        double dz = aim.z - origin.z;
        double distance = Math.sqrt(dx * dx + dz * dz);
        double speed = 0.85D + Math.min(0.7D, distance * 0.015D);
        double time = Math.max(0.8D, distance / speed);
        double vy = (dy + 0.5D * 0.055D * time * time) / time;
        Vec3d velocity = new Vec3d(dx / time, MathHelper.clamp(vy, 0.2D, 2.2D), dz / time);

        ShellEntity.launch(this.getWorld(), this, origin, velocity, 3.0F, false);

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.8F, 0.6F);
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.LARGE_SMOKE, origin.x, origin.y, origin.z,
                    24, 0.5D, 0.4D, 0.5D, 0.06D);
        }
    }

    @Override
    public void playChargeSound() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.HOSTILE, 1.6F, 0.5F);
    }
}
