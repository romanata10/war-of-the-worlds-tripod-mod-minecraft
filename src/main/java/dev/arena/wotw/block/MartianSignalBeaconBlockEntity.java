package dev.arena.wotw.block;

import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * The timer behind {@link MartianSignalBeaconBlock}: every pulse it heals the machines around it and
 * every fifth pulse it calls in a new one.
 */
public class MartianSignalBeaconBlockEntity extends BlockEntity {

    /** Ticks between two regeneration pulses. */
    private static final int PULSE = 100;
    /** How many pulses between two summonings. */
    private static final int SUMMON_EVERY = 5;
    /** Machines this beacon will keep on the field at once. */
    private static final int MAX_MACHINES = 3;

    private int ticks;

    public MartianSignalBeaconBlockEntity(BlockPos pos, BlockState state) {
        super(dev.arena.wotw.registry.ModBlockEntities.SIGNAL_BEACON, pos, state);
    }

    public void tick() {
        if (!(this.level instanceof ServerLevel world)) {
            return;
        }
        this.ticks++;
        if (this.ticks % PULSE != 0) {
            return;
        }
        BlockPos pos = this.getBlockPos();
        List<AbstractTripodEntity> nearby = world.getEntitiesOfClass(AbstractTripodEntity.class,
                new net.minecraft.world.phys.AABB(pos).inflate(24.0D));
        for (AbstractTripodEntity machine : nearby) {
            machine.addEffect(new MobEffectInstance(MobEffects.REGENERATION, PULSE + 40, 1));
        }
        if (this.ticks % (PULSE * SUMMON_EVERY) != 0) {
            return;
        }
        if (nearby.size() >= MAX_MACHINES) {
            return;
        }
        List<Player> players = world.getEntitiesOfClass(Player.class,
                new net.minecraft.world.phys.AABB(pos).inflate(32.0D));
        if (players.isEmpty()) {
            return;
        }
        List<EntityType<?>> kinds = ModEntities.tripods();
        if (kinds.isEmpty()) {
            return;
        }
        EntityType<?> type = kinds.get(world.getRandom().nextInt(kinds.size()));
        double angle = world.getRandom().nextDouble() * Math.PI * 2.0D;
        double distance = 6.0D + world.getRandom().nextDouble() * 6.0D;
        BlockPos spot = pos.offset((int) Math.round(Math.cos(angle) * distance), 0,
                (int) Math.round(Math.sin(angle) * distance));
        int groundY = world.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                spot.getX(), spot.getZ());
        if (type.create(world) instanceof Mob mob) {
            mob.moveTo(spot.getX() + 0.5D, groundY, spot.getZ() + 0.5D,
                    world.getRandom().nextFloat() * 360.0F, 0.0F);
            mob.finalizeSpawn(world, world.getCurrentDifficultyAt(spot), MobSpawnType.MOB_SUMMONED, null, null);
            world.addFreshEntity(mob);
            MartianSignalBeaconBlock.flash(world, pos);
        }
    }
}
