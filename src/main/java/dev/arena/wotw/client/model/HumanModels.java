package dev.arena.wotw.client.model;

import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;

/**
 * The human army: soldiers, tanks, interceptors and warships.
 *
 * <p><b>Generated file.</b> The geometry lives in `tools/generate_models.py` (see
 * `tools/model_lib.py`); edit that and run `python3 tools/generate_models.py`.
 * Model pixels: 16 per block, +y downwards, feet at y = 24, the machine looks towards
 * (0, +y down). Lenses sample the glowing patch of the texture at (96, 96).</p>
 */
public final class HumanModels {


    public static LayerDefinition soldier() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 16, 4)
                        .addBox(-2, 15, -3, 4, 3, 6),
                PartPose.offsetAndRotation(-3, 6, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 16, 4)
                        .addBox(-2, 15, -3, 4, 3, 6),
                PartPose.offsetAndRotation(3, 6, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -14, -3, 10, 14, 6)
                        .addBox(-6, -13, -3, 12, 4, 7),
                PartPose.offsetAndRotation(0, 6, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -9, -4, 8, 9, 8)
                        .addBox(-5, -10, -5, 10, 4, 10),
                PartPose.offsetAndRotation(0, -14, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -1, -1, 5, 2, 2),
                PartPose.offsetAndRotation(0, -5, -5, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 11, 4)
                        .addBox(-1, 8, -10, 3, 3, 14)
                        .addBox(-2, 7, -2, 4, 5, 5),
                PartPose.offsetAndRotation(6, -12, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 11, 4),
                PartPose.offsetAndRotation(-6, -12, 0, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition tank() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-16, -30, -26, 32, 14, 52)
                        .addBox(-22, -20, -28, 5, 20, 56)
                        .addBox(17, -20, -28, 5, 20, 56)
                        .addBox(-16, -34, 6, 32, 4, 18),
                PartPose.offsetAndRotation(0, 24, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition turret = body.addOrReplaceChild("turret",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-9, -11, -11, 18, 11, 22)
                        .addBox(-2, -8, -32, 4, 4, 22)
                        .addBox(-4, -9, -34, 8, 6, 4)
                        .addBox(-1, -7, -35, 2, 2, 2),
                PartPose.offsetAndRotation(0, -30, -2, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition fighter() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -16, -31, 6, 6, 9)
                        .addBox(-6, -16, -22, 6, 6, 9)
                        .addBox(-6, -16, -13, 6, 6, 9)
                        .addBox(-6, -16, -4, 6, 6, 9)
                        .addBox(-6, -16, 4, 6, 6, 9)
                        .addBox(-6, -16, 13, 6, 6, 9)
                        .addBox(-6, -16, 22, 6, 6, 9)
                        .addBox(-6, -10, -31, 6, 6, 9)
                        .addBox(-6, -10, -22, 6, 6, 9)
                        .addBox(-6, -10, -13, 6, 6, 9)
                        .addBox(-6, -10, -4, 6, 6, 9)
                        .addBox(-6, -10, 4, 6, 6, 9)
                        .addBox(-6, -10, 13, 6, 6, 9)
                        .addBox(-6, -10, 22, 6, 6, 9)
                        .addBox(0, -16, -31, 6, 6, 9)
                        .addBox(0, -16, -22, 6, 6, 9)
                        .addBox(0, -16, -13, 6, 6, 9)
                        .addBox(0, -16, -4, 6, 6, 9)
                        .addBox(0, -16, 4, 6, 6, 9)
                        .addBox(0, -16, 13, 6, 6, 9)
                        .addBox(0, -16, 22, 6, 6, 9)
                        .addBox(0, -10, -31, 6, 6, 9)
                        .addBox(0, -10, -22, 6, 6, 9)
                        .addBox(0, -10, -13, 6, 6, 9)
                        .addBox(0, -10, -4, 6, 6, 9)
                        .addBox(0, -10, 4, 6, 6, 9)
                        .addBox(0, -10, 13, 6, 6, 9)
                        .addBox(0, -10, 22, 6, 6, 9)
                        .addBox(-4, -20, -3, 8, 6, 7)
                        .addBox(-4, -20, 4, 8, 6, 7)
                        .addBox(-4, -20, 12, 8, 6, 7),
                PartPose.offsetAndRotation(0, 28, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_l = body.addOrReplaceChild("wing_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -2, -8, -34, 4, 16)
                        .addBox(0, -1, 8, -20, 3, 8)
                        .addBox(-26, -2, -4, 6, 4, 6),
                PartPose.offsetAndRotation(-5, -12, -4, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_r = body.addOrReplaceChild("wing_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -2, -8, 34, 4, 16)
                        .addBox(0, -1, 8, 20, 3, 8)
                        .addBox(26, -2, -4, 6, 4, 6),
                PartPose.offsetAndRotation(5, -12, -4, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -20, -2, 2, 20, 6)
                        .addBox(-6, -6, 2, 12, 5, 8),
                PartPose.offsetAndRotation(0, -16, 26, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -2, -1, 6, 4, 2),
                PartPose.offsetAndRotation(0, -12, -31, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition warship() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-17, -22, -50, 11, 11, 14)
                        .addBox(-17, -22, -36, 11, 11, 14)
                        .addBox(-17, -22, -21, 11, 11, 14)
                        .addBox(-17, -22, -7, 11, 11, 14)
                        .addBox(-17, -22, 7, 11, 11, 14)
                        .addBox(-17, -22, 21, 11, 11, 14)
                        .addBox(-17, -22, 36, 11, 11, 14)
                        .addBox(-17, -11, -50, 11, 11, 14)
                        .addBox(-17, -11, -36, 11, 11, 14)
                        .addBox(-17, -11, -21, 11, 11, 14)
                        .addBox(-17, -11, -7, 11, 11, 14)
                        .addBox(-17, -11, 7, 11, 11, 14)
                        .addBox(-17, -11, 21, 11, 11, 14)
                        .addBox(-17, -11, 36, 11, 11, 14)
                        .addBox(-6, -22, -50, 11, 11, 14)
                        .addBox(-6, -22, -36, 11, 11, 14)
                        .addBox(-6, -22, -21, 11, 11, 14)
                        .addBox(-6, -22, -7, 11, 11, 14)
                        .addBox(-6, -22, 7, 11, 11, 14)
                        .addBox(-6, -22, 21, 11, 11, 14)
                        .addBox(-6, -22, 36, 11, 11, 14)
                        .addBox(-6, -11, -50, 11, 11, 14)
                        .addBox(-6, -11, -36, 11, 11, 14)
                        .addBox(-6, -11, -21, 11, 11, 14)
                        .addBox(-6, -11, -7, 11, 11, 14)
                        .addBox(-6, -11, 7, 11, 11, 14)
                        .addBox(-6, -11, 21, 11, 11, 14)
                        .addBox(-6, -11, 36, 11, 11, 14)
                        .addBox(6, -22, -50, 11, 11, 14)
                        .addBox(6, -22, -36, 11, 11, 14)
                        .addBox(6, -22, -21, 11, 11, 14)
                        .addBox(6, -22, -7, 11, 11, 14)
                        .addBox(6, -22, 7, 11, 11, 14)
                        .addBox(6, -22, 21, 11, 11, 14)
                        .addBox(6, -22, 36, 11, 11, 14)
                        .addBox(6, -11, -50, 11, 11, 14)
                        .addBox(6, -11, -36, 11, 11, 14)
                        .addBox(6, -11, -21, 11, 11, 14)
                        .addBox(6, -11, -7, 11, 11, 14)
                        .addBox(6, -11, 7, 11, 11, 14)
                        .addBox(6, -11, 21, 11, 11, 14)
                        .addBox(6, -11, 36, 11, 11, 14)
                        .addBox(-10, -28, -30, 10, 8, 12)
                        .addBox(-10, -28, -18, 10, 8, 12)
                        .addBox(-10, -28, -6, 10, 8, 12)
                        .addBox(-10, -28, 6, 10, 8, 12)
                        .addBox(-10, -28, 18, 10, 8, 12)
                        .addBox(0, -28, -30, 10, 8, 12)
                        .addBox(0, -28, -18, 10, 8, 12)
                        .addBox(0, -28, -6, 10, 8, 12)
                        .addBox(0, -28, 6, 10, 8, 12)
                        .addBox(0, -28, 18, 10, 8, 12),
                PartPose.offsetAndRotation(0, 24, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -14, -10, 16, 14, 20)
                        .addBox(-3, -24, -3, 6, 10, 6)
                        .addBox(-8, -10, -11, 16, 4, 2),
                PartPose.offsetAndRotation(0, -28, -6, 0.0F, 0.0F, 0.0F));

        PartDefinition turret = body.addOrReplaceChild("turret",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, -8, -7, 14, 8, 14)
                        .addBox(-2, -6, -28, 4, 4, 22)
                        .addBox(-2, -6, 20, 4, 4, 20),
                PartPose.offsetAndRotation(0, -22, -34, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -16, -1, 2, 16, 2)
                        .addBox(-1, -18, -1, 2, 2, 2),
                PartPose.offsetAndRotation(-12, -28, 6, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -16, -1, 2, 16, 2)
                        .addBox(-1, -18, -1, 2, 2, 2),
                PartPose.offsetAndRotation(12, -28, 6, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

}
