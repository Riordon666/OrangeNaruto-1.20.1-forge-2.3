package net.xwj.test.registry;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.xwj.test.client.render.FlyingThunderGodRenderer;
import net.xwj.test.entity.custom.FlyingThunderGodEntity;

public class ModEntityRenderers {
    public static void register() {
        EntityRenderers.register(ModEntities.FLYING_THUNDER_GOD.get(), FlyingThunderGodRenderer::new);
    }
}