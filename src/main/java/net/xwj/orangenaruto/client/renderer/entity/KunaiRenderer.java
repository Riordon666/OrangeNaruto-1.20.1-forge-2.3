package net.xwj.orangenaruto.client.renderer.entity;

import net.xwj.orangenaruto.entity.projectile.KunaiEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;


public class KunaiRenderer extends ArrowRenderer<KunaiEntity> {
   public static final ResourceLocation RES_ARROW = new ResourceLocation("orangenaruto", "textures/entity/projectiles/kunai.png");

   public KunaiRenderer(EntityRendererProvider.Context manager) {
      super(manager);
   }

   /**
    * Returns the location of an entity's texture.
    */
   @Override
   public ResourceLocation getTextureLocation(KunaiEntity entity) {
      return RES_ARROW;
   }

}
