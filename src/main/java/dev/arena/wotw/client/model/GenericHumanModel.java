package dev.arena.wotw.client.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

/**
 * The rig for the human side: a soldier walks with two legs, a tank rolls, a fighter banks and a
 * warship ploughs through the swell. All of it is driven by part names, so the four models share
 * one class.
 */
public class GenericHumanModel<T extends LivingEntity> extends EntityModel<T> {

    private final ModelPart root;
    private final ModelPart legLeft;
    private final ModelPart legRight;
    private final ModelPart armLeft;
    private final ModelPart armRight;
    private final ModelPart turret;
    private final ModelPart head;

    private final float legPitch;
    private final float turretYawBase;
    private final float rootPivotY;

    public GenericHumanModel(ModelPart root) {
        this.root = root;
        this.legLeft = child(root, "leg_l");
        this.legRight = child(root, "leg_r");
        this.armLeft = child(root, "arm_l");
        this.armRight = child(root, "arm_r");
        this.turret = child(root, "turret");
        this.head = child(root, "head");
        this.legPitch = this.legLeft == null ? 0.0F : this.legLeft.pitch;
        this.turretYawBase = this.turret == null ? 0.0F : this.turret.yaw;
        this.rootPivotY = root.pivotY;
    }

    private static ModelPart child(ModelPart parent, String name) {
        return parent.hasChild(name) ? parent.getChild(name) : null;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress,
                          float headYaw, float headPitch) {
        float walk = MathHelper.clamp(limbDistance, 0.0F, 1.0F);
        if (this.legLeft != null) {
            this.legLeft.pitch = this.legPitch + MathHelper.cos(limbAngle) * 0.7F * walk;
            this.legLeft.roll = MathHelper.sin(limbAngle) * 0.06F * walk;
        }
        if (this.legRight != null) {
            this.legRight.pitch = this.legPitch + MathHelper.cos(limbAngle + (float) Math.PI) * 0.7F * walk;
            this.legRight.roll = MathHelper.sin(limbAngle + (float) Math.PI) * 0.06F * walk;
        }
        if (this.armLeft != null) {
            this.armLeft.pitch = MathHelper.cos(limbAngle + (float) Math.PI) * 0.5F * walk;
        }
        if (this.armRight != null) {
            this.armRight.pitch = MathHelper.cos(limbAngle) * 0.5F * walk;
        }
        if (this.head != null) {
            this.head.yaw = headYaw * 0.017453292F;
            this.head.pitch = headPitch * 0.017453292F;
        }
        if (this.turret != null) {
            this.turret.yaw = this.turretYawBase + headYaw * 0.017453292F * 0.6F;
        }
        this.root.pivotY = this.rootPivotY + MathHelper.sin(animationProgress * 0.08F) * 0.02F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
