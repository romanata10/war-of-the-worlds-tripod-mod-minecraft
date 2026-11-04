package dev.arena.wotw.client.render;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.client.model.GenericHumanModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.resources.ResourceLocation;

/** Draws the human army: soldiers, tanks, fighters and warships. */
public class GenericHumanRenderer<T extends PathfinderMob>
        extends MobRenderer<T, GenericHumanModel<T>> {

    private final ResourceLocation texture;
    private final float renderScale;

    public GenericHumanRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer,
                                String texturePath, float renderScale, float shadowRadius) {
        super(context, new GenericHumanModel<>(context.bakeLayer(layer)), shadowRadius);
        this.texture = WotwMod.id("textures/entity/" + texturePath + ".png");
        this.renderScale = renderScale;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return this.texture;
    }

    @Override
    public void scale(T entity, PoseStack matrices, float amount) {
        matrices.scale(this.renderScale, this.renderScale, this.renderScale);
    }
}
