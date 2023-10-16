package net.io.fabric.util;

import net.io.fabric.antarctica;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import net.io.fabric.loader.module.modules.misc.NP;
import org.apache.logging.log4j.LogManager;

import static net.io.fabric.antarctica.MC;

public enum ChatUtils {
    ;
    private static final String prefix = "§f[§9Pugware§f] ";

    public static void log(String message) {
        LogManager.getLogger().info("[perronesware] {}", message.replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
    }

    public static void info(String message) {
        String string = prefix + "Info: " + message;
        sendPlainMessage(string);
    }

    public static void error(String message) {
        String string = prefix + "§4Error: §f" + message;
        sendPlainMessage(string);
    }

    public static void sendPlainMessage(String message) {
        InGameHud hud = MC.inGameHud;
        if (hud != null)
            hud.getChatHud().addMessage(Text.literal(message));
    }

    public static void plainMessageWithPrefix(String message) {
        String string = prefix + message;
        sendPlainMessage(string);
    }

    public static String replaceName(String string) {
        if (string != null && antarctica.INSTANCE.getModuleManager().getModule(NP.class).isEnabled()) {
            return string.replace(MC.getSession().getUsername(), "Player");
        }
        return string;
    }
}
