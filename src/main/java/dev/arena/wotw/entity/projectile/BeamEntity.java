package dev.arena.wotw.entity.projectile;

import dev.arena.wotw.registry.ModSounds;
import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * A travelling heat ray segment. It is a purely visual/tactical projectile: every tick it sweeps
 * a short capsule forward, damaging everything on its way, and it stops on the first solid block.
 */
public class BeamEntity extends Entity {

    private static final TrackedData<Integer> KIND =
            DataTracker.registerData(BeamEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private Vec3d direction = new Vec3d(0.0D, 0.0D, 1.0D);
    private float damage = 6.0F;
    private int life;
    @Nullable
    private Entity ownerEntity;

    public BeamEntity(EntityType<? extends BeamEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.noClip = true;
    }

    public BeamEntity(World world, Vec3d origin, Vec3d direction, BeamKind kind, @Nullable Entity owner, float damage) {
        this(ModEntities.BEAM, world);
        this.setPosition(origin.x, origin.y, origin.z);
        this.aimAt(direction);
        this.dataTracker.set(KIND, kind.ordinal());
        this.damage = damage;
        this.ownerEntity = owner;
    }

    private void aimAt(Vec3d look) {
        Vec3d dir = look.normalize();
        this.direction = dir;
        this.setYaw((float) Math.toDegrees(Math.atan2(-dir.x, dir.z)));
        this.setPitch((float) (-Math.toDegrees(Math.asin(MathHelper.clamp(dir.y, -1.0D, 1.0D)))));
    }

    public BeamKind getKind() {
        return BeamKind.byId(this.dataTracker.get(KIND));
    }

    public Vec3d getDirection() {
        return this.direction;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(KIND, BeamKind.HEAT_RAY.ordinal());
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

        Vec3d from = this.getPos();
        Vec3d to = from.add(this.direction.multiply(kind.getSpeed()));

        if (this.getWorld().isClient) {
            for (int i = 0; i < 3; i++) {
                this.getWorld().addParticle(kind.getParticle(),
                        this.getX() + (this.random.nextDouble() - 0.5D) * 0.2D,
                        this.getY() + (this.random.nextDouble() - 0.5D) * 0.2D,
                        this.getZ() + (this.random.nextDouble() - 0.5D) * 0.2D,
                        0.0D, 0.0D, 0.0D);
            }
            return;
        }

        // The server is authoritative about the position, the client just interpolates the
        // tracked position it receives every tick.
        Box sweep = new Box(from, to).expand(kind.getRadius());
        for (Entity candidate : this.getWorld().getOtherEntities(this, sweep, this::canHit)) {
            this.hitLiving((LivingEntity) candidate, kind);
        }

        BlockPos landing = BlockPos.ofFloored(to);
        BlockState state = this.getWorld().getBlockState(landing);
        if (state.isSolidBlock(this.getWorld(), landing)) {
            this.impact(landing, kind);
            this.discard();
            return;
        }

        this.setPosition(to.x, to.y, to.z);
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
        living.damage(this.getWorld().getDamageSources().magic(), this.damage);
        living.setOnFireFor(kind.getIgniteSeconds());
        living.takeKnockback(0.3D, -this.direction.x, -this.direction.z);
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(kind.getParticle(), living.getX(), living.getBodyY(0.5D), living.getZ(),
                    6, 0.2D, 0.3D, 0.2D, 0.02D);
        }
    }

    private void impact(BlockPos pos, BeamKind kind) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(kind.getParticle(), pos.getX() + 0.5D, pos.getY() + 0.6D, pos.getZ() + 0.5D,
                    18, 0.3D, 0.3D, 0.3D, 0.08D);
        }
        this.getWorld().playSound(null, pos, ModSounds.HEAT_RAY, SoundCategory.HOSTILE, 0.9F,
                kind.getSoundPitch() * 1.1F);

        BlockPos above = pos.up();
        if (kind.getIgniteSeconds() >= 6 && this.getWorld().getBlockState(above).isAir()
                && this.random.nextFloat() < 0.6F) {
            this.getWorld().setBlockState(above, Blocks.FIRE.getDefaultState());
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.dataTracker.set(KIND, nbt.getInt("BeamKind"));
        this.life = nbt.getInt("Life");
        this.damage = nbt.getFloat("Damage");
        this.direction = new Vec3d(nbt.getDouble("DirX"), nbt.getDouble("DirY"), nbt.getDouble("DirZ"));
        if (this.direction.lengthSquared() < 1.0E-4D) {
            this.direction = new Vec3d(0.0D, 0.0D, 1.0D);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("BeamKind", this.getKind().ordinal());
        nbt.putInt("Life", this.life);
        nbt.putFloat("Damage", this.damage);
        nbt.putDouble("DirX", this.direction.x);
        nbt.putDouble("DirY", this.direction.y);
        nbt.putDouble("DirZ", this.direction.z);
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 16384.0D;
    }
}
