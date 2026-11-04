package dev.arena.wotw.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.arena.wotw.WotwMod;
import dev.arena.wotw.registry.ModSpawns;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Puts the Martian spawn table into a biome.
 *
 * <p>Forge 1.20.1 has no runtime "add a spawn to every biome" hook any more: the decision lives in a
 * datapack file ({@code data/wotw/forge/biome_modifier/natural_spawns.json}) and this class is the
 * {@code type} of that file. The table itself stays in {@link ModSpawns}, so the weights and the
 * spawn rules can never drift apart.</p>
 */
public record NaturalSpawnsBiomeModifier(HolderSet<Biome> biomes) implements BiomeModifier {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, WotwMod.MOD_ID);

    public static final RegistryObject<Codec<NaturalSpawnsBiomeModifier>> CODEC = SERIALIZERS.register(
            "natural_spawns",
            () -> RecordCodecBuilder.create(instance -> instance.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(NaturalSpawnsBiomeModifier::biomes)
            ).apply(instance, NaturalSpawnsBiomeModifier::new)));

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && this.biomes.contains(biome)) {
            ModSpawns.addSpawners(builder);
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC.get();
    }
}
