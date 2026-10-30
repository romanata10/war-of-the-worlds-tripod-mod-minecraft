package dev.arena.wotw.client.model;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

/**
 * The Martians themselves: the leathery, tentacled creatures from the novel.
 *
 * <p><b>Generated file.</b> The geometry lives in `tools/generate_models.py` (see
 * `tools/model_lib.py`); edit that and run `python3 tools/generate_models.py`.
 * Model pixels: 16 per block, +y downwards, feet at y = 24, the machine looks towards
 * (0, +y down). Lenses sample the glowing patch of the texture at (96, 96).</p>
 */
public final class MartianModels {


    public static TexturedModelData martian() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -8, -6, 6, 2, 6)
                        .cuboid(-6, -8, 0, 6, 2, 6)
                        .cuboid(0, -8, -6, 6, 2, 6)
                        .cuboid(0, -8, 0, 6, 2, 6)
                        .cuboid(-7, -6, -8, 5, 2, 5)
                        .cuboid(-7, -6, -2, 5, 2, 5)
                        .cuboid(-7, -6, 2, 5, 2, 5)
                        .cuboid(-2, -6, -8, 5, 2, 5)
                        .cuboid(-2, -6, -2, 5, 2, 5)
                        .cuboid(-2, -6, 2, 5, 2, 5)
                        .cuboid(2, -6, -8, 5, 2, 5)
                        .cuboid(2, -6, -2, 5, 2, 5)
                        .cuboid(2, -6, 2, 5, 2, 5)
                        .cuboid(-8, -4, -9, 6, 2, 6)
                        .cuboid(-8, -4, -3, 6, 2, 6)
                        .cuboid(-8, -4, 3, 6, 2, 6)
                        .cuboid(-3, -4, -9, 6, 2, 6)
                        .cuboid(-3, -4, -3, 6, 2, 6)
                        .cuboid(-3, -4, 3, 6, 2, 6)
                        .cuboid(3, -4, -9, 6, 2, 6)
                        .cuboid(3, -4, -3, 6, 2, 6)
                        .cuboid(3, -4, 3, 6, 2, 6)
                        .cuboid(-10, -2, -10, 5, 2, 5)
                        .cuboid(-10, -2, -5, 5, 2, 5)
                        .cuboid(-10, -2, 0, 5, 2, 5)
                        .cuboid(-10, -2, 5, 5, 2, 5)
                        .cuboid(-5, -2, -10, 5, 2, 5)
                        .cuboid(-5, -2, -5, 5, 2, 5)
                        .cuboid(-5, -2, 0, 5, 2, 5)
                        .cuboid(-5, -2, 5, 5, 2, 5)
                        .cuboid(0, -2, -10, 5, 2, 5)
                        .cuboid(0, -2, -5, 5, 2, 5)
                        .cuboid(0, -2, 0, 5, 2, 5)
                        .cuboid(0, -2, 5, 5, 2, 5)
                        .cuboid(5, -2, -10, 5, 2, 5)
                        .cuboid(5, -2, -5, 5, 2, 5)
                        .cuboid(5, -2, 0, 5, 2, 5)
                        .cuboid(5, -2, 5, 5, 2, 5)
                        .cuboid(-10, 0, -11, 5, 2, 6)
                        .cuboid(-10, 0, -6, 5, 2, 6)
                        .cuboid(-10, 0, 0, 5, 2, 6)
                        .cuboid(-10, 0, 6, 5, 2, 6)
                        .cuboid(-5, 0, -11, 5, 2, 6)
                        .cuboid(-5, 0, -6, 5, 2, 6)
                        .cuboid(-5, 0, 0, 5, 2, 6)
                        .cuboid(-5, 0, 6, 5, 2, 6)
                        .cuboid(0, 0, -11, 5, 2, 6)
                        .cuboid(0, 0, -6, 5, 2, 6)
                        .cuboid(0, 0, 0, 5, 2, 6)
                        .cuboid(0, 0, 6, 5, 2, 6)
                        .cuboid(5, 0, -11, 5, 2, 6)
                        .cuboid(5, 0, -6, 5, 2, 6)
                        .cuboid(5, 0, 0, 5, 2, 6)
                        .cuboid(5, 0, 6, 5, 2, 6)
                        .cuboid(-10, 2, -11, 5, 2, 6)
                        .cuboid(-10, 2, -6, 5, 2, 6)
                        .cuboid(-10, 2, 0, 5, 2, 6)
                        .cuboid(-10, 2, 6, 5, 2, 6)
                        .cuboid(-5, 2, -11, 5, 2, 6)
                        .cuboid(-5, 2, -6, 5, 2, 6)
                        .cuboid(-5, 2, 0, 5, 2, 6)
                        .cuboid(-5, 2, 6, 5, 2, 6)
                        .cuboid(0, 2, -11, 5, 2, 6)
                        .cuboid(0, 2, -6, 5, 2, 6)
                        .cuboid(0, 2, 0, 5, 2, 6)
                        .cuboid(0, 2, 6, 5, 2, 6)
                        .cuboid(5, 2, -11, 5, 2, 6)
                        .cuboid(5, 2, -6, 5, 2, 6)
                        .cuboid(5, 2, 0, 5, 2, 6)
                        .cuboid(5, 2, 6, 5, 2, 6)
                        .cuboid(-10, 4, -10, 5, 2, 5)
                        .cuboid(-10, 4, -5, 5, 2, 5)
                        .cuboid(-10, 4, 0, 5, 2, 5)
                        .cuboid(-10, 4, 5, 5, 2, 5)
                        .cuboid(-5, 4, -10, 5, 2, 5)
                        .cuboid(-5, 4, -5, 5, 2, 5)
                        .cuboid(-5, 4, 0, 5, 2, 5)
                        .cuboid(-5, 4, 5, 5, 2, 5)
                        .cuboid(0, 4, -10, 5, 2, 5)
                        .cuboid(0, 4, -5, 5, 2, 5)
                        .cuboid(0, 4, 0, 5, 2, 5)
                        .cuboid(0, 4, 5, 5, 2, 5)
                        .cuboid(5, 4, -10, 5, 2, 5)
                        .cuboid(5, 4, -5, 5, 2, 5)
                        .cuboid(5, 4, 0, 5, 2, 5)
                        .cuboid(5, 4, 5, 5, 2, 5)
                        .cuboid(-8, 6, -9, 5, 2, 6)
                        .cuboid(-8, 6, -3, 5, 2, 6)
                        .cuboid(-8, 6, 3, 5, 2, 6)
                        .cuboid(-3, 6, -9, 5, 2, 6)
                        .cuboid(-3, 6, -3, 5, 2, 6)
                        .cuboid(-3, 6, 3, 5, 2, 6)
                        .cuboid(3, 6, -9, 5, 2, 6)
                        .cuboid(3, 6, -3, 5, 2, 6)
                        .cuboid(3, 6, 3, 5, 2, 6)
                        .cuboid(-6, 8, -7, 4, 2, 5)
                        .cuboid(-6, 8, -2, 4, 2, 5)
                        .cuboid(-6, 8, 2, 4, 2, 5)
                        .cuboid(-2, 8, -7, 4, 2, 5)
                        .cuboid(-2, 8, -2, 4, 2, 5)
                        .cuboid(-2, 8, 2, 4, 2, 5)
                        .cuboid(2, 8, -7, 4, 2, 5)
                        .cuboid(2, 8, -2, 4, 2, 5)
                        .cuboid(2, 8, 2, 4, 2, 5),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData beak = body.addChild("beak",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, -4, -7, 8, 8, 6)
                        .cuboid(-3, -3, -10, 5, 5, 5),
                ModelTransform.of(0, 3, -10, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_l = body.addChild("eye_l",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-2, -1, -1, 4, 2, 2),
                ModelTransform.of(-5, -4, -9, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_r = body.addChild("eye_r",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-2, -1, -1, 4, 2, 2),
                ModelTransform.of(5, -4, -9, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 12, -11, 0.6632F, -0.1571F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.3265F, -0.3142F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.3265F, -0.3142F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(7, 12, -8, 0.5934F, -0.0873F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.1868F, -0.1745F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.1868F, -0.1745F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(10, 12, -1, 0.4451F, -0.0175F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 0.8901F, -0.0349F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 0.8901F, -0.0349F, 0.0F));

        ModelPartData tent3_1_body = body.addChild("tent3_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(9, 12, 5, 0.5236F, 0.0524F, 0.0F));

        ModelPartData tent3_2_body = tent3_1_body.addChild("tent3_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.0472F, 0.1047F, 0.0F));

        ModelPartData tent3_3_body = tent3_2_body.addChild("tent3_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.0472F, 0.1047F, 0.0F));

        ModelPartData tent4_1_body = body.addChild("tent4_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(3, 12, 10, 0.6458F, 0.1222F, 0.0F));

        ModelPartData tent4_2_body = tent4_1_body.addChild("tent4_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.2915F, 0.2443F, 0.0F));

        ModelPartData tent4_3_body = tent4_2_body.addChild("tent4_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.2915F, 0.2443F, 0.0F));

        ModelPartData tent5_1_body = body.addChild("tent5_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(-3, 12, 10, 0.6458F, 0.1920F, 0.0F));

        ModelPartData tent5_2_body = tent5_1_body.addChild("tent5_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.2915F, 0.3840F, 0.0F));

        ModelPartData tent5_3_body = tent5_2_body.addChild("tent5_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.2915F, 0.3840F, 0.0F));

        ModelPartData tent6_1_body = body.addChild("tent6_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(-9, 12, 5, 0.5236F, 0.2618F, 0.0F));

        ModelPartData tent6_2_body = tent6_1_body.addChild("tent6_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.0472F, 0.5236F, 0.0F));

        ModelPartData tent6_3_body = tent6_2_body.addChild("tent6_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.0472F, 0.5236F, 0.0F));

        ModelPartData tent7_1_body = body.addChild("tent7_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(-10, 12, -1, 0.4451F, 0.3316F, 0.0F));

        ModelPartData tent7_2_body = tent7_1_body.addChild("tent7_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 0.8901F, 0.6632F, 0.0F));

        ModelPartData tent7_3_body = tent7_2_body.addChild("tent7_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 0.8901F, 0.6632F, 0.0F));

        ModelPartData tent8_1_body = body.addChild("tent8_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(-7, 12, -8, 0.5934F, 0.4014F, 0.0F));

        ModelPartData tent8_2_body = tent8_1_body.addChild("tent8_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.1868F, 0.8029F, 0.0F));

        ModelPartData tent8_3_body = tent8_2_body.addChild("tent8_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 5, 2),
                ModelTransform.of(0, 5, 0, 1.1868F, 0.8029F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData martianMaster() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-8, -18, -8, 8, 3, 8)
                        .cuboid(-8, -18, 0, 8, 3, 8)
                        .cuboid(0, -18, -8, 8, 3, 8)
                        .cuboid(0, -18, 0, 8, 3, 8)
                        .cuboid(-10, -15, -11, 7, 3, 7)
                        .cuboid(-10, -15, -4, 7, 3, 7)
                        .cuboid(-10, -15, 4, 7, 3, 7)
                        .cuboid(-3, -15, -11, 7, 3, 7)
                        .cuboid(-3, -15, -4, 7, 3, 7)
                        .cuboid(-3, -15, 4, 7, 3, 7)
                        .cuboid(3, -15, -11, 7, 3, 7)
                        .cuboid(3, -15, -4, 7, 3, 7)
                        .cuboid(3, -15, 4, 7, 3, 7)
                        .cuboid(-12, -12, -13, 8, 3, 9)
                        .cuboid(-12, -12, -4, 8, 3, 9)
                        .cuboid(-12, -12, 4, 8, 3, 9)
                        .cuboid(-4, -12, -13, 8, 3, 9)
                        .cuboid(-4, -12, -4, 8, 3, 9)
                        .cuboid(-4, -12, 4, 8, 3, 9)
                        .cuboid(4, -12, -13, 8, 3, 9)
                        .cuboid(4, -12, -4, 8, 3, 9)
                        .cuboid(4, -12, 4, 8, 3, 9)
                        .cuboid(-13, -9, -14, 9, 3, 7)
                        .cuboid(-13, -9, -7, 9, 3, 7)
                        .cuboid(-13, -9, 0, 9, 3, 7)
                        .cuboid(-13, -9, 7, 9, 3, 7)
                        .cuboid(-4, -9, -14, 9, 3, 7)
                        .cuboid(-4, -9, -7, 9, 3, 7)
                        .cuboid(-4, -9, 0, 9, 3, 7)
                        .cuboid(-4, -9, 7, 9, 3, 7)
                        .cuboid(4, -9, -14, 9, 3, 7)
                        .cuboid(4, -9, -7, 9, 3, 7)
                        .cuboid(4, -9, 0, 9, 3, 7)
                        .cuboid(4, -9, 7, 9, 3, 7)
                        .cuboid(-14, -6, -16, 7, 3, 8)
                        .cuboid(-14, -6, -8, 7, 3, 8)
                        .cuboid(-14, -6, 0, 7, 3, 8)
                        .cuboid(-14, -6, 8, 7, 3, 8)
                        .cuboid(-7, -6, -16, 7, 3, 8)
                        .cuboid(-7, -6, -8, 7, 3, 8)
                        .cuboid(-7, -6, 0, 7, 3, 8)
                        .cuboid(-7, -6, 8, 7, 3, 8)
                        .cuboid(0, -6, -16, 7, 3, 8)
                        .cuboid(0, -6, -8, 7, 3, 8)
                        .cuboid(0, -6, 0, 7, 3, 8)
                        .cuboid(0, -6, 8, 7, 3, 8)
                        .cuboid(7, -6, -16, 7, 3, 8)
                        .cuboid(7, -6, -8, 7, 3, 8)
                        .cuboid(7, -6, 0, 7, 3, 8)
                        .cuboid(7, -6, 8, 7, 3, 8)
                        .cuboid(-14, -3, -16, 7, 3, 8)
                        .cuboid(-14, -3, -8, 7, 3, 8)
                        .cuboid(-14, -3, 0, 7, 3, 8)
                        .cuboid(-14, -3, 8, 7, 3, 8)
                        .cuboid(-7, -3, -16, 7, 3, 8)
                        .cuboid(-7, -3, -8, 7, 3, 8)
                        .cuboid(-7, -3, 0, 7, 3, 8)
                        .cuboid(-7, -3, 8, 7, 3, 8)
                        .cuboid(0, -3, -16, 7, 3, 8)
                        .cuboid(0, -3, -8, 7, 3, 8)
                        .cuboid(0, -3, 0, 7, 3, 8)
                        .cuboid(0, -3, 8, 7, 3, 8)
                        .cuboid(7, -3, -16, 7, 3, 8)
                        .cuboid(7, -3, -8, 7, 3, 8)
                        .cuboid(7, -3, 0, 7, 3, 8)
                        .cuboid(7, -3, 8, 7, 3, 8)
                        .cuboid(-13, 0, -14, 9, 3, 7)
                        .cuboid(-13, 0, -7, 9, 3, 7)
                        .cuboid(-13, 0, 0, 9, 3, 7)
                        .cuboid(-13, 0, 7, 9, 3, 7)
                        .cuboid(-4, 0, -14, 9, 3, 7)
                        .cuboid(-4, 0, -7, 9, 3, 7)
                        .cuboid(-4, 0, 0, 9, 3, 7)
                        .cuboid(-4, 0, 7, 9, 3, 7)
                        .cuboid(4, 0, -14, 9, 3, 7)
                        .cuboid(4, 0, -7, 9, 3, 7)
                        .cuboid(4, 0, 0, 9, 3, 7)
                        .cuboid(4, 0, 7, 9, 3, 7)
                        .cuboid(-12, 3, -12, 8, 3, 8)
                        .cuboid(-12, 3, -4, 8, 3, 8)
                        .cuboid(-12, 3, 4, 8, 3, 8)
                        .cuboid(-4, 3, -12, 8, 3, 8)
                        .cuboid(-4, 3, -4, 8, 3, 8)
                        .cuboid(-4, 3, 4, 8, 3, 8)
                        .cuboid(4, 3, -12, 8, 3, 8)
                        .cuboid(4, 3, -4, 8, 3, 8)
                        .cuboid(4, 3, 4, 8, 3, 8)
                        .cuboid(-9, 6, -10, 9, 3, 7)
                        .cuboid(-9, 6, -3, 9, 3, 7)
                        .cuboid(-9, 6, 3, 9, 3, 7)
                        .cuboid(0, 6, -10, 9, 3, 7)
                        .cuboid(0, 6, -3, 9, 3, 7)
                        .cuboid(0, 6, 3, 9, 3, 7)
                        .cuboid(-13, -18, -10, 7, 2, 7)
                        .cuboid(-12, -16, -9, 5, 2, 5)
                        .cuboid(-11, -14, -8, 3, 2, 3)
                        .cuboid(6, -18, -10, 7, 2, 7)
                        .cuboid(7, -16, -9, 5, 2, 5)
                        .cuboid(8, -14, -8, 3, 2, 3)
                        .cuboid(-13, -18, 4, 7, 2, 7)
                        .cuboid(-12, -16, 5, 5, 2, 5)
                        .cuboid(-11, -14, 6, 3, 2, 3)
                        .cuboid(6, -18, 4, 7, 2, 7)
                        .cuboid(7, -16, 5, 5, 2, 5)
                        .cuboid(8, -14, 6, 3, 2, 3),
                ModelTransform.of(0, 0, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData beak = body.addChild("beak",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -6, -10, 11, 11, 9)
                        .cuboid(-4, -4, -14, 8, 8, 7),
                ModelTransform.of(0, -3, -14, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_l = body.addChild("eye_l",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -2, -1, 5, 4, 2),
                ModelTransform.of(-7, -12, -12, 0.0F, 0.0F, 0.0F));

        ModelPartData eye_r = body.addChild("eye_r",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -2, -1, 5, 4, 2),
                ModelTransform.of(7, -12, -12, 0.0F, 0.0F, 0.0F));

        ModelPartData tent0_1_body = body.addChild("tent0_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 10, -15, 0.6632F, -0.1571F, 0.0F));

        ModelPartData tent0_2_body = tent0_1_body.addChild("tent0_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.3265F, -0.3142F, 0.0F));

        ModelPartData tent0_3_body = tent0_2_body.addChild("tent0_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.3265F, -0.3142F, 0.0F));

        ModelPartData tent1_1_body = body.addChild("tent1_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(9, 10, -11, 0.5934F, -0.0873F, 0.0F));

        ModelPartData tent1_2_body = tent1_1_body.addChild("tent1_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.1868F, -0.1745F, 0.0F));

        ModelPartData tent1_3_body = tent1_2_body.addChild("tent1_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.1868F, -0.1745F, 0.0F));

        ModelPartData tent2_1_body = body.addChild("tent2_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(14, 10, -2, 0.4451F, -0.0175F, 0.0F));

        ModelPartData tent2_2_body = tent2_1_body.addChild("tent2_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 0.8901F, -0.0349F, 0.0F));

        ModelPartData tent2_3_body = tent2_2_body.addChild("tent2_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 0.8901F, -0.0349F, 0.0F));

        ModelPartData tent3_1_body = body.addChild("tent3_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(12, 10, 7, 0.5236F, 0.0524F, 0.0F));

        ModelPartData tent3_2_body = tent3_1_body.addChild("tent3_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.0472F, 0.1047F, 0.0F));

        ModelPartData tent3_3_body = tent3_2_body.addChild("tent3_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.0472F, 0.1047F, 0.0F));

        ModelPartData tent4_1_body = body.addChild("tent4_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(5, 10, 14, 0.6458F, 0.1222F, 0.0F));

        ModelPartData tent4_2_body = tent4_1_body.addChild("tent4_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.2915F, 0.2443F, 0.0F));

        ModelPartData tent4_3_body = tent4_2_body.addChild("tent4_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.2915F, 0.2443F, 0.0F));

        ModelPartData tent5_1_body = body.addChild("tent5_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(-5, 10, 14, 0.6458F, 0.1920F, 0.0F));

        ModelPartData tent5_2_body = tent5_1_body.addChild("tent5_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.2915F, 0.3840F, 0.0F));

        ModelPartData tent5_3_body = tent5_2_body.addChild("tent5_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.2915F, 0.3840F, 0.0F));

        ModelPartData tent6_1_body = body.addChild("tent6_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(-12, 10, 7, 0.5236F, 0.2618F, 0.0F));

        ModelPartData tent6_2_body = tent6_1_body.addChild("tent6_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.0472F, 0.5236F, 0.0F));

        ModelPartData tent6_3_body = tent6_2_body.addChild("tent6_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.0472F, 0.5236F, 0.0F));

        ModelPartData tent7_1_body = body.addChild("tent7_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(-14, 10, -2, 0.4451F, 0.3316F, 0.0F));

        ModelPartData tent7_2_body = tent7_1_body.addChild("tent7_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 0.8901F, 0.6632F, 0.0F));

        ModelPartData tent7_3_body = tent7_2_body.addChild("tent7_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 0.8901F, 0.6632F, 0.0F));

        ModelPartData tent8_1_body = body.addChild("tent8_1_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(-9, 10, -11, 0.5934F, 0.4014F, 0.0F));

        ModelPartData tent8_2_body = tent8_1_body.addChild("tent8_2_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.1868F, 0.8029F, 0.0F));

        ModelPartData tent8_3_body = tent8_2_body.addChild("tent8_3_body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 7, 4),
                ModelTransform.of(0, 7, 0, 1.1868F, 0.8029F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

}
