package net.xwj.test.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.xwj.test.modblock.ModBlocks;
import net.xwj.test.test;
import net.xwj.test.util.ModTags;
import org.dom4j.rule.Rule;

import java.util.List;

public class ModConfigureFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_RED_ORE_KEY = registerKey("red_ore");


    public static void boostrap(BootstapContext<ConfiguredFeature<?,?>> context){
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldRedOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.RED_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.RED_ORE.get().defaultBlockState()));

        register(context,OVERWORLD_RED_ORE_KEY,Feature.ORE,new OreConfiguration(overworldRedOres,9));


    }

    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(test.MODID, name));

    }
    private static <FC extends FeatureConfiguration,F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context,
                                                                                         ResourceKey<ConfiguredFeature<?,?>> key,F feature,FC configuration){
        context.register(key,new ConfiguredFeature<>(feature, configuration));
    }

}
