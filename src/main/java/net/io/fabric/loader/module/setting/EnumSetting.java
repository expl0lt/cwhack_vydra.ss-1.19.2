package net.io.fabric.loader.module.setting;

import net.io.fabric.loader.gui.component.Component;
import net.io.fabric.loader.gui.window.Window;
import net.io.fabric.loader.module.Module;

import java.util.Arrays;
import java.util.Optional;

public class EnumSetting<T extends Enum<T>> extends Setting<T>
{

    private final T[] values;
    private T value;

    public EnumSetting(String name, String description, T[] values, T value, Module module)
    {
        super(name, description, module);
        this.values = values;
        this.value = value;
    }


    public T getValue()
    {
        return value;
    }


    public void loadFromStringInternal(String string)
    {
        Optional<T> v = Arrays.stream(values)
                .filter(e -> e.toString().equalsIgnoreCase(string))
                .findFirst();
        if (v.isEmpty())
            throw new RuntimeException();
        value = v.get();
    }


    public String storeAsString()
    {
        return value.toString();
    }

    @Override
    public T get() {
        return null;
    }

    @Override
    public void set(T value) {

    }

    @Override
    public Component makeComponent(Window parent) {
        return null;
    }
}
