package net.xwj.orangenaruto.client.gui;

import net.xwj.orangenaruto.OrangeNaruto;
import net.minecraft.resources.ResourceLocation;

public class BarDesigns {
    private static final ResourceLocation DEFAULTTEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/gui/chakrabars/defaultbar.png");
    private static final ResourceLocation KATANATEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/gui/chakrabars/katana.png");
    private static final ResourceLocation KUBITEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/gui/chakrabars/kubi.png");
    private static final ResourceLocation KUNAITEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/gui/chakrabars/kunai.png");
    private static final ResourceLocation LAMPSHADETEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/gui/chakrabars/lampshade.png");
    private static final ResourceLocation SCEPTERTEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/gui/chakrabars/scepter.png");
    private static final ResourceLocation SCROLLTEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/gui/chakrabars/scroll.png");
    private static final ResourceLocation UNNAMEDTEXTURE = new ResourceLocation(OrangeNaruto.MODID, "textures/gui/chakrabars/unnamedbar.png");

    public enum BarInfo {

        DEFAULT(DEFAULTTEXTURE, 86,8),

        KATANA(KATANATEXTURE, 94,1),

        KUBI(KUBITEXTURE, 86,11),

        KUNAI(KUNAITEXTURE, 86,9),

        LAMP(LAMPSHADETEXTURE, 86,8),

        SCEPTER(SCEPTERTEXTURE, 86,9),

        SCROLL(SCROLLTEXTURE, 86,8),

        UNNAMED(UNNAMEDTEXTURE, 74,23),
        ;

        public final ResourceLocation texture;
        public final int width;
        public final int offset;

        BarInfo(ResourceLocation texture, int width, int offset) {

            this.texture = texture;
            this.width = width;
            this.offset = offset;
        }
    }
}
