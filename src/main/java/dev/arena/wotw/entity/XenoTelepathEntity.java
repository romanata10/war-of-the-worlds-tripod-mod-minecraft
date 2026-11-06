package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * The Xeno-Telepath — a Martian sealed inside a glass dome, riding a light tripod.
 *
 * <p><b>Signature ability</b>: telepathy. There is no gun on the dome at all; the Martian inside
 * reaches into the minds around it.</p>
 * <ul>
 *     <li><b>Human minds</b> — soldiers, crews and pilots within range turn on each other: the
 *     carrier wave makes one of them choose a comrade as its target and pulls the strength out of
 *     the rest.</li>
 *     <li><b>Your mind</b> — a player close to the machine is left nauseous and glowing, so the
 *     tripods can see them through walls.</li>
 * </ul>
 */
public class XenoTelepathEntity extends AbstractTripodEntity {

    /** How far the carrier wave reaches into human minds. */
    private static final double HUMAN_RANGE = 24.0D;
    /** How far it reaches into a player's head. */
    private static final double PLAYER_RANGE = 18.0D;

    public XenoTelepathEntity(EntityType<? extends XenoTelepathEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(230.0D, 0.28D, 8.0D, 10.0D, 5.0D, 0.7D, 44.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.LASER, 7.0F, 34.0D, 20, 130));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (this.onIntervalZero(140)) {
            this.broadcastTelepathy();
        }
    }

    private void broadcastTelepathy() {
        for (Player player : this.level().getEntitiesOfClass(Player.class,
                this.getBoundingBox().inflate(PLAYER_RANGE))) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 1));
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0));
        }

        List<AbstractHumanCombatantEntity> humans = this.level().getEntitiesOfClass(
                AbstractHumanCombatantEntity.class, this.getBoundingBox().inflate(HUMAN_RANGE));
        boolean turned = false;
        for (AbstractHumanCombatantEntity human : humans) {
            human.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 1));
            if (this.random.nextFloat() > 0.45F) {
                continue;
            }
            // find a comrade nearby and make this one believe it is the enemy
            AbstractHumanCombatantEntity scapegoat = null;
            for (AbstractHumanCombatantEntity other : humans) {
                if (other != human && other.distanceToSqr(human) < 196.0D) {
                    scapegoat = other;
                    break;
                }
            }
            if (scapegoat == null) {
                continue;
            }
            human.setTarget(scapegoat);
            human.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 160, 0));
            turned = true;
        }

        if (turned && this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.ENCHANT, this.getX(), this.getY() + this.getBbHeight() * 0.7D,
                    this.getZ(), 70, HUMAN_RANGE * 0.5D, 2.0D, HUMAN_RANGE * 0.5D, 0.6D);
            server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + this.getBbHeight() * 0.6D,
                    this.getZ(), 25, 3.0D, 1.0D, 3.0D, 0.02D);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        double angle = this.random.nextDouble() * Math.PI * 2.0D;
        this.level().addParticle(ParticleTypes.ENCHANT,
                this.getX() + Math.cos(angle) * 3.0D, this.getY() + this.getBbHeight() * 0.55D,
                this.getZ() + Math.sin(angle) * 3.0D, 0.0D, 0.05D, 0.0D);
    }
}
