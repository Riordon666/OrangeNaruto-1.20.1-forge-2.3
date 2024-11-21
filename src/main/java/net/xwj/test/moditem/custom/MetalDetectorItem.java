package net.xwj.test.moditem.custom;

import net.xwj.test.util.ModTags;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            for (int i=0; i<= positionClicked.getY() + 64;i++){
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));

                if (isValuableBlock(state)){
                    outputValueableCoordinates(positionClicked.below(i),player,state.getBlock());
                    foundBlock = true;
                    break;
                }
            }

            if (!foundBlock){
                player.sendSystemMessage(Component.literal("找到了个鸡毛"));
            }
        }
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;

    }
    private void outputValueableCoordinates(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("找到了" + I18n.get(block.getDescriptionId()) + "在" +
                "(" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ() + "),我真牛逼"));
    }

    private boolean isValuableBlock(BlockState state){
        return state.is(ModTags.Blocks.METAL_DETECTOR_VALUABLES);
    }
}
