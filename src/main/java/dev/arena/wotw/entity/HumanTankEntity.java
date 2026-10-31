package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

/**
 * A human tank: slow, plated, and it lobs a shell at anything with too many legs.
 */
public class HumanTankEntity extends AbstractHumanCombatantEntity {

    public HumanTankEntity(EntityType<? extends HumanTankEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return humanAttributes(180.0D, 0.17D, 6.0D, 16.0D, 8.0D, 44.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.add(3, new GunAttackGoal(this, 42.0D, 80, 2.2F, 0.0F));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.5D));
    }
}
