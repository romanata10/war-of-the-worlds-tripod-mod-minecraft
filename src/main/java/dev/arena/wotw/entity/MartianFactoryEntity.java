package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * The factory of Martian materials: a core sphere between two pods on steel rods, a stack on top and
 * four legs.
 *
 * <p><b>Signature ability</b>: the <b>forge</b>. It welds the Martian machines around it back
 * together and pours out a bar of alloy every so often — kill it and it spills the rest of the
 * hold.</p>
 */
public class MartianFactoryEntity extends AbstractTripodEntity {

    private static final int WELD_INTERVAL = 40;
    private static final int POUR_INTERVAL = 200;
    private static final double WELD_RANGE = 12.0D;

    public MartianFactoryEntity(EntityType<? extends MartianFactoryEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(460.0D, 0.20D, 0.0D, 12.0D, 8.0D, 0.95D, 36.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (this.onIntervalZero(WELD_INTERVAL)) {
            for (AbstractTripodEntity machine : server.getEntitiesOfClass(AbstractTripodEntity.class,
                    this.getBoundingBox().inflate(WELD_RANGE), machine -> machine != this && machine.isAlive())) {
                if (machine.getHealth() < machine.getMaxHealth()) {
                    machine.heal(5.0F);
                    server.sendParticles(ParticleTypes.ELECTRIC_SPARK, machine.getX(),
                            machine.getY() + machine.getBbHeight() * 0.5D, machine.getZ(), 10, 0.4D, 0.6D, 0.4D,
                            0.05D);
                }
            }
        }
        if (this.onIntervalZero(POUR_INTERVAL)) {
            ItemEntity bar = new ItemEntity(server, this.getX(), this.getY() + 1.0D, this.getZ(),
                    new ItemStack(ModItems.MARTIAN_ALLOY));
            bar.setDefaultPickUpDelay();
            server.addFreshEntity(bar);
            server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.LAVA_POP,
                    SoundSource.HOSTILE, 1.2F, 0.8F);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.LAVA,
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D,
                this.getY() + 1.4D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D, 0.0D, 0.0D, 0.0D);
    }

    /** The factory is a rolling mill, not a hunter: it never picks a fight. */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.FloatGoal(this));
        this.goalSelector.addGoal(8, new net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(9, new net.minecraft.world.entity.ai.goal.RandomLookAroundGoal(this));
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean causedByPlayer) {
        super.dropCustomDeathLoot(source, looting, causedByPlayer);
        ItemStack hold = new ItemStack(ModItems.MARTIAN_ALLOY, 3 + looting);
        this.spawnAtLocation(hold);
    }

    /** Nothing here is a victim: the factory only ever heals its own. */
    @Override
    public boolean isPushable() {
        return false;
    }

    /** The forge burns: a stack of alloy instead of the usual kill reward. */
    @Override
    public boolean fireImmune() {
        return true;
    }
}
