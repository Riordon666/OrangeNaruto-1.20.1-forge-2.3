package net.xwj.test;

import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.xwj.test.modblock.ModBlocks;
import net.xwj.test.moditem.ModCreativeModTabs;
import net.xwj.test.moditem.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.xwj.test.sound.ModSounds;
import org.slf4j.Logger;
import net.xwj.test.registry.ModEntities;
import net.xwj.test.client.model.FlyingThunderGodModel;
import net.xwj.test.registry.ModEntityRenderers;
import net.xwj.test.networking.ModMessages;
import net.xwj.test.capability.PlayerTeleportPos;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

@Mod(test.MODID)
public class test {
    public static final String MODID = "test";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static KeyMapping teleportKey;

    public test() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEntities.register(modEventBus);
        // 注册网络包
        ModMessages.register();

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerTeleportPos.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(ModBlocks.ORANGE_GEMSTONE_BLOCK_ITEM);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            // 注册实体渲染器
            ModEntityRenderers.register();
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            // 注册模型层
            event.registerLayerDefinition(FlyingThunderGodModel.LAYER_LOCATION,
                    FlyingThunderGodModel::createBodyLayer);
        }
    }
}