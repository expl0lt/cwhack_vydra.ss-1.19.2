package net.io.fabric.loader.module.modules.render;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;

public class PP extends Module {


    public PP() {
        super("PlayerPing", "Show ping in a player's name tag.", false, Category.Render);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }
}