package dev.arena.wotw.client.model;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * One animation rig for every machine in the mod.
 *
 * <p>It discovers the parts by name, so a model with three legs walks with a three phase gait and
 * a model with six or eight legs walks with alternating tripod groups. Wings flap, tentacles sway,
 * the head tracks the target and everything shivers while an ability is charging.</p>
 */
public class GenericTripodModel<T extends AbstractTripodEntity> extends EntityModel<T> {

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

        for (Map.Entry<String, ModelPart> entry : new TreeMap<>(root.children).entrySet()) {
            String name = entry.getKey();
            ModelPart part = entry.getValue();
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
            this.legPitchBase[i] = this.legs[i].pitch;
            this.legRollBase[i] = this.legs[i].roll;
        }

        this.wings = wingList.toArray(new ModelPart[0]);
        this.wingRollBase = new float[this.wings.length];
        for (int i = 0; i < this.wings.length; i++) {
            this.wingRollBase[i] = this.wings[i].roll;
        }

        this.arms = armList.toArray(new ModelPart[0]);
        this.armPitchBase = new float[this.arms.length];
        this.armRollBase = new float[this.arms.length];
        for (int i = 0; i < this.arms.length; i++) {
            this.armPitchBase[i] = this.arms[i].pitch;
            this.armRollBase[i] = this.arms[i].roll;
        }

        this.rootPivotY = root.pivotY;
        this.bodyPitchBase = this.body == null ? 0.0F : this.body.pitch;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress,
                          float headYaw, float headPitch) {
        float walk = MathHelper.clamp(limbDistance, 0.0F, 1.0F);
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
            float swing = MathHelper.cos(phase) * 0.45F * walk;
            float idle = MathHelper.sin(animationProgress * 0.06F + i * 0.7F) * 0.02F;
            float shiver = MathHelper.sin(animationProgress * 1.9F + i) * 0.01F * charge;
            leg.pitch = this.legPitchBase[i] + swing + idle + shiver;
            leg.roll = this.legRollBase[i] + MathHelper.sin(phase) * 0.1F * walk;
        }

        /* ---- wings ---- */
        for (int i = 0; i < this.wings.length; i++) {
            float sign = i % 2 == 0 ? 1.0F : -1.0F;
            this.wings[i].roll = this.wingRollBase[i]
                    + sign * MathHelper.sin(animationProgress * 0.32F) * 0.32F;
        }

        /* ---- arms / tentacles ---- */
        for (int i = 0; i < this.arms.length; i++) {
            this.arms[i].pitch = this.armPitchBase[i]
                    + MathHelper.sin(animationProgress * 0.09F + i) * 0.14F;
            this.arms[i].roll = this.armRollBase[i]
                    + MathHelper.cos(animationProgress * 0.07F + i) * 0.12F;
        }

        /* ---- hull bob and sway ---- */
        float bob = MathHelper.sin(animationProgress * 0.05F) * (entity.isFlyer() ? 0.8F : 0.25F);
        this.modelRoot.pivotY = this.rootPivotY + bob;
        if (this.body != null) {
            this.body.pitch = this.bodyPitchBase + MathHelper.sin(animationProgress * 0.045F) * 0.02F;
        }

        /* ---- head tracks the target ---- */
        if (this.head != null) {
            this.head.yaw = headYaw * 0.017453292F;
            this.head.pitch = headPitch * 0.017453292F;
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        this.modelRoot.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
