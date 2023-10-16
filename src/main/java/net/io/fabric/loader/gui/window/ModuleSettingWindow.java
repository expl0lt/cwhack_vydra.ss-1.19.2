package net.io.fabric.loader.gui.window;

import net.io.fabric.loader.gui.ClickGui;
import net.io.fabric.loader.gui.component.Component;
import net.io.fabric.loader.gui.component.ModuleButtonComponent;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.setting.Setting;

public class ModuleSettingWindow extends Window {

    private final Module module;
    private final ModuleButtonComponent moduleButton;

    public ModuleSettingWindow(ClickGui parent, double x, double y, Module module, ModuleButtonComponent moduleButton) {
        super(parent, x, y, 120, 250);
        super.closable = true;
        super.minimizable = false;
        super.setTitle(module.getName());
        this.module = module;
        this.moduleButton = moduleButton;
        y = 40;
        for (Setting<?> setting : module.getSettings()) {
            Component component = setting.makeComponent(this);
            if (component != null) {
                component.setX(20);
                component.setY(y);
                addComponent(component);
                y += component.getLength() + 15.0;
            }
        }
    }

    @Override
    public void onClose() {
        moduleButton.settingWindowClosed();
    }

}
