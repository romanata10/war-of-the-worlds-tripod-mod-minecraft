package dev.arena.wotw.client.render;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.client.model.GenericTripodModel;
import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

/**
 * Draws any tripod: the model comes from its own model layer, the texture from the type and the
 * render scale turns the 16-pixel unit model into a 6-12 block tall war machine.
 */
public class GenericTripodRenderer<T extends AbstractTripodEntity>
        extends MobRenderer<T, GenericTripodModel<T>> {

    private final ResourceLocation texture;
    private final float renderScale;

    public GenericTripodRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer,
                                 String texturePath, float renderScale, float shadowRadius) {
        super(context, new GenericTripodModel<>(context.bakeLayer(layer)), shadowRadius);
        this.texture = WotwMod.id("textures/entity/" + texturePath + ".png");
        this.renderScale = renderScale;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return this.texture;
    }

    @Override
    public void scale(T entity, PoseStack matrices, float amount) {
        matrices.scale(this.renderScale, this.renderScale, this.renderScale);
    }
}
