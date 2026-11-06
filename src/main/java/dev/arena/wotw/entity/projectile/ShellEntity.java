package dev.arena.wotw.entity.projectile;

import dev.arena.wotw.registry.ModEntities;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.AABB;
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
 * leaves a plain crater, an electric charge sets the ground alight and cracks with lightning, and a
 * thrown nuclear bomb leaves a crater, a fire storm and a cloud of fallout.</p>
 */
public class ShellEntity extends Entity {

    private static final int MAX_LIFE = 220;

    /** Blast radius of a nuclear bomb, roughly twenty blocks of crater. */
    private static final float NUCLEAR_POWER = 12.0F;
    /** Everything living inside this radius of the detonation catches the fallout. */
    private static final double FALLOUT_RADIUS = 28.0D;

    private float power = 2.6F;
    private boolean electric;
    /** A rifle bullet: no blast, it just hurts what it hits. */
    private boolean bullet;
    /** A nuclear bomb: the blast plus a fallout cloud that withers everything in it. */
    private boolean nuclear;

    /** A mortar bomb of black smoke: a small blast, then a cloud of the 1898 stuff. */
    private boolean gas;
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

    /** A thrown nuclear bomb: the biggest bang in the mod, plus fallout. */
    @Nullable
    public static ShellEntity launchNuke(Level world, @Nullable LivingEntity owner, Vec3 origin, Vec3 velocity) {
        ShellEntity bomb = launch(world, owner, origin, velocity, NUCLEAR_POWER, false);
        if (bomb != null) {
            bomb.nuclear = true;
        }
        return bomb;
    }

    /** A mortar bomb of the Martian smoke batteries: a small blast and a lingering black cloud. */
    @Nullable
    public static ShellEntity launchSmokeBomb(Level world, @Nullable LivingEntity owner, Vec3 origin,
                                              Vec3 velocity) {
        ShellEntity bomb = launch(world, owner, origin, velocity, 2.6F, false);
        if (bomb != null) {
            bomb.gas = true;
        }
        return bomb;
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
        if (this.nuclear) {
            this.detonateNuclear();
            return;
        }
        if (this.gas) {
            this.detonateGas();
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

    /** The smoke bomb: a burst that leaves the black smoke hanging over the impact. */
    private void detonateGas() {
        if (this.level() instanceof ServerLevel world) {
            world.explode(this, this.getX(), this.getY() + 0.2D, this.getZ(), this.power, false,
                    Level.ExplosionInteraction.MOB);
            AreaEffectCloud cloud = new AreaEffectCloud(world, this.getX(), this.getY() + 0.3D, this.getZ());
            cloud.setOwner(this.owner);
            cloud.setParticle(ParticleTypes.LARGE_SMOKE);
            cloud.setRadius(5.5F);
            cloud.setRadiusPerTick(0.01F);
            cloud.setRadiusOnUse(-0.05F);
            cloud.setDuration(300);
            cloud.setWaitTime(0);
            cloud.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1));
            cloud.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 240, 0));
            cloud.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 160, 0));
            cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
            world.addFreshEntity(cloud);
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.HOSTILE, 2.0F, 0.4F);
        }
        this.discard();
    }

    /** The nuclear bomb: a very big crater, a fire storm and a fallout cloud that eats what it touches. */
    private void detonateNuclear() {
        if (!(this.level() instanceof ServerLevel world)) {
            this.discard();
            return;
        }
        double x = this.getX();
        double y = this.getY() + 0.3D;
        double z = this.getZ();
        world.explode(this, x, y, z, NUCLEAR_POWER, true, Level.ExplosionInteraction.TNT);
        world.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y + 1.0D, z, 6, 3.5D, 1.0D, 3.5D, 0.0D);
        world.sendParticles(ParticleTypes.LARGE_SMOKE, x, y + 4.0D, z, 240, 5.0D, 7.0D, 5.0D, 0.08D);
        world.sendParticles(ParticleTypes.FLAME, x, y + 1.5D, z, 180, 4.5D, 2.0D, 4.5D, 0.14D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y + 6.0D, z, 60, 3.0D, 4.0D, 3.0D, 0.02D);
        world.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 8.0F, 0.45F);
        world.playSound(null, x, y, z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 6.0F, 0.6F);

        AABB fallout = new AABB(x - FALLOUT_RADIUS, y - 12.0D, z - FALLOUT_RADIUS,
                x + FALLOUT_RADIUS, y + 18.0D, z + FALLOUT_RADIUS);
        for (LivingEntity victim : world.getEntitiesOfClass(LivingEntity.class, fallout,
                entity -> entity.isAlive() && entity != this.owner)) {
            double distance = Math.sqrt(victim.distanceToSqr(x, y, z));
            float damage = (float) Math.max(4.0D, 44.0D - distance * 1.4D);
            victim.hurt(world.damageSources().explosion(this, this.owner), damage);
            victim.addEffect(new MobEffectInstance(MobEffects.WITHER, 300, 1));
            victim.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
            victim.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 2));
            victim.setSecondsOnFire(12);
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
        nbt.putBoolean("Nuclear", this.nuclear);
        nbt.putBoolean("Gas", this.gas);
        nbt.putInt("Life", this.life);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.power = nbt.getFloat("Power");
        this.electric = nbt.getBoolean("Electric");
        this.bullet = nbt.getBoolean("Bullet");
        this.nuclear = nbt.getBoolean("Nuclear");
        this.gas = nbt.getBoolean("Gas");
        this.life = nbt.getInt("Life");
    }
}
