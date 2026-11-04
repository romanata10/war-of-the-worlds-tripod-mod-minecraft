package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModStatusEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
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

    public HandlingMachineEntity(EntityType<? extends HandlingMachineEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(240.0D, 0.26D, 10.0D, 14.0D, 6.0D, 1.0D, 44.0D);
    }

    public boolean hasPrisoner() {
        return this.prisoner != null;
    }

    /** Where the cage sits: right under the hull, in front of the machine. */
    public Vec3 getCagePos() {
        Vec3 forward = this.getViewVector(1.0F).scale(1.4D);
        return new Vec3(this.getX() + forward.x, this.getY() + 0.3D, this.getZ() + forward.z);
    }

    @Override
    protected void serverTick() {
        Player prisoner = this.getPrisoner();
        if (prisoner != null) {
            this.holdPrisoner(prisoner);
            return;
        }
        LivingEntity target = this.getTarget();
        if (!(target instanceof Player player) || !player.isAlive()) {
            return;
        }
        if (player.hasEffect(ModStatusEffects.CAGED)) {
            return;
        }
        if (this.distanceToSqr(player) <= GRAB_RANGE * GRAB_RANGE) {
            this.capture(player);
        }
    }

    private void capture(Player player) {
        this.prisoner = player.getUUID();
        this.drainTicks = 0;
        this.cageTicks = 0;
        player.addEffect(new MobEffectInstance(ModStatusEffects.CAGED, CAGE_TICKS, 0, false, false, false));
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.IRON_DOOR_CLOSE, SoundSource.HOSTILE, 2.2F, 0.7F);
    }

    private void holdPrisoner(Player player) {
        if (!player.isAlive() || player.isRemoved() || this.isDeadOrDying()) {
            this.release(player);
            return;
        }
        if (!player.hasEffect(ModStatusEffects.CAGED)) {
            // the effect ran out: the prisoner broke free
            this.prisoner = null;
            return;
        }

        Vec3 cage = this.getCagePos();
        player.teleportTo(cage.x, cage.y, cage.z);
        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0.0F;

        // keep the tag topped up so the other machines keep ignoring him, but only for the
        // length of the cage — the prisoner eventually pries the bars open and runs
        this.cageTicks++;
        if (this.cageTicks <= CAGE_TICKS && player.age % 20 == 0) {
            player.addEffect(new MobEffectInstance(ModStatusEffects.CAGED, 60, 0, false, false, false));
        }

        this.drainTicks++;
        if (this.drainTicks % DAMAGE_INTERVAL == 0) {
            player.hurt(this.damageSources().mobAttack(this), CAGE_DAMAGE);
            if (this.level() instanceof ServerLevel serverWorld) {
                serverWorld.sendParticles(ParticleTypes.CRIMSON_SPORE, cage.x, cage.y + 1.0D, cage.z,
                        8, 0.3D, 0.3D, 0.3D, 0.0D);
            }
        }
    }

    private void release(@Nullable Player player) {
        if (player != null) {
            player.removeEffect(ModStatusEffects.CAGED);
        }
        this.prisoner = null;
    }

    @Nullable
    private Player getPrisoner() {
        if (this.prisoner == null || !(this.level() instanceof ServerLevel world)) {
            return null;
        }
        Player player = world.getPlayerByUUID(this.prisoner);
        if (player == null) {
            this.prisoner = null;
        }
        return player;
    }

    @Override
    public void die(DamageSource source) {
        this.release(this.getPrisoner());
        super.die(source);
    }

    @Override
    protected void clientTick() {
        if (this.prisoner != null && this.random.nextInt(4) == 0) {
            Vec3 cage = this.getCagePos();
            this.level().addParticle(ParticleTypes.SMOKE, cage.x, cage.y + 0.8D, cage.z, 0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    public void playChargeSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.HOSTILE, 2.0F, 0.6F);
    }
}
