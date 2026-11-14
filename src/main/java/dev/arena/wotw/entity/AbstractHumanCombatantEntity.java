package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * The human side of the war: soldiers, tanks, fighters and warships.
 *
 * <p>Every one of them is at war with the Martians and nothing else. They will not start a fight
 * with the player — they only shoot back when they are hit first — and they hunt every Martian
 * machine in the mod, including the tripods from the 2005 film and the drones from the second
 * wave. The tripod built from a human blueprint is on their side and is left alone.</p>
 */
public abstract class AbstractHumanCombatantEntity extends PathfinderMob {

    protected AbstractHumanCombatantEntity(EntityType<? extends AbstractHumanCombatantEntity> type, Level world) {
        super(type, world);
    }

    protected static AttributeSupplier.Builder humanAttributes(double health, double speed,
                                                                      double attackDamage, double armor,
                                                                      double toughness, double followRange) {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, health)
                .add(Attributes.MOVEMENT_SPEED, speed)
                .add(Attributes.ATTACK_DAMAGE, attackDamage)
                .add(Attributes.ARMOR, armor)
                .add(Attributes.ARMOR_TOUGHNESS, toughness)
                .add(Attributes.FOLLOW_RANGE, followRange);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addCombatGoals();

        // Only a Martian or the player who swung first may pull a human unit into a fight. Without
        // this a stray shell that clipped a friendly crew would make the whole line turn on itself.
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                LivingEntity attacker = AbstractHumanCombatantEntity.this.getLastHurtByMob();
                if (attacker != null && !mayRetaliateAgainst(attacker)) {
                    return false;
                }
                return super.canUse();
            }
        });
        // enemies of humanity: every Martian machine, but never the crewed tripod
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false,
                ModEntities::isMartianInvader));
    }

    /** True for the only things a human unit is allowed to answer: Martians and the player. */
    protected static boolean mayRetaliateAgainst(LivingEntity attacker) {
        return ModEntities.isMartianInvader(attacker) || attacker instanceof Player;
    }

    /** Every human unit is on the same side: shells and bullets never hurt the crew next to it. */
    public static boolean isHumanUnit(Entity entity) {
        return entity instanceof AbstractHumanCombatantEntity;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isHumanUnit(source.getEntity()) || isHumanUnit(source.getDirectEntity())) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return !isHumanUnit(target) && super.canAttack(target);
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        return isHumanUnit(other) || super.isAlliedTo(other);
    }

    /** Hook for the subclass: its weapon goal and how it walks around. */
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 24.0D, 28, 0.0F, 5.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.6D));
    }
}
