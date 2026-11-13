package dev.arena.wotw.client.model;

import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;

/**
 * The human army: soldiers, tanks, interceptors, warships, gunships, submarines and bombers.
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
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 13, 4)
                        .addBox(-2, 0, -2, 4, 3, 4)
                        .addBox(-2, 12, -3, 4, 4, 6)
                        .addBox(-2, 14, -4, 4, 2, 3),
                PartPose.offsetAndRotation(-3, 8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 13, 4)
                        .addBox(-2, 0, -2, 4, 3, 4)
                        .addBox(-2, 12, -3, 4, 4, 6)
                        .addBox(-2, 14, -4, 4, 2, 3),
                PartPose.offsetAndRotation(3, 8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -14, -3, 10, 14, 6)
                        .addBox(-6, -11, -3, 12, 9, 7)
                        .addBox(-5, -14, 3, 10, 12, 5)
                        .addBox(-4, -15, -3, 8, 2, 6),
                PartPose.offsetAndRotation(0, 8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -9, -4, 8, 9, 8)
                        .addBox(-5, -11, -5, 10, 3, 10)
                        .addBox(-5, -8, -6, 10, 2, 2),
                PartPose.offsetAndRotation(0, -14, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -1, -1, 6, 2, 2),
                PartPose.offsetAndRotation(0, -5, -5, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 10, 4)
                        .addBox(-1, 8, -2, 3, 3, 12)
                        .addBox(-1, 10, -1, 2, 2, 4)
                        .addBox(-1, 8, 9, 2, 2, 4),
                PartPose.offsetAndRotation(6, -12, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 10, 4)
                        .addBox(-2, 8, -1, 3, 3, 6),
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

    public static LayerDefinition helicopter() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, -18, -17, 7, 7, 11)
                        .addBox(-7, -18, -6, 7, 7, 11)
                        .addBox(-7, -18, 6, 7, 7, 11)
                        .addBox(-7, -11, -17, 7, 7, 11)
                        .addBox(-7, -11, -6, 7, 7, 11)
                        .addBox(-7, -11, 6, 7, 7, 11)
                        .addBox(0, -18, -17, 7, 7, 11)
                        .addBox(0, -18, -6, 7, 7, 11)
                        .addBox(0, -18, 6, 7, 7, 11)
                        .addBox(0, -11, -17, 7, 7, 11)
                        .addBox(0, -11, -6, 7, 7, 11)
                        .addBox(0, -11, 6, 7, 7, 11)
                        .addBox(-5, -20, -24, 5, 8, 6)
                        .addBox(-5, -20, -18, 5, 8, 6)
                        .addBox(0, -20, -24, 5, 8, 6)
                        .addBox(0, -20, -18, 5, 8, 6)
                        .addBox(-3, -14, 6, 6, 6, 6)
                        .addBox(-3, -14, 12, 6, 6, 6)
                        .addBox(-3, -14, 18, 6, 6, 6)
                        .addBox(-3, -14, 24, 6, 6, 6),
                PartPose.offsetAndRotation(0, 26, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -14, -2, 2, 16, 6)
                        .addBox(-8, -2, -1, 16, 2, 6),
                PartPose.offsetAndRotation(0, -12, 40, 0.0F, 0.0F, 0.0F));

        PartDefinition rotor = body.addOrReplaceChild("rotor",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -1, -36, 2, 2, 72)
                        .addBox(-36, -1, -1, 72, 2, 2)
                        .addBox(-4, -4, -4, 8, 5, 8),
                PartPose.offsetAndRotation(0, -24, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 6, 2)
                        .addBox(-1, 5, -9, 2, 2, 10),
                PartPose.offsetAndRotation(-8, -10, -4, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 6, 2)
                        .addBox(-1, 5, -9, 2, 2, 10),
                PartPose.offsetAndRotation(8, -10, -4, 0.0F, 0.0F, 0.0F));

        PartDefinition leg_l = body.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -12, 2, 2, 26)
                        .addBox(-1, -5, -7, 2, 5, 2)
                        .addBox(-1, -5, 6, 2, 5, 2),
                PartPose.offsetAndRotation(-6, -4, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition leg_r = body.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -12, 2, 2, 26)
                        .addBox(-1, -5, -7, 2, 5, 2)
                        .addBox(-1, -5, 6, 2, 5, 2),
                PartPose.offsetAndRotation(6, -4, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-4, -2, -1, 7, 3, 2),
                PartPose.offsetAndRotation(0, -14, -25, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition submarine() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -14, -38, 16, 14, 15)
                        .addBox(-8, -14, -23, 16, 14, 15)
                        .addBox(-8, -14, -8, 16, 14, 15)
                        .addBox(-8, -14, 8, 16, 14, 15)
                        .addBox(-8, -14, 23, 16, 14, 15)
                        .addBox(-5, -14, -46, 5, 4, 6)
                        .addBox(-5, -14, -40, 5, 4, 6)
                        .addBox(-5, -10, -46, 5, 4, 6)
                        .addBox(-5, -10, -40, 5, 4, 6)
                        .addBox(0, -14, -46, 5, 4, 6)
                        .addBox(0, -14, -40, 5, 4, 6)
                        .addBox(0, -10, -46, 5, 4, 6)
                        .addBox(0, -10, -40, 5, 4, 6),
                PartPose.offsetAndRotation(0, 24, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -14, -9, 10, 14, 18)
                        .addBox(-1, -23, -1, 2, 9, 2)
                        .addBox(-4, -10, -10, 8, 3, 1),
                PartPose.offsetAndRotation(0, -14, -4, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -9, 0, 2, 18, 2)
                        .addBox(-9, -1, 0, 18, 2, 2)
                        .addBox(-2, -2, -2, 4, 4, 5),
                PartPose.offsetAndRotation(0, -10, 38, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_l = body.addOrReplaceChild("wing_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -1, -7, -5, 2, 16),
                PartPose.offsetAndRotation(-7, -10, 28, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_r = body.addOrReplaceChild("wing_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -1, -7, 5, 2, 16),
                PartPose.offsetAndRotation(7, -10, 28, 0.0F, 0.0F, 0.0F));

        PartDefinition leg_l = body.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -1, -7, 2, 2, 9),
                PartPose.offsetAndRotation(-5, -9, -38, 0.0F, 0.0F, 0.0F));

        PartDefinition leg_r = body.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -1, -7, 2, 2, 9),
                PartPose.offsetAndRotation(5, -9, -38, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -2, -1, 5, 3, 2),
                PartPose.offsetAndRotation(0, -12, -42, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition bomber() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -22, -43, 16, 8, 14)
                        .addBox(-8, -22, -29, 16, 8, 14)
                        .addBox(-8, -22, -14, 16, 8, 14)
                        .addBox(-8, -22, 0, 16, 8, 14)
                        .addBox(-8, -22, 14, 16, 8, 14)
                        .addBox(-8, -22, 29, 16, 8, 14)
                        .addBox(-8, -14, -43, 16, 8, 14)
                        .addBox(-8, -14, -29, 16, 8, 14)
                        .addBox(-8, -14, -14, 16, 8, 14)
                        .addBox(-8, -14, 0, 16, 8, 14)
                        .addBox(-8, -14, 14, 16, 8, 14)
                        .addBox(-8, -14, 29, 16, 8, 14)
                        .addBox(-5, -22, -50, 5, 6, 6)
                        .addBox(-5, -22, -44, 5, 6, 6)
                        .addBox(-5, -16, -50, 5, 6, 6)
                        .addBox(-5, -16, -44, 5, 6, 6)
                        .addBox(0, -22, -50, 5, 6, 6)
                        .addBox(0, -22, -44, 5, 6, 6)
                        .addBox(0, -16, -50, 5, 6, 6)
                        .addBox(0, -16, -44, 5, 6, 6)
                        .addBox(-3, -20, 37, 6, 6, 5)
                        .addBox(-3, -20, 42, 6, 6, 5)
                        .addBox(-3, -20, 46, 6, 6, 5),
                PartPose.offsetAndRotation(0, 27, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_l = body.addOrReplaceChild("wing_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -2, -15, -48, 4, 28)
                        .addBox(-20, -7, -10, -6, 8, 16)
                        .addBox(-34, -7, -8, -6, 8, 14)
                        .addBox(-44, -2, -8, -5, 4, 14),
                PartPose.offsetAndRotation(-8, -16, -4, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_r = body.addOrReplaceChild("wing_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -2, -15, 48, 4, 28)
                        .addBox(20, -7, -10, 6, 8, 16)
                        .addBox(34, -7, -8, 6, 8, 14)
                        .addBox(44, -2, -8, 5, 4, 14),
                PartPose.offsetAndRotation(8, -16, -4, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -16, -2, 2, 18, 7)
                        .addBox(-13, -3, -1, 26, 3, 9)
                        .addBox(-1, -16, 5, 2, 2, 2),
                PartPose.offsetAndRotation(0, -20, 48, 0.0F, 0.0F, 0.0F));

        PartDefinition bay = body.addOrReplaceChild("bay",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -14, 8, 3, 28),
                PartPose.offsetAndRotation(0, -6, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-4, -2, -1, 7, 3, 2),
                PartPose.offsetAndRotation(0, -20, -45, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition apc() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-14, -26, -24, 28, 12, 48)
                        .addBox(-12, -32, -18, 24, 6, 30)
                        .addBox(-13, -20, -26, 26, 4, 4)
                        .addBox(-14, -14, -22, 28, 4, 44),
                PartPose.offsetAndRotation(0, 27, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition wheels_l = body.addOrReplaceChild("wheels_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -5, -23, 4, 10, 10)
                        .addBox(-2, -2, -20, 4, 4, 4)
                        .addBox(-2, -5, -12, 4, 10, 10)
                        .addBox(-2, -2, -9, 4, 4, 4)
                        .addBox(-2, -5, 3, 4, 10, 10)
                        .addBox(-2, -2, 6, 4, 4, 4)
                        .addBox(-2, -5, 14, 4, 10, 10)
                        .addBox(-2, -2, 17, 4, 4, 4),
                PartPose.offsetAndRotation(-15, -8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition wheels_r = body.addOrReplaceChild("wheels_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -5, -23, 4, 10, 10)
                        .addBox(-2, -2, -20, 4, 4, 4)
                        .addBox(-2, -5, -12, 4, 10, 10)
                        .addBox(-2, -2, -9, 4, 4, 4)
                        .addBox(-2, -5, 3, 4, 10, 10)
                        .addBox(-2, -2, 6, 4, 4, 4)
                        .addBox(-2, -5, 14, 4, 10, 10)
                        .addBox(-2, -2, 17, 4, 4, 4),
                PartPose.offsetAndRotation(15, -8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition turret = body.addOrReplaceChild("turret",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -8, -9, 16, 8, 18)
                        .addBox(-2, -5, -26, 4, 4, 18)
                        .addBox(-3, -6, -28, 6, 6, 3)
                        .addBox(-5, -6, 6, 10, 3, 2),
                PartPose.offsetAndRotation(0, -32, -4, 0.0F, 0.0F, 0.0F));

        PartDefinition lamp_l = body.addOrReplaceChild("lamp_l",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-2, -2, -1, 4, 3, 2),
                PartPose.offsetAndRotation(-12, -22, -24, 0.0F, 0.0F, 0.0F));

        PartDefinition lamp_r = body.addOrReplaceChild("lamp_r",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-2, -2, -1, 4, 3, 2),
                PartPose.offsetAndRotation(8, -22, -24, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition artillery() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, -12, -10, 14, 6, 22)
                        .addBox(-9, -20, 2, 4, 10, 18)
                        .addBox(5, -20, 2, 4, 10, 18)
                        .addBox(-1, -18, 18, 2, 8, 6),
                PartPose.offsetAndRotation(0, 24, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition wheel_l = body.addOrReplaceChild("wheel_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -8, -8, 4, 16, 16)
                        .addBox(-2, -4, -4, 4, 8, 8)
                        .addBox(-2, -7, -8, 4, 1, 16)
                        .addBox(-2, -3, -8, 4, 1, 16)
                        .addBox(-2, 1, -8, 4, 1, 16)
                        .addBox(-2, 5, -8, 4, 1, 16),
                PartPose.offsetAndRotation(-10, -8, -2, 0.0F, 0.0F, 0.0F));

        PartDefinition wheel_r = body.addOrReplaceChild("wheel_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -8, -8, 4, 16, 16)
                        .addBox(-2, -4, -4, 4, 8, 8)
                        .addBox(-2, -7, -8, 4, 1, 16)
                        .addBox(-2, -3, -8, 4, 1, 16)
                        .addBox(-2, 1, -8, 4, 1, 16)
                        .addBox(-2, 5, -8, 4, 1, 16),
                PartPose.offsetAndRotation(10, -8, -2, 0.0F, 0.0F, 0.0F));

        PartDefinition shield = body.addOrReplaceChild("shield",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-11, -8, 0, 22, 16, 3)
                        .addBox(-6, -4, -1, 12, 3, 2),
                PartPose.offsetAndRotation(0, -18, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition barrel = body.addOrReplaceChild("barrel",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, -3, -34, 6, 6, 34)
                        .addBox(-4, -4, -38, 8, 8, 6)
                        .addBox(-5, -5, -40, 10, 10, 3)
                        .addBox(-2, -2, -41, 4, 4, 2),
                PartPose.offsetAndRotation(0, -16, -6, -0.4189F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition jet() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -18, -29, 5, 5, 8)
                        .addBox(-5, -18, -21, 5, 5, 8)
                        .addBox(-5, -18, -12, 5, 5, 8)
                        .addBox(-5, -18, -4, 5, 5, 8)
                        .addBox(-5, -18, 4, 5, 5, 8)
                        .addBox(-5, -18, 12, 5, 5, 8)
                        .addBox(-5, -18, 21, 5, 5, 8)
                        .addBox(-5, -13, -29, 5, 5, 8)
                        .addBox(-5, -13, -21, 5, 5, 8)
                        .addBox(-5, -13, -12, 5, 5, 8)
                        .addBox(-5, -13, -4, 5, 5, 8)
                        .addBox(-5, -13, 4, 5, 5, 8)
                        .addBox(-5, -13, 12, 5, 5, 8)
                        .addBox(-5, -13, 21, 5, 5, 8)
                        .addBox(0, -18, -29, 5, 5, 8)
                        .addBox(0, -18, -21, 5, 5, 8)
                        .addBox(0, -18, -12, 5, 5, 8)
                        .addBox(0, -18, -4, 5, 5, 8)
                        .addBox(0, -18, 4, 5, 5, 8)
                        .addBox(0, -18, 12, 5, 5, 8)
                        .addBox(0, -18, 21, 5, 5, 8)
                        .addBox(0, -13, -29, 5, 5, 8)
                        .addBox(0, -13, -21, 5, 5, 8)
                        .addBox(0, -13, -12, 5, 5, 8)
                        .addBox(0, -13, -4, 5, 5, 8)
                        .addBox(0, -13, 4, 5, 5, 8)
                        .addBox(0, -13, 12, 5, 5, 8)
                        .addBox(0, -13, 21, 5, 5, 8)
                        .addBox(-3, -22, -28, 6, 6, 14)
                        .addBox(-1, -21, -32, 2, 3, 6)
                        .addBox(-6, -14, 22, 12, 8, 6),
                PartPose.offsetAndRotation(0, 30, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_l = body.addOrReplaceChild("wing_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -2, -14, -30, 3, 26)
                        .addBox(-12, -2, 10, -14, 3, 10)
                        .addBox(-22, -3, -6, 5, 5, 5)
                        .addBox(-16, -4, -2, 3, 3, 14),
                PartPose.offsetAndRotation(-4, -14, 2, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_r = body.addOrReplaceChild("wing_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -2, -14, 30, 3, 26)
                        .addBox(12, -2, 10, 14, 3, 10)
                        .addBox(22, -3, -6, 5, 5, 5)
                        .addBox(16, -4, -2, 3, 3, 14),
                PartPose.offsetAndRotation(4, -14, 2, 0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -20, -2, 2, 20, 8)
                        .addBox(-10, -6, 0, 20, 3, 6),
                PartPose.offsetAndRotation(0, -18, 22, 0.0F, 0.0F, 0.0F));

        PartDefinition canopy = body.addOrReplaceChild("canopy",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -2, -1, 6, 4, 2),
                PartPose.offsetAndRotation(0, -20, -18, 0.0F, 0.0F, 0.0F));

        PartDefinition exhaust = body.addOrReplaceChild("exhaust",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-4, -3, 0, 8, 6, 4),
                PartPose.offsetAndRotation(0, -10, 26, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition sniper() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 12, 4)
                        .addBox(-2, 11, -3, 4, 4, 6),
                PartPose.offsetAndRotation(-3, 9, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 12, 4)
                        .addBox(-2, 11, -3, 4, 4, 6)
                        .addBox(-3, 6, -5, 6, 5, 10),
                PartPose.offsetAndRotation(3, 9, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -14, -3, 10, 14, 6)
                        .addBox(-6, -13, 3, 12, 11, 4)
                        .addBox(-7, -4, -4, 14, 4, 8),
                PartPose.offsetAndRotation(0, 9, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -8, -4, 8, 8, 8)
                        .addBox(-5, -11, -5, 10, 4, 10)
                        .addBox(-6, -8, -6, 12, 3, 2),
                PartPose.offsetAndRotation(0, -14, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition scope = head.addOrReplaceChild("scope",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-2, -1, -1, 4, 2, 2),
                PartPose.offsetAndRotation(0, -5, -6, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 9, 4)
                        .addBox(-1, 7, -12, 3, 3, 26)
                        .addBox(-1, 6, -4, 3, 5, 6)
                        .addBox(-1, 9, -14, 3, 3, 6)
                        .addBox(-1, 8, -20, 2, 2, 6),
                PartPose.offsetAndRotation(6, -12, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 9, 4)
                        .addBox(-2, 7, -8, 4, 3, 8),
                PartPose.offsetAndRotation(-6, -12, 0, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition medic() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 13, 4)
                        .addBox(-2, 12, -3, 4, 4, 6),
                PartPose.offsetAndRotation(-3, 8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 13, 4)
                        .addBox(-2, 12, -3, 4, 4, 6),
                PartPose.offsetAndRotation(3, 8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -14, -3, 10, 14, 6)
                        .addBox(-6, -12, -3, 12, 8, 7)
                        .addBox(-5, -13, 3, 10, 10, 5)
                        .addBox(-4, -9, 3, 8, 3, 2)
                        .addBox(-2, -11, 3, 4, 7, 2),
                PartPose.offsetAndRotation(0, 8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -9, -4, 8, 9, 8)
                        .addBox(-5, -11, -5, 10, 3, 10)
                        .addBox(-1, -12, -6, 2, 4, 2)
                        .addBox(-3, -11, -6, 6, 2, 2),
                PartPose.offsetAndRotation(0, -14, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -1, -1, 5, 2, 2),
                PartPose.offsetAndRotation(0, -5, -5, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 10, 4)
                        .addBox(-1, 8, -6, 3, 3, 8),
                PartPose.offsetAndRotation(6, -12, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 10, 4)
                        .addBox(-3, 8, -4, 5, 6, 5)
                        .addBox(-2, 9, -3, 3, 4, 3),
                PartPose.offsetAndRotation(-6, -12, 0, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

}
