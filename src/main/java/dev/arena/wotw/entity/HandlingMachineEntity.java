package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModStatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Handling Machine — the machine that takes prisoners.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Cage</b> — it has no heat ray. Two arms snatch a player within a few blocks and stuff
 *     him into the cage welded under the hull, where he loses five hit points every two seconds.</li>
 *     <li><b>Invisible prey</b> — while a player is caged every machine in the mod ignores him: he
 *     is already caught, after all. The cage is the only thing killing him.</li>
 *     <li><b>Roots</b> — a prisoner who dies inside the cage leaves a whole thicket of red roots
 *     where he died.</li>
 * </ul>
 */
public class HandlingMachineEntity extends AbstractTripodEntity {

    private static final double GRAB_RANGE = 5.0D;
    private static final int DAMAGE_INTERVAL = 40;
    private static final float CAGE_DAMAGE = 5.0F;
    private static final int CAGE_TICKS = 400;

    @Nullable
    private UUID prisoner;
    private int drainTicks;
    private int cageTicks;

    public HandlingMachineEntity(EntityType<? extends HandlingMachineEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(240.0D, 0.26D, 10.0D, 14.0D, 6.0D, 1.0D, 44.0D);
    }

    public boolean hasPrisoner() {
        return this.prisoner != null;
    }

    /** Where the cage sits: right under the hull, in front of the machine. */
    public Vec3d getCagePos() {
        Vec3d forward = this.getRotationVec(1.0F).multiply(1.4D);
        return new Vec3d(this.getX() + forward.x, this.getY() + 0.3D, this.getZ() + forward.z);
    }

    @Override
    protected void serverTick() {
        PlayerEntity prisoner = this.getPrisoner();
        if (prisoner != null) {
            this.holdPrisoner(prisoner);
            return;
        }
        LivingEntity target = this.getTarget();
        if (!(target instanceof PlayerEntity player) || !player.isAlive()) {
            return;
        }
        if (player.hasStatusEffect(ModStatusEffects.CAGED)) {
            return;
        }
        if (this.squaredDistanceTo(player) <= GRAB_RANGE * GRAB_RANGE) {
            this.capture(player);
        }
    }

    private void capture(PlayerEntity player) {
        this.prisoner = player.getUuid();
        this.drainTicks = 0;
        this.cageTicks = 0;
        player.addStatusEffect(new StatusEffectInstance(ModStatusEffects.CAGED, CAGE_TICKS, 0, false, false, false));
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.HOSTILE, 2.2F, 0.7F);
    }

    private void holdPrisoner(PlayerEntity player) {
        if (!player.isAlive() || player.isRemoved() || this.isDead()) {
            this.release(player);
            return;
        }
        if (!player.hasStatusEffect(ModStatusEffects.CAGED)) {
            // the effect ran out: the prisoner broke free
            this.prisoner = null;
            return;
        }

        Vec3d cage = this.getCagePos();
        player.requestTeleport(cage.x, cage.y, cage.z);
        player.setVelocity(Vec3d.ZERO);
        player.fallDistance = 0.0F;

        // keep the tag topped up so the other machines keep ignoring him, but only for the
        // length of the cage — the prisoner eventually pries the bars open and runs
        this.cageTicks++;
        if (this.cageTicks <= CAGE_TICKS && player.age % 20 == 0) {
            player.addStatusEffect(new StatusEffectInstance(ModStatusEffects.CAGED, 60, 0, false, false, false));
        }

        this.drainTicks++;
        if (this.drainTicks % DAMAGE_INTERVAL == 0) {
            player.damage(this.getDamageSources().mobAttack(this), CAGE_DAMAGE);
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.CRIMSON_SPORE, cage.x, cage.y + 1.0D, cage.z,
                        8, 0.3D, 0.3D, 0.3D, 0.0D);
            }
        }
    }

    private void release(@Nullable PlayerEntity player) {
        if (player != null) {
            player.removeStatusEffect(ModStatusEffects.CAGED);
        }
        this.prisoner = null;
    }

    @Nullable
    private PlayerEntity getPrisoner() {
        if (this.prisoner == null || !(this.getWorld() instanceof ServerWorld world)) {
            return null;
        }
        PlayerEntity player = world.getPlayerByUuid(this.prisoner);
        if (player == null) {
            this.prisoner = null;
        }
        return player;
    }

    @Override
    public void onDeath(DamageSource source) {
        this.release(this.getPrisoner());
        super.onDeath(source);
    }

    @Override
    protected void clientTick() {
        if (this.prisoner != null && this.random.nextInt(4) == 0) {
            Vec3d cage = this.getCagePos();
            this.getWorld().addParticle(ParticleTypes.SMOKE, cage.x, cage.y + 0.8D, cage.z, 0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    public void playChargeSound() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.HOSTILE, 2.0F, 0.6F);
    }
}
