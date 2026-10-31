package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * The human side of the war: soldiers, tanks, fighters and warships.
 *
 * <p>Every one of them is at war with the Martians and nothing else. They will not start a fight
 * with the player — they only shoot back when they are hit first — and they hunt every Martian
 * machine in the mod, including the tripods from the 2005 film and the drones from the second
 * wave. The tripod built from a human blueprint is on their side and is left alone.</p>
 */
public abstract class AbstractHumanCombatantEntity extends PathAwareEntity {

    protected AbstractHumanCombatantEntity(EntityType<? extends AbstractHumanCombatantEntity> type, World world) {
        super(type, world);
    }

    protected static DefaultAttributeContainer.Builder humanAttributes(double health, double speed,
                                                                      double attackDamage, double armor,
                                                                      double toughness, double followRange) {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, health)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attackDamage)
                .add(EntityAttributes.GENERIC_ARMOR, armor)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, toughness)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, followRange);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.addCombatGoals();

        this.targetSelector.add(1, new RevengeGoal(this));
        // enemies of humanity: every Martian machine, but never the crewed tripod
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, MobEntity.class, 10, true, false,
                ModEntities::isMartianInvader));
    }

    /** Hook for the subclass: its weapon goal and how it walks around. */
    protected void addCombatGoals() {
        this.goalSelector.add(3, new GunAttackGoal(this, 24.0D, 28, 0.0F, 5.0F));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.6D));
    }
}
