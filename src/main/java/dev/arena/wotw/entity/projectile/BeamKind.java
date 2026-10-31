package dev.arena.wotw.entity.projectile;

import dev.arena.wotw.WotwMod;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/**
 * Every heat ray variant in the mod. The kind decides speed, reach, area of effect,
 * how long it sets things on fire, the impact particles and the texture of the beam.
 */
public enum BeamKind {
    /** 2005 Spielberg / Goliath / 1953 heat ray: slow, wide, sets the world ablaze. */
    HEAT_RAY("heat_ray", 1.9D, 52.0D, 1.25D, 7, 1.0F, 0.8F, ParticleTypes.FLAME),
    /** BBC red tripod laser volley: fast, thin, piercing. */
    LASER("laser", 3.4D, 64.0D, 0.85D, 4, 0.7F, 1.5F, ParticleTypes.CRIT),
    /** Asylum walker eye beam: green, heavy and dirty. */
    GREEN_BEAM("green_beam", 2.4D, 44.0D, 1.4D, 8, 1.15F, 0.7F, ParticleTypes.HAPPY_VILLAGER),
    /** Martian heron lance: extremely fast and long, but very narrow. */
    LANCE("lance", 4.6D, 80.0D, 0.7D, 3, 0.55F, 1.9F, ParticleTypes.END_ROD);

    private final String id;
    private final double speed;
    private final double maxDistance;
    private final double radius;
    private final int igniteSeconds;
    private final float width;
    private final float soundPitch;
    private final ParticleEffect particle;
    private final Identifier texture;

    BeamKind(String id, double speed, double maxDistance, double radius, int igniteSeconds,
             float width, float soundPitch, ParticleEffect particle) {
        this.id = id;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.radius = radius;
        this.igniteSeconds = igniteSeconds;
        this.width = width;
        this.soundPitch = soundPitch;
        this.particle = particle;
        this.texture = WotwMod.id("textures/entity/beam_" + id + ".png");
    }

    public String getId() {
        return this.id;
    }

    /** Blocks travelled per tick. */
    public double getSpeed() {
        return this.speed;
    }

    public double getMaxDistance() {
        return this.maxDistance;
    }

    public int getMaxLife() {
        return MathHelper.ceil(this.maxDistance / this.speed) + 2;
    }

    /** Half thickness of the damaging sweep. */
    public double getRadius() {
        return this.radius;
    }

    public int getIgniteSeconds() {
        return this.igniteSeconds;
    }

    /** Width multiplier used when rendering the beam segment. */
    public float getWidth() {
        return this.width;
    }

    public float getSoundPitch() {
        return this.soundPitch;
    }

    public ParticleEffect getParticle() {
        return this.particle;
    }

    public Identifier getTexture() {
        return this.texture;
    }

    public static BeamKind byId(int ordinal) {
        BeamKind[] values = values();
        if (ordinal < 0 || ordinal >= values.length) {
            return HEAT_RAY;
        }
        return values[ordinal];
    }
}
