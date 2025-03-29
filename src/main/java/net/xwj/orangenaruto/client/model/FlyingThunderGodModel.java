package net.xwj.orangenaruto.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.entity.custom.FlyingThunderGodEntity;

public class FlyingThunderGodModel extends EntityModel<FlyingThunderGodEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(OrangeNaruto.MODID, "flying_thunder_god"), "main");

    private final ModelPart kunai;

    public FlyingThunderGodModel(ModelPart root) {
        this.kunai = root.getChild("kunai");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // 创建苦无的模型
        PartDefinition kunai = partdefinition.addOrReplaceChild("kunai", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F) // 主体
                        .texOffs(4, 0)
                        .addBox(-2.0F, -6.0F, -0.5F, 4.0F, 2.0F, 1.0F) // 横杆
                        .texOffs(4, 3)
                        .addBox(-1.0F, 4.0F, -0.5F, 2.0F, 2.0F, 1.0F), // 环
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(FlyingThunderGodEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        // 不需要动画，所以这里不做任何事
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        kunai.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
