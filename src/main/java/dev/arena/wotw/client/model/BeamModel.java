package dev.arena.wotw.client.model;

import dev.arena.wotw.entity.projectile.BeamEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

/**
 * A single elongated glowing cuboid. Its long axis is local -Z (the "forward" of every entity
 * model), the renderer stretches it along the flight direction of the ray.
 */
public class BeamModel extends EntityModel<BeamEntity> {

    private final ModelPart beam;

    public BeamModel(ModelPart root) {
        this.beam = root.getChild("beam");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition data = new MeshDefinition();
        data.getRoot().addOrReplaceChild("beam", CubeListBuilder.create()
                        .addBox(-4, -4, -8, 8, 8, 16),
                PartPose.ZERO);
        return LayerDefinition.create(data, 64, 64);
    }

    @Override
    public void setupAnim(BeamEntity entity, float limbAngle, float limbDistance, float animationProgress,
                          float yHeadRot, float headPitch) {
        // Beams do not animate, they only glow.
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        this.beam.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
