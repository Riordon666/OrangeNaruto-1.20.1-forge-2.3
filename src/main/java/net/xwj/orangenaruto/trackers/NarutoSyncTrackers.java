package net.xwj.orangenaruto.trackers;

import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.capabilities.DoubleJumpData;
import net.xwj.orangenaruto.capabilities.toggleabilitydata.ToggleAbilityData;
import net.xwj.orangelib.capabilitysync.capabilitysync.RegisterSyncTrackerTypeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OrangeNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NarutoSyncTrackers {
    @SubscribeEvent
    public static void registerSyncTrackerEvent(RegisterSyncTrackerTypeEvent event) {
        event.registerSyncTracker(ToggleAbilityData.class, new ToggleAbilityDataSyncTracker());
        event.registerSyncTracker(DoubleJumpData.class, new DoubleJumpDataSyncTracker());
    }
}
