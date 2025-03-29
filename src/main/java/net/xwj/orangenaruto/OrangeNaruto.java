package net.xwj.orangenaruto;

import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.xwj.orangenaruto.modblock.ModBlocks;
import net.xwj.orangenaruto.moditem.ModCreativeModTabs;
import net.xwj.orangenaruto.moditem.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
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
import net.xwj.orangenaruto.sound.ModSounds;
import org.slf4j.Logger;
import net.xwj.orangenaruto.registry.ModEntities;
import net.xwj.orangenaruto.client.model.FlyingThunderGodModel;
import net.xwj.orangenaruto.registry.ModEntityRenderers;
import net.xwj.orangenaruto.networking.ModMessages;
import net.xwj.orangenaruto.capability.PlayerTeleportPos;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.xwj.orangenaruto.worldgen.dimension.ModDimensions;

@Mod(OrangeNaruto.MODID)
public class OrangeNaruto {
    public static final String MODID = "orangenaruto";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static KeyMapping teleportKey;

    public OrangeNaruto() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModDimensions.register();  // 注册维度

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
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.RED_BLOCK_ITEM);
            event.accept(ModBlocks.ORANGE_GEMSTONE_BLOCK_ITEM);
        }
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
            ModEntityRenderers.register();
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(FlyingThunderGodModel.LAYER_LOCATION,
                    FlyingThunderGodModel::createBodyLayer);
        }
    }
}