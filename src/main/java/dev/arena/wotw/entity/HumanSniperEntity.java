package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;

/**
 * A sniper team of one: a ghillie cape, a scoped rifle and a great deal of patience. It fires rarely
 * and hits hard, picking at tripod joints from the far side of the field.
 */
public class HumanSniperEntity extends AbstractHumanCombatantEntity {

    public HumanSniperEntity(EntityType<? extends HumanSniperEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(60.0D, 0.26D, 4.0D, 4.0D, 2.0D, 56.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 56.0D, 80, 0.0F, 11.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.45D));
    }
}
