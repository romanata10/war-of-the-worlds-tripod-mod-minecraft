package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * A towed field gun. It barely moves, but it can put a heavy shell on a tripod sixty blocks away,
 * which makes it the human answer to a machine that thinks it is out of range.
 */
public class HumanArtilleryEntity extends AbstractHumanCombatantEntity {

    public HumanArtilleryEntity(EntityType<? extends HumanArtilleryEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(110.0D, 0.12D, 5.0D, 8.0D, 4.0D, 60.0D);
    }

    @Override
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 58.0D, 110, 3.4F, 0.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.25D));
    }
}
