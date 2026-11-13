package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * An armoured personnel carrier: eight wheels, a sloped hull and a small autocannon. It is the work
 * horse of the human counter-attack — quick enough to catch a striding machine and armoured enough
 * to survive the first exchange.
 */
public class HumanApcEntity extends AbstractHumanCombatantEntity {

    public HumanApcEntity(EntityType<? extends HumanApcEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(150.0D, 0.30D, 6.0D, 12.0D, 6.0D, 48.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 34.0D, 34, 0.0F, 4.5F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.7D));
    }
}
