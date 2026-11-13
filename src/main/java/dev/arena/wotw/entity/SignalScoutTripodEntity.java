package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * The scout: a bare sphere of lenses on a stem, a radar dish above it and three very thin legs.
 *
 * <p><b>Signature ability</b>: the <b>signal</b>. It carries no weapon at all — it finds the player,
 * marks him with a glowing trace and screams the coordinates over the Martian network. Every war
 * machine within a hundred and fifty blocks turns around and comes for that spot.</p>
 */
public class SignalScoutTripodEntity extends AbstractTripodEntity {

    /** How often it sends a new signal. */
    private static final int SIGNAL_INTERVAL = 420;
    /** Radius in which it can see a player. */
    private static final double DETECT_RADIUS = 64.0D;
    /** Radius of the call. */
    private static final double CALL_RADIUS = 150.0D;

    public SignalScoutTripodEntity(EntityType<? extends SignalScoutTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(200.0D, 0.33D, 8.0D, 5.0D, 3.0D, 0.6D, 64.0D);
    }

    @Override
    protected void addExtraGoals() {
        // it never shoots: the ability is the call, not a weapon
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(SIGNAL_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        List<Player> players = server.getEntitiesOfClass(Player.class,
                this.getBoundingBox().inflate(DETECT_RADIUS),
                player -> player.isAlive() && !player.isCreative() && !player.isSpectator());
        if (players.isEmpty()) {
            return;
        }
        Player prey = players.get(0);
        int called = 0;
        for (AbstractTripodEntity ally : server.getEntitiesOfClass(AbstractTripodEntity.class,
                this.getBoundingBox().inflate(CALL_RADIUS))) {
            if (ally == this || ally.getTarget() != null) {
                continue;
            }
            ally.setTarget(prey);
            ally.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1, false, false, false));
            called++;
        }
        // the mark: everything nearby now knows exactly where the player is
        prey.addEffect(new MobEffectInstance(MobEffects.GLOWING, 240, 0, false, false, false));
        server.playSound(null, prey.getX(), prey.getY(), prey.getZ(), ModSounds.TRIPOD_HORN,
                SoundSource.HOSTILE, 3.0F, 1.35F);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.TRIPOD_HORN,
                SoundSource.HOSTILE, 2.0F, 0.85F);
        server.sendParticles(ParticleTypes.END_ROD, prey.getX(), prey.getY() + 0.2D, prey.getZ(),
                40, 0.6D, 2.2D, 0.6D, 0.02D);
        server.sendParticles(ParticleTypes.SCULK_SOUL, this.getX(), this.getY() + this.getBbHeight() * 0.9D,
                this.getZ(), 24, 0.8D, 0.8D, 0.8D, 0.05D);
        if (called > 0) {
            this.setAbilityCooldown(0);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(6) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX(), this.getY() + this.getBbHeight() * 1.02D, this.getZ(), 0.0D, 0.02D, 0.0D);
    }
}
