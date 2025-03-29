package net.xwj.orangenaruto.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.networking.ModMessages;
import net.xwj.orangenaruto.networking.packet.TeleportC2SPacket;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = OrangeNaruto.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(ModKeyBindings.TELEPORT_KEY.consumeClick()) {
                // 发送传送数据包到服务器
                ModMessages.sendToServer(new TeleportC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = OrangeNaruto.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBindings.TELEPORT_KEY);
        }
    }
}
