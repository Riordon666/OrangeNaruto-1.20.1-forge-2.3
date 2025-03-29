package net.xwj.orangenaruto.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.client.model.FlyingThunderGodModel;
import net.xwj.orangenaruto.entity.custom.FlyingThunderGodEntity;

public class FlyingThunderGodRenderer extends EntityRenderer<FlyingThunderGodEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/entity/flying_thunder_god.png");
    private final FlyingThunderGodModel model;
    private final ItemRenderer itemRenderer;

    public FlyingThunderGodRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FlyingThunderGodModel(context.bakeLayer(FlyingThunderGodModel.LAYER_LOCATION));
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(FlyingThunderGodEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 调整位置和大小
        poseStack.scale(1.0F, 1.0F, 1.0F);

        if (entity.isInGround()) {
            // 如果插在方块上，使用实体的固定旋转
            poseStack.translate(0.0D, 0.0D, 0.0D);
            poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        } else {
            // 如果在飞行中，使用实时计算的旋转
            poseStack.translate(0.0D, 0.25D, 0.0D);
            float yRot = entity.yRotO + (entity.getYRot() - entity.yRotO) * partialTicks;
            float xRot = entity.xRotO + (entity.getXRot() - entity.xRotO) * partialTicks;
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        }

        // 渲染模型
        this.model.renderToBuffer(poseStack, buffer.getBuffer(this.model.renderType(TEXTURE)),
                packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingThunderGodEntity entity) {
        return TEXTURE;
    }
}
