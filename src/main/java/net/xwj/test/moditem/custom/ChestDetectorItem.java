package net.xwj.test.moditem.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ChestDetectorItem extends Item {
    public ChestDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if (!pContext.getLevel().isClientSide) {
            BlockPos playerPos = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundChest = false;

            // 遍历玩家周围500格内的方块，查找所有宝箱
            Level level = pContext.getLevel();
            int radius = 100; // 设置范围为500格

            // 遍历半径内的方块
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (pContext.getClickedPos().getY() + y < 0 || pContext.getClickedPos().getY() + y > 384) {
                            continue; // 避免越界
                        }
                        BlockPos checkPos = playerPos.offset(x, y, z);
                        BlockState state = level.getBlockState(checkPos);

                        // 检查是否为宝箱（Chest）
                        if (state.getBlock() instanceof ChestBlock) {
                            foundChest = true;
                            outputChestCoordinates(checkPos, player, state.getBlock());
                            break;
                        }
                    }
                }
            }
            if (!foundChest) {
                if (player != null) {
                    player.sendSystemMessage(Component.literal("周围100格内没有宝箱。"));
                }
            }
        }
        // 物品耐久度减少
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));


        return InteractionResult.SUCCESS;
    }

    private void outputChestCoordinates(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("找到了 " + I18n.get(block.getDescriptionId()) + " 在 " +
                "(" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ() + ")"));
    }
}
