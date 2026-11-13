package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.CrateBlockEntity;
import dev.arena.wotw.block.GravityWellBlockEntity;
import dev.arena.wotw.block.MartianMortarBlockEntity;
import dev.arena.wotw.block.MartianSignalBeaconBlockEntity;
import dev.arena.wotw.block.MartianTurretBlockEntity;
import dev.arena.wotw.block.RepairPodBlockEntity;
import dev.arena.wotw.block.RootSpreaderBlockEntity;
import dev.arena.wotw.block.ShieldPylonBlockEntity;
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

    /** Mortar emplacement: lobbed shells. */
    public static BlockEntityType<MartianMortarBlockEntity> MARTIAN_MORTAR;

    /** Root spreader: sows the red weed. */
    public static BlockEntityType<RootSpreaderBlockEntity> ROOT_SPREADER;

    /** Gravity well: pulls entities in. */
    public static BlockEntityType<GravityWellBlockEntity> GRAVITY_WELL;

    /** Repair pod: heals Martian machines. */
    public static BlockEntityType<RepairPodBlockEntity> REPAIR_POD;

    /** Shield pylon: buffs Martian machines. */
    public static BlockEntityType<ShieldPylonBlockEntity> SHIELD_PYLON;

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
        BLOCK_ENTITIES.register("martian_mortar",
                () -> MARTIAN_MORTAR = BlockEntityType.Builder.of(MartianMortarBlockEntity::new,
                                ModBlocks.MARTIAN_MORTAR)
                        .build(null));
        BLOCK_ENTITIES.register("root_spreader",
                () -> ROOT_SPREADER = BlockEntityType.Builder.of(RootSpreaderBlockEntity::new,
                                ModBlocks.ROOT_SPREADER)
                        .build(null));
        BLOCK_ENTITIES.register("gravity_well",
                () -> GRAVITY_WELL = BlockEntityType.Builder.of(GravityWellBlockEntity::new,
                                ModBlocks.GRAVITY_WELL)
                        .build(null));
        BLOCK_ENTITIES.register("repair_pod",
                () -> REPAIR_POD = BlockEntityType.Builder.of(RepairPodBlockEntity::new,
                                ModBlocks.REPAIR_POD)
                        .build(null));
        BLOCK_ENTITIES.register("shield_pylon",
                () -> SHIELD_PYLON = BlockEntityType.Builder.of(ShieldPylonBlockEntity::new,
                                ModBlocks.SHIELD_PYLON)
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
