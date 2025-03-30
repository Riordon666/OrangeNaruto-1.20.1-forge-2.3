package net.xwj.orangenaruto;

import com.mojang.logging.LogUtils;
import net.xwj.orangelib.capabilitysync.capabilitysync.RegisterCapabilitySyncEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.xwj.orangenaruto.abilities.NarutoAbilities;
import net.xwj.orangenaruto.capabilities.NinjaCapabilityHandler;
import net.xwj.orangenaruto.capabilities.NinjaData;
import net.xwj.orangenaruto.client.keybinds.NarutoKeyHandler;
import net.xwj.orangenaruto.commands.NarutoCommands;
import net.xwj.orangenaruto.config.NarutoConfig;
import net.xwj.orangenaruto.entity.NarutoDataSerialisers;
import net.xwj.orangenaruto.entity.NarutoEntities;
import net.xwj.orangenaruto.gameevents.NarutoGameEvents;
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
import net.xwj.orangenaruto.moditem.NarutoDispenseItemBehavior;
import net.xwj.orangenaruto.network.PacketHandler;
import net.xwj.orangenaruto.registry.NarutoRegistries;
import net.xwj.orangenaruto.sounds.ModSounds;
import org.slf4j.Logger;
import net.xwj.orangenaruto.registry.ModEntities;
import net.xwj.orangenaruto.client.model.FlyingThunderGodModel;
import net.xwj.orangenaruto.registry.ModEntityRenderers;
import net.xwj.orangenaruto.networking.ModMessages;
import net.xwj.orangenaruto.capabilities.PlayerTeleportPos;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.xwj.orangenaruto.worldgen.dimension.ModDimensions;

import net.xwj.orangenaruto.client.gui.NarutoInGameGUI;

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
        ModDimensions.register();// 注册维度

        // 注册网络包
        ModMessages.register();

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        ModLoadingContext loadingContext = ModLoadingContext.get();
        loadingContext.registerConfig(ModConfig.Type.COMMON, NarutoConfig.MOD_CONFIG, "naruto-mod.toml");

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setup);
        eventBus.addListener(this::registerCapabilitySync);
        eventBus.addListener(NarutoRegistries::init);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            eventBus.addListener(NarutoKeyHandler::registerKeyBinds);
        }

        NarutoDataSerialisers.register(eventBus);
        NarutoEntities.register(eventBus);
        NarutoAbilities.register(eventBus);
        NarutoGameEvents.register(eventBus);

        DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> NarutoInGameGUI::new);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        //NarutoRenderEvents.registerRenderTypes();
        //NarutoWorldRenderEvents.registerEvents();
    }
    private void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
        NarutoDispenseItemBehavior.register();
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

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        NarutoCommands.register(event.getDispatcher());
    }

    public void registerCapabilitySync(RegisterCapabilitySyncEvent event) {
        event.registerPlayerCap(new ResourceLocation(OrangeNaruto.MODID, "ninja_data"), NinjaCapabilityHandler.NINJA_DATA, NinjaData.class);
    }
}