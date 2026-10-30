package dev.arena.wotw.client.render;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

/**
 * A small glowing ball for shells and lightning charges — no model file, just a lit cuboid that
 * always faces the camera and grows as it flies.
 */
public class ShellRenderer extends EntityRenderer<ShellEntity> {

    private static final Identifier SHELL_TEXTURE = new Identifier("wotw", "textures/entity/beam_heat_ray.png");
    private static final Identifier CHARGE_TEXTURE = new Identifier("wotw", "textures/entity/beam_laser.png");
    private static final int FULL_BRIGHT = 15728880;

    public ShellRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.4F;
    }

    @Override
    public void render(ShellEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-this.dispatcher.camera.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.dispatcher.camera.getPitch()));
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(
                entity.isElectricCharge() ? CHARGE_TEXTURE : SHELL_TEXTURE));
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        float size = 0.35F;
        float r = 1.0F;
        float g = entity.isElectricCharge() ? 0.95F : 1.0F;
        float b = entity.isElectricCharge() ? 1.0F : 0.55F;
        consumer.vertex(matrix, -size, -size, 0.0F).color(r, g, b, 0.9F).texture(0.0F, 1.0F)
                .overlay(OverlayTexture.DEFAULT_UV).light(FULL_BRIGHT).normal(0.0F, 0.0F, 1.0F).next();
        consumer.vertex(matrix, size, -size, 0.0F).color(r, g, b, 0.9F).texture(1.0F, 1.0F)
                .overlay(OverlayTexture.DEFAULT_UV).light(FULL_BRIGHT).normal(0.0F, 0.0F, 1.0F).next();
        consumer.vertex(matrix, size, size, 0.0F).color(r, g, b, 0.9F).texture(1.0F, 0.0F)
                .overlay(OverlayTexture.DEFAULT_UV).light(FULL_BRIGHT).normal(0.0F, 0.0F, 1.0F).next();
        consumer.vertex(matrix, -size, size, 0.0F).color(r, g, b, 0.9F).texture(0.0F, 0.0F)
                .overlay(OverlayTexture.DEFAULT_UV).light(FULL_BRIGHT).normal(0.0F, 0.0F, 1.0F).next();
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ShellEntity entity) {
        return entity.isElectricCharge() ? CHARGE_TEXTURE : SHELL_TEXTURE;
    }
}
