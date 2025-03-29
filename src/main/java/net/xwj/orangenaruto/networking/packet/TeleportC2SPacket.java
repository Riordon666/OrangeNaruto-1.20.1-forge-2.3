package net.xwj.orangenaruto.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.xwj.orangenaruto.capability.PlayerTeleportPosProvider;
import net.xwj.orangenaruto.sound.ModSounds;

import java.util.function.Supplier;

public class TeleportC2SPacket {
    public TeleportC2SPacket() {
    }

    public TeleportC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // 在服务器上执行
            ServerPlayer player = context.getSender();
            if (player != null) {
                player.getCapability(PlayerTeleportPosProvider.PLAYER_TELEPORT_POS).ifPresent(teleportPos -> {
                    if (teleportPos.hasValidTeleportPos()) {
                        Vec3 pos = teleportPos.getTeleportPos();
                        long timeSet = teleportPos.getTimeSet();
                        // 检查是否在5分钟内
                        if (System.currentTimeMillis() - timeSet <= 5 * 60 * 1000) {
                            player.teleportTo(pos.x, pos.y, pos.z);
                            player.sendSystemMessage(Component.literal("传送成功!"));
                            // 让玩家发送一条所有人都能看到的消息
                            player.level().getServer().getPlayerList().broadcastSystemMessage(Component
                                    .literal("<" + player.getName().getString() + "> 我来晚了吗?"), false);

                            // 播放自定义传送音效
                            player.level().playSound(null, pos.x, pos.y, pos.z, ModSounds.TELEPORT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                            // 传送后清除坐标
                            teleportPos.clearTeleportPos();
                        } else {
                            player.sendSystemMessage(Component.literal("传送标记已过期!"));
                            teleportPos.clearTeleportPos();
                        }
                    } else {
                        player.sendSystemMessage(Component.literal("没有有效的传送标记!"));
                    }
                });
            }
        });
        return true;
    }
}
