package dev.arena.wotw.block;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.registry.ModBlockEntities;
import dev.arena.wotw.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A crate with things in it: 27 slots, the same size as a chest, and random loot the first time
 * somebody looks inside.
 *
 * <p>Two ways to fill them, both are vanilla behaviour and both are wired here:</p>
 * <ul>
 *     <li>a crate that arrives with a structure carries a {@code LootTable} tag in the NBT of the
 *     template, exactly like a village chest;</li>
 *     <li>a crate the player places by hand gets the default table of its own kind
 *     ({@code wotw:crates/martian}, {@code .../human}, {@code .../military}), so it is never an
 *     empty box.</li>
 * </ul>
 */
public class CrateBlockEntity extends RandomizableContainerBlockEntity {

    /** Same as a chest: three rows of nine. */
    public static final int SLOTS = 27;

    private NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);

    public CrateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRATE, pos, state);
    }

    /**
     * Gives a freshly placed crate the loot table of its kind.
     *
     * <p>Called from {@link CrateBlock#newBlockEntity}; a template that already carries its own
     * table overwrites this when the block entity is loaded.</p>
     */
    public void useDefaultLoot(Block block) {
        if (this.lootTable != null) {
            return;
        }
        if (block == ModBlocks.MARTIAN_CRATE) {
            this.lootTable = WotwMod.id("crates/martian");
        } else if (block == ModBlocks.HUMAN_CRATE) {
            this.lootTable = WotwMod.id("crates/human");
        } else if (block == ModBlocks.MILITARY_CRATE) {
            this.lootTable = WotwMod.id("crates/military");
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getContainerSize() {
        return SLOTS;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.wotw.crate");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return ChestMenu.threeRows(containerId, inventory, this);
    }

    @Override
    public void startOpen(Player player) {
        if (player.isSpectator()) {
            return;
        }
        if (this.getLevel() instanceof ServerLevel) {
            // fills the slots from the loot table the first time the lid comes up
            this.unpackLootTable(player);
        }
        this.playCrateSound(this.getOpenSound());
    }

    @Override
    public void stopOpen(Player player) {
        if (player.isSpectator()) {
            return;
        }
        this.playCrateSound(this.getCloseSound());
    }

    private void playCrateSound(SoundEvent sound) {
        if (this.level == null) {
            return;
        }
        this.level.playSound(null, this.getBlockPos(), sound, SoundSource.BLOCKS, 0.8F,
                this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    private SoundEvent getOpenSound() {
        return this.getBlockState().is(ModBlocks.MILITARY_CRATE)
                ? SoundEvents.IRON_DOOR_OPEN
                : SoundEvents.BARREL_OPEN;
    }

    private SoundEvent getCloseSound() {
        return this.getBlockState().is(ModBlocks.MILITARY_CRATE)
                ? SoundEvents.IRON_DOOR_CLOSE
                : SoundEvents.BARREL_CLOSE;
    }
}
