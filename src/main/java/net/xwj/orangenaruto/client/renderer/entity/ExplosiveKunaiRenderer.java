package net.xwj.orangenaruto.client.renderer.entity;

import net.xwj.orangenaruto.entity.projectile.KunaiEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;


public class ExplosiveKunaiRenderer extends ArrowRenderer<KunaiEntity> {
   public static final ResourceLocation RES_ARROW = new ResourceLocation("orangenaruto", "textures/entity/projectiles/explosive_kunai.png");

   public ExplosiveKunaiRenderer(EntityRendererProvider.Context manager) {
      super(manager);
   }

   /**
    * Returns the location of an entity's texture.
    */
   @Override
   public ResourceLocation getTextureLocation(KunaiEntity p_110775_1_) {
      return RES_ARROW;
   }

}
