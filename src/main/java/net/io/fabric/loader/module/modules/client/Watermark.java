package net.io.fabric.loader.module.modules.client;

import net.minecraft.client.util.math.MatrixStack;
import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.event.events.RenderHudListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;


import static net.io.fabric.antarctica.MC;

public class Watermark extends Module implements RenderHudListener
{
    public Watermark()
    {
        super("Watermark", "", false, Category.Client);
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        eventManager.add(RenderHudListener.class, this);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        eventManager.remove(RenderHudListener.class, this);
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }

    @Override
    public void onRenderHud(MatrixStack matrices, double partialTicks)
    {
        int count = 0;
        matrices.push();
        matrices.translate(0, 0, 0);
        MC.textRenderer.drawWithShadow(matrices, "vydra.ss", 200, 0,0x000000);
        matrices.pop();

    }
}
