package dev.arena.wotw.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Marks a player who has been stuffed into the cage of a Handling Machine.
 *
 * <p>The effect does nothing on its own — it is a tag. Every tripod in the mod ignores players who
 * carry it (they are already caught, after all), and the Handling Machine keeps the prisoner
 * teleported inside its cage and drains five hit points every two seconds while it lasts.</p>
 */
public class CagedEffect extends MobEffect {

    public CagedEffect() {
        super(MobEffectCategory.HARMFUL, 0xB03A6E);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
