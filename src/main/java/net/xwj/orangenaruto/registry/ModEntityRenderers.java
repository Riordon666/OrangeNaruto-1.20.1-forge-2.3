package net.xwj.orangenaruto.registry;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.xwj.orangenaruto.client.render.FlyingThunderGodRenderer;

public class ModEntityRenderers {
    public static void register() {
        EntityRenderers.register(ModEntities.FLYING_THUNDER_GOD.get(), FlyingThunderGodRenderer::new);
    }
}