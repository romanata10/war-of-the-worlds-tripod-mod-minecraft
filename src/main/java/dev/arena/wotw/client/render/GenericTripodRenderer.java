package dev.arena.wotw.client.render;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.client.model.GenericTripodModel;
import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

/**
 * Draws any tripod: the model comes from its own model layer, the texture from the type and the
 * render scale turns the 16-pixel unit model into a 6-12 block tall war machine.
 */
public class GenericTripodRenderer<T extends AbstractTripodEntity>
        extends MobRenderer<T, GenericTripodModel<T>> {

    /** How far the culling box is grown around the hitbox, in blocks. */
    private static final double CULLING_PADDING = 14.0D;

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

    /**
     * The hitbox of a tripod is a narrow column under a body that is much wider and taller than it.
     * With the vanilla check the machine vanished the moment that thin column left the frustum,
     * which read as a model that flickers in and out while walking around it, so the test uses a
     * box inflated to the size of the drawn machine instead.
     */
    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        if (!entity.shouldRender(x, y, z)) {
            return false;
        }
        AABB box = entity.getBoundingBox().inflate(CULLING_PADDING * this.renderScale);
        return frustum.isVisible(box);
    }

    @Override
    public void scale(T entity, PoseStack matrices, float amount) {
        matrices.scale(this.renderScale, this.renderScale, this.renderScale);
    }
}
