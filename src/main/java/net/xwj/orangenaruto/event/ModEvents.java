package net.xwj.orangenaruto.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xwj.orangenaruto.OrangeNaruto;
import net.xwj.orangenaruto.modblock.ModBlocks;
import net.xwj.orangenaruto.modblock.RedPortalBlock;

@Mod.EventBusSubscriber(modid = OrangeNaruto.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().is(Items.FLINT_AND_STEEL)) {
            Level level = event.getLevel();
            BlockPos pos = event.getPos();

            // 检查点击的是否是红色方块
            if (level.getBlockState(pos).is(ModBlocks.RED_BLOCK.get())) {
                // 尝试在点击位置的上方创建传送门
                BlockPos portalPos = pos.above();
                RedPortalBlock portalBlock = (RedPortalBlock) ModBlocks.RED_PORTAL.get();

                if (portalBlock.trySpawnPortal(level, portalPos)) {
                    // 如果成功创建传送门，消耗打火石的耐久
                    event.getItemStack().hurtAndBreak(1, event.getEntity(),
                            player -> player.broadcastBreakEvent(event.getHand()));

                    // 防止默认的打火石行为
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }
}