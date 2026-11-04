package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

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

    public BombardingMachineEntity(EntityType<? extends BombardingMachineEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
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
        double squared = this.distanceToSqr(target);
        if (squared > 52.0D * 52.0D || squared < 25.0D) {
            return;
        }
        this.lobShell(target);
        this.setAbilityCooldown(FIRE_INTERVAL);
    }

    private void lobShell(LivingEntity target) {
        Vec3 origin = new Vec3(this.getX(), this.getY() + this.getBbHeight() * 0.95D, this.getZ());
        Vec3 aim = target.position();
        double dx = aim.x - origin.x;
        double dy = aim.y - origin.y;
        double dz = aim.z - origin.z;
        double distance = Math.sqrt(dx * dx + dz * dz);
        double speed = 0.85D + Math.min(0.7D, distance * 0.015D);
        double time = Math.max(0.8D, distance / speed);
        double vy = (dy + 0.5D * 0.055D * time * time) / time;
        Vec3 velocity = new Vec3(dx / time, Mth.clamp(vy, 0.2D, 2.2D), dz / time);

        ShellEntity.launch(this.level(), this, origin, velocity, 3.0F, false);

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.8F, 0.6F);
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE, origin.x, origin.y, origin.z,
                    24, 0.5D, 0.4D, 0.5D, 0.06D);
        }
    }

    @Override
    public void playChargeSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ANVIL_LAND, SoundSource.HOSTILE, 1.6F, 0.5F);
    }
}
