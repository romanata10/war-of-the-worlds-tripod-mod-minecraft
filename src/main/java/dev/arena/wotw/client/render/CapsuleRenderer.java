package dev.arena.wotw.client.render;

import dev.arena.wotw.entity.CapsuleEntity;
import dev.arena.wotw.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * Draws the falling capsule with the cylinder block model blown up three times, so the thing that
 * comes down out of the sky really is a giant cylinder and not a particle effect.
 */
public class CapsuleRenderer extends EntityRenderer<CapsuleEntity> {

    private static final float SCALE = 3.0F;
    private static final Identifier BLOCK_ATLAS = new Identifier("textures/atlas/blocks.png");

    private final BlockRenderManager blockRenderManager;

    public CapsuleRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.blockRenderManager = context.getBlockRenderManager();
        this.shadowRadius = 3.0F;
    }

    @Override
    public void render(CapsuleEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        BlockState state = ModBlocks.MARTIAN_CYLINDER.getDefaultState();
        matrices.push();
        matrices.scale(SCALE, SCALE, SCALE);
        matrices.translate(-0.5D, 0.0D, -0.5D);
        this.blockRenderManager.renderBlockAsEntity(state, matrices, vertexConsumers, light,
                OverlayTexture.DEFAULT_UV);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(CapsuleEntity entity) {
        return BLOCK_ATLAS;
    }
}
