package net.io.fabric.loader.module;

import net.io.fabric.antarctica;
import net.minecraft.client.MinecraftClient;
import net.io.fabric.loader.event.EventManager;
import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.setting.KeybindSetting;
import net.io.fabric.loader.module.setting.Setting;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Module implements Serializable {

    public final static MinecraftClient mc = MinecraftClient.getInstance();
    protected static EventManager eventManager = antarctica.INSTANCE.getEventManager();

    private final String name;
    private final String description;
    private boolean enabled;
    private final ArrayList<Setting<?>> settings = new ArrayList<>();
    private final Category category;

    public Module(String name, String description, boolean enabled, Category category) {
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.category = category;
        if (enabled)
            onEnable();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled)
                onEnable();
            else
                onDisable();
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void onEnable() {
        for (Setting<?> setting : settings) {
            if (setting instanceof KeybindSetting keybindSetting) {
                antarctica.INSTANCE.getKeybindManager().addKeybind(keybindSetting.get());
            }
        }
    }

    public void onDisable() {
        for (Setting<?> setting : settings) {
            if (setting instanceof KeybindSetting keybindSetting) {
                antarctica.INSTANCE.getKeybindManager().removeKeybind(keybindSetting.get());
            }
        }
    }

    public void addSetting(Setting<?> setting) {
        settings.add(setting);
    }

    public ArrayList<Setting<?>> getSettings() {
        return (ArrayList<Setting<?>>) settings.clone();
    }

    public abstract void ItemUseListener(ItemUseListener.ItemUseEvent event);
}
