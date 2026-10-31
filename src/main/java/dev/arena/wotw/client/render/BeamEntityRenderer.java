package dev.arena.wotw.client.render;

import dev.arena.wotw.client.ModModelLayers;
import dev.arena.wotw.client.model.BeamModel;
import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

/**
 * Renders the ray as an emissive, direction aligned cuboid segment. A new segment is spawned every
 * tick, so the chain of segments reads as one continuous beam.
 */
public class BeamEntityRenderer extends EntityRenderer<BeamEntity> {

    private static final int FULL_BRIGHT = 15728880;

    private final BeamModel model;

    public BeamEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new BeamModel(context.getPart(ModModelLayers.BEAM));
    }

    @Override
    public Identifier getTexture(BeamEntity entity) {
        return entity.getKind().getTexture();
    }

    @Override
    public void render(BeamEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        BeamKind kind = entity.getKind();
        float length = (float) (kind.getSpeed() * 1.8D);

        matrices.push();
        // Point the model's -Z axis (the "forward" of every entity model) along the beam.
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - entity.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-entity.getPitch()));
        matrices.scale(kind.getWidth(), kind.getWidth(), length);

        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEyes(this.getTexture(entity)));
        this.model.render(matrices, consumer, FULL_BRIGHT, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();
    }
}
