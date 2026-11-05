package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * A human tank: slow, plated, and it lobs a shell at anything with too many legs.
 */
public class HumanTankEntity extends AbstractHumanCombatantEntity {

    public HumanTankEntity(EntityType<? extends HumanTankEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(180.0D, 0.17D, 6.0D, 16.0D, 8.0D, 44.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 42.0D, 80, 2.2F, 0.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.5D));
    }
}
