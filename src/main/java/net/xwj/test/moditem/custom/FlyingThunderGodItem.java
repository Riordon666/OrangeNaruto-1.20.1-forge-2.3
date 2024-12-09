package net.xwj.test.moditem.custom;

import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.xwj.test.entity.custom.FlyingThunderGodEntity;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@EventBusSubscriber
public class FlyingThunderGodItem extends Item {
    private static final String TAG_CHARGE_TIME = "ChargeTime";
    private static final float MIN_VELOCITY = 1.5F;  // 原本的速度
    private static final float MAX_VELOCITY = 4.5F;  // 3倍速度
    private static final int MAX_CHARGE_TIME = 60; // 最大蓄力时间（tick）, 3秒

    private boolean isCharging = false;
    private float chargeProgress = 0.0f;
    private long chargeStartTime;

    public FlyingThunderGodItem(Properties properties) {
        super(properties);
    }



    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        if (isCharging) {
            System.out.println("Charging... Current progress: " + chargeProgress);
            long currentTime = System.currentTimeMillis();
            chargeProgress = Math.min((float)(currentTime - chargeStartTime) / (MAX_CHARGE_TIME * 50), 1.0F);

            Minecraft minecraft = Minecraft.getInstance();
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            double mouseX = minecraft.mouseHandler.xpos() * (double)screenWidth / minecraft.getWindow().getWidth();
            double mouseY = minecraft.mouseHandler.ypos() * (double)screenHeight / minecraft.getWindow().getHeight();
            int barWidth = 100;
            int barHeight = 10;
            int x = (int) mouseX - barWidth / 2;
            int y = (int) mouseY + 20; // Position it below the cursor
            drawRect(x, y, x + (int) (barWidth * chargeProgress), y + barHeight, 0xFF00FF00);
        }
    }

    private void drawRect(int left, int top, int right, int bottom, int color) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(left, bottom, 0.0D).color((color >> 16) & 255, (color >> 8) & 255, color & 255, (color >> 24) & 255).endVertex();
        bufferbuilder.vertex(right, bottom, 0.0D).color((color >> 16) & 255, (color >> 8) & 255, color & 255, (color >> 24) & 255).endVertex();
        bufferbuilder.vertex(right, top, 0.0D).color((color >> 16) & 255, (color >> 8) & 255, color & 255, (color >> 24) & 255).endVertex();
        bufferbuilder.vertex(left, top, 0.0D).color((color >> 16) & 255, (color >> 8) & 255, color & 255, (color >> 24) & 255).endVertex();
        tesselator.end();
        RenderSystem.disableBlend();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // 开始蓄力
        player.startUsingItem(hand);
        isCharging = true;
        chargeStartTime = System.currentTimeMillis();
        chargeProgress = 0.0f;
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        isCharging = false;
        int chargeTime = this.getUseDuration(stack) - timeLeft;
        chargeProgress = Math.min((float)chargeTime / MAX_CHARGE_TIME, 1.0F);

        // 计算投掷速度
        float velocity = MIN_VELOCITY + (MAX_VELOCITY - MIN_VELOCITY) * chargeProgress;

        // 播放投掷音效
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS,
                1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide) {
            // 创建并发射飞雷神实体
            FlyingThunderGodEntity kunai = new FlyingThunderGodEntity(level, player);
            kunai.setItem(stack);

            float inaccuracy = 1.0F;  // 精确度（越小越准）

            kunai.shootFromRotation(player, player.getXRot(), player.getYRot(),
                    0.0F, velocity, inaccuracy);

            level.addFreshEntity(kunai);

            // 显示蓄力程度信息
            String chargeMsg = String.format("蓄力程度: %.0f%%", chargeProgress * 100);
            player.sendSystemMessage(Component.literal(chargeMsg));
        }

        // 增加使用统计
        player.awardStat(Stats.ITEM_USED.get(this));

        // 如果不是创造模式，消耗物品
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // 最大使用时间，足够长以支持蓄力
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.literal("§a右键长按3s可达最远距离,按v传送")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))));
    }
}
