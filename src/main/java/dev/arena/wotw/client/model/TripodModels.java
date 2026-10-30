package dev.arena.wotw.client.model;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

/**
 * Hand built geometry for all ten machines.
 *
 * <p>Conventions: model space has its feet at {@code y = 24} (vanilla living entity anchor),
 * the machine looks towards {@code -Z}, and every animated part is a direct child of the root
 * named {@code leg*} / {@code wing*} / {@code arm*} / {@code head}. The glow (eye) parts sample
 * the bright patch of the texture at {@code (96, 96)}.</p>
 */
public final class TripodModels {

    private static final int GLOW_U = 96;
    private static final int GLOW_V = 96;

    private TripodModels() {
    }

    /* ------------------------------------------------------------------ */
    /*  Helpers                                                            */
    /* ------------------------------------------------------------------ */

    /**
     * Pivot with a rotation given in degrees (Minecraft itself wants radians).
     * Rotation order is the vanilla one: roll (Z), then yaw (Y), then pitch (X).
     */
    private static ModelTransform pose(int px, int py, int pz, float pitchDeg, float yawDeg, float rollDeg) {
        return ModelTransform.of(px, py, pz,
                (float) Math.toRadians(pitchDeg),
                (float) Math.toRadians(yawDeg),
                (float) Math.toRadians(rollDeg));
    }

    /**
     * A leg that hangs from its hip and splays outwards, so the machine stands like a tripod
     * instead of balancing on vertical sticks. The splay is derived from where the hip is:
     * legs on the left go left, legs in front go forward.
     */
    private static ModelPartData leg(ModelPartData root, String name, int px, int py, int pz,
                                     int thickness, int thigh, int shin, int bendX, int bendZ) {
        int lower = Math.max(1, thickness - 1);
        float side = Integer.signum(px) * 13.0F;
        float front = Integer.signum(pz) * 10.0F;
        return root.addChild(name, ModelPartBuilder.create()
                        .cuboid(-thickness, 0, -thickness, thickness * 2, thigh, thickness * 2)
                        .cuboid(-lower + bendX, thigh, -lower + bendZ, lower * 2, shin, lower * 2),
                pose(px, py, pz, front, 0.0F, side));
    }

    private static ModelPartData glow(ModelPartData root, String name, int px, int py, int pz,
                                      int width, int height, int depth) {
        return root.addChild(name, ModelPartBuilder.create().uv(GLOW_U, GLOW_V)
                        .cuboid(-width / 2, -height / 2, -depth / 2, width, height, depth),
                ModelTransform.pivot(px, py, pz));
    }

    /* ------------------------------------------------------------------ */
    /*  1. 2005 Steven Spielberg tripod                                    */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData tripod2005() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-8, -8, -9, 16, 16, 18)
                        .cuboid(-6, -10, -6, 12, 3, 12),
                ModelTransform.pivot(0, 2, 0));
        body.addChild("dome", ModelPartBuilder.create()
                        .cuboid(-5, -3, -5, 10, 5, 10),
                ModelTransform.pivot(0, -7, 0));

        root.addChild("head", ModelPartBuilder.create()
                        .cuboid(-4, -4, -4, 8, 8, 5),
                ModelTransform.pivot(0, 0, -9));
        glow(root, "lens", 0, 0, -13, 6, 6, 2);

        leg(root, "leg_fl", -6, 8, -5, 2, 7, 9, -1, 0);
        leg(root, "leg_fr", 6, 8, -5, 2, 7, 9, 1, 0);
        leg(root, "leg_b", 0, 8, 8, 2, 7, 9, 0, 1);

        root.addChild("arm_l", ModelPartBuilder.create()
                        .cuboid(-2, 0, -2, 3, 9, 3)
                        .cuboid(-1, 9, -1, 2, 6, 2),
                ModelTransform.pivot(-5, 10, 2));
        root.addChild("arm_r", ModelPartBuilder.create()
                        .cuboid(-1, 0, -2, 3, 9, 3)
                        .cuboid(-1, 9, -1, 2, 6, 2),
                ModelTransform.pivot(5, 10, 2));

        return TexturedModelData.of(data, 128, 128);
    }

    /* ------------------------------------------------------------------ */
    /*  2 + 3. BBC "The Tripods" — white collector, red laser variant      */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData bbc(boolean armed) {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-5, -5, -5, 10, 10, 10),
                ModelTransform.pivot(0, 6, 0));
        root.addChild("dome", ModelPartBuilder.create()
                        .cuboid(-4, -3, -4, 8, 4, 8),
                ModelTransform.pivot(0, 1, 0));

        root.addChild("head", ModelPartBuilder.create()
                        .cuboid(-3, -3, -3, 6, 6, 4),
                ModelTransform.pivot(0, 7, -5));
        glow(root, "lens", 0, 7, -9, 4, 4, 2);

        if (armed) {
            root.addChild("cannon", ModelPartBuilder.create()
                            .cuboid(-1, -1, -8, 2, 2, 8),
                    ModelTransform.pivot(0, 4, -4));
        }

        leg(root, "leg_l", -4, 10, 0, 1, 7, 7, -1, 0);
        leg(root, "leg_r", 4, 10, 0, 1, 7, 7, 1, 0);
        leg(root, "leg_b", 0, 10, 4, 1, 7, 7, 0, 1);

        return TexturedModelData.of(data, 128, 128);
    }

    /* ------------------------------------------------------------------ */
    /*  4. 1953 George Pal flying manta                                    */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData manta() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-5, -3, -12, 10, 6, 24)
                        .cuboid(-3, -5, -8, 6, 2, 14),
                ModelTransform.pivot(0, 6, 0));
        glow(root, "lens", 0, 6, -13, 4, 4, 2);

        root.addChild("wing_l", ModelPartBuilder.create()
                        .cuboid(-18, -1, -6, 18, 3, 12),
                ModelTransform.pivot(-5, 6, 0));
        root.addChild("wing_r", ModelPartBuilder.create()
                        .cuboid(0, -1, -6, 18, 3, 12),
                ModelTransform.pivot(5, 6, 0));
        root.addChild("tail", ModelPartBuilder.create()
                        .cuboid(-1, -1, 0, 2, 2, 8),
                ModelTransform.pivot(0, 6, 12));

        leg(root, "leg_l", -3, 9, 2, 1, 3, 3, 0, 0);
        leg(root, "leg_r", 3, 9, 2, 1, 3, 3, 0, 0);
        leg(root, "leg_b", 0, 9, 8, 1, 3, 3, 0, 0);

        return TexturedModelData.of(data, 128, 128);
    }

    /* ------------------------------------------------------------------ */
    /*  5. 1898 H.G. Wells tripod                                          */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData book() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-6, -5, -6, 12, 10, 12),
                ModelTransform.pivot(0, 4, 0));
        root.addChild("dome", ModelPartBuilder.create()
                        .cuboid(-9, -3, -9, 18, 4, 18)
                        .cuboid(-6, -4, -6, 12, 4, 12),
                ModelTransform.pivot(0, -1, 0));
        glow(root, "lens", 0, 6, -7, 4, 4, 2);

        root.addChild("arm_l", ModelPartBuilder.create()
                        .cuboid(-2, 0, -2, 3, 8, 3)
                        .cuboid(-1, 8, -1, 2, 5, 2),
                ModelTransform.pivot(-5, 9, 2));
        root.addChild("arm_r", ModelPartBuilder.create()
                        .cuboid(-1, 0, -2, 3, 8, 3)
                        .cuboid(-1, 8, -1, 2, 5, 2),
                ModelTransform.pivot(5, 9, 2));

        leg(root, "leg_fl", -5, 4, -4, 2, 10, 10, -1, 0);
        leg(root, "leg_fr", 5, 4, -4, 2, 10, 10, 1, 0);
        leg(root, "leg_b", 0, 4, 6, 2, 10, 10, 0, 1);

        return TexturedModelData.of(data, 128, 128);
    }

    /* ------------------------------------------------------------------ */
    /*  6. Asylum "War of the Worlds" 2005 six legged walker               */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData asylum() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-7, -5, -12, 14, 10, 24)
                        .cuboid(-5, -8, -9, 10, 3, 18),
                ModelTransform.pivot(0, 6, 0));
        glow(root, "lens", 0, 10, -13, 6, 6, 3);

        root.addChild("spike1", ModelPartBuilder.create().cuboid(-1, -4, -1, 2, 4, 2), ModelTransform.pivot(-4, 1, -6));
        root.addChild("spike2", ModelPartBuilder.create().cuboid(-1, -4, -1, 2, 4, 2), ModelTransform.pivot(4, 1, -6));
        root.addChild("spike3", ModelPartBuilder.create().cuboid(-1, -5, -1, 2, 5, 2), ModelTransform.pivot(0, 1, 0));
        root.addChild("spike4", ModelPartBuilder.create().cuboid(-1, -4, -1, 2, 4, 2), ModelTransform.pivot(-4, 1, 6));
        root.addChild("spike5", ModelPartBuilder.create().cuboid(-1, -4, -1, 2, 4, 2), ModelTransform.pivot(4, 1, 6));

        leg(root, "leg_l1", -7, 11, -8, 1, 6, 7, -1, 0);
        leg(root, "leg_l2", -7, 11, 0, 1, 6, 7, -1, 0);
        leg(root, "leg_l3", -7, 11, 8, 1, 6, 7, -1, 0);
        leg(root, "leg_r1", 7, 11, -8, 1, 6, 7, 1, 0);
        leg(root, "leg_r2", 7, 11, 0, 1, 6, 7, 1, 0);
        leg(root, "leg_r3", 7, 11, 8, 1, 6, 7, 1, 0);

        return TexturedModelData.of(data, 128, 128);
    }

    /* ------------------------------------------------------------------ */
    /*  7. War of the Worlds: Goliath                                      */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData goliath() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-8, -6, -8, 16, 12, 16),
                ModelTransform.pivot(0, 4, 0));
        root.addChild("dome", ModelPartBuilder.create()
                        .cuboid(-6, -4, -6, 12, 4, 12)
                        .cuboid(-4, -5, -4, 8, 5, 8),
                ModelTransform.pivot(0, -2, 0));

        root.addChild("head", ModelPartBuilder.create()
                        .cuboid(-4, -4, -3, 8, 8, 4),
                ModelTransform.pivot(0, 4, -8));
        glow(root, "lens", 0, 4, -11, 4, 4, 2);

        root.addChild("cannon", ModelPartBuilder.create()
                        .cuboid(-2, -2, -11, 4, 4, 11),
                ModelTransform.pivot(0, 8, -9));

        root.addChild("plate_l", ModelPartBuilder.create()
                        .cuboid(-3, -4, -6, 3, 8, 12),
                ModelTransform.pivot(-8, 2, -2));
        root.addChild("plate_r", ModelPartBuilder.create()
                        .cuboid(0, -4, -6, 3, 8, 12),
                ModelTransform.pivot(8, 2, -2));

        leg(root, "leg_fl", -7, 8, -5, 2, 8, 8, -1, 0);
        leg(root, "leg_fr", 7, 8, -5, 2, 8, 8, 1, 0);
        leg(root, "leg_b", 0, 8, 7, 2, 8, 8, 0, 1);

        return TexturedModelData.of(data, 128, 128);
    }

    /* ------------------------------------------------------------------ */
    /*  8. The Great Martian War — Martian Heron                           */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData heron() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-4, -6, -7, 8, 12, 14),
                ModelTransform.pivot(0, 2, 0));
        root.addChild("neck", ModelPartBuilder.create()
                        .cuboid(-2, -8, -2, 4, 8, 4),
                ModelTransform.pivot(0, -4, -4));
        root.addChild("head", ModelPartBuilder.create()
                        .cuboid(-2, -2, -6, 4, 4, 8),
                ModelTransform.pivot(0, -12, -6));
        glow(root, "lens", 0, -12, -13, 4, 4, 2);

        root.addChild("arm_l", ModelPartBuilder.create().cuboid(-1, 0, -1, 2, 6, 2), ModelTransform.pivot(-3, 8, 0));
        root.addChild("arm_r", ModelPartBuilder.create().cuboid(-1, 0, -1, 2, 6, 2), ModelTransform.pivot(3, 8, 0));

        leg(root, "leg_fl", -3, 8, -2, 2, 8, 8, -1, 0);
        leg(root, "leg_fr", 3, 8, -2, 2, 8, 8, 1, 0);
        leg(root, "leg_b", 0, 8, 4, 2, 8, 8, 0, 1);

        return TexturedModelData.of(data, 128, 128);
    }

    /* ------------------------------------------------------------------ */
    /*  9. The Great Martian War — Martian Spider                          */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData spider() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-4, -3, -6, 8, 6, 12),
                ModelTransform.pivot(0, 8, 0));
        root.addChild("head", ModelPartBuilder.create()
                        .cuboid(-2, -2, -2, 4, 4, 3),
                ModelTransform.pivot(0, 9, -6));
        glow(root, "lens", 0, 9, -9, 4, 4, 2);

        int[] zOffsets = {-5, -2, 1, 4};
        for (int i = 0; i < zOffsets.length; i++) {
            leg(root, "leg_l" + (i + 1), -4, 10, zOffsets[i], 1, 6, 8, -1, 0);
            leg(root, "leg_r" + (i + 1), 4, 10, zOffsets[i], 1, 6, 8, 1, 0);
        }

        return TexturedModelData.of(data, 128, 128);
    }

    /* ------------------------------------------------------------------ */
    /*  10. The Great Martian War — Martian Louse                          */
    /* ------------------------------------------------------------------ */

    public static TexturedModelData louse() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .cuboid(-4, -3, -6, 8, 6, 12),
                ModelTransform.pivot(0, 18, 0));
        root.addChild("head", ModelPartBuilder.create()
                        .cuboid(-2, -2, -2, 4, 4, 3),
                ModelTransform.pivot(0, 19, -6));
        glow(root, "lens", 0, 19, -9, 4, 4, 2);

        int[] zOffsets = {-4, 0, 4};
        for (int i = 0; i < zOffsets.length; i++) {
            leg(root, "leg_l" + (i + 1), -4, 21, zOffsets[i], 1, 2, 1, 0, 0);
            leg(root, "leg_r" + (i + 1), 4, 21, zOffsets[i], 1, 2, 1, 0, 0);
        }

        return TexturedModelData.of(data, 128, 128);
    }
}
