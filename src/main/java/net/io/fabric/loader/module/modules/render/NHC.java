package net.io.fabric.loader.module.modules.render;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;

public class NHC extends Module {

    public static boolean toggledOn = false;

    public NHC() {
        super("NoHurtcam", "N", false, Category.Render);

    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        toggledOn = true;
    }

    public void onDisable()
    {
        super.onEnable();
        toggledOn = false;
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }
}
