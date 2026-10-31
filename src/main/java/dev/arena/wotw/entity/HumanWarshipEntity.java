package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

/**
 * A coastal defence ship: it sits in the water and shells Martian machines on the shore.
 */
public class HumanWarshipEntity extends AbstractHumanCombatantEntity {

    public HumanWarshipEntity(EntityType<? extends HumanWarshipEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return humanAttributes(240.0D, 0.14D, 8.0D, 18.0D, 8.0D, 48.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.add(3, new GunAttackGoal(this, 48.0D, 100, 3.2F, 0.0F));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.4D));
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }
}
