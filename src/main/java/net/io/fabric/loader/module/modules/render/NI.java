package net.io.fabric.loader.module.modules.render;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.Category;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
public class NI extends Module{

    public NI(){
        super("NoInvisibles", "Shows Invisible Entities", false, Category.Render);
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
