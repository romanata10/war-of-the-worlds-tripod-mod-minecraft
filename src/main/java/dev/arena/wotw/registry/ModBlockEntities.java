package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.CrateBlockEntity;
import dev.arena.wotw.block.MartianSignalBeaconBlockEntity;
import dev.arena.wotw.block.MartianTurretBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

/** Block entities: the tall Martian turret with its timer, and the three crates with their loot. */
public final class ModBlockEntities {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.BLOCK_ENTITY_TYPES,
                    WotwMod.MOD_ID);

    /** Set by the register event; the turret block is registered before this, so it is already there. */
    public static BlockEntityType<MartianTurretBlockEntity> MARTIAN_TURRET;

    /** The signal beacon that calls new machines in. */
    public static BlockEntityType<MartianSignalBeaconBlockEntity> SIGNAL_BEACON;

    /** One type for all three crates: same inventory, different loot table. */
    public static BlockEntityType<CrateBlockEntity> CRATE;

    static {
        BLOCK_ENTITIES.register("martian_signal_beacon",
                () -> SIGNAL_BEACON = BlockEntityType.Builder.of(MartianSignalBeaconBlockEntity::new,
                                ModBlocks.MARTIAN_SIGNAL_BEACON)
                        .build(null));
        BLOCK_ENTITIES.register("crate",
                () -> CRATE = BlockEntityType.Builder.of(CrateBlockEntity::new,
                                ModBlocks.MARTIAN_CRATE, ModBlocks.HUMAN_CRATE, ModBlocks.MILITARY_CRATE)
                        .build(null));
        BLOCK_ENTITIES.register("martian_turret",
                () -> MARTIAN_TURRET = BlockEntityType.Builder.of(MartianTurretBlockEntity::new, ModBlocks.MARTIAN_TURRET)
                        .build(null));
    }

    private ModBlockEntities() {
    }

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
        WotwMod.LOGGER.debug("Registered block entities");
    }
}
