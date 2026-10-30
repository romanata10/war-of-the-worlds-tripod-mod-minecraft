package dev.arena.wotw.client.model;

import dev.arena.wotw.entity.projectile.BeamEntity;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A single elongated glowing cuboid. Its long axis is local -Z (the "forward" of every entity
 * model), the renderer stretches it along the flight direction of the ray.
 */
public class BeamModel extends EntityModel<BeamEntity> {

    private final ModelPart beam;

    public BeamModel(ModelPart root) {
        this.beam = root.getChild("beam");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        data.getRoot().addChild("beam", ModelPartBuilder.create()
                        .cuboid(-4, -4, -8, 8, 8, 16),
                ModelTransform.NONE);
        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void setAngles(BeamEntity entity, float limbAngle, float limbDistance, float animationProgress,
                          float headYaw, float headPitch) {
        // Beams do not animate, they only glow.
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        this.beam.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
