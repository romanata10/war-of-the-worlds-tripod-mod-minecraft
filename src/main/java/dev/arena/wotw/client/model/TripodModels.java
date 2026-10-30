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
                        .uv(0, 0).cuboid(-14, -104, -16, 14, 12, 16)
                        .cuboid(-14, -104, 0, 14, 12, 16)
                        .cuboid(0, -104, -16, 14, 12, 16)
                        .cuboid(0, -104, 0, 14, 12, 16)
                        .cuboid(-19, -92, -22, 19, 16, 22)
                        .cuboid(-19, -92, 0, 19, 16, 22)
                        .cuboid(0, -92, -22, 19, 16, 22)
                        .cuboid(0, -92, 0, 19, 16, 22)
                        .cuboid(-22, -76, -25, 22, 16, 17)
                        .cuboid(-22, -76, -8, 22, 16, 17)
                        .cuboid(-22, -76, 8, 22, 16, 17)
                        .cuboid(0, -76, -25, 22, 16, 17)
                        .cuboid(0, -76, -8, 22, 16, 17)
                        .cuboid(0, -76, 8, 22, 16, 17)
                        .cuboid(-17, -60, -20, 17, 12, 20)
                        .cuboid(-17, -60, 0, 17, 12, 20)
                        .cuboid(0, -60, -20, 17, 12, 20)
                        .cuboid(0, -60, 0, 17, 12, 20)
                        .cuboid(-12, -48, -14, 24, 6, 14)
                        .cuboid(-12, -48, 0, 24, 6, 14)
                        .cuboid(-23, -94, -28, 15, 7, 10)
                        .cuboid(-23, -94, -18, 15, 7, 10)
                        .cuboid(-8, -94, -28, 15, 7, 10)
                        .cuboid(-8, -94, -18, 15, 7, 10)
                        .cuboid(8, -94, -28, 15, 7, 10)
                        .cuboid(8, -94, -18, 15, 7, 10)
                        .cuboid(-11, -48, -13, 11, 8, 9)
                        .cuboid(-11, -48, -4, 11, 8, 9)
                        .cuboid(-11, -48, 4, 11, 8, 9)
                        .cuboid(0, -48, -13, 11, 8, 9)
                        .cuboid(0, -48, -4, 11, 8, 9)
                        .cuboid(0, -48, 4, 11, 8, 9),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_l = body.addChild("eye_l",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -4, -1, 11, 8, 2),
                ModelTransform.of(-11, -84, -24, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_r = body.addChild("eye_r",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -4, -1, 11, 8, 2),
                ModelTransform.of(11, -84, -24, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(-12, -44, -8, 0.1396F, -0.0524F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(0, 11, 0, 0.2793F, -0.1047F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(0, 11, 0, 0.2793F, -0.1047F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(12, -44, -8, 0.1396F, 0.0524F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(0, 11, 0, 0.2793F, 0.1047F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(0, 11, 0, 0.2793F, 0.1047F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(-9, -44, 9, 0.1920F, -0.0873F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(0, 11, 0, 0.3840F, -0.1745F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(0, 11, 0, 0.3840F, -0.1745F, 0.0F));

        ModelPartData tent3_1_body = body.addChild("tent3_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(9, -44, 9, 0.1920F, 0.0873F, 0.0F));

        ModelPartData tent3_2_body = tent3_1_body.addChild("tent3_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(0, 11, 0, 0.3840F, 0.1745F, 0.0F));

        ModelPartData tent3_3_body = tent3_2_body.addChild("tent3_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 11, 3),
                ModelTransform.of(0, 11, 0, 0.3840F, 0.1745F, 0.0F));

        ModelPartData leg_fl = root.addChild("leg_fl",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 36, 4),
                ModelTransform.of(-16, -46, -12, -0.1745F, 0.0F, -0.2443F));

        ModelPartData leg_fl_shin = leg_fl.addChild("leg_fl_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 33, 4)
                        .cuboid(-3, 32, -3, 5, 3, 5),
                ModelTransform.of(0, 36, 0, 0.1484F, 0.0F, 0.2077F));

        ModelPartData leg_fr = root.addChild("leg_fr",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 36, 4),
                ModelTransform.of(16, -46, -12, -0.1745F, 0.0F, 0.2443F));

        ModelPartData leg_fr_shin = leg_fr.addChild("leg_fr_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 33, 4)
                        .cuboid(-3, 32, -3, 5, 3, 5),
                ModelTransform.of(0, 36, 0, 0.1484F, 0.0F, -0.2077F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 37, 4),
                ModelTransform.of(0, -46, 18, 0.2094F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 34, 4)
                        .cuboid(-3, 33, -3, 5, 3, 5),
                ModelTransform.of(0, 37, 0, -0.1780F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData bbcWhite() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-10, -80, -11, 20, 10, 22)
                        .cuboid(-14, -70, -15, 14, 12, 15)
                        .cuboid(-14, -70, 0, 14, 12, 15)
                        .cuboid(0, -70, -15, 14, 12, 15)
                        .cuboid(0, -70, 0, 14, 12, 15)
                        .cuboid(-12, -58, -13, 24, 10, 13)
                        .cuboid(-12, -58, 0, 24, 10, 13)
                        .cuboid(-8, -48, -9, 16, 6, 18)
                        .cuboid(-5, -84, -5, 5, 5, 5)
                        .cuboid(-5, -84, 0, 5, 5, 5)
                        .cuboid(0, -84, -5, 5, 5, 5)
                        .cuboid(0, -84, 0, 5, 5, 5)
                        .cuboid(-9, -46, -10, 9, 6, 10)
                        .cuboid(-9, -46, 0, 9, 6, 10)
                        .cuboid(0, -46, -10, 9, 6, 10)
                        .cuboid(0, -46, 0, 9, 6, 10),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -5, -1, 9, 9, 2),
                ModelTransform.of(0, -66, -16, 0.0F, 0.0F, 0.0F));

        ModelPartData arm0 = body.addChild("arm0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 16, 3)
                        .cuboid(-1, 15, -1, 3, 6, 3),
                ModelTransform.of(-9, -44, -6, 0.1047F, 0.0F, -0.1414F));

        ModelPartData arm1 = body.addChild("arm1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 16, 3)
                        .cuboid(-1, 15, -1, 3, 6, 3),
                ModelTransform.of(9, -44, -6, 0.1047F, 0.0F, 0.1414F));

        ModelPartData arm2 = body.addChild("arm2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 16, 3)
                        .cuboid(-1, 15, -1, 3, 6, 3),
                ModelTransform.of(0, -44, 9, 0.1047F, 0.0F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 35, 3),
                ModelTransform.of(-10, -44, 0, 0.0F, 0.0F, -0.2094F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 34, 3)
                        .cuboid(-2, 33, -2, 4, 3, 4),
                ModelTransform.of(0, 35, 0, 0.0F, 0.0F, 0.1780F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 35, 3),
                ModelTransform.of(10, -44, 0, 0.0F, 0.0F, 0.2094F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 34, 3)
                        .cuboid(-2, 33, -2, 4, 3, 4),
                ModelTransform.of(0, 35, 0, 0.0F, 0.0F, -0.1780F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 35, 3),
                ModelTransform.of(0, -44, 10, 0.1571F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 34, 3)
                        .cuboid(-2, 33, -2, 4, 3, 4),
                ModelTransform.of(0, 35, 0, -0.1335F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData bbcRed() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-10, -80, -11, 20, 10, 22)
                        .cuboid(-14, -70, -15, 14, 12, 15)
                        .cuboid(-14, -70, 0, 14, 12, 15)
                        .cuboid(0, -70, -15, 14, 12, 15)
                        .cuboid(0, -70, 0, 14, 12, 15)
                        .cuboid(-12, -58, -13, 24, 10, 13)
                        .cuboid(-12, -58, 0, 24, 10, 13)
                        .cuboid(-8, -48, -9, 16, 6, 18)
                        .cuboid(-5, -84, -5, 5, 5, 5)
                        .cuboid(-5, -84, 0, 5, 5, 5)
                        .cuboid(0, -84, -5, 5, 5, 5)
                        .cuboid(0, -84, 0, 5, 5, 5)
                        .cuboid(-9, -46, -10, 9, 6, 10)
                        .cuboid(-9, -46, 0, 9, 6, 10)
                        .cuboid(0, -46, -10, 9, 6, 10)
                        .cuboid(0, -46, 0, 9, 6, 10),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-5, -5, -1, 9, 9, 2),
                ModelTransform.of(0, -66, -16, 0.0F, 0.0F, 0.0F));

        ModelPartData arm0 = body.addChild("arm0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 16, 3)
                        .cuboid(-1, 15, -1, 3, 6, 3),
                ModelTransform.of(-9, -44, -6, 0.1047F, 0.0F, -0.1414F));

        ModelPartData arm1 = body.addChild("arm1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 16, 3)
                        .cuboid(-1, 15, -1, 3, 6, 3),
                ModelTransform.of(9, -44, -6, 0.1047F, 0.0F, 0.1414F));

        ModelPartData arm2 = body.addChild("arm2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 3, 16, 3)
                        .cuboid(-1, 15, -1, 3, 6, 3),
                ModelTransform.of(0, -44, 9, 0.1047F, 0.0F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 35, 3),
                ModelTransform.of(-10, -44, 0, 0.0F, 0.0F, -0.2094F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 34, 3)
                        .cuboid(-2, 33, -2, 4, 3, 4),
                ModelTransform.of(0, 35, 0, 0.0F, 0.0F, 0.1780F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 35, 3),
                ModelTransform.of(10, -44, 0, 0.0F, 0.0F, 0.2094F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 34, 3)
                        .cuboid(-2, 33, -2, 4, 3, 4),
                ModelTransform.of(0, 35, 0, 0.0F, 0.0F, -0.1780F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 35, 3),
                ModelTransform.of(0, -44, 10, 0.1571F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 34, 3)
                        .cuboid(-2, 33, -2, 4, 3, 4),
                ModelTransform.of(0, 35, 0, -0.1335F, 0.0F, 0.0F));

        ModelPartData cannon = body.addChild("cannon",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, -2, -22, 5, 5, 24)
                        .cuboid(-3, -3, -26, 7, 7, 4)
                        .cuboid(-2, -2, -27, 4, 4, 1),
                ModelTransform.of(0, -58, -14, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData manta() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-17, -20, -31, 17, 18, 16)
                        .cuboid(-17, -20, -16, 17, 18, 16)
                        .cuboid(-17, -20, 0, 17, 18, 16)
                        .cuboid(-17, -20, 16, 17, 18, 16)
                        .cuboid(0, -20, -31, 17, 18, 16)
                        .cuboid(0, -20, -16, 17, 18, 16)
                        .cuboid(0, -20, 0, 17, 18, 16)
                        .cuboid(0, -20, 16, 17, 18, 16)
                        .cuboid(-12, -30, -23, 12, 10, 11)
                        .cuboid(-12, -30, -12, 12, 10, 11)
                        .cuboid(-12, -30, 0, 12, 10, 11)
                        .cuboid(0, -30, -23, 12, 10, 11)
                        .cuboid(0, -30, -12, 12, 10, 11)
                        .cuboid(0, -30, 0, 12, 10, 11),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData wing_l = body.addChild("wing_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-30, -2, -20, 30, 5, 34)
                        .cuboid(-46, -1, -12, 16, 4, 22)
                        .cuboid(-56, 0, -6, 10, 3, 12),
                ModelTransform.of(-16, -18, 0, 0.0F, 0.0F, 0.1047F));

        ModelPartData wing_r = body.addChild("wing_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(0, -2, -20, 30, 5, 34)
                        .cuboid(30, -1, -12, 16, 4, 22)
                        .cuboid(46, 0, -6, 10, 3, 12),
                ModelTransform.of(16, -18, 0, 0.0F, 0.0F, -0.1047F));

        ModelPartData tail = body.addChild("tail",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, -3, 0, 6, 6, 18),
                ModelTransform.of(0, -16, 30, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -4, -1, 12, 7, 2),
                ModelTransform.of(0, -26, -30, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 10, 2),
                ModelTransform.of(-12, -12, -14, 0.0F, 0.0F, -0.1047F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2)
                        .cuboid(-2, 8, -2, 3, 3, 3),
                ModelTransform.of(0, 10, 0, 0.0F, 0.0F, 0.0890F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 10, 2),
                ModelTransform.of(12, -12, -14, 0.0F, 0.0F, 0.1047F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2)
                        .cuboid(-2, 8, -2, 3, 3, 3),
                ModelTransform.of(0, 10, 0, 0.0F, 0.0F, -0.0890F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 10, 2),
                ModelTransform.of(0, -12, 12, 0.0F, 0.0F, 0.0F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 9, 2)
                        .cuboid(-2, 8, -2, 3, 3, 3),
                ModelTransform.of(0, 10, 0, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData book() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-13, -120, -14, 13, 14, 14)
                        .cuboid(-13, -120, 0, 13, 14, 14)
                        .cuboid(0, -120, -14, 13, 14, 14)
                        .cuboid(0, -120, 0, 13, 14, 14)
                        .cuboid(-18, -106, -20, 18, 18, 20)
                        .cuboid(-18, -106, 0, 18, 18, 20)
                        .cuboid(0, -106, -20, 18, 18, 20)
                        .cuboid(0, -106, 0, 18, 18, 20)
                        .cuboid(-17, -88, -19, 17, 16, 19)
                        .cuboid(-17, -88, 0, 17, 16, 19)
                        .cuboid(0, -88, -19, 17, 16, 19)
                        .cuboid(0, -88, 0, 17, 16, 19)
                        .cuboid(-13, -72, -15, 13, 10, 15)
                        .cuboid(-13, -72, 0, 13, 10, 15)
                        .cuboid(0, -72, -15, 13, 10, 15)
                        .cuboid(0, -72, 0, 13, 10, 15)
                        .cuboid(-9, -62, -10, 18, 6, 20)
                        .cuboid(-17, -112, -24, 11, 8, 8)
                        .cuboid(-17, -112, -16, 11, 8, 8)
                        .cuboid(-6, -112, -24, 11, 8, 8)
                        .cuboid(-6, -112, -16, 11, 8, 8)
                        .cuboid(6, -112, -24, 11, 8, 8)
                        .cuboid(6, -112, -16, 11, 8, 8)
                        .cuboid(-9, -60, -10, 9, 6, 10)
                        .cuboid(-9, -60, 0, 9, 6, 10)
                        .cuboid(0, -60, -10, 9, 6, 10)
                        .cuboid(0, -60, 0, 9, 6, 10),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-4, -3, -1, 8, 6, 2),
                ModelTransform.of(0, -100, -20, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(-11, -62, -9, 0.1222F, -0.0698F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2443F, -0.1396F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2443F, -0.1396F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(11, -62, -9, 0.1222F, 0.0698F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2443F, 0.1396F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2443F, 0.1396F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, -62, 11, 0.1222F, 0.0F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2443F, 0.0F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2443F, 0.0F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 44, 4),
                ModelTransform.of(-14, -62, -10, -0.1920F, 0.0F, -0.2618F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 42, 4)
                        .cuboid(-3, 41, -3, 5, 3, 5),
                ModelTransform.of(0, 44, 0, 0.1632F, 0.0F, 0.2225F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 44, 4),
                ModelTransform.of(14, -62, -10, -0.1920F, 0.0F, 0.2618F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 42, 4)
                        .cuboid(-3, 41, -3, 5, 3, 5),
                ModelTransform.of(0, 44, 0, 0.1632F, 0.0F, -0.2225F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 45, 4),
                ModelTransform.of(0, -62, 14, 0.2269F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 43, 4)
                        .cuboid(-3, 42, -3, 5, 3, 5),
                ModelTransform.of(0, 45, 0, -0.1929F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData asylum() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-25, -43, -28, 12, 10, 14)
                        .cuboid(-25, -43, -14, 12, 10, 14)
                        .cuboid(-25, -43, 0, 12, 10, 14)
                        .cuboid(-25, -43, 14, 12, 10, 14)
                        .cuboid(-12, -43, -28, 12, 10, 14)
                        .cuboid(-12, -43, -14, 12, 10, 14)
                        .cuboid(-12, -43, 0, 12, 10, 14)
                        .cuboid(-12, -43, 14, 12, 10, 14)
                        .cuboid(0, -43, -28, 12, 10, 14)
                        .cuboid(0, -43, -14, 12, 10, 14)
                        .cuboid(0, -43, 0, 12, 10, 14)
                        .cuboid(0, -43, 14, 12, 10, 14)
                        .cuboid(12, -43, -28, 12, 10, 14)
                        .cuboid(12, -43, -14, 12, 10, 14)
                        .cuboid(12, -43, 0, 12, 10, 14)
                        .cuboid(12, -43, 14, 12, 10, 14)
                        .cuboid(-22, -33, -25, 15, 8, 12)
                        .cuboid(-22, -33, -12, 15, 8, 12)
                        .cuboid(-22, -33, 0, 15, 8, 12)
                        .cuboid(-22, -33, 12, 15, 8, 12)
                        .cuboid(-7, -33, -25, 15, 8, 12)
                        .cuboid(-7, -33, -12, 15, 8, 12)
                        .cuboid(-7, -33, 0, 15, 8, 12)
                        .cuboid(-7, -33, 12, 15, 8, 12)
                        .cuboid(7, -33, -25, 15, 8, 12)
                        .cuboid(7, -33, -12, 15, 8, 12)
                        .cuboid(7, -33, 0, 15, 8, 12)
                        .cuboid(7, -33, 12, 15, 8, 12)
                        .cuboid(-17, -25, -20, 11, 5, 13)
                        .cuboid(-17, -25, -7, 11, 5, 13)
                        .cuboid(-17, -25, 7, 11, 5, 13)
                        .cuboid(-6, -25, -20, 11, 5, 13)
                        .cuboid(-6, -25, -7, 11, 5, 13)
                        .cuboid(-6, -25, 7, 11, 5, 13)
                        .cuboid(6, -25, -20, 11, 5, 13)
                        .cuboid(6, -25, -7, 11, 5, 13)
                        .cuboid(6, -25, 7, 11, 5, 13)
                        .cuboid(-20, -43, -22, 8, 2, 8)
                        .cuboid(-19, -41, -21, 6, 2, 6)
                        .cuboid(-18, -39, -20, 4, 2, 4)
                        .cuboid(12, -43, -22, 8, 2, 8)
                        .cuboid(13, -41, -21, 6, 2, 6)
                        .cuboid(14, -39, -20, 4, 2, 4)
                        .cuboid(-22, -43, 0, 8, 2, 8)
                        .cuboid(-21, -41, 1, 6, 2, 6)
                        .cuboid(-20, -39, 2, 4, 2, 4)
                        .cuboid(14, -43, 0, 8, 2, 8)
                        .cuboid(15, -41, 1, 6, 2, 6)
                        .cuboid(16, -39, 2, 4, 2, 4)
                        .cuboid(-18, -43, 18, 8, 2, 8)
                        .cuboid(-17, -41, 19, 6, 2, 6)
                        .cuboid(-16, -39, 20, 4, 2, 4)
                        .cuboid(10, -43, 18, 8, 2, 8)
                        .cuboid(11, -41, 19, 6, 2, 6)
                        .cuboid(12, -39, 20, 4, 2, 4)
                        .cuboid(-13, -41, -31, 9, 6, 10)
                        .cuboid(-4, -41, -31, 9, 6, 10)
                        .cuboid(4, -41, -31, 9, 6, 10),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-7, -5, -1, 13, 9, 2),
                ModelTransform.of(0, -37, -32, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 25, 3),
                ModelTransform.of(-20, -24, -20, -0.1920F, 0.0F, -0.2618F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 24, 3)
                        .cuboid(-2, 23, -2, 4, 3, 4),
                ModelTransform.of(0, 25, 0, 0.1632F, 0.0F, 0.2225F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 25, 3),
                ModelTransform.of(20, -24, -20, -0.1920F, 0.0F, 0.2618F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 24, 3)
                        .cuboid(-2, 23, -2, 4, 3, 4),
                ModelTransform.of(0, 25, 0, 0.1632F, 0.0F, -0.2225F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 25, 3),
                ModelTransform.of(-22, -24, 0, 0.0F, 0.0F, -0.2880F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 24, 3)
                        .cuboid(-2, 23, -2, 4, 3, 4),
                ModelTransform.of(0, 25, 0, 0.0F, 0.0F, 0.2448F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 25, 3),
                ModelTransform.of(22, -24, 0, 0.0F, 0.0F, 0.2880F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 24, 3)
                        .cuboid(-2, 23, -2, 4, 3, 4),
                ModelTransform.of(0, 25, 0, 0.0F, 0.0F, -0.2448F));

        ModelPartData leg4 = root.addChild("leg4",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 25, 3),
                ModelTransform.of(-18, -24, 20, 0.1920F, 0.0F, -0.2356F));

        ModelPartData leg4_shin = leg4.addChild("leg4_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 24, 3)
                        .cuboid(-2, 23, -2, 4, 3, 4),
                ModelTransform.of(0, 25, 0, -0.1632F, 0.0F, 0.2003F));

        ModelPartData leg5 = root.addChild("leg5",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 25, 3),
                ModelTransform.of(18, -24, 20, 0.1920F, 0.0F, 0.2356F));

        ModelPartData leg5_shin = leg5.addChild("leg5_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 24, 3)
                        .cuboid(-2, 23, -2, 4, 3, 4),
                ModelTransform.of(0, 25, 0, -0.1632F, 0.0F, -0.2003F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData goliath() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-15, -136, -17, 15, 14, 17)
                        .cuboid(-15, -136, 0, 15, 14, 17)
                        .cuboid(0, -136, -17, 15, 14, 17)
                        .cuboid(0, -136, 0, 15, 14, 17)
                        .cuboid(-23, -122, -25, 23, 18, 17)
                        .cuboid(-23, -122, -8, 23, 18, 17)
                        .cuboid(-23, -122, 8, 23, 18, 17)
                        .cuboid(0, -122, -25, 23, 18, 17)
                        .cuboid(0, -122, -8, 23, 18, 17)
                        .cuboid(0, -122, 8, 23, 18, 17)
                        .cuboid(-22, -104, -24, 22, 16, 24)
                        .cuboid(-22, -104, 0, 22, 16, 24)
                        .cuboid(0, -104, -24, 22, 16, 24)
                        .cuboid(0, -104, 0, 22, 16, 24)
                        .cuboid(-16, -88, -18, 16, 10, 18)
                        .cuboid(-16, -88, 0, 16, 10, 18)
                        .cuboid(0, -88, -18, 16, 10, 18)
                        .cuboid(0, -88, 0, 16, 10, 18)
                        .cuboid(-11, -78, -13, 22, 6, 13)
                        .cuboid(-11, -78, 0, 22, 6, 13)
                        .cuboid(-25, -122, -31, 12, 8, 9)
                        .cuboid(-25, -122, -22, 12, 8, 9)
                        .cuboid(-12, -122, -31, 12, 8, 9)
                        .cuboid(-12, -122, -22, 12, 8, 9)
                        .cuboid(0, -122, -31, 12, 8, 9)
                        .cuboid(0, -122, -22, 12, 8, 9)
                        .cuboid(12, -122, -31, 12, 8, 9)
                        .cuboid(12, -122, -22, 12, 8, 9)
                        .cuboid(-12, -82, -14, 12, 8, 9)
                        .cuboid(-12, -82, -5, 12, 8, 9)
                        .cuboid(-12, -82, 5, 12, 8, 9)
                        .cuboid(0, -82, -14, 12, 8, 9)
                        .cuboid(0, -82, -5, 12, 8, 9)
                        .cuboid(0, -82, 5, 12, 8, 9),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_l = body.addChild("eye_l",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -4, -1, 12, 8, 2),
                ModelTransform.of(-12, -112, -26, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_r = body.addChild("eye_r",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -4, -1, 12, 8, 2),
                ModelTransform.of(12, -112, -26, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(-16, -78, -12, 0.1571F, -0.1047F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, -0.2094F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, -0.2094F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(16, -78, -12, 0.1571F, 0.1047F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, 0.2094F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, 0.2094F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(-20, -78, 4, 0.1571F, -0.1571F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, -0.3142F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, -0.3142F, 0.0F));

        ModelPartData tent3_1_body = body.addChild("tent3_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(20, -78, 4, 0.1571F, 0.1571F, 0.0F));

        ModelPartData tent3_2_body = tent3_1_body.addChild("tent3_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, 0.3142F, 0.0F));

        ModelPartData tent3_3_body = tent3_2_body.addChild("tent3_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, 0.3142F, 0.0F));

        ModelPartData tent4_1_body = body.addChild("tent4_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(-10, -78, 16, 0.1571F, -0.0698F, 0.0F));

        ModelPartData tent4_2_body = tent4_1_body.addChild("tent4_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, -0.1396F, 0.0F));

        ModelPartData tent4_3_body = tent4_2_body.addChild("tent4_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, -0.1396F, 0.0F));

        ModelPartData tent5_1_body = body.addChild("tent5_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(10, -78, 16, 0.1571F, 0.0698F, 0.0F));

        ModelPartData tent5_2_body = tent5_1_body.addChild("tent5_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, 0.1396F, 0.0F));

        ModelPartData tent5_3_body = tent5_2_body.addChild("tent5_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 12, 3),
                ModelTransform.of(0, 12, 0, 0.3142F, 0.1396F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 52, 5),
                ModelTransform.of(-20, -80, -12, -0.1745F, 0.0F, -0.2269F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 50, 5)
                        .cuboid(-3, 49, -3, 6, 3, 6),
                ModelTransform.of(0, 52, 0, 0.1484F, 0.0F, 0.1929F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 52, 5),
                ModelTransform.of(20, -80, -12, -0.1745F, 0.0F, 0.2269F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 50, 5)
                        .cuboid(-3, 49, -3, 6, 3, 6),
                ModelTransform.of(0, 52, 0, 0.1484F, 0.0F, -0.1929F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 53, 5),
                ModelTransform.of(0, -80, 22, 0.2094F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3, 0, -3, 5, 51, 5)
                        .cuboid(-3, 50, -3, 6, 3, 6),
                ModelTransform.of(0, 53, 0, -0.1780F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData heron() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-13, -84, -15, 13, 8, 10)
                        .cuboid(-13, -84, -5, 13, 8, 10)
                        .cuboid(-13, -84, 5, 13, 8, 10)
                        .cuboid(-13, -76, -15, 13, 8, 10)
                        .cuboid(-13, -76, -5, 13, 8, 10)
                        .cuboid(-13, -76, 5, 13, 8, 10)
                        .cuboid(0, -84, -15, 13, 8, 10)
                        .cuboid(0, -84, -5, 13, 8, 10)
                        .cuboid(0, -84, 5, 13, 8, 10)
                        .cuboid(0, -76, -15, 13, 8, 10)
                        .cuboid(0, -76, -5, 13, 8, 10)
                        .cuboid(0, -76, 5, 13, 8, 10)
                        .cuboid(-9, -70, -11, 9, 10, 11)
                        .cuboid(-9, -70, 0, 9, 10, 11)
                        .cuboid(0, -70, -11, 9, 10, 11)
                        .cuboid(0, -70, 0, 9, 10, 11),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData neck = body.addChild("neck",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, -22, -4, 8, 24, 8)
                        .cuboid(-3, -44, -5, 6, 24, 6),
                ModelTransform.of(0, -84, -4, -0.1396F, 0.0F, 0.0F));

        ModelPartData head = neck.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-10, -18, -16, 10, 9, 12)
                        .cuboid(-10, -18, -4, 10, 9, 12)
                        .cuboid(-10, -9, -16, 10, 9, 12)
                        .cuboid(-10, -9, -4, 10, 9, 12)
                        .cuboid(0, -18, -16, 10, 9, 12)
                        .cuboid(0, -18, -4, 10, 9, 12)
                        .cuboid(0, -9, -16, 10, 9, 12)
                        .cuboid(0, -9, -4, 10, 9, 12),
                ModelTransform.of(0, -46, -5, 0.1047F, 0.0F, 0.0F));

        ModelPartData eye = head.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -3, -1, 12, 5, 2),
                ModelTransform.of(0, -10, -16, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(-8, -68, -2, 0.1396F, -0.1222F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, -0.2443F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, -0.2443F, 0.0F));

        ModelPartData tent0_4_body = tent0_3_body.addChild("tent0_4_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, -0.2443F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(8, -68, -2, 0.1396F, 0.1222F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, 0.2443F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, 0.2443F, 0.0F));

        ModelPartData tent1_4_body = tent1_3_body.addChild("tent1_4_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, 0.2443F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(-6, -68, 8, 0.1396F, -0.0698F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, -0.1396F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, -0.1396F, 0.0F));

        ModelPartData tent2_4_body = tent2_3_body.addChild("tent2_4_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, -0.1396F, 0.0F));

        ModelPartData tent3_1_body = body.addChild("tent3_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(6, -68, 8, 0.1396F, 0.0698F, 0.0F));

        ModelPartData tent3_2_body = tent3_1_body.addChild("tent3_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, 0.1396F, 0.0F));

        ModelPartData tent3_3_body = tent3_2_body.addChild("tent3_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, 0.1396F, 0.0F));

        ModelPartData tent3_4_body = tent3_3_body.addChild("tent3_4_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 10, 3),
                ModelTransform.of(0, 10, 0, 0.2793F, 0.1396F, 0.0F));

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3),
                ModelTransform.of(-8, -70, -4, -0.0873F, 0.0F, -0.1396F));

        ModelPartData leg_l_shin = leg_l.addChild("leg_l_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 45, 3)
                        .cuboid(-2, 44, -2, 4, 3, 4),
                ModelTransform.of(0, 46, 0, 0.0742F, 0.0F, 0.1187F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3),
                ModelTransform.of(8, -70, -4, -0.0873F, 0.0F, 0.1396F));

        ModelPartData leg_r_shin = leg_r.addChild("leg_r_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 45, 3)
                        .cuboid(-2, 44, -2, 4, 3, 4),
                ModelTransform.of(0, 46, 0, 0.0742F, 0.0F, -0.1187F));

        ModelPartData leg_b = root.addChild("leg_b",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 47, 3),
                ModelTransform.of(0, -70, 8, 0.1222F, 0.0F, 0.0F));

        ModelPartData leg_b_shin = leg_b.addChild("leg_b_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 3, 46, 3)
                        .cuboid(-2, 45, -2, 4, 3, 4),
                ModelTransform.of(0, 47, 0, -0.1038F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData spider() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-15, -26, -19, 15, 7, 19)
                        .cuboid(-15, -26, 0, 15, 7, 19)
                        .cuboid(0, -26, -19, 15, 7, 19)
                        .cuboid(0, -26, 0, 15, 7, 19)
                        .cuboid(-20, -19, -24, 20, 7, 24)
                        .cuboid(-20, -19, 0, 20, 7, 24)
                        .cuboid(0, -19, -24, 20, 7, 24)
                        .cuboid(0, -19, 0, 20, 7, 24)
                        .cuboid(-17, -12, -21, 17, 5, 21)
                        .cuboid(-17, -12, 0, 17, 5, 21)
                        .cuboid(0, -12, -21, 17, 5, 21)
                        .cuboid(0, -12, 0, 17, 5, 21),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-11, -6, -16, 7, 6, 8)
                        .cuboid(-11, -6, -8, 7, 6, 8)
                        .cuboid(-11, 0, -16, 7, 6, 8)
                        .cuboid(-11, 0, -8, 7, 6, 8)
                        .cuboid(-4, -6, -16, 7, 6, 8)
                        .cuboid(-4, -6, -8, 7, 6, 8)
                        .cuboid(-4, 0, -16, 7, 6, 8)
                        .cuboid(-4, 0, -8, 7, 6, 8)
                        .cuboid(4, -6, -16, 7, 6, 8)
                        .cuboid(4, -6, -8, 7, 6, 8)
                        .cuboid(4, 0, -16, 7, 6, 8)
                        .cuboid(4, 0, -8, 7, 6, 8),
                ModelTransform.of(0, -24, -24, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = head.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-6, -3, -1, 11, 6, 2),
                ModelTransform.of(0, -4, -17, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 16, 2),
                ModelTransform.of(-18, -10, -18, -0.2199F, 0.0F, -0.3456F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2)
                        .cuboid(-2, 14, -2, 3, 3, 3),
                ModelTransform.of(0, 16, 0, 0.1869F, 0.0F, 0.2937F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 16, 2),
                ModelTransform.of(18, -10, -18, -0.2199F, 0.0F, 0.3456F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2)
                        .cuboid(-2, 14, -2, 3, 3, 3),
                ModelTransform.of(0, 16, 0, 0.1869F, 0.0F, -0.2937F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 16, 2),
                ModelTransform.of(-20, -10, -6, -0.0733F, 0.0F, -0.3840F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2)
                        .cuboid(-2, 14, -2, 3, 3, 3),
                ModelTransform.of(0, 16, 0, 0.0623F, 0.0F, 0.3264F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 16, 2),
                ModelTransform.of(20, -10, -6, -0.0733F, 0.0F, 0.3840F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2)
                        .cuboid(-2, 14, -2, 3, 3, 3),
                ModelTransform.of(0, 16, 0, 0.0623F, 0.0F, -0.3264F));

        ModelPartData leg4 = root.addChild("leg4",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 16, 2),
                ModelTransform.of(-20, -10, 6, 0.0733F, 0.0F, -0.3840F));

        ModelPartData leg4_shin = leg4.addChild("leg4_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2)
                        .cuboid(-2, 14, -2, 3, 3, 3),
                ModelTransform.of(0, 16, 0, -0.0623F, 0.0F, 0.3264F));

        ModelPartData leg5 = root.addChild("leg5",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 16, 2),
                ModelTransform.of(20, -10, 6, 0.0733F, 0.0F, 0.3840F));

        ModelPartData leg5_shin = leg5.addChild("leg5_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2)
                        .cuboid(-2, 14, -2, 3, 3, 3),
                ModelTransform.of(0, 16, 0, -0.0623F, 0.0F, -0.3264F));

        ModelPartData leg6 = root.addChild("leg6",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 16, 2),
                ModelTransform.of(-18, -10, 18, 0.2199F, 0.0F, -0.3456F));

        ModelPartData leg6_shin = leg6.addChild("leg6_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2)
                        .cuboid(-2, 14, -2, 3, 3, 3),
                ModelTransform.of(0, 16, 0, -0.1869F, 0.0F, 0.2937F));

        ModelPartData leg7 = root.addChild("leg7",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 16, 2),
                ModelTransform.of(18, -10, 18, 0.2199F, 0.0F, 0.3456F));

        ModelPartData leg7_shin = leg7.addChild("leg7_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 15, 2)
                        .cuboid(-2, 14, -2, 3, 3, 3),
                ModelTransform.of(0, 16, 0, -0.1869F, 0.0F, -0.2937F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData louse() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-8, -8, -10, 16, 6, 20)
                        .cuboid(-10, -2, -12, 20, 6, 24)
                        .cuboid(-8, 4, -10, 16, 4, 20)
                        .cuboid(-10, -8, -11, 4, 1, 4)
                        .cuboid(-9, -7, -10, 2, 1, 2)
                        .cuboid(-8, -6, -9, 1, 1, 1)
                        .cuboid(6, -8, -11, 4, 1, 4)
                        .cuboid(7, -7, -10, 2, 1, 2)
                        .cuboid(8, -6, -9, 1, 1, 1)
                        .cuboid(-10, -8, 7, 4, 1, 4)
                        .cuboid(-9, -7, 8, 2, 1, 2)
                        .cuboid(-8, -6, 9, 1, 1, 1)
                        .cuboid(6, -8, 7, 4, 1, 4)
                        .cuboid(7, -7, 8, 2, 1, 2)
                        .cuboid(8, -6, 9, 1, 1, 1),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-4, -2, -1, 7, 4, 2),
                ModelTransform.of(0, -4, -12, 0.0F, 0.0F, 0.0F));

        ModelPartData leg0 = root.addChild("leg0",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                ModelTransform.of(-9, 4, -8, -0.1117F, 0.0F, -0.1885F));

        ModelPartData leg0_shin = leg0.addChild("leg0_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 6, 2)
                        .cuboid(-1, 5, -1, 2, 3, 2),
                ModelTransform.of(0, 7, 0, 0.0949F, 0.0F, 0.1602F));

        ModelPartData leg1 = root.addChild("leg1",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                ModelTransform.of(9, 4, -8, -0.1117F, 0.0F, 0.1885F));

        ModelPartData leg1_shin = leg1.addChild("leg1_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 6, 2)
                        .cuboid(-1, 5, -1, 2, 3, 2),
                ModelTransform.of(0, 7, 0, 0.0949F, 0.0F, -0.1602F));

        ModelPartData leg2 = root.addChild("leg2",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                ModelTransform.of(-9, 4, 8, 0.1117F, 0.0F, -0.1885F));

        ModelPartData leg2_shin = leg2.addChild("leg2_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 6, 2)
                        .cuboid(-1, 5, -1, 2, 3, 2),
                ModelTransform.of(0, 7, 0, -0.0949F, 0.0F, 0.1602F));

        ModelPartData leg3 = root.addChild("leg3",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                ModelTransform.of(9, 4, 8, 0.1117F, 0.0F, 0.1885F));

        ModelPartData leg3_shin = leg3.addChild("leg3_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 6, 2)
                        .cuboid(-1, 5, -1, 2, 3, 2),
                ModelTransform.of(0, 7, 0, -0.0949F, 0.0F, -0.1602F));

        ModelPartData leg4 = root.addChild("leg4",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                ModelTransform.of(0, 4, -9, -0.1257F, 0.0F, 0.0F));

        ModelPartData leg4_shin = leg4.addChild("leg4_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 6, 2)
                        .cuboid(-1, 5, -1, 2, 3, 2),
                ModelTransform.of(0, 7, 0, 0.1068F, 0.0F, 0.0F));

        ModelPartData leg5 = root.addChild("leg5",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                ModelTransform.of(0, 4, 9, 0.1257F, 0.0F, 0.0F));

        ModelPartData leg5_shin = leg5.addChild("leg5_shin",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 6, 2)
                        .cuboid(-1, 5, -1, 2, 3, 2),
                ModelTransform.of(0, 7, 0, -0.1068F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    /** The BBC machines differ only by the laser cannon, so one entry point serves both. */
    public static TexturedModelData bbc(boolean armed) {
        return armed ? bbcRed() : bbcWhite();
    }
}
