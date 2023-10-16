package net.io.fabric.loader.module.modules.render;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;

public class UpsideDownPlayers extends Module {


    public UpsideDownPlayers() {
        super("UpsideDownPlayers", "Render everyone upside-down!", false, Category.Render);
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