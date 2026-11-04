package dev.arena.wotw.client.model;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.client.model.geom.ModelPart;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

/**
 * One animation rig for every machine in the mod.
 *
 * <p>It discovers the parts by name, so a model with three legs walks with a three phase gait and
 * a model with six or eight legs walks with alternating tripod groups. Wings flap, tentacles sway,
 * the head tracks the target and everything shivers while an ability is charging.</p>
 */
public class GenericTripodModel<T extends AbstractTripodEntity> extends EntityModel<T> {

    /**
     * `ModelPart#children` is private in 1.20.1, so the rig probes the names our generated models
     * use instead of walking the children map.
     */
    private static final String[] ANIMATED_PARTS = {
            "leg_fl", "leg_fr", "leg_b", "leg_l", "leg_r",
            "leg0", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7",
            "wing_l", "wing_r",
            "arm0", "arm1", "arm2", "arm_l", "arm_r",
            "body", "head"
    };

    private final ModelPart modelRoot;
    private final ModelPart body;
    private final ModelPart head;

    private final ModelPart[] legs;
    private final float[] legPitchBase;
    private final float[] legRollBase;

    private final ModelPart[] wings;
    private final float[] wingRollBase;

    private final ModelPart[] arms;
    private final float[] armPitchBase;
    private final float[] armRollBase;

    private final float rootPivotY;
    private final float bodyPitchBase;

    public GenericTripodModel(ModelPart root) {
        this.modelRoot = root;

        List<ModelPart> legList = new ArrayList<>();
        List<ModelPart> wingList = new ArrayList<>();
        List<ModelPart> armList = new ArrayList<>();
        ModelPart bodyPart = null;
        ModelPart headPart = null;

        for (String name : ANIMATED_PARTS) {
            if (!root.hasChild(name)) {
                continue;
            }
            ModelPart part = root.getChild(name);
            if (name.startsWith("leg")) {
                legList.add(part);
            } else if (name.startsWith("wing")) {
                wingList.add(part);
            } else if (name.startsWith("arm")) {
                armList.add(part);
            } else if (name.equals("body")) {
                bodyPart = part;
            } else if (name.equals("head")) {
                headPart = part;
            }
        }

        this.body = bodyPart;
        this.head = headPart;

        this.legs = legList.toArray(new ModelPart[0]);
        this.legPitchBase = new float[this.legs.length];
        this.legRollBase = new float[this.legs.length];
        for (int i = 0; i < this.legs.length; i++) {
            this.legPitchBase[i] = this.legs[i].xRot;
            this.legRollBase[i] = this.legs[i].zRot;
        }

        this.wings = wingList.toArray(new ModelPart[0]);
        this.wingRollBase = new float[this.wings.length];
        for (int i = 0; i < this.wings.length; i++) {
            this.wingRollBase[i] = this.wings[i].zRot;
        }

        this.arms = armList.toArray(new ModelPart[0]);
        this.armPitchBase = new float[this.arms.length];
        this.armRollBase = new float[this.arms.length];
        for (int i = 0; i < this.arms.length; i++) {
            this.armPitchBase[i] = this.arms[i].xRot;
            this.armRollBase[i] = this.arms[i].zRot;
        }

        this.rootPivotY = root.y;
        this.bodyPitchBase = this.body == null ? 0.0F : this.body.xRot;
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress,
                          float yHeadRot, float headPitch) {
        float walk = Mth.clamp(limbDistance, 0.0F, 1.0F);
        float charge = entity.isAbilityCharging() ? 1.0F : 0.0F;
        float throttle = entity.isFlyer() ? 1.4F : 1.0F;

        /* ---- legs: three phase gait for tripods, alternating for 6/8 legs ---- */
        int legCount = this.legs.length;
        for (int i = 0; i < legCount; i++) {
            ModelPart leg = this.legs[i];
            float phase;
            if (legCount <= 3) {
                phase = limbAngle * throttle + i * (float) (Math.PI * 2.0D / 3.0D);
            } else {
                phase = limbAngle * throttle + (i % 2 == 0 ? 0.0F : (float) Math.PI);
            }
            float swing = Mth.cos(phase) * 0.45F * walk;
            float idle = Mth.sin(animationProgress * 0.06F + i * 0.7F) * 0.02F;
            float shiver = Mth.sin(animationProgress * 1.9F + i) * 0.01F * charge;
            leg.xRot = this.legPitchBase[i] + swing + idle + shiver;
            leg.zRot = this.legRollBase[i] + Mth.sin(phase) * 0.1F * walk;
        }

        /* ---- wings ---- */
        for (int i = 0; i < this.wings.length; i++) {
            float sign = i % 2 == 0 ? 1.0F : -1.0F;
            this.wings[i].zRot = this.wingRollBase[i]
                    + sign * Mth.sin(animationProgress * 0.32F) * 0.32F;
        }

        /* ---- arms / tentacles ---- */
        for (int i = 0; i < this.arms.length; i++) {
            this.arms[i].xRot = this.armPitchBase[i]
                    + Mth.sin(animationProgress * 0.09F + i) * 0.14F;
            this.arms[i].zRot = this.armRollBase[i]
                    + Mth.cos(animationProgress * 0.07F + i) * 0.12F;
        }

        /* ---- hull bob and sway ---- */
        float bob = Mth.sin(animationProgress * 0.05F) * (entity.isFlyer() ? 0.8F : 0.25F);
        this.modelRoot.y = this.rootPivotY + bob;
        if (this.body != null) {
            this.body.xRot = this.bodyPitchBase + Mth.sin(animationProgress * 0.045F) * 0.02F;
        }

        /* ---- head tracks the target ---- */
        if (this.head != null) {
            this.head.yRot = yHeadRot * 0.017453292F;
            this.head.xRot = headPitch * 0.017453292F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        this.modelRoot.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
