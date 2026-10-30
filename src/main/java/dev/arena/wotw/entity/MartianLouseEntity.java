package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

/**
 * "Martian Louse" from <i>The Great Martian War 1913-1917</i> — the smallest war machine of the
 * invasion, a knee high crawler that never travels alone.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Swarm</b> — every 15 seconds a louse that is not surrounded by its kin spawns a new
 *     louse, so ignore them and you will drown in them.</li>
 *     <li><b>Infested bite</b> — melee hits poison the victim.</li>
 * </ul>
 */
public class MartianLouseEntity extends AbstractTripodEntity {

    private static final int MAX_SWARM = 7;

    public MartianLouseEntity(EntityType<? extends MartianLouseEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(18.0D, 0.45D, 4.0D, 0.0D, 0.0D, 0.0D, 32.0D);
    }

    @Override
    protected void serverTick() {
        if (!this.onIntervalZero(300) || this.getRandom().nextFloat() > 0.55F) {
            return;
        }
        Box area = new Box(this.getX() - 14.0D, this.getY() - 6.0D, this.getZ() - 14.0D,
                this.getX() + 14.0D, this.getY() + 6.0D, this.getZ() + 14.0D);
        List<MartianLouseEntity> swarm = this.world.getEntitiesByClass(MartianLouseEntity.class, area,
                louse -> louse.isAlive());
        if (swarm.size() >= MAX_SWARM) {
            return;
        }
        MartianLouseEntity child = ModEntities.MARTIAN_LOUSE.create(this.world);
        if (child == null) {
            return;
        }
        child.refreshPositionAndAngles(this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D,
                this.getY(), this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D,
                this.random.nextFloat() * 360.0F, 0.0F);
        child.setPersistent();
        this.world.spawnEntity(child);
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        victim.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SILVERFISH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SILVERFISH_DEATH;
    }

    @Override
    public float getSoundVolume() {
        return 0.7F;
    }
}
