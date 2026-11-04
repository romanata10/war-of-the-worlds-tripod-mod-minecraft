package dev.arena.wotw.client.model;

import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;

/**
 * The Martians themselves: the leathery, tentacled creatures from the novel.
 *
 * <p><b>Generated file.</b> The geometry lives in `tools/generate_models.py` (see
 * `tools/model_lib.py`); edit that and run `python3 tools/generate_models.py`.
 * Model pixels: 16 per block, +y downwards, feet at y = 24, the machine looks towards
 * (0, +y down). Lenses sample the glowing patch of the texture at (96, 96).</p>
 */
public final class MartianModels {


    public static LayerDefinition martian() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-6, -11, -6, 6, 3, 6)
                        .cuboid(-6, -11, 0, 6, 3, 6)
                        .cuboid(0, -11, -6, 6, 3, 6)
                        .cuboid(0, -11, 0, 6, 3, 6)
                        .cuboid(-7, -8, -8, 5, 3, 5)
                        .cuboid(-7, -8, -2, 5, 3, 5)
                        .cuboid(-7, -8, 2, 5, 3, 5)
                        .cuboid(-2, -8, -8, 5, 3, 5)
                        .cuboid(-2, -8, -2, 5, 3, 5)
                        .cuboid(-2, -8, 2, 5, 3, 5)
                        .cuboid(2, -8, -8, 5, 3, 5)
                        .cuboid(2, -8, -2, 5, 3, 5)
                        .cuboid(2, -8, 2, 5, 3, 5)
                        .cuboid(-8, -5, -9, 6, 3, 6)
                        .cuboid(-8, -5, -3, 6, 3, 6)
                        .cuboid(-8, -5, 3, 6, 3, 6)
                        .cuboid(-3, -5, -9, 6, 3, 6)
                        .cuboid(-3, -5, -3, 6, 3, 6)
                        .cuboid(-3, -5, 3, 6, 3, 6)
                        .cuboid(3, -5, -9, 6, 3, 6)
                        .cuboid(3, -5, -3, 6, 3, 6)
                        .cuboid(3, -5, 3, 6, 3, 6)
                        .cuboid(-10, -3, -10, 5, 3, 5)
                        .cuboid(-10, -3, -5, 5, 3, 5)
                        .cuboid(-10, -3, 0, 5, 3, 5)
                        .cuboid(-10, -3, 5, 5, 3, 5)
                        .cuboid(-5, -3, -10, 5, 3, 5)
                        .cuboid(-5, -3, -5, 5, 3, 5)
                        .cuboid(-5, -3, 0, 5, 3, 5)
                        .cuboid(-5, -3, 5, 5, 3, 5)
                        .cuboid(0, -3, -10, 5, 3, 5)
                        .cuboid(0, -3, -5, 5, 3, 5)
                        .cuboid(0, -3, 0, 5, 3, 5)
                        .cuboid(0, -3, 5, 5, 3, 5)
                        .cuboid(5, -3, -10, 5, 3, 5)
                        .cuboid(5, -3, -5, 5, 3, 5)
                        .cuboid(5, -3, 0, 5, 3, 5)
                        .cuboid(5, -3, 5, 5, 3, 5)
                        .cuboid(-10, 0, -11, 5, 3, 6)
                        .cuboid(-10, 0, -6, 5, 3, 6)
                        .cuboid(-10, 0, 0, 5, 3, 6)
                        .cuboid(-10, 0, 6, 5, 3, 6)
                        .cuboid(-5, 0, -11, 5, 3, 6)
                        .cuboid(-5, 0, -6, 5, 3, 6)
                        .cuboid(-5, 0, 0, 5, 3, 6)
                        .cuboid(-5, 0, 6, 5, 3, 6)
                        .cuboid(0, 0, -11, 5, 3, 6)
                        .cuboid(0, 0, -6, 5, 3, 6)
                        .cuboid(0, 0, 0, 5, 3, 6)
                        .cuboid(0, 0, 6, 5, 3, 6)
                        .cuboid(5, 0, -11, 5, 3, 6)
                        .cuboid(5, 0, -6, 5, 3, 6)
                        .cuboid(5, 0, 0, 5, 3, 6)
                        .cuboid(5, 0, 6, 5, 3, 6)
                        .cuboid(-10, 3, -11, 5, 3, 6)
                        .cuboid(-10, 3, -6, 5, 3, 6)
                        .cuboid(-10, 3, 0, 5, 3, 6)
                        .cuboid(-10, 3, 6, 5, 3, 6)
                        .cuboid(-5, 3, -11, 5, 3, 6)
                        .cuboid(-5, 3, -6, 5, 3, 6)
                        .cuboid(-5, 3, 0, 5, 3, 6)
                        .cuboid(-5, 3, 6, 5, 3, 6)
                        .cuboid(0, 3, -11, 5, 3, 6)
                        .cuboid(0, 3, -6, 5, 3, 6)
                        .cuboid(0, 3, 0, 5, 3, 6)
                        .cuboid(0, 3, 6, 5, 3, 6)
                        .cuboid(5, 3, -11, 5, 3, 6)
                        .cuboid(5, 3, -6, 5, 3, 6)
                        .cuboid(5, 3, 0, 5, 3, 6)
                        .cuboid(5, 3, 6, 5, 3, 6)
                        .cuboid(-10, 5, -10, 5, 3, 5)
                        .cuboid(-10, 5, -5, 5, 3, 5)
                        .cuboid(-10, 5, 0, 5, 3, 5)
                        .cuboid(-10, 5, 5, 5, 3, 5)
                        .cuboid(-5, 5, -10, 5, 3, 5)
                        .cuboid(-5, 5, -5, 5, 3, 5)
                        .cuboid(-5, 5, 0, 5, 3, 5)
                        .cuboid(-5, 5, 5, 5, 3, 5)
                        .cuboid(0, 5, -10, 5, 3, 5)
                        .cuboid(0, 5, -5, 5, 3, 5)
                        .cuboid(0, 5, 0, 5, 3, 5)
                        .cuboid(0, 5, 5, 5, 3, 5)
                        .cuboid(5, 5, -10, 5, 3, 5)
                        .cuboid(5, 5, -5, 5, 3, 5)
                        .cuboid(5, 5, 0, 5, 3, 5)
                        .cuboid(5, 5, 5, 5, 3, 5)
                        .cuboid(-8, 8, -9, 5, 3, 6)
                        .cuboid(-8, 8, -3, 5, 3, 6)
                        .cuboid(-8, 8, 3, 5, 3, 6)
                        .cuboid(-3, 8, -9, 5, 3, 6)
                        .cuboid(-3, 8, -3, 5, 3, 6)
                        .cuboid(-3, 8, 3, 5, 3, 6)
                        .cuboid(3, 8, -9, 5, 3, 6)
                        .cuboid(3, 8, -3, 5, 3, 6)
                        .cuboid(3, 8, 3, 5, 3, 6)
                        .cuboid(-6, 11, -7, 4, 3, 5)
                        .cuboid(-6, 11, -2, 4, 3, 5)
                        .cuboid(-6, 11, 2, 4, 3, 5)
                        .cuboid(-2, 11, -7, 4, 3, 5)
                        .cuboid(-2, 11, -2, 4, 3, 5)
                        .cuboid(-2, 11, 2, 4, 3, 5)
                        .cuboid(2, 11, -7, 4, 3, 5)
                        .cuboid(2, 11, -2, 4, 3, 5)
                        .cuboid(2, 11, 2, 4, 3, 5),
                PartPose.of(0, -2, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition beak = body.addOrReplaceChild("beak",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-4, -5, -7, 8, 11, 6)
                        .cuboid(-3, -4, -10, 5, 7, 5),
                PartPose.of(0, 4, -10, 0.0F, 0.0F, 0.0F));

        PartDefinition eye_l = body.addOrReplaceChild("eye_l",
                CubeListBuilder.create()
                        .uv(96, 96).cuboid(-2, -1, -1, 4, 3, 2),
                PartPose.of(-5, -5, -9, 0.0F, 0.0F, 0.0F));

        PartDefinition eye_r = body.addOrReplaceChild("eye_r",
                CubeListBuilder.create()
                        .uv(96, 96).cuboid(-2, -1, -1, 4, 3, 2),
                PartPose.of(5, -5, -9, 0.0F, 0.0F, 0.0F));

        PartDefinition tent0_1_body = body.addOrReplaceChild("tent0_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 16, -11, 0.6632F, -0.1571F, 0.0F));

        PartDefinition tent0_2_body = tent0_1_body.addOrReplaceChild("tent0_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.3265F, -0.3142F, 0.0F));

        PartDefinition tent0_3_body = tent0_2_body.addOrReplaceChild("tent0_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.3265F, -0.3142F, 0.0F));

        PartDefinition tent1_1_body = body.addOrReplaceChild("tent1_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(7, 16, -8, 0.5934F, -0.0873F, 0.0F));

        PartDefinition tent1_2_body = tent1_1_body.addOrReplaceChild("tent1_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.1868F, -0.1745F, 0.0F));

        PartDefinition tent1_3_body = tent1_2_body.addOrReplaceChild("tent1_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.1868F, -0.1745F, 0.0F));

        PartDefinition tent2_1_body = body.addOrReplaceChild("tent2_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(10, 16, -1, 0.4451F, -0.0175F, 0.0F));

        PartDefinition tent2_2_body = tent2_1_body.addOrReplaceChild("tent2_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 0.8901F, -0.0349F, 0.0F));

        PartDefinition tent2_3_body = tent2_2_body.addOrReplaceChild("tent2_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 0.8901F, -0.0349F, 0.0F));

        PartDefinition tent3_1_body = body.addOrReplaceChild("tent3_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(9, 16, 5, 0.5236F, 0.0524F, 0.0F));

        PartDefinition tent3_2_body = tent3_1_body.addOrReplaceChild("tent3_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.0472F, 0.1047F, 0.0F));

        PartDefinition tent3_3_body = tent3_2_body.addOrReplaceChild("tent3_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.0472F, 0.1047F, 0.0F));

        PartDefinition tent4_1_body = body.addOrReplaceChild("tent4_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(3, 16, 10, 0.6458F, 0.1222F, 0.0F));

        PartDefinition tent4_2_body = tent4_1_body.addOrReplaceChild("tent4_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.2915F, 0.2443F, 0.0F));

        PartDefinition tent4_3_body = tent4_2_body.addOrReplaceChild("tent4_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.2915F, 0.2443F, 0.0F));

        PartDefinition tent5_1_body = body.addOrReplaceChild("tent5_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(-3, 16, 10, 0.6458F, 0.1920F, 0.0F));

        PartDefinition tent5_2_body = tent5_1_body.addOrReplaceChild("tent5_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.2915F, 0.3840F, 0.0F));

        PartDefinition tent5_3_body = tent5_2_body.addOrReplaceChild("tent5_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.2915F, 0.3840F, 0.0F));

        PartDefinition tent6_1_body = body.addOrReplaceChild("tent6_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(-9, 16, 5, 0.5236F, 0.2618F, 0.0F));

        PartDefinition tent6_2_body = tent6_1_body.addOrReplaceChild("tent6_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.0472F, 0.5236F, 0.0F));

        PartDefinition tent6_3_body = tent6_2_body.addOrReplaceChild("tent6_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.0472F, 0.5236F, 0.0F));

        PartDefinition tent7_1_body = body.addOrReplaceChild("tent7_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(-10, 16, -1, 0.4451F, 0.3316F, 0.0F));

        PartDefinition tent7_2_body = tent7_1_body.addOrReplaceChild("tent7_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 0.8901F, 0.6632F, 0.0F));

        PartDefinition tent7_3_body = tent7_2_body.addOrReplaceChild("tent7_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 0.8901F, 0.6632F, 0.0F));

        PartDefinition tent8_1_body = body.addOrReplaceChild("tent8_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(-7, 16, -8, 0.5934F, 0.4014F, 0.0F));

        PartDefinition tent8_2_body = tent8_1_body.addOrReplaceChild("tent8_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.1868F, 0.8029F, 0.0F));

        PartDefinition tent8_3_body = tent8_2_body.addOrReplaceChild("tent8_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-1, 0, -1, 2, 7, 2),
                PartPose.of(0, 7, 0, 1.1868F, 0.8029F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

    public static LayerDefinition martianMaster() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-8, -22, -8, 8, 4, 8)
                        .cuboid(-8, -22, 0, 8, 4, 8)
                        .cuboid(0, -22, -8, 8, 4, 8)
                        .cuboid(0, -22, 0, 8, 4, 8)
                        .cuboid(-10, -18, -11, 7, 4, 7)
                        .cuboid(-10, -18, -4, 7, 4, 7)
                        .cuboid(-10, -18, 4, 7, 4, 7)
                        .cuboid(-3, -18, -11, 7, 4, 7)
                        .cuboid(-3, -18, -4, 7, 4, 7)
                        .cuboid(-3, -18, 4, 7, 4, 7)
                        .cuboid(3, -18, -11, 7, 4, 7)
                        .cuboid(3, -18, -4, 7, 4, 7)
                        .cuboid(3, -18, 4, 7, 4, 7)
                        .cuboid(-12, -15, -13, 8, 4, 9)
                        .cuboid(-12, -15, -4, 8, 4, 9)
                        .cuboid(-12, -15, 4, 8, 4, 9)
                        .cuboid(-4, -15, -13, 8, 4, 9)
                        .cuboid(-4, -15, -4, 8, 4, 9)
                        .cuboid(-4, -15, 4, 8, 4, 9)
                        .cuboid(4, -15, -13, 8, 4, 9)
                        .cuboid(4, -15, -4, 8, 4, 9)
                        .cuboid(4, -15, 4, 8, 4, 9)
                        .cuboid(-13, -11, -14, 9, 4, 7)
                        .cuboid(-13, -11, -7, 9, 4, 7)
                        .cuboid(-13, -11, 0, 9, 4, 7)
                        .cuboid(-13, -11, 7, 9, 4, 7)
                        .cuboid(-4, -11, -14, 9, 4, 7)
                        .cuboid(-4, -11, -7, 9, 4, 7)
                        .cuboid(-4, -11, 0, 9, 4, 7)
                        .cuboid(-4, -11, 7, 9, 4, 7)
                        .cuboid(4, -11, -14, 9, 4, 7)
                        .cuboid(4, -11, -7, 9, 4, 7)
                        .cuboid(4, -11, 0, 9, 4, 7)
                        .cuboid(4, -11, 7, 9, 4, 7)
                        .cuboid(-14, -7, -16, 7, 4, 8)
                        .cuboid(-14, -7, -8, 7, 4, 8)
                        .cuboid(-14, -7, 0, 7, 4, 8)
                        .cuboid(-14, -7, 8, 7, 4, 8)
                        .cuboid(-7, -7, -16, 7, 4, 8)
                        .cuboid(-7, -7, -8, 7, 4, 8)
                        .cuboid(-7, -7, 0, 7, 4, 8)
                        .cuboid(-7, -7, 8, 7, 4, 8)
                        .cuboid(0, -7, -16, 7, 4, 8)
                        .cuboid(0, -7, -8, 7, 4, 8)
                        .cuboid(0, -7, 0, 7, 4, 8)
                        .cuboid(0, -7, 8, 7, 4, 8)
                        .cuboid(7, -7, -16, 7, 4, 8)
                        .cuboid(7, -7, -8, 7, 4, 8)
                        .cuboid(7, -7, 0, 7, 4, 8)
                        .cuboid(7, -7, 8, 7, 4, 8)
                        .cuboid(-14, -4, -16, 7, 4, 8)
                        .cuboid(-14, -4, -8, 7, 4, 8)
                        .cuboid(-14, -4, 0, 7, 4, 8)
                        .cuboid(-14, -4, 8, 7, 4, 8)
                        .cuboid(-7, -4, -16, 7, 4, 8)
                        .cuboid(-7, -4, -8, 7, 4, 8)
                        .cuboid(-7, -4, 0, 7, 4, 8)
                        .cuboid(-7, -4, 8, 7, 4, 8)
                        .cuboid(0, -4, -16, 7, 4, 8)
                        .cuboid(0, -4, -8, 7, 4, 8)
                        .cuboid(0, -4, 0, 7, 4, 8)
                        .cuboid(0, -4, 8, 7, 4, 8)
                        .cuboid(7, -4, -16, 7, 4, 8)
                        .cuboid(7, -4, -8, 7, 4, 8)
                        .cuboid(7, -4, 0, 7, 4, 8)
                        .cuboid(7, -4, 8, 7, 4, 8)
                        .cuboid(-13, 0, -14, 9, 4, 7)
                        .cuboid(-13, 0, -7, 9, 4, 7)
                        .cuboid(-13, 0, 0, 9, 4, 7)
                        .cuboid(-13, 0, 7, 9, 4, 7)
                        .cuboid(-4, 0, -14, 9, 4, 7)
                        .cuboid(-4, 0, -7, 9, 4, 7)
                        .cuboid(-4, 0, 0, 9, 4, 7)
                        .cuboid(-4, 0, 7, 9, 4, 7)
                        .cuboid(4, 0, -14, 9, 4, 7)
                        .cuboid(4, 0, -7, 9, 4, 7)
                        .cuboid(4, 0, 0, 9, 4, 7)
                        .cuboid(4, 0, 7, 9, 4, 7)
                        .cuboid(-12, 4, -12, 8, 4, 8)
                        .cuboid(-12, 4, -4, 8, 4, 8)
                        .cuboid(-12, 4, 4, 8, 4, 8)
                        .cuboid(-4, 4, -12, 8, 4, 8)
                        .cuboid(-4, 4, -4, 8, 4, 8)
                        .cuboid(-4, 4, 4, 8, 4, 8)
                        .cuboid(4, 4, -12, 8, 4, 8)
                        .cuboid(4, 4, -4, 8, 4, 8)
                        .cuboid(4, 4, 4, 8, 4, 8)
                        .cuboid(-9, 7, -10, 9, 4, 7)
                        .cuboid(-9, 7, -3, 9, 4, 7)
                        .cuboid(-9, 7, 3, 9, 4, 7)
                        .cuboid(0, 7, -10, 9, 4, 7)
                        .cuboid(0, 7, -3, 9, 4, 7)
                        .cuboid(0, 7, 3, 9, 4, 7)
                        .cuboid(-13, -22, -10, 7, 2, 7)
                        .cuboid(-12, -20, -9, 5, 2, 5)
                        .cuboid(-11, -17, -8, 3, 2, 3)
                        .cuboid(6, -22, -10, 7, 2, 7)
                        .cuboid(7, -20, -9, 5, 2, 5)
                        .cuboid(8, -17, -8, 3, 2, 3)
                        .cuboid(-13, -22, 4, 7, 2, 7)
                        .cuboid(-12, -20, 5, 5, 2, 5)
                        .cuboid(-11, -17, 6, 3, 2, 3)
                        .cuboid(6, -22, 4, 7, 2, 7)
                        .cuboid(7, -20, 5, 5, 2, 5)
                        .cuboid(8, -17, 6, 3, 2, 3),
                PartPose.of(0, -2, 0, 0.0F, 0.0F, 0.0F));

        PartDefinition beak = body.addOrReplaceChild("beak",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-6, -7, -10, 11, 13, 9)
                        .cuboid(-4, -5, -14, 8, 10, 7),
                PartPose.of(0, -4, -14, 0.0F, 0.0F, 0.0F));

        PartDefinition eye_l = body.addOrReplaceChild("eye_l",
                CubeListBuilder.create()
                        .uv(96, 96).cuboid(-3, -2, -1, 5, 5, 2),
                PartPose.of(-7, -15, -12, 0.0F, 0.0F, 0.0F));

        PartDefinition eye_r = body.addOrReplaceChild("eye_r",
                CubeListBuilder.create()
                        .uv(96, 96).cuboid(-3, -2, -1, 5, 5, 2),
                PartPose.of(7, -15, -12, 0.0F, 0.0F, 0.0F));

        PartDefinition tent0_1_body = body.addOrReplaceChild("tent0_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 12, -15, 0.6632F, -0.1571F, 0.0F));

        PartDefinition tent0_2_body = tent0_1_body.addOrReplaceChild("tent0_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.3265F, -0.3142F, 0.0F));

        PartDefinition tent0_3_body = tent0_2_body.addOrReplaceChild("tent0_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.3265F, -0.3142F, 0.0F));

        PartDefinition tent1_1_body = body.addOrReplaceChild("tent1_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(9, 12, -11, 0.5934F, -0.0873F, 0.0F));

        PartDefinition tent1_2_body = tent1_1_body.addOrReplaceChild("tent1_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.1868F, -0.1745F, 0.0F));

        PartDefinition tent1_3_body = tent1_2_body.addOrReplaceChild("tent1_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.1868F, -0.1745F, 0.0F));

        PartDefinition tent2_1_body = body.addOrReplaceChild("tent2_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(14, 12, -2, 0.4451F, -0.0175F, 0.0F));

        PartDefinition tent2_2_body = tent2_1_body.addOrReplaceChild("tent2_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 0.8901F, -0.0349F, 0.0F));

        PartDefinition tent2_3_body = tent2_2_body.addOrReplaceChild("tent2_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 0.8901F, -0.0349F, 0.0F));

        PartDefinition tent3_1_body = body.addOrReplaceChild("tent3_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(12, 12, 7, 0.5236F, 0.0524F, 0.0F));

        PartDefinition tent3_2_body = tent3_1_body.addOrReplaceChild("tent3_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.0472F, 0.1047F, 0.0F));

        PartDefinition tent3_3_body = tent3_2_body.addOrReplaceChild("tent3_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.0472F, 0.1047F, 0.0F));

        PartDefinition tent4_1_body = body.addOrReplaceChild("tent4_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(5, 12, 14, 0.6458F, 0.1222F, 0.0F));

        PartDefinition tent4_2_body = tent4_1_body.addOrReplaceChild("tent4_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.2915F, 0.2443F, 0.0F));

        PartDefinition tent4_3_body = tent4_2_body.addOrReplaceChild("tent4_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.2915F, 0.2443F, 0.0F));

        PartDefinition tent5_1_body = body.addOrReplaceChild("tent5_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(-5, 12, 14, 0.6458F, 0.1920F, 0.0F));

        PartDefinition tent5_2_body = tent5_1_body.addOrReplaceChild("tent5_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.2915F, 0.3840F, 0.0F));

        PartDefinition tent5_3_body = tent5_2_body.addOrReplaceChild("tent5_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.2915F, 0.3840F, 0.0F));

        PartDefinition tent6_1_body = body.addOrReplaceChild("tent6_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(-12, 12, 7, 0.5236F, 0.2618F, 0.0F));

        PartDefinition tent6_2_body = tent6_1_body.addOrReplaceChild("tent6_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.0472F, 0.5236F, 0.0F));

        PartDefinition tent6_3_body = tent6_2_body.addOrReplaceChild("tent6_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.0472F, 0.5236F, 0.0F));

        PartDefinition tent7_1_body = body.addOrReplaceChild("tent7_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(-14, 12, -2, 0.4451F, 0.3316F, 0.0F));

        PartDefinition tent7_2_body = tent7_1_body.addOrReplaceChild("tent7_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 0.8901F, 0.6632F, 0.0F));

        PartDefinition tent7_3_body = tent7_2_body.addOrReplaceChild("tent7_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 0.8901F, 0.6632F, 0.0F));

        PartDefinition tent8_1_body = body.addOrReplaceChild("tent8_1_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(-9, 12, -11, 0.5934F, 0.4014F, 0.0F));

        PartDefinition tent8_2_body = tent8_1_body.addOrReplaceChild("tent8_2_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.1868F, 0.8029F, 0.0F));

        PartDefinition tent8_3_body = tent8_2_body.addOrReplaceChild("tent8_3_body",
                CubeListBuilder.create()
                        .uv(0, 0).cuboid(-2, 0, -2, 4, 9, 4),
                PartPose.of(0, 9, 0, 1.1868F, 0.8029F, 0.0F));

        return LayerDefinition.create(data, 128, 128);
    }

}
