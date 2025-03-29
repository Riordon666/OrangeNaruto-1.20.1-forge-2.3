package net.xwj.orangenaruto.worldgen.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.xwj.orangenaruto.OrangeNaruto;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = OrangeNaruto.MODID, bus = Bus.MOD)
public class ModDimensions {
    public static final ResourceKey<Level> RED_DIMENSION_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(OrangeNaruto.MODID, "red_dimension"));

    public static final ResourceKey<DimensionType> RED_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(OrangeNaruto.MODID, "red_dimension_type"));

    public static void register() {
        System.out.println("Registering ModDimensions for " + OrangeNaruto.MODID);
    }
}