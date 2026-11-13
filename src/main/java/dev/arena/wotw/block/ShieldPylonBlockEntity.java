package dev.arena.wotw.block;

import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Shield pylon: a stack of charged plates that wraps every Martian machine within sixteen blocks in
 * a shimmering field, so they shrug off part of every hit. The field drops the moment the pylon is
 * broken, which makes it the first thing to dig out of a base.
 */
public class ShieldPylonBlockEntity extends AbstractMartianDeviceBlockEntity {

    private static final double RADIUS = 16.0D;

    public ShieldPylonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHIELD_PYLON, pos, state, 60);
    }

    @Override
    protected void pulse(Level world) {
        List<AbstractTripodEntity> machines = world.getEntitiesOfClass(AbstractTripodEntity.class,
                this.area(RADIUS));
        for (AbstractTripodEntity machine : machines) {
            machine.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 0, false, false, false));
            machine.startShield(100);
            if (world instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.ELECTRIC_SPARK, machine.getX(),
                        machine.getY() + machine.getBbHeight() * 0.5D, machine.getZ(),
                        6, machine.getBbWidth() * 0.5D, machine.getBbHeight() * 0.4D,
                        machine.getBbWidth() * 0.5D, 0.05D);
            }
        }
        if (!machines.isEmpty()) {
            world.playSound(null, this.worldPosition, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.6F);
        }
    }
}
