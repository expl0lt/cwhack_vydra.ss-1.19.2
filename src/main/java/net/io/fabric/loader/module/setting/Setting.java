package net.io.fabric.loader.module.setting;

import net.io.fabric.loader.gui.component.Component;
import net.io.fabric.loader.gui.window.Window;
import net.io.fabric.loader.module.Module;

import java.io.Serializable;

public abstract class Setting<T> implements Serializable {

    private final String name;
    private final String description;

    protected Setting(String name, String description, Module module) {
        this.name = name;
        this.description = description;
        module.addSetting(this);
    }

    public abstract T get();

    public abstract void set(T value);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract Component makeComponent(Window parent);

}
