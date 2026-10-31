package dev.arena.wotw.client.model;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

/**
 * The human army: soldiers, tanks, interceptors and warships.
 *
 * <p><b>Generated file.</b> The geometry lives in `tools/generate_models.py` (see
 * `tools/model_lib.py`); edit that and run `python3 tools/generate_models.py`.
 * Model pixels: 16 per block, +y downwards, feet at y = 24, the machine looks towards
 * (0, +y down). Lenses sample the glowing patch of the texture at (96, 96).</p>
 */
public final class HumanModels {


    public static TexturedModelData soldier() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData leg_l = root.addChild("leg_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 16, 4)
                        .cuboid(-2, 15, -3, 4, 3, 6),
                ModelTransform.of(-3, 6, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData leg_r = root.addChild("leg_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 16, 4)
                        .cuboid(-2, 15, -3, 4, 3, 6),
                ModelTransform.of(3, 6, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-5, -14, -3, 10, 14, 6)
                        .cuboid(-6, -13, -3, 12, 4, 7),
                ModelTransform.of(0, 6, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4, -9, -4, 8, 9, 8)
                        .cuboid(-5, -10, -5, 10, 4, 10),
                ModelTransform.of(0, -14, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = head.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -1, -1, 5, 2, 2),
                ModelTransform.of(0, -5, -5, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 11, 4)
                        .cuboid(-1, 8, -10, 3, 3, 14)
                        .cuboid(-2, 7, -2, 4, 5, 5),
                ModelTransform.of(6, -12, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 11, 4),
                ModelTransform.of(-6, -12, 0, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData tank() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-16, -30, -26, 32, 14, 52)
                        .cuboid(-22, -20, -28, 5, 20, 56)
                        .cuboid(17, -20, -28, 5, 20, 56)
                        .cuboid(-16, -34, 6, 32, 4, 18),
                ModelTransform.of(0, 24, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData turret = body.addChild("turret",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-9, -11, -11, 18, 11, 22)
                        .cuboid(-2, -8, -32, 4, 4, 22)
                        .cuboid(-4, -9, -34, 8, 6, 4)
                        .cuboid(-1, -7, -35, 2, 2, 2),
                ModelTransform.of(0, -30, -2, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData fighter() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-6, -16, -31, 6, 6, 9)
                        .cuboid(-6, -16, -22, 6, 6, 9)
                        .cuboid(-6, -16, -13, 6, 6, 9)
                        .cuboid(-6, -16, -4, 6, 6, 9)
                        .cuboid(-6, -16, 4, 6, 6, 9)
                        .cuboid(-6, -16, 13, 6, 6, 9)
                        .cuboid(-6, -16, 22, 6, 6, 9)
                        .cuboid(-6, -10, -31, 6, 6, 9)
                        .cuboid(-6, -10, -22, 6, 6, 9)
                        .cuboid(-6, -10, -13, 6, 6, 9)
                        .cuboid(-6, -10, -4, 6, 6, 9)
                        .cuboid(-6, -10, 4, 6, 6, 9)
                        .cuboid(-6, -10, 13, 6, 6, 9)
                        .cuboid(-6, -10, 22, 6, 6, 9)
                        .cuboid(0, -16, -31, 6, 6, 9)
                        .cuboid(0, -16, -22, 6, 6, 9)
                        .cuboid(0, -16, -13, 6, 6, 9)
                        .cuboid(0, -16, -4, 6, 6, 9)
                        .cuboid(0, -16, 4, 6, 6, 9)
                        .cuboid(0, -16, 13, 6, 6, 9)
                        .cuboid(0, -16, 22, 6, 6, 9)
                        .cuboid(0, -10, -31, 6, 6, 9)
                        .cuboid(0, -10, -22, 6, 6, 9)
                        .cuboid(0, -10, -13, 6, 6, 9)
                        .cuboid(0, -10, -4, 6, 6, 9)
                        .cuboid(0, -10, 4, 6, 6, 9)
                        .cuboid(0, -10, 13, 6, 6, 9)
                        .cuboid(0, -10, 22, 6, 6, 9)
                        .cuboid(-4, -20, -3, 8, 6, 7)
                        .cuboid(-4, -20, 4, 8, 6, 7)
                        .cuboid(-4, -20, 12, 8, 6, 7),
                ModelTransform.of(0, 28, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData wing_l = body.addChild("wing_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(0, -2, -8, -34, 4, 16)
                        .cuboid(0, -1, 8, -20, 3, 8)
                        .cuboid(-26, -2, -4, 6, 4, 6),
                ModelTransform.of(-5, -12, -4, 0.0F, 0.0F, 0.0F));

        ModelPartData wing_r = body.addChild("wing_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(0, -2, -8, 34, 4, 16)
                        .cuboid(0, -1, 8, 20, 3, 8)
                        .cuboid(26, -2, -4, 6, 4, 6),
                ModelTransform.of(5, -12, -4, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, -20, -2, 2, 20, 6)
                        .cuboid(-6, -6, 2, 12, 5, 8),
                ModelTransform.of(0, -16, 26, 0.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye",
                ModelPartBuilder.create()
                        .uv(96, 96).cuboid(-3, -2, -1, 6, 4, 2),
                ModelTransform.of(0, -12, -31, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

    public static TexturedModelData warship() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-17, -22, -50, 11, 11, 14)
                        .cuboid(-17, -22, -36, 11, 11, 14)
                        .cuboid(-17, -22, -21, 11, 11, 14)
                        .cuboid(-17, -22, -7, 11, 11, 14)
                        .cuboid(-17, -22, 7, 11, 11, 14)
                        .cuboid(-17, -22, 21, 11, 11, 14)
                        .cuboid(-17, -22, 36, 11, 11, 14)
                        .cuboid(-17, -11, -50, 11, 11, 14)
                        .cuboid(-17, -11, -36, 11, 11, 14)
                        .cuboid(-17, -11, -21, 11, 11, 14)
                        .cuboid(-17, -11, -7, 11, 11, 14)
                        .cuboid(-17, -11, 7, 11, 11, 14)
                        .cuboid(-17, -11, 21, 11, 11, 14)
                        .cuboid(-17, -11, 36, 11, 11, 14)
                        .cuboid(-6, -22, -50, 11, 11, 14)
                        .cuboid(-6, -22, -36, 11, 11, 14)
                        .cuboid(-6, -22, -21, 11, 11, 14)
                        .cuboid(-6, -22, -7, 11, 11, 14)
                        .cuboid(-6, -22, 7, 11, 11, 14)
                        .cuboid(-6, -22, 21, 11, 11, 14)
                        .cuboid(-6, -22, 36, 11, 11, 14)
                        .cuboid(-6, -11, -50, 11, 11, 14)
                        .cuboid(-6, -11, -36, 11, 11, 14)
                        .cuboid(-6, -11, -21, 11, 11, 14)
                        .cuboid(-6, -11, -7, 11, 11, 14)
                        .cuboid(-6, -11, 7, 11, 11, 14)
                        .cuboid(-6, -11, 21, 11, 11, 14)
                        .cuboid(-6, -11, 36, 11, 11, 14)
                        .cuboid(6, -22, -50, 11, 11, 14)
                        .cuboid(6, -22, -36, 11, 11, 14)
                        .cuboid(6, -22, -21, 11, 11, 14)
                        .cuboid(6, -22, -7, 11, 11, 14)
                        .cuboid(6, -22, 7, 11, 11, 14)
                        .cuboid(6, -22, 21, 11, 11, 14)
                        .cuboid(6, -22, 36, 11, 11, 14)
                        .cuboid(6, -11, -50, 11, 11, 14)
                        .cuboid(6, -11, -36, 11, 11, 14)
                        .cuboid(6, -11, -21, 11, 11, 14)
                        .cuboid(6, -11, -7, 11, 11, 14)
                        .cuboid(6, -11, 7, 11, 11, 14)
                        .cuboid(6, -11, 21, 11, 11, 14)
                        .cuboid(6, -11, 36, 11, 11, 14)
                        .cuboid(-10, -28, -30, 10, 8, 12)
                        .cuboid(-10, -28, -18, 10, 8, 12)
                        .cuboid(-10, -28, -6, 10, 8, 12)
                        .cuboid(-10, -28, 6, 10, 8, 12)
                        .cuboid(-10, -28, 18, 10, 8, 12)
                        .cuboid(0, -28, -30, 10, 8, 12)
                        .cuboid(0, -28, -18, 10, 8, 12)
                        .cuboid(0, -28, -6, 10, 8, 12)
                        .cuboid(0, -28, 6, 10, 8, 12)
                        .cuboid(0, -28, 18, 10, 8, 12),
                ModelTransform.of(0, 24, 0, 0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-8, -14, -10, 16, 14, 20)
                        .cuboid(-3, -24, -3, 6, 10, 6)
                        .cuboid(-8, -10, -11, 16, 4, 2),
                ModelTransform.of(0, -28, -6, 0.0F, 0.0F, 0.0F));

        ModelPartData turret = body.addChild("turret",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-7, -8, -7, 14, 8, 14)
                        .cuboid(-2, -6, -28, 4, 4, 22)
                        .cuboid(-2, -6, 20, 4, 4, 20),
                ModelTransform.of(0, -22, -34, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_l = body.addChild("arm_l",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, -16, -1, 2, 16, 2)
                        .cuboid(-1, -18, -1, 2, 2, 2),
                ModelTransform.of(-12, -28, 6, 0.0F, 0.0F, 0.0F));

        ModelPartData arm_r = body.addChild("arm_r",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1, -16, -1, 2, 16, 2)
                        .cuboid(-1, -18, -1, 2, 2, 2),
                ModelTransform.of(12, -28, 6, 0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 128, 128);
    }

}
