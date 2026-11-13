package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.GunAttackGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * A field medic. It carries a pistol for the odd pot shot, but its job is patching up the rest of
 * the squad: every five seconds it bandages every wounded human within twelve blocks.
 */
public class HumanMedicEntity extends AbstractHumanCombatantEntity {

    private static final int HEAL_INTERVAL = 100;
    private static final double HEAL_RADIUS = 12.0D;

    public HumanMedicEntity(EntityType<? extends HumanMedicEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return humanAttributes(70.0D, 0.34D, 3.0D, 4.0D, 2.0D, 40.0D);
    }

    private int healTimer;

    @Override
    protected void addCombatGoals() {
        this.goalSelector.addGoal(3, new GunAttackGoal(this, 20.0D, 40, 0.0F, 2.5F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.55D));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        if (--this.healTimer > 0) {
            return;
        }
        this.healTimer = HEAL_INTERVAL;
        List<AbstractHumanCombatantEntity> squad = this.level().getEntitiesOfClass(
                AbstractHumanCombatantEntity.class, this.getBoundingBox().inflate(HEAL_RADIUS));
        int patched = 0;
        for (AbstractHumanCombatantEntity soldier : squad) {
            if (soldier.getHealth() >= soldier.getMaxHealth()) {
                continue;
            }
            soldier.heal(6.0F);
            patched++;
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.HAPPY_VILLAGER, soldier.getX(),
                        soldier.getY() + soldier.getBbHeight() * 0.6D, soldier.getZ(),
                        4, 0.3D, 0.4D, 0.3D, 0.01D);
            }
        }
        if (patched > 0) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.NEUTRAL, 1.0F, 1.4F);
        }
    }
}
