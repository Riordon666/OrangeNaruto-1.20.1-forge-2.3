package net.xwj.test.moditem;

import net.xwj.test.modblock.ModBlocks;
import net.xwj.test.test;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, test.MODID);


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
                // 将物品加入该物品栏内
            }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
