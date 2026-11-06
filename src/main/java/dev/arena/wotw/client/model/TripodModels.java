package dev.arena.wotw.client.model;

import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;

/**
 * Hand tuned blocky geometry for the ten war machines, following the film references.
 *
 * <p><b>Generated file.</b> The geometry lives in `tools/generate_models.py` (see
 * `tools/model_lib.py`); edit that and run `python3 tools/generate_models.py`.
 * Model pixels: 16 per block, +y downwards, feet at y = 24, the machine looks towards
 * (0, +y down). Lenses sample the glowing patch of the texture at (96, 96).</p>
 */
public final class TripodModels {


    public static LayerDefinition tripod2005() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-14, -144, -16, 14, 17, 16)
                        .addBox(-14, -144, 0, 14, 17, 16)
                        .addBox(0, -144, -16, 14, 17, 16)
                        .addBox(0, -144, 0, 14, 17, 16)
                        .addBox(-19, -127, -22, 19, 22, 22)
                        .addBox(-19, -127, 0, 19, 22, 22)
                        .addBox(0, -127, -22, 19, 22, 22)
                        .addBox(0, -127, 0, 19, 22, 22)
                        .addBox(-22, -105, -25, 22, 22, 17)
                        .addBox(-22, -105, -8, 22, 22, 17)
                        .addBox(-22, -105, 8, 22, 22, 17)
                        .addBox(0, -105, -25, 22, 22, 17)
                        .addBox(0, -105, -8, 22, 22, 17)
                        .addBox(0, -105, 8, 22, 22, 17)
                        .addBox(-17, -83, -20, 17, 17, 20)
                        .addBox(-17, -83, 0, 17, 17, 20)
                        .addBox(0, -83, -20, 17, 17, 20)
                        .addBox(0, -83, 0, 17, 17, 20)
                        .addBox(-12, -66, -14, 24, 8, 14)
                        .addBox(-12, -66, 0, 24, 8, 14)
                        .addBox(-23, -130, -28, 15, 10, 10)
                        .addBox(-23, -130, -18, 15, 10, 10)
                        .addBox(-8, -130, -28, 15, 10, 10)
                        .addBox(-8, -130, -18, 15, 10, 10)
                        .addBox(8, -130, -28, 15, 10, 10)
                        .addBox(8, -130, -18, 15, 10, 10)
                        .addBox(-11, -66, -13, 11, 11, 9)
                        .addBox(-11, -66, -4, 11, 11, 9)
                        .addBox(-11, -66, 4, 11, 11, 9)
                        .addBox(0, -66, -13, 11, 11, 9)
                        .addBox(0, -66, -4, 11, 11, 9)
                        .addBox(0, -66, 4, 11, 11, 9),
                PartPose.offsetAndRotation(0, -12, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye_l = body.addOrReplaceChild("eye_l",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -6, -1, 11, 11, 2),
                PartPose.offsetAndRotation(-11, -116, -24, 0.0F, 0.0F, 0.0F));

        PartDefinition eye_r = body.addOrReplaceChild("eye_r",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -6, -1, 11, 11, 2),
                PartPose.offsetAndRotation(11, -116, -24, 0.0F, 0.0F, 0.0F));

        PartDefinition tent0_1_body = body.addOrReplaceChild("tent0_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(-12, -61, -8, 0.1396F, -0.0524F, 0.0F));

        PartDefinition tent0_2_body = tent0_1_body.addOrReplaceChild("tent0_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.2793F, -0.1047F, 0.0F));

        PartDefinition tent0_3_body = tent0_2_body.addOrReplaceChild("tent0_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.2793F, -0.1047F, 0.0F));

        PartDefinition tent1_1_body = body.addOrReplaceChild("tent1_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(12, -61, -8, 0.1396F, 0.0524F, 0.0F));

        PartDefinition tent1_2_body = tent1_1_body.addOrReplaceChild("tent1_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.2793F, 0.1047F, 0.0F));

        PartDefinition tent1_3_body = tent1_2_body.addOrReplaceChild("tent1_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.2793F, 0.1047F, 0.0F));

        PartDefinition tent2_1_body = body.addOrReplaceChild("tent2_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(-9, -61, 9, 0.1920F, -0.0873F, 0.0F));

        PartDefinition tent2_2_body = tent2_1_body.addOrReplaceChild("tent2_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.3840F, -0.1745F, 0.0F));

        PartDefinition tent2_3_body = tent2_2_body.addOrReplaceChild("tent2_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.3840F, -0.1745F, 0.0F));

        PartDefinition tent3_1_body = body.addOrReplaceChild("tent3_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(9, -61, 9, 0.1920F, 0.0873F, 0.0F));

        PartDefinition tent3_2_body = tent3_1_body.addOrReplaceChild("tent3_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.3840F, 0.1745F, 0.0F));

        PartDefinition tent3_3_body = tent3_2_body.addOrReplaceChild("tent3_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 15, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.3840F, 0.1745F, 0.0F));

        PartDefinition leg_fl = root.addOrReplaceChild("leg_fl",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 50, 4),
                PartPose.offsetAndRotation(-16, -75, -12, -0.1745F, 0.0F, 0.2443F));

        PartDefinition leg_fl_shin = leg_fl.addOrReplaceChild("leg_fl_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 46, 4)
                        .addBox(-3, 44, -3, 5, 4, 5),
                PartPose.offsetAndRotation(0, 50, 0, -0.0611F, 0.0F, 0.0855F));

        PartDefinition leg_fr = root.addOrReplaceChild("leg_fr",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 50, 4),
                PartPose.offsetAndRotation(16, -75, -12, -0.1745F, 0.0F, -0.2443F));

        PartDefinition leg_fr_shin = leg_fr.addOrReplaceChild("leg_fr_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 46, 4)
                        .addBox(-3, 44, -3, 5, 4, 5),
                PartPose.offsetAndRotation(0, 50, 0, -0.0611F, 0.0F, -0.0855F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 51, 4),
                PartPose.offsetAndRotation(0, -75, 18, 0.2094F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 47, 4)
                        .addBox(-3, 46, -3, 5, 4, 5),
                PartPose.offsetAndRotation(0, 51, 0, 0.0733F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition bbcWhite() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-10, -107, -11, 20, 13, 22)
                        .addBox(-14, -94, -15, 14, 16, 15)
                        .addBox(-14, -94, 0, 14, 16, 15)
                        .addBox(0, -94, -15, 14, 16, 15)
                        .addBox(0, -94, 0, 14, 16, 15)
                        .addBox(-12, -78, -13, 24, 13, 13)
                        .addBox(-12, -78, 0, 24, 13, 13)
                        .addBox(-8, -64, -9, 16, 8, 18)
                        .addBox(-5, -113, -5, 5, 7, 5)
                        .addBox(-5, -113, 0, 5, 7, 5)
                        .addBox(0, -113, -5, 5, 7, 5)
                        .addBox(0, -113, 0, 5, 7, 5)
                        .addBox(-9, -62, -10, 9, 8, 10)
                        .addBox(-9, -62, 0, 9, 8, 10)
                        .addBox(0, -62, -10, 9, 8, 10)
                        .addBox(0, -62, 0, 9, 8, 10),
                PartPose.offsetAndRotation(0, -11, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -7, -1, 9, 12, 2),
                PartPose.offsetAndRotation(0, -88, -16, 0.0F, 0.0F, 0.0F));

        PartDefinition arm0 = body.addOrReplaceChild("arm0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 3, 21, 3)
                        .addBox(-1, 20, -1, 3, 8, 3),
                PartPose.offsetAndRotation(-9, -59, -6, 0.1047F, 0.0F, -0.1414F));

        PartDefinition arm1 = body.addOrReplaceChild("arm1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 3, 21, 3)
                        .addBox(-1, 20, -1, 3, 8, 3),
                PartPose.offsetAndRotation(9, -59, -6, 0.1047F, 0.0F, 0.1414F));

        PartDefinition arm2 = body.addOrReplaceChild("arm2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 3, 21, 3)
                        .addBox(-1, 20, -1, 3, 8, 3),
                PartPose.offsetAndRotation(0, -59, 9, 0.1047F, 0.0F, 0.0F));

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 47, 3),
                PartPose.offsetAndRotation(-10, -70, 0, 0.0F, 0.0F, 0.2094F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 46, 3)
                        .addBox(-2, 44, -2, 4, 4, 4),
                PartPose.offsetAndRotation(0, 47, 0, 0.0F, 0.0F, 0.0733F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 47, 3),
                PartPose.offsetAndRotation(10, -70, 0, 0.0F, 0.0F, -0.2094F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 46, 3)
                        .addBox(-2, 44, -2, 4, 4, 4),
                PartPose.offsetAndRotation(0, 47, 0, 0.0F, 0.0F, -0.0733F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 47, 3),
                PartPose.offsetAndRotation(0, -70, 10, 0.1571F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 46, 3)
                        .addBox(-2, 44, -2, 4, 4, 4),
                PartPose.offsetAndRotation(0, 47, 0, 0.0550F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition bbcRed() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-10, -107, -11, 20, 13, 22)
                        .addBox(-14, -94, -15, 14, 16, 15)
                        .addBox(-14, -94, 0, 14, 16, 15)
                        .addBox(0, -94, -15, 14, 16, 15)
                        .addBox(0, -94, 0, 14, 16, 15)
                        .addBox(-12, -78, -13, 24, 13, 13)
                        .addBox(-12, -78, 0, 24, 13, 13)
                        .addBox(-8, -64, -9, 16, 8, 18)
                        .addBox(-5, -113, -5, 5, 7, 5)
                        .addBox(-5, -113, 0, 5, 7, 5)
                        .addBox(0, -113, -5, 5, 7, 5)
                        .addBox(0, -113, 0, 5, 7, 5)
                        .addBox(-9, -62, -10, 9, 8, 10)
                        .addBox(-9, -62, 0, 9, 8, 10)
                        .addBox(0, -62, -10, 9, 8, 10)
                        .addBox(0, -62, 0, 9, 8, 10),
                PartPose.offsetAndRotation(0, -11, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -7, -1, 9, 12, 2),
                PartPose.offsetAndRotation(0, -88, -16, 0.0F, 0.0F, 0.0F));

        PartDefinition arm0 = body.addOrReplaceChild("arm0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 3, 21, 3)
                        .addBox(-1, 20, -1, 3, 8, 3),
                PartPose.offsetAndRotation(-9, -59, -6, 0.1047F, 0.0F, -0.1414F));

        PartDefinition arm1 = body.addOrReplaceChild("arm1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 3, 21, 3)
                        .addBox(-1, 20, -1, 3, 8, 3),
                PartPose.offsetAndRotation(9, -59, -6, 0.1047F, 0.0F, 0.1414F));

        PartDefinition arm2 = body.addOrReplaceChild("arm2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 3, 21, 3)
                        .addBox(-1, 20, -1, 3, 8, 3),
                PartPose.offsetAndRotation(0, -59, 9, 0.1047F, 0.0F, 0.0F));

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 47, 3),
                PartPose.offsetAndRotation(-10, -70, 0, 0.0F, 0.0F, 0.2094F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 46, 3)
                        .addBox(-2, 44, -2, 4, 4, 4),
                PartPose.offsetAndRotation(0, 47, 0, 0.0F, 0.0F, 0.0733F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 47, 3),
                PartPose.offsetAndRotation(10, -70, 0, 0.0F, 0.0F, -0.2094F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 46, 3)
                        .addBox(-2, 44, -2, 4, 4, 4),
                PartPose.offsetAndRotation(0, 47, 0, 0.0F, 0.0F, -0.0733F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 47, 3),
                PartPose.offsetAndRotation(0, -70, 10, 0.1571F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 46, 3)
                        .addBox(-2, 44, -2, 4, 4, 4),
                PartPose.offsetAndRotation(0, 47, 0, 0.0550F, 0.0F, 0.0F));

        PartDefinition cannon = body.addOrReplaceChild("cannon",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -3, -22, 5, 7, 24)
                        .addBox(-3, -4, -26, 7, 9, 4)
                        .addBox(-2, -3, -27, 4, 5, 1),
                PartPose.offsetAndRotation(0, -78, -14, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition manta() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-17, -25, -31, 17, 23, 16)
                        .addBox(-17, -25, -16, 17, 23, 16)
                        .addBox(-17, -25, 0, 17, 23, 16)
                        .addBox(-17, -25, 16, 17, 23, 16)
                        .addBox(0, -25, -31, 17, 23, 16)
                        .addBox(0, -25, -16, 17, 23, 16)
                        .addBox(0, -25, 0, 17, 23, 16)
                        .addBox(0, -25, 16, 17, 23, 16)
                        .addBox(-12, -38, -23, 12, 13, 11)
                        .addBox(-12, -38, -12, 12, 13, 11)
                        .addBox(-12, -38, 0, 12, 13, 11)
                        .addBox(0, -38, -23, 12, 13, 11)
                        .addBox(0, -38, -12, 12, 13, 11)
                        .addBox(0, -38, 0, 12, 13, 11),
                PartPose.offsetAndRotation(0, 12, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_l = body.addOrReplaceChild("wing_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-30, -3, -20, 30, 6, 34)
                        .addBox(-46, -1, -12, 16, 5, 22)
                        .addBox(-56, 0, -6, 10, 4, 12),
                PartPose.offsetAndRotation(-16, -23, 0, 0.0F, 0.0F, 0.1047F));

        PartDefinition wing_r = body.addOrReplaceChild("wing_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -3, -20, 30, 6, 34)
                        .addBox(30, -1, -12, 16, 5, 22)
                        .addBox(46, 0, -6, 10, 4, 12),
                PartPose.offsetAndRotation(16, -23, 0, 0.0F, 0.0F, -0.1047F));

        PartDefinition tail = body.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, -4, 0, 6, 8, 18),
                PartPose.offsetAndRotation(0, -20, 30, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -5, -1, 12, 9, 2),
                PartPose.offsetAndRotation(0, -33, -30, 0.0F, 0.0F, 0.0F));

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 13, 2),
                PartPose.offsetAndRotation(-12, -3, -14, 0.0F, 0.0F, 0.1047F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-2, 10, -2, 3, 4, 3),
                PartPose.offsetAndRotation(0, 13, 0, 0.0F, 0.0F, 0.0367F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 13, 2),
                PartPose.offsetAndRotation(12, -3, -14, 0.0F, 0.0F, -0.1047F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-2, 10, -2, 3, 4, 3),
                PartPose.offsetAndRotation(0, 13, 0, 0.0F, 0.0F, -0.0367F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 13, 2),
                PartPose.offsetAndRotation(0, -3, 12, 0.0F, 0.0F, 0.0F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-2, 10, -2, 3, 4, 3),
                PartPose.offsetAndRotation(0, 13, 0, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition book() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-13, -163, -14, 13, 19, 14)
                        .addBox(-13, -163, 0, 13, 19, 14)
                        .addBox(0, -163, -14, 13, 19, 14)
                        .addBox(0, -163, 0, 13, 19, 14)
                        .addBox(-18, -144, -20, 18, 24, 20)
                        .addBox(-18, -144, 0, 18, 24, 20)
                        .addBox(0, -144, -20, 18, 24, 20)
                        .addBox(0, -144, 0, 18, 24, 20)
                        .addBox(-17, -120, -19, 17, 22, 19)
                        .addBox(-17, -120, 0, 17, 22, 19)
                        .addBox(0, -120, -19, 17, 22, 19)
                        .addBox(0, -120, 0, 17, 22, 19)
                        .addBox(-13, -98, -15, 13, 14, 15)
                        .addBox(-13, -98, 0, 13, 14, 15)
                        .addBox(0, -98, -15, 13, 14, 15)
                        .addBox(0, -98, 0, 13, 14, 15)
                        .addBox(-9, -84, -10, 18, 8, 20)
                        .addBox(-17, -152, -24, 11, 11, 8)
                        .addBox(-17, -152, -16, 11, 11, 8)
                        .addBox(-6, -152, -24, 11, 11, 8)
                        .addBox(-6, -152, -16, 11, 11, 8)
                        .addBox(6, -152, -24, 11, 11, 8)
                        .addBox(6, -152, -16, 11, 11, 8)
                        .addBox(-9, -82, -10, 9, 8, 10)
                        .addBox(-9, -82, 0, 9, 8, 10)
                        .addBox(0, -82, -10, 9, 8, 10)
                        .addBox(0, -82, 0, 9, 8, 10),
                PartPose.offsetAndRotation(0, -11, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-4, -4, -1, 8, 8, 2),
                PartPose.offsetAndRotation(0, -136, -20, 0.0F, 0.0F, 0.0F));

        PartDefinition tent0_1_body = body.addOrReplaceChild("tent0_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(-11, -84, -9, 0.1222F, -0.0698F, 0.0F));

        PartDefinition tent0_2_body = tent0_1_body.addOrReplaceChild("tent0_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(0, 14, 0, 0.2443F, -0.1396F, 0.0F));

        PartDefinition tent0_3_body = tent0_2_body.addOrReplaceChild("tent0_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(0, 14, 0, 0.2443F, -0.1396F, 0.0F));

        PartDefinition tent1_1_body = body.addOrReplaceChild("tent1_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(11, -84, -9, 0.1222F, 0.0698F, 0.0F));

        PartDefinition tent1_2_body = tent1_1_body.addOrReplaceChild("tent1_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(0, 14, 0, 0.2443F, 0.1396F, 0.0F));

        PartDefinition tent1_3_body = tent1_2_body.addOrReplaceChild("tent1_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(0, 14, 0, 0.2443F, 0.1396F, 0.0F));

        PartDefinition tent2_1_body = body.addOrReplaceChild("tent2_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(0, -84, 11, 0.1222F, 0.0F, 0.0F));

        PartDefinition tent2_2_body = tent2_1_body.addOrReplaceChild("tent2_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(0, 14, 0, 0.2443F, 0.0F, 0.0F));

        PartDefinition tent2_3_body = tent2_2_body.addOrReplaceChild("tent2_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 14, 3),
                PartPose.offsetAndRotation(0, 14, 0, 0.2443F, 0.0F, 0.0F));

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 60, 4),
                PartPose.offsetAndRotation(-14, -95, -10, -0.1920F, 0.0F, 0.2618F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 57, 4)
                        .addBox(-3, 56, -3, 5, 4, 5),
                PartPose.offsetAndRotation(0, 60, 0, -0.0672F, 0.0F, 0.0916F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 60, 4),
                PartPose.offsetAndRotation(14, -95, -10, -0.1920F, 0.0F, -0.2618F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 57, 4)
                        .addBox(-3, 56, -3, 5, 4, 5),
                PartPose.offsetAndRotation(0, 60, 0, -0.0672F, 0.0F, -0.0916F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 61, 4),
                PartPose.offsetAndRotation(0, -95, 14, 0.2269F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 58, 4)
                        .addBox(-3, 57, -3, 5, 4, 5),
                PartPose.offsetAndRotation(0, 61, 0, 0.0794F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition asylum() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-25, -67, -28, 12, 16, 14)
                        .addBox(-25, -67, -14, 12, 16, 14)
                        .addBox(-25, -67, 0, 12, 16, 14)
                        .addBox(-25, -67, 14, 12, 16, 14)
                        .addBox(-12, -67, -28, 12, 16, 14)
                        .addBox(-12, -67, -14, 12, 16, 14)
                        .addBox(-12, -67, 0, 12, 16, 14)
                        .addBox(-12, -67, 14, 12, 16, 14)
                        .addBox(0, -67, -28, 12, 16, 14)
                        .addBox(0, -67, -14, 12, 16, 14)
                        .addBox(0, -67, 0, 12, 16, 14)
                        .addBox(0, -67, 14, 12, 16, 14)
                        .addBox(12, -67, -28, 12, 16, 14)
                        .addBox(12, -67, -14, 12, 16, 14)
                        .addBox(12, -67, 0, 12, 16, 14)
                        .addBox(12, -67, 14, 12, 16, 14)
                        .addBox(-22, -51, -25, 15, 12, 12)
                        .addBox(-22, -51, -12, 15, 12, 12)
                        .addBox(-22, -51, 0, 15, 12, 12)
                        .addBox(-22, -51, 12, 15, 12, 12)
                        .addBox(-7, -51, -25, 15, 12, 12)
                        .addBox(-7, -51, -12, 15, 12, 12)
                        .addBox(-7, -51, 0, 15, 12, 12)
                        .addBox(-7, -51, 12, 15, 12, 12)
                        .addBox(7, -51, -25, 15, 12, 12)
                        .addBox(7, -51, -12, 15, 12, 12)
                        .addBox(7, -51, 0, 15, 12, 12)
                        .addBox(7, -51, 12, 15, 12, 12)
                        .addBox(-17, -39, -20, 11, 8, 13)
                        .addBox(-17, -39, -7, 11, 8, 13)
                        .addBox(-17, -39, 7, 11, 8, 13)
                        .addBox(-6, -39, -20, 11, 8, 13)
                        .addBox(-6, -39, -7, 11, 8, 13)
                        .addBox(-6, -39, 7, 11, 8, 13)
                        .addBox(6, -39, -20, 11, 8, 13)
                        .addBox(6, -39, -7, 11, 8, 13)
                        .addBox(6, -39, 7, 11, 8, 13)
                        .addBox(-20, -67, -22, 8, 3, 8)
                        .addBox(-19, -64, -21, 6, 3, 6)
                        .addBox(-18, -60, -20, 4, 3, 4)
                        .addBox(12, -67, -22, 8, 3, 8)
                        .addBox(13, -64, -21, 6, 3, 6)
                        .addBox(14, -60, -20, 4, 3, 4)
                        .addBox(-22, -67, 0, 8, 3, 8)
                        .addBox(-21, -64, 1, 6, 3, 6)
                        .addBox(-20, -60, 2, 4, 3, 4)
                        .addBox(14, -67, 0, 8, 3, 8)
                        .addBox(15, -64, 1, 6, 3, 6)
                        .addBox(16, -60, 2, 4, 3, 4)
                        .addBox(-18, -67, 18, 8, 3, 8)
                        .addBox(-17, -64, 19, 6, 3, 6)
                        .addBox(-16, -60, 20, 4, 3, 4)
                        .addBox(10, -67, 18, 8, 3, 8)
                        .addBox(11, -64, 19, 6, 3, 6)
                        .addBox(12, -60, 20, 4, 3, 4)
                        .addBox(-13, -64, -31, 9, 9, 10)
                        .addBox(-4, -64, -31, 9, 9, 10)
                        .addBox(4, -64, -31, 9, 9, 10),
                PartPose.offsetAndRotation(0, -15, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-7, -8, -1, 13, 14, 2),
                PartPose.offsetAndRotation(0, -57, -32, 0.0F, 0.0F, 0.0F));

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 39, 3),
                PartPose.offsetAndRotation(-20, -52, -20, -0.1920F, 0.0F, 0.2618F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 37, 3)
                        .addBox(-2, 36, -2, 4, 5, 4),
                PartPose.offsetAndRotation(0, 39, 0, -0.0672F, 0.0F, 0.0916F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 39, 3),
                PartPose.offsetAndRotation(20, -52, -20, -0.1920F, 0.0F, -0.2618F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 37, 3)
                        .addBox(-2, 36, -2, 4, 5, 4),
                PartPose.offsetAndRotation(0, 39, 0, -0.0672F, 0.0F, -0.0916F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 39, 3),
                PartPose.offsetAndRotation(-22, -52, 0, 0.0F, 0.0F, 0.2880F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 37, 3)
                        .addBox(-2, 36, -2, 4, 5, 4),
                PartPose.offsetAndRotation(0, 39, 0, 0.0F, 0.0F, 0.1008F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 39, 3),
                PartPose.offsetAndRotation(22, -52, 0, 0.0F, 0.0F, -0.2880F));

        PartDefinition leg3_shin = leg3.addOrReplaceChild("leg3_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 37, 3)
                        .addBox(-2, 36, -2, 4, 5, 4),
                PartPose.offsetAndRotation(0, 39, 0, 0.0F, 0.0F, -0.1008F));

        PartDefinition leg4 = root.addOrReplaceChild("leg4",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 39, 3),
                PartPose.offsetAndRotation(-18, -52, 20, 0.1920F, 0.0F, 0.2356F));

        PartDefinition leg4_shin = leg4.addOrReplaceChild("leg4_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 37, 3)
                        .addBox(-2, 36, -2, 4, 5, 4),
                PartPose.offsetAndRotation(0, 39, 0, 0.0672F, 0.0F, 0.0825F));

        PartDefinition leg5 = root.addOrReplaceChild("leg5",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 39, 3),
                PartPose.offsetAndRotation(18, -52, 20, 0.1920F, 0.0F, -0.2356F));

        PartDefinition leg5_shin = leg5.addOrReplaceChild("leg5_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 37, 3)
                        .addBox(-2, 36, -2, 4, 5, 4),
                PartPose.offsetAndRotation(0, 39, 0, 0.0672F, 0.0F, -0.0825F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition goliath() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-15, -193, -17, 15, 20, 17)
                        .addBox(-15, -193, 0, 15, 20, 17)
                        .addBox(0, -193, -17, 15, 20, 17)
                        .addBox(0, -193, 0, 15, 20, 17)
                        .addBox(-23, -173, -25, 23, 26, 17)
                        .addBox(-23, -173, -8, 23, 26, 17)
                        .addBox(-23, -173, 8, 23, 26, 17)
                        .addBox(0, -173, -25, 23, 26, 17)
                        .addBox(0, -173, -8, 23, 26, 17)
                        .addBox(0, -173, 8, 23, 26, 17)
                        .addBox(-22, -148, -24, 22, 23, 24)
                        .addBox(-22, -148, 0, 22, 23, 24)
                        .addBox(0, -148, -24, 22, 23, 24)
                        .addBox(0, -148, 0, 22, 23, 24)
                        .addBox(-16, -125, -18, 16, 14, 18)
                        .addBox(-16, -125, 0, 16, 14, 18)
                        .addBox(0, -125, -18, 16, 14, 18)
                        .addBox(0, -125, 0, 16, 14, 18)
                        .addBox(-11, -111, -13, 22, 9, 13)
                        .addBox(-11, -111, 0, 22, 9, 13)
                        .addBox(-25, -173, -31, 12, 11, 9)
                        .addBox(-25, -173, -22, 12, 11, 9)
                        .addBox(-12, -173, -31, 12, 11, 9)
                        .addBox(-12, -173, -22, 12, 11, 9)
                        .addBox(0, -173, -31, 12, 11, 9)
                        .addBox(0, -173, -22, 12, 11, 9)
                        .addBox(12, -173, -31, 12, 11, 9)
                        .addBox(12, -173, -22, 12, 11, 9)
                        .addBox(-12, -116, -14, 12, 11, 9)
                        .addBox(-12, -116, -5, 12, 11, 9)
                        .addBox(-12, -116, 5, 12, 11, 9)
                        .addBox(0, -116, -14, 12, 11, 9)
                        .addBox(0, -116, -5, 12, 11, 9)
                        .addBox(0, -116, 5, 12, 11, 9),
                PartPose.offsetAndRotation(0, -8, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye_l = body.addOrReplaceChild("eye_l",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -6, -1, 12, 11, 2),
                PartPose.offsetAndRotation(-12, -159, -26, 0.0F, 0.0F, 0.0F));

        PartDefinition eye_r = body.addOrReplaceChild("eye_r",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -6, -1, 12, 11, 2),
                PartPose.offsetAndRotation(12, -159, -26, 0.0F, 0.0F, 0.0F));

        PartDefinition tent0_1_body = body.addOrReplaceChild("tent0_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(-16, -111, -12, 0.1571F, -0.1047F, 0.0F));

        PartDefinition tent0_2_body = tent0_1_body.addOrReplaceChild("tent0_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, -0.2094F, 0.0F));

        PartDefinition tent0_3_body = tent0_2_body.addOrReplaceChild("tent0_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, -0.2094F, 0.0F));

        PartDefinition tent1_1_body = body.addOrReplaceChild("tent1_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(16, -111, -12, 0.1571F, 0.1047F, 0.0F));

        PartDefinition tent1_2_body = tent1_1_body.addOrReplaceChild("tent1_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, 0.2094F, 0.0F));

        PartDefinition tent1_3_body = tent1_2_body.addOrReplaceChild("tent1_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, 0.2094F, 0.0F));

        PartDefinition tent2_1_body = body.addOrReplaceChild("tent2_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(-20, -111, 4, 0.1571F, -0.1571F, 0.0F));

        PartDefinition tent2_2_body = tent2_1_body.addOrReplaceChild("tent2_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, -0.3142F, 0.0F));

        PartDefinition tent2_3_body = tent2_2_body.addOrReplaceChild("tent2_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, -0.3142F, 0.0F));

        PartDefinition tent3_1_body = body.addOrReplaceChild("tent3_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(20, -111, 4, 0.1571F, 0.1571F, 0.0F));

        PartDefinition tent3_2_body = tent3_1_body.addOrReplaceChild("tent3_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, 0.3142F, 0.0F));

        PartDefinition tent3_3_body = tent3_2_body.addOrReplaceChild("tent3_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, 0.3142F, 0.0F));

        PartDefinition tent4_1_body = body.addOrReplaceChild("tent4_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(-10, -111, 16, 0.1571F, -0.0698F, 0.0F));

        PartDefinition tent4_2_body = tent4_1_body.addOrReplaceChild("tent4_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, -0.1396F, 0.0F));

        PartDefinition tent4_3_body = tent4_2_body.addOrReplaceChild("tent4_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, -0.1396F, 0.0F));

        PartDefinition tent5_1_body = body.addOrReplaceChild("tent5_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(10, -111, 16, 0.1571F, 0.0698F, 0.0F));

        PartDefinition tent5_2_body = tent5_1_body.addOrReplaceChild("tent5_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, 0.1396F, 0.0F));

        PartDefinition tent5_3_body = tent5_2_body.addOrReplaceChild("tent5_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 17, 3),
                PartPose.offsetAndRotation(0, 17, 0, 0.3142F, 0.1396F, 0.0F));

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 74, 5),
                PartPose.offsetAndRotation(-20, -122, -12, -0.1745F, 0.0F, 0.2269F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 71, 5)
                        .addBox(-3, 70, -3, 6, 4, 6),
                PartPose.offsetAndRotation(0, 74, 0, -0.0611F, 0.0F, 0.0794F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 74, 5),
                PartPose.offsetAndRotation(20, -122, -12, -0.1745F, 0.0F, -0.2269F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 71, 5)
                        .addBox(-3, 70, -3, 6, 4, 6),
                PartPose.offsetAndRotation(0, 74, 0, -0.0611F, 0.0F, -0.0794F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 75, 5),
                PartPose.offsetAndRotation(0, -122, 22, 0.2094F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 72, 5)
                        .addBox(-3, 71, -3, 6, 4, 6),
                PartPose.offsetAndRotation(0, 75, 0, 0.0733F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition heron() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 82, 3),
                PartPose.offsetAndRotation(-11, -139, -6, -0.0698F, 0.0F, 0.1222F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 80, 3)
                        .addBox(-2, 79, -2, 4, 3, 4),
                PartPose.offsetAndRotation(0, 82, 0, -0.0244F, 0.0F, 0.0428F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 82, 3),
                PartPose.offsetAndRotation(11, -139, -6, -0.0698F, 0.0F, -0.1222F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 80, 3)
                        .addBox(-2, 79, -2, 4, 3, 4),
                PartPose.offsetAndRotation(0, 82, 0, -0.0244F, 0.0F, -0.0428F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 82, 3),
                PartPose.offsetAndRotation(0, -139, 12, 0.1047F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 80, 3)
                        .addBox(-2, 79, -2, 4, 3, 4),
                PartPose.offsetAndRotation(0, 82, 0, 0.0367F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -204, -9, 8, 9, 9)
                        .addBox(-8, -204, 0, 8, 9, 9)
                        .addBox(-8, -195, -9, 8, 9, 9)
                        .addBox(-8, -195, 0, 8, 9, 9)
                        .addBox(-8, -186, -9, 8, 9, 9)
                        .addBox(-8, -186, 0, 8, 9, 9)
                        .addBox(-8, -178, -9, 8, 9, 9)
                        .addBox(-8, -178, 0, 8, 9, 9)
                        .addBox(-8, -169, -9, 8, 9, 9)
                        .addBox(-8, -169, 0, 8, 9, 9)
                        .addBox(0, -204, -9, 8, 9, 9)
                        .addBox(0, -204, 0, 8, 9, 9)
                        .addBox(0, -195, -9, 8, 9, 9)
                        .addBox(0, -195, 0, 8, 9, 9)
                        .addBox(0, -186, -9, 8, 9, 9)
                        .addBox(0, -186, 0, 8, 9, 9)
                        .addBox(0, -178, -9, 8, 9, 9)
                        .addBox(0, -178, 0, 8, 9, 9)
                        .addBox(0, -169, -9, 8, 9, 9)
                        .addBox(0, -169, 0, 8, 9, 9)
                        .addBox(-14, -160, -14, 9, 7, 9)
                        .addBox(-14, -160, -5, 9, 7, 9)
                        .addBox(-14, -160, 5, 9, 7, 9)
                        .addBox(-14, -153, -14, 9, 7, 9)
                        .addBox(-14, -153, -5, 9, 7, 9)
                        .addBox(-14, -153, 5, 9, 7, 9)
                        .addBox(-5, -160, -14, 9, 7, 9)
                        .addBox(-5, -160, -5, 9, 7, 9)
                        .addBox(-5, -160, 5, 9, 7, 9)
                        .addBox(-5, -153, -14, 9, 7, 9)
                        .addBox(-5, -153, -5, 9, 7, 9)
                        .addBox(-5, -153, 5, 9, 7, 9)
                        .addBox(5, -160, -14, 9, 7, 9)
                        .addBox(5, -160, -5, 9, 7, 9)
                        .addBox(5, -160, 5, 9, 7, 9)
                        .addBox(5, -153, -14, 9, 7, 9)
                        .addBox(5, -153, -5, 9, 7, 9)
                        .addBox(5, -153, 5, 9, 7, 9)
                        .addBox(-14, -172, -13, 28, 5, 26)
                        .addBox(-14, -138, -13, 28, 4, 26)
                        .addBox(-13, -190, -13, 26, 14, 26),
                PartPose.offsetAndRotation(0, 11, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition rear_hood = body.addOrReplaceChild("rear_hood",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-15, 0, -13, 10, 11, 9)
                        .addBox(-15, 0, -4, 10, 11, 9)
                        .addBox(-15, 0, 4, 10, 11, 9)
                        .addBox(-5, 0, -13, 10, 11, 9)
                        .addBox(-5, 0, -4, 10, 11, 9)
                        .addBox(-5, 0, 4, 10, 11, 9)
                        .addBox(5, 0, -13, 10, 11, 9)
                        .addBox(5, 0, -4, 10, 11, 9)
                        .addBox(5, 0, 4, 10, 11, 9)
                        .addBox(-14, 11, -11, 9, 11, 11)
                        .addBox(-14, 11, 0, 9, 11, 11)
                        .addBox(-5, 11, -11, 9, 11, 11)
                        .addBox(-5, 11, 0, 9, 11, 11)
                        .addBox(5, 11, -11, 9, 11, 11)
                        .addBox(5, 11, 0, 9, 11, 11)
                        .addBox(-10, 22, -8, 10, 8, 8)
                        .addBox(-10, 22, 0, 10, 8, 8)
                        .addBox(0, 22, -8, 10, 8, 8)
                        .addBox(0, 22, 0, 10, 8, 8)
                        .addBox(-6, 30, -5, 6, 4, 10)
                        .addBox(0, 30, -5, 6, 4, 10)
                        .addBox(-10, -5, 13, 3, 22, 11)
                        .addBox(0, -5, 13, 3, 22, 11)
                        .addBox(10, -5, 13, 3, 22, 11)
                        .addBox(-7, 4, 16, 5, 9, 2)
                        .addBox(2, 4, 16, 5, 9, 2),
                PartPose.offsetAndRotation(0, -204, 20, 0.1047F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-17, -3, -18, 34, 7, 36)
                        .addBox(-13, -7, -14, 26, 4, 28)
                        .addBox(-17, 4, -18, 5, 26, 34)
                        .addBox(12, 4, -18, 5, 26, 34)
                        .addBox(-12, 4, 10, 24, 26, 6)
                        .addBox(-17, 4, -24, 34, 6, 8)
                        .addBox(-12, 30, -16, 24, 4, 28)
                        .addBox(-20, -1, -6, 3, 5, 20)
                        .addBox(17, -1, -6, 3, 5, 20)
                        .addBox(-10, 2, 14, 3, 24, 11)
                        .addBox(0, 2, 14, 3, 24, 11)
                        .addBox(10, 2, 14, 3, 24, 11),
                PartPose.offsetAndRotation(0, -204, -22, -0.2094F, 0.0F, 0.0F));

        PartDefinition pilot = body.addOrReplaceChild("pilot",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, 0, -4, 16, 14, 12)
                        .addBox(-5, -9, -2, 10, 9, 10)
                        .addBox(-4, -7, -4, 3, 2, 2)
                        .addBox(1, -7, -4, 3, 2, 2),
                PartPose.offsetAndRotation(0, -186, -14, 0.0F, 0.0F, 0.0F));

        PartDefinition cannon0 = body.addOrReplaceChild("cannon0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -2, -16, 5, 5, 18)
                        .addBox(-3, -3, -18, 7, 7, 2)
                        .addBox(-1, 2, -8, 3, 4, 8),
                PartPose.offsetAndRotation(-11, -180, -20, 0.0F, 0.0F, 0.0F));

        PartDefinition cannon1 = body.addOrReplaceChild("cannon1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -2, -16, 5, 5, 18)
                        .addBox(-3, -3, -18, 7, 7, 2)
                        .addBox(-1, 2, -8, 3, 4, 8),
                PartPose.offsetAndRotation(11, -180, -20, 0.0F, 0.0F, 0.0F));

        PartDefinition tent0_1_body = body.addOrReplaceChild("tent0_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 13, 3),
                PartPose.offsetAndRotation(-9, -120, -2, 0.0698F, -0.1047F, 0.0F));

        PartDefinition tent0_2_body = tent0_1_body.addOrReplaceChild("tent0_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 13, 3),
                PartPose.offsetAndRotation(0, 13, 0, 0.1396F, -0.2094F, 0.0F));

        PartDefinition tent0_3_body = tent0_2_body.addOrReplaceChild("tent0_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 13, 3),
                PartPose.offsetAndRotation(0, 13, 0, 0.1396F, -0.2094F, 0.0F));

        PartDefinition tent1_1_body = body.addOrReplaceChild("tent1_1_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 13, 3),
                PartPose.offsetAndRotation(9, -120, -2, 0.0698F, 0.1047F, 0.0F));

        PartDefinition tent1_2_body = tent1_1_body.addOrReplaceChild("tent1_2_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 13, 3),
                PartPose.offsetAndRotation(0, 13, 0, 0.1396F, 0.2094F, 0.0F));

        PartDefinition tent1_3_body = tent1_2_body.addOrReplaceChild("tent1_3_body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 13, 3),
                PartPose.offsetAndRotation(0, 13, 0, 0.1396F, 0.2094F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition spider() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-16, -60, -20, 11, 8, 13)
                        .addBox(-16, -60, -7, 11, 8, 13)
                        .addBox(-16, -60, 7, 11, 8, 13)
                        .addBox(-5, -60, -20, 11, 8, 13)
                        .addBox(-5, -60, -7, 11, 8, 13)
                        .addBox(-5, -60, 7, 11, 8, 13)
                        .addBox(5, -60, -20, 11, 8, 13)
                        .addBox(5, -60, -7, 11, 8, 13)
                        .addBox(5, -60, 7, 11, 8, 13)
                        .addBox(-19, -52, -23, 13, 9, 12)
                        .addBox(-19, -52, -12, 13, 9, 12)
                        .addBox(-19, -52, 0, 13, 9, 12)
                        .addBox(-19, -52, 12, 13, 9, 12)
                        .addBox(-6, -52, -23, 13, 9, 12)
                        .addBox(-6, -52, -12, 13, 9, 12)
                        .addBox(-6, -52, 0, 13, 9, 12)
                        .addBox(-6, -52, 12, 13, 9, 12)
                        .addBox(6, -52, -23, 13, 9, 12)
                        .addBox(6, -52, -12, 13, 9, 12)
                        .addBox(6, -52, 0, 13, 9, 12)
                        .addBox(6, -52, 12, 13, 9, 12)
                        .addBox(-16, -43, -20, 11, 7, 13)
                        .addBox(-16, -43, -7, 11, 7, 13)
                        .addBox(-16, -43, 7, 11, 7, 13)
                        .addBox(-5, -43, -20, 11, 7, 13)
                        .addBox(-5, -43, -7, 11, 7, 13)
                        .addBox(-5, -43, 7, 11, 7, 13)
                        .addBox(5, -43, -20, 11, 7, 13)
                        .addBox(5, -43, -7, 11, 7, 13)
                        .addBox(5, -43, 7, 11, 7, 13)
                        .addBox(-11, -36, -15, 11, 4, 10)
                        .addBox(-11, -36, -5, 11, 4, 10)
                        .addBox(-11, -36, 5, 11, 4, 10)
                        .addBox(0, -36, -15, 11, 4, 10)
                        .addBox(0, -36, -5, 11, 4, 10)
                        .addBox(0, -36, 5, 11, 4, 10)
                        .addBox(-9, -64, 6, 3, 9, 14)
                        .addBox(0, -64, 6, 3, 9, 14)
                        .addBox(9, -64, 6, 3, 9, 14),
                PartPose.offsetAndRotation(0, 11, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-13, -3, -10, 26, 9, 14)
                        .addBox(-9, -2, -18, 18, 7, 9)
                        .addBox(-4, -1, -24, 8, 5, 7),
                PartPose.offsetAndRotation(0, -52, -20, -0.0698F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -2, -1, 12, 4, 2),
                PartPose.offsetAndRotation(0, 2, -22, 0.0F, 0.0F, 0.0F));

        PartDefinition blade0 = head.addOrReplaceChild("blade0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -22, 2, 3, 22),
                PartPose.offsetAndRotation(-9, 4, -20, 0.1745F, -0.2443F, 0.0F));

        PartDefinition blade1 = head.addOrReplaceChild("blade1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -22, 2, 3, 22),
                PartPose.offsetAndRotation(-4, 4, -20, 0.0698F, -0.0873F, 0.0F));

        PartDefinition blade2 = head.addOrReplaceChild("blade2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -22, 2, 3, 22),
                PartPose.offsetAndRotation(4, 4, -20, 0.0698F, 0.0873F, 0.0F));

        PartDefinition blade3 = head.addOrReplaceChild("blade3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -22, 2, 3, 22),
                PartPose.offsetAndRotation(9, 4, -20, 0.1745F, 0.2443F, 0.0F));

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 30, 2),
                PartPose.offsetAndRotation(-17, -25, -16, -0.2513F, 0.0F, 0.4451F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 28, 2)
                        .addBox(-2, 27, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 30, 0, -0.1508F, 0.0F, 0.2670F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 30, 2),
                PartPose.offsetAndRotation(17, -25, -16, -0.2513F, 0.0F, -0.4451F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 28, 2)
                        .addBox(-2, 27, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 30, 0, -0.1508F, 0.0F, -0.2670F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 30, 2),
                PartPose.offsetAndRotation(-19, -25, -5, -0.0785F, 0.0F, 0.4974F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 28, 2)
                        .addBox(-2, 27, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 30, 0, -0.0471F, 0.0F, 0.2985F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 30, 2),
                PartPose.offsetAndRotation(19, -25, -5, -0.0785F, 0.0F, -0.4974F));

        PartDefinition leg3_shin = leg3.addOrReplaceChild("leg3_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 28, 2)
                        .addBox(-2, 27, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 30, 0, -0.0471F, 0.0F, -0.2985F));

        PartDefinition leg4 = root.addOrReplaceChild("leg4",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 30, 2),
                PartPose.offsetAndRotation(-19, -25, 6, 0.0942F, 0.0F, 0.4974F));

        PartDefinition leg4_shin = leg4.addOrReplaceChild("leg4_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 28, 2)
                        .addBox(-2, 27, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 30, 0, 0.0565F, 0.0F, 0.2985F));

        PartDefinition leg5 = root.addOrReplaceChild("leg5",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 30, 2),
                PartPose.offsetAndRotation(19, -25, 6, 0.0942F, 0.0F, -0.4974F));

        PartDefinition leg5_shin = leg5.addOrReplaceChild("leg5_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 28, 2)
                        .addBox(-2, 27, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 30, 0, 0.0565F, 0.0F, -0.2985F));

        PartDefinition leg6 = root.addOrReplaceChild("leg6",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 30, 2),
                PartPose.offsetAndRotation(-17, -25, 17, 0.2670F, 0.0F, 0.4451F));

        PartDefinition leg6_shin = leg6.addOrReplaceChild("leg6_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 28, 2)
                        .addBox(-2, 27, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 30, 0, 0.1602F, 0.0F, 0.2670F));

        PartDefinition leg7 = root.addOrReplaceChild("leg7",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 30, 2),
                PartPose.offsetAndRotation(17, -25, 17, 0.2670F, 0.0F, -0.4451F));

        PartDefinition leg7_shin = leg7.addOrReplaceChild("leg7_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 28, 2)
                        .addBox(-2, 27, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 30, 0, 0.1602F, 0.0F, -0.2670F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition louse() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -10, -10, 16, 8, 20)
                        .addBox(-10, -3, -12, 20, 8, 24)
                        .addBox(-8, 5, -10, 16, 5, 20)
                        .addBox(-10, -10, -11, 4, 1, 4)
                        .addBox(-9, -9, -10, 2, 1, 2)
                        .addBox(-8, -8, -9, 1, 1, 1)
                        .addBox(6, -10, -11, 4, 1, 4)
                        .addBox(7, -9, -10, 2, 1, 2)
                        .addBox(8, -8, -9, 1, 1, 1)
                        .addBox(-10, -10, 7, 4, 1, 4)
                        .addBox(-9, -9, 8, 2, 1, 2)
                        .addBox(-8, -8, 9, 1, 1, 1)
                        .addBox(6, -10, 7, 4, 1, 4)
                        .addBox(7, -9, 8, 2, 1, 2)
                        .addBox(8, -8, 9, 1, 1, 1),
                PartPose.offsetAndRotation(0, 0, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-4, -3, -1, 7, 5, 2),
                PartPose.offsetAndRotation(0, -5, -12, 0.0F, 0.0F, 0.0F));

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 9, 2),
                PartPose.offsetAndRotation(-9, 5, -8, -0.1117F, 0.0F, 0.1885F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 8, 2)
                        .addBox(-1, 6, -1, 2, 4, 2),
                PartPose.offsetAndRotation(0, 9, 0, -0.0391F, 0.0F, 0.0660F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 9, 2),
                PartPose.offsetAndRotation(9, 5, -8, -0.1117F, 0.0F, -0.1885F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 8, 2)
                        .addBox(-1, 6, -1, 2, 4, 2),
                PartPose.offsetAndRotation(0, 9, 0, -0.0391F, 0.0F, -0.0660F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 9, 2),
                PartPose.offsetAndRotation(-9, 5, 8, 0.1117F, 0.0F, 0.1885F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 8, 2)
                        .addBox(-1, 6, -1, 2, 4, 2),
                PartPose.offsetAndRotation(0, 9, 0, 0.0391F, 0.0F, 0.0660F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 9, 2),
                PartPose.offsetAndRotation(9, 5, 8, 0.1117F, 0.0F, -0.1885F));

        PartDefinition leg3_shin = leg3.addOrReplaceChild("leg3_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 8, 2)
                        .addBox(-1, 6, -1, 2, 4, 2),
                PartPose.offsetAndRotation(0, 9, 0, 0.0391F, 0.0F, -0.0660F));

        PartDefinition leg4 = root.addOrReplaceChild("leg4",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 9, 2),
                PartPose.offsetAndRotation(0, 5, -9, -0.1257F, 0.0F, 0.0F));

        PartDefinition leg4_shin = leg4.addOrReplaceChild("leg4_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 8, 2)
                        .addBox(-1, 6, -1, 2, 4, 2),
                PartPose.offsetAndRotation(0, 9, 0, -0.0440F, 0.0F, 0.0F));

        PartDefinition leg5 = root.addOrReplaceChild("leg5",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 9, 2),
                PartPose.offsetAndRotation(0, 5, 9, 0.1257F, 0.0F, 0.0F));

        PartDefinition leg5_shin = leg5.addOrReplaceChild("leg5_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 8, 2)
                        .addBox(-1, 6, -1, 2, 4, 2),
                PartPose.offsetAndRotation(0, 9, 0, 0.0440F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition flyingMachine() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-23, -48, -28, 12, 8, 11)
                        .addBox(-23, -48, -17, 12, 8, 11)
                        .addBox(-23, -48, -6, 12, 8, 11)
                        .addBox(-23, -48, 6, 12, 8, 11)
                        .addBox(-23, -48, 17, 12, 8, 11)
                        .addBox(-12, -48, -28, 12, 8, 11)
                        .addBox(-12, -48, -17, 12, 8, 11)
                        .addBox(-12, -48, -6, 12, 8, 11)
                        .addBox(-12, -48, 6, 12, 8, 11)
                        .addBox(-12, -48, 17, 12, 8, 11)
                        .addBox(0, -48, -28, 12, 8, 11)
                        .addBox(0, -48, -17, 12, 8, 11)
                        .addBox(0, -48, -6, 12, 8, 11)
                        .addBox(0, -48, 6, 12, 8, 11)
                        .addBox(0, -48, 17, 12, 8, 11)
                        .addBox(12, -48, -28, 12, 8, 11)
                        .addBox(12, -48, -17, 12, 8, 11)
                        .addBox(12, -48, -6, 12, 8, 11)
                        .addBox(12, -48, 6, 12, 8, 11)
                        .addBox(12, -48, 17, 12, 8, 11)
                        .addBox(-22, -40, -27, 11, 8, 11)
                        .addBox(-22, -40, -16, 11, 8, 11)
                        .addBox(-22, -40, -5, 11, 8, 11)
                        .addBox(-22, -40, 5, 11, 8, 11)
                        .addBox(-22, -40, 16, 11, 8, 11)
                        .addBox(-11, -40, -27, 11, 8, 11)
                        .addBox(-11, -40, -16, 11, 8, 11)
                        .addBox(-11, -40, -5, 11, 8, 11)
                        .addBox(-11, -40, 5, 11, 8, 11)
                        .addBox(-11, -40, 16, 11, 8, 11)
                        .addBox(0, -40, -27, 11, 8, 11)
                        .addBox(0, -40, -16, 11, 8, 11)
                        .addBox(0, -40, -5, 11, 8, 11)
                        .addBox(0, -40, 5, 11, 8, 11)
                        .addBox(0, -40, 16, 11, 8, 11)
                        .addBox(11, -40, -27, 11, 8, 11)
                        .addBox(11, -40, -16, 11, 8, 11)
                        .addBox(11, -40, -5, 11, 8, 11)
                        .addBox(11, -40, 5, 11, 8, 11)
                        .addBox(11, -40, 16, 11, 8, 11)
                        .addBox(-17, -32, -21, 11, 6, 10)
                        .addBox(-17, -32, -10, 11, 6, 10)
                        .addBox(-17, -32, 0, 11, 6, 10)
                        .addBox(-17, -32, 10, 11, 6, 10)
                        .addBox(-6, -32, -21, 11, 6, 10)
                        .addBox(-6, -32, -10, 11, 6, 10)
                        .addBox(-6, -32, 0, 11, 6, 10)
                        .addBox(-6, -32, 10, 11, 6, 10)
                        .addBox(6, -32, -21, 11, 6, 10)
                        .addBox(6, -32, -10, 11, 6, 10)
                        .addBox(6, -32, 0, 11, 6, 10)
                        .addBox(6, -32, 10, 11, 6, 10)
                        .addBox(-11, -25, -14, 11, 4, 9)
                        .addBox(-11, -25, -5, 11, 4, 9)
                        .addBox(-11, -25, 5, 11, 4, 9)
                        .addBox(0, -25, -14, 11, 4, 9)
                        .addBox(0, -25, -5, 11, 4, 9)
                        .addBox(0, -25, 5, 11, 4, 9)
                        .addBox(-10, -32, -13, 10, 9, 9)
                        .addBox(-10, -32, -4, 10, 9, 9)
                        .addBox(-10, -32, 4, 10, 9, 9)
                        .addBox(0, -32, -13, 10, 9, 9)
                        .addBox(0, -32, -4, 10, 9, 9)
                        .addBox(0, -32, 4, 10, 9, 9)
                        .addBox(-14, -48, -33, 9, 5, 10)
                        .addBox(-5, -48, -33, 9, 5, 10)
                        .addBox(5, -48, -33, 9, 5, 10),
                PartPose.offsetAndRotation(0, 21, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-7, -4, -1, 14, 8, 2),
                PartPose.offsetAndRotation(0, -40, -30, 0.0F, 0.0F, 0.0F));

        PartDefinition wing_l = body.addOrReplaceChild("wing_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-18, -2, -13, 18, 6, 26)
                        .addBox(-16, -6, -11, 12, 5, 16)
                        .addBox(-15, -9, -6, 6, 3, 6)
                        .addBox(-16, -6, 7, 12, 5, 16)
                        .addBox(-15, -9, 12, 6, 3, 6),
                PartPose.offsetAndRotation(-20, -46, 2, 0.0F, 0.0F, 0.1396F));

        PartDefinition wing_r = body.addOrReplaceChild("wing_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0, -2, -13, 18, 6, 26)
                        .addBox(4, -6, -11, 12, 5, 16)
                        .addBox(7, -9, -6, 6, 3, 6)
                        .addBox(4, -6, 7, 12, 5, 16)
                        .addBox(7, -9, 12, 6, 3, 6),
                PartPose.offsetAndRotation(20, -46, 2, 0.0F, 0.0F, -0.1396F));

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 15, 2),
                PartPose.offsetAndRotation(-9, -6, -14, -0.0977F, 0.0F, 0.1571F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 13, 2)
                        .addBox(-2, 12, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 15, 0, -0.0342F, 0.0F, 0.0550F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 15, 2),
                PartPose.offsetAndRotation(9, -6, -14, -0.0977F, 0.0F, -0.1571F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 13, 2)
                        .addBox(-2, 12, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 15, 0, -0.0342F, 0.0F, -0.0550F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 15, 2),
                PartPose.offsetAndRotation(-9, -6, 16, 0.1117F, 0.0F, 0.1571F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 13, 2)
                        .addBox(-2, 12, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.0391F, 0.0F, 0.0550F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 15, 2),
                PartPose.offsetAndRotation(9, -6, 16, 0.1117F, 0.0F, -0.1571F));

        PartDefinition leg3_shin = leg3.addOrReplaceChild("leg3_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 13, 2)
                        .addBox(-2, 12, -2, 3, 3, 3),
                PartPose.offsetAndRotation(0, 15, 0, 0.0391F, 0.0F, -0.0550F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition electric() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 37, 4),
                PartPose.offsetAndRotation(-13, -51, -8, -0.1396F, 0.0F, 0.2094F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 35, 4)
                        .addBox(-3, 33, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 37, 0, -0.0489F, 0.0F, 0.0733F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 37, 4),
                PartPose.offsetAndRotation(13, -51, -8, -0.1396F, 0.0F, -0.2094F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 35, 4)
                        .addBox(-3, 33, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 37, 0, -0.0489F, 0.0F, -0.0733F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 38, 4),
                PartPose.offsetAndRotation(0, -51, 12, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 36, 4)
                        .addBox(-3, 35, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 38, 0, 0.0611F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-13, -117, -14, 9, 9, 9)
                        .addBox(-13, -117, -5, 9, 9, 9)
                        .addBox(-13, -117, 5, 9, 9, 9)
                        .addBox(-13, -108, -14, 9, 9, 9)
                        .addBox(-13, -108, -5, 9, 9, 9)
                        .addBox(-13, -108, 5, 9, 9, 9)
                        .addBox(-13, -98, -14, 9, 9, 9)
                        .addBox(-13, -98, -5, 9, 9, 9)
                        .addBox(-13, -98, 5, 9, 9, 9)
                        .addBox(-13, -89, -14, 9, 9, 9)
                        .addBox(-13, -89, -5, 9, 9, 9)
                        .addBox(-13, -89, 5, 9, 9, 9)
                        .addBox(-13, -78, -14, 9, 9, 9)
                        .addBox(-13, -78, -5, 9, 9, 9)
                        .addBox(-13, -78, 5, 9, 9, 9)
                        .addBox(-4, -117, -14, 9, 9, 9)
                        .addBox(-4, -117, -5, 9, 9, 9)
                        .addBox(-4, -117, 5, 9, 9, 9)
                        .addBox(-4, -108, -14, 9, 9, 9)
                        .addBox(-4, -108, -5, 9, 9, 9)
                        .addBox(-4, -108, 5, 9, 9, 9)
                        .addBox(-4, -98, -14, 9, 9, 9)
                        .addBox(-4, -98, -5, 9, 9, 9)
                        .addBox(-4, -98, 5, 9, 9, 9)
                        .addBox(-4, -89, -14, 9, 9, 9)
                        .addBox(-4, -89, -5, 9, 9, 9)
                        .addBox(-4, -89, 5, 9, 9, 9)
                        .addBox(-4, -78, -14, 9, 9, 9)
                        .addBox(-4, -78, -5, 9, 9, 9)
                        .addBox(-4, -78, 5, 9, 9, 9)
                        .addBox(4, -117, -14, 9, 9, 9)
                        .addBox(4, -117, -5, 9, 9, 9)
                        .addBox(4, -117, 5, 9, 9, 9)
                        .addBox(4, -108, -14, 9, 9, 9)
                        .addBox(4, -108, -5, 9, 9, 9)
                        .addBox(4, -108, 5, 9, 9, 9)
                        .addBox(4, -98, -14, 9, 9, 9)
                        .addBox(4, -98, -5, 9, 9, 9)
                        .addBox(4, -98, 5, 9, 9, 9)
                        .addBox(4, -89, -14, 9, 9, 9)
                        .addBox(4, -89, -5, 9, 9, 9)
                        .addBox(4, -89, 5, 9, 9, 9)
                        .addBox(4, -78, -14, 9, 9, 9)
                        .addBox(4, -78, -5, 9, 9, 9)
                        .addBox(4, -78, 5, 9, 9, 9)
                        .addBox(-17, -113, -15, 34, 5, 30)
                        .addBox(-17, -101, -15, 34, 5, 30)
                        .addBox(-17, -90, -15, 34, 5, 30)
                        .addBox(-9, -69, -10, 9, 9, 10)
                        .addBox(-9, -69, 0, 9, 9, 10)
                        .addBox(0, -69, -10, 9, 9, 10)
                        .addBox(0, -69, 0, 9, 9, 10)
                        .addBox(-13, -140, -14, 9, 9, 9)
                        .addBox(-13, -140, -5, 9, 9, 9)
                        .addBox(-13, -140, 5, 9, 9, 9)
                        .addBox(-4, -140, -14, 9, 9, 9)
                        .addBox(-4, -140, -5, 9, 9, 9)
                        .addBox(-4, -140, 5, 9, 9, 9)
                        .addBox(4, -140, -14, 9, 9, 9)
                        .addBox(4, -140, -5, 9, 9, 9)
                        .addBox(4, -140, 5, 9, 9, 9)
                        .addBox(-11, -131, -12, 7, 8, 8)
                        .addBox(-11, -131, -4, 7, 8, 8)
                        .addBox(-11, -131, 4, 7, 8, 8)
                        .addBox(-4, -131, -12, 7, 8, 8)
                        .addBox(-4, -131, -4, 7, 8, 8)
                        .addBox(-4, -131, 4, 7, 8, 8)
                        .addBox(4, -131, -12, 7, 8, 8)
                        .addBox(4, -131, -4, 7, 8, 8)
                        .addBox(4, -131, 4, 7, 8, 8)
                        .addBox(-7, -123, -8, 7, 5, 8)
                        .addBox(-7, -123, 0, 7, 5, 8)
                        .addBox(0, -123, -8, 7, 5, 8)
                        .addBox(0, -123, 0, 7, 5, 8),
                PartPose.offsetAndRotation(0, 20, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition coil = body.addOrReplaceChild("coil",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-8, -7, -1, 16, 14, 2),
                PartPose.offsetAndRotation(0, -138, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -2, -16, 4, 5, 18),
                PartPose.offsetAndRotation(-12, -124, -10, -0.4887F, 0.0F, -0.3491F));

        PartDefinition arm_l_tip = arm_l.addOrReplaceChild("arm_l_tip",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -3, -1, 5, 6, 2),
                PartPose.offsetAndRotation(0, 0, -18, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -2, -16, 4, 5, 18),
                PartPose.offsetAndRotation(12, -124, -10, -0.4887F, 0.0F, 0.3491F));

        PartDefinition arm_r_tip = arm_r.addOrReplaceChild("arm_r_tip",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -3, -1, 5, 6, 2),
                PartPose.offsetAndRotation(0, 0, -18, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition handling() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-20, -92, -23, 10, 13, 12)
                        .addBox(-20, -92, -12, 10, 13, 12)
                        .addBox(-20, -92, 0, 10, 13, 12)
                        .addBox(-20, -92, 12, 10, 13, 12)
                        .addBox(-10, -92, -23, 10, 13, 12)
                        .addBox(-10, -92, -12, 10, 13, 12)
                        .addBox(-10, -92, 0, 10, 13, 12)
                        .addBox(-10, -92, 12, 10, 13, 12)
                        .addBox(0, -92, -23, 10, 13, 12)
                        .addBox(0, -92, -12, 10, 13, 12)
                        .addBox(0, -92, 0, 10, 13, 12)
                        .addBox(0, -92, 12, 10, 13, 12)
                        .addBox(10, -92, -23, 10, 13, 12)
                        .addBox(10, -92, -12, 10, 13, 12)
                        .addBox(10, -92, 0, 10, 13, 12)
                        .addBox(10, -92, 12, 10, 13, 12)
                        .addBox(-19, -79, -22, 10, 13, 11)
                        .addBox(-19, -79, -11, 10, 13, 11)
                        .addBox(-19, -79, 0, 10, 13, 11)
                        .addBox(-19, -79, 11, 10, 13, 11)
                        .addBox(-10, -79, -22, 10, 13, 11)
                        .addBox(-10, -79, -11, 10, 13, 11)
                        .addBox(-10, -79, 0, 10, 13, 11)
                        .addBox(-10, -79, 11, 10, 13, 11)
                        .addBox(0, -79, -22, 10, 13, 11)
                        .addBox(0, -79, -11, 10, 13, 11)
                        .addBox(0, -79, 0, 10, 13, 11)
                        .addBox(0, -79, 11, 10, 13, 11)
                        .addBox(10, -79, -22, 10, 13, 11)
                        .addBox(10, -79, -11, 10, 13, 11)
                        .addBox(10, -79, 0, 10, 13, 11)
                        .addBox(10, -79, 11, 10, 13, 11)
                        .addBox(-14, -66, -16, 9, 9, 11)
                        .addBox(-14, -66, -5, 9, 9, 11)
                        .addBox(-14, -66, 5, 9, 9, 11)
                        .addBox(-5, -66, -16, 9, 9, 11)
                        .addBox(-5, -66, -5, 9, 9, 11)
                        .addBox(-5, -66, 5, 9, 9, 11)
                        .addBox(5, -66, -16, 9, 9, 11)
                        .addBox(5, -66, -5, 9, 9, 11)
                        .addBox(5, -66, 5, 9, 9, 11)
                        .addBox(-11, -62, -13, 7, 11, 9)
                        .addBox(-11, -62, -4, 7, 11, 9)
                        .addBox(-11, -62, 4, 7, 11, 9)
                        .addBox(-4, -62, -13, 7, 11, 9)
                        .addBox(-4, -62, -4, 7, 11, 9)
                        .addBox(-4, -62, 4, 7, 11, 9)
                        .addBox(4, -62, -13, 7, 11, 9)
                        .addBox(4, -62, -4, 7, 11, 9)
                        .addBox(4, -62, 4, 7, 11, 9),
                PartPose.offsetAndRotation(0, 13, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -4, -1, 12, 8, 2),
                PartPose.offsetAndRotation(0, -81, -25, 0.0F, 0.0F, 0.0F));

        PartDefinition cage = body.addOrReplaceChild("cage",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-11, 0, -9, 22, 3, 18)
                        .addBox(-11, 30, -9, 22, 3, 18)
                        .addBox(-11, 3, -9, 3, 26, 3)
                        .addBox(-11, 3, 6, 3, 26, 3)
                        .addBox(8, 3, -9, 3, 26, 3)
                        .addBox(8, 3, 6, 3, 26, 3)
                        .addBox(-3, 3, -9, 6, 26, 3)
                        .addBox(-3, 3, 6, 6, 26, 3),
                PartPose.offsetAndRotation(0, -57, -16, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -28, 6, 7, 32)
                        .addBox(-6, 1, -36, 5, 6, 11)
                        .addBox(1, 1, -36, 5, 6, 11),
                PartPose.offsetAndRotation(-17, -73, -14, -0.5585F, 0.0F, -0.2443F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -28, 6, 7, 32)
                        .addBox(-6, 1, -36, 5, 6, 11)
                        .addBox(1, 1, -36, 5, 6, 11),
                PartPose.offsetAndRotation(17, -73, -14, -0.5585F, 0.0F, 0.2443F));

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 33, 4),
                PartPose.offsetAndRotation(-19, -40, -14, -0.1100F, 0.0F, 0.2321F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 31, 4)
                        .addBox(-3, 30, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 33, 0, -0.0385F, 0.0F, 0.0812F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 33, 4),
                PartPose.offsetAndRotation(19, -40, -14, -0.1100F, 0.0F, -0.2321F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 31, 4)
                        .addBox(-3, 30, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 33, 0, -0.0385F, 0.0F, -0.0812F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 33, 4),
                PartPose.offsetAndRotation(-19, -40, 14, 0.1100F, 0.0F, 0.2321F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 31, 4)
                        .addBox(-3, 30, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 33, 0, 0.0385F, 0.0F, 0.0812F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 33, 4),
                PartPose.offsetAndRotation(19, -40, 14, 0.1100F, 0.0F, -0.2321F));

        PartDefinition leg3_shin = leg3.addOrReplaceChild("leg3_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 31, 4)
                        .addBox(-3, 30, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 33, 0, 0.0385F, 0.0F, -0.0812F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition digging() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-21, -61, -25, 14, 8, 12)
                        .addBox(-21, -61, -12, 14, 8, 12)
                        .addBox(-21, -61, 0, 14, 8, 12)
                        .addBox(-21, -61, 12, 14, 8, 12)
                        .addBox(-21, -53, -25, 14, 8, 12)
                        .addBox(-21, -53, -12, 14, 8, 12)
                        .addBox(-21, -53, 0, 14, 8, 12)
                        .addBox(-21, -53, 12, 14, 8, 12)
                        .addBox(-7, -61, -25, 14, 8, 12)
                        .addBox(-7, -61, -12, 14, 8, 12)
                        .addBox(-7, -61, 0, 14, 8, 12)
                        .addBox(-7, -61, 12, 14, 8, 12)
                        .addBox(-7, -53, -25, 14, 8, 12)
                        .addBox(-7, -53, -12, 14, 8, 12)
                        .addBox(-7, -53, 0, 14, 8, 12)
                        .addBox(-7, -53, 12, 14, 8, 12)
                        .addBox(7, -61, -25, 14, 8, 12)
                        .addBox(7, -61, -12, 14, 8, 12)
                        .addBox(7, -61, 0, 14, 8, 12)
                        .addBox(7, -61, 12, 14, 8, 12)
                        .addBox(7, -53, -25, 14, 8, 12)
                        .addBox(7, -53, -12, 14, 8, 12)
                        .addBox(7, -53, 0, 14, 8, 12)
                        .addBox(7, -53, 12, 14, 8, 12)
                        .addBox(-17, -44, -15, 11, 11, 10)
                        .addBox(-17, -44, -6, 11, 11, 10)
                        .addBox(-17, -44, 4, 11, 11, 10)
                        .addBox(-17, -44, 14, 11, 11, 10)
                        .addBox(-6, -44, -15, 11, 11, 10)
                        .addBox(-6, -44, -6, 11, 11, 10)
                        .addBox(-6, -44, 4, 11, 11, 10)
                        .addBox(-6, -44, 14, 11, 11, 10)
                        .addBox(6, -44, -15, 11, 11, 10)
                        .addBox(6, -44, -6, 11, 11, 10)
                        .addBox(6, -44, 4, 11, 11, 10)
                        .addBox(6, -44, 14, 11, 11, 10)
                        .addBox(-10, -61, -17, 10, 6, 7)
                        .addBox(-10, -61, -10, 10, 6, 7)
                        .addBox(-10, -61, -2, 10, 6, 7)
                        .addBox(0, -61, -17, 10, 6, 7)
                        .addBox(0, -61, -10, 10, 6, 7)
                        .addBox(0, -61, -2, 10, 6, 7),
                PartPose.offsetAndRotation(0, 35, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -3, -1, 10, 6, 2),
                PartPose.offsetAndRotation(0, -53, -23, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-11, -9, -12, 22, 19, 13)
                        .addBox(-8, -7, -20, 16, 15, 9)
                        .addBox(-5, -4, -26, 10, 8, 7)
                        .addBox(-2, -2, -30, 4, 4, 5)
                        .addBox(-1, -1, -32, 2, 2, 3),
                PartPose.offsetAndRotation(0, -48, -24, 0.0F, 0.0F, 0.0F));

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 23, 5),
                PartPose.offsetAndRotation(-21, -22, -16, -0.0977F, 0.0F, 0.1833F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 21, 5)
                        .addBox(-3, 20, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 23, 0, -0.0342F, 0.0F, 0.0641F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 23, 5),
                PartPose.offsetAndRotation(21, -22, -16, -0.0977F, 0.0F, -0.1833F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 21, 5)
                        .addBox(-3, 20, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 23, 0, -0.0342F, 0.0F, -0.0641F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 23, 5),
                PartPose.offsetAndRotation(-21, -22, 12, 0.0733F, 0.0F, 0.1833F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 21, 5)
                        .addBox(-3, 20, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 23, 0, 0.0257F, 0.0F, 0.0641F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 23, 5),
                PartPose.offsetAndRotation(21, -22, 12, 0.0733F, 0.0F, -0.1833F));

        PartDefinition leg3_shin = leg3.addOrReplaceChild("leg3_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 21, 5)
                        .addBox(-3, 20, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 23, 0, 0.0257F, 0.0F, -0.0641F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition tempest() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_fl = root.addOrReplaceChild("leg_fl",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 75, 6),
                PartPose.offsetAndRotation(-22, -123, -16, -0.1222F, 0.0F, 0.1571F));

        PartDefinition leg_fl_shin = leg_fl.addOrReplaceChild("leg_fl_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 73, 6)
                        .addBox(-4, 72, -4, 8, 3, 8),
                PartPose.offsetAndRotation(0, 75, 0, -0.0428F, 0.0F, 0.0550F));

        PartDefinition leg_fr = root.addOrReplaceChild("leg_fr",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 75, 6),
                PartPose.offsetAndRotation(22, -123, -16, -0.1222F, 0.0F, -0.1571F));

        PartDefinition leg_fr_shin = leg_fr.addOrReplaceChild("leg_fr_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 73, 6)
                        .addBox(-4, 72, -4, 8, 3, 8),
                PartPose.offsetAndRotation(0, 75, 0, -0.0428F, 0.0F, -0.0550F));

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 75, 6),
                PartPose.offsetAndRotation(-24, -123, 18, 0.1222F, 0.0F, 0.1571F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 73, 6)
                        .addBox(-4, 72, -4, 8, 3, 8),
                PartPose.offsetAndRotation(0, 75, 0, 0.0428F, 0.0F, 0.0550F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 75, 6),
                PartPose.offsetAndRotation(24, -123, 18, 0.1222F, 0.0F, -0.1571F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 73, 6)
                        .addBox(-4, 72, -4, 8, 3, 8),
                PartPose.offsetAndRotation(0, 75, 0, 0.0428F, 0.0F, -0.0550F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-17, -237, -20, 11, 14, 13)
                        .addBox(-17, -237, -7, 11, 14, 13)
                        .addBox(-17, -237, 7, 11, 14, 13)
                        .addBox(-17, -222, -20, 11, 14, 13)
                        .addBox(-17, -222, -7, 11, 14, 13)
                        .addBox(-17, -222, 7, 11, 14, 13)
                        .addBox(-17, -209, -20, 11, 14, 13)
                        .addBox(-17, -209, -7, 11, 14, 13)
                        .addBox(-17, -209, 7, 11, 14, 13)
                        .addBox(-17, -195, -20, 11, 14, 13)
                        .addBox(-17, -195, -7, 11, 14, 13)
                        .addBox(-17, -195, 7, 11, 14, 13)
                        .addBox(-17, -181, -20, 11, 14, 13)
                        .addBox(-17, -181, -7, 11, 14, 13)
                        .addBox(-17, -181, 7, 11, 14, 13)
                        .addBox(-17, -167, -20, 11, 14, 13)
                        .addBox(-17, -167, -7, 11, 14, 13)
                        .addBox(-17, -167, 7, 11, 14, 13)
                        .addBox(-6, -237, -20, 11, 14, 13)
                        .addBox(-6, -237, -7, 11, 14, 13)
                        .addBox(-6, -237, 7, 11, 14, 13)
                        .addBox(-6, -222, -20, 11, 14, 13)
                        .addBox(-6, -222, -7, 11, 14, 13)
                        .addBox(-6, -222, 7, 11, 14, 13)
                        .addBox(-6, -209, -20, 11, 14, 13)
                        .addBox(-6, -209, -7, 11, 14, 13)
                        .addBox(-6, -209, 7, 11, 14, 13)
                        .addBox(-6, -195, -20, 11, 14, 13)
                        .addBox(-6, -195, -7, 11, 14, 13)
                        .addBox(-6, -195, 7, 11, 14, 13)
                        .addBox(-6, -181, -20, 11, 14, 13)
                        .addBox(-6, -181, -7, 11, 14, 13)
                        .addBox(-6, -181, 7, 11, 14, 13)
                        .addBox(-6, -167, -20, 11, 14, 13)
                        .addBox(-6, -167, -7, 11, 14, 13)
                        .addBox(-6, -167, 7, 11, 14, 13)
                        .addBox(6, -237, -20, 11, 14, 13)
                        .addBox(6, -237, -7, 11, 14, 13)
                        .addBox(6, -237, 7, 11, 14, 13)
                        .addBox(6, -222, -20, 11, 14, 13)
                        .addBox(6, -222, -7, 11, 14, 13)
                        .addBox(6, -222, 7, 11, 14, 13)
                        .addBox(6, -209, -20, 11, 14, 13)
                        .addBox(6, -209, -7, 11, 14, 13)
                        .addBox(6, -209, 7, 11, 14, 13)
                        .addBox(6, -195, -20, 11, 14, 13)
                        .addBox(6, -195, -7, 11, 14, 13)
                        .addBox(6, -195, 7, 11, 14, 13)
                        .addBox(6, -181, -20, 11, 14, 13)
                        .addBox(6, -181, -7, 11, 14, 13)
                        .addBox(6, -181, 7, 11, 14, 13)
                        .addBox(6, -167, -20, 11, 14, 13)
                        .addBox(6, -167, -7, 11, 14, 13)
                        .addBox(6, -167, 7, 11, 14, 13)
                        .addBox(-24, -161, -24, 12, 8, 12)
                        .addBox(-24, -161, -12, 12, 8, 12)
                        .addBox(-24, -161, 0, 12, 8, 12)
                        .addBox(-24, -161, 12, 12, 8, 12)
                        .addBox(-24, -153, -24, 12, 8, 12)
                        .addBox(-24, -153, -12, 12, 8, 12)
                        .addBox(-24, -153, 0, 12, 8, 12)
                        .addBox(-24, -153, 12, 12, 8, 12)
                        .addBox(-12, -161, -24, 12, 8, 12)
                        .addBox(-12, -161, -12, 12, 8, 12)
                        .addBox(-12, -161, 0, 12, 8, 12)
                        .addBox(-12, -161, 12, 12, 8, 12)
                        .addBox(-12, -153, -24, 12, 8, 12)
                        .addBox(-12, -153, -12, 12, 8, 12)
                        .addBox(-12, -153, 0, 12, 8, 12)
                        .addBox(-12, -153, 12, 12, 8, 12)
                        .addBox(0, -161, -24, 12, 8, 12)
                        .addBox(0, -161, -12, 12, 8, 12)
                        .addBox(0, -161, 0, 12, 8, 12)
                        .addBox(0, -161, 12, 12, 8, 12)
                        .addBox(0, -153, -24, 12, 8, 12)
                        .addBox(0, -153, -12, 12, 8, 12)
                        .addBox(0, -153, 0, 12, 8, 12)
                        .addBox(0, -153, 12, 12, 8, 12)
                        .addBox(12, -161, -24, 12, 8, 12)
                        .addBox(12, -161, -12, 12, 8, 12)
                        .addBox(12, -161, 0, 12, 8, 12)
                        .addBox(12, -161, 12, 12, 8, 12)
                        .addBox(12, -153, -24, 12, 8, 12)
                        .addBox(12, -153, -12, 12, 8, 12)
                        .addBox(12, -153, 0, 12, 8, 12)
                        .addBox(12, -153, 12, 12, 8, 12)
                        .addBox(-23, -204, -23, 12, 11, 12)
                        .addBox(-23, -204, -12, 12, 11, 12)
                        .addBox(-23, -204, 0, 12, 11, 12)
                        .addBox(-23, -204, 12, 12, 11, 12)
                        .addBox(-23, -193, -23, 12, 11, 12)
                        .addBox(-23, -193, -12, 12, 11, 12)
                        .addBox(-23, -193, 0, 12, 11, 12)
                        .addBox(-23, -193, 12, 12, 11, 12)
                        .addBox(-12, -204, -23, 12, 11, 12)
                        .addBox(-12, -204, -12, 12, 11, 12)
                        .addBox(-12, -204, 0, 12, 11, 12)
                        .addBox(-12, -204, 12, 12, 11, 12)
                        .addBox(-12, -193, -23, 12, 11, 12)
                        .addBox(-12, -193, -12, 12, 11, 12)
                        .addBox(-12, -193, 0, 12, 11, 12)
                        .addBox(-12, -193, 12, 12, 11, 12)
                        .addBox(0, -204, -23, 12, 11, 12)
                        .addBox(0, -204, -12, 12, 11, 12)
                        .addBox(0, -204, 0, 12, 11, 12)
                        .addBox(0, -204, 12, 12, 11, 12)
                        .addBox(0, -193, -23, 12, 11, 12)
                        .addBox(0, -193, -12, 12, 11, 12)
                        .addBox(0, -193, 0, 12, 11, 12)
                        .addBox(0, -193, 12, 12, 11, 12)
                        .addBox(12, -204, -23, 12, 11, 12)
                        .addBox(12, -204, -12, 12, 11, 12)
                        .addBox(12, -204, 0, 12, 11, 12)
                        .addBox(12, -204, 12, 12, 11, 12)
                        .addBox(12, -193, -23, 12, 11, 12)
                        .addBox(12, -193, -12, 12, 11, 12)
                        .addBox(12, -193, 0, 12, 11, 12)
                        .addBox(12, -193, 12, 12, 11, 12),
                PartPose.offsetAndRotation(0, 30, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-10, -8, -16, 20, 16, 22),
                PartPose.offsetAndRotation(0, -237, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -3, -1, 10, 6, 2),
                PartPose.offsetAndRotation(0, 0, -17, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, -7, -20, 14, 14, 24)
                        .addBox(-3, -3, -22, 6, 6, 3),
                PartPose.offsetAndRotation(-16, -216, -20, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, -7, -20, 14, 14, 24)
                        .addBox(-3, -3, -22, 6, 6, 3),
                PartPose.offsetAndRotation(16, -216, -20, 0.0F, 0.0F, 0.0F));

        PartDefinition stack_l = body.addOrReplaceChild("stack_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 31, 10),
                PartPose.offsetAndRotation(-13, -220, 22, 0.0F, 0.0F, 0.0F));

        PartDefinition stack_l_mouth = stack_l.addOrReplaceChild("stack_l_mouth",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -3, -1, 6, 6, 2),
                PartPose.offsetAndRotation(0, 31, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition stack_r = body.addOrReplaceChild("stack_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 31, 10),
                PartPose.offsetAndRotation(13, -220, 22, 0.0F, 0.0F, 0.0F));

        PartDefinition stack_r_mouth = stack_r.addOrReplaceChild("stack_r_mouth",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -3, -1, 6, 6, 2),
                PartPose.offsetAndRotation(0, 31, 0, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition bombarding() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 35, 4),
                PartPose.offsetAndRotation(-12, -44, 0, 0.0F, 0.0F, 0.1745F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 32, 4)
                        .addBox(-3, 31, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 35, 0, 0.0F, 0.0F, 0.0611F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 35, 4),
                PartPose.offsetAndRotation(12, -44, 0, 0.0F, 0.0F, -0.1745F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 32, 4)
                        .addBox(-3, 31, -3, 5, 3, 5),
                PartPose.offsetAndRotation(0, 35, 0, 0.0F, 0.0F, -0.0611F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-16, -101, -18, 11, 13, 12)
                        .addBox(-16, -101, -6, 11, 13, 12)
                        .addBox(-16, -101, 6, 11, 13, 12)
                        .addBox(-16, -89, -18, 11, 13, 12)
                        .addBox(-16, -89, -6, 11, 13, 12)
                        .addBox(-16, -89, 6, 11, 13, 12)
                        .addBox(-16, -77, -18, 11, 13, 12)
                        .addBox(-16, -77, -6, 11, 13, 12)
                        .addBox(-16, -77, 6, 11, 13, 12)
                        .addBox(-5, -101, -18, 11, 13, 12)
                        .addBox(-5, -101, -6, 11, 13, 12)
                        .addBox(-5, -101, 6, 11, 13, 12)
                        .addBox(-5, -89, -18, 11, 13, 12)
                        .addBox(-5, -89, -6, 11, 13, 12)
                        .addBox(-5, -89, 6, 11, 13, 12)
                        .addBox(-5, -77, -18, 11, 13, 12)
                        .addBox(-5, -77, -6, 11, 13, 12)
                        .addBox(-5, -77, 6, 11, 13, 12)
                        .addBox(5, -101, -18, 11, 13, 12)
                        .addBox(5, -101, -6, 11, 13, 12)
                        .addBox(5, -101, 6, 11, 13, 12)
                        .addBox(5, -89, -18, 11, 13, 12)
                        .addBox(5, -89, -6, 11, 13, 12)
                        .addBox(5, -89, 6, 11, 13, 12)
                        .addBox(5, -77, -18, 11, 13, 12)
                        .addBox(5, -77, -6, 11, 13, 12)
                        .addBox(5, -77, 6, 11, 13, 12)
                        .addBox(-12, -103, -5, 8, 9, 7)
                        .addBox(-12, -103, 2, 8, 9, 7)
                        .addBox(-12, -103, 10, 8, 9, 7)
                        .addBox(-4, -103, -5, 8, 9, 7)
                        .addBox(-4, -103, 2, 8, 9, 7)
                        .addBox(-4, -103, 10, 8, 9, 7)
                        .addBox(4, -103, -5, 8, 9, 7)
                        .addBox(4, -103, 2, 8, 9, 7)
                        .addBox(4, -103, 10, 8, 9, 7),
                PartPose.offsetAndRotation(0, 20, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -3, -1, 10, 7, 2),
                PartPose.offsetAndRotation(0, -92, -19, 0.0F, 0.0F, 0.0F));

        PartDefinition cannon = body.addOrReplaceChild("cannon",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-9, -9, -12, 18, 17, 18)
                        .addBox(-8, -7, -16, 16, 5, 6)
                        .addBox(-5, -16, -30, 10, 10, 20)
                        .addBox(-7, -18, -34, 14, 15, 6)
                        .addBox(-3, -15, -36, 6, 7, 3),
                PartPose.offsetAndRotation(0, -106, -6, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition drone() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -15, -8, 4, 4, 5)
                        .addBox(-6, -15, -2, 4, 4, 5)
                        .addBox(-6, -15, 2, 4, 4, 5)
                        .addBox(-2, -15, -8, 4, 4, 5)
                        .addBox(-2, -15, -2, 4, 4, 5)
                        .addBox(-2, -15, 2, 4, 4, 5)
                        .addBox(2, -15, -8, 4, 4, 5)
                        .addBox(2, -15, -2, 4, 4, 5)
                        .addBox(2, -15, 2, 4, 4, 5)
                        .addBox(-8, -11, -8, 5, 4, 6)
                        .addBox(-8, -11, -3, 5, 4, 6)
                        .addBox(-8, -11, 3, 5, 4, 6)
                        .addBox(-2, -11, -8, 5, 4, 6)
                        .addBox(-2, -11, -3, 5, 4, 6)
                        .addBox(-2, -11, 3, 5, 4, 6)
                        .addBox(2, -11, -8, 5, 4, 6)
                        .addBox(2, -11, -3, 5, 4, 6)
                        .addBox(2, -11, 3, 5, 4, 6)
                        .addBox(-6, -7, -6, 6, 3, 4)
                        .addBox(-6, -7, -2, 6, 3, 4)
                        .addBox(-6, -7, 2, 6, 3, 4)
                        .addBox(0, -7, -6, 6, 3, 4)
                        .addBox(0, -7, -2, 6, 3, 4)
                        .addBox(0, -7, 2, 6, 3, 4)
                        .addBox(-4, -3, -4, 4, 2, 4)
                        .addBox(-4, -3, 0, 4, 2, 4)
                        .addBox(0, -3, -4, 4, 2, 4)
                        .addBox(0, -3, 0, 4, 2, 4)
                        .addBox(-6, -15, -6, 3, 1, 3)
                        .addBox(-5, -14, -5, 1, 1, 1)
                        .addBox(-5, -13, -5, 1, 1, 1)
                        .addBox(4, -15, -6, 3, 1, 3)
                        .addBox(5, -14, -5, 1, 1, 1)
                        .addBox(5, -13, -5, 1, 1, 1)
                        .addBox(-6, -15, 4, 3, 1, 3)
                        .addBox(-5, -14, 5, 1, 1, 1)
                        .addBox(-5, -13, 5, 1, 1, 1)
                        .addBox(4, -15, 4, 3, 1, 3)
                        .addBox(5, -14, 5, 1, 1, 1)
                        .addBox(5, -13, 5, 1, 1, 1),
                PartPose.offsetAndRotation(0, 7, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -2, -1, 5, 3, 2),
                PartPose.offsetAndRotation(0, -11, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 12, 2),
                PartPose.offsetAndRotation(-7, -1, -6, -0.0733F, 0.0F, 0.1833F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-1, 10, -1, 2, 3, 2),
                PartPose.offsetAndRotation(0, 12, 0, -0.0257F, 0.0F, 0.0641F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 12, 2),
                PartPose.offsetAndRotation(7, -1, -6, -0.0733F, 0.0F, -0.1833F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-1, 10, -1, 2, 3, 2),
                PartPose.offsetAndRotation(0, 12, 0, -0.0257F, 0.0F, -0.0641F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 12, 2),
                PartPose.offsetAndRotation(-8, -1, 0, 0.0F, 0.0F, 0.2094F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-1, 10, -1, 2, 3, 2),
                PartPose.offsetAndRotation(0, 12, 0, 0.0F, 0.0F, 0.0733F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 12, 2),
                PartPose.offsetAndRotation(8, -1, 0, 0.0F, 0.0F, -0.2094F));

        PartDefinition leg3_shin = leg3.addOrReplaceChild("leg3_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-1, 10, -1, 2, 3, 2),
                PartPose.offsetAndRotation(0, 12, 0, 0.0F, 0.0F, -0.0733F));

        PartDefinition leg4 = root.addOrReplaceChild("leg4",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 12, 2),
                PartPose.offsetAndRotation(-8, -1, 6, 0.0733F, 0.0F, 0.2094F));

        PartDefinition leg4_shin = leg4.addOrReplaceChild("leg4_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-1, 10, -1, 2, 3, 2),
                PartPose.offsetAndRotation(0, 12, 0, 0.0257F, 0.0F, 0.0733F));

        PartDefinition leg5 = root.addOrReplaceChild("leg5",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 12, 2),
                PartPose.offsetAndRotation(8, -1, 6, 0.0733F, 0.0F, -0.2094F));

        PartDefinition leg5_shin = leg5.addOrReplaceChild("leg5_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-1, 10, -1, 2, 3, 2),
                PartPose.offsetAndRotation(0, 12, 0, 0.0257F, 0.0F, -0.0733F));

        PartDefinition leg6 = root.addOrReplaceChild("leg6",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 12, 2),
                PartPose.offsetAndRotation(-6, -1, 9, 0.1100F, 0.0F, 0.1571F));

        PartDefinition leg6_shin = leg6.addOrReplaceChild("leg6_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-1, 10, -1, 2, 3, 2),
                PartPose.offsetAndRotation(0, 12, 0, 0.0385F, 0.0F, 0.0550F));

        PartDefinition leg7 = root.addOrReplaceChild("leg7",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 12, 2),
                PartPose.offsetAndRotation(6, -1, 9, 0.1100F, 0.0F, -0.1571F));

        PartDefinition leg7_shin = leg7.addOrReplaceChild("leg7_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, 0, -1, 2, 11, 2)
                        .addBox(-1, 10, -1, 2, 3, 2),
                PartPose.offsetAndRotation(0, 12, 0, 0.0385F, 0.0F, -0.0550F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition humanTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-14, -123, -16, 9, 11, 11)
                        .addBox(-14, -123, -5, 9, 11, 11)
                        .addBox(-14, -123, 5, 9, 11, 11)
                        .addBox(-5, -123, -16, 9, 11, 11)
                        .addBox(-5, -123, -5, 9, 11, 11)
                        .addBox(-5, -123, 5, 9, 11, 11)
                        .addBox(5, -123, -16, 9, 11, 11)
                        .addBox(5, -123, -5, 9, 11, 11)
                        .addBox(5, -123, 5, 9, 11, 11)
                        .addBox(-18, -112, -20, 12, 13, 10)
                        .addBox(-18, -112, -10, 12, 13, 10)
                        .addBox(-18, -112, 0, 12, 13, 10)
                        .addBox(-18, -112, 10, 12, 13, 10)
                        .addBox(-6, -112, -20, 12, 13, 10)
                        .addBox(-6, -112, -10, 12, 13, 10)
                        .addBox(-6, -112, 0, 12, 13, 10)
                        .addBox(-6, -112, 10, 12, 13, 10)
                        .addBox(6, -112, -20, 12, 13, 10)
                        .addBox(6, -112, -10, 12, 13, 10)
                        .addBox(6, -112, 0, 12, 13, 10)
                        .addBox(6, -112, 10, 12, 13, 10)
                        .addBox(-17, -99, -19, 11, 13, 10)
                        .addBox(-17, -99, -10, 11, 13, 10)
                        .addBox(-17, -99, 0, 11, 13, 10)
                        .addBox(-17, -99, 10, 11, 13, 10)
                        .addBox(-6, -99, -19, 11, 13, 10)
                        .addBox(-6, -99, -10, 11, 13, 10)
                        .addBox(-6, -99, 0, 11, 13, 10)
                        .addBox(-6, -99, 10, 11, 13, 10)
                        .addBox(6, -99, -19, 11, 13, 10)
                        .addBox(6, -99, -10, 11, 13, 10)
                        .addBox(6, -99, 0, 11, 13, 10)
                        .addBox(6, -99, 10, 11, 13, 10)
                        .addBox(-11, -85, -13, 11, 7, 9)
                        .addBox(-11, -85, -4, 11, 7, 9)
                        .addBox(-11, -85, 4, 11, 7, 9)
                        .addBox(0, -85, -13, 11, 7, 9)
                        .addBox(0, -85, -4, 11, 7, 9)
                        .addBox(0, -85, 4, 11, 7, 9)
                        .addBox(-11, -96, -23, 7, 10, 7)
                        .addBox(-11, -96, -16, 7, 10, 7)
                        .addBox(-11, -86, -23, 7, 10, 7)
                        .addBox(-11, -86, -16, 7, 10, 7)
                        .addBox(-4, -96, -23, 7, 10, 7)
                        .addBox(-4, -96, -16, 7, 10, 7)
                        .addBox(-4, -86, -23, 7, 10, 7)
                        .addBox(-4, -86, -16, 7, 10, 7)
                        .addBox(4, -96, -23, 7, 10, 7)
                        .addBox(4, -96, -16, 7, 10, 7)
                        .addBox(4, -86, -23, 7, 10, 7)
                        .addBox(4, -86, -16, 7, 10, 7)
                        .addBox(-8, -85, -24, 8, 9, 4)
                        .addBox(0, -85, -24, 8, 9, 4)
                        .addBox(-10, -72, -12, 10, 11, 8)
                        .addBox(-10, -72, -4, 10, 11, 8)
                        .addBox(-10, -72, 4, 10, 11, 8)
                        .addBox(0, -72, -12, 10, 11, 8)
                        .addBox(0, -72, -4, 10, 11, 8)
                        .addBox(0, -72, 4, 10, 11, 8),
                PartPose.offsetAndRotation(0, 19, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -3, -1, 12, 7, 2),
                PartPose.offsetAndRotation(0, -87, -24, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, -3, -20, 6, 7, 24)
                        .addBox(-2, -2, -22, 4, 4, 3),
                PartPose.offsetAndRotation(-17, -112, -10, 0.0F, 0.0F, 0.1396F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, -3, -20, 6, 7, 24)
                        .addBox(-2, -2, -22, 4, 4, 3),
                PartPose.offsetAndRotation(17, -112, -10, 0.0F, 0.0F, -0.1396F));

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 38, 4),
                PartPose.offsetAndRotation(-14, -53, -8, -0.1396F, 0.0F, 0.2094F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 36, 4)
                        .addBox(-3, 35, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 38, 0, -0.0489F, 0.0F, 0.0733F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 38, 4),
                PartPose.offsetAndRotation(14, -53, -8, -0.1396F, 0.0F, -0.2094F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 36, 4)
                        .addBox(-3, 35, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 38, 0, -0.0489F, 0.0F, -0.0733F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 39, 4),
                PartPose.offsetAndRotation(0, -53, 14, 0.1920F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 37, 4)
                        .addBox(-3, 36, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 39, 0, 0.0672F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition waterTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_fl = root.addOrReplaceChild("leg_fl",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 64, 7),
                PartPose.offsetAndRotation(-22, -97, -18, -0.2094F, 0.0F, 0.3142F));

        PartDefinition leg_fl_shin = leg_fl.addOrReplaceChild("leg_fl_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 60, 7)
                        .addBox(-5, 59, -5, 9, 3, 9),
                PartPose.offsetAndRotation(0, 64, 0, -0.0733F, 0.0F, 0.1100F));

        PartDefinition leg_fr = root.addOrReplaceChild("leg_fr",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 64, 7),
                PartPose.offsetAndRotation(22, -97, -18, -0.2094F, 0.0F, -0.3142F));

        PartDefinition leg_fr_shin = leg_fr.addOrReplaceChild("leg_fr_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 60, 7)
                        .addBox(-5, 59, -5, 9, 3, 9),
                PartPose.offsetAndRotation(0, 64, 0, -0.0733F, 0.0F, -0.1100F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 64, 7),
                PartPose.offsetAndRotation(0, -97, 22, 0.2793F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 60, 7)
                        .addBox(-5, 59, -5, 9, 3, 9),
                PartPose.offsetAndRotation(0, 64, 0, 0.0977F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-29, -121, -29, 14, 18, 14)
                        .addBox(-29, -121, -14, 14, 18, 14)
                        .addBox(-29, -121, 0, 14, 18, 14)
                        .addBox(-29, -121, 14, 14, 18, 14)
                        .addBox(-29, -103, -29, 14, 18, 14)
                        .addBox(-29, -103, -14, 14, 18, 14)
                        .addBox(-29, -103, 0, 14, 18, 14)
                        .addBox(-29, -103, 14, 14, 18, 14)
                        .addBox(-14, -121, -29, 14, 18, 14)
                        .addBox(-14, -121, -14, 14, 18, 14)
                        .addBox(-14, -121, 0, 14, 18, 14)
                        .addBox(-14, -121, 14, 14, 18, 14)
                        .addBox(-14, -103, -29, 14, 18, 14)
                        .addBox(-14, -103, -14, 14, 18, 14)
                        .addBox(-14, -103, 0, 14, 18, 14)
                        .addBox(-14, -103, 14, 14, 18, 14)
                        .addBox(0, -121, -29, 14, 18, 14)
                        .addBox(0, -121, -14, 14, 18, 14)
                        .addBox(0, -121, 0, 14, 18, 14)
                        .addBox(0, -121, 14, 14, 18, 14)
                        .addBox(0, -103, -29, 14, 18, 14)
                        .addBox(0, -103, -14, 14, 18, 14)
                        .addBox(0, -103, 0, 14, 18, 14)
                        .addBox(0, -103, 14, 14, 18, 14)
                        .addBox(14, -121, -29, 14, 18, 14)
                        .addBox(14, -121, -14, 14, 18, 14)
                        .addBox(14, -121, 0, 14, 18, 14)
                        .addBox(14, -121, 14, 14, 18, 14)
                        .addBox(14, -103, -29, 14, 18, 14)
                        .addBox(14, -103, -14, 14, 18, 14)
                        .addBox(14, -103, 0, 14, 18, 14)
                        .addBox(14, -103, 14, 14, 18, 14)
                        .addBox(-20, -83, -20, 13, 8, 13)
                        .addBox(-20, -83, -7, 13, 8, 13)
                        .addBox(-20, -83, 7, 13, 8, 13)
                        .addBox(-7, -83, -20, 13, 8, 13)
                        .addBox(-7, -83, -7, 13, 8, 13)
                        .addBox(-7, -83, 7, 13, 8, 13)
                        .addBox(7, -83, -20, 13, 8, 13)
                        .addBox(7, -83, -7, 13, 8, 13)
                        .addBox(7, -83, 7, 13, 8, 13)
                        .addBox(-13, -127, -13, 9, 8, 9)
                        .addBox(-13, -127, -4, 9, 8, 9)
                        .addBox(-13, -127, 4, 9, 8, 9)
                        .addBox(-4, -127, -13, 9, 8, 9)
                        .addBox(-4, -127, -4, 9, 8, 9)
                        .addBox(-4, -127, 4, 9, 8, 9)
                        .addBox(4, -127, -13, 9, 8, 9)
                        .addBox(4, -127, -4, 9, 8, 9)
                        .addBox(4, -127, 4, 9, 8, 9),
                PartPose.offsetAndRotation(0, -20, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-9, -79, -9, 18, 79, 18)
                        .addBox(-15, -62, -15, 30, 7, 30)
                        .addBox(-13, -42, -13, 26, 6, 26)
                        .addBox(-12, -87, -12, 24, 6, 24)
                        .addBox(-2, -100, -2, 4, 12, 4),
                PartPose.offsetAndRotation(0, -121, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -3, -1, 9, 5, 2),
                PartPose.offsetAndRotation(0, -71, -15, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -4, -18, 8, 8, 36)
                        .addBox(-5, -6, 16, 10, 12, 8)
                        .addBox(-3, -4, -22, 6, 6, 4),
                PartPose.offsetAndRotation(-24, -96, 0, 0.0F, 0.0F, 0.2443F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -4, -18, 8, 8, 36)
                        .addBox(-5, -6, 16, 10, 12, 8)
                        .addBox(-3, -4, -22, 6, 6, 4),
                PartPose.offsetAndRotation(24, -96, 0, 0.0F, 0.0F, -0.2443F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition scoutTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 23, 3),
                PartPose.offsetAndRotation(-8, -22, -4, -0.0698F, 0.0F, 0.1396F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 21, 3)
                        .addBox(-2, 20, -2, 4, 3, 4),
                PartPose.offsetAndRotation(0, 23, 0, -0.0244F, 0.0F, 0.0489F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 23, 3),
                PartPose.offsetAndRotation(8, -22, -4, -0.0698F, 0.0F, -0.1396F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 21, 3)
                        .addBox(-2, 20, -2, 4, 3, 4),
                PartPose.offsetAndRotation(0, 23, 0, -0.0244F, 0.0F, -0.0489F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 23, 3),
                PartPose.offsetAndRotation(0, -22, 8, 0.1571F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 3, 21, 3)
                        .addBox(-2, 20, -2, 4, 3, 4),
                PartPose.offsetAndRotation(0, 23, 0, 0.0550F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-11, -40, -11, 7, 8, 7)
                        .addBox(-11, -40, -4, 7, 8, 7)
                        .addBox(-11, -40, 4, 7, 8, 7)
                        .addBox(-11, -32, -11, 7, 8, 7)
                        .addBox(-11, -32, -4, 7, 8, 7)
                        .addBox(-11, -32, 4, 7, 8, 7)
                        .addBox(-4, -40, -11, 7, 8, 7)
                        .addBox(-4, -40, -4, 7, 8, 7)
                        .addBox(-4, -40, 4, 7, 8, 7)
                        .addBox(-4, -32, -11, 7, 8, 7)
                        .addBox(-4, -32, -4, 7, 8, 7)
                        .addBox(-4, -32, 4, 7, 8, 7)
                        .addBox(4, -40, -11, 7, 8, 7)
                        .addBox(4, -40, -4, 7, 8, 7)
                        .addBox(4, -40, 4, 7, 8, 7)
                        .addBox(4, -32, -11, 7, 8, 7)
                        .addBox(4, -32, -4, 7, 8, 7)
                        .addBox(4, -32, 4, 7, 8, 7)
                        .addBox(-8, -27, -8, 5, 6, 5)
                        .addBox(-8, -27, -3, 5, 6, 5)
                        .addBox(-8, -27, 3, 5, 6, 5)
                        .addBox(-3, -27, -8, 5, 6, 5)
                        .addBox(-3, -27, -3, 5, 6, 5)
                        .addBox(-3, -27, 3, 5, 6, 5)
                        .addBox(3, -27, -8, 5, 6, 5)
                        .addBox(3, -27, -3, 5, 6, 5)
                        .addBox(3, -27, 3, 5, 6, 5),
                PartPose.offsetAndRotation(0, 1, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -2, -1, 6, 4, 2),
                PartPose.offsetAndRotation(0, -35, -11, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, -3, -4, 6, 6, 10)
                        .addBox(-2, -2, -18, 4, 4, 16)
                        .addBox(-4, -4, -6, 8, 8, 4)
                        .addBox(-1, -1, -20, 2, 2, 3),
                PartPose.offsetAndRotation(8, -36, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -11, -2, 4, 13, 4)
                        .addBox(-2, -15, -2, 4, 4, 4),
                PartPose.offsetAndRotation(-8, -42, 6, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition ramTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 14, 5),
                PartPose.offsetAndRotation(-16, -4, -6, -0.1047F, 0.0F, 0.3491F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 12, 5)
                        .addBox(-3, 11, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 14, 0, -0.0367F, 0.0F, 0.1222F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 14, 5),
                PartPose.offsetAndRotation(16, -4, -6, -0.1047F, 0.0F, -0.3491F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 12, 5)
                        .addBox(-3, 11, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 14, 0, -0.0367F, 0.0F, -0.1222F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 14, 5),
                PartPose.offsetAndRotation(0, -4, 12, 0.2793F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 12, 5)
                        .addBox(-3, 11, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 14, 0, 0.0977F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-16, -38, -14, 11, 8, 10)
                        .addBox(-16, -38, -4, 11, 8, 10)
                        .addBox(-16, -38, 6, 11, 8, 10)
                        .addBox(-16, -38, 16, 11, 8, 10)
                        .addBox(-16, -29, -14, 11, 8, 10)
                        .addBox(-16, -29, -4, 11, 8, 10)
                        .addBox(-16, -29, 6, 11, 8, 10)
                        .addBox(-16, -29, 16, 11, 8, 10)
                        .addBox(-5, -38, -14, 11, 8, 10)
                        .addBox(-5, -38, -4, 11, 8, 10)
                        .addBox(-5, -38, 6, 11, 8, 10)
                        .addBox(-5, -38, 16, 11, 8, 10)
                        .addBox(-5, -29, -14, 11, 8, 10)
                        .addBox(-5, -29, -4, 11, 8, 10)
                        .addBox(-5, -29, 6, 11, 8, 10)
                        .addBox(-5, -29, 16, 11, 8, 10)
                        .addBox(5, -38, -14, 11, 8, 10)
                        .addBox(5, -38, -4, 11, 8, 10)
                        .addBox(5, -38, 6, 11, 8, 10)
                        .addBox(5, -38, 16, 11, 8, 10)
                        .addBox(5, -29, -14, 11, 8, 10)
                        .addBox(5, -29, -4, 11, 8, 10)
                        .addBox(5, -29, 6, 11, 8, 10)
                        .addBox(5, -29, 16, 11, 8, 10)
                        .addBox(-11, -29, -23, 7, 11, 7)
                        .addBox(-11, -29, -16, 7, 11, 7)
                        .addBox(-4, -29, -23, 7, 11, 7)
                        .addBox(-4, -29, -16, 7, 11, 7)
                        .addBox(4, -29, -23, 7, 11, 7)
                        .addBox(4, -29, -16, 7, 11, 7),
                PartPose.offsetAndRotation(0, 21, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-24, -13, -6, 48, 25, 6)
                        .addBox(-18, -8, -10, 36, 17, 4)
                        .addBox(-20, -15, -6, 5, 2, 5)
                        .addBox(-19, -13, -5, 3, 2, 3)
                        .addBox(-18, -11, -4, 1, 2, 1)
                        .addBox(-10, -15, -6, 5, 2, 5)
                        .addBox(-9, -13, -5, 3, 2, 3)
                        .addBox(-8, -11, -4, 1, 2, 1)
                        .addBox(-2, -15, -6, 5, 2, 5)
                        .addBox(-1, -13, -5, 3, 2, 3)
                        .addBox(0, -11, -4, 1, 2, 1)
                        .addBox(6, -15, -6, 5, 2, 5)
                        .addBox(7, -13, -5, 3, 2, 3)
                        .addBox(8, -11, -4, 1, 2, 1)
                        .addBox(16, -15, -6, 5, 2, 5)
                        .addBox(17, -13, -5, 3, 2, 3)
                        .addBox(18, -11, -4, 1, 2, 1),
                PartPose.offsetAndRotation(0, -32, -22, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -2, -1, 10, 4, 2),
                PartPose.offsetAndRotation(0, -34, -21, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -4, -10, 8, 8, 20),
                PartPose.offsetAndRotation(-18, -21, 8, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -4, -10, 8, 8, 20),
                PartPose.offsetAndRotation(18, -21, 8, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition uberpod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_fl = root.addOrReplaceChild("leg_fl",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 43, 5),
                PartPose.offsetAndRotation(-16, -62, -12, -0.1745F, 0.0F, 0.2443F));

        PartDefinition leg_fl_shin = leg_fl.addOrReplaceChild("leg_fl_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 41, 5)
                        .addBox(-3, 40, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 43, 0, -0.0611F, 0.0F, 0.0855F));

        PartDefinition leg_fr = root.addOrReplaceChild("leg_fr",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 43, 5),
                PartPose.offsetAndRotation(16, -62, -12, -0.1745F, 0.0F, -0.2443F));

        PartDefinition leg_fr_shin = leg_fr.addOrReplaceChild("leg_fr_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 41, 5)
                        .addBox(-3, 40, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 43, 0, -0.0611F, 0.0F, -0.0855F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 44, 5),
                PartPose.offsetAndRotation(0, -62, 14, 0.2094F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 42, 5)
                        .addBox(-3, 41, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 44, 0, 0.0733F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-12, -155, -13, 12, 10, 9)
                        .addBox(-12, -155, -4, 12, 10, 9)
                        .addBox(-12, -155, 4, 12, 10, 9)
                        .addBox(0, -155, -13, 12, 10, 9)
                        .addBox(0, -155, -4, 12, 10, 9)
                        .addBox(0, -155, 4, 12, 10, 9)
                        .addBox(-18, -144, -19, 12, 7, 10)
                        .addBox(-18, -144, -10, 12, 7, 10)
                        .addBox(-18, -144, 0, 12, 7, 10)
                        .addBox(-18, -144, 10, 12, 7, 10)
                        .addBox(-18, -137, -19, 12, 7, 10)
                        .addBox(-18, -137, -10, 12, 7, 10)
                        .addBox(-18, -137, 0, 12, 7, 10)
                        .addBox(-18, -137, 10, 12, 7, 10)
                        .addBox(-6, -144, -19, 12, 7, 10)
                        .addBox(-6, -144, -10, 12, 7, 10)
                        .addBox(-6, -144, 0, 12, 7, 10)
                        .addBox(-6, -144, 10, 12, 7, 10)
                        .addBox(-6, -137, -19, 12, 7, 10)
                        .addBox(-6, -137, -10, 12, 7, 10)
                        .addBox(-6, -137, 0, 12, 7, 10)
                        .addBox(-6, -137, 10, 12, 7, 10)
                        .addBox(6, -144, -19, 12, 7, 10)
                        .addBox(6, -144, -10, 12, 7, 10)
                        .addBox(6, -144, 0, 12, 7, 10)
                        .addBox(6, -144, 10, 12, 7, 10)
                        .addBox(6, -137, -19, 12, 7, 10)
                        .addBox(6, -137, -10, 12, 7, 10)
                        .addBox(6, -137, 0, 12, 7, 10)
                        .addBox(6, -137, 10, 12, 7, 10)
                        .addBox(-21, -130, -22, 10, 8, 11)
                        .addBox(-21, -130, -11, 10, 8, 11)
                        .addBox(-21, -130, 0, 10, 8, 11)
                        .addBox(-21, -130, 11, 10, 8, 11)
                        .addBox(-21, -122, -22, 10, 8, 11)
                        .addBox(-21, -122, -11, 10, 8, 11)
                        .addBox(-21, -122, 0, 10, 8, 11)
                        .addBox(-21, -122, 11, 10, 8, 11)
                        .addBox(-10, -130, -22, 10, 8, 11)
                        .addBox(-10, -130, -11, 10, 8, 11)
                        .addBox(-10, -130, 0, 10, 8, 11)
                        .addBox(-10, -130, 11, 10, 8, 11)
                        .addBox(-10, -122, -22, 10, 8, 11)
                        .addBox(-10, -122, -11, 10, 8, 11)
                        .addBox(-10, -122, 0, 10, 8, 11)
                        .addBox(-10, -122, 11, 10, 8, 11)
                        .addBox(0, -130, -22, 10, 8, 11)
                        .addBox(0, -130, -11, 10, 8, 11)
                        .addBox(0, -130, 0, 10, 8, 11)
                        .addBox(0, -130, 11, 10, 8, 11)
                        .addBox(0, -122, -22, 10, 8, 11)
                        .addBox(0, -122, -11, 10, 8, 11)
                        .addBox(0, -122, 0, 10, 8, 11)
                        .addBox(0, -122, 11, 10, 8, 11)
                        .addBox(10, -130, -22, 10, 8, 11)
                        .addBox(10, -130, -11, 10, 8, 11)
                        .addBox(10, -130, 0, 10, 8, 11)
                        .addBox(10, -130, 11, 10, 8, 11)
                        .addBox(10, -122, -22, 10, 8, 11)
                        .addBox(10, -122, -11, 10, 8, 11)
                        .addBox(10, -122, 0, 10, 8, 11)
                        .addBox(10, -122, 11, 10, 8, 11)
                        .addBox(-17, -113, -18, 11, 12, 12)
                        .addBox(-17, -113, -6, 11, 12, 12)
                        .addBox(-17, -113, 6, 11, 12, 12)
                        .addBox(-6, -113, -18, 11, 12, 12)
                        .addBox(-6, -113, -6, 11, 12, 12)
                        .addBox(-6, -113, 6, 11, 12, 12)
                        .addBox(6, -113, -18, 11, 12, 12)
                        .addBox(6, -113, -6, 11, 12, 12)
                        .addBox(6, -113, 6, 11, 12, 12)
                        .addBox(-11, -101, -12, 11, 8, 12)
                        .addBox(-11, -101, 0, 11, 8, 12)
                        .addBox(0, -101, -12, 11, 8, 12)
                        .addBox(0, -101, 0, 11, 8, 12)
                        .addBox(-15, -82, -15, 10, 10, 10)
                        .addBox(-15, -82, -5, 10, 10, 10)
                        .addBox(-15, -82, 5, 10, 10, 10)
                        .addBox(-5, -82, -15, 10, 10, 10)
                        .addBox(-5, -82, -5, 10, 10, 10)
                        .addBox(-5, -82, 5, 10, 10, 10)
                        .addBox(5, -82, -15, 10, 10, 10)
                        .addBox(5, -82, -5, 10, 10, 10)
                        .addBox(5, -82, 5, 10, 10, 10),
                PartPose.offsetAndRotation(0, 18, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-7, -4, -1, 14, 8, 2),
                PartPose.offsetAndRotation(0, -115, -20, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -6, -12, 12, 12, 20)
                        .addBox(-3, -3, -16, 6, 6, 3),
                PartPose.offsetAndRotation(-18, -122, -12, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -6, -12, 12, 12, 20)
                        .addBox(-3, -3, -16, 6, 6, 3),
                PartPose.offsetAndRotation(18, -122, -12, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition bunkerTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg0 = root.addOrReplaceChild("leg0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 24, 10),
                PartPose.offsetAndRotation(-34, -25, -20, -0.1047F, 0.0F, 0.2077F));

        PartDefinition leg0_shin = leg0.addOrReplaceChild("leg0_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 22, 10)
                        .addBox(-6, 21, -6, 11, 3, 11),
                PartPose.offsetAndRotation(0, 24, 0, -0.0367F, 0.0F, 0.0727F));

        PartDefinition leg1 = root.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 24, 10),
                PartPose.offsetAndRotation(34, -25, -20, -0.1047F, 0.0F, -0.2077F));

        PartDefinition leg1_shin = leg1.addOrReplaceChild("leg1_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 22, 10)
                        .addBox(-6, 21, -6, 11, 3, 11),
                PartPose.offsetAndRotation(0, 24, 0, -0.0367F, 0.0F, -0.0727F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 24, 10),
                PartPose.offsetAndRotation(-38, -25, 8, 0.0419F, 0.0F, 0.2321F));

        PartDefinition leg2_shin = leg2.addOrReplaceChild("leg2_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 22, 10)
                        .addBox(-6, 21, -6, 11, 3, 11),
                PartPose.offsetAndRotation(0, 24, 0, 0.0147F, 0.0F, 0.0812F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 24, 10),
                PartPose.offsetAndRotation(38, -25, 8, 0.0419F, 0.0F, -0.2321F));

        PartDefinition leg3_shin = leg3.addOrReplaceChild("leg3_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 22, 10)
                        .addBox(-6, 21, -6, 11, 3, 11),
                PartPose.offsetAndRotation(0, 24, 0, 0.0147F, 0.0F, -0.0812F));

        PartDefinition leg4 = root.addOrReplaceChild("leg4",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 24, 10),
                PartPose.offsetAndRotation(-18, -25, 30, 0.1571F, 0.0F, 0.1100F));

        PartDefinition leg4_shin = leg4.addOrReplaceChild("leg4_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 22, 10)
                        .addBox(-6, 21, -6, 11, 3, 11),
                PartPose.offsetAndRotation(0, 24, 0, 0.0550F, 0.0F, 0.0385F));

        PartDefinition leg5 = root.addOrReplaceChild("leg5",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 24, 10),
                PartPose.offsetAndRotation(18, -25, 30, 0.1571F, 0.0F, -0.1100F));

        PartDefinition leg5_shin = leg5.addOrReplaceChild("leg5_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 22, 10)
                        .addBox(-6, 21, -6, 11, 3, 11),
                PartPose.offsetAndRotation(0, 24, 0, 0.0550F, 0.0F, -0.0385F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-38, -98, -31, 13, 13, 12)
                        .addBox(-38, -98, -19, 13, 13, 12)
                        .addBox(-38, -98, -6, 13, 13, 12)
                        .addBox(-38, -98, 6, 13, 13, 12)
                        .addBox(-38, -98, 19, 13, 13, 12)
                        .addBox(-38, -85, -31, 13, 13, 12)
                        .addBox(-38, -85, -19, 13, 13, 12)
                        .addBox(-38, -85, -6, 13, 13, 12)
                        .addBox(-38, -85, 6, 13, 13, 12)
                        .addBox(-38, -85, 19, 13, 13, 12)
                        .addBox(-38, -70, -31, 13, 13, 12)
                        .addBox(-38, -70, -19, 13, 13, 12)
                        .addBox(-38, -70, -6, 13, 13, 12)
                        .addBox(-38, -70, 6, 13, 13, 12)
                        .addBox(-38, -70, 19, 13, 13, 12)
                        .addBox(-25, -98, -31, 13, 13, 12)
                        .addBox(-25, -98, -19, 13, 13, 12)
                        .addBox(-25, -98, -6, 13, 13, 12)
                        .addBox(-25, -98, 6, 13, 13, 12)
                        .addBox(-25, -98, 19, 13, 13, 12)
                        .addBox(-25, -85, -31, 13, 13, 12)
                        .addBox(-25, -85, -19, 13, 13, 12)
                        .addBox(-25, -85, -6, 13, 13, 12)
                        .addBox(-25, -85, 6, 13, 13, 12)
                        .addBox(-25, -85, 19, 13, 13, 12)
                        .addBox(-25, -70, -31, 13, 13, 12)
                        .addBox(-25, -70, -19, 13, 13, 12)
                        .addBox(-25, -70, -6, 13, 13, 12)
                        .addBox(-25, -70, 6, 13, 13, 12)
                        .addBox(-25, -70, 19, 13, 13, 12)
                        .addBox(-13, -98, -31, 13, 13, 12)
                        .addBox(-13, -98, -19, 13, 13, 12)
                        .addBox(-13, -98, -6, 13, 13, 12)
                        .addBox(-13, -98, 6, 13, 13, 12)
                        .addBox(-13, -98, 19, 13, 13, 12)
                        .addBox(-13, -85, -31, 13, 13, 12)
                        .addBox(-13, -85, -19, 13, 13, 12)
                        .addBox(-13, -85, -6, 13, 13, 12)
                        .addBox(-13, -85, 6, 13, 13, 12)
                        .addBox(-13, -85, 19, 13, 13, 12)
                        .addBox(-13, -70, -31, 13, 13, 12)
                        .addBox(-13, -70, -19, 13, 13, 12)
                        .addBox(-13, -70, -6, 13, 13, 12)
                        .addBox(-13, -70, 6, 13, 13, 12)
                        .addBox(-13, -70, 19, 13, 13, 12)
                        .addBox(0, -98, -31, 13, 13, 12)
                        .addBox(0, -98, -19, 13, 13, 12)
                        .addBox(0, -98, -6, 13, 13, 12)
                        .addBox(0, -98, 6, 13, 13, 12)
                        .addBox(0, -98, 19, 13, 13, 12)
                        .addBox(0, -85, -31, 13, 13, 12)
                        .addBox(0, -85, -19, 13, 13, 12)
                        .addBox(0, -85, -6, 13, 13, 12)
                        .addBox(0, -85, 6, 13, 13, 12)
                        .addBox(0, -85, 19, 13, 13, 12)
                        .addBox(0, -70, -31, 13, 13, 12)
                        .addBox(0, -70, -19, 13, 13, 12)
                        .addBox(0, -70, -6, 13, 13, 12)
                        .addBox(0, -70, 6, 13, 13, 12)
                        .addBox(0, -70, 19, 13, 13, 12)
                        .addBox(13, -98, -31, 13, 13, 12)
                        .addBox(13, -98, -19, 13, 13, 12)
                        .addBox(13, -98, -6, 13, 13, 12)
                        .addBox(13, -98, 6, 13, 13, 12)
                        .addBox(13, -98, 19, 13, 13, 12)
                        .addBox(13, -85, -31, 13, 13, 12)
                        .addBox(13, -85, -19, 13, 13, 12)
                        .addBox(13, -85, -6, 13, 13, 12)
                        .addBox(13, -85, 6, 13, 13, 12)
                        .addBox(13, -85, 19, 13, 13, 12)
                        .addBox(13, -70, -31, 13, 13, 12)
                        .addBox(13, -70, -19, 13, 13, 12)
                        .addBox(13, -70, -6, 13, 13, 12)
                        .addBox(13, -70, 6, 13, 13, 12)
                        .addBox(13, -70, 19, 13, 13, 12)
                        .addBox(25, -98, -31, 13, 13, 12)
                        .addBox(25, -98, -19, 13, 13, 12)
                        .addBox(25, -98, -6, 13, 13, 12)
                        .addBox(25, -98, 6, 13, 13, 12)
                        .addBox(25, -98, 19, 13, 13, 12)
                        .addBox(25, -85, -31, 13, 13, 12)
                        .addBox(25, -85, -19, 13, 13, 12)
                        .addBox(25, -85, -6, 13, 13, 12)
                        .addBox(25, -85, 6, 13, 13, 12)
                        .addBox(25, -85, 19, 13, 13, 12)
                        .addBox(25, -70, -31, 13, 13, 12)
                        .addBox(25, -70, -19, 13, 13, 12)
                        .addBox(25, -70, -6, 13, 13, 12)
                        .addBox(25, -70, 6, 13, 13, 12)
                        .addBox(25, -70, 19, 13, 13, 12)
                        .addBox(-28, -57, -23, 11, 10, 12)
                        .addBox(-28, -57, -12, 11, 10, 12)
                        .addBox(-28, -57, 0, 11, 10, 12)
                        .addBox(-28, -57, 12, 11, 10, 12)
                        .addBox(-17, -57, -23, 11, 10, 12)
                        .addBox(-17, -57, -12, 11, 10, 12)
                        .addBox(-17, -57, 0, 11, 10, 12)
                        .addBox(-17, -57, 12, 11, 10, 12)
                        .addBox(-6, -57, -23, 11, 10, 12)
                        .addBox(-6, -57, -12, 11, 10, 12)
                        .addBox(-6, -57, 0, 11, 10, 12)
                        .addBox(-6, -57, 12, 11, 10, 12)
                        .addBox(6, -57, -23, 11, 10, 12)
                        .addBox(6, -57, -12, 11, 10, 12)
                        .addBox(6, -57, 0, 11, 10, 12)
                        .addBox(6, -57, 12, 11, 10, 12)
                        .addBox(17, -57, -23, 11, 10, 12)
                        .addBox(17, -57, -12, 11, 10, 12)
                        .addBox(17, -57, 0, 11, 10, 12)
                        .addBox(17, -57, 12, 11, 10, 12)
                        .addBox(-30, -102, -24, 12, 6, 12)
                        .addBox(-30, -102, -12, 12, 6, 12)
                        .addBox(-30, -102, 0, 12, 6, 12)
                        .addBox(-30, -102, 12, 12, 6, 12)
                        .addBox(-18, -102, -24, 12, 6, 12)
                        .addBox(-18, -102, -12, 12, 6, 12)
                        .addBox(-18, -102, 0, 12, 6, 12)
                        .addBox(-18, -102, 12, 12, 6, 12)
                        .addBox(-6, -102, -24, 12, 6, 12)
                        .addBox(-6, -102, -12, 12, 6, 12)
                        .addBox(-6, -102, 0, 12, 6, 12)
                        .addBox(-6, -102, 12, 12, 6, 12)
                        .addBox(6, -102, -24, 12, 6, 12)
                        .addBox(6, -102, -12, 12, 6, 12)
                        .addBox(6, -102, 0, 12, 6, 12)
                        .addBox(6, -102, 12, 12, 6, 12)
                        .addBox(18, -102, -24, 12, 6, 12)
                        .addBox(18, -102, -12, 12, 6, 12)
                        .addBox(18, -102, 0, 12, 6, 12)
                        .addBox(18, -102, 12, 12, 6, 12),
                PartPose.offsetAndRotation(0, 22, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition arm0 = body.addOrReplaceChild("arm0",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-1, -2, -3, 4, 6, 6)
                        .addBox(-2, -3, -5, 5, 8, 10),
                PartPose.offsetAndRotation(-39, -90, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition arm1 = body.addOrReplaceChild("arm1",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-1, -2, -3, 4, 6, 6)
                        .addBox(-2, -3, -5, 5, 8, 10),
                PartPose.offsetAndRotation(39, -90, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition arm2 = body.addOrReplaceChild("arm2",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-1, -2, -3, 4, 6, 6)
                        .addBox(-2, -3, -5, 5, 8, 10),
                PartPose.offsetAndRotation(-39, -80, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition arm3 = body.addOrReplaceChild("arm3",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-1, -2, -3, 4, 6, 6)
                        .addBox(-2, -3, -5, 5, 8, 10),
                PartPose.offsetAndRotation(39, -80, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition arm4 = body.addOrReplaceChild("arm4",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-1, -2, -3, 4, 6, 6)
                        .addBox(-2, -3, -5, 5, 8, 10),
                PartPose.offsetAndRotation(-39, -69, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition arm5 = body.addOrReplaceChild("arm5",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-1, -2, -3, 4, 6, 6)
                        .addBox(-2, -3, -5, 5, 8, 10),
                PartPose.offsetAndRotation(39, -69, -8, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -10, -6, 12, 10, 12)
                        .addBox(-2, -8, -16, 4, 4, 12)
                        .addBox(-3, -6, -18, 6, 5, 3),
                PartPose.offsetAndRotation(0, -102, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -3, -1, 12, 6, 2),
                PartPose.offsetAndRotation(0, -86, -32, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition flamerTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 29, 4),
                PartPose.offsetAndRotation(-14, -35, -8, -0.1396F, 0.0F, 0.2094F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 27, 4)
                        .addBox(-3, 26, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 29, 0, -0.0489F, 0.0F, 0.0733F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 29, 4),
                PartPose.offsetAndRotation(14, -35, -8, -0.1396F, 0.0F, -0.2094F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 27, 4)
                        .addBox(-3, 26, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 29, 0, -0.0489F, 0.0F, -0.0733F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 30, 4),
                PartPose.offsetAndRotation(0, -35, 14, 0.2094F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, 0, -2, 4, 28, 4)
                        .addBox(-3, 27, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 30, 0, 0.0733F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-15, -90, -17, 10, 11, 11)
                        .addBox(-15, -90, -6, 10, 11, 11)
                        .addBox(-15, -90, 6, 10, 11, 11)
                        .addBox(-15, -80, -17, 10, 11, 11)
                        .addBox(-15, -80, -6, 10, 11, 11)
                        .addBox(-15, -80, 6, 10, 11, 11)
                        .addBox(-15, -69, -17, 10, 11, 11)
                        .addBox(-15, -69, -6, 10, 11, 11)
                        .addBox(-15, -69, 6, 10, 11, 11)
                        .addBox(-5, -90, -17, 10, 11, 11)
                        .addBox(-5, -90, -6, 10, 11, 11)
                        .addBox(-5, -90, 6, 10, 11, 11)
                        .addBox(-5, -80, -17, 10, 11, 11)
                        .addBox(-5, -80, -6, 10, 11, 11)
                        .addBox(-5, -80, 6, 10, 11, 11)
                        .addBox(-5, -69, -17, 10, 11, 11)
                        .addBox(-5, -69, -6, 10, 11, 11)
                        .addBox(-5, -69, 6, 10, 11, 11)
                        .addBox(5, -90, -17, 10, 11, 11)
                        .addBox(5, -90, -6, 10, 11, 11)
                        .addBox(5, -90, 6, 10, 11, 11)
                        .addBox(5, -80, -17, 10, 11, 11)
                        .addBox(5, -80, -6, 10, 11, 11)
                        .addBox(5, -80, 6, 10, 11, 11)
                        .addBox(5, -69, -17, 10, 11, 11)
                        .addBox(5, -69, -6, 10, 11, 11)
                        .addBox(5, -69, 6, 10, 11, 11)
                        .addBox(-11, -61, -13, 7, 8, 9)
                        .addBox(-11, -61, -4, 7, 8, 9)
                        .addBox(-11, -61, 4, 7, 8, 9)
                        .addBox(-4, -61, -13, 7, 8, 9)
                        .addBox(-4, -61, -4, 7, 8, 9)
                        .addBox(-4, -61, 4, 7, 8, 9)
                        .addBox(4, -61, -13, 7, 8, 9)
                        .addBox(4, -61, -4, 7, 8, 9)
                        .addBox(4, -61, 4, 7, 8, 9),
                PartPose.offsetAndRotation(0, 20, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -3, -1, 10, 5, 2),
                PartPose.offsetAndRotation(0, -82, -18, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -5, -14, 10, 11, 16)
                        .addBox(-3, -3, -28, 6, 6, 16)
                        .addBox(-2, -2, -32, 4, 4, 4),
                PartPose.offsetAndRotation(0, -84, -16, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -19, -6, 12, 27, 12)
                        .addBox(-4, -23, -4, 8, 5, 8)
                        .addBox(-2, -25, -2, 4, 3, 4),
                PartPose.offsetAndRotation(-19, -82, 10, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -19, -6, 12, 27, 12)
                        .addBox(-4, -23, -4, 8, 5, 8)
                        .addBox(-2, -25, -2, 4, 3, 4),
                PartPose.offsetAndRotation(19, -82, 10, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition tankTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_fl = root.addOrReplaceChild("leg_fl",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, 0, -6, 12, 63, 12),
                PartPose.offsetAndRotation(-32, -95, -26, -0.2793F, 0.0F, 0.3840F));

        PartDefinition leg_fl_shin = leg_fl.addOrReplaceChild("leg_fl_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, 0, -6, 12, 59, 12)
                        .addBox(-7, 58, -7, 14, 3, 14),
                PartPose.offsetAndRotation(0, 63, 0, -0.0977F, 0.0F, 0.1344F));

        PartDefinition leg_fr = root.addOrReplaceChild("leg_fr",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, 0, -6, 12, 63, 12),
                PartPose.offsetAndRotation(32, -95, -26, -0.2793F, 0.0F, -0.3840F));

        PartDefinition leg_fr_shin = leg_fr.addOrReplaceChild("leg_fr_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, 0, -6, 12, 59, 12)
                        .addBox(-7, 58, -7, 14, 3, 14),
                PartPose.offsetAndRotation(0, 63, 0, -0.0977F, 0.0F, -0.1344F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, 0, -6, 12, 64, 12),
                PartPose.offsetAndRotation(0, -95, 32, 0.3491F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, 0, -6, 12, 60, 12)
                        .addBox(-7, 59, -7, 14, 3, 14),
                PartPose.offsetAndRotation(0, 64, 0, 0.1222F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-32, -204, -37, 16, 15, 15)
                        .addBox(-32, -204, -22, 16, 15, 15)
                        .addBox(-32, -204, -7, 16, 15, 15)
                        .addBox(-32, -204, 7, 16, 15, 15)
                        .addBox(-32, -204, 22, 16, 15, 15)
                        .addBox(-32, -189, -37, 16, 15, 15)
                        .addBox(-32, -189, -22, 16, 15, 15)
                        .addBox(-32, -189, -7, 16, 15, 15)
                        .addBox(-32, -189, 7, 16, 15, 15)
                        .addBox(-32, -189, 22, 16, 15, 15)
                        .addBox(-32, -173, -37, 16, 15, 15)
                        .addBox(-32, -173, -22, 16, 15, 15)
                        .addBox(-32, -173, -7, 16, 15, 15)
                        .addBox(-32, -173, 7, 16, 15, 15)
                        .addBox(-32, -173, 22, 16, 15, 15)
                        .addBox(-32, -159, -37, 16, 15, 15)
                        .addBox(-32, -159, -22, 16, 15, 15)
                        .addBox(-32, -159, -7, 16, 15, 15)
                        .addBox(-32, -159, 7, 16, 15, 15)
                        .addBox(-32, -159, 22, 16, 15, 15)
                        .addBox(-32, -144, -37, 16, 15, 15)
                        .addBox(-32, -144, -22, 16, 15, 15)
                        .addBox(-32, -144, -7, 16, 15, 15)
                        .addBox(-32, -144, 7, 16, 15, 15)
                        .addBox(-32, -144, 22, 16, 15, 15)
                        .addBox(-16, -204, -37, 16, 15, 15)
                        .addBox(-16, -204, -22, 16, 15, 15)
                        .addBox(-16, -204, -7, 16, 15, 15)
                        .addBox(-16, -204, 7, 16, 15, 15)
                        .addBox(-16, -204, 22, 16, 15, 15)
                        .addBox(-16, -189, -37, 16, 15, 15)
                        .addBox(-16, -189, -22, 16, 15, 15)
                        .addBox(-16, -189, -7, 16, 15, 15)
                        .addBox(-16, -189, 7, 16, 15, 15)
                        .addBox(-16, -189, 22, 16, 15, 15)
                        .addBox(-16, -173, -37, 16, 15, 15)
                        .addBox(-16, -173, -22, 16, 15, 15)
                        .addBox(-16, -173, -7, 16, 15, 15)
                        .addBox(-16, -173, 7, 16, 15, 15)
                        .addBox(-16, -173, 22, 16, 15, 15)
                        .addBox(-16, -159, -37, 16, 15, 15)
                        .addBox(-16, -159, -22, 16, 15, 15)
                        .addBox(-16, -159, -7, 16, 15, 15)
                        .addBox(-16, -159, 7, 16, 15, 15)
                        .addBox(-16, -159, 22, 16, 15, 15)
                        .addBox(-16, -144, -37, 16, 15, 15)
                        .addBox(-16, -144, -22, 16, 15, 15)
                        .addBox(-16, -144, -7, 16, 15, 15)
                        .addBox(-16, -144, 7, 16, 15, 15)
                        .addBox(-16, -144, 22, 16, 15, 15)
                        .addBox(0, -204, -37, 16, 15, 15)
                        .addBox(0, -204, -22, 16, 15, 15)
                        .addBox(0, -204, -7, 16, 15, 15)
                        .addBox(0, -204, 7, 16, 15, 15)
                        .addBox(0, -204, 22, 16, 15, 15)
                        .addBox(0, -189, -37, 16, 15, 15)
                        .addBox(0, -189, -22, 16, 15, 15)
                        .addBox(0, -189, -7, 16, 15, 15)
                        .addBox(0, -189, 7, 16, 15, 15)
                        .addBox(0, -189, 22, 16, 15, 15)
                        .addBox(0, -173, -37, 16, 15, 15)
                        .addBox(0, -173, -22, 16, 15, 15)
                        .addBox(0, -173, -7, 16, 15, 15)
                        .addBox(0, -173, 7, 16, 15, 15)
                        .addBox(0, -173, 22, 16, 15, 15)
                        .addBox(0, -159, -37, 16, 15, 15)
                        .addBox(0, -159, -22, 16, 15, 15)
                        .addBox(0, -159, -7, 16, 15, 15)
                        .addBox(0, -159, 7, 16, 15, 15)
                        .addBox(0, -159, 22, 16, 15, 15)
                        .addBox(0, -144, -37, 16, 15, 15)
                        .addBox(0, -144, -22, 16, 15, 15)
                        .addBox(0, -144, -7, 16, 15, 15)
                        .addBox(0, -144, 7, 16, 15, 15)
                        .addBox(0, -144, 22, 16, 15, 15)
                        .addBox(16, -204, -37, 16, 15, 15)
                        .addBox(16, -204, -22, 16, 15, 15)
                        .addBox(16, -204, -7, 16, 15, 15)
                        .addBox(16, -204, 7, 16, 15, 15)
                        .addBox(16, -204, 22, 16, 15, 15)
                        .addBox(16, -189, -37, 16, 15, 15)
                        .addBox(16, -189, -22, 16, 15, 15)
                        .addBox(16, -189, -7, 16, 15, 15)
                        .addBox(16, -189, 7, 16, 15, 15)
                        .addBox(16, -189, 22, 16, 15, 15)
                        .addBox(16, -173, -37, 16, 15, 15)
                        .addBox(16, -173, -22, 16, 15, 15)
                        .addBox(16, -173, -7, 16, 15, 15)
                        .addBox(16, -173, 7, 16, 15, 15)
                        .addBox(16, -173, 22, 16, 15, 15)
                        .addBox(16, -159, -37, 16, 15, 15)
                        .addBox(16, -159, -22, 16, 15, 15)
                        .addBox(16, -159, -7, 16, 15, 15)
                        .addBox(16, -159, 7, 16, 15, 15)
                        .addBox(16, -159, 22, 16, 15, 15)
                        .addBox(16, -144, -37, 16, 15, 15)
                        .addBox(16, -144, -22, 16, 15, 15)
                        .addBox(16, -144, -7, 16, 15, 15)
                        .addBox(16, -144, 7, 16, 15, 15)
                        .addBox(16, -144, 22, 16, 15, 15)
                        .addBox(-42, -122, -44, 17, 12, 18)
                        .addBox(-42, -122, -26, 17, 12, 18)
                        .addBox(-42, -122, -9, 17, 12, 18)
                        .addBox(-42, -122, 9, 17, 12, 18)
                        .addBox(-42, -122, 26, 17, 12, 18)
                        .addBox(-42, -110, -44, 17, 12, 18)
                        .addBox(-42, -110, -26, 17, 12, 18)
                        .addBox(-42, -110, -9, 17, 12, 18)
                        .addBox(-42, -110, 9, 17, 12, 18)
                        .addBox(-42, -110, 26, 17, 12, 18)
                        .addBox(-25, -122, -44, 17, 12, 18)
                        .addBox(-25, -122, -26, 17, 12, 18)
                        .addBox(-25, -122, -9, 17, 12, 18)
                        .addBox(-25, -122, 9, 17, 12, 18)
                        .addBox(-25, -122, 26, 17, 12, 18)
                        .addBox(-25, -110, -44, 17, 12, 18)
                        .addBox(-25, -110, -26, 17, 12, 18)
                        .addBox(-25, -110, -9, 17, 12, 18)
                        .addBox(-25, -110, 9, 17, 12, 18)
                        .addBox(-25, -110, 26, 17, 12, 18)
                        .addBox(-8, -122, -44, 17, 12, 18)
                        .addBox(-8, -122, -26, 17, 12, 18)
                        .addBox(-8, -122, -9, 17, 12, 18)
                        .addBox(-8, -122, 9, 17, 12, 18)
                        .addBox(-8, -122, 26, 17, 12, 18)
                        .addBox(-8, -110, -44, 17, 12, 18)
                        .addBox(-8, -110, -26, 17, 12, 18)
                        .addBox(-8, -110, -9, 17, 12, 18)
                        .addBox(-8, -110, 9, 17, 12, 18)
                        .addBox(-8, -110, 26, 17, 12, 18)
                        .addBox(8, -122, -44, 17, 12, 18)
                        .addBox(8, -122, -26, 17, 12, 18)
                        .addBox(8, -122, -9, 17, 12, 18)
                        .addBox(8, -122, 9, 17, 12, 18)
                        .addBox(8, -122, 26, 17, 12, 18)
                        .addBox(8, -110, -44, 17, 12, 18)
                        .addBox(8, -110, -26, 17, 12, 18)
                        .addBox(8, -110, -9, 17, 12, 18)
                        .addBox(8, -110, 9, 17, 12, 18)
                        .addBox(8, -110, 26, 17, 12, 18)
                        .addBox(25, -122, -44, 17, 12, 18)
                        .addBox(25, -122, -26, 17, 12, 18)
                        .addBox(25, -122, -9, 17, 12, 18)
                        .addBox(25, -122, 9, 17, 12, 18)
                        .addBox(25, -122, 26, 17, 12, 18)
                        .addBox(25, -110, -44, 17, 12, 18)
                        .addBox(25, -110, -26, 17, 12, 18)
                        .addBox(25, -110, -9, 17, 12, 18)
                        .addBox(25, -110, 9, 17, 12, 18)
                        .addBox(25, -110, 26, 17, 12, 18)
                        .addBox(-46, -159, -46, 15, 10, 15)
                        .addBox(-46, -159, -31, 15, 10, 15)
                        .addBox(-46, -159, -15, 15, 10, 15)
                        .addBox(-46, -159, 0, 15, 10, 15)
                        .addBox(-46, -159, 15, 15, 10, 15)
                        .addBox(-46, -159, 31, 15, 10, 15)
                        .addBox(-46, -149, -46, 15, 10, 15)
                        .addBox(-46, -149, -31, 15, 10, 15)
                        .addBox(-46, -149, -15, 15, 10, 15)
                        .addBox(-46, -149, 0, 15, 10, 15)
                        .addBox(-46, -149, 15, 15, 10, 15)
                        .addBox(-46, -149, 31, 15, 10, 15)
                        .addBox(-31, -159, -46, 15, 10, 15)
                        .addBox(-31, -159, -31, 15, 10, 15)
                        .addBox(-31, -159, -15, 15, 10, 15)
                        .addBox(-31, -159, 0, 15, 10, 15)
                        .addBox(-31, -159, 15, 15, 10, 15)
                        .addBox(-31, -159, 31, 15, 10, 15)
                        .addBox(-31, -149, -46, 15, 10, 15)
                        .addBox(-31, -149, -31, 15, 10, 15)
                        .addBox(-31, -149, -15, 15, 10, 15)
                        .addBox(-31, -149, 0, 15, 10, 15)
                        .addBox(-31, -149, 15, 15, 10, 15)
                        .addBox(-31, -149, 31, 15, 10, 15)
                        .addBox(-15, -159, -46, 15, 10, 15)
                        .addBox(-15, -159, -31, 15, 10, 15)
                        .addBox(-15, -159, -15, 15, 10, 15)
                        .addBox(-15, -159, 0, 15, 10, 15)
                        .addBox(-15, -159, 15, 15, 10, 15)
                        .addBox(-15, -159, 31, 15, 10, 15)
                        .addBox(-15, -149, -46, 15, 10, 15)
                        .addBox(-15, -149, -31, 15, 10, 15)
                        .addBox(-15, -149, -15, 15, 10, 15)
                        .addBox(-15, -149, 0, 15, 10, 15)
                        .addBox(-15, -149, 15, 15, 10, 15)
                        .addBox(-15, -149, 31, 15, 10, 15)
                        .addBox(0, -159, -46, 15, 10, 15)
                        .addBox(0, -159, -31, 15, 10, 15)
                        .addBox(0, -159, -15, 15, 10, 15)
                        .addBox(0, -159, 0, 15, 10, 15)
                        .addBox(0, -159, 15, 15, 10, 15)
                        .addBox(0, -159, 31, 15, 10, 15)
                        .addBox(0, -149, -46, 15, 10, 15)
                        .addBox(0, -149, -31, 15, 10, 15)
                        .addBox(0, -149, -15, 15, 10, 15)
                        .addBox(0, -149, 0, 15, 10, 15)
                        .addBox(0, -149, 15, 15, 10, 15)
                        .addBox(0, -149, 31, 15, 10, 15)
                        .addBox(15, -159, -46, 15, 10, 15)
                        .addBox(15, -159, -31, 15, 10, 15)
                        .addBox(15, -159, -15, 15, 10, 15)
                        .addBox(15, -159, 0, 15, 10, 15)
                        .addBox(15, -159, 15, 15, 10, 15)
                        .addBox(15, -159, 31, 15, 10, 15)
                        .addBox(15, -149, -46, 15, 10, 15)
                        .addBox(15, -149, -31, 15, 10, 15)
                        .addBox(15, -149, -15, 15, 10, 15)
                        .addBox(15, -149, 0, 15, 10, 15)
                        .addBox(15, -149, 15, 15, 10, 15)
                        .addBox(15, -149, 31, 15, 10, 15)
                        .addBox(31, -159, -46, 15, 10, 15)
                        .addBox(31, -159, -31, 15, 10, 15)
                        .addBox(31, -159, -15, 15, 10, 15)
                        .addBox(31, -159, 0, 15, 10, 15)
                        .addBox(31, -159, 15, 15, 10, 15)
                        .addBox(31, -159, 31, 15, 10, 15)
                        .addBox(31, -149, -46, 15, 10, 15)
                        .addBox(31, -149, -31, 15, 10, 15)
                        .addBox(31, -149, -15, 15, 10, 15)
                        .addBox(31, -149, 0, 15, 10, 15)
                        .addBox(31, -149, 15, 15, 10, 15)
                        .addBox(31, -149, 31, 15, 10, 15),
                PartPose.offsetAndRotation(0, 3, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-8, -4, -1, 16, 8, 2),
                PartPose.offsetAndRotation(0, -169, -38, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-11, -11, -16, 22, 22, 26)
                        .addBox(-6, -6, -60, 12, 12, 46)
                        .addBox(-8, -8, -68, 16, 16, 9)
                        .addBox(-5, -5, -70, 10, 10, 4),
                PartPose.offsetAndRotation(-46, -194, -18, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-11, -11, -16, 22, 22, 26)
                        .addBox(-6, -6, -60, 12, 12, 46)
                        .addBox(-8, -8, -68, 16, 16, 9)
                        .addBox(-5, -5, -70, 10, 10, 4),
                PartPose.offsetAndRotation(46, -194, -18, 0.0F, 0.0F, 0.0F));

        PartDefinition arm0 = body.addOrReplaceChild("arm0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -8, -14, 16, 16, 20)
                        .addBox(-4, -4, -46, 8, 8, 34)
                        .addBox(-3, -3, -49, 6, 6, 4),
                PartPose.offsetAndRotation(0, -216, -30, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-12, -14, -14, 24, 20, 26)
                        .addBox(-8, -35, -8, 16, 22, 16)
                        .addBox(-10, -41, -10, 20, 8, 20)
                        .addBox(-5, -39, -12, 10, 6, 4),
                PartPose.offsetAndRotation(0, -204, 20, 0.0F, 0.0F, 0.0F));

        PartDefinition stack_r = body.addOrReplaceChild("stack_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -27, -6, 12, 29, 12),
                PartPose.offsetAndRotation(28, -204, 10, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition taxiTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 17, 5),
                PartPose.offsetAndRotation(-18, -9, -8, -0.1047F, 0.0F, 0.3142F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 15, 5)
                        .addBox(-3, 14, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 17, 0, -0.0367F, 0.0F, 0.1100F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 17, 5),
                PartPose.offsetAndRotation(18, -9, -8, -0.1047F, 0.0F, -0.3142F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 15, 5)
                        .addBox(-3, 14, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 17, 0, -0.0367F, 0.0F, -0.1100F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 17, 5),
                PartPose.offsetAndRotation(0, -9, 16, 0.2793F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 15, 5)
                        .addBox(-3, 14, -3, 6, 3, 6),
                PartPose.offsetAndRotation(0, 17, 0, 0.0977F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-22, -40, -20, 11, 7, 10)
                        .addBox(-22, -40, -10, 11, 7, 10)
                        .addBox(-22, -40, 0, 11, 7, 10)
                        .addBox(-22, -40, 10, 11, 7, 10)
                        .addBox(-22, -33, -20, 11, 7, 10)
                        .addBox(-22, -33, -10, 11, 7, 10)
                        .addBox(-22, -33, 0, 11, 7, 10)
                        .addBox(-22, -33, 10, 11, 7, 10)
                        .addBox(-11, -40, -20, 11, 7, 10)
                        .addBox(-11, -40, -10, 11, 7, 10)
                        .addBox(-11, -40, 0, 11, 7, 10)
                        .addBox(-11, -40, 10, 11, 7, 10)
                        .addBox(-11, -33, -20, 11, 7, 10)
                        .addBox(-11, -33, -10, 11, 7, 10)
                        .addBox(-11, -33, 0, 11, 7, 10)
                        .addBox(-11, -33, 10, 11, 7, 10)
                        .addBox(0, -40, -20, 11, 7, 10)
                        .addBox(0, -40, -10, 11, 7, 10)
                        .addBox(0, -40, 0, 11, 7, 10)
                        .addBox(0, -40, 10, 11, 7, 10)
                        .addBox(0, -33, -20, 11, 7, 10)
                        .addBox(0, -33, -10, 11, 7, 10)
                        .addBox(0, -33, 0, 11, 7, 10)
                        .addBox(0, -33, 10, 11, 7, 10)
                        .addBox(11, -40, -20, 11, 7, 10)
                        .addBox(11, -40, -10, 11, 7, 10)
                        .addBox(11, -40, 0, 11, 7, 10)
                        .addBox(11, -40, 10, 11, 7, 10)
                        .addBox(11, -33, -20, 11, 7, 10)
                        .addBox(11, -33, -10, 11, 7, 10)
                        .addBox(11, -33, 0, 11, 7, 10)
                        .addBox(11, -33, 10, 11, 7, 10)
                        .addBox(-15, -47, -14, 10, 6, 9)
                        .addBox(-15, -47, -5, 10, 6, 9)
                        .addBox(-15, -47, 5, 10, 6, 9)
                        .addBox(-5, -47, -14, 10, 6, 9)
                        .addBox(-5, -47, -5, 10, 6, 9)
                        .addBox(-5, -47, 5, 10, 6, 9)
                        .addBox(5, -47, -14, 10, 6, 9)
                        .addBox(5, -47, -5, 10, 6, 9)
                        .addBox(5, -47, 5, 10, 6, 9),
                PartPose.offsetAndRotation(0, 16, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = body.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-4, -2, -1, 8, 4, 2),
                PartPose.offsetAndRotation(0, -42, -21, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_l = body.addOrReplaceChild("arm_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -11, -18, 4, 11, 36)
                        .addBox(-3, -13, -20, 6, 4, 4)
                        .addBox(-2, -15, -21, 4, 3, 3),
                PartPose.offsetAndRotation(-21, -40, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition arm_r = body.addOrReplaceChild("arm_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2, -11, -18, 4, 11, 36)
                        .addBox(-3, -13, -20, 6, 4, 4)
                        .addBox(-2, -15, -21, 4, 3, 3),
                PartPose.offsetAndRotation(21, -40, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-10, -13, -4, 20, 13, 6),
                PartPose.offsetAndRotation(0, -40, -14, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition constructionTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 84, 10),
                PartPose.offsetAndRotation(-28, -148, -16, -0.1396F, 0.0F, 0.1745F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 88, 10)
                        .addBox(-6, 87, -6, 12, 3, 12),
                PartPose.offsetAndRotation(0, 84, 0, -0.0489F, 0.0F, 0.0611F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 84, 10),
                PartPose.offsetAndRotation(28, -148, -16, -0.1396F, 0.0F, -0.1745F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 88, 10)
                        .addBox(-6, 87, -6, 12, 3, 12),
                PartPose.offsetAndRotation(0, 84, 0, -0.0489F, 0.0F, -0.0611F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 84, 10),
                PartPose.offsetAndRotation(0, -148, 30, 0.1571F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, 0, -5, 10, 88, 10)
                        .addBox(-6, 87, -6, 12, 3, 12),
                PartPose.offsetAndRotation(0, 84, 0, 0.0550F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-20, -268, -26, 13, 13, 13)
                        .addBox(-20, -268, -13, 13, 13, 13)
                        .addBox(-20, -268, 0, 13, 13, 13)
                        .addBox(-20, -268, 13, 13, 13, 13)
                        .addBox(-20, -255, -26, 13, 13, 13)
                        .addBox(-20, -255, -13, 13, 13, 13)
                        .addBox(-20, -255, 0, 13, 13, 13)
                        .addBox(-20, -255, 13, 13, 13, 13)
                        .addBox(-20, -242, -26, 13, 13, 13)
                        .addBox(-20, -242, -13, 13, 13, 13)
                        .addBox(-20, -242, 0, 13, 13, 13)
                        .addBox(-20, -242, 13, 13, 13, 13)
                        .addBox(-20, -230, -26, 13, 13, 13)
                        .addBox(-20, -230, -13, 13, 13, 13)
                        .addBox(-20, -230, 0, 13, 13, 13)
                        .addBox(-20, -230, 13, 13, 13, 13)
                        .addBox(-20, -217, -26, 13, 13, 13)
                        .addBox(-20, -217, -13, 13, 13, 13)
                        .addBox(-20, -217, 0, 13, 13, 13)
                        .addBox(-20, -217, 13, 13, 13, 13)
                        .addBox(-7, -268, -26, 13, 13, 13)
                        .addBox(-7, -268, -13, 13, 13, 13)
                        .addBox(-7, -268, 0, 13, 13, 13)
                        .addBox(-7, -268, 13, 13, 13, 13)
                        .addBox(-7, -255, -26, 13, 13, 13)
                        .addBox(-7, -255, -13, 13, 13, 13)
                        .addBox(-7, -255, 0, 13, 13, 13)
                        .addBox(-7, -255, 13, 13, 13, 13)
                        .addBox(-7, -242, -26, 13, 13, 13)
                        .addBox(-7, -242, -13, 13, 13, 13)
                        .addBox(-7, -242, 0, 13, 13, 13)
                        .addBox(-7, -242, 13, 13, 13, 13)
                        .addBox(-7, -230, -26, 13, 13, 13)
                        .addBox(-7, -230, -13, 13, 13, 13)
                        .addBox(-7, -230, 0, 13, 13, 13)
                        .addBox(-7, -230, 13, 13, 13, 13)
                        .addBox(-7, -217, -26, 13, 13, 13)
                        .addBox(-7, -217, -13, 13, 13, 13)
                        .addBox(-7, -217, 0, 13, 13, 13)
                        .addBox(-7, -217, 13, 13, 13, 13)
                        .addBox(7, -268, -26, 13, 13, 13)
                        .addBox(7, -268, -13, 13, 13, 13)
                        .addBox(7, -268, 0, 13, 13, 13)
                        .addBox(7, -268, 13, 13, 13, 13)
                        .addBox(7, -255, -26, 13, 13, 13)
                        .addBox(7, -255, -13, 13, 13, 13)
                        .addBox(7, -255, 0, 13, 13, 13)
                        .addBox(7, -255, 13, 13, 13, 13)
                        .addBox(7, -242, -26, 13, 13, 13)
                        .addBox(7, -242, -13, 13, 13, 13)
                        .addBox(7, -242, 0, 13, 13, 13)
                        .addBox(7, -242, 13, 13, 13, 13)
                        .addBox(7, -230, -26, 13, 13, 13)
                        .addBox(7, -230, -13, 13, 13, 13)
                        .addBox(7, -230, 0, 13, 13, 13)
                        .addBox(7, -230, 13, 13, 13, 13)
                        .addBox(7, -217, -26, 13, 13, 13)
                        .addBox(7, -217, -13, 13, 13, 13)
                        .addBox(7, -217, 0, 13, 13, 13)
                        .addBox(7, -217, 13, 13, 13, 13)
                        .addBox(-28, -182, -31, 14, 9, 12)
                        .addBox(-28, -182, -19, 14, 9, 12)
                        .addBox(-28, -182, -6, 14, 9, 12)
                        .addBox(-28, -182, 6, 14, 9, 12)
                        .addBox(-28, -182, 19, 14, 9, 12)
                        .addBox(-28, -173, -31, 14, 9, 12)
                        .addBox(-28, -173, -19, 14, 9, 12)
                        .addBox(-28, -173, -6, 14, 9, 12)
                        .addBox(-28, -173, 6, 14, 9, 12)
                        .addBox(-28, -173, 19, 14, 9, 12)
                        .addBox(-14, -182, -31, 14, 9, 12)
                        .addBox(-14, -182, -19, 14, 9, 12)
                        .addBox(-14, -182, -6, 14, 9, 12)
                        .addBox(-14, -182, 6, 14, 9, 12)
                        .addBox(-14, -182, 19, 14, 9, 12)
                        .addBox(-14, -173, -31, 14, 9, 12)
                        .addBox(-14, -173, -19, 14, 9, 12)
                        .addBox(-14, -173, -6, 14, 9, 12)
                        .addBox(-14, -173, 6, 14, 9, 12)
                        .addBox(-14, -173, 19, 14, 9, 12)
                        .addBox(0, -182, -31, 14, 9, 12)
                        .addBox(0, -182, -19, 14, 9, 12)
                        .addBox(0, -182, -6, 14, 9, 12)
                        .addBox(0, -182, 6, 14, 9, 12)
                        .addBox(0, -182, 19, 14, 9, 12)
                        .addBox(0, -173, -31, 14, 9, 12)
                        .addBox(0, -173, -19, 14, 9, 12)
                        .addBox(0, -173, -6, 14, 9, 12)
                        .addBox(0, -173, 6, 14, 9, 12)
                        .addBox(0, -173, 19, 14, 9, 12)
                        .addBox(14, -182, -31, 14, 9, 12)
                        .addBox(14, -182, -19, 14, 9, 12)
                        .addBox(14, -182, -6, 14, 9, 12)
                        .addBox(14, -182, 6, 14, 9, 12)
                        .addBox(14, -182, 19, 14, 9, 12)
                        .addBox(14, -173, -31, 14, 9, 12)
                        .addBox(14, -173, -19, 14, 9, 12)
                        .addBox(14, -173, -6, 14, 9, 12)
                        .addBox(14, -173, 6, 14, 9, 12)
                        .addBox(14, -173, 19, 14, 9, 12)
                        .addBox(-24, -250, -32, 12, 12, 13)
                        .addBox(-24, -250, -19, 12, 12, 13)
                        .addBox(-24, -250, -6, 12, 12, 13)
                        .addBox(-24, -250, 6, 12, 12, 13)
                        .addBox(-24, -250, 19, 12, 12, 13)
                        .addBox(-24, -238, -32, 12, 12, 13)
                        .addBox(-24, -238, -19, 12, 12, 13)
                        .addBox(-24, -238, -6, 12, 12, 13)
                        .addBox(-24, -238, 6, 12, 12, 13)
                        .addBox(-24, -238, 19, 12, 12, 13)
                        .addBox(-12, -250, -32, 12, 12, 13)
                        .addBox(-12, -250, -19, 12, 12, 13)
                        .addBox(-12, -250, -6, 12, 12, 13)
                        .addBox(-12, -250, 6, 12, 12, 13)
                        .addBox(-12, -250, 19, 12, 12, 13)
                        .addBox(-12, -238, -32, 12, 12, 13)
                        .addBox(-12, -238, -19, 12, 12, 13)
                        .addBox(-12, -238, -6, 12, 12, 13)
                        .addBox(-12, -238, 6, 12, 12, 13)
                        .addBox(-12, -238, 19, 12, 12, 13)
                        .addBox(0, -250, -32, 12, 12, 13)
                        .addBox(0, -250, -19, 12, 12, 13)
                        .addBox(0, -250, -6, 12, 12, 13)
                        .addBox(0, -250, 6, 12, 12, 13)
                        .addBox(0, -250, 19, 12, 12, 13)
                        .addBox(0, -238, -32, 12, 12, 13)
                        .addBox(0, -238, -19, 12, 12, 13)
                        .addBox(0, -238, -6, 12, 12, 13)
                        .addBox(0, -238, 6, 12, 12, 13)
                        .addBox(0, -238, 19, 12, 12, 13)
                        .addBox(12, -250, -32, 12, 12, 13)
                        .addBox(12, -250, -19, 12, 12, 13)
                        .addBox(12, -250, -6, 12, 12, 13)
                        .addBox(12, -250, 6, 12, 12, 13)
                        .addBox(12, -250, 19, 12, 12, 13)
                        .addBox(12, -238, -32, 12, 12, 13)
                        .addBox(12, -238, -19, 12, 12, 13)
                        .addBox(12, -238, -6, 12, 12, 13)
                        .addBox(12, -238, 6, 12, 12, 13)
                        .addBox(12, -238, 19, 12, 12, 13),
                PartPose.offsetAndRotation(0, 28, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-13, -28, -12, 26, 28, 24)
                        .addBox(-14, -6, -13, 28, 4, 26),
                PartPose.offsetAndRotation(0, -270, -22, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-10, -7, -1, 20, 14, 2),
                PartPose.offsetAndRotation(0, -16, -13, 0.0F, 0.0F, 0.0F));

        PartDefinition hold_l = body.addOrReplaceChild("hold_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, 0, -26, 14, 22, 52)
                        .addBox(-6, 18, -22, 12, 3, 44),
                PartPose.offsetAndRotation(-26, -248, 4, 0.0F, 0.0F, 0.0F));

        PartDefinition hold_r = body.addOrReplaceChild("hold_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, 0, -26, 14, 22, 52)
                        .addBox(-6, 18, -22, 12, 3, 44),
                PartPose.offsetAndRotation(26, -248, 4, 0.0F, 0.0F, 0.0F));

        PartDefinition crane = body.addOrReplaceChild("crane",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -8, -52, 10, 12, 86)
                        .addBox(-9, -10, 28, 18, 14, 16)
                        .addBox(-3, 4, -50, 6, 20, 6),
                PartPose.offsetAndRotation(0, -276, 20, 0.0F, 0.0F, 0.0F));

        PartDefinition claw = crane.addOrReplaceChild("claw",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, 0, -5, 14, 6, 12)
                        .addBox(-11, 6, -4, 5, 14, 10)
                        .addBox(6, 6, -4, 5, 14, 10)
                        .addBox(-9, 18, -3, 18, 4, 8)
                        .addBox(-2, 2, 6, 4, 4, 2),
                PartPose.offsetAndRotation(0, 24, -48, 0.0F, 0.0F, 0.0F));

        PartDefinition gun = body.addOrReplaceChild("gun",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -8, -14, 16, 16, 16)
                        .addBox(-2, -2, -34, 4, 4, 22),
                PartPose.offsetAndRotation(0, -206, -30, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition smokeTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 62, 6),
                PartPose.offsetAndRotation(-20, -101, 0, 0.0349F, 0.0F, 0.2269F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 64, 6)
                        .addBox(-5, 63, -5, 9, 3, 9),
                PartPose.offsetAndRotation(0, 62, 0, 0.0122F, 0.0F, 0.0794F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 62, 6),
                PartPose.offsetAndRotation(20, -101, 0, 0.0349F, 0.0F, -0.2269F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 64, 6)
                        .addBox(-5, 63, -5, 9, 3, 9),
                PartPose.offsetAndRotation(0, 62, 0, 0.0122F, 0.0F, -0.0794F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 62, 6),
                PartPose.offsetAndRotation(0, -101, 24, 0.2443F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 6, 64, 6)
                        .addBox(-5, 63, -5, 9, 3, 9),
                PartPose.offsetAndRotation(0, 62, 0, 0.0855F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-22, -196, -21, 11, 11, 10)
                        .addBox(-22, -196, -10, 11, 11, 10)
                        .addBox(-22, -196, 0, 11, 11, 10)
                        .addBox(-22, -196, 10, 11, 11, 10)
                        .addBox(-22, -185, -21, 11, 11, 10)
                        .addBox(-22, -185, -10, 11, 11, 10)
                        .addBox(-22, -185, 0, 11, 11, 10)
                        .addBox(-22, -185, 10, 11, 11, 10)
                        .addBox(-22, -174, -21, 11, 11, 10)
                        .addBox(-22, -174, -10, 11, 11, 10)
                        .addBox(-22, -174, 0, 11, 11, 10)
                        .addBox(-22, -174, 10, 11, 11, 10)
                        .addBox(-22, -162, -21, 11, 11, 10)
                        .addBox(-22, -162, -10, 11, 11, 10)
                        .addBox(-22, -162, 0, 11, 11, 10)
                        .addBox(-22, -162, 10, 11, 11, 10)
                        .addBox(-22, -151, -21, 11, 11, 10)
                        .addBox(-22, -151, -10, 11, 11, 10)
                        .addBox(-22, -151, 0, 11, 11, 10)
                        .addBox(-22, -151, 10, 11, 11, 10)
                        .addBox(-11, -196, -21, 11, 11, 10)
                        .addBox(-11, -196, -10, 11, 11, 10)
                        .addBox(-11, -196, 0, 11, 11, 10)
                        .addBox(-11, -196, 10, 11, 11, 10)
                        .addBox(-11, -185, -21, 11, 11, 10)
                        .addBox(-11, -185, -10, 11, 11, 10)
                        .addBox(-11, -185, 0, 11, 11, 10)
                        .addBox(-11, -185, 10, 11, 11, 10)
                        .addBox(-11, -174, -21, 11, 11, 10)
                        .addBox(-11, -174, -10, 11, 11, 10)
                        .addBox(-11, -174, 0, 11, 11, 10)
                        .addBox(-11, -174, 10, 11, 11, 10)
                        .addBox(-11, -162, -21, 11, 11, 10)
                        .addBox(-11, -162, -10, 11, 11, 10)
                        .addBox(-11, -162, 0, 11, 11, 10)
                        .addBox(-11, -162, 10, 11, 11, 10)
                        .addBox(-11, -151, -21, 11, 11, 10)
                        .addBox(-11, -151, -10, 11, 11, 10)
                        .addBox(-11, -151, 0, 11, 11, 10)
                        .addBox(-11, -151, 10, 11, 11, 10)
                        .addBox(0, -196, -21, 11, 11, 10)
                        .addBox(0, -196, -10, 11, 11, 10)
                        .addBox(0, -196, 0, 11, 11, 10)
                        .addBox(0, -196, 10, 11, 11, 10)
                        .addBox(0, -185, -21, 11, 11, 10)
                        .addBox(0, -185, -10, 11, 11, 10)
                        .addBox(0, -185, 0, 11, 11, 10)
                        .addBox(0, -185, 10, 11, 11, 10)
                        .addBox(0, -174, -21, 11, 11, 10)
                        .addBox(0, -174, -10, 11, 11, 10)
                        .addBox(0, -174, 0, 11, 11, 10)
                        .addBox(0, -174, 10, 11, 11, 10)
                        .addBox(0, -162, -21, 11, 11, 10)
                        .addBox(0, -162, -10, 11, 11, 10)
                        .addBox(0, -162, 0, 11, 11, 10)
                        .addBox(0, -162, 10, 11, 11, 10)
                        .addBox(0, -151, -21, 11, 11, 10)
                        .addBox(0, -151, -10, 11, 11, 10)
                        .addBox(0, -151, 0, 11, 11, 10)
                        .addBox(0, -151, 10, 11, 11, 10)
                        .addBox(11, -196, -21, 11, 11, 10)
                        .addBox(11, -196, -10, 11, 11, 10)
                        .addBox(11, -196, 0, 11, 11, 10)
                        .addBox(11, -196, 10, 11, 11, 10)
                        .addBox(11, -185, -21, 11, 11, 10)
                        .addBox(11, -185, -10, 11, 11, 10)
                        .addBox(11, -185, 0, 11, 11, 10)
                        .addBox(11, -185, 10, 11, 11, 10)
                        .addBox(11, -174, -21, 11, 11, 10)
                        .addBox(11, -174, -10, 11, 11, 10)
                        .addBox(11, -174, 0, 11, 11, 10)
                        .addBox(11, -174, 10, 11, 11, 10)
                        .addBox(11, -162, -21, 11, 11, 10)
                        .addBox(11, -162, -10, 11, 11, 10)
                        .addBox(11, -162, 0, 11, 11, 10)
                        .addBox(11, -162, 10, 11, 11, 10)
                        .addBox(11, -151, -21, 11, 11, 10)
                        .addBox(11, -151, -10, 11, 11, 10)
                        .addBox(11, -151, 0, 11, 11, 10)
                        .addBox(11, -151, 10, 11, 11, 10)
                        .addBox(-20, -132, -19, 10, 12, 10)
                        .addBox(-20, -132, -10, 10, 12, 10)
                        .addBox(-20, -132, 0, 10, 12, 10)
                        .addBox(-20, -132, 10, 10, 12, 10)
                        .addBox(-10, -132, -19, 10, 12, 10)
                        .addBox(-10, -132, -10, 10, 12, 10)
                        .addBox(-10, -132, 0, 10, 12, 10)
                        .addBox(-10, -132, 10, 10, 12, 10)
                        .addBox(0, -132, -19, 10, 12, 10)
                        .addBox(0, -132, -10, 10, 12, 10)
                        .addBox(0, -132, 0, 10, 12, 10)
                        .addBox(0, -132, 10, 10, 12, 10)
                        .addBox(10, -132, -19, 10, 12, 10)
                        .addBox(10, -132, -10, 10, 12, 10)
                        .addBox(10, -132, 0, 10, 12, 10)
                        .addBox(10, -132, 10, 10, 12, 10)
                        .addBox(-25, -170, -23, 10, 10, 12)
                        .addBox(-25, -170, -12, 10, 10, 12)
                        .addBox(-25, -170, 0, 10, 10, 12)
                        .addBox(-25, -170, 12, 10, 10, 12)
                        .addBox(-15, -170, -23, 10, 10, 12)
                        .addBox(-15, -170, -12, 10, 10, 12)
                        .addBox(-15, -170, 0, 10, 10, 12)
                        .addBox(-15, -170, 12, 10, 10, 12)
                        .addBox(-5, -170, -23, 10, 10, 12)
                        .addBox(-5, -170, -12, 10, 10, 12)
                        .addBox(-5, -170, 0, 10, 10, 12)
                        .addBox(-5, -170, 12, 10, 10, 12)
                        .addBox(5, -170, -23, 10, 10, 12)
                        .addBox(5, -170, -12, 10, 10, 12)
                        .addBox(5, -170, 0, 10, 10, 12)
                        .addBox(5, -170, 12, 10, 10, 12)
                        .addBox(15, -170, -23, 10, 10, 12)
                        .addBox(15, -170, -12, 10, 10, 12)
                        .addBox(15, -170, 0, 10, 10, 12)
                        .addBox(15, -170, 12, 10, 10, 12)
                        .addBox(-16, -204, -15, 8, 8, 10)
                        .addBox(-16, -204, -5, 8, 8, 10)
                        .addBox(-16, -204, 5, 8, 8, 10)
                        .addBox(-16, -196, -15, 8, 8, 10)
                        .addBox(-16, -196, -5, 8, 8, 10)
                        .addBox(-16, -196, 5, 8, 8, 10)
                        .addBox(-8, -204, -15, 8, 8, 10)
                        .addBox(-8, -204, -5, 8, 8, 10)
                        .addBox(-8, -204, 5, 8, 8, 10)
                        .addBox(-8, -196, -15, 8, 8, 10)
                        .addBox(-8, -196, -5, 8, 8, 10)
                        .addBox(-8, -196, 5, 8, 8, 10)
                        .addBox(0, -204, -15, 8, 8, 10)
                        .addBox(0, -204, -5, 8, 8, 10)
                        .addBox(0, -204, 5, 8, 8, 10)
                        .addBox(0, -196, -15, 8, 8, 10)
                        .addBox(0, -196, -5, 8, 8, 10)
                        .addBox(0, -196, 5, 8, 8, 10)
                        .addBox(8, -204, -15, 8, 8, 10)
                        .addBox(8, -204, -5, 8, 8, 10)
                        .addBox(8, -204, 5, 8, 8, 10)
                        .addBox(8, -196, -15, 8, 8, 10)
                        .addBox(8, -196, -5, 8, 8, 10)
                        .addBox(8, -196, 5, 8, 8, 10),
                PartPose.offsetAndRotation(0, 23, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-9, -12, -10, 18, 14, 14),
                PartPose.offsetAndRotation(0, -204, -16, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -3, -1, 12, 6, 2),
                PartPose.offsetAndRotation(0, -4, -11, 0.0F, 0.0F, 0.0F));

        PartDefinition vent_l = body.addOrReplaceChild("vent_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -34, -9, 10, 34, 18)
                        .addBox(-7, -38, -11, 14, 5, 22),
                PartPose.offsetAndRotation(-17, -196, 6, 0.0F, 0.0F, 0.0F));

        PartDefinition vent_l_mouth = vent_l.addOrReplaceChild("vent_l_mouth",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-4, -4, -1, 8, 8, 2),
                PartPose.offsetAndRotation(0, -40, -1, 0.0F, 0.0F, 0.0F));

        PartDefinition vent_r = body.addOrReplaceChild("vent_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5, -34, -9, 10, 34, 18)
                        .addBox(-7, -38, -11, 14, 5, 22),
                PartPose.offsetAndRotation(17, -196, 6, 0.0F, 0.0F, 0.0F));

        PartDefinition vent_r_mouth = vent_r.addOrReplaceChild("vent_r_mouth",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-4, -4, -1, 8, 8, 2),
                PartPose.offsetAndRotation(0, -40, -1, 0.0F, 0.0F, 0.0F));

        PartDefinition pipe = body.addOrReplaceChild("pipe",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-16, -6, 0, 32, 10, 8),
                PartPose.offsetAndRotation(0, -196, 22, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition bombTripod() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 68, 7),
                PartPose.offsetAndRotation(-22, -113, -6, -0.0698F, 0.0F, 0.2094F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 70, 7)
                        .addBox(-5, 69, -5, 10, 3, 10),
                PartPose.offsetAndRotation(0, 68, 0, -0.0244F, 0.0F, 0.0733F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 68, 7),
                PartPose.offsetAndRotation(22, -113, -6, -0.0698F, 0.0F, -0.2094F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 70, 7)
                        .addBox(-5, 69, -5, 10, 3, 10),
                PartPose.offsetAndRotation(0, 68, 0, -0.0244F, 0.0F, -0.0733F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 68, 7),
                PartPose.offsetAndRotation(0, -113, 26, 0.2094F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 7, 70, 7)
                        .addBox(-5, 69, -5, 10, 3, 10),
                PartPose.offsetAndRotation(0, 68, 0, 0.0733F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-19, -214, -21, 10, 11, 10)
                        .addBox(-19, -214, -10, 10, 11, 10)
                        .addBox(-19, -214, 0, 10, 11, 10)
                        .addBox(-19, -214, 10, 10, 11, 10)
                        .addBox(-19, -203, -21, 10, 11, 10)
                        .addBox(-19, -203, -10, 10, 11, 10)
                        .addBox(-19, -203, 0, 10, 11, 10)
                        .addBox(-19, -203, 10, 10, 11, 10)
                        .addBox(-19, -192, -21, 10, 11, 10)
                        .addBox(-19, -192, -10, 10, 11, 10)
                        .addBox(-19, -192, 0, 10, 11, 10)
                        .addBox(-19, -192, 10, 10, 11, 10)
                        .addBox(-19, -180, -21, 10, 11, 10)
                        .addBox(-19, -180, -10, 10, 11, 10)
                        .addBox(-19, -180, 0, 10, 11, 10)
                        .addBox(-19, -180, 10, 10, 11, 10)
                        .addBox(-19, -169, -21, 10, 11, 10)
                        .addBox(-19, -169, -10, 10, 11, 10)
                        .addBox(-19, -169, 0, 10, 11, 10)
                        .addBox(-19, -169, 10, 10, 11, 10)
                        .addBox(-10, -214, -21, 10, 11, 10)
                        .addBox(-10, -214, -10, 10, 11, 10)
                        .addBox(-10, -214, 0, 10, 11, 10)
                        .addBox(-10, -214, 10, 10, 11, 10)
                        .addBox(-10, -203, -21, 10, 11, 10)
                        .addBox(-10, -203, -10, 10, 11, 10)
                        .addBox(-10, -203, 0, 10, 11, 10)
                        .addBox(-10, -203, 10, 10, 11, 10)
                        .addBox(-10, -192, -21, 10, 11, 10)
                        .addBox(-10, -192, -10, 10, 11, 10)
                        .addBox(-10, -192, 0, 10, 11, 10)
                        .addBox(-10, -192, 10, 10, 11, 10)
                        .addBox(-10, -180, -21, 10, 11, 10)
                        .addBox(-10, -180, -10, 10, 11, 10)
                        .addBox(-10, -180, 0, 10, 11, 10)
                        .addBox(-10, -180, 10, 10, 11, 10)
                        .addBox(-10, -169, -21, 10, 11, 10)
                        .addBox(-10, -169, -10, 10, 11, 10)
                        .addBox(-10, -169, 0, 10, 11, 10)
                        .addBox(-10, -169, 10, 10, 11, 10)
                        .addBox(0, -214, -21, 10, 11, 10)
                        .addBox(0, -214, -10, 10, 11, 10)
                        .addBox(0, -214, 0, 10, 11, 10)
                        .addBox(0, -214, 10, 10, 11, 10)
                        .addBox(0, -203, -21, 10, 11, 10)
                        .addBox(0, -203, -10, 10, 11, 10)
                        .addBox(0, -203, 0, 10, 11, 10)
                        .addBox(0, -203, 10, 10, 11, 10)
                        .addBox(0, -192, -21, 10, 11, 10)
                        .addBox(0, -192, -10, 10, 11, 10)
                        .addBox(0, -192, 0, 10, 11, 10)
                        .addBox(0, -192, 10, 10, 11, 10)
                        .addBox(0, -180, -21, 10, 11, 10)
                        .addBox(0, -180, -10, 10, 11, 10)
                        .addBox(0, -180, 0, 10, 11, 10)
                        .addBox(0, -180, 10, 10, 11, 10)
                        .addBox(0, -169, -21, 10, 11, 10)
                        .addBox(0, -169, -10, 10, 11, 10)
                        .addBox(0, -169, 0, 10, 11, 10)
                        .addBox(0, -169, 10, 10, 11, 10)
                        .addBox(10, -214, -21, 10, 11, 10)
                        .addBox(10, -214, -10, 10, 11, 10)
                        .addBox(10, -214, 0, 10, 11, 10)
                        .addBox(10, -214, 10, 10, 11, 10)
                        .addBox(10, -203, -21, 10, 11, 10)
                        .addBox(10, -203, -10, 10, 11, 10)
                        .addBox(10, -203, 0, 10, 11, 10)
                        .addBox(10, -203, 10, 10, 11, 10)
                        .addBox(10, -192, -21, 10, 11, 10)
                        .addBox(10, -192, -10, 10, 11, 10)
                        .addBox(10, -192, 0, 10, 11, 10)
                        .addBox(10, -192, 10, 10, 11, 10)
                        .addBox(10, -180, -21, 10, 11, 10)
                        .addBox(10, -180, -10, 10, 11, 10)
                        .addBox(10, -180, 0, 10, 11, 10)
                        .addBox(10, -180, 10, 10, 11, 10)
                        .addBox(10, -169, -21, 10, 11, 10)
                        .addBox(10, -169, -10, 10, 11, 10)
                        .addBox(10, -169, 0, 10, 11, 10)
                        .addBox(10, -169, 10, 10, 11, 10)
                        .addBox(-23, -148, -23, 12, 7, 12)
                        .addBox(-23, -148, -12, 12, 7, 12)
                        .addBox(-23, -148, 0, 12, 7, 12)
                        .addBox(-23, -148, 12, 12, 7, 12)
                        .addBox(-23, -141, -23, 12, 7, 12)
                        .addBox(-23, -141, -12, 12, 7, 12)
                        .addBox(-23, -141, 0, 12, 7, 12)
                        .addBox(-23, -141, 12, 12, 7, 12)
                        .addBox(-12, -148, -23, 12, 7, 12)
                        .addBox(-12, -148, -12, 12, 7, 12)
                        .addBox(-12, -148, 0, 12, 7, 12)
                        .addBox(-12, -148, 12, 12, 7, 12)
                        .addBox(-12, -141, -23, 12, 7, 12)
                        .addBox(-12, -141, -12, 12, 7, 12)
                        .addBox(-12, -141, 0, 12, 7, 12)
                        .addBox(-12, -141, 12, 12, 7, 12)
                        .addBox(0, -148, -23, 12, 7, 12)
                        .addBox(0, -148, -12, 12, 7, 12)
                        .addBox(0, -148, 0, 12, 7, 12)
                        .addBox(0, -148, 12, 12, 7, 12)
                        .addBox(0, -141, -23, 12, 7, 12)
                        .addBox(0, -141, -12, 12, 7, 12)
                        .addBox(0, -141, 0, 12, 7, 12)
                        .addBox(0, -141, 12, 12, 7, 12)
                        .addBox(12, -148, -23, 12, 7, 12)
                        .addBox(12, -148, -12, 12, 7, 12)
                        .addBox(12, -148, 0, 12, 7, 12)
                        .addBox(12, -148, 12, 12, 7, 12)
                        .addBox(12, -141, -23, 12, 7, 12)
                        .addBox(12, -141, -12, 12, 7, 12)
                        .addBox(12, -141, 0, 12, 7, 12)
                        .addBox(12, -141, 12, 12, 7, 12),
                PartPose.offsetAndRotation(0, 29, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-9, -12, -11, 18, 14, 16),
                PartPose.offsetAndRotation(0, -214, -18, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-5, -3, -1, 10, 6, 2),
                PartPose.offsetAndRotation(0, -4, -12, 0.0F, 0.0F, 0.0F));

        PartDefinition mortar = body.addOrReplaceChild("mortar",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-10, -8, -10, 20, 16, 20)
                        .addBox(-7, -56, -7, 14, 48, 14)
                        .addBox(-9, -60, -9, 18, 6, 18),
                PartPose.offsetAndRotation(0, -206, 12, -0.0122F, 0.0F, 0.0F));

        PartDefinition muzzle = mortar.addOrReplaceChild("muzzle",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-6, -6, -1, 11, 11, 2),
                PartPose.offsetAndRotation(0, -60, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition rack_l = body.addOrReplaceChild("rack_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, 0, -12, 12, 14, 26)
                        .addBox(-5, 12, -10, 10, 2, 22),
                PartPose.offsetAndRotation(-22, -204, 10, 0.0F, 0.0F, 0.0F));

        PartDefinition rack_r = body.addOrReplaceChild("rack_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, 0, -12, 12, 14, 26)
                        .addBox(-5, 12, -10, 10, 2, 22),
                PartPose.offsetAndRotation(22, -204, 10, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition constrictor() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 8, 60, 8),
                PartPose.offsetAndRotation(-26, -99, -10, -0.0873F, 0.0F, 0.1396F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 8, 62, 8)
                        .addBox(-5, 61, -5, 10, 3, 10),
                PartPose.offsetAndRotation(0, 60, 0, -0.0305F, 0.0F, 0.0489F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 8, 60, 8),
                PartPose.offsetAndRotation(26, -99, -10, -0.0873F, 0.0F, -0.1396F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 8, 62, 8)
                        .addBox(-5, 61, -5, 10, 3, 10),
                PartPose.offsetAndRotation(0, 60, 0, -0.0305F, 0.0F, -0.0489F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 8, 60, 8),
                PartPose.offsetAndRotation(0, -99, 28, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, 0, -4, 8, 62, 8)
                        .addBox(-5, 61, -5, 10, 3, 10),
                PartPose.offsetAndRotation(0, 60, 0, 0.0611F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-26, -186, -23, 13, 13, 12)
                        .addBox(-26, -186, -12, 13, 13, 12)
                        .addBox(-26, -186, 0, 13, 13, 12)
                        .addBox(-26, -186, 12, 13, 13, 12)
                        .addBox(-26, -173, -23, 13, 13, 12)
                        .addBox(-26, -173, -12, 13, 13, 12)
                        .addBox(-26, -173, 0, 13, 13, 12)
                        .addBox(-26, -173, 12, 13, 13, 12)
                        .addBox(-26, -159, -23, 13, 13, 12)
                        .addBox(-26, -159, -12, 13, 13, 12)
                        .addBox(-26, -159, 0, 13, 13, 12)
                        .addBox(-26, -159, 12, 13, 13, 12)
                        .addBox(-13, -186, -23, 13, 13, 12)
                        .addBox(-13, -186, -12, 13, 13, 12)
                        .addBox(-13, -186, 0, 13, 13, 12)
                        .addBox(-13, -186, 12, 13, 13, 12)
                        .addBox(-13, -173, -23, 13, 13, 12)
                        .addBox(-13, -173, -12, 13, 13, 12)
                        .addBox(-13, -173, 0, 13, 13, 12)
                        .addBox(-13, -173, 12, 13, 13, 12)
                        .addBox(-13, -159, -23, 13, 13, 12)
                        .addBox(-13, -159, -12, 13, 13, 12)
                        .addBox(-13, -159, 0, 13, 13, 12)
                        .addBox(-13, -159, 12, 13, 13, 12)
                        .addBox(0, -186, -23, 13, 13, 12)
                        .addBox(0, -186, -12, 13, 13, 12)
                        .addBox(0, -186, 0, 13, 13, 12)
                        .addBox(0, -186, 12, 13, 13, 12)
                        .addBox(0, -173, -23, 13, 13, 12)
                        .addBox(0, -173, -12, 13, 13, 12)
                        .addBox(0, -173, 0, 13, 13, 12)
                        .addBox(0, -173, 12, 13, 13, 12)
                        .addBox(0, -159, -23, 13, 13, 12)
                        .addBox(0, -159, -12, 13, 13, 12)
                        .addBox(0, -159, 0, 13, 13, 12)
                        .addBox(0, -159, 12, 13, 13, 12)
                        .addBox(13, -186, -23, 13, 13, 12)
                        .addBox(13, -186, -12, 13, 13, 12)
                        .addBox(13, -186, 0, 13, 13, 12)
                        .addBox(13, -186, 12, 13, 13, 12)
                        .addBox(13, -173, -23, 13, 13, 12)
                        .addBox(13, -173, -12, 13, 13, 12)
                        .addBox(13, -173, 0, 13, 13, 12)
                        .addBox(13, -173, 12, 13, 13, 12)
                        .addBox(13, -159, -23, 13, 13, 12)
                        .addBox(13, -159, -12, 13, 13, 12)
                        .addBox(13, -159, 0, 13, 13, 12)
                        .addBox(13, -159, 12, 13, 13, 12)
                        .addBox(-23, -126, -21, 12, 12, 10)
                        .addBox(-23, -126, -10, 12, 12, 10)
                        .addBox(-23, -126, 0, 12, 12, 10)
                        .addBox(-23, -126, 10, 12, 12, 10)
                        .addBox(-12, -126, -21, 12, 12, 10)
                        .addBox(-12, -126, -10, 12, 12, 10)
                        .addBox(-12, -126, 0, 12, 12, 10)
                        .addBox(-12, -126, 10, 12, 12, 10)
                        .addBox(0, -126, -21, 12, 12, 10)
                        .addBox(0, -126, -10, 12, 12, 10)
                        .addBox(0, -126, 0, 12, 12, 10)
                        .addBox(0, -126, 10, 12, 12, 10)
                        .addBox(12, -126, -21, 12, 12, 10)
                        .addBox(12, -126, -10, 12, 12, 10)
                        .addBox(12, -126, 0, 12, 12, 10)
                        .addBox(12, -126, 10, 12, 12, 10)
                        .addBox(-29, -160, -25, 12, 10, 12)
                        .addBox(-29, -160, -12, 12, 10, 12)
                        .addBox(-29, -160, 0, 12, 10, 12)
                        .addBox(-29, -160, 12, 12, 10, 12)
                        .addBox(-17, -160, -25, 12, 10, 12)
                        .addBox(-17, -160, -12, 12, 10, 12)
                        .addBox(-17, -160, 0, 12, 10, 12)
                        .addBox(-17, -160, 12, 12, 10, 12)
                        .addBox(-6, -160, -25, 12, 10, 12)
                        .addBox(-6, -160, -12, 12, 10, 12)
                        .addBox(-6, -160, 0, 12, 10, 12)
                        .addBox(-6, -160, 12, 12, 10, 12)
                        .addBox(6, -160, -25, 12, 10, 12)
                        .addBox(6, -160, -12, 12, 10, 12)
                        .addBox(6, -160, 0, 12, 10, 12)
                        .addBox(6, -160, 12, 12, 10, 12)
                        .addBox(17, -160, -25, 12, 10, 12)
                        .addBox(17, -160, -12, 12, 10, 12)
                        .addBox(17, -160, 0, 12, 10, 12)
                        .addBox(17, -160, 12, 12, 10, 12),
                PartPose.offsetAndRotation(0, 21, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-12, -14, -12, 24, 16, 18),
                PartPose.offsetAndRotation(0, -190, -18, 0.0F, 0.0F, 0.0F));

        PartDefinition eye = head.addOrReplaceChild("eye",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-7, -4, -1, 14, 7, 2),
                PartPose.offsetAndRotation(0, -5, -13, 0.0F, 0.0F, 0.0F));

        PartDefinition nozzle_l = body.addOrReplaceChild("nozzle_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -4, -24, 8, 8, 26)
                        .addBox(-3, -3, -27, 6, 6, 3),
                PartPose.offsetAndRotation(-14, -176, -20, 0.0F, 0.0F, 0.0F));

        PartDefinition nozzle_l_tank = body.addOrReplaceChild("nozzle_l_tank",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, -12, -16, 14, 24, 32),
                PartPose.offsetAndRotation(-26, -168, 8, 0.0F, 0.0F, 0.0F));

        PartDefinition nozzle_r = body.addOrReplaceChild("nozzle_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4, -4, -24, 8, 8, 26)
                        .addBox(-3, -3, -27, 6, 6, 3),
                PartPose.offsetAndRotation(14, -176, -20, 0.0F, 0.0F, 0.0F));

        PartDefinition nozzle_r_tank = body.addOrReplaceChild("nozzle_r_tank",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7, -12, -16, 14, 24, 32),
                PartPose.offsetAndRotation(26, -168, 8, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition xenoTelepath() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition leg_l = root.addOrReplaceChild("leg_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 72, 5),
                PartPose.offsetAndRotation(-18, -124, 0, 0.0349F, 0.0F, 0.2094F));

        PartDefinition leg_l_shin = leg_l.addOrReplaceChild("leg_l_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 78, 5)
                        .addBox(-4, 77, -4, 8, 3, 8),
                PartPose.offsetAndRotation(0, 72, 0, 0.0122F, 0.0F, 0.0733F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 72, 5),
                PartPose.offsetAndRotation(18, -124, 0, 0.0349F, 0.0F, -0.2094F));

        PartDefinition leg_r_shin = leg_r.addOrReplaceChild("leg_r_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 78, 5)
                        .addBox(-4, 77, -4, 8, 3, 8),
                PartPose.offsetAndRotation(0, 72, 0, 0.0122F, 0.0F, -0.0733F));

        PartDefinition leg_b = root.addOrReplaceChild("leg_b",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 72, 5),
                PartPose.offsetAndRotation(0, -124, 22, 0.2269F, 0.0F, 0.0F));

        PartDefinition leg_b_shin = leg_b.addOrReplaceChild("leg_b_shin",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3, 0, -3, 5, 78, 5)
                        .addBox(-4, 77, -4, 8, 3, 8),
                PartPose.offsetAndRotation(0, 72, 0, 0.0794F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-20, -158, -19, 10, 10, 10)
                        .addBox(-20, -158, -10, 10, 10, 10)
                        .addBox(-20, -158, 0, 10, 10, 10)
                        .addBox(-20, -158, 10, 10, 10, 10)
                        .addBox(-10, -158, -19, 10, 10, 10)
                        .addBox(-10, -158, -10, 10, 10, 10)
                        .addBox(-10, -158, 0, 10, 10, 10)
                        .addBox(-10, -158, 10, 10, 10, 10)
                        .addBox(0, -158, -19, 10, 10, 10)
                        .addBox(0, -158, -10, 10, 10, 10)
                        .addBox(0, -158, 0, 10, 10, 10)
                        .addBox(0, -158, 10, 10, 10, 10)
                        .addBox(10, -158, -19, 10, 10, 10)
                        .addBox(10, -158, -10, 10, 10, 10)
                        .addBox(10, -158, 0, 10, 10, 10)
                        .addBox(10, -158, 10, 10, 10, 10)
                        .addBox(-17, -186, -17, 11, 9, 11)
                        .addBox(-17, -186, -6, 11, 9, 11)
                        .addBox(-17, -186, 6, 11, 9, 11)
                        .addBox(-17, -177, -17, 11, 9, 11)
                        .addBox(-17, -177, -6, 11, 9, 11)
                        .addBox(-17, -177, 6, 11, 9, 11)
                        .addBox(-17, -167, -17, 11, 9, 11)
                        .addBox(-17, -167, -6, 11, 9, 11)
                        .addBox(-17, -167, 6, 11, 9, 11)
                        .addBox(-6, -186, -17, 11, 9, 11)
                        .addBox(-6, -186, -6, 11, 9, 11)
                        .addBox(-6, -186, 6, 11, 9, 11)
                        .addBox(-6, -177, -17, 11, 9, 11)
                        .addBox(-6, -177, -6, 11, 9, 11)
                        .addBox(-6, -177, 6, 11, 9, 11)
                        .addBox(-6, -167, -17, 11, 9, 11)
                        .addBox(-6, -167, -6, 11, 9, 11)
                        .addBox(-6, -167, 6, 11, 9, 11)
                        .addBox(6, -186, -17, 11, 9, 11)
                        .addBox(6, -186, -6, 11, 9, 11)
                        .addBox(6, -186, 6, 11, 9, 11)
                        .addBox(6, -177, -17, 11, 9, 11)
                        .addBox(6, -177, -6, 11, 9, 11)
                        .addBox(6, -177, 6, 11, 9, 11)
                        .addBox(6, -167, -17, 11, 9, 11)
                        .addBox(6, -167, -6, 11, 9, 11)
                        .addBox(6, -167, 6, 11, 9, 11),
                PartPose.offsetAndRotation(0, 26, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-16, -30, -13, 32, 30, 26)
                        .addBox(-13, -26, -14, 26, 20, 1)
                        .addBox(-16, -32, -13, 32, 3, 26),
                PartPose.offsetAndRotation(0, -186, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition pilot = head.addOrReplaceChild("pilot",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8, -16, -17, 16, 18, 10)
                        .addBox(-10, -6, -16, 20, 6, 14)
                        .addBox(-6, -13, -18, 4, 4, 2)
                        .addBox(2, -13, -18, 4, 4, 2),
                PartPose.offsetAndRotation(0, 0, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition dome = body.addOrReplaceChild("dome",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-12, -16, -12, 8, 6, 8)
                        .addBox(-12, -16, -4, 8, 6, 8)
                        .addBox(-12, -16, 4, 8, 6, 8)
                        .addBox(-4, -16, -12, 8, 6, 8)
                        .addBox(-4, -16, -4, 8, 6, 8)
                        .addBox(-4, -16, 4, 8, 6, 8)
                        .addBox(4, -16, -12, 8, 6, 8)
                        .addBox(4, -16, -4, 8, 6, 8)
                        .addBox(4, -16, 4, 8, 6, 8)
                        .addBox(-8, -10, -8, 8, 6, 8)
                        .addBox(-8, -10, 0, 8, 6, 8)
                        .addBox(0, -10, -8, 8, 6, 8)
                        .addBox(0, -10, 0, 8, 6, 8)
                        .addBox(-4, -4, -4, 8, 4, 8),
                PartPose.offsetAndRotation(0, -218, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition antenna_l = body.addOrReplaceChild("antenna_l",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -30, -1, 2, 30, 2),
                PartPose.offsetAndRotation(-15, -216, 0, -0.0035F, 0.0F, 0.0061F));

        PartDefinition antenna_l_tip = antenna_l.addOrReplaceChild("antenna_l_tip",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -3, -1, 5, 5, 2),
                PartPose.offsetAndRotation(0, -30, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition antenna_r = body.addOrReplaceChild("antenna_r",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1, -30, -1, 2, 30, 2),
                PartPose.offsetAndRotation(15, -216, 0, -0.0035F, 0.0F, -0.0061F));

        PartDefinition antenna_r_tip = antenna_r.addOrReplaceChild("antenna_r_tip",
                CubeListBuilder.create()
                        .texOffs(96, 96).addBox(-3, -3, -1, 5, 5, 2),
                PartPose.offsetAndRotation(0, -30, 0, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    /** The BBC machines differ only by the laser cannon, so one entry point serves both. */
    public static LayerDefinition bbc(boolean armed) {
        return armed ? bbcRed() : bbcWhite();
    }
}
