package net.io.fabric.keybind;

import net.io.fabric.antarctica;
import net.io.fabric.loader.event.events.KeyPressListener;
import net.io.fabric.loader.gui.GuiScreen;
import net.io.fabric.loader.module.modules.client.CGS;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static net.io.fabric.antarctica.MC;

public class KeybindManager implements KeyPressListener {

    private final ArrayList<Keybind> keybinds = new ArrayList<>();

    public KeybindManager() {
        antarctica.INSTANCE.getEventManager().add(KeyPressListener.class, this);
        addDefaultKeybinds();
    }

    public ArrayList<Keybind> getAllKeybinds() {
        return (ArrayList<Keybind>) keybinds.clone();
    }

    public void removeAll() {
        keybinds.clear();
        addDefaultKeybinds();
    }

    public void addKeybind(Keybind keybind) {
        keybinds.add(keybind);
    }

    public void removeKeybind(Keybind keybind) {
        keybinds.remove(keybind);
    }

    public void removeKeybind(String name) {
        keybinds.removeIf(e -> e.getName().equals(name));
    }

    @Override
    public void onKeyPress(KeyPressListener.KeyPressEvent event) {
        for (Keybind keybind : keybinds) {
            if (event.getKeyCode() == keybind.getKey()) {
                if (event.getAction() == GLFW.GLFW_PRESS)
                    keybind.press();
                if (event.getAction() == GLFW.GLFW_RELEASE)
                    keybind.release();
            }
        }
        //event.cancel();
    }

    private void addDefaultKeybinds() {
        Keybind guiBind = new Keybind("insert-gui", GLFW.GLFW_KEY_INSERT, true, false, () ->
        {
            if (MC.currentScreen != null)
                return;
            MC.setScreen(new GuiScreen());
        });
        addKeybind(guiBind);
        CGS.class.cast(antarctica.INSTANCE.getModuleManager().getModule(CGS.class)).activateKey.set(guiBind);
    }
}
