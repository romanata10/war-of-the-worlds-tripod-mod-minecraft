package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.MartianCylinderBlock;
import dev.arena.wotw.block.MartianDeviceBlock;
import dev.arena.wotw.block.BloodRootsBlock;
import dev.arena.wotw.block.CrateBlock;
import dev.arena.wotw.block.BloodVinesBlock;
import dev.arena.wotw.block.InfestedBlock;
import dev.arena.wotw.block.MartianSignalBeaconBlock;
import dev.arena.wotw.block.MartianTurretBlock;
import dev.arena.wotw.block.SporeSackBlock;
import dev.arena.wotw.block.RedRootBlock;
import dev.arena.wotw.block.TripodBlueprintBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/** Blocks of the mod: the Martian cylinder, the alloy the machines are plated with and the roots. */
public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WotwMod.MOD_ID);


    /* Sixth wave: the metals of the new machines and two working blocks. */

    /** Polished plate of the 1950s machine. */
    public static Block CAMERON_STEEL_BLOCK;

    /** Riveted brass of the 1906 lattice walker. */
    public static Block GOBLE_BRASS_BLOCK;

    /** The black, bone-dry shell of the flat saucer. */
    public static Block GOREY_BONE_BLOCK;

    /** Pale vegetable husk from the conical machine. */
    public static Block VEGETABLE_HUSK_BLOCK;

    /** Signal beacon: pulses regeneration, and summons machines when a player is near. */
    public static Block MARTIAN_SIGNAL_BEACON;

    /** Spore sack: cracks open into three lice. */
    public static Block SPORE_SACK;

    /* Ninth wave: the machines of the new bases and the ten new building materials. */

    /** Mortar emplacement: lobs shells in an arc at anything that comes close. */
    public static Block MARTIAN_MORTAR;

    /** Root spreader: sows the red weed over the soil around it. */
    public static Block ROOT_SPREADER;

    /** Gravity well: drags everything loose towards the pillar. */
    public static Block GRAVITY_WELL;

    /** Repair pod: welds damaged Martian machines back together. */
    public static Block REPAIR_POD;

    /** Shield pylon: wraps nearby machines in a shimmering field. */
    public static Block SHIELD_PYLON;

    /** Pressed Martian pipework, for the refineries. */
    public static Block MARTIAN_PIPE;

    /** Walkable grating cut from hull plate. */
    public static Block HULL_GRATING;

    /** Martian chitin, the shell the walkers grow instead of plating. */
    public static Block CHITIN_BLOCK;

    /** Ash and cinder left where a heat ray burned the ground. */
    public static Block ASHEN_SOIL;

    /** A hull plate that has been frozen solid by the frost machine. */
    public static Block FROZEN_HULL;
    public static Block CITY_PLATE;
    public static Block LENS_PILLAR;
    public static Block LAVA_CHANNEL;
    public static Block BONE_WALL;
    public static Block CHAIN_GRATING;
    public static Block CAPSULE_RACK;
    public static Block SPORE_VENT;
    public static Block MEMBRANE;

    public static Block MARTIAN_CYLINDER;

    /** Worked Martian alloy: the material every capsule and structure is built from. */
    public static Block MARTIAN_ALLOY_BLOCK;

    /** The red root weed the machines sow behind themselves. */
    public static Block RED_ROOT;

    /** The pale plate the Martian complexes are clad in. */
    public static Block WHITE_MARTIAN_ALLOY;

    /** Strong Martian glass: alloy and glass, and it stops more than a pickaxe swing. */
    public static Block MARTIAN_GLASS;

    /** Blood roots: stepping on them brings the dark. */
    public static Block BLOOD_ROOTS;

    /** The blueprint: put it down and a rideable tripod is built on top of it. */
    public static Block TRIPOD_BLUEPRINT;

    /* ------------------------------------------------------------------ */
    /*  Third wave: Martian tech, crates, turrets and the infected stone   */
    /* ------------------------------------------------------------------ */

    /** Riveted hull plating: the walls of the domes, garages and workshops. */
    public static Block MARTIAN_HULL;

    /** A laboratory console: screens, dials and a green glow. */
    public static Block MARTIAN_CONSOLE;

    /** Martian lamp: cold white light on a stalk. */
    public static Block MARTIAN_LAMP;

    /** Martian supply crate: a container you can open, like a barrel. */
    public static Block MARTIAN_CRATE;

    /** Human wooden crate, left behind in the towns and the warehouses. */
    public static Block HUMAN_CRATE;

    /** Military crate: olive steel, stencilled, and usually full of nothing useful any more. */
    public static Block MILITARY_CRATE;

    /** The tall Martian turret that watches over the bases and the garages. */
    public static Block MARTIAN_TURRET;

    /** Tall blood vines: the next generation of the red weed. */
    public static Block BLOOD_VINES;

    /** Cobblestone the roots have cracked open. */
    public static Block INFESTED_COBBLESTONE;

    /** Bricks the roots have cracked open. */
    public static Block INFESTED_BRICKS;

    /*
     * The blocks are built here, not next to the fields: Forge freezes the vanilla block registry before
     * a mod is constructed and {@code Block} registers a holder for itself in its own constructor. Doing
     * the work inside the supplier keeps it in the register event, where Forge expects it, and every
     * field above is ready by the time the game asks for it.
     */
    static {
        BLOCKS.register("cameron_steel_block",
                () -> CAMERON_STEEL_BLOCK = new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("goble_brass_block",
                () -> GOBLE_BRASS_BLOCK = new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("gorey_bone_block",
                () -> GOREY_BONE_BLOCK = new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("vegetable_husk_block",
                () -> VEGETABLE_HUSK_BLOCK = new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_signal_beacon",
                () -> MARTIAN_SIGNAL_BEACON = new MartianSignalBeaconBlock(BlockBehaviour.Properties.of()
                    .strength(4.0F, 10.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 12)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("spore_sack",
                () -> SPORE_SACK = new SporeSackBlock(BlockBehaviour.Properties.of()
                    .strength(1.0F, 2.0F)
                    .sound(SoundType.SLIME_BLOCK)
                    .lightLevel(state -> 4)));
        BLOCKS.register("martian_cylinder",
                () -> MARTIAN_CYLINDER = new MartianCylinderBlock(BlockBehaviour.Properties.of()
                    .strength(6.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 6)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_alloy_block",
                () -> MARTIAN_ALLOY_BLOCK = new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("red_root",
                () -> RED_ROOT = new RedRootBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 3)
                    .sound(SoundType.GRASS)
                    .randomTicks()));
        BLOCKS.register("white_martian_alloy",
                () -> WHITE_MARTIAN_ALLOY = new Block(BlockBehaviour.Properties.of()
                    .strength(6.0F, 15.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_glass",
                () -> MARTIAN_GLASS = new Block(BlockBehaviour.Properties.of()
                    .strength(4.0F, 12.0F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .lightLevel(state -> 3)));
        BLOCKS.register("blood_roots",
                () -> BLOOD_ROOTS = new BloodRootsBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 4)
                    .sound(SoundType.GRASS)
                    .randomTicks()));
        BLOCKS.register("tripod_blueprint",
                () -> TRIPOD_BLUEPRINT = new TripodBlueprintBlock(BlockBehaviour.Properties.of()
                    .strength(1.5F, 6.0F)
                    .lightLevel(state -> 7)
                    .sound(SoundType.WOOL)));
        BLOCKS.register("martian_hull",
                () -> MARTIAN_HULL = new Block(BlockBehaviour.Properties.of()
            .strength(7.0F, 16.0F)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_console",
                () -> MARTIAN_CONSOLE = new Block(BlockBehaviour.Properties.of()
            .strength(4.0F, 10.0F)
            .sound(SoundType.METAL)
            .lightLevel(state -> 9)
            .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_lamp",
                () -> MARTIAN_LAMP = new Block(BlockBehaviour.Properties.of()
            .strength(3.0F, 8.0F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> 14)));
        BLOCKS.register("martian_crate",
                () -> MARTIAN_CRATE = new CrateBlock(BlockBehaviour.Properties.of()
            .strength(2.5F, 8.0F)
            .sound(SoundType.WOOD)));
        BLOCKS.register("human_crate",
                () -> HUMAN_CRATE = new CrateBlock(BlockBehaviour.Properties.of()
            .strength(2.0F, 6.0F)
            .sound(SoundType.WOOD)));
        BLOCKS.register("military_crate",
                () -> MILITARY_CRATE = new CrateBlock(BlockBehaviour.Properties.of()
            .strength(3.0F, 9.0F)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_turret",
                () -> MARTIAN_TURRET = new MartianTurretBlock(BlockBehaviour.Properties.of()
                    .strength(6.0F, 14.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .lightLevel(state -> 5)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("blood_vines",
                () -> BLOOD_VINES = new BloodVinesBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 4)
                    .sound(SoundType.GRASS)
                    .randomTicks()));
        BLOCKS.register("infested_cobblestone", () -> INFESTED_COBBLESTONE = new InfestedBlock(BlockBehaviour.Properties.of().strength(2.4F, 8.0F).requiresCorrectToolForDrops().randomTicks()));
        BLOCKS.register("infested_bricks", () -> INFESTED_BRICKS = new InfestedBlock(BlockBehaviour.Properties.of().strength(2.6F, 8.0F).requiresCorrectToolForDrops().randomTicks()));

        BLOCKS.register("martian_mortar",
                () -> MARTIAN_MORTAR = new MartianDeviceBlock(BlockBehaviour.Properties.of()
                    .strength(7.0F, 16.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .lightLevel(state -> 6)
                    .requiresCorrectToolForDrops(), MartianDeviceBlock.Device.MORTAR));
        BLOCKS.register("root_spreader",
                () -> ROOT_SPREADER = new MartianDeviceBlock(BlockBehaviour.Properties.of()
                    .strength(3.0F, 8.0F)
                    .sound(SoundType.GRASS)
                    .lightLevel(state -> 8), MartianDeviceBlock.Device.ROOT_SPREADER));
        BLOCKS.register("gravity_well",
                () -> GRAVITY_WELL = new MartianDeviceBlock(BlockBehaviour.Properties.of()
                    .strength(5.0F, 14.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .lightLevel(state -> 12)
                    .requiresCorrectToolForDrops(), MartianDeviceBlock.Device.GRAVITY_WELL));
        BLOCKS.register("repair_pod",
                () -> REPAIR_POD = new MartianDeviceBlock(BlockBehaviour.Properties.of()
                    .strength(5.5F, 14.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 7)
                    .requiresCorrectToolForDrops(), MartianDeviceBlock.Device.REPAIR_POD));
        BLOCKS.register("shield_pylon",
                () -> SHIELD_PYLON = new MartianDeviceBlock(BlockBehaviour.Properties.of()
                    .strength(6.5F, 15.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 10)
                    .requiresCorrectToolForDrops(), MartianDeviceBlock.Device.SHIELD_PYLON));
        BLOCKS.register("martian_pipe",
                () -> MARTIAN_PIPE = new Block(BlockBehaviour.Properties.of()
                    .strength(4.5F, 11.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("hull_grating",
                () -> HULL_GRATING = new Block(BlockBehaviour.Properties.of()
                    .strength(4.0F, 10.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("chitin_block",
                () -> CHITIN_BLOCK = new Block(BlockBehaviour.Properties.of()
                    .strength(3.2F, 9.0F)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("ashen_soil",
                () -> ASHEN_SOIL = new Block(BlockBehaviour.Properties.of()
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.GRAVEL)));
        BLOCKS.register("frozen_hull",
                () -> FROZEN_HULL = new Block(BlockBehaviour.Properties.of()
                    .strength(5.2F, 13.0F)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("city_plate",
                () -> CITY_PLATE = new Block(BlockBehaviour.Properties.of()
                    .strength(8.0F, 20.0F)
                    .sound(SoundType.METAL)));
        BLOCKS.register("lens_pillar",
                () -> LENS_PILLAR = new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F, 12.0F)
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> 14)));
        BLOCKS.register("lava_channel",
                () -> LAVA_CHANNEL = new Block(BlockBehaviour.Properties.of()
                    .strength(3.0F, 9.0F)
                    .sound(SoundType.STONE)
                    .lightLevel(state -> 12)));
        BLOCKS.register("bone_wall",
                () -> BONE_WALL = new Block(BlockBehaviour.Properties.of()
                    .strength(4.0F, 10.0F)
                    .sound(SoundType.BONE_BLOCK)));
        BLOCKS.register("chain_grating",
                () -> CHAIN_GRATING = new Block(BlockBehaviour.Properties.of()
                    .strength(4.5F, 11.0F)
                    .sound(SoundType.METAL)));
        BLOCKS.register("capsule_rack",
                () -> CAPSULE_RACK = new Block(BlockBehaviour.Properties.of()
                    .strength(6.0F, 14.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 6)));
        BLOCKS.register("spore_vent",
                () -> SPORE_VENT = new Block(BlockBehaviour.Properties.of()
                    .strength(4.0F, 10.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 4)));
        BLOCKS.register("membrane",
                () -> MEMBRANE = new Block(BlockBehaviour.Properties.of()
                    .strength(1.6F, 4.0F)
                    .sound(SoundType.HONEY_BLOCK)
                    .lightLevel(state -> 2)));
    }

    private ModBlocks() {
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        WotwMod.LOGGER.debug("Registered {} blocks", BLOCKS.getEntries().size());
    }
}
