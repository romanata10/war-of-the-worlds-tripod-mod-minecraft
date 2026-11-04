package dev.arena.wotw.entity.projectile;

import dev.arena.wotw.registry.ModSounds;
import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * A travelling heat ray segment. It is a purely visual/tactical projectile: every tick it sweeps
 * a short capsule forward, damaging everything on its way, and it stops on the first solid block.
 */
public class BeamEntity extends Entity {

    private static final EntityDataAccessor<Integer> KIND =
            SynchedEntityData.defineId(BeamEntity.class, EntityDataSerializers.INT);

    private Vec3 direction = new Vec3(0.0D, 0.0D, 1.0D);
    private float damage = 6.0F;
    private int life;
    @Nullable
    private Entity ownerEntity;

    public BeamEntity(EntityType<? extends BeamEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
        this.noPhysics = true;
    }

    public BeamEntity(Level world, Vec3 origin, Vec3 direction, BeamKind kind, @Nullable Entity owner, float damage) {
        this(ModEntities.BEAM, world);
        this.setPos(origin.x, origin.y, origin.z);
        this.aimAt(direction);
        this.entityData.set(KIND, kind.ordinal());
        this.damage = damage;
        this.ownerEntity = owner;
    }

    private void aimAt(Vec3 look) {
        Vec3 dir = look.normalize();
        this.direction = dir;
        this.setYRot((float) Math.toDegrees(Math.atan2(-dir.x, dir.z)));
        this.setXRot((float) (-Math.toDegrees(Math.asin(Mth.clamp(dir.y, -1.0D, 1.0D)))));
    }

    public BeamKind getKind() {
        return BeamKind.byId(this.entityData.get(KIND));
    }

    public Vec3 getBeamDirection() {
        return this.direction;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(KIND, BeamKind.HEAT_RAY.ordinal());
    }

    @Override
    public void tick() {
        super.tick();
        this.life++;

        BeamKind kind = this.getKind();
        if (this.life > kind.getMaxLife()) {
            this.discard();
            return;
        }

        Vec3 from = this.position();
        Vec3 to = from.add(this.direction.scale(kind.getSpeed()));

        if (this.level().isClientSide) {
            for (int i = 0; i < 3; i++) {
                this.level().addParticle(kind.getParticle(),
                        this.getX() + (this.random.nextDouble() - 0.5D) * 0.2D,
                        this.getY() + (this.random.nextDouble() - 0.5D) * 0.2D,
                        this.getZ() + (this.random.nextDouble() - 0.5D) * 0.2D,
                        0.0D, 0.0D, 0.0D);
            }
            return;
        }

        // The server is authoritative about the position, the client just interpolates the
        // tracked position it receives every tick.
        AABB sweep = new AABB(from, to).inflate(kind.getRadius());
        for (Entity candidate : this.level().getEntities(this, sweep, this::canHit)) {
            this.hitLiving((LivingEntity) candidate, kind);
        }

        BlockPos landing = BlockPos.containing(to);
        BlockState state = this.level().getBlockState(landing);
        if (state.isSolidRender(this.level(), landing)) {
            this.impact(landing, kind);
            this.discard();
            return;
        }

        this.setPos(to.x, to.y, to.z);
    }

    private boolean canHit(Entity entity) {
        if (!(entity instanceof LivingEntity living) || !living.isAlive()) {
            return false;
        }
        if (living instanceof AbstractTripodEntity) {
            return false;
        }
        return this.ownerEntity == null || living != this.ownerEntity;
    }

    private void hitLiving(LivingEntity living, BeamKind kind) {
        living.hurt(this.level().damageSources().magic(), this.damage);
        living.setSecondsOnFire(kind.getIgniteSeconds());
        living.knockback(0.3D, -this.direction.x, -this.direction.z);
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(kind.getParticle(), living.getX(), living.getY(0.5D), living.getZ(),
                    6, 0.2D, 0.3D, 0.2D, 0.02D);
        }
    }

    private void impact(BlockPos pos, BeamKind kind) {
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(kind.getParticle(), pos.getX() + 0.5D, pos.getY() + 0.6D, pos.getZ() + 0.5D,
                    18, 0.3D, 0.3D, 0.3D, 0.08D);
        }
        this.level().playSound(null, pos, ModSounds.HEAT_RAY, SoundSource.HOSTILE, 0.9F,
                kind.getSoundPitch() * 1.1F);

        BlockPos above = pos.above();
        if (kind.getIgniteSeconds() >= 6 && this.level().getBlockState(above).isAir()
                && this.random.nextFloat() < 0.6F) {
            this.level().setBlock(above, Blocks.FIRE.defaultBlockState(), 3);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.entityData.set(KIND, nbt.getInt("BeamKind"));
        this.life = nbt.getInt("Life");
        this.damage = nbt.getFloat("Damage");
        this.direction = new Vec3(nbt.getDouble("DirX"), nbt.getDouble("DirY"), nbt.getDouble("DirZ"));
        if (this.direction.lengthSqr() < 1.0E-4D) {
            this.direction = new Vec3(0.0D, 0.0D, 1.0D);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putInt("BeamKind", this.getKind().ordinal());
        nbt.putInt("Life", this.life);
        nbt.putFloat("Damage", this.damage);
        nbt.putDouble("DirX", this.direction.x);
        nbt.putDouble("DirY", this.direction.y);
        nbt.putDouble("DirZ", this.direction.z);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 16384.0D;
    }
}
