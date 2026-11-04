package dev.arena.wotw.client.render;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Axis;
import org.joml.Matrix4f;

/**
 * A small glowing ball for shells and lightning charges — no model file, just a lit cuboid that
 * always faces the camera and grows as it flies.
 */
public class ShellRenderer extends EntityRenderer<ShellEntity> {

    private static final ResourceLocation SHELL_TEXTURE = new ResourceLocation("wotw", "textures/entity/beam_heat_ray.png");
    private static final ResourceLocation CHARGE_TEXTURE = new ResourceLocation("wotw", "textures/entity/beam_laser.png");
    private static final int FULL_BRIGHT = 15728880;

    public ShellRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.4F;
    }

    @Override
    public void render(ShellEntity entity, float yaw, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees(-this.entityRenderDispatcher.camera.getYRot()));
        matrices.mulPose(Axis.XP.rotationDegrees(this.entityRenderDispatcher.camera.getXRot()));
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderType.entityTranslucentEmissive(
                entity.isElectricCharge() ? CHARGE_TEXTURE : SHELL_TEXTURE));
        Matrix4f matrix = matrices.last().pose();
        float size = 0.35F;
        float r = 1.0F;
        float g = entity.isElectricCharge() ? 0.95F : 1.0F;
        float b = entity.isElectricCharge() ? 1.0F : 0.55F;
        consumer.vertex(matrix, -size, -size, 0.0F).color(r, g, b, 0.9F).texture(0.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(FULL_BRIGHT).normal(0.0F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix, size, -size, 0.0F).color(r, g, b, 0.9F).texture(1.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(FULL_BRIGHT).normal(0.0F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix, size, size, 0.0F).color(r, g, b, 0.9F).texture(1.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(FULL_BRIGHT).normal(0.0F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix, -size, size, 0.0F).color(r, g, b, 0.9F).texture(0.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(FULL_BRIGHT).normal(0.0F, 0.0F, 1.0F).endVertex();
        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellEntity entity) {
        return entity.isElectricCharge() ? CHARGE_TEXTURE : SHELL_TEXTURE;
    }
}
