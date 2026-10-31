package dev.arena.wotw.client.render;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.client.model.GenericHumanModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;

/** Draws the human army: soldiers, tanks, fighters and warships. */
public class GenericHumanRenderer<T extends PathAwareEntity>
        extends MobEntityRenderer<T, GenericHumanModel<T>> {

    private final Identifier texture;
    private final float renderScale;

    public GenericHumanRenderer(EntityRendererFactory.Context context, EntityModelLayer layer,
                                String texturePath, float renderScale, float shadowRadius) {
        super(context, new GenericHumanModel<>(context.getPart(layer)), shadowRadius);
        this.texture = WotwMod.id("textures/entity/" + texturePath + ".png");
        this.renderScale = renderScale;
    }

    @Override
    public Identifier getTexture(T entity) {
        return this.texture;
    }

    @Override
    public void scale(T entity, MatrixStack matrices, float amount) {
        matrices.scale(this.renderScale, this.renderScale, this.renderScale);
    }
}
