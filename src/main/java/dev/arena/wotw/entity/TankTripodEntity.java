package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
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
 * Tank tripod — the biggest walker the Martians have.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Triple heat ray</b> — three cannons fire in one burst.</li>
 *     <li><b>Siege mortar</b> — a volley of three heavy bombs on a high arc, each of them a crater.</li>
 * </ul>
 */
public class TankTripodEntity extends AbstractTripodEntity {

    private static final int VOLLEY_INTERVAL = 200;

    public TankTripodEntity(EntityType<? extends TankTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(420.0D, 0.18D, 24.0D, 18.0D, 12.0D, 1.0D, 64.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 10.0F, 62.0D, 26, 90, 3, 8, true));
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
        if (squared < 18.0D * 18.0D || squared > 60.0D * 60.0D) {
            return;
        }
        Vec3 origin = new Vec3(this.getX(), this.getY() + this.getBbHeight() * 0.92D, this.getZ());
        Vec3 aim = target.position();
        double dx = aim.x - origin.x;
        double dz = aim.z - origin.z;
        double distance = Math.sqrt(dx * dx + dz * dz);
        double speed = 1.05D + Math.min(0.5D, distance * 0.008D);
        double time = Math.max(0.8D, distance / speed);
        double vy = Mth.clamp((aim.y - origin.y + 0.5D * 0.055D * time * time) / time, 0.3D, 2.4D);
        Vec3 velocity = new Vec3(dx / time, vy, dz / time);

        for (int shot = -1; shot <= 1; shot++) {
            Vec3 spread = new Vec3(velocity.x + shot * 0.06D, velocity.y, velocity.z + shot * 0.06D);
            ShellEntity.launch(this.level(), this, origin, spread, 4.5F, false);
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 2.4F, 0.5F);
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE, origin.x, origin.y, origin.z,
                    40, 1.2D, 0.6D, 1.2D, 0.05D);
        }
        this.setAbilityCooldown(VOLLEY_INTERVAL);
    }
}
