package net.xwj.orangenaruto.moditem.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.xwj.orangenaruto.moditem.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SaladItem extends Item {
    public SaladItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.literal("§a吃了它你就有神奇力量")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))));
    }
    @SubscribeEvent
    public void onItemConsumed(LivingEntityUseItemEvent.Finish event) {
        // 确保是玩家
        if (event.getEntity() instanceof Player player) {
            // 检查玩家使用的物品是不是沙拉
            ItemStack itemStack = event.getItem();
            if (itemStack.getItem() instanceof SaladItem) {
                // 创建一个盘子物品
                ItemStack plate = new ItemStack(ModItems.PLATE.get());

                // 将盘子物品添加到玩家背包
                if (!player.getInventory().add(plate)) {
                    // 如果背包已满，则将盘子掉落在玩家脚下
                    player.drop(plate, false);
                }
            }
        }
    }

}
