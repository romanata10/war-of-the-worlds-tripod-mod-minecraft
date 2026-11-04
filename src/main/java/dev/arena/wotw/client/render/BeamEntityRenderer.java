package dev.arena.wotw.client.render;

import dev.arena.wotw.client.ModModelLayers;
import dev.arena.wotw.client.model.BeamModel;
import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Axis;

/**
 * Renders the ray as an emissive, direction aligned cuboid segment. A new segment is spawned every
 * tick, so the chain of segments reads as one continuous beam.
 */
public class BeamEntityRenderer extends EntityRenderer<BeamEntity> {

    private static final int FULL_BRIGHT = 15728880;

    private final BeamModel model;

    public BeamEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BeamModel(context.bakeLayer(ModModelLayers.BEAM));
    }

    @Override
    public ResourceLocation getTextureLocation(BeamEntity entity) {
        return entity.getKind().getTexture();
    }

    @Override
    public void render(BeamEntity entity, float yaw, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light) {
        BeamKind kind = entity.getKind();
        float length = (float) (kind.getSpeed() * 1.8D);

        matrices.pushPose();
        // Point the model's -Z axis (the "forward" of every entity model) along the beam.
        matrices.mulPose(Axis.YP.rotationDegrees(180.0F - entity.getYRot()));
        matrices.mulPose(Axis.XP.rotationDegrees(-entity.getXRot()));
        matrices.scale(kind.getBbWidth(), kind.getBbWidth(), length);

        VertexConsumer consumer = vertexConsumers.getBuffer(RenderType.eyes(this.getTextureLocation(entity)));
        this.model.render(matrices, consumer, FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.popPose();
    }
}
