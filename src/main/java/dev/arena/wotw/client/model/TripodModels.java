package dev.arena.wotw.client.model;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

/**
 * Hand tuned blocky geometry for the ten war machines, following the film references.
 *
 * <p><b>Generated file.</b> The geometry lives in `tools/generate_models.py` (see
 * `tools/model_lib.py`); edit that and run `python3 tools/generate_models.py`.
 * Model pixels: 16 per block, +y downwards, feet at y = 24, the machine looks towards
 * (0, +y down). Lenses sample the glowing patch of the texture at (96, 96).</p>
 */
public final class TripodModels {


    public static TexturedModelData tripod2005() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-14, -144, -16, 14, 17, 16)
                        .cuboid(-14, -144, 0, 14, 17, 16)
                        .cuboid(0, -144, -16, 14, 17, 16)
                        .cuboid(0, -144, 0, 14, 17, 16)
                        .cuboid(-19, -127, -22, 19, 22, 22)
                        .cuboid(-19, -127, 0, 19, 22, 22)
                        .cuboid(0, -127, -22, 19, 22, 22)
                        .cuboid(0, -127, 0, 19, 22, 22)
                        .cuboid(-22, -105, -25, 22, 22, 17)
                        .cuboid(-22, -105, -8, 22, 22, 17)
                        .cuboid(-22, -105, 8, 22, 22, 17)
                        .cuboid(0, -105, -25, 22, 22, 17)
                        .cuboid(0, -105, -8, 22, 22, 17)
                        .cuboid(0, -105, 8, 22, 22, 17)
                        .cuboid(-17, -83, -20, 17, 17, 20)
                        .cuboid(-17, -83, 0, 17, 17, 20)
                        .cuboid(0, -83, -20, 17, 17, 20)
                        .cuboid(0, -83, 0, 17, 17, 20)
                        .cuboid(-12, -66, -14, 24, 8, 14)
                        .cuboid(-12, -66, 0, 24, 8, 14)
                        .cuboid(-23, -130, -28, 15, 10, 10)
                        .cuboid(-23, -130, -18, 15, 10, 10)
                        .cuboid(-8, -130, -28, 15, 10, 10)
                        .cuboid(-8, -130, -18, 15, 10, 10)
                        .cuboid(8, -130, -28, 15, 10, 10)
                        .cuboid(8, -130, -18, 15, 10, 10)
                        .cuboid(-11, -66, -13, 11, 11, 9)
                        .cuboid(-11, -66, -4, 11, 11, 9)
                        .cuboid(-11, -66, 4, 11, 11, 9)
                        .cuboid(0, -66, -13, 11, 11, 9)
                        .cuboid(0, -66, -4, 11, 11, 9)
                        .cuboid(0, -66, 4, 11, 11, 9),
                ModelTransform.of(0, -12, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_l = body.addChild("eye_l",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -6, -1, 11, 11, 2),
                ModelTransform.of(-11, -116, -24, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_r = body.addChild("eye_r",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -6, -1, 11, 11, 2),
                ModelTransform.of(11, -116, -24, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(-12, -61, -8, 0.1396F, -0.0524F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(0, 15, 0, 0.2793F, -0.1047F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(0, 15, 0, 0.2793F, -0.1047F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(12, -61, -8, 0.1396F, 0.0524F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(0, 15, 0, 0.2793F, 0.1047F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(0, 15, 0, 0.2793F, 0.1047F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(-9, -61, 9, 0.1920F, -0.0873F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(0, 15, 0, 0.3840F, -0.1745F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(0, 15, 0, 0.3840F, -0.1745F, 0.0F));

        ModelPartData tent3_1_body = body.addChild("tent3_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(9, -61, 9, 0.1920F, 0.0873F, 0.0F));

        ModelPartData tent3_2_body = tent3_1_body.addChild("tent3_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(0, 15, 0, 0.3840F, 0.1745F, 0.0F));

        ModelPartData tent3_3_body = tent3_2_body.addChild("tent3_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 15, 3),
                ModelTransform.of(0, 15, 0, 0.3840F, 0.1745F, 0.0F));

        ModelPartData leg_fl = root.addChild("leg_fl",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 50, 4),
                ModelTransform.of(-16, -75, -12, -0.1745F, 0.0F, 0.2443F));

        ModelPartData leg_fl_shin = leg_fl.addChild("leg_fl_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 46, 4)
                        .cuboid(-3, 44, -3, 5, 4, 5),
                ModelTransform.of(0, 50, 0, -0.0611F, 0.0F, 0.0855F));

        ModelPartData leg_fr = root.addChild("leg_fr",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 50, 4),
                ModelTransform.of(16, -75, -12, -0.1745F, 0.0F, -0.2443F));

        ModelPartData leg_fr_shin = leg_fr.addChild("leg_fr_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 46, 4)
                        .cuboid(-3, 44, -3, 5, 4, 5),
                ModelTransform.of(0, 50, 0, -0.0611F, 0.0F, -0.0855F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 51, 4),
                ModelTransform.of(0, -75, 18, 0.2094F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 47, 4)
                        .cuboid(-3, 46, -3, 5, 4, 5),
                ModelTransform.of(0, 51, 0, 0.0733F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData bbcWhite() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-10, -107, -11, 20, 13, 22)
                        .cuboid(-14, -94, -15, 14, 16, 15)
                        .cuboid(-14, -94, 0, 14, 16, 15)
                        .cuboid(0, -94, -15, 14, 16, 15)
                        .cuboid(0, -94, 0, 14, 16, 15)
                        .cuboid(-12, -78, -13, 24, 13, 13)
                        .cuboid(-12, -78, 0, 24, 13, 13)
                        .cuboid(-8, -64, -9, 16, 8, 18)
                        .cuboid(-5, -113, -5, 5, 7, 5)
                        .cuboid(-5, -113, 0, 5, 7, 5)
                        .cuboid(0, -113, -5, 5, 7, 5)
                        .cuboid(0, -113, 0, 5, 7, 5)
                        .cuboid(-9, -62, -10, 9, 8, 10)
                        .cuboid(-9, -62, 0, 9, 8, 10)
                        .cuboid(0, -62, -10, 9, 8, 10)
                        .cuboid(0, -62, 0, 9, 8, 10),
                ModelTransform.of(0, -11, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -7, -1, 9, 12, 2),
                ModelTransform.of(0, -88, -16, 0.0F, 0.0F, 0.0F));

        ModelPartData arm0 = body.addChild("arm0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 21, 3)
                        .cuboid(-1, 20, -1, 3, 8, 3),
                ModelTransform.of(-9, -59, -6, 0.1047F, 0.0F, -0.1414F));

        ModelPartData arm1 = body.addChild("arm1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 21, 3)
                        .cuboid(-1, 20, -1, 3, 8, 3),
                ModelTransform.of(9, -59, -6, 0.1047F, 0.0F, 0.1414F));

        ModelPartData arm2 = body.addChild("arm2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 21, 3)
                        .cuboid(-1, 20, -1, 3, 8, 3),
                ModelTransform.of(0, -59, 9, 0.1047F, 0.0F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 47, 3),
                ModelTransform.of(-10, -70, 0, 0.0F, 0.0F, 0.2094F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3)
                        .cuboid(-2, 44, -2, 4, 4, 4),
                ModelTransform.of(0, 47, 0, 0.0F, 0.0F, 0.0733F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 47, 3),
                ModelTransform.of(10, -70, 0, 0.0F, 0.0F, -0.2094F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3)
                        .cuboid(-2, 44, -2, 4, 4, 4),
                ModelTransform.of(0, 47, 0, 0.0F, 0.0F, -0.0733F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 47, 3),
                ModelTransform.of(0, -70, 10, 0.1571F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3)
                        .cuboid(-2, 44, -2, 4, 4, 4),
                ModelTransform.of(0, 47, 0, 0.0550F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData bbcRed() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-10, -107, -11, 20, 13, 22)
                        .cuboid(-14, -94, -15, 14, 16, 15)
                        .cuboid(-14, -94, 0, 14, 16, 15)
                        .cuboid(0, -94, -15, 14, 16, 15)
                        .cuboid(0, -94, 0, 14, 16, 15)
                        .cuboid(-12, -78, -13, 24, 13, 13)
                        .cuboid(-12, -78, 0, 24, 13, 13)
                        .cuboid(-8, -64, -9, 16, 8, 18)
                        .cuboid(-5, -113, -5, 5, 7, 5)
                        .cuboid(-5, -113, 0, 5, 7, 5)
                        .cuboid(0, -113, -5, 5, 7, 5)
                        .cuboid(0, -113, 0, 5, 7, 5)
                        .cuboid(-9, -62, -10, 9, 8, 10)
                        .cuboid(-9, -62, 0, 9, 8, 10)
                        .cuboid(0, -62, -10, 9, 8, 10)
                        .cuboid(0, -62, 0, 9, 8, 10),
                ModelTransform.of(0, -11, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -7, -1, 9, 12, 2),
                ModelTransform.of(0, -88, -16, 0.0F, 0.0F, 0.0F));

        ModelPartData arm0 = body.addChild("arm0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 21, 3)
                        .cuboid(-1, 20, -1, 3, 8, 3),
                ModelTransform.of(-9, -59, -6, 0.1047F, 0.0F, -0.1414F));

        ModelPartData arm1 = body.addChild("arm1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 21, 3)
                        .cuboid(-1, 20, -1, 3, 8, 3),
                ModelTransform.of(9, -59, -6, 0.1047F, 0.0F, 0.1414F));

        ModelPartData arm2 = body.addChild("arm2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 21, 3)
                        .cuboid(-1, 20, -1, 3, 8, 3),
                ModelTransform.of(0, -59, 9, 0.1047F, 0.0F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 47, 3),
                ModelTransform.of(-10, -70, 0, 0.0F, 0.0F, 0.2094F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3)
                        .cuboid(-2, 44, -2, 4, 4, 4),
                ModelTransform.of(0, 47, 0, 0.0F, 0.0F, 0.0733F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 47, 3),
                ModelTransform.of(10, -70, 0, 0.0F, 0.0F, -0.2094F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3)
                        .cuboid(-2, 44, -2, 4, 4, 4),
                ModelTransform.of(0, 47, 0, 0.0F, 0.0F, -0.0733F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 47, 3),
                ModelTransform.of(0, -70, 10, 0.1571F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3)
                        .cuboid(-2, 44, -2, 4, 4, 4),
                ModelTransform.of(0, 47, 0, 0.0550F, 0.0F, 0.0F));

        ModelPartData cannon = body.addChild("cannon",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -3, -22, 5, 7, 24)
                        .cuboid(-3, -4, -26, 7, 9, 4)
                        .cuboid(-2, -3, -27, 4, 5, 1),
                ModelTransform.of(0, -78, -14, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData manta() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-17, -25, -31, 17, 23, 16)
                        .cuboid(-17, -25, -16, 17, 23, 16)
                        .cuboid(-17, -25, 0, 17, 23, 16)
                        .cuboid(-17, -25, 16, 17, 23, 16)
                        .cuboid(0, -25, -31, 17, 23, 16)
                        .cuboid(0, -25, -16, 17, 23, 16)
                        .cuboid(0, -25, 0, 17, 23, 16)
                        .cuboid(0, -25, 16, 17, 23, 16)
                        .cuboid(-12, -38, -23, 12, 13, 11)
                        .cuboid(-12, -38, -12, 12, 13, 11)
                        .cuboid(-12, -38, 0, 12, 13, 11)
                        .cuboid(0, -38, -23, 12, 13, 11)
                        .cuboid(0, -38, -12, 12, 13, 11)
                        .cuboid(0, -38, 0, 12, 13, 11),
                ModelTransform.of(0, 12, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData wing_l = body.addChild("wing_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-30, -3, -20, 30, 6, 34)
                        .cuboid(-46, -1, -12, 16, 5, 22)
                        .cuboid(-56, 0, -6, 10, 4, 12),
                ModelTransform.of(-16, -23, 0, 0.0F, 0.0F, 0.1047F));

        ModelPartData wing_r = body.addChild("wing_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(0, -3, -20, 30, 6, 34)
                        .cuboid(30, -1, -12, 16, 5, 22)
                        .cuboid(46, 0, -6, 10, 4, 12),
                ModelTransform.of(16, -23, 0, 0.0F, 0.0F, -0.1047F));

        ModelPartData tail = body.addChild("tail",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, -4, 0, 6, 8, 18),
                ModelTransform.of(0, -20, 30, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -5, -1, 12, 9, 2),
                ModelTransform.of(0, -33, -30, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 13, 2),
                ModelTransform.of(-12, -3, -14, 0.0F, 0.0F, 0.1047F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-2, 10, -2, 3, 4, 3),
                ModelTransform.of(0, 13, 0, 0.0F, 0.0F, 0.0367F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 13, 2),
                ModelTransform.of(12, -3, -14, 0.0F, 0.0F, -0.1047F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-2, 10, -2, 3, 4, 3),
                ModelTransform.of(0, 13, 0, 0.0F, 0.0F, -0.0367F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 13, 2),
                ModelTransform.of(0, -3, 12, 0.0F, 0.0F, 0.0F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-2, 10, -2, 3, 4, 3),
                ModelTransform.of(0, 13, 0, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData book() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-13, -163, -14, 13, 19, 14)
                        .cuboid(-13, -163, 0, 13, 19, 14)
                        .cuboid(0, -163, -14, 13, 19, 14)
                        .cuboid(0, -163, 0, 13, 19, 14)
                        .cuboid(-18, -144, -20, 18, 24, 20)
                        .cuboid(-18, -144, 0, 18, 24, 20)
                        .cuboid(0, -144, -20, 18, 24, 20)
                        .cuboid(0, -144, 0, 18, 24, 20)
                        .cuboid(-17, -120, -19, 17, 22, 19)
                        .cuboid(-17, -120, 0, 17, 22, 19)
                        .cuboid(0, -120, -19, 17, 22, 19)
                        .cuboid(0, -120, 0, 17, 22, 19)
                        .cuboid(-13, -98, -15, 13, 14, 15)
                        .cuboid(-13, -98, 0, 13, 14, 15)
                        .cuboid(0, -98, -15, 13, 14, 15)
                        .cuboid(0, -98, 0, 13, 14, 15)
                        .cuboid(-9, -84, -10, 18, 8, 20)
                        .cuboid(-17, -152, -24, 11, 11, 8)
                        .cuboid(-17, -152, -16, 11, 11, 8)
                        .cuboid(-6, -152, -24, 11, 11, 8)
                        .cuboid(-6, -152, -16, 11, 11, 8)
                        .cuboid(6, -152, -24, 11, 11, 8)
                        .cuboid(6, -152, -16, 11, 11, 8)
                        .cuboid(-9, -82, -10, 9, 8, 10)
                        .cuboid(-9, -82, 0, 9, 8, 10)
                        .cuboid(0, -82, -10, 9, 8, 10)
                        .cuboid(0, -82, 0, 9, 8, 10),
                ModelTransform.of(0, -11, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-4, -4, -1, 8, 8, 2),
                ModelTransform.of(0, -136, -20, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(-11, -84, -9, 0.1222F, -0.0698F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(0, 14, 0, 0.2443F, -0.1396F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(0, 14, 0, 0.2443F, -0.1396F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(11, -84, -9, 0.1222F, 0.0698F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(0, 14, 0, 0.2443F, 0.1396F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(0, 14, 0, 0.2443F, 0.1396F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(0, -84, 11, 0.1222F, 0.0F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(0, 14, 0, 0.2443F, 0.0F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 14, 3),
                ModelTransform.of(0, 14, 0, 0.2443F, 0.0F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 60, 4),
                ModelTransform.of(-14, -95, -10, -0.1920F, 0.0F, 0.2618F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 57, 4)
                        .cuboid(-3, 56, -3, 5, 4, 5),
                ModelTransform.of(0, 60, 0, -0.0672F, 0.0F, 0.0916F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 60, 4),
                ModelTransform.of(14, -95, -10, -0.1920F, 0.0F, -0.2618F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 57, 4)
                        .cuboid(-3, 56, -3, 5, 4, 5),
                ModelTransform.of(0, 60, 0, -0.0672F, 0.0F, -0.0916F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 61, 4),
                ModelTransform.of(0, -95, 14, 0.2269F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 58, 4)
                        .cuboid(-3, 57, -3, 5, 4, 5),
                ModelTransform.of(0, 61, 0, 0.0794F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData asylum() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-25, -67, -28, 12, 16, 14)
                        .cuboid(-25, -67, -14, 12, 16, 14)
                        .cuboid(-25, -67, 0, 12, 16, 14)
                        .cuboid(-25, -67, 14, 12, 16, 14)
                        .cuboid(-12, -67, -28, 12, 16, 14)
                        .cuboid(-12, -67, -14, 12, 16, 14)
                        .cuboid(-12, -67, 0, 12, 16, 14)
                        .cuboid(-12, -67, 14, 12, 16, 14)
                        .cuboid(0, -67, -28, 12, 16, 14)
                        .cuboid(0, -67, -14, 12, 16, 14)
                        .cuboid(0, -67, 0, 12, 16, 14)
                        .cuboid(0, -67, 14, 12, 16, 14)
                        .cuboid(12, -67, -28, 12, 16, 14)
                        .cuboid(12, -67, -14, 12, 16, 14)
                        .cuboid(12, -67, 0, 12, 16, 14)
                        .cuboid(12, -67, 14, 12, 16, 14)
                        .cuboid(-22, -51, -25, 15, 12, 12)
                        .cuboid(-22, -51, -12, 15, 12, 12)
                        .cuboid(-22, -51, 0, 15, 12, 12)
                        .cuboid(-22, -51, 12, 15, 12, 12)
                        .cuboid(-7, -51, -25, 15, 12, 12)
                        .cuboid(-7, -51, -12, 15, 12, 12)
                        .cuboid(-7, -51, 0, 15, 12, 12)
                        .cuboid(-7, -51, 12, 15, 12, 12)
                        .cuboid(7, -51, -25, 15, 12, 12)
                        .cuboid(7, -51, -12, 15, 12, 12)
                        .cuboid(7, -51, 0, 15, 12, 12)
                        .cuboid(7, -51, 12, 15, 12, 12)
                        .cuboid(-17, -39, -20, 11, 8, 13)
                        .cuboid(-17, -39, -7, 11, 8, 13)
                        .cuboid(-17, -39, 7, 11, 8, 13)
                        .cuboid(-6, -39, -20, 11, 8, 13)
                        .cuboid(-6, -39, -7, 11, 8, 13)
                        .cuboid(-6, -39, 7, 11, 8, 13)
                        .cuboid(6, -39, -20, 11, 8, 13)
                        .cuboid(6, -39, -7, 11, 8, 13)
                        .cuboid(6, -39, 7, 11, 8, 13)
                        .cuboid(-20, -67, -22, 8, 3, 8)
                        .cuboid(-19, -64, -21, 6, 3, 6)
                        .cuboid(-18, -60, -20, 4, 3, 4)
                        .cuboid(12, -67, -22, 8, 3, 8)
                        .cuboid(13, -64, -21, 6, 3, 6)
                        .cuboid(14, -60, -20, 4, 3, 4)
                        .cuboid(-22, -67, 0, 8, 3, 8)
                        .cuboid(-21, -64, 1, 6, 3, 6)
                        .cuboid(-20, -60, 2, 4, 3, 4)
                        .cuboid(14, -67, 0, 8, 3, 8)
                        .cuboid(15, -64, 1, 6, 3, 6)
                        .cuboid(16, -60, 2, 4, 3, 4)
                        .cuboid(-18, -67, 18, 8, 3, 8)
                        .cuboid(-17, -64, 19, 6, 3, 6)
                        .cuboid(-16, -60, 20, 4, 3, 4)
                        .cuboid(10, -67, 18, 8, 3, 8)
                        .cuboid(11, -64, 19, 6, 3, 6)
                        .cuboid(12, -60, 20, 4, 3, 4)
                        .cuboid(-13, -64, -31, 9, 9, 10)
                        .cuboid(-4, -64, -31, 9, 9, 10)
                        .cuboid(4, -64, -31, 9, 9, 10),
                ModelTransform.of(0, -15, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-7, -8, -1, 13, 14, 2),
                ModelTransform.of(0, -57, -32, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 39, 3),
                ModelTransform.of(-20, -52, -20, -0.1920F, 0.0F, 0.2618F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 37, 3)
                        .cuboid(-2, 36, -2, 4, 5, 4),
                ModelTransform.of(0, 39, 0, -0.0672F, 0.0F, 0.0916F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 39, 3),
                ModelTransform.of(20, -52, -20, -0.1920F, 0.0F, -0.2618F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 37, 3)
                        .cuboid(-2, 36, -2, 4, 5, 4),
                ModelTransform.of(0, 39, 0, -0.0672F, 0.0F, -0.0916F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 39, 3),
                ModelTransform.of(-22, -52, 0, 0.0F, 0.0F, 0.2880F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 37, 3)
                        .cuboid(-2, 36, -2, 4, 5, 4),
                ModelTransform.of(0, 39, 0, 0.0F, 0.0F, 0.1008F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 39, 3),
                ModelTransform.of(22, -52, 0, 0.0F, 0.0F, -0.2880F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 37, 3)
                        .cuboid(-2, 36, -2, 4, 5, 4),
                ModelTransform.of(0, 39, 0, 0.0F, 0.0F, -0.1008F));

        ModelPartData leg4 = root.addChild("leg4",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 39, 3),
                ModelTransform.of(-18, -52, 20, 0.1920F, 0.0F, 0.2356F));

        ModelPartData leg4_shin = leg4.addChild("leg4_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 37, 3)
                        .cuboid(-2, 36, -2, 4, 5, 4),
                ModelTransform.of(0, 39, 0, 0.0672F, 0.0F, 0.0825F));

        ModelPartData leg5 = root.addChild("leg5",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 39, 3),
                ModelTransform.of(18, -52, 20, 0.1920F, 0.0F, -0.2356F));

        ModelPartData leg5_shin = leg5.addChild("leg5_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 37, 3)
                        .cuboid(-2, 36, -2, 4, 5, 4),
                ModelTransform.of(0, 39, 0, 0.0672F, 0.0F, -0.0825F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData goliath() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-15, -193, -17, 15, 20, 17)
                        .cuboid(-15, -193, 0, 15, 20, 17)
                        .cuboid(0, -193, -17, 15, 20, 17)
                        .cuboid(0, -193, 0, 15, 20, 17)
                        .cuboid(-23, -173, -25, 23, 26, 17)
                        .cuboid(-23, -173, -8, 23, 26, 17)
                        .cuboid(-23, -173, 8, 23, 26, 17)
                        .cuboid(0, -173, -25, 23, 26, 17)
                        .cuboid(0, -173, -8, 23, 26, 17)
                        .cuboid(0, -173, 8, 23, 26, 17)
                        .cuboid(-22, -148, -24, 22, 23, 24)
                        .cuboid(-22, -148, 0, 22, 23, 24)
                        .cuboid(0, -148, -24, 22, 23, 24)
                        .cuboid(0, -148, 0, 22, 23, 24)
                        .cuboid(-16, -125, -18, 16, 14, 18)
                        .cuboid(-16, -125, 0, 16, 14, 18)
                        .cuboid(0, -125, -18, 16, 14, 18)
                        .cuboid(0, -125, 0, 16, 14, 18)
                        .cuboid(-11, -111, -13, 22, 9, 13)
                        .cuboid(-11, -111, 0, 22, 9, 13)
                        .cuboid(-25, -173, -31, 12, 11, 9)
                        .cuboid(-25, -173, -22, 12, 11, 9)
                        .cuboid(-12, -173, -31, 12, 11, 9)
                        .cuboid(-12, -173, -22, 12, 11, 9)
                        .cuboid(0, -173, -31, 12, 11, 9)
                        .cuboid(0, -173, -22, 12, 11, 9)
                        .cuboid(12, -173, -31, 12, 11, 9)
                        .cuboid(12, -173, -22, 12, 11, 9)
                        .cuboid(-12, -116, -14, 12, 11, 9)
                        .cuboid(-12, -116, -5, 12, 11, 9)
                        .cuboid(-12, -116, 5, 12, 11, 9)
                        .cuboid(0, -116, -14, 12, 11, 9)
                        .cuboid(0, -116, -5, 12, 11, 9)
                        .cuboid(0, -116, 5, 12, 11, 9),
                ModelTransform.of(0, -8, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_l = body.addChild("eye_l",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -6, -1, 12, 11, 2),
                ModelTransform.of(-12, -159, -26, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_r = body.addChild("eye_r",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -6, -1, 12, 11, 2),
                ModelTransform.of(12, -159, -26, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(-16, -111, -12, 0.1571F, -0.1047F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, -0.2094F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, -0.2094F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(16, -111, -12, 0.1571F, 0.1047F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, 0.2094F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, 0.2094F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(-20, -111, 4, 0.1571F, -0.1571F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, -0.3142F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, -0.3142F, 0.0F));

        ModelPartData tent3_1_body = body.addChild("tent3_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(20, -111, 4, 0.1571F, 0.1571F, 0.0F));

        ModelPartData tent3_2_body = tent3_1_body.addChild("tent3_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, 0.3142F, 0.0F));

        ModelPartData tent3_3_body = tent3_2_body.addChild("tent3_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, 0.3142F, 0.0F));

        ModelPartData tent4_1_body = body.addChild("tent4_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(-10, -111, 16, 0.1571F, -0.0698F, 0.0F));

        ModelPartData tent4_2_body = tent4_1_body.addChild("tent4_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, -0.1396F, 0.0F));

        ModelPartData tent4_3_body = tent4_2_body.addChild("tent4_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, -0.1396F, 0.0F));

        ModelPartData tent5_1_body = body.addChild("tent5_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(10, -111, 16, 0.1571F, 0.0698F, 0.0F));

        ModelPartData tent5_2_body = tent5_1_body.addChild("tent5_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, 0.1396F, 0.0F));

        ModelPartData tent5_3_body = tent5_2_body.addChild("tent5_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 17, 3),
                ModelTransform.of(0, 17, 0, 0.3142F, 0.1396F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 74, 5),
                ModelTransform.of(-20, -122, -12, -0.1745F, 0.0F, 0.2269F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 71, 5)
                        .cuboid(-3, 70, -3, 6, 4, 6),
                ModelTransform.of(0, 74, 0, -0.0611F, 0.0F, 0.0794F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 74, 5),
                ModelTransform.of(20, -122, -12, -0.1745F, 0.0F, -0.2269F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 71, 5)
                        .cuboid(-3, 70, -3, 6, 4, 6),
                ModelTransform.of(0, 74, 0, -0.0611F, 0.0F, -0.0794F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 75, 5),
                ModelTransform.of(0, -122, 22, 0.2094F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 72, 5)
                        .cuboid(-3, 71, -3, 6, 4, 6),
                ModelTransform.of(0, 75, 0, 0.0733F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData heron() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 82, 3),
                ModelTransform.of(-11, -139, -6, -0.0698F, 0.0F, 0.1222F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 80, 3)
                        .cuboid(-2, 79, -2, 4, 3, 4),
                ModelTransform.of(0, 82, 0, -0.0244F, 0.0F, 0.0428F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 82, 3),
                ModelTransform.of(11, -139, -6, -0.0698F, 0.0F, -0.1222F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 80, 3)
                        .cuboid(-2, 79, -2, 4, 3, 4),
                ModelTransform.of(0, 82, 0, -0.0244F, 0.0F, -0.0428F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 82, 3),
                ModelTransform.of(0, -139, 12, 0.1047F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 80, 3)
                        .cuboid(-2, 79, -2, 4, 3, 4),
                ModelTransform.of(0, 82, 0, 0.0367F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-8, -204, -9, 8, 9, 9)
                        .cuboid(-8, -204, 0, 8, 9, 9)
                        .cuboid(-8, -195, -9, 8, 9, 9)
                        .cuboid(-8, -195, 0, 8, 9, 9)
                        .cuboid(-8, -186, -9, 8, 9, 9)
                        .cuboid(-8, -186, 0, 8, 9, 9)
                        .cuboid(-8, -178, -9, 8, 9, 9)
                        .cuboid(-8, -178, 0, 8, 9, 9)
                        .cuboid(-8, -169, -9, 8, 9, 9)
                        .cuboid(-8, -169, 0, 8, 9, 9)
                        .cuboid(0, -204, -9, 8, 9, 9)
                        .cuboid(0, -204, 0, 8, 9, 9)
                        .cuboid(0, -195, -9, 8, 9, 9)
                        .cuboid(0, -195, 0, 8, 9, 9)
                        .cuboid(0, -186, -9, 8, 9, 9)
                        .cuboid(0, -186, 0, 8, 9, 9)
                        .cuboid(0, -178, -9, 8, 9, 9)
                        .cuboid(0, -178, 0, 8, 9, 9)
                        .cuboid(0, -169, -9, 8, 9, 9)
                        .cuboid(0, -169, 0, 8, 9, 9)
                        .cuboid(-14, -160, -14, 9, 7, 9)
                        .cuboid(-14, -160, -5, 9, 7, 9)
                        .cuboid(-14, -160, 5, 9, 7, 9)
                        .cuboid(-14, -153, -14, 9, 7, 9)
                        .cuboid(-14, -153, -5, 9, 7, 9)
                        .cuboid(-14, -153, 5, 9, 7, 9)
                        .cuboid(-5, -160, -14, 9, 7, 9)
                        .cuboid(-5, -160, -5, 9, 7, 9)
                        .cuboid(-5, -160, 5, 9, 7, 9)
                        .cuboid(-5, -153, -14, 9, 7, 9)
                        .cuboid(-5, -153, -5, 9, 7, 9)
                        .cuboid(-5, -153, 5, 9, 7, 9)
                        .cuboid(5, -160, -14, 9, 7, 9)
                        .cuboid(5, -160, -5, 9, 7, 9)
                        .cuboid(5, -160, 5, 9, 7, 9)
                        .cuboid(5, -153, -14, 9, 7, 9)
                        .cuboid(5, -153, -5, 9, 7, 9)
                        .cuboid(5, -153, 5, 9, 7, 9)
                        .cuboid(-14, -172, -13, 28, 5, 26)
                        .cuboid(-14, -138, -13, 28, 4, 26)
                        .cuboid(-13, -190, -13, 26, 14, 26),
                ModelTransform.of(0, 11, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData rear_hood = body.addChild("rear_hood",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-15, 0, -13, 10, 11, 9)
                        .cuboid(-15, 0, -4, 10, 11, 9)
                        .cuboid(-15, 0, 4, 10, 11, 9)
                        .cuboid(-5, 0, -13, 10, 11, 9)
                        .cuboid(-5, 0, -4, 10, 11, 9)
                        .cuboid(-5, 0, 4, 10, 11, 9)
                        .cuboid(5, 0, -13, 10, 11, 9)
                        .cuboid(5, 0, -4, 10, 11, 9)
                        .cuboid(5, 0, 4, 10, 11, 9)
                        .cuboid(-14, 11, -11, 9, 11, 11)
                        .cuboid(-14, 11, 0, 9, 11, 11)
                        .cuboid(-5, 11, -11, 9, 11, 11)
                        .cuboid(-5, 11, 0, 9, 11, 11)
                        .cuboid(5, 11, -11, 9, 11, 11)
                        .cuboid(5, 11, 0, 9, 11, 11)
                        .cuboid(-10, 22, -8, 10, 8, 8)
                        .cuboid(-10, 22, 0, 10, 8, 8)
                        .cuboid(0, 22, -8, 10, 8, 8)
                        .cuboid(0, 22, 0, 10, 8, 8)
                        .cuboid(-6, 30, -5, 6, 4, 10)
                        .cuboid(0, 30, -5, 6, 4, 10)
                        .cuboid(-10, -5, 13, 3, 22, 11)
                        .cuboid(0, -5, 13, 3, 22, 11)
                        .cuboid(10, -5, 13, 3, 22, 11)
                        .cuboid(-7, 4, 16, 5, 9, 2)
                        .cuboid(2, 4, 16, 5, 9, 2),
                ModelTransform.of(0, -204, 20, 0.1047F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-17, -3, -18, 34, 7, 36)
                        .cuboid(-13, -7, -14, 26, 4, 28)
                        .cuboid(-17, 4, -18, 5, 26, 34)
                        .cuboid(12, 4, -18, 5, 26, 34)
                        .cuboid(-12, 4, 10, 24, 26, 6)
                        .cuboid(-17, 4, -24, 34, 6, 8)
                        .cuboid(-12, 30, -16, 24, 4, 28)
                        .cuboid(-20, -1, -6, 3, 5, 20)
                        .cuboid(17, -1, -6, 3, 5, 20)
                        .cuboid(-10, 2, 14, 3, 24, 11)
                        .cuboid(0, 2, 14, 3, 24, 11)
                        .cuboid(10, 2, 14, 3, 24, 11),
                ModelTransform.of(0, -204, -22, -0.2094F, 0.0F, 0.0F));

        ModelPartData pilot = body.addChild("pilot",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-8, 0, -4, 16, 14, 12)
                        .cuboid(-5, -9, -2, 10, 9, 10)
                        .cuboid(-4, -7, -4, 3, 2, 2)
                        .cuboid(1, -7, -4, 3, 2, 2),
                ModelTransform.of(0, -186, -14, 0.0F, 0.0F, 0.0F));

        ModelPartData cannon0 = body.addChild("cannon0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -2, -16, 5, 5, 18)
                        .cuboid(-3, -3, -18, 7, 7, 2)
                        .cuboid(-1, 2, -8, 3, 4, 8),
                ModelTransform.of(-11, -180, -20, 0.0F, 0.0F, 0.0F));

        ModelPartData cannon1 = body.addChild("cannon1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -2, -16, 5, 5, 18)
                        .cuboid(-3, -3, -18, 7, 7, 2)
                        .cuboid(-1, 2, -8, 3, 4, 8),
                ModelTransform.of(11, -180, -20, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 13, 3),
                ModelTransform.of(-9, -120, -2, 0.0698F, -0.1047F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 13, 3),
                ModelTransform.of(0, 13, 0, 0.1396F, -0.2094F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 13, 3),
                ModelTransform.of(0, 13, 0, 0.1396F, -0.2094F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 13, 3),
                ModelTransform.of(9, -120, -2, 0.0698F, 0.1047F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 13, 3),
                ModelTransform.of(0, 13, 0, 0.1396F, 0.2094F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 13, 3),
                ModelTransform.of(0, 13, 0, 0.1396F, 0.2094F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData spider() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-16, -60, -20, 11, 8, 13)
                        .cuboid(-16, -60, -7, 11, 8, 13)
                        .cuboid(-16, -60, 7, 11, 8, 13)
                        .cuboid(-5, -60, -20, 11, 8, 13)
                        .cuboid(-5, -60, -7, 11, 8, 13)
                        .cuboid(-5, -60, 7, 11, 8, 13)
                        .cuboid(5, -60, -20, 11, 8, 13)
                        .cuboid(5, -60, -7, 11, 8, 13)
                        .cuboid(5, -60, 7, 11, 8, 13)
                        .cuboid(-19, -52, -23, 13, 9, 12)
                        .cuboid(-19, -52, -12, 13, 9, 12)
                        .cuboid(-19, -52, 0, 13, 9, 12)
                        .cuboid(-19, -52, 12, 13, 9, 12)
                        .cuboid(-6, -52, -23, 13, 9, 12)
                        .cuboid(-6, -52, -12, 13, 9, 12)
                        .cuboid(-6, -52, 0, 13, 9, 12)
                        .cuboid(-6, -52, 12, 13, 9, 12)
                        .cuboid(6, -52, -23, 13, 9, 12)
                        .cuboid(6, -52, -12, 13, 9, 12)
                        .cuboid(6, -52, 0, 13, 9, 12)
                        .cuboid(6, -52, 12, 13, 9, 12)
                        .cuboid(-16, -43, -20, 11, 7, 13)
                        .cuboid(-16, -43, -7, 11, 7, 13)
                        .cuboid(-16, -43, 7, 11, 7, 13)
                        .cuboid(-5, -43, -20, 11, 7, 13)
                        .cuboid(-5, -43, -7, 11, 7, 13)
                        .cuboid(-5, -43, 7, 11, 7, 13)
                        .cuboid(5, -43, -20, 11, 7, 13)
                        .cuboid(5, -43, -7, 11, 7, 13)
                        .cuboid(5, -43, 7, 11, 7, 13)
                        .cuboid(-11, -36, -15, 11, 4, 10)
                        .cuboid(-11, -36, -5, 11, 4, 10)
                        .cuboid(-11, -36, 5, 11, 4, 10)
                        .cuboid(0, -36, -15, 11, 4, 10)
                        .cuboid(0, -36, -5, 11, 4, 10)
                        .cuboid(0, -36, 5, 11, 4, 10)
                        .cuboid(-9, -64, 6, 3, 9, 14)
                        .cuboid(0, -64, 6, 3, 9, 14)
                        .cuboid(9, -64, 6, 3, 9, 14),
                ModelTransform.of(0, 11, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-13, -3, -10, 26, 9, 14)
                        .cuboid(-9, -2, -18, 18, 7, 9)
                        .cuboid(-4, -1, -24, 8, 5, 7),
                ModelTransform.of(0, -52, -20, -0.0698F, 0.0F, 0.0F));

        ModelPartData eye = head.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -2, -1, 12, 4, 2),
                ModelTransform.of(0, 2, -22, 0.0F, 0.0F, 0.0F));

        ModelPartData blade0 = head.addChild("blade0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -22, 2, 3, 22),
                ModelTransform.of(-9, 4, -20, 0.1745F, -0.2443F, 0.0F));

        ModelPartData blade1 = head.addChild("blade1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -22, 2, 3, 22),
                ModelTransform.of(-4, 4, -20, 0.0698F, -0.0873F, 0.0F));

        ModelPartData blade2 = head.addChild("blade2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -22, 2, 3, 22),
                ModelTransform.of(4, 4, -20, 0.0698F, 0.0873F, 0.0F));

        ModelPartData blade3 = head.addChild("blade3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -22, 2, 3, 22),
                ModelTransform.of(9, 4, -20, 0.1745F, 0.2443F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 30, 2),
                ModelTransform.of(-17, -25, -16, -0.2513F, 0.0F, 0.4451F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 28, 2)
                        .cuboid(-2, 27, -2, 3, 3, 3),
                ModelTransform.of(0, 30, 0, -0.1508F, 0.0F, 0.2670F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 30, 2),
                ModelTransform.of(17, -25, -16, -0.2513F, 0.0F, -0.4451F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 28, 2)
                        .cuboid(-2, 27, -2, 3, 3, 3),
                ModelTransform.of(0, 30, 0, -0.1508F, 0.0F, -0.2670F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 30, 2),
                ModelTransform.of(-19, -25, -5, -0.0785F, 0.0F, 0.4974F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 28, 2)
                        .cuboid(-2, 27, -2, 3, 3, 3),
                ModelTransform.of(0, 30, 0, -0.0471F, 0.0F, 0.2985F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 30, 2),
                ModelTransform.of(19, -25, -5, -0.0785F, 0.0F, -0.4974F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 28, 2)
                        .cuboid(-2, 27, -2, 3, 3, 3),
                ModelTransform.of(0, 30, 0, -0.0471F, 0.0F, -0.2985F));

        ModelPartData leg4 = root.addChild("leg4",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 30, 2),
                ModelTransform.of(-19, -25, 6, 0.0942F, 0.0F, 0.4974F));

        ModelPartData leg4_shin = leg4.addChild("leg4_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 28, 2)
                        .cuboid(-2, 27, -2, 3, 3, 3),
                ModelTransform.of(0, 30, 0, 0.0565F, 0.0F, 0.2985F));

        ModelPartData leg5 = root.addChild("leg5",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 30, 2),
                ModelTransform.of(19, -25, 6, 0.0942F, 0.0F, -0.4974F));

        ModelPartData leg5_shin = leg5.addChild("leg5_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 28, 2)
                        .cuboid(-2, 27, -2, 3, 3, 3),
                ModelTransform.of(0, 30, 0, 0.0565F, 0.0F, -0.2985F));

        ModelPartData leg6 = root.addChild("leg6",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 30, 2),
                ModelTransform.of(-17, -25, 17, 0.2670F, 0.0F, 0.4451F));

        ModelPartData leg6_shin = leg6.addChild("leg6_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 28, 2)
                        .cuboid(-2, 27, -2, 3, 3, 3),
                ModelTransform.of(0, 30, 0, 0.1602F, 0.0F, 0.2670F));

        ModelPartData leg7 = root.addChild("leg7",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 30, 2),
                ModelTransform.of(17, -25, 17, 0.2670F, 0.0F, -0.4451F));

        ModelPartData leg7_shin = leg7.addChild("leg7_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 28, 2)
                        .cuboid(-2, 27, -2, 3, 3, 3),
                ModelTransform.of(0, 30, 0, 0.1602F, 0.0F, -0.2670F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData louse() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-8, -10, -10, 16, 8, 20)
                        .cuboid(-10, -3, -12, 20, 8, 24)
                        .cuboid(-8, 5, -10, 16, 5, 20)
                        .cuboid(-10, -10, -11, 4, 1, 4)
                        .cuboid(-9, -9, -10, 2, 1, 2)
                        .cuboid(-8, -8, -9, 1, 1, 1)
                        .cuboid(6, -10, -11, 4, 1, 4)
                        .cuboid(7, -9, -10, 2, 1, 2)
                        .cuboid(8, -8, -9, 1, 1, 1)
                        .cuboid(-10, -10, 7, 4, 1, 4)
                        .cuboid(-9, -9, 8, 2, 1, 2)
                        .cuboid(-8, -8, 9, 1, 1, 1)
                        .cuboid(6, -10, 7, 4, 1, 4)
                        .cuboid(7, -9, 8, 2, 1, 2)
                        .cuboid(8, -8, 9, 1, 1, 1),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-4, -3, -1, 7, 5, 2),
                ModelTransform.of(0, -5, -12, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2),
                ModelTransform.of(-9, 5, -8, -0.1117F, 0.0F, 0.1885F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 8, 2)
                        .cuboid(-1, 6, -1, 2, 4, 2),
                ModelTransform.of(0, 9, 0, -0.0391F, 0.0F, 0.0660F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2),
                ModelTransform.of(9, 5, -8, -0.1117F, 0.0F, -0.1885F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 8, 2)
                        .cuboid(-1, 6, -1, 2, 4, 2),
                ModelTransform.of(0, 9, 0, -0.0391F, 0.0F, -0.0660F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2),
                ModelTransform.of(-9, 5, 8, 0.1117F, 0.0F, 0.1885F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 8, 2)
                        .cuboid(-1, 6, -1, 2, 4, 2),
                ModelTransform.of(0, 9, 0, 0.0391F, 0.0F, 0.0660F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2),
                ModelTransform.of(9, 5, 8, 0.1117F, 0.0F, -0.1885F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 8, 2)
                        .cuboid(-1, 6, -1, 2, 4, 2),
                ModelTransform.of(0, 9, 0, 0.0391F, 0.0F, -0.0660F));

        ModelPartData leg4 = root.addChild("leg4",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2),
                ModelTransform.of(0, 5, -9, -0.1257F, 0.0F, 0.0F));

        ModelPartData leg4_shin = leg4.addChild("leg4_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 8, 2)
                        .cuboid(-1, 6, -1, 2, 4, 2),
                ModelTransform.of(0, 9, 0, -0.0440F, 0.0F, 0.0F));

        ModelPartData leg5 = root.addChild("leg5",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2),
                ModelTransform.of(0, 5, 9, 0.1257F, 0.0F, 0.0F));

        ModelPartData leg5_shin = leg5.addChild("leg5_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 8, 2)
                        .cuboid(-1, 6, -1, 2, 4, 2),
                ModelTransform.of(0, 9, 0, 0.0440F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData flyingMachine() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-23, -48, -28, 12, 8, 11)
                        .cuboid(-23, -48, -17, 12, 8, 11)
                        .cuboid(-23, -48, -6, 12, 8, 11)
                        .cuboid(-23, -48, 6, 12, 8, 11)
                        .cuboid(-23, -48, 17, 12, 8, 11)
                        .cuboid(-12, -48, -28, 12, 8, 11)
                        .cuboid(-12, -48, -17, 12, 8, 11)
                        .cuboid(-12, -48, -6, 12, 8, 11)
                        .cuboid(-12, -48, 6, 12, 8, 11)
                        .cuboid(-12, -48, 17, 12, 8, 11)
                        .cuboid(0, -48, -28, 12, 8, 11)
                        .cuboid(0, -48, -17, 12, 8, 11)
                        .cuboid(0, -48, -6, 12, 8, 11)
                        .cuboid(0, -48, 6, 12, 8, 11)
                        .cuboid(0, -48, 17, 12, 8, 11)
                        .cuboid(12, -48, -28, 12, 8, 11)
                        .cuboid(12, -48, -17, 12, 8, 11)
                        .cuboid(12, -48, -6, 12, 8, 11)
                        .cuboid(12, -48, 6, 12, 8, 11)
                        .cuboid(12, -48, 17, 12, 8, 11)
                        .cuboid(-22, -40, -27, 11, 8, 11)
                        .cuboid(-22, -40, -16, 11, 8, 11)
                        .cuboid(-22, -40, -5, 11, 8, 11)
                        .cuboid(-22, -40, 5, 11, 8, 11)
                        .cuboid(-22, -40, 16, 11, 8, 11)
                        .cuboid(-11, -40, -27, 11, 8, 11)
                        .cuboid(-11, -40, -16, 11, 8, 11)
                        .cuboid(-11, -40, -5, 11, 8, 11)
                        .cuboid(-11, -40, 5, 11, 8, 11)
                        .cuboid(-11, -40, 16, 11, 8, 11)
                        .cuboid(0, -40, -27, 11, 8, 11)
                        .cuboid(0, -40, -16, 11, 8, 11)
                        .cuboid(0, -40, -5, 11, 8, 11)
                        .cuboid(0, -40, 5, 11, 8, 11)
                        .cuboid(0, -40, 16, 11, 8, 11)
                        .cuboid(11, -40, -27, 11, 8, 11)
                        .cuboid(11, -40, -16, 11, 8, 11)
                        .cuboid(11, -40, -5, 11, 8, 11)
                        .cuboid(11, -40, 5, 11, 8, 11)
                        .cuboid(11, -40, 16, 11, 8, 11)
                        .cuboid(-17, -32, -21, 11, 6, 10)
                        .cuboid(-17, -32, -10, 11, 6, 10)
                        .cuboid(-17, -32, 0, 11, 6, 10)
                        .cuboid(-17, -32, 10, 11, 6, 10)
                        .cuboid(-6, -32, -21, 11, 6, 10)
                        .cuboid(-6, -32, -10, 11, 6, 10)
                        .cuboid(-6, -32, 0, 11, 6, 10)
                        .cuboid(-6, -32, 10, 11, 6, 10)
                        .cuboid(6, -32, -21, 11, 6, 10)
                        .cuboid(6, -32, -10, 11, 6, 10)
                        .cuboid(6, -32, 0, 11, 6, 10)
                        .cuboid(6, -32, 10, 11, 6, 10)
                        .cuboid(-11, -25, -14, 11, 4, 9)
                        .cuboid(-11, -25, -5, 11, 4, 9)
                        .cuboid(-11, -25, 5, 11, 4, 9)
                        .cuboid(0, -25, -14, 11, 4, 9)
                        .cuboid(0, -25, -5, 11, 4, 9)
                        .cuboid(0, -25, 5, 11, 4, 9)
                        .cuboid(-10, -32, -13, 10, 9, 9)
                        .cuboid(-10, -32, -4, 10, 9, 9)
                        .cuboid(-10, -32, 4, 10, 9, 9)
                        .cuboid(0, -32, -13, 10, 9, 9)
                        .cuboid(0, -32, -4, 10, 9, 9)
                        .cuboid(0, -32, 4, 10, 9, 9)
                        .cuboid(-14, -48, -33, 9, 5, 10)
                        .cuboid(-5, -48, -33, 9, 5, 10)
                        .cuboid(5, -48, -33, 9, 5, 10),
                ModelTransform.of(0, 21, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-7, -4, -1, 14, 8, 2),
                ModelTransform.of(0, -40, -30, 0.0F, 0.0F, 0.0F));

        ModelPartData wing_l = body.addChild("wing_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-18, -2, -13, 18, 6, 26)
                        .cuboid(-16, -6, -11, 12, 5, 16)
                        .cuboid(-15, -9, -6, 6, 3, 6)
                        .cuboid(-16, -6, 7, 12, 5, 16)
                        .cuboid(-15, -9, 12, 6, 3, 6),
                ModelTransform.of(-20, -46, 2, 0.0F, 0.0F, 0.1396F));

        ModelPartData wing_r = body.addChild("wing_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(0, -2, -13, 18, 6, 26)
                        .cuboid(4, -6, -11, 12, 5, 16)
                        .cuboid(7, -9, -6, 6, 3, 6)
                        .cuboid(4, -6, 7, 12, 5, 16)
                        .cuboid(7, -9, 12, 6, 3, 6),
                ModelTransform.of(20, -46, 2, 0.0F, 0.0F, -0.1396F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2),
                ModelTransform.of(-9, -6, -14, -0.0977F, 0.0F, 0.1571F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 13, 2)
                        .cuboid(-2, 12, -2, 3, 3, 3),
                ModelTransform.of(0, 15, 0, -0.0342F, 0.0F, 0.0550F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2),
                ModelTransform.of(9, -6, -14, -0.0977F, 0.0F, -0.1571F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 13, 2)
                        .cuboid(-2, 12, -2, 3, 3, 3),
                ModelTransform.of(0, 15, 0, -0.0342F, 0.0F, -0.0550F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2),
                ModelTransform.of(-9, -6, 16, 0.1117F, 0.0F, 0.1571F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 13, 2)
                        .cuboid(-2, 12, -2, 3, 3, 3),
                ModelTransform.of(0, 15, 0, 0.0391F, 0.0F, 0.0550F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2),
                ModelTransform.of(9, -6, 16, 0.1117F, 0.0F, -0.1571F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 13, 2)
                        .cuboid(-2, 12, -2, 3, 3, 3),
                ModelTransform.of(0, 15, 0, 0.0391F, 0.0F, -0.0550F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData electric() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 37, 4),
                ModelTransform.of(-13, -51, -8, -0.1396F, 0.0F, 0.2094F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 35, 4)
                        .cuboid(-3, 33, -3, 5, 3, 5),
                ModelTransform.of(0, 37, 0, -0.0489F, 0.0F, 0.0733F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 37, 4),
                ModelTransform.of(13, -51, -8, -0.1396F, 0.0F, -0.2094F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 35, 4)
                        .cuboid(-3, 33, -3, 5, 3, 5),
                ModelTransform.of(0, 37, 0, -0.0489F, 0.0F, -0.0733F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 38, 4),
                ModelTransform.of(0, -51, 12, 0.1745F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 36, 4)
                        .cuboid(-3, 35, -3, 5, 3, 5),
                ModelTransform.of(0, 38, 0, 0.0611F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-13, -117, -14, 9, 9, 9)
                        .cuboid(-13, -117, -5, 9, 9, 9)
                        .cuboid(-13, -117, 5, 9, 9, 9)
                        .cuboid(-13, -108, -14, 9, 9, 9)
                        .cuboid(-13, -108, -5, 9, 9, 9)
                        .cuboid(-13, -108, 5, 9, 9, 9)
                        .cuboid(-13, -98, -14, 9, 9, 9)
                        .cuboid(-13, -98, -5, 9, 9, 9)
                        .cuboid(-13, -98, 5, 9, 9, 9)
                        .cuboid(-13, -89, -14, 9, 9, 9)
                        .cuboid(-13, -89, -5, 9, 9, 9)
                        .cuboid(-13, -89, 5, 9, 9, 9)
                        .cuboid(-13, -78, -14, 9, 9, 9)
                        .cuboid(-13, -78, -5, 9, 9, 9)
                        .cuboid(-13, -78, 5, 9, 9, 9)
                        .cuboid(-4, -117, -14, 9, 9, 9)
                        .cuboid(-4, -117, -5, 9, 9, 9)
                        .cuboid(-4, -117, 5, 9, 9, 9)
                        .cuboid(-4, -108, -14, 9, 9, 9)
                        .cuboid(-4, -108, -5, 9, 9, 9)
                        .cuboid(-4, -108, 5, 9, 9, 9)
                        .cuboid(-4, -98, -14, 9, 9, 9)
                        .cuboid(-4, -98, -5, 9, 9, 9)
                        .cuboid(-4, -98, 5, 9, 9, 9)
                        .cuboid(-4, -89, -14, 9, 9, 9)
                        .cuboid(-4, -89, -5, 9, 9, 9)
                        .cuboid(-4, -89, 5, 9, 9, 9)
                        .cuboid(-4, -78, -14, 9, 9, 9)
                        .cuboid(-4, -78, -5, 9, 9, 9)
                        .cuboid(-4, -78, 5, 9, 9, 9)
                        .cuboid(4, -117, -14, 9, 9, 9)
                        .cuboid(4, -117, -5, 9, 9, 9)
                        .cuboid(4, -117, 5, 9, 9, 9)
                        .cuboid(4, -108, -14, 9, 9, 9)
                        .cuboid(4, -108, -5, 9, 9, 9)
                        .cuboid(4, -108, 5, 9, 9, 9)
                        .cuboid(4, -98, -14, 9, 9, 9)
                        .cuboid(4, -98, -5, 9, 9, 9)
                        .cuboid(4, -98, 5, 9, 9, 9)
                        .cuboid(4, -89, -14, 9, 9, 9)
                        .cuboid(4, -89, -5, 9, 9, 9)
                        .cuboid(4, -89, 5, 9, 9, 9)
                        .cuboid(4, -78, -14, 9, 9, 9)
                        .cuboid(4, -78, -5, 9, 9, 9)
                        .cuboid(4, -78, 5, 9, 9, 9)
                        .cuboid(-17, -113, -15, 34, 5, 30)
                        .cuboid(-17, -101, -15, 34, 5, 30)
                        .cuboid(-17, -90, -15, 34, 5, 30)
                        .cuboid(-9, -69, -10, 9, 9, 10)
                        .cuboid(-9, -69, 0, 9, 9, 10)
                        .cuboid(0, -69, -10, 9, 9, 10)
                        .cuboid(0, -69, 0, 9, 9, 10)
                        .cuboid(-13, -140, -14, 9, 9, 9)
                        .cuboid(-13, -140, -5, 9, 9, 9)
                        .cuboid(-13, -140, 5, 9, 9, 9)
                        .cuboid(-4, -140, -14, 9, 9, 9)
                        .cuboid(-4, -140, -5, 9, 9, 9)
                        .cuboid(-4, -140, 5, 9, 9, 9)
                        .cuboid(4, -140, -14, 9, 9, 9)
                        .cuboid(4, -140, -5, 9, 9, 9)
                        .cuboid(4, -140, 5, 9, 9, 9)
                        .cuboid(-11, -131, -12, 7, 8, 8)
                        .cuboid(-11, -131, -4, 7, 8, 8)
                        .cuboid(-11, -131, 4, 7, 8, 8)
                        .cuboid(-4, -131, -12, 7, 8, 8)
                        .cuboid(-4, -131, -4, 7, 8, 8)
                        .cuboid(-4, -131, 4, 7, 8, 8)
                        .cuboid(4, -131, -12, 7, 8, 8)
                        .cuboid(4, -131, -4, 7, 8, 8)
                        .cuboid(4, -131, 4, 7, 8, 8)
                        .cuboid(-7, -123, -8, 7, 5, 8)
                        .cuboid(-7, -123, 0, 7, 5, 8)
                        .cuboid(0, -123, -8, 7, 5, 8)
                        .cuboid(0, -123, 0, 7, 5, 8),
                ModelTransform.of(0, 20, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData coil = body.addChild("coil",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-8, -7, -1, 16, 14, 2),
                ModelTransform.of(0, -138, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -2, -16, 4, 5, 18),
                ModelTransform.of(-12, -124, -10, -0.4887F, 0.0F, -0.3491F));

        ModelPartData arm_l_tip = arm_l.addChild("arm_l_tip",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -3, -1, 5, 6, 2),
                ModelTransform.of(0, 0, -18, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -2, -16, 4, 5, 18),
                ModelTransform.of(12, -124, -10, -0.4887F, 0.0F, 0.3491F));

        ModelPartData arm_r_tip = arm_r.addChild("arm_r_tip",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -3, -1, 5, 6, 2),
                ModelTransform.of(0, 0, -18, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData handling() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-20, -92, -23, 10, 13, 12)
                        .cuboid(-20, -92, -12, 10, 13, 12)
                        .cuboid(-20, -92, 0, 10, 13, 12)
                        .cuboid(-20, -92, 12, 10, 13, 12)
                        .cuboid(-10, -92, -23, 10, 13, 12)
                        .cuboid(-10, -92, -12, 10, 13, 12)
                        .cuboid(-10, -92, 0, 10, 13, 12)
                        .cuboid(-10, -92, 12, 10, 13, 12)
                        .cuboid(0, -92, -23, 10, 13, 12)
                        .cuboid(0, -92, -12, 10, 13, 12)
                        .cuboid(0, -92, 0, 10, 13, 12)
                        .cuboid(0, -92, 12, 10, 13, 12)
                        .cuboid(10, -92, -23, 10, 13, 12)
                        .cuboid(10, -92, -12, 10, 13, 12)
                        .cuboid(10, -92, 0, 10, 13, 12)
                        .cuboid(10, -92, 12, 10, 13, 12)
                        .cuboid(-19, -79, -22, 10, 13, 11)
                        .cuboid(-19, -79, -11, 10, 13, 11)
                        .cuboid(-19, -79, 0, 10, 13, 11)
                        .cuboid(-19, -79, 11, 10, 13, 11)
                        .cuboid(-10, -79, -22, 10, 13, 11)
                        .cuboid(-10, -79, -11, 10, 13, 11)
                        .cuboid(-10, -79, 0, 10, 13, 11)
                        .cuboid(-10, -79, 11, 10, 13, 11)
                        .cuboid(0, -79, -22, 10, 13, 11)
                        .cuboid(0, -79, -11, 10, 13, 11)
                        .cuboid(0, -79, 0, 10, 13, 11)
                        .cuboid(0, -79, 11, 10, 13, 11)
                        .cuboid(10, -79, -22, 10, 13, 11)
                        .cuboid(10, -79, -11, 10, 13, 11)
                        .cuboid(10, -79, 0, 10, 13, 11)
                        .cuboid(10, -79, 11, 10, 13, 11)
                        .cuboid(-14, -66, -16, 9, 9, 11)
                        .cuboid(-14, -66, -5, 9, 9, 11)
                        .cuboid(-14, -66, 5, 9, 9, 11)
                        .cuboid(-5, -66, -16, 9, 9, 11)
                        .cuboid(-5, -66, -5, 9, 9, 11)
                        .cuboid(-5, -66, 5, 9, 9, 11)
                        .cuboid(5, -66, -16, 9, 9, 11)
                        .cuboid(5, -66, -5, 9, 9, 11)
                        .cuboid(5, -66, 5, 9, 9, 11)
                        .cuboid(-11, -62, -13, 7, 11, 9)
                        .cuboid(-11, -62, -4, 7, 11, 9)
                        .cuboid(-11, -62, 4, 7, 11, 9)
                        .cuboid(-4, -62, -13, 7, 11, 9)
                        .cuboid(-4, -62, -4, 7, 11, 9)
                        .cuboid(-4, -62, 4, 7, 11, 9)
                        .cuboid(4, -62, -13, 7, 11, 9)
                        .cuboid(4, -62, -4, 7, 11, 9)
                        .cuboid(4, -62, 4, 7, 11, 9),
                ModelTransform.of(0, 13, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -4, -1, 12, 8, 2),
                ModelTransform.of(0, -81, -25, 0.0F, 0.0F, 0.0F));

        ModelPartData cage = body.addChild("cage",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-11, 0, -9, 22, 3, 18)
                        .cuboid(-11, 30, -9, 22, 3, 18)
                        .cuboid(-11, 3, -9, 3, 26, 3)
                        .cuboid(-11, 3, 6, 3, 26, 3)
                        .cuboid(8, 3, -9, 3, 26, 3)
                        .cuboid(8, 3, 6, 3, 26, 3)
                        .cuboid(-3, 3, -9, 6, 26, 3)
                        .cuboid(-3, 3, 6, 6, 26, 3),
                ModelTransform.of(0, -57, -16, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -28, 6, 7, 32)
                        .cuboid(-6, 1, -36, 5, 6, 11)
                        .cuboid(1, 1, -36, 5, 6, 11),
                ModelTransform.of(-17, -73, -14, -0.5585F, 0.0F, -0.2443F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -28, 6, 7, 32)
                        .cuboid(-6, 1, -36, 5, 6, 11)
                        .cuboid(1, 1, -36, 5, 6, 11),
                ModelTransform.of(17, -73, -14, -0.5585F, 0.0F, 0.2443F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 33, 4),
                ModelTransform.of(-19, -40, -14, -0.1100F, 0.0F, 0.2321F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 31, 4)
                        .cuboid(-3, 30, -3, 5, 3, 5),
                ModelTransform.of(0, 33, 0, -0.0385F, 0.0F, 0.0812F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 33, 4),
                ModelTransform.of(19, -40, -14, -0.1100F, 0.0F, -0.2321F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 31, 4)
                        .cuboid(-3, 30, -3, 5, 3, 5),
                ModelTransform.of(0, 33, 0, -0.0385F, 0.0F, -0.0812F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 33, 4),
                ModelTransform.of(-19, -40, 14, 0.1100F, 0.0F, 0.2321F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 31, 4)
                        .cuboid(-3, 30, -3, 5, 3, 5),
                ModelTransform.of(0, 33, 0, 0.0385F, 0.0F, 0.0812F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 33, 4),
                ModelTransform.of(19, -40, 14, 0.1100F, 0.0F, -0.2321F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 31, 4)
                        .cuboid(-3, 30, -3, 5, 3, 5),
                ModelTransform.of(0, 33, 0, 0.0385F, 0.0F, -0.0812F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData digging() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-21, -61, -25, 14, 8, 12)
                        .cuboid(-21, -61, -12, 14, 8, 12)
                        .cuboid(-21, -61, 0, 14, 8, 12)
                        .cuboid(-21, -61, 12, 14, 8, 12)
                        .cuboid(-21, -53, -25, 14, 8, 12)
                        .cuboid(-21, -53, -12, 14, 8, 12)
                        .cuboid(-21, -53, 0, 14, 8, 12)
                        .cuboid(-21, -53, 12, 14, 8, 12)
                        .cuboid(-7, -61, -25, 14, 8, 12)
                        .cuboid(-7, -61, -12, 14, 8, 12)
                        .cuboid(-7, -61, 0, 14, 8, 12)
                        .cuboid(-7, -61, 12, 14, 8, 12)
                        .cuboid(-7, -53, -25, 14, 8, 12)
                        .cuboid(-7, -53, -12, 14, 8, 12)
                        .cuboid(-7, -53, 0, 14, 8, 12)
                        .cuboid(-7, -53, 12, 14, 8, 12)
                        .cuboid(7, -61, -25, 14, 8, 12)
                        .cuboid(7, -61, -12, 14, 8, 12)
                        .cuboid(7, -61, 0, 14, 8, 12)
                        .cuboid(7, -61, 12, 14, 8, 12)
                        .cuboid(7, -53, -25, 14, 8, 12)
                        .cuboid(7, -53, -12, 14, 8, 12)
                        .cuboid(7, -53, 0, 14, 8, 12)
                        .cuboid(7, -53, 12, 14, 8, 12)
                        .cuboid(-17, -44, -15, 11, 11, 10)
                        .cuboid(-17, -44, -6, 11, 11, 10)
                        .cuboid(-17, -44, 4, 11, 11, 10)
                        .cuboid(-17, -44, 14, 11, 11, 10)
                        .cuboid(-6, -44, -15, 11, 11, 10)
                        .cuboid(-6, -44, -6, 11, 11, 10)
                        .cuboid(-6, -44, 4, 11, 11, 10)
                        .cuboid(-6, -44, 14, 11, 11, 10)
                        .cuboid(6, -44, -15, 11, 11, 10)
                        .cuboid(6, -44, -6, 11, 11, 10)
                        .cuboid(6, -44, 4, 11, 11, 10)
                        .cuboid(6, -44, 14, 11, 11, 10)
                        .cuboid(-10, -61, -17, 10, 6, 7)
                        .cuboid(-10, -61, -10, 10, 6, 7)
                        .cuboid(-10, -61, -2, 10, 6, 7)
                        .cuboid(0, -61, -17, 10, 6, 7)
                        .cuboid(0, -61, -10, 10, 6, 7)
                        .cuboid(0, -61, -2, 10, 6, 7),
                ModelTransform.of(0, 35, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -3, -1, 10, 6, 2),
                ModelTransform.of(0, -53, -23, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-11, -9, -12, 22, 19, 13)
                        .cuboid(-8, -7, -20, 16, 15, 9)
                        .cuboid(-5, -4, -26, 10, 8, 7)
                        .cuboid(-2, -2, -30, 4, 4, 5)
                        .cuboid(-1, -1, -32, 2, 2, 3),
                ModelTransform.of(0, -48, -24, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 23, 5),
                ModelTransform.of(-21, -22, -16, -0.0977F, 0.0F, 0.1833F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 21, 5)
                        .cuboid(-3, 20, -3, 6, 3, 6),
                ModelTransform.of(0, 23, 0, -0.0342F, 0.0F, 0.0641F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 23, 5),
                ModelTransform.of(21, -22, -16, -0.0977F, 0.0F, -0.1833F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 21, 5)
                        .cuboid(-3, 20, -3, 6, 3, 6),
                ModelTransform.of(0, 23, 0, -0.0342F, 0.0F, -0.0641F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 23, 5),
                ModelTransform.of(-21, -22, 12, 0.0733F, 0.0F, 0.1833F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 21, 5)
                        .cuboid(-3, 20, -3, 6, 3, 6),
                ModelTransform.of(0, 23, 0, 0.0257F, 0.0F, 0.0641F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 23, 5),
                ModelTransform.of(21, -22, 12, 0.0733F, 0.0F, -0.1833F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 21, 5)
                        .cuboid(-3, 20, -3, 6, 3, 6),
                ModelTransform.of(0, 23, 0, 0.0257F, 0.0F, -0.0641F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData tempest() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_fl = root.addChild("leg_fl",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 6, 75, 6),
                ModelTransform.of(-22, -123, -16, -0.1222F, 0.0F, 0.1571F));

        ModelPartData leg_fl_shin = leg_fl.addChild("leg_fl_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 6, 73, 6)
                        .cuboid(-4, 72, -4, 8, 3, 8),
                ModelTransform.of(0, 75, 0, -0.0428F, 0.0F, 0.0550F));

        ModelPartData leg_fr = root.addChild("leg_fr",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 6, 75, 6),
                ModelTransform.of(22, -123, -16, -0.1222F, 0.0F, -0.1571F));

        ModelPartData leg_fr_shin = leg_fr.addChild("leg_fr_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 6, 73, 6)
                        .cuboid(-4, 72, -4, 8, 3, 8),
                ModelTransform.of(0, 75, 0, -0.0428F, 0.0F, -0.0550F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 6, 75, 6),
                ModelTransform.of(-24, -123, 18, 0.1222F, 0.0F, 0.1571F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 6, 73, 6)
                        .cuboid(-4, 72, -4, 8, 3, 8),
                ModelTransform.of(0, 75, 0, 0.0428F, 0.0F, 0.0550F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 6, 75, 6),
                ModelTransform.of(24, -123, 18, 0.1222F, 0.0F, -0.1571F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 6, 73, 6)
                        .cuboid(-4, 72, -4, 8, 3, 8),
                ModelTransform.of(0, 75, 0, 0.0428F, 0.0F, -0.0550F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-17, -237, -20, 11, 14, 13)
                        .cuboid(-17, -237, -7, 11, 14, 13)
                        .cuboid(-17, -237, 7, 11, 14, 13)
                        .cuboid(-17, -222, -20, 11, 14, 13)
                        .cuboid(-17, -222, -7, 11, 14, 13)
                        .cuboid(-17, -222, 7, 11, 14, 13)
                        .cuboid(-17, -209, -20, 11, 14, 13)
                        .cuboid(-17, -209, -7, 11, 14, 13)
                        .cuboid(-17, -209, 7, 11, 14, 13)
                        .cuboid(-17, -195, -20, 11, 14, 13)
                        .cuboid(-17, -195, -7, 11, 14, 13)
                        .cuboid(-17, -195, 7, 11, 14, 13)
                        .cuboid(-17, -181, -20, 11, 14, 13)
                        .cuboid(-17, -181, -7, 11, 14, 13)
                        .cuboid(-17, -181, 7, 11, 14, 13)
                        .cuboid(-17, -167, -20, 11, 14, 13)
                        .cuboid(-17, -167, -7, 11, 14, 13)
                        .cuboid(-17, -167, 7, 11, 14, 13)
                        .cuboid(-6, -237, -20, 11, 14, 13)
                        .cuboid(-6, -237, -7, 11, 14, 13)
                        .cuboid(-6, -237, 7, 11, 14, 13)
                        .cuboid(-6, -222, -20, 11, 14, 13)
                        .cuboid(-6, -222, -7, 11, 14, 13)
                        .cuboid(-6, -222, 7, 11, 14, 13)
                        .cuboid(-6, -209, -20, 11, 14, 13)
                        .cuboid(-6, -209, -7, 11, 14, 13)
                        .cuboid(-6, -209, 7, 11, 14, 13)
                        .cuboid(-6, -195, -20, 11, 14, 13)
                        .cuboid(-6, -195, -7, 11, 14, 13)
                        .cuboid(-6, -195, 7, 11, 14, 13)
                        .cuboid(-6, -181, -20, 11, 14, 13)
                        .cuboid(-6, -181, -7, 11, 14, 13)
                        .cuboid(-6, -181, 7, 11, 14, 13)
                        .cuboid(-6, -167, -20, 11, 14, 13)
                        .cuboid(-6, -167, -7, 11, 14, 13)
                        .cuboid(-6, -167, 7, 11, 14, 13)
                        .cuboid(6, -237, -20, 11, 14, 13)
                        .cuboid(6, -237, -7, 11, 14, 13)
                        .cuboid(6, -237, 7, 11, 14, 13)
                        .cuboid(6, -222, -20, 11, 14, 13)
                        .cuboid(6, -222, -7, 11, 14, 13)
                        .cuboid(6, -222, 7, 11, 14, 13)
                        .cuboid(6, -209, -20, 11, 14, 13)
                        .cuboid(6, -209, -7, 11, 14, 13)
                        .cuboid(6, -209, 7, 11, 14, 13)
                        .cuboid(6, -195, -20, 11, 14, 13)
                        .cuboid(6, -195, -7, 11, 14, 13)
                        .cuboid(6, -195, 7, 11, 14, 13)
                        .cuboid(6, -181, -20, 11, 14, 13)
                        .cuboid(6, -181, -7, 11, 14, 13)
                        .cuboid(6, -181, 7, 11, 14, 13)
                        .cuboid(6, -167, -20, 11, 14, 13)
                        .cuboid(6, -167, -7, 11, 14, 13)
                        .cuboid(6, -167, 7, 11, 14, 13)
                        .cuboid(-24, -161, -24, 12, 8, 12)
                        .cuboid(-24, -161, -12, 12, 8, 12)
                        .cuboid(-24, -161, 0, 12, 8, 12)
                        .cuboid(-24, -161, 12, 12, 8, 12)
                        .cuboid(-24, -153, -24, 12, 8, 12)
                        .cuboid(-24, -153, -12, 12, 8, 12)
                        .cuboid(-24, -153, 0, 12, 8, 12)
                        .cuboid(-24, -153, 12, 12, 8, 12)
                        .cuboid(-12, -161, -24, 12, 8, 12)
                        .cuboid(-12, -161, -12, 12, 8, 12)
                        .cuboid(-12, -161, 0, 12, 8, 12)
                        .cuboid(-12, -161, 12, 12, 8, 12)
                        .cuboid(-12, -153, -24, 12, 8, 12)
                        .cuboid(-12, -153, -12, 12, 8, 12)
                        .cuboid(-12, -153, 0, 12, 8, 12)
                        .cuboid(-12, -153, 12, 12, 8, 12)
                        .cuboid(0, -161, -24, 12, 8, 12)
                        .cuboid(0, -161, -12, 12, 8, 12)
                        .cuboid(0, -161, 0, 12, 8, 12)
                        .cuboid(0, -161, 12, 12, 8, 12)
                        .cuboid(0, -153, -24, 12, 8, 12)
                        .cuboid(0, -153, -12, 12, 8, 12)
                        .cuboid(0, -153, 0, 12, 8, 12)
                        .cuboid(0, -153, 12, 12, 8, 12)
                        .cuboid(12, -161, -24, 12, 8, 12)
                        .cuboid(12, -161, -12, 12, 8, 12)
                        .cuboid(12, -161, 0, 12, 8, 12)
                        .cuboid(12, -161, 12, 12, 8, 12)
                        .cuboid(12, -153, -24, 12, 8, 12)
                        .cuboid(12, -153, -12, 12, 8, 12)
                        .cuboid(12, -153, 0, 12, 8, 12)
                        .cuboid(12, -153, 12, 12, 8, 12)
                        .cuboid(-23, -204, -23, 12, 11, 12)
                        .cuboid(-23, -204, -12, 12, 11, 12)
                        .cuboid(-23, -204, 0, 12, 11, 12)
                        .cuboid(-23, -204, 12, 12, 11, 12)
                        .cuboid(-23, -193, -23, 12, 11, 12)
                        .cuboid(-23, -193, -12, 12, 11, 12)
                        .cuboid(-23, -193, 0, 12, 11, 12)
                        .cuboid(-23, -193, 12, 12, 11, 12)
                        .cuboid(-12, -204, -23, 12, 11, 12)
                        .cuboid(-12, -204, -12, 12, 11, 12)
                        .cuboid(-12, -204, 0, 12, 11, 12)
                        .cuboid(-12, -204, 12, 12, 11, 12)
                        .cuboid(-12, -193, -23, 12, 11, 12)
                        .cuboid(-12, -193, -12, 12, 11, 12)
                        .cuboid(-12, -193, 0, 12, 11, 12)
                        .cuboid(-12, -193, 12, 12, 11, 12)
                        .cuboid(0, -204, -23, 12, 11, 12)
                        .cuboid(0, -204, -12, 12, 11, 12)
                        .cuboid(0, -204, 0, 12, 11, 12)
                        .cuboid(0, -204, 12, 12, 11, 12)
                        .cuboid(0, -193, -23, 12, 11, 12)
                        .cuboid(0, -193, -12, 12, 11, 12)
                        .cuboid(0, -193, 0, 12, 11, 12)
                        .cuboid(0, -193, 12, 12, 11, 12)
                        .cuboid(12, -204, -23, 12, 11, 12)
                        .cuboid(12, -204, -12, 12, 11, 12)
                        .cuboid(12, -204, 0, 12, 11, 12)
                        .cuboid(12, -204, 12, 12, 11, 12)
                        .cuboid(12, -193, -23, 12, 11, 12)
                        .cuboid(12, -193, -12, 12, 11, 12)
                        .cuboid(12, -193, 0, 12, 11, 12)
                        .cuboid(12, -193, 12, 12, 11, 12),
                ModelTransform.of(0, 30, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-10, -8, -16, 20, 16, 22),
                ModelTransform.of(0, -237, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = head.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -3, -1, 10, 6, 2),
                ModelTransform.of(0, 0, -17, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-7, -7, -20, 14, 14, 24)
                        .cuboid(-3, -3, -22, 6, 6, 3),
                ModelTransform.of(-16, -216, -20, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-7, -7, -20, 14, 14, 24)
                        .cuboid(-3, -3, -22, 6, 6, 3),
                ModelTransform.of(16, -216, -20, 0.0F, 0.0F, 0.0F));

        ModelPartData stack_l = body.addChild("stack_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 31, 10),
                ModelTransform.of(-13, -220, 22, 0.0F, 0.0F, 0.0F));

        ModelPartData stack_l_mouth = stack_l.addChild("stack_l_mouth",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -3, -1, 6, 6, 2),
                ModelTransform.of(0, 31, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData stack_r = body.addChild("stack_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 31, 10),
                ModelTransform.of(13, -220, 22, 0.0F, 0.0F, 0.0F));

        ModelPartData stack_r_mouth = stack_r.addChild("stack_r_mouth",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -3, -1, 6, 6, 2),
                ModelTransform.of(0, 31, 0, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData bombarding() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 35, 4),
                ModelTransform.of(-12, -44, 0, 0.0F, 0.0F, 0.1745F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 32, 4)
                        .cuboid(-3, 31, -3, 5, 3, 5),
                ModelTransform.of(0, 35, 0, 0.0F, 0.0F, 0.0611F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 35, 4),
                ModelTransform.of(12, -44, 0, 0.0F, 0.0F, -0.1745F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 32, 4)
                        .cuboid(-3, 31, -3, 5, 3, 5),
                ModelTransform.of(0, 35, 0, 0.0F, 0.0F, -0.0611F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-16, -101, -18, 11, 13, 12)
                        .cuboid(-16, -101, -6, 11, 13, 12)
                        .cuboid(-16, -101, 6, 11, 13, 12)
                        .cuboid(-16, -89, -18, 11, 13, 12)
                        .cuboid(-16, -89, -6, 11, 13, 12)
                        .cuboid(-16, -89, 6, 11, 13, 12)
                        .cuboid(-16, -77, -18, 11, 13, 12)
                        .cuboid(-16, -77, -6, 11, 13, 12)
                        .cuboid(-16, -77, 6, 11, 13, 12)
                        .cuboid(-5, -101, -18, 11, 13, 12)
                        .cuboid(-5, -101, -6, 11, 13, 12)
                        .cuboid(-5, -101, 6, 11, 13, 12)
                        .cuboid(-5, -89, -18, 11, 13, 12)
                        .cuboid(-5, -89, -6, 11, 13, 12)
                        .cuboid(-5, -89, 6, 11, 13, 12)
                        .cuboid(-5, -77, -18, 11, 13, 12)
                        .cuboid(-5, -77, -6, 11, 13, 12)
                        .cuboid(-5, -77, 6, 11, 13, 12)
                        .cuboid(5, -101, -18, 11, 13, 12)
                        .cuboid(5, -101, -6, 11, 13, 12)
                        .cuboid(5, -101, 6, 11, 13, 12)
                        .cuboid(5, -89, -18, 11, 13, 12)
                        .cuboid(5, -89, -6, 11, 13, 12)
                        .cuboid(5, -89, 6, 11, 13, 12)
                        .cuboid(5, -77, -18, 11, 13, 12)
                        .cuboid(5, -77, -6, 11, 13, 12)
                        .cuboid(5, -77, 6, 11, 13, 12)
                        .cuboid(-12, -103, -5, 8, 9, 7)
                        .cuboid(-12, -103, 2, 8, 9, 7)
                        .cuboid(-12, -103, 10, 8, 9, 7)
                        .cuboid(-4, -103, -5, 8, 9, 7)
                        .cuboid(-4, -103, 2, 8, 9, 7)
                        .cuboid(-4, -103, 10, 8, 9, 7)
                        .cuboid(4, -103, -5, 8, 9, 7)
                        .cuboid(4, -103, 2, 8, 9, 7)
                        .cuboid(4, -103, 10, 8, 9, 7),
                ModelTransform.of(0, 20, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -3, -1, 10, 7, 2),
                ModelTransform.of(0, -92, -19, 0.0F, 0.0F, 0.0F));

        ModelPartData cannon = body.addChild("cannon",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-9, -9, -12, 18, 17, 18)
                        .cuboid(-8, -7, -16, 16, 5, 6)
                        .cuboid(-5, -16, -30, 10, 10, 20)
                        .cuboid(-7, -18, -34, 14, 15, 6)
                        .cuboid(-3, -15, -36, 6, 7, 3),
                ModelTransform.of(0, -106, -6, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData drone() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -15, -8, 4, 4, 5)
                        .cuboid(-6, -15, -2, 4, 4, 5)
                        .cuboid(-6, -15, 2, 4, 4, 5)
                        .cuboid(-2, -15, -8, 4, 4, 5)
                        .cuboid(-2, -15, -2, 4, 4, 5)
                        .cuboid(-2, -15, 2, 4, 4, 5)
                        .cuboid(2, -15, -8, 4, 4, 5)
                        .cuboid(2, -15, -2, 4, 4, 5)
                        .cuboid(2, -15, 2, 4, 4, 5)
                        .cuboid(-8, -11, -8, 5, 4, 6)
                        .cuboid(-8, -11, -3, 5, 4, 6)
                        .cuboid(-8, -11, 3, 5, 4, 6)
                        .cuboid(-2, -11, -8, 5, 4, 6)
                        .cuboid(-2, -11, -3, 5, 4, 6)
                        .cuboid(-2, -11, 3, 5, 4, 6)
                        .cuboid(2, -11, -8, 5, 4, 6)
                        .cuboid(2, -11, -3, 5, 4, 6)
                        .cuboid(2, -11, 3, 5, 4, 6)
                        .cuboid(-6, -7, -6, 6, 3, 4)
                        .cuboid(-6, -7, -2, 6, 3, 4)
                        .cuboid(-6, -7, 2, 6, 3, 4)
                        .cuboid(0, -7, -6, 6, 3, 4)
                        .cuboid(0, -7, -2, 6, 3, 4)
                        .cuboid(0, -7, 2, 6, 3, 4)
                        .cuboid(-4, -3, -4, 4, 2, 4)
                        .cuboid(-4, -3, 0, 4, 2, 4)
                        .cuboid(0, -3, -4, 4, 2, 4)
                        .cuboid(0, -3, 0, 4, 2, 4)
                        .cuboid(-6, -15, -6, 3, 1, 3)
                        .cuboid(-5, -14, -5, 1, 1, 1)
                        .cuboid(-5, -13, -5, 1, 1, 1)
                        .cuboid(4, -15, -6, 3, 1, 3)
                        .cuboid(5, -14, -5, 1, 1, 1)
                        .cuboid(5, -13, -5, 1, 1, 1)
                        .cuboid(-6, -15, 4, 3, 1, 3)
                        .cuboid(-5, -14, 5, 1, 1, 1)
                        .cuboid(-5, -13, 5, 1, 1, 1)
                        .cuboid(4, -15, 4, 3, 1, 3)
                        .cuboid(5, -14, 5, 1, 1, 1)
                        .cuboid(5, -13, 5, 1, 1, 1),
                ModelTransform.of(0, 7, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -2, -1, 5, 3, 2),
                ModelTransform.of(0, -11, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 12, 2),
                ModelTransform.of(-7, -1, -6, -0.0733F, 0.0F, 0.1833F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-1, 10, -1, 2, 3, 2),
                ModelTransform.of(0, 12, 0, -0.0257F, 0.0F, 0.0641F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 12, 2),
                ModelTransform.of(7, -1, -6, -0.0733F, 0.0F, -0.1833F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-1, 10, -1, 2, 3, 2),
                ModelTransform.of(0, 12, 0, -0.0257F, 0.0F, -0.0641F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 12, 2),
                ModelTransform.of(-8, -1, 0, 0.0F, 0.0F, 0.2094F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-1, 10, -1, 2, 3, 2),
                ModelTransform.of(0, 12, 0, 0.0F, 0.0F, 0.0733F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 12, 2),
                ModelTransform.of(8, -1, 0, 0.0F, 0.0F, -0.2094F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-1, 10, -1, 2, 3, 2),
                ModelTransform.of(0, 12, 0, 0.0F, 0.0F, -0.0733F));

        ModelPartData leg4 = root.addChild("leg4",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 12, 2),
                ModelTransform.of(-8, -1, 6, 0.0733F, 0.0F, 0.2094F));

        ModelPartData leg4_shin = leg4.addChild("leg4_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-1, 10, -1, 2, 3, 2),
                ModelTransform.of(0, 12, 0, 0.0257F, 0.0F, 0.0733F));

        ModelPartData leg5 = root.addChild("leg5",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 12, 2),
                ModelTransform.of(8, -1, 6, 0.0733F, 0.0F, -0.2094F));

        ModelPartData leg5_shin = leg5.addChild("leg5_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-1, 10, -1, 2, 3, 2),
                ModelTransform.of(0, 12, 0, 0.0257F, 0.0F, -0.0733F));

        ModelPartData leg6 = root.addChild("leg6",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 12, 2),
                ModelTransform.of(-6, -1, 9, 0.1100F, 0.0F, 0.1571F));

        ModelPartData leg6_shin = leg6.addChild("leg6_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-1, 10, -1, 2, 3, 2),
                ModelTransform.of(0, 12, 0, 0.0385F, 0.0F, 0.0550F));

        ModelPartData leg7 = root.addChild("leg7",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 12, 2),
                ModelTransform.of(6, -1, 9, 0.1100F, 0.0F, -0.1571F));

        ModelPartData leg7_shin = leg7.addChild("leg7_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 11, 2)
                        .cuboid(-1, 10, -1, 2, 3, 2),
                ModelTransform.of(0, 12, 0, 0.0385F, 0.0F, -0.0550F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData humanTripod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-14, -123, -16, 9, 11, 11)
                        .cuboid(-14, -123, -5, 9, 11, 11)
                        .cuboid(-14, -123, 5, 9, 11, 11)
                        .cuboid(-5, -123, -16, 9, 11, 11)
                        .cuboid(-5, -123, -5, 9, 11, 11)
                        .cuboid(-5, -123, 5, 9, 11, 11)
                        .cuboid(5, -123, -16, 9, 11, 11)
                        .cuboid(5, -123, -5, 9, 11, 11)
                        .cuboid(5, -123, 5, 9, 11, 11)
                        .cuboid(-18, -112, -20, 12, 13, 10)
                        .cuboid(-18, -112, -10, 12, 13, 10)
                        .cuboid(-18, -112, 0, 12, 13, 10)
                        .cuboid(-18, -112, 10, 12, 13, 10)
                        .cuboid(-6, -112, -20, 12, 13, 10)
                        .cuboid(-6, -112, -10, 12, 13, 10)
                        .cuboid(-6, -112, 0, 12, 13, 10)
                        .cuboid(-6, -112, 10, 12, 13, 10)
                        .cuboid(6, -112, -20, 12, 13, 10)
                        .cuboid(6, -112, -10, 12, 13, 10)
                        .cuboid(6, -112, 0, 12, 13, 10)
                        .cuboid(6, -112, 10, 12, 13, 10)
                        .cuboid(-17, -99, -19, 11, 13, 10)
                        .cuboid(-17, -99, -10, 11, 13, 10)
                        .cuboid(-17, -99, 0, 11, 13, 10)
                        .cuboid(-17, -99, 10, 11, 13, 10)
                        .cuboid(-6, -99, -19, 11, 13, 10)
                        .cuboid(-6, -99, -10, 11, 13, 10)
                        .cuboid(-6, -99, 0, 11, 13, 10)
                        .cuboid(-6, -99, 10, 11, 13, 10)
                        .cuboid(6, -99, -19, 11, 13, 10)
                        .cuboid(6, -99, -10, 11, 13, 10)
                        .cuboid(6, -99, 0, 11, 13, 10)
                        .cuboid(6, -99, 10, 11, 13, 10)
                        .cuboid(-11, -85, -13, 11, 7, 9)
                        .cuboid(-11, -85, -4, 11, 7, 9)
                        .cuboid(-11, -85, 4, 11, 7, 9)
                        .cuboid(0, -85, -13, 11, 7, 9)
                        .cuboid(0, -85, -4, 11, 7, 9)
                        .cuboid(0, -85, 4, 11, 7, 9)
                        .cuboid(-11, -96, -23, 7, 10, 7)
                        .cuboid(-11, -96, -16, 7, 10, 7)
                        .cuboid(-11, -86, -23, 7, 10, 7)
                        .cuboid(-11, -86, -16, 7, 10, 7)
                        .cuboid(-4, -96, -23, 7, 10, 7)
                        .cuboid(-4, -96, -16, 7, 10, 7)
                        .cuboid(-4, -86, -23, 7, 10, 7)
                        .cuboid(-4, -86, -16, 7, 10, 7)
                        .cuboid(4, -96, -23, 7, 10, 7)
                        .cuboid(4, -96, -16, 7, 10, 7)
                        .cuboid(4, -86, -23, 7, 10, 7)
                        .cuboid(4, -86, -16, 7, 10, 7)
                        .cuboid(-8, -85, -24, 8, 9, 4)
                        .cuboid(0, -85, -24, 8, 9, 4)
                        .cuboid(-10, -72, -12, 10, 11, 8)
                        .cuboid(-10, -72, -4, 10, 11, 8)
                        .cuboid(-10, -72, 4, 10, 11, 8)
                        .cuboid(0, -72, -12, 10, 11, 8)
                        .cuboid(0, -72, -4, 10, 11, 8)
                        .cuboid(0, -72, 4, 10, 11, 8),
                ModelTransform.of(0, 19, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -3, -1, 12, 7, 2),
                ModelTransform.of(0, -87, -24, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, -3, -20, 6, 7, 24)
                        .cuboid(-2, -2, -22, 4, 4, 3),
                ModelTransform.of(-17, -112, -10, 0.0F, 0.0F, 0.1396F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, -3, -20, 6, 7, 24)
                        .cuboid(-2, -2, -22, 4, 4, 3),
                ModelTransform.of(17, -112, -10, 0.0F, 0.0F, -0.1396F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 38, 4),
                ModelTransform.of(-14, -53, -8, -0.1396F, 0.0F, 0.2094F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 36, 4)
                        .cuboid(-3, 35, -3, 6, 3, 6),
                ModelTransform.of(0, 38, 0, -0.0489F, 0.0F, 0.0733F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 38, 4),
                ModelTransform.of(14, -53, -8, -0.1396F, 0.0F, -0.2094F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 36, 4)
                        .cuboid(-3, 35, -3, 6, 3, 6),
                ModelTransform.of(0, 38, 0, -0.0489F, 0.0F, -0.0733F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 39, 4),
                ModelTransform.of(0, -53, 14, 0.1920F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 37, 4)
                        .cuboid(-3, 36, -3, 6, 3, 6),
                ModelTransform.of(0, 39, 0, 0.0672F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData waterTripod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_fl = root.addChild("leg_fl",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, 0, -4, 7, 64, 7),
                ModelTransform.of(-22, -97, -18, -0.2094F, 0.0F, 0.3142F));

        ModelPartData leg_fl_shin = leg_fl.addChild("leg_fl_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, 0, -4, 7, 60, 7)
                        .cuboid(-5, 59, -5, 9, 3, 9),
                ModelTransform.of(0, 64, 0, -0.0733F, 0.0F, 0.1100F));

        ModelPartData leg_fr = root.addChild("leg_fr",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, 0, -4, 7, 64, 7),
                ModelTransform.of(22, -97, -18, -0.2094F, 0.0F, -0.3142F));

        ModelPartData leg_fr_shin = leg_fr.addChild("leg_fr_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, 0, -4, 7, 60, 7)
                        .cuboid(-5, 59, -5, 9, 3, 9),
                ModelTransform.of(0, 64, 0, -0.0733F, 0.0F, -0.1100F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, 0, -4, 7, 64, 7),
                ModelTransform.of(0, -97, 22, 0.2793F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, 0, -4, 7, 60, 7)
                        .cuboid(-5, 59, -5, 9, 3, 9),
                ModelTransform.of(0, 64, 0, 0.0977F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-29, -121, -29, 14, 18, 14)
                        .cuboid(-29, -121, -14, 14, 18, 14)
                        .cuboid(-29, -121, 0, 14, 18, 14)
                        .cuboid(-29, -121, 14, 14, 18, 14)
                        .cuboid(-29, -103, -29, 14, 18, 14)
                        .cuboid(-29, -103, -14, 14, 18, 14)
                        .cuboid(-29, -103, 0, 14, 18, 14)
                        .cuboid(-29, -103, 14, 14, 18, 14)
                        .cuboid(-14, -121, -29, 14, 18, 14)
                        .cuboid(-14, -121, -14, 14, 18, 14)
                        .cuboid(-14, -121, 0, 14, 18, 14)
                        .cuboid(-14, -121, 14, 14, 18, 14)
                        .cuboid(-14, -103, -29, 14, 18, 14)
                        .cuboid(-14, -103, -14, 14, 18, 14)
                        .cuboid(-14, -103, 0, 14, 18, 14)
                        .cuboid(-14, -103, 14, 14, 18, 14)
                        .cuboid(0, -121, -29, 14, 18, 14)
                        .cuboid(0, -121, -14, 14, 18, 14)
                        .cuboid(0, -121, 0, 14, 18, 14)
                        .cuboid(0, -121, 14, 14, 18, 14)
                        .cuboid(0, -103, -29, 14, 18, 14)
                        .cuboid(0, -103, -14, 14, 18, 14)
                        .cuboid(0, -103, 0, 14, 18, 14)
                        .cuboid(0, -103, 14, 14, 18, 14)
                        .cuboid(14, -121, -29, 14, 18, 14)
                        .cuboid(14, -121, -14, 14, 18, 14)
                        .cuboid(14, -121, 0, 14, 18, 14)
                        .cuboid(14, -121, 14, 14, 18, 14)
                        .cuboid(14, -103, -29, 14, 18, 14)
                        .cuboid(14, -103, -14, 14, 18, 14)
                        .cuboid(14, -103, 0, 14, 18, 14)
                        .cuboid(14, -103, 14, 14, 18, 14)
                        .cuboid(-20, -83, -20, 13, 8, 13)
                        .cuboid(-20, -83, -7, 13, 8, 13)
                        .cuboid(-20, -83, 7, 13, 8, 13)
                        .cuboid(-7, -83, -20, 13, 8, 13)
                        .cuboid(-7, -83, -7, 13, 8, 13)
                        .cuboid(-7, -83, 7, 13, 8, 13)
                        .cuboid(7, -83, -20, 13, 8, 13)
                        .cuboid(7, -83, -7, 13, 8, 13)
                        .cuboid(7, -83, 7, 13, 8, 13)
                        .cuboid(-13, -127, -13, 9, 8, 9)
                        .cuboid(-13, -127, -4, 9, 8, 9)
                        .cuboid(-13, -127, 4, 9, 8, 9)
                        .cuboid(-4, -127, -13, 9, 8, 9)
                        .cuboid(-4, -127, -4, 9, 8, 9)
                        .cuboid(-4, -127, 4, 9, 8, 9)
                        .cuboid(4, -127, -13, 9, 8, 9)
                        .cuboid(4, -127, -4, 9, 8, 9)
                        .cuboid(4, -127, 4, 9, 8, 9),
                ModelTransform.of(0, -20, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-9, -79, -9, 18, 79, 18)
                        .cuboid(-15, -62, -15, 30, 7, 30)
                        .cuboid(-13, -42, -13, 26, 6, 26)
                        .cuboid(-12, -87, -12, 24, 6, 24)
                        .cuboid(-2, -100, -2, 4, 12, 4),
                ModelTransform.of(0, -121, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = head.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -3, -1, 9, 5, 2),
                ModelTransform.of(0, -71, -15, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, -4, -18, 8, 8, 36)
                        .cuboid(-5, -6, 16, 10, 12, 8)
                        .cuboid(-3, -4, -22, 6, 6, 4),
                ModelTransform.of(-24, -96, 0, 0.0F, 0.0F, 0.2443F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, -4, -18, 8, 8, 36)
                        .cuboid(-5, -6, 16, 10, 12, 8)
                        .cuboid(-3, -4, -22, 6, 6, 4),
                ModelTransform.of(24, -96, 0, 0.0F, 0.0F, -0.2443F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData scoutTripod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 23, 3),
                ModelTransform.of(-8, -22, -4, -0.0698F, 0.0F, 0.1396F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 21, 3)
                        .cuboid(-2, 20, -2, 4, 3, 4),
                ModelTransform.of(0, 23, 0, -0.0244F, 0.0F, 0.0489F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 23, 3),
                ModelTransform.of(8, -22, -4, -0.0698F, 0.0F, -0.1396F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 21, 3)
                        .cuboid(-2, 20, -2, 4, 3, 4),
                ModelTransform.of(0, 23, 0, -0.0244F, 0.0F, -0.0489F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 23, 3),
                ModelTransform.of(0, -22, 8, 0.1571F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 21, 3)
                        .cuboid(-2, 20, -2, 4, 3, 4),
                ModelTransform.of(0, 23, 0, 0.0550F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-11, -40, -11, 7, 8, 7)
                        .cuboid(-11, -40, -4, 7, 8, 7)
                        .cuboid(-11, -40, 4, 7, 8, 7)
                        .cuboid(-11, -32, -11, 7, 8, 7)
                        .cuboid(-11, -32, -4, 7, 8, 7)
                        .cuboid(-11, -32, 4, 7, 8, 7)
                        .cuboid(-4, -40, -11, 7, 8, 7)
                        .cuboid(-4, -40, -4, 7, 8, 7)
                        .cuboid(-4, -40, 4, 7, 8, 7)
                        .cuboid(-4, -32, -11, 7, 8, 7)
                        .cuboid(-4, -32, -4, 7, 8, 7)
                        .cuboid(-4, -32, 4, 7, 8, 7)
                        .cuboid(4, -40, -11, 7, 8, 7)
                        .cuboid(4, -40, -4, 7, 8, 7)
                        .cuboid(4, -40, 4, 7, 8, 7)
                        .cuboid(4, -32, -11, 7, 8, 7)
                        .cuboid(4, -32, -4, 7, 8, 7)
                        .cuboid(4, -32, 4, 7, 8, 7)
                        .cuboid(-8, -27, -8, 5, 6, 5)
                        .cuboid(-8, -27, -3, 5, 6, 5)
                        .cuboid(-8, -27, 3, 5, 6, 5)
                        .cuboid(-3, -27, -8, 5, 6, 5)
                        .cuboid(-3, -27, -3, 5, 6, 5)
                        .cuboid(-3, -27, 3, 5, 6, 5)
                        .cuboid(3, -27, -8, 5, 6, 5)
                        .cuboid(3, -27, -3, 5, 6, 5)
                        .cuboid(3, -27, 3, 5, 6, 5),
                ModelTransform.of(0, 1, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -2, -1, 6, 4, 2),
                ModelTransform.of(0, -35, -11, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, -3, -4, 6, 6, 10)
                        .cuboid(-2, -2, -18, 4, 4, 16)
                        .cuboid(-4, -4, -6, 8, 8, 4)
                        .cuboid(-1, -1, -20, 2, 2, 3),
                ModelTransform.of(8, -36, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -11, -2, 4, 13, 4)
                        .cuboid(-2, -15, -2, 4, 4, 4),
                ModelTransform.of(-8, -42, 6, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData ramTripod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 14, 5),
                ModelTransform.of(-16, -4, -6, -0.1047F, 0.0F, 0.3491F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 12, 5)
                        .cuboid(-3, 11, -3, 6, 3, 6),
                ModelTransform.of(0, 14, 0, -0.0367F, 0.0F, 0.1222F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 14, 5),
                ModelTransform.of(16, -4, -6, -0.1047F, 0.0F, -0.3491F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 12, 5)
                        .cuboid(-3, 11, -3, 6, 3, 6),
                ModelTransform.of(0, 14, 0, -0.0367F, 0.0F, -0.1222F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 14, 5),
                ModelTransform.of(0, -4, 12, 0.2793F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 12, 5)
                        .cuboid(-3, 11, -3, 6, 3, 6),
                ModelTransform.of(0, 14, 0, 0.0977F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-16, -38, -14, 11, 8, 10)
                        .cuboid(-16, -38, -4, 11, 8, 10)
                        .cuboid(-16, -38, 6, 11, 8, 10)
                        .cuboid(-16, -38, 16, 11, 8, 10)
                        .cuboid(-16, -29, -14, 11, 8, 10)
                        .cuboid(-16, -29, -4, 11, 8, 10)
                        .cuboid(-16, -29, 6, 11, 8, 10)
                        .cuboid(-16, -29, 16, 11, 8, 10)
                        .cuboid(-5, -38, -14, 11, 8, 10)
                        .cuboid(-5, -38, -4, 11, 8, 10)
                        .cuboid(-5, -38, 6, 11, 8, 10)
                        .cuboid(-5, -38, 16, 11, 8, 10)
                        .cuboid(-5, -29, -14, 11, 8, 10)
                        .cuboid(-5, -29, -4, 11, 8, 10)
                        .cuboid(-5, -29, 6, 11, 8, 10)
                        .cuboid(-5, -29, 16, 11, 8, 10)
                        .cuboid(5, -38, -14, 11, 8, 10)
                        .cuboid(5, -38, -4, 11, 8, 10)
                        .cuboid(5, -38, 6, 11, 8, 10)
                        .cuboid(5, -38, 16, 11, 8, 10)
                        .cuboid(5, -29, -14, 11, 8, 10)
                        .cuboid(5, -29, -4, 11, 8, 10)
                        .cuboid(5, -29, 6, 11, 8, 10)
                        .cuboid(5, -29, 16, 11, 8, 10)
                        .cuboid(-11, -29, -23, 7, 11, 7)
                        .cuboid(-11, -29, -16, 7, 11, 7)
                        .cuboid(-4, -29, -23, 7, 11, 7)
                        .cuboid(-4, -29, -16, 7, 11, 7)
                        .cuboid(4, -29, -23, 7, 11, 7)
                        .cuboid(4, -29, -16, 7, 11, 7),
                ModelTransform.of(0, 21, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-24, -13, -6, 48, 25, 6)
                        .cuboid(-18, -8, -10, 36, 17, 4)
                        .cuboid(-20, -15, -6, 5, 2, 5)
                        .cuboid(-19, -13, -5, 3, 2, 3)
                        .cuboid(-18, -11, -4, 1, 2, 1)
                        .cuboid(-10, -15, -6, 5, 2, 5)
                        .cuboid(-9, -13, -5, 3, 2, 3)
                        .cuboid(-8, -11, -4, 1, 2, 1)
                        .cuboid(-2, -15, -6, 5, 2, 5)
                        .cuboid(-1, -13, -5, 3, 2, 3)
                        .cuboid(0, -11, -4, 1, 2, 1)
                        .cuboid(6, -15, -6, 5, 2, 5)
                        .cuboid(7, -13, -5, 3, 2, 3)
                        .cuboid(8, -11, -4, 1, 2, 1)
                        .cuboid(16, -15, -6, 5, 2, 5)
                        .cuboid(17, -13, -5, 3, 2, 3)
                        .cuboid(18, -11, -4, 1, 2, 1),
                ModelTransform.of(0, -32, -22, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -2, -1, 10, 4, 2),
                ModelTransform.of(0, -34, -21, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, -4, -10, 8, 8, 20),
                ModelTransform.of(-18, -21, 8, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, -4, -10, 8, 8, 20),
                ModelTransform.of(18, -21, 8, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData uberpod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_fl = root.addChild("leg_fl",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 43, 5),
                ModelTransform.of(-16, -62, -12, -0.1745F, 0.0F, 0.2443F));

        ModelPartData leg_fl_shin = leg_fl.addChild("leg_fl_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 41, 5)
                        .cuboid(-3, 40, -3, 6, 3, 6),
                ModelTransform.of(0, 43, 0, -0.0611F, 0.0F, 0.0855F));

        ModelPartData leg_fr = root.addChild("leg_fr",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 43, 5),
                ModelTransform.of(16, -62, -12, -0.1745F, 0.0F, -0.2443F));

        ModelPartData leg_fr_shin = leg_fr.addChild("leg_fr_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 41, 5)
                        .cuboid(-3, 40, -3, 6, 3, 6),
                ModelTransform.of(0, 43, 0, -0.0611F, 0.0F, -0.0855F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 44, 5),
                ModelTransform.of(0, -62, 14, 0.2094F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 42, 5)
                        .cuboid(-3, 41, -3, 6, 3, 6),
                ModelTransform.of(0, 44, 0, 0.0733F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-12, -155, -13, 12, 10, 9)
                        .cuboid(-12, -155, -4, 12, 10, 9)
                        .cuboid(-12, -155, 4, 12, 10, 9)
                        .cuboid(0, -155, -13, 12, 10, 9)
                        .cuboid(0, -155, -4, 12, 10, 9)
                        .cuboid(0, -155, 4, 12, 10, 9)
                        .cuboid(-18, -144, -19, 12, 7, 10)
                        .cuboid(-18, -144, -10, 12, 7, 10)
                        .cuboid(-18, -144, 0, 12, 7, 10)
                        .cuboid(-18, -144, 10, 12, 7, 10)
                        .cuboid(-18, -137, -19, 12, 7, 10)
                        .cuboid(-18, -137, -10, 12, 7, 10)
                        .cuboid(-18, -137, 0, 12, 7, 10)
                        .cuboid(-18, -137, 10, 12, 7, 10)
                        .cuboid(-6, -144, -19, 12, 7, 10)
                        .cuboid(-6, -144, -10, 12, 7, 10)
                        .cuboid(-6, -144, 0, 12, 7, 10)
                        .cuboid(-6, -144, 10, 12, 7, 10)
                        .cuboid(-6, -137, -19, 12, 7, 10)
                        .cuboid(-6, -137, -10, 12, 7, 10)
                        .cuboid(-6, -137, 0, 12, 7, 10)
                        .cuboid(-6, -137, 10, 12, 7, 10)
                        .cuboid(6, -144, -19, 12, 7, 10)
                        .cuboid(6, -144, -10, 12, 7, 10)
                        .cuboid(6, -144, 0, 12, 7, 10)
                        .cuboid(6, -144, 10, 12, 7, 10)
                        .cuboid(6, -137, -19, 12, 7, 10)
                        .cuboid(6, -137, -10, 12, 7, 10)
                        .cuboid(6, -137, 0, 12, 7, 10)
                        .cuboid(6, -137, 10, 12, 7, 10)
                        .cuboid(-21, -130, -22, 10, 8, 11)
                        .cuboid(-21, -130, -11, 10, 8, 11)
                        .cuboid(-21, -130, 0, 10, 8, 11)
                        .cuboid(-21, -130, 11, 10, 8, 11)
                        .cuboid(-21, -122, -22, 10, 8, 11)
                        .cuboid(-21, -122, -11, 10, 8, 11)
                        .cuboid(-21, -122, 0, 10, 8, 11)
                        .cuboid(-21, -122, 11, 10, 8, 11)
                        .cuboid(-10, -130, -22, 10, 8, 11)
                        .cuboid(-10, -130, -11, 10, 8, 11)
                        .cuboid(-10, -130, 0, 10, 8, 11)
                        .cuboid(-10, -130, 11, 10, 8, 11)
                        .cuboid(-10, -122, -22, 10, 8, 11)
                        .cuboid(-10, -122, -11, 10, 8, 11)
                        .cuboid(-10, -122, 0, 10, 8, 11)
                        .cuboid(-10, -122, 11, 10, 8, 11)
                        .cuboid(0, -130, -22, 10, 8, 11)
                        .cuboid(0, -130, -11, 10, 8, 11)
                        .cuboid(0, -130, 0, 10, 8, 11)
                        .cuboid(0, -130, 11, 10, 8, 11)
                        .cuboid(0, -122, -22, 10, 8, 11)
                        .cuboid(0, -122, -11, 10, 8, 11)
                        .cuboid(0, -122, 0, 10, 8, 11)
                        .cuboid(0, -122, 11, 10, 8, 11)
                        .cuboid(10, -130, -22, 10, 8, 11)
                        .cuboid(10, -130, -11, 10, 8, 11)
                        .cuboid(10, -130, 0, 10, 8, 11)
                        .cuboid(10, -130, 11, 10, 8, 11)
                        .cuboid(10, -122, -22, 10, 8, 11)
                        .cuboid(10, -122, -11, 10, 8, 11)
                        .cuboid(10, -122, 0, 10, 8, 11)
                        .cuboid(10, -122, 11, 10, 8, 11)
                        .cuboid(-17, -113, -18, 11, 12, 12)
                        .cuboid(-17, -113, -6, 11, 12, 12)
                        .cuboid(-17, -113, 6, 11, 12, 12)
                        .cuboid(-6, -113, -18, 11, 12, 12)
                        .cuboid(-6, -113, -6, 11, 12, 12)
                        .cuboid(-6, -113, 6, 11, 12, 12)
                        .cuboid(6, -113, -18, 11, 12, 12)
                        .cuboid(6, -113, -6, 11, 12, 12)
                        .cuboid(6, -113, 6, 11, 12, 12)
                        .cuboid(-11, -101, -12, 11, 8, 12)
                        .cuboid(-11, -101, 0, 11, 8, 12)
                        .cuboid(0, -101, -12, 11, 8, 12)
                        .cuboid(0, -101, 0, 11, 8, 12)
                        .cuboid(-15, -82, -15, 10, 10, 10)
                        .cuboid(-15, -82, -5, 10, 10, 10)
                        .cuboid(-15, -82, 5, 10, 10, 10)
                        .cuboid(-5, -82, -15, 10, 10, 10)
                        .cuboid(-5, -82, -5, 10, 10, 10)
                        .cuboid(-5, -82, 5, 10, 10, 10)
                        .cuboid(5, -82, -15, 10, 10, 10)
                        .cuboid(5, -82, -5, 10, 10, 10)
                        .cuboid(5, -82, 5, 10, 10, 10),
                ModelTransform.of(0, 18, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-7, -4, -1, 14, 8, 2),
                ModelTransform.of(0, -115, -20, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -6, -12, 12, 12, 20)
                        .cuboid(-3, -3, -16, 6, 6, 3),
                ModelTransform.of(-18, -122, -12, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -6, -12, 12, 12, 20)
                        .cuboid(-3, -3, -16, 6, 6, 3),
                ModelTransform.of(18, -122, -12, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData bunkerTripod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 24, 10),
                ModelTransform.of(-34, -25, -20, -0.1047F, 0.0F, 0.2077F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 22, 10)
                        .cuboid(-6, 21, -6, 11, 3, 11),
                ModelTransform.of(0, 24, 0, -0.0367F, 0.0F, 0.0727F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 24, 10),
                ModelTransform.of(34, -25, -20, -0.1047F, 0.0F, -0.2077F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 22, 10)
                        .cuboid(-6, 21, -6, 11, 3, 11),
                ModelTransform.of(0, 24, 0, -0.0367F, 0.0F, -0.0727F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 24, 10),
                ModelTransform.of(-38, -25, 8, 0.0419F, 0.0F, 0.2321F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 22, 10)
                        .cuboid(-6, 21, -6, 11, 3, 11),
                ModelTransform.of(0, 24, 0, 0.0147F, 0.0F, 0.0812F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 24, 10),
                ModelTransform.of(38, -25, 8, 0.0419F, 0.0F, -0.2321F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 22, 10)
                        .cuboid(-6, 21, -6, 11, 3, 11),
                ModelTransform.of(0, 24, 0, 0.0147F, 0.0F, -0.0812F));

        ModelPartData leg4 = root.addChild("leg4",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 24, 10),
                ModelTransform.of(-18, -25, 30, 0.1571F, 0.0F, 0.1100F));

        ModelPartData leg4_shin = leg4.addChild("leg4_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 22, 10)
                        .cuboid(-6, 21, -6, 11, 3, 11),
                ModelTransform.of(0, 24, 0, 0.0550F, 0.0F, 0.0385F));

        ModelPartData leg5 = root.addChild("leg5",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 24, 10),
                ModelTransform.of(18, -25, 30, 0.1571F, 0.0F, -0.1100F));

        ModelPartData leg5_shin = leg5.addChild("leg5_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, 0, -5, 10, 22, 10)
                        .cuboid(-6, 21, -6, 11, 3, 11),
                ModelTransform.of(0, 24, 0, 0.0550F, 0.0F, -0.0385F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-38, -98, -31, 13, 13, 12)
                        .cuboid(-38, -98, -19, 13, 13, 12)
                        .cuboid(-38, -98, -6, 13, 13, 12)
                        .cuboid(-38, -98, 6, 13, 13, 12)
                        .cuboid(-38, -98, 19, 13, 13, 12)
                        .cuboid(-38, -85, -31, 13, 13, 12)
                        .cuboid(-38, -85, -19, 13, 13, 12)
                        .cuboid(-38, -85, -6, 13, 13, 12)
                        .cuboid(-38, -85, 6, 13, 13, 12)
                        .cuboid(-38, -85, 19, 13, 13, 12)
                        .cuboid(-38, -70, -31, 13, 13, 12)
                        .cuboid(-38, -70, -19, 13, 13, 12)
                        .cuboid(-38, -70, -6, 13, 13, 12)
                        .cuboid(-38, -70, 6, 13, 13, 12)
                        .cuboid(-38, -70, 19, 13, 13, 12)
                        .cuboid(-25, -98, -31, 13, 13, 12)
                        .cuboid(-25, -98, -19, 13, 13, 12)
                        .cuboid(-25, -98, -6, 13, 13, 12)
                        .cuboid(-25, -98, 6, 13, 13, 12)
                        .cuboid(-25, -98, 19, 13, 13, 12)
                        .cuboid(-25, -85, -31, 13, 13, 12)
                        .cuboid(-25, -85, -19, 13, 13, 12)
                        .cuboid(-25, -85, -6, 13, 13, 12)
                        .cuboid(-25, -85, 6, 13, 13, 12)
                        .cuboid(-25, -85, 19, 13, 13, 12)
                        .cuboid(-25, -70, -31, 13, 13, 12)
                        .cuboid(-25, -70, -19, 13, 13, 12)
                        .cuboid(-25, -70, -6, 13, 13, 12)
                        .cuboid(-25, -70, 6, 13, 13, 12)
                        .cuboid(-25, -70, 19, 13, 13, 12)
                        .cuboid(-13, -98, -31, 13, 13, 12)
                        .cuboid(-13, -98, -19, 13, 13, 12)
                        .cuboid(-13, -98, -6, 13, 13, 12)
                        .cuboid(-13, -98, 6, 13, 13, 12)
                        .cuboid(-13, -98, 19, 13, 13, 12)
                        .cuboid(-13, -85, -31, 13, 13, 12)
                        .cuboid(-13, -85, -19, 13, 13, 12)
                        .cuboid(-13, -85, -6, 13, 13, 12)
                        .cuboid(-13, -85, 6, 13, 13, 12)
                        .cuboid(-13, -85, 19, 13, 13, 12)
                        .cuboid(-13, -70, -31, 13, 13, 12)
                        .cuboid(-13, -70, -19, 13, 13, 12)
                        .cuboid(-13, -70, -6, 13, 13, 12)
                        .cuboid(-13, -70, 6, 13, 13, 12)
                        .cuboid(-13, -70, 19, 13, 13, 12)
                        .cuboid(0, -98, -31, 13, 13, 12)
                        .cuboid(0, -98, -19, 13, 13, 12)
                        .cuboid(0, -98, -6, 13, 13, 12)
                        .cuboid(0, -98, 6, 13, 13, 12)
                        .cuboid(0, -98, 19, 13, 13, 12)
                        .cuboid(0, -85, -31, 13, 13, 12)
                        .cuboid(0, -85, -19, 13, 13, 12)
                        .cuboid(0, -85, -6, 13, 13, 12)
                        .cuboid(0, -85, 6, 13, 13, 12)
                        .cuboid(0, -85, 19, 13, 13, 12)
                        .cuboid(0, -70, -31, 13, 13, 12)
                        .cuboid(0, -70, -19, 13, 13, 12)
                        .cuboid(0, -70, -6, 13, 13, 12)
                        .cuboid(0, -70, 6, 13, 13, 12)
                        .cuboid(0, -70, 19, 13, 13, 12)
                        .cuboid(13, -98, -31, 13, 13, 12)
                        .cuboid(13, -98, -19, 13, 13, 12)
                        .cuboid(13, -98, -6, 13, 13, 12)
                        .cuboid(13, -98, 6, 13, 13, 12)
                        .cuboid(13, -98, 19, 13, 13, 12)
                        .cuboid(13, -85, -31, 13, 13, 12)
                        .cuboid(13, -85, -19, 13, 13, 12)
                        .cuboid(13, -85, -6, 13, 13, 12)
                        .cuboid(13, -85, 6, 13, 13, 12)
                        .cuboid(13, -85, 19, 13, 13, 12)
                        .cuboid(13, -70, -31, 13, 13, 12)
                        .cuboid(13, -70, -19, 13, 13, 12)
                        .cuboid(13, -70, -6, 13, 13, 12)
                        .cuboid(13, -70, 6, 13, 13, 12)
                        .cuboid(13, -70, 19, 13, 13, 12)
                        .cuboid(25, -98, -31, 13, 13, 12)
                        .cuboid(25, -98, -19, 13, 13, 12)
                        .cuboid(25, -98, -6, 13, 13, 12)
                        .cuboid(25, -98, 6, 13, 13, 12)
                        .cuboid(25, -98, 19, 13, 13, 12)
                        .cuboid(25, -85, -31, 13, 13, 12)
                        .cuboid(25, -85, -19, 13, 13, 12)
                        .cuboid(25, -85, -6, 13, 13, 12)
                        .cuboid(25, -85, 6, 13, 13, 12)
                        .cuboid(25, -85, 19, 13, 13, 12)
                        .cuboid(25, -70, -31, 13, 13, 12)
                        .cuboid(25, -70, -19, 13, 13, 12)
                        .cuboid(25, -70, -6, 13, 13, 12)
                        .cuboid(25, -70, 6, 13, 13, 12)
                        .cuboid(25, -70, 19, 13, 13, 12)
                        .cuboid(-28, -57, -23, 11, 10, 12)
                        .cuboid(-28, -57, -12, 11, 10, 12)
                        .cuboid(-28, -57, 0, 11, 10, 12)
                        .cuboid(-28, -57, 12, 11, 10, 12)
                        .cuboid(-17, -57, -23, 11, 10, 12)
                        .cuboid(-17, -57, -12, 11, 10, 12)
                        .cuboid(-17, -57, 0, 11, 10, 12)
                        .cuboid(-17, -57, 12, 11, 10, 12)
                        .cuboid(-6, -57, -23, 11, 10, 12)
                        .cuboid(-6, -57, -12, 11, 10, 12)
                        .cuboid(-6, -57, 0, 11, 10, 12)
                        .cuboid(-6, -57, 12, 11, 10, 12)
                        .cuboid(6, -57, -23, 11, 10, 12)
                        .cuboid(6, -57, -12, 11, 10, 12)
                        .cuboid(6, -57, 0, 11, 10, 12)
                        .cuboid(6, -57, 12, 11, 10, 12)
                        .cuboid(17, -57, -23, 11, 10, 12)
                        .cuboid(17, -57, -12, 11, 10, 12)
                        .cuboid(17, -57, 0, 11, 10, 12)
                        .cuboid(17, -57, 12, 11, 10, 12)
                        .cuboid(-30, -102, -24, 12, 6, 12)
                        .cuboid(-30, -102, -12, 12, 6, 12)
                        .cuboid(-30, -102, 0, 12, 6, 12)
                        .cuboid(-30, -102, 12, 12, 6, 12)
                        .cuboid(-18, -102, -24, 12, 6, 12)
                        .cuboid(-18, -102, -12, 12, 6, 12)
                        .cuboid(-18, -102, 0, 12, 6, 12)
                        .cuboid(-18, -102, 12, 12, 6, 12)
                        .cuboid(-6, -102, -24, 12, 6, 12)
                        .cuboid(-6, -102, -12, 12, 6, 12)
                        .cuboid(-6, -102, 0, 12, 6, 12)
                        .cuboid(-6, -102, 12, 12, 6, 12)
                        .cuboid(6, -102, -24, 12, 6, 12)
                        .cuboid(6, -102, -12, 12, 6, 12)
                        .cuboid(6, -102, 0, 12, 6, 12)
                        .cuboid(6, -102, 12, 12, 6, 12)
                        .cuboid(18, -102, -24, 12, 6, 12)
                        .cuboid(18, -102, -12, 12, 6, 12)
                        .cuboid(18, -102, 0, 12, 6, 12)
                        .cuboid(18, -102, 12, 12, 6, 12),
                ModelTransform.of(0, 22, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData arm0 = body.addChild("arm0",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-1, -2, -3, 4, 6, 6)
                        .cuboid(-2, -3, -5, 5, 8, 10),
                ModelTransform.of(-39, -90, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData arm1 = body.addChild("arm1",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-1, -2, -3, 4, 6, 6)
                        .cuboid(-2, -3, -5, 5, 8, 10),
                ModelTransform.of(39, -90, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData arm2 = body.addChild("arm2",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-1, -2, -3, 4, 6, 6)
                        .cuboid(-2, -3, -5, 5, 8, 10),
                ModelTransform.of(-39, -80, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData arm3 = body.addChild("arm3",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-1, -2, -3, 4, 6, 6)
                        .cuboid(-2, -3, -5, 5, 8, 10),
                ModelTransform.of(39, -80, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData arm4 = body.addChild("arm4",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-1, -2, -3, 4, 6, 6)
                        .cuboid(-2, -3, -5, 5, 8, 10),
                ModelTransform.of(-39, -69, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData arm5 = body.addChild("arm5",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-1, -2, -3, 4, 6, 6)
                        .cuboid(-2, -3, -5, 5, 8, 10),
                ModelTransform.of(39, -69, -8, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -10, -6, 12, 10, 12)
                        .cuboid(-2, -8, -16, 4, 4, 12)
                        .cuboid(-3, -6, -18, 6, 5, 3),
                ModelTransform.of(0, -102, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -3, -1, 12, 6, 2),
                ModelTransform.of(0, -86, -32, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData flamerTripod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 29, 4),
                ModelTransform.of(-14, -35, -8, -0.1396F, 0.0F, 0.2094F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 27, 4)
                        .cuboid(-3, 26, -3, 6, 3, 6),
                ModelTransform.of(0, 29, 0, -0.0489F, 0.0F, 0.0733F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 29, 4),
                ModelTransform.of(14, -35, -8, -0.1396F, 0.0F, -0.2094F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 27, 4)
                        .cuboid(-3, 26, -3, 6, 3, 6),
                ModelTransform.of(0, 29, 0, -0.0489F, 0.0F, -0.0733F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 30, 4),
                ModelTransform.of(0, -35, 14, 0.2094F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 28, 4)
                        .cuboid(-3, 27, -3, 6, 3, 6),
                ModelTransform.of(0, 30, 0, 0.0733F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-15, -90, -17, 10, 11, 11)
                        .cuboid(-15, -90, -6, 10, 11, 11)
                        .cuboid(-15, -90, 6, 10, 11, 11)
                        .cuboid(-15, -80, -17, 10, 11, 11)
                        .cuboid(-15, -80, -6, 10, 11, 11)
                        .cuboid(-15, -80, 6, 10, 11, 11)
                        .cuboid(-15, -69, -17, 10, 11, 11)
                        .cuboid(-15, -69, -6, 10, 11, 11)
                        .cuboid(-15, -69, 6, 10, 11, 11)
                        .cuboid(-5, -90, -17, 10, 11, 11)
                        .cuboid(-5, -90, -6, 10, 11, 11)
                        .cuboid(-5, -90, 6, 10, 11, 11)
                        .cuboid(-5, -80, -17, 10, 11, 11)
                        .cuboid(-5, -80, -6, 10, 11, 11)
                        .cuboid(-5, -80, 6, 10, 11, 11)
                        .cuboid(-5, -69, -17, 10, 11, 11)
                        .cuboid(-5, -69, -6, 10, 11, 11)
                        .cuboid(-5, -69, 6, 10, 11, 11)
                        .cuboid(5, -90, -17, 10, 11, 11)
                        .cuboid(5, -90, -6, 10, 11, 11)
                        .cuboid(5, -90, 6, 10, 11, 11)
                        .cuboid(5, -80, -17, 10, 11, 11)
                        .cuboid(5, -80, -6, 10, 11, 11)
                        .cuboid(5, -80, 6, 10, 11, 11)
                        .cuboid(5, -69, -17, 10, 11, 11)
                        .cuboid(5, -69, -6, 10, 11, 11)
                        .cuboid(5, -69, 6, 10, 11, 11)
                        .cuboid(-11, -61, -13, 7, 8, 9)
                        .cuboid(-11, -61, -4, 7, 8, 9)
                        .cuboid(-11, -61, 4, 7, 8, 9)
                        .cuboid(-4, -61, -13, 7, 8, 9)
                        .cuboid(-4, -61, -4, 7, 8, 9)
                        .cuboid(-4, -61, 4, 7, 8, 9)
                        .cuboid(4, -61, -13, 7, 8, 9)
                        .cuboid(4, -61, -4, 7, 8, 9)
                        .cuboid(4, -61, 4, 7, 8, 9),
                ModelTransform.of(0, 20, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -3, -1, 10, 5, 2),
                ModelTransform.of(0, -82, -18, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, -5, -14, 10, 11, 16)
                        .cuboid(-3, -3, -28, 6, 6, 16)
                        .cuboid(-2, -2, -32, 4, 4, 4),
                ModelTransform.of(0, -84, -16, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -19, -6, 12, 27, 12)
                        .cuboid(-4, -23, -4, 8, 5, 8)
                        .cuboid(-2, -25, -2, 4, 3, 4),
                ModelTransform.of(-19, -82, 10, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -19, -6, 12, 27, 12)
                        .cuboid(-4, -23, -4, 8, 5, 8)
                        .cuboid(-2, -25, -2, 4, 3, 4),
                ModelTransform.of(19, -82, 10, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData tankTripod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_fl = root.addChild("leg_fl",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, 0, -6, 12, 63, 12),
                ModelTransform.of(-32, -95, -26, -0.2793F, 0.0F, 0.3840F));

        ModelPartData leg_fl_shin = leg_fl.addChild("leg_fl_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, 0, -6, 12, 59, 12)
                        .cuboid(-7, 58, -7, 14, 3, 14),
                ModelTransform.of(0, 63, 0, -0.0977F, 0.0F, 0.1344F));

        ModelPartData leg_fr = root.addChild("leg_fr",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, 0, -6, 12, 63, 12),
                ModelTransform.of(32, -95, -26, -0.2793F, 0.0F, -0.3840F));

        ModelPartData leg_fr_shin = leg_fr.addChild("leg_fr_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, 0, -6, 12, 59, 12)
                        .cuboid(-7, 58, -7, 14, 3, 14),
                ModelTransform.of(0, 63, 0, -0.0977F, 0.0F, -0.1344F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, 0, -6, 12, 64, 12),
                ModelTransform.of(0, -95, 32, 0.3491F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, 0, -6, 12, 60, 12)
                        .cuboid(-7, 59, -7, 14, 3, 14),
                ModelTransform.of(0, 64, 0, 0.1222F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-32, -204, -37, 16, 15, 15)
                        .cuboid(-32, -204, -22, 16, 15, 15)
                        .cuboid(-32, -204, -7, 16, 15, 15)
                        .cuboid(-32, -204, 7, 16, 15, 15)
                        .cuboid(-32, -204, 22, 16, 15, 15)
                        .cuboid(-32, -189, -37, 16, 15, 15)
                        .cuboid(-32, -189, -22, 16, 15, 15)
                        .cuboid(-32, -189, -7, 16, 15, 15)
                        .cuboid(-32, -189, 7, 16, 15, 15)
                        .cuboid(-32, -189, 22, 16, 15, 15)
                        .cuboid(-32, -173, -37, 16, 15, 15)
                        .cuboid(-32, -173, -22, 16, 15, 15)
                        .cuboid(-32, -173, -7, 16, 15, 15)
                        .cuboid(-32, -173, 7, 16, 15, 15)
                        .cuboid(-32, -173, 22, 16, 15, 15)
                        .cuboid(-32, -159, -37, 16, 15, 15)
                        .cuboid(-32, -159, -22, 16, 15, 15)
                        .cuboid(-32, -159, -7, 16, 15, 15)
                        .cuboid(-32, -159, 7, 16, 15, 15)
                        .cuboid(-32, -159, 22, 16, 15, 15)
                        .cuboid(-32, -144, -37, 16, 15, 15)
                        .cuboid(-32, -144, -22, 16, 15, 15)
                        .cuboid(-32, -144, -7, 16, 15, 15)
                        .cuboid(-32, -144, 7, 16, 15, 15)
                        .cuboid(-32, -144, 22, 16, 15, 15)
                        .cuboid(-16, -204, -37, 16, 15, 15)
                        .cuboid(-16, -204, -22, 16, 15, 15)
                        .cuboid(-16, -204, -7, 16, 15, 15)
                        .cuboid(-16, -204, 7, 16, 15, 15)
                        .cuboid(-16, -204, 22, 16, 15, 15)
                        .cuboid(-16, -189, -37, 16, 15, 15)
                        .cuboid(-16, -189, -22, 16, 15, 15)
                        .cuboid(-16, -189, -7, 16, 15, 15)
                        .cuboid(-16, -189, 7, 16, 15, 15)
                        .cuboid(-16, -189, 22, 16, 15, 15)
                        .cuboid(-16, -173, -37, 16, 15, 15)
                        .cuboid(-16, -173, -22, 16, 15, 15)
                        .cuboid(-16, -173, -7, 16, 15, 15)
                        .cuboid(-16, -173, 7, 16, 15, 15)
                        .cuboid(-16, -173, 22, 16, 15, 15)
                        .cuboid(-16, -159, -37, 16, 15, 15)
                        .cuboid(-16, -159, -22, 16, 15, 15)
                        .cuboid(-16, -159, -7, 16, 15, 15)
                        .cuboid(-16, -159, 7, 16, 15, 15)
                        .cuboid(-16, -159, 22, 16, 15, 15)
                        .cuboid(-16, -144, -37, 16, 15, 15)
                        .cuboid(-16, -144, -22, 16, 15, 15)
                        .cuboid(-16, -144, -7, 16, 15, 15)
                        .cuboid(-16, -144, 7, 16, 15, 15)
                        .cuboid(-16, -144, 22, 16, 15, 15)
                        .cuboid(0, -204, -37, 16, 15, 15)
                        .cuboid(0, -204, -22, 16, 15, 15)
                        .cuboid(0, -204, -7, 16, 15, 15)
                        .cuboid(0, -204, 7, 16, 15, 15)
                        .cuboid(0, -204, 22, 16, 15, 15)
                        .cuboid(0, -189, -37, 16, 15, 15)
                        .cuboid(0, -189, -22, 16, 15, 15)
                        .cuboid(0, -189, -7, 16, 15, 15)
                        .cuboid(0, -189, 7, 16, 15, 15)
                        .cuboid(0, -189, 22, 16, 15, 15)
                        .cuboid(0, -173, -37, 16, 15, 15)
                        .cuboid(0, -173, -22, 16, 15, 15)
                        .cuboid(0, -173, -7, 16, 15, 15)
                        .cuboid(0, -173, 7, 16, 15, 15)
                        .cuboid(0, -173, 22, 16, 15, 15)
                        .cuboid(0, -159, -37, 16, 15, 15)
                        .cuboid(0, -159, -22, 16, 15, 15)
                        .cuboid(0, -159, -7, 16, 15, 15)
                        .cuboid(0, -159, 7, 16, 15, 15)
                        .cuboid(0, -159, 22, 16, 15, 15)
                        .cuboid(0, -144, -37, 16, 15, 15)
                        .cuboid(0, -144, -22, 16, 15, 15)
                        .cuboid(0, -144, -7, 16, 15, 15)
                        .cuboid(0, -144, 7, 16, 15, 15)
                        .cuboid(0, -144, 22, 16, 15, 15)
                        .cuboid(16, -204, -37, 16, 15, 15)
                        .cuboid(16, -204, -22, 16, 15, 15)
                        .cuboid(16, -204, -7, 16, 15, 15)
                        .cuboid(16, -204, 7, 16, 15, 15)
                        .cuboid(16, -204, 22, 16, 15, 15)
                        .cuboid(16, -189, -37, 16, 15, 15)
                        .cuboid(16, -189, -22, 16, 15, 15)
                        .cuboid(16, -189, -7, 16, 15, 15)
                        .cuboid(16, -189, 7, 16, 15, 15)
                        .cuboid(16, -189, 22, 16, 15, 15)
                        .cuboid(16, -173, -37, 16, 15, 15)
                        .cuboid(16, -173, -22, 16, 15, 15)
                        .cuboid(16, -173, -7, 16, 15, 15)
                        .cuboid(16, -173, 7, 16, 15, 15)
                        .cuboid(16, -173, 22, 16, 15, 15)
                        .cuboid(16, -159, -37, 16, 15, 15)
                        .cuboid(16, -159, -22, 16, 15, 15)
                        .cuboid(16, -159, -7, 16, 15, 15)
                        .cuboid(16, -159, 7, 16, 15, 15)
                        .cuboid(16, -159, 22, 16, 15, 15)
                        .cuboid(16, -144, -37, 16, 15, 15)
                        .cuboid(16, -144, -22, 16, 15, 15)
                        .cuboid(16, -144, -7, 16, 15, 15)
                        .cuboid(16, -144, 7, 16, 15, 15)
                        .cuboid(16, -144, 22, 16, 15, 15)
                        .cuboid(-42, -122, -44, 17, 12, 18)
                        .cuboid(-42, -122, -26, 17, 12, 18)
                        .cuboid(-42, -122, -9, 17, 12, 18)
                        .cuboid(-42, -122, 9, 17, 12, 18)
                        .cuboid(-42, -122, 26, 17, 12, 18)
                        .cuboid(-42, -110, -44, 17, 12, 18)
                        .cuboid(-42, -110, -26, 17, 12, 18)
                        .cuboid(-42, -110, -9, 17, 12, 18)
                        .cuboid(-42, -110, 9, 17, 12, 18)
                        .cuboid(-42, -110, 26, 17, 12, 18)
                        .cuboid(-25, -122, -44, 17, 12, 18)
                        .cuboid(-25, -122, -26, 17, 12, 18)
                        .cuboid(-25, -122, -9, 17, 12, 18)
                        .cuboid(-25, -122, 9, 17, 12, 18)
                        .cuboid(-25, -122, 26, 17, 12, 18)
                        .cuboid(-25, -110, -44, 17, 12, 18)
                        .cuboid(-25, -110, -26, 17, 12, 18)
                        .cuboid(-25, -110, -9, 17, 12, 18)
                        .cuboid(-25, -110, 9, 17, 12, 18)
                        .cuboid(-25, -110, 26, 17, 12, 18)
                        .cuboid(-8, -122, -44, 17, 12, 18)
                        .cuboid(-8, -122, -26, 17, 12, 18)
                        .cuboid(-8, -122, -9, 17, 12, 18)
                        .cuboid(-8, -122, 9, 17, 12, 18)
                        .cuboid(-8, -122, 26, 17, 12, 18)
                        .cuboid(-8, -110, -44, 17, 12, 18)
                        .cuboid(-8, -110, -26, 17, 12, 18)
                        .cuboid(-8, -110, -9, 17, 12, 18)
                        .cuboid(-8, -110, 9, 17, 12, 18)
                        .cuboid(-8, -110, 26, 17, 12, 18)
                        .cuboid(8, -122, -44, 17, 12, 18)
                        .cuboid(8, -122, -26, 17, 12, 18)
                        .cuboid(8, -122, -9, 17, 12, 18)
                        .cuboid(8, -122, 9, 17, 12, 18)
                        .cuboid(8, -122, 26, 17, 12, 18)
                        .cuboid(8, -110, -44, 17, 12, 18)
                        .cuboid(8, -110, -26, 17, 12, 18)
                        .cuboid(8, -110, -9, 17, 12, 18)
                        .cuboid(8, -110, 9, 17, 12, 18)
                        .cuboid(8, -110, 26, 17, 12, 18)
                        .cuboid(25, -122, -44, 17, 12, 18)
                        .cuboid(25, -122, -26, 17, 12, 18)
                        .cuboid(25, -122, -9, 17, 12, 18)
                        .cuboid(25, -122, 9, 17, 12, 18)
                        .cuboid(25, -122, 26, 17, 12, 18)
                        .cuboid(25, -110, -44, 17, 12, 18)
                        .cuboid(25, -110, -26, 17, 12, 18)
                        .cuboid(25, -110, -9, 17, 12, 18)
                        .cuboid(25, -110, 9, 17, 12, 18)
                        .cuboid(25, -110, 26, 17, 12, 18)
                        .cuboid(-46, -159, -46, 15, 10, 15)
                        .cuboid(-46, -159, -31, 15, 10, 15)
                        .cuboid(-46, -159, -15, 15, 10, 15)
                        .cuboid(-46, -159, 0, 15, 10, 15)
                        .cuboid(-46, -159, 15, 15, 10, 15)
                        .cuboid(-46, -159, 31, 15, 10, 15)
                        .cuboid(-46, -149, -46, 15, 10, 15)
                        .cuboid(-46, -149, -31, 15, 10, 15)
                        .cuboid(-46, -149, -15, 15, 10, 15)
                        .cuboid(-46, -149, 0, 15, 10, 15)
                        .cuboid(-46, -149, 15, 15, 10, 15)
                        .cuboid(-46, -149, 31, 15, 10, 15)
                        .cuboid(-31, -159, -46, 15, 10, 15)
                        .cuboid(-31, -159, -31, 15, 10, 15)
                        .cuboid(-31, -159, -15, 15, 10, 15)
                        .cuboid(-31, -159, 0, 15, 10, 15)
                        .cuboid(-31, -159, 15, 15, 10, 15)
                        .cuboid(-31, -159, 31, 15, 10, 15)
                        .cuboid(-31, -149, -46, 15, 10, 15)
                        .cuboid(-31, -149, -31, 15, 10, 15)
                        .cuboid(-31, -149, -15, 15, 10, 15)
                        .cuboid(-31, -149, 0, 15, 10, 15)
                        .cuboid(-31, -149, 15, 15, 10, 15)
                        .cuboid(-31, -149, 31, 15, 10, 15)
                        .cuboid(-15, -159, -46, 15, 10, 15)
                        .cuboid(-15, -159, -31, 15, 10, 15)
                        .cuboid(-15, -159, -15, 15, 10, 15)
                        .cuboid(-15, -159, 0, 15, 10, 15)
                        .cuboid(-15, -159, 15, 15, 10, 15)
                        .cuboid(-15, -159, 31, 15, 10, 15)
                        .cuboid(-15, -149, -46, 15, 10, 15)
                        .cuboid(-15, -149, -31, 15, 10, 15)
                        .cuboid(-15, -149, -15, 15, 10, 15)
                        .cuboid(-15, -149, 0, 15, 10, 15)
                        .cuboid(-15, -149, 15, 15, 10, 15)
                        .cuboid(-15, -149, 31, 15, 10, 15)
                        .cuboid(0, -159, -46, 15, 10, 15)
                        .cuboid(0, -159, -31, 15, 10, 15)
                        .cuboid(0, -159, -15, 15, 10, 15)
                        .cuboid(0, -159, 0, 15, 10, 15)
                        .cuboid(0, -159, 15, 15, 10, 15)
                        .cuboid(0, -159, 31, 15, 10, 15)
                        .cuboid(0, -149, -46, 15, 10, 15)
                        .cuboid(0, -149, -31, 15, 10, 15)
                        .cuboid(0, -149, -15, 15, 10, 15)
                        .cuboid(0, -149, 0, 15, 10, 15)
                        .cuboid(0, -149, 15, 15, 10, 15)
                        .cuboid(0, -149, 31, 15, 10, 15)
                        .cuboid(15, -159, -46, 15, 10, 15)
                        .cuboid(15, -159, -31, 15, 10, 15)
                        .cuboid(15, -159, -15, 15, 10, 15)
                        .cuboid(15, -159, 0, 15, 10, 15)
                        .cuboid(15, -159, 15, 15, 10, 15)
                        .cuboid(15, -159, 31, 15, 10, 15)
                        .cuboid(15, -149, -46, 15, 10, 15)
                        .cuboid(15, -149, -31, 15, 10, 15)
                        .cuboid(15, -149, -15, 15, 10, 15)
                        .cuboid(15, -149, 0, 15, 10, 15)
                        .cuboid(15, -149, 15, 15, 10, 15)
                        .cuboid(15, -149, 31, 15, 10, 15)
                        .cuboid(31, -159, -46, 15, 10, 15)
                        .cuboid(31, -159, -31, 15, 10, 15)
                        .cuboid(31, -159, -15, 15, 10, 15)
                        .cuboid(31, -159, 0, 15, 10, 15)
                        .cuboid(31, -159, 15, 15, 10, 15)
                        .cuboid(31, -159, 31, 15, 10, 15)
                        .cuboid(31, -149, -46, 15, 10, 15)
                        .cuboid(31, -149, -31, 15, 10, 15)
                        .cuboid(31, -149, -15, 15, 10, 15)
                        .cuboid(31, -149, 0, 15, 10, 15)
                        .cuboid(31, -149, 15, 15, 10, 15)
                        .cuboid(31, -149, 31, 15, 10, 15),
                ModelTransform.of(0, 3, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-8, -4, -1, 16, 8, 2),
                ModelTransform.of(0, -169, -38, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-11, -11, -16, 22, 22, 26)
                        .cuboid(-6, -6, -60, 12, 12, 46)
                        .cuboid(-8, -8, -68, 16, 16, 9)
                        .cuboid(-5, -5, -70, 10, 10, 4),
                ModelTransform.of(-46, -194, -18, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-11, -11, -16, 22, 22, 26)
                        .cuboid(-6, -6, -60, 12, 12, 46)
                        .cuboid(-8, -8, -68, 16, 16, 9)
                        .cuboid(-5, -5, -70, 10, 10, 4),
                ModelTransform.of(46, -194, -18, 0.0F, 0.0F, 0.0F));

        ModelPartData arm0 = body.addChild("arm0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-8, -8, -14, 16, 16, 20)
                        .cuboid(-4, -4, -46, 8, 8, 34)
                        .cuboid(-3, -3, -49, 6, 6, 4),
                ModelTransform.of(0, -216, -30, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-12, -14, -14, 24, 20, 26)
                        .cuboid(-8, -35, -8, 16, 22, 16)
                        .cuboid(-10, -41, -10, 20, 8, 20)
                        .cuboid(-5, -39, -12, 10, 6, 4),
                ModelTransform.of(0, -204, 20, 0.0F, 0.0F, 0.0F));

        ModelPartData stack_r = body.addChild("stack_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -27, -6, 12, 29, 12),
                ModelTransform.of(28, -204, 10, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData taxiTripod() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 17, 5),
                ModelTransform.of(-18, -9, -8, -0.1047F, 0.0F, 0.3142F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 15, 5)
                        .cuboid(-3, 14, -3, 6, 3, 6),
                ModelTransform.of(0, 17, 0, -0.0367F, 0.0F, 0.1100F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 17, 5),
                ModelTransform.of(18, -9, -8, -0.1047F, 0.0F, -0.3142F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 15, 5)
                        .cuboid(-3, 14, -3, 6, 3, 6),
                ModelTransform.of(0, 17, 0, -0.0367F, 0.0F, -0.1100F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 17, 5),
                ModelTransform.of(0, -9, 16, 0.2793F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 15, 5)
                        .cuboid(-3, 14, -3, 6, 3, 6),
                ModelTransform.of(0, 17, 0, 0.0977F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-22, -40, -20, 11, 7, 10)
                        .cuboid(-22, -40, -10, 11, 7, 10)
                        .cuboid(-22, -40, 0, 11, 7, 10)
                        .cuboid(-22, -40, 10, 11, 7, 10)
                        .cuboid(-22, -33, -20, 11, 7, 10)
                        .cuboid(-22, -33, -10, 11, 7, 10)
                        .cuboid(-22, -33, 0, 11, 7, 10)
                        .cuboid(-22, -33, 10, 11, 7, 10)
                        .cuboid(-11, -40, -20, 11, 7, 10)
                        .cuboid(-11, -40, -10, 11, 7, 10)
                        .cuboid(-11, -40, 0, 11, 7, 10)
                        .cuboid(-11, -40, 10, 11, 7, 10)
                        .cuboid(-11, -33, -20, 11, 7, 10)
                        .cuboid(-11, -33, -10, 11, 7, 10)
                        .cuboid(-11, -33, 0, 11, 7, 10)
                        .cuboid(-11, -33, 10, 11, 7, 10)
                        .cuboid(0, -40, -20, 11, 7, 10)
                        .cuboid(0, -40, -10, 11, 7, 10)
                        .cuboid(0, -40, 0, 11, 7, 10)
                        .cuboid(0, -40, 10, 11, 7, 10)
                        .cuboid(0, -33, -20, 11, 7, 10)
                        .cuboid(0, -33, -10, 11, 7, 10)
                        .cuboid(0, -33, 0, 11, 7, 10)
                        .cuboid(0, -33, 10, 11, 7, 10)
                        .cuboid(11, -40, -20, 11, 7, 10)
                        .cuboid(11, -40, -10, 11, 7, 10)
                        .cuboid(11, -40, 0, 11, 7, 10)
                        .cuboid(11, -40, 10, 11, 7, 10)
                        .cuboid(11, -33, -20, 11, 7, 10)
                        .cuboid(11, -33, -10, 11, 7, 10)
                        .cuboid(11, -33, 0, 11, 7, 10)
                        .cuboid(11, -33, 10, 11, 7, 10)
                        .cuboid(-15, -47, -14, 10, 6, 9)
                        .cuboid(-15, -47, -5, 10, 6, 9)
                        .cuboid(-15, -47, 5, 10, 6, 9)
                        .cuboid(-5, -47, -14, 10, 6, 9)
                        .cuboid(-5, -47, -5, 10, 6, 9)
                        .cuboid(-5, -47, 5, 10, 6, 9)
                        .cuboid(5, -47, -14, 10, 6, 9)
                        .cuboid(5, -47, -5, 10, 6, 9)
                        .cuboid(5, -47, 5, 10, 6, 9),
                ModelTransform.of(0, 16, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-4, -2, -1, 8, 4, 2),
                ModelTransform.of(0, -42, -21, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -11, -18, 4, 11, 36)
                        .cuboid(-3, -13, -20, 6, 4, 4)
                        .cuboid(-2, -15, -21, 4, 3, 3),
                ModelTransform.of(-21, -40, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -11, -18, 4, 11, 36)
                        .cuboid(-3, -13, -20, 6, 4, 4)
                        .cuboid(-2, -15, -21, 4, 3, 3),
                ModelTransform.of(21, -40, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-10, -13, -4, 20, 13, 6),
                ModelTransform.of(0, -40, -14, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    /** The BBC machines differ only by the laser cannon, so one entry point serves both. */
    public static TexturedModelData bbc(boolean armed) {
        return armed ? bbcRed() : bbcWhite();
    }
}
