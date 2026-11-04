package dev.arena.wotw.entity.projectile;

import dev.arena.wotw.registry.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * A shell from a Bombarding Machine, or a ball of lightning from an Electric Machine.
 *
 * <p>Both fly on a ballistic arc, both blow up on impact — the difference is the charge: a shell
 * leaves a plain crater, an electric charge sets the ground alight and cracks with lightning.</p>
 */
public class ShellEntity extends Entity {

    private static final int MAX_LIFE = 220;

    private float power = 2.6F;
    private boolean electric;
    /** A rifle bullet: no blast, it just hurts what it hits. */
    private boolean bullet;
    private int life;
    @Nullable
    private LivingEntity owner;

    public ShellEntity(EntityType<? extends ShellEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    /** Spawns a shell travelling with the given velocity. Returns null if the type refused. */
    @Nullable
    public static ShellEntity launch(Level world, @Nullable LivingEntity owner, Vec3 origin, Vec3 velocity,
                                     float power, boolean electric) {
        ShellEntity shell = ModEntities.SHELL.create(world);
        if (shell == null) {
            return null;
        }
        shell.moveTo(origin.x, origin.y, origin.z, 0.0F, 0.0F);
        shell.setDeltaMovement(velocity);
        shell.power = power;
        shell.electric = electric;
        shell.owner = owner;
        world.addFreshEntity(shell);
        return shell;
    }

    /** A flat, fast rifle bullet fired by the human soldiers and fighters. */
    @Nullable
    public static ShellEntity launchBullet(Level world, @Nullable LivingEntity owner, Vec3 origin,
                                           Vec3 velocity, float damage) {
        ShellEntity shell = launch(world, owner, origin, velocity, damage, false);
        if (shell != null) {
            shell.bullet = true;
        }
        return shell;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(this.electric ? ParticleTypes.ELECTRIC_SPARK : ParticleTypes.SMOKE,
                    this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            return;
        }

        this.life++;
        Vec3 velocity = this.getDeltaMovement();
        this.setDeltaMovement(velocity.x, velocity.y - 0.055D, velocity.z);
        this.move(MoverType.SELF, this.getDeltaMovement());

        if (this.life > 3) {
            for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(0.6D),
                    entity -> entity.isAlive() && entity != this.owner)) {
                this.detonate();
                return;
            }
        }
        if (this.onGround() || this.life > MAX_LIFE) {
            this.detonate();
        }
    }

    private void detonate() {
        if (this.bullet) {
            this.hitTarget();
            return;
        }
        if (this.level() instanceof ServerLevel world) {
            world.explode(this, this.getX(), this.getY() + 0.2D, this.getZ(), this.power,
                    this.electric, Level.ExplosionInteraction.MOB);
            if (this.electric) {
                world.sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY() + 0.5D, this.getZ(),
                        90, 1.6D, 0.8D, 1.6D, 0.25D);
                world.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() + 0.3D, this.getZ(),
                        40, 1.8D, 0.4D, 1.8D, 0.02D);
                world.playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.HOSTILE, 2.2F, 1.2F);
            } else {
                world.playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 2.0F, 0.8F);
            }
        }
        this.discard();
    }

    /** A bullet does not blow up: it damages the first living thing it runs into. */
    private void hitTarget() {
        if (this.level() instanceof ServerLevel world) {
            for (LivingEntity victim : world.getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(1.0D),
                    entity -> entity.isAlive() && entity != this.owner)) {
                victim.hurt(world.damageSources().mobProjectile(this, this.owner), this.power);
            }
            world.sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(),
                    10, 0.3D, 0.3D, 0.3D, 0.1D);
            world.playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ARROW_HIT, SoundSource.NEUTRAL, 0.9F, 1.6F);
        }
        this.discard();
    }

    /** True for the lightning charges of the Electric Machine and the arc rifle. */
    public boolean isElectricCharge() {
        return this.electric;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putFloat("Power", this.power);
        nbt.putBoolean("Electric", this.electric);
        nbt.putBoolean("Bullet", this.bullet);
        nbt.putInt("Life", this.life);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.power = nbt.getFloat("Power");
        this.electric = nbt.getBoolean("Electric");
        this.bullet = nbt.getBoolean("Bullet");
        this.life = nbt.getInt("Life");
    }
}
