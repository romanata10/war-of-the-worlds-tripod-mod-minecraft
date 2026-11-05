package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.MartianTurretBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

/** Block entities: so far only the tall Martian turret, which needs somewhere to keep its timer. */
public final class ModBlockEntities {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.BLOCK_ENTITY_TYPES,
                    WotwMod.MOD_ID);

    /** Set by the register event; the turret block is registered before this, so it is already there. */
    public static BlockEntityType<MartianTurretBlockEntity> MARTIAN_TURRET;

    static {
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
