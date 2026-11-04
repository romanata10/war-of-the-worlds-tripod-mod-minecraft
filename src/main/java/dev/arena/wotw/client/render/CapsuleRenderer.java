package dev.arena.wotw.client.render;

import dev.arena.wotw.entity.CapsuleEntity;
import dev.arena.wotw.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

/**
 * Draws the falling capsule with the cylinder block model blown up three times, so the thing that
 * comes down out of the sky really is a giant cylinder and not a particle effect.
 */
public class CapsuleRenderer extends EntityRenderer<CapsuleEntity> {

    private static final float SCALE = 3.0F;
    private static final ResourceLocation BLOCK_ATLAS = new ResourceLocation("textures/atlas/blocks.png");

    private final BlockRenderManager blockRenderManager;

    public CapsuleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderManager = context.getBlockRenderDispatcher();
        this.shadowRadius = 3.0F;
    }

    @Override
    public void render(CapsuleEntity entity, float yaw, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light) {
        BlockState state = ModBlocks.MARTIAN_CYLINDER.defaultBlockState();
        matrices.pushPose();
        matrices.scale(SCALE, SCALE, SCALE);
        matrices.translate(-0.5D, 0.0D, -0.5D);
        this.blockRenderManager.renderSingleBlock(state, matrices, vertexConsumers, light,
                OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY,
                net.minecraft.client.renderer.RenderType.cutout());
        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(CapsuleEntity entity) {
        return BLOCK_ATLAS;
    }
}
