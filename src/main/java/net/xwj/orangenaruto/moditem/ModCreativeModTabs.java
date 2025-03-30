package net.xwj.orangenaruto.moditem;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.xwj.orangenaruto.modblock.ModBlocks;
import net.xwj.orangenaruto.OrangeNaruto;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OrangeNaruto.MODID);


    public static final RegistryObject<CreativeModeTab> ORANGE_TAB = CREATIVE_MODE_TABS.register("orange_tab",
            () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.ORANGE_GEMSTONE.get().getDefaultInstance())
            .title(Component.translatable("橙宝石类"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.ORANGE_GEMSTONE.get());
                output.accept(ModItems.RED_GEMSTONE.get());

                output.accept(ModBlocks.ORANGE_GEMSTONE_BLOCK_ITEM.get());
                output.accept(ModBlocks.RED_ORE_ITEM.get());
                output.accept(ModBlocks.RED_BLOCK_ITEM.get());

                output.accept(ModItems.ORANGE_SWORD.get());
                output.accept(ModItems.ORANGE_AXE.get());
                output.accept(ModItems.ORANGE_SHOVEL.get());
                output.accept(ModItems.ORANGE_HOE.get());
                output.accept(ModItems.ORANGE_PICKAXE.get());
                output.accept(ModItems.METAL_DETECTOR.get());
                output.accept(ModItems.CHEST_DETECTOR.get());
                output.accept(ModItems.ORANGE_HELMET.get());
                output.accept(ModItems.ORANGE_CHESTPLATE.get());
                output.accept(ModItems.ORANGE_LEGGINGS.get());
                output.accept(ModItems.ORANGE_BOOTS.get());
                output.accept(ModItems.SALAD.get());
                output.accept(ModItems.WOJU.get());
                output.accept(ModItems.FANQIE.get());
                output.accept(ModItems.HUANGGUA.get());
                output.accept(ModItems.BOCAI.get());
                output.accept(ModItems.PLATE.get());
                output.accept(ModItems.FLYING_THUNDER.get());
                // 将物品加入该物品栏内
            }).build());


    public static RegistryObject<CreativeModeTab> NINJA_WEAPONS = CREATIVE_MODE_TABS.register("orange_naruto",
            ()->CreativeModeTab.builder()
            .icon(() -> ModItems.KUNAI.get().getDefaultInstance())
                    .title(Component.translatable("橙宝石_火影忍者"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.KUNAI.get());
                output.accept(ModItems.SENBON.get());
                output.accept(ModItems.EXPLOSIVE_KUNAI.get());
                output.accept(ModItems.SHURIKEN.get());
                output.accept(ModBlocks.ITEM_PAPER_BOMB.get());
                output.accept(ModItems.KATANA.get());
                output.accept(ModItems.RED_ANBU_MASK.get());
                output.accept(ModItems.YELLOW_ANBU_MASK.get());
                output.accept(ModItems.GREEN_ANBU_MASK.get());
                output.accept(ModItems.BLUE_ANBU_MASK.get());
                output.accept(ModItems.MIST_ANBU_MASK.get());
                output.accept(ModItems.FLAK_JACKET_NEW.get());
                output.accept(ModItems.FLAK_JACKET.get());
                output.accept(ModItems.ANBU_ARMOR.get());
                output.accept(ModItems.AKATSUKI_CLOAK.get());
                output.accept(ModItems.HEADBAND_BLUE.get());
                output.accept(ModItems.HEADBAND_BLACK.get());
                output.accept(ModItems.HEADBAND_RED.get());
                output.accept(ModItems.HEADBAND_CUSTARD.get());
                output.accept(ModItems.HEADBAND_LEAF.get());
                output.accept(ModItems.HEADBAND_LEAF_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_LEAF_BLACK.get());
                output.accept(ModItems.HEADBAND_LEAF_BLACK_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_ROCK.get());
                output.accept(ModItems.HEADBAND_ROCK_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_SAND.get());
                output.accept(ModItems.HEADBAND_SAND_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_SOUND.get());
                output.accept(ModItems.HEADBAND_SOUND_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_MIST.get());
                output.accept(ModItems.HEADBAND_MIST_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_WATERFALL.get());
                output.accept(ModItems.HEADBAND_WATERFALL_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_CLOUD.get());
                output.accept(ModItems.HEADBAND_CLOUD_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_RAIN.get());
                output.accept(ModItems.HEADBAND_RAIN_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_GRASS.get());
                output.accept(ModItems.HEADBAND_GRASS_SCRATCHED.get());
                output.accept(ModItems.HEADBAND_PRIDE.get());
                output.accept(ModItems.HEADBAND_YOUTUBE.get());
                output.accept(ModItems.HEADBAND_LAVA.get());
                output.accept(ModItems.FABRIC.get());
                output.accept(ModItems.FABRIC_REINFORCED.get());
                output.accept(ModItems.FABRIC_REINFORCED_GREEN.get());
                output.accept(ModItems.FABRIC_REINFORCED_BLACK.get());
                output.accept(ModItems.ARMOR_PLATE.get());
                output.accept(ModItems.ARMOR_PLATE_GREEN.get());
            }).build());

    @SubscribeEvent
    public static void creativeModeBuildContent(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.LONELY_MARCH.get());
        } else if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.ITEM_BONSAI_TREE.get());
        }
    }

    
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
