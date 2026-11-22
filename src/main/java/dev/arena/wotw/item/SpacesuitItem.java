package dev.arena.wotw.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * The spacesuit: the only thing that keeps a player alive on Mars.
 *
 * <p>It is a chest piece, because that is where the air tanks and the radio of a suit sit. Without
 * it the planet hurts, see {@code MarsVacuum}.</p>
 */
public class SpacesuitItem extends ArmorItem {

    public SpacesuitItem(Properties properties) {
        super(SUIT, ArmorItem.Type.CHESTPLATE, properties);
    }

    /** Glass, iron and a pinch of redstone: nothing Martian, so it can be built before the first trip. */
    public static final ArmorMaterial SUIT = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return 320;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return 5;
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT);
        }

        @Override
        public String getName() {
            return "wotw:spacesuit";
        }

        @Override
        public float getToughness() {
            return 0.5F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    };

    /** The suit is drawn from its own armour layer, not from the iron or leather one. */
    @Override
    @javax.annotation.Nullable
    public String getArmorTexture(ItemStack stack, net.minecraft.world.entity.Entity entity,
                                  EquipmentSlot slot, String type) {
        return "wotw:textures/models/armor/spacesuit_layer_1.png";
    }

    /** True when the player wears the suit, which is the whole life support system. */
    public static boolean isWorn(net.minecraft.world.entity.LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        return chest.getItem() instanceof SpacesuitItem;
    }
}
