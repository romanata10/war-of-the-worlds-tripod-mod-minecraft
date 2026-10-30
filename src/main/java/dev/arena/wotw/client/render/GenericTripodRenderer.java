package dev.arena.wotw.client.render;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.client.model.GenericTripodModel;
import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * Draws any tripod: the model comes from its own model layer, the texture from the type and the
 * render scale turns the 16-pixel unit model into a 6-12 block tall war machine.
 */
public class GenericTripodRenderer<T extends AbstractTripodEntity>
        extends MobEntityRenderer<T, GenericTripodModel<T>> {

    private final Identifier texture;
    private final float renderScale;

    public GenericTripodRenderer(EntityRendererFactory.Context context, EntityModelLayer layer,
                                 String texturePath, float renderScale, float shadowRadius) {
        super(context, new GenericTripodModel<>(context.getPart(layer)), shadowRadius);
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
