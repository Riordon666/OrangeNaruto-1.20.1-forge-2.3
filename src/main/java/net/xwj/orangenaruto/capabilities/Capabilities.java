package net.xwj.orangenaruto.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xwj.orangenaruto.OrangeNaruto;

@Mod.EventBusSubscriber(modid = OrangeNaruto.MODID)
public class Capabilities {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerTeleportPosProvider.PLAYER_TELEPORT_POS).isPresent()) {
                event.addCapability(new ResourceLocation(OrangeNaruto.MODID, "properties"), new PlayerTeleportPosProvider());
            }
        }
    }
}
