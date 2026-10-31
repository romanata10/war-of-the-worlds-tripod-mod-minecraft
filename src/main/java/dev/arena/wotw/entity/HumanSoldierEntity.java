package dev.arena.wotw.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

/**
 * A conscript of the human resistance: a rifle, a helmet and a grudge.
 *
 * <p>It shoots bullets (see {@link dev.arena.wotw.entity.projectile.ShellEntity}) at any Martian
 * machine it can see and never fires at the player unless the player shoots first.</p>
 */
public class HumanSoldierEntity extends AbstractHumanCombatantEntity {

    public HumanSoldierEntity(EntityType<? extends HumanSoldierEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return humanAttributes(24.0D, 0.30D, 3.0D, 4.0D, 0.0D, 28.0D);
    }
}
