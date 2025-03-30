package net.xwj.orangenaruto.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final KeyMapping TELEPORT_KEY = new KeyMapping(
            "key.orangenaruto.teleport", // 键位的翻译键
            KeyConflictContext.IN_GAME, // 只在游戏中生效
            InputConstants.Type.KEYSYM, // 键盘按键
            GLFW.GLFW_KEY_V, // 默认绑定到V键
            "key.category.orangenaruto" // 键位分类的翻译键
    );
}
