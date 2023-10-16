package net.io.fabric.loader.module.modules.render;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.Category;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
public class AGH extends Module {

    static MinecraftClient mc = MinecraftClient.getInstance();
    static int totemSlot = 1;

    public AGH(){
        super("AutoGhost", "Yea", false, Category.Render);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static int putTotemInHand()  {
        mc.player.getInventory().selectedSlot = totemSlot-1;
        mc.player.getInventory().setStack(totemSlot-1, Items.TOTEM_OF_UNDYING.getDefaultStack());
        return 1;
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }

}
