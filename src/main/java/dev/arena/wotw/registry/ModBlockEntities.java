package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.MartianTurretBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/** Block entities: so far only the tall Martian turret, which needs somewhere to keep its timer. */
public final class ModBlockEntities {

    public static final BlockEntityType<MartianTurretBlockEntity> MARTIAN_TURRET =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, WotwMod.id("martian_turret"),
                    FabricBlockEntityTypeBuilder.create(MartianTurretBlockEntity::new, ModBlocks.MARTIAN_TURRET)
                            .build());

    private ModBlockEntities() {
    }

    public static void register() {
        WotwMod.LOGGER.debug("Registered block entity {}", Registries.BLOCK_ENTITY_TYPE.getId(MARTIAN_TURRET));
    }
}
