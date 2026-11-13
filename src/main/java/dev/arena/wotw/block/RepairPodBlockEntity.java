package dev.arena.wotw.block;

import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Repair pod: a squat cradle of alloy plates that welds Martian machines back together. Every five
 * seconds it heals eight health on every damaged tripod within twelve blocks.
 */
public class RepairPodBlockEntity extends AbstractMartianDeviceBlockEntity {

    private static final double RADIUS = 12.0D;

    public RepairPodBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REPAIR_POD, pos, state, 100);
    }

    @Override
    protected void pulse(Level world) {
        List<AbstractTripodEntity> machines = world.getEntitiesOfClass(AbstractTripodEntity.class,
                this.area(RADIUS));
        int repaired = 0;
        for (AbstractTripodEntity machine : machines) {
            if (machine.getHealth() >= machine.getMaxHealth()) {
                continue;
            }
            machine.heal(8.0F);
            repaired++;
            if (world instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.HAPPY_VILLAGER, machine.getX(),
                        machine.getY() + machine.getBbHeight() * 0.5D, machine.getZ(),
                        6, 0.5D, 1.0D, 0.5D, 0.01D);
            }
        }
        if (repaired > 0) {
            world.playSound(null, this.worldPosition, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.2F, 0.7F);
        }
    }
}
