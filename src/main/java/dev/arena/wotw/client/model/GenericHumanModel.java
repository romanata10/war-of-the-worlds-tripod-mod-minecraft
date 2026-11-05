package dev.arena.wotw.client.model;

import net.minecraft.client.model.geom.ModelPart;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.Mth;

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
        this.legPitch = this.legLeft == null ? 0.0F : this.legLeft.xRot;
        this.turretYawBase = this.turret == null ? 0.0F : this.turret.yRot;
        this.rootPivotY = root.y;
    }

    private static ModelPart child(ModelPart parent, String name) {
        return parent.hasChild(name) ? parent.getChild(name) : null;
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress,
                          float yHeadRot, float headPitch) {
        float walk = Mth.clamp(limbDistance, 0.0F, 1.0F);
        if (this.legLeft != null) {
            this.legLeft.xRot = this.legPitch + Mth.cos(limbAngle) * 0.7F * walk;
            this.legLeft.zRot = Mth.sin(limbAngle) * 0.06F * walk;
        }
        if (this.legRight != null) {
            this.legRight.xRot = this.legPitch + Mth.cos(limbAngle + (float) Math.PI) * 0.7F * walk;
            this.legRight.zRot = Mth.sin(limbAngle + (float) Math.PI) * 0.06F * walk;
        }
        if (this.armLeft != null) {
            this.armLeft.xRot = Mth.cos(limbAngle + (float) Math.PI) * 0.5F * walk;
        }
        if (this.armRight != null) {
            this.armRight.xRot = Mth.cos(limbAngle) * 0.5F * walk;
        }
        if (this.head != null) {
            this.head.yRot = yHeadRot * 0.017453292F;
            this.head.xRot = headPitch * 0.017453292F;
        }
        if (this.turret != null) {
            this.turret.yRot = this.turretYawBase + yHeadRot * 0.017453292F * 0.6F;
        }
        this.root.y = this.rootPivotY + Mth.sin(animationProgress * 0.08F) * 0.02F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
