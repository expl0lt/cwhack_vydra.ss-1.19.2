package net.io.fabric.loader.module.modules.combate;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.event.events.PlayerTickListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;
import net.io.fabric.util.BlockUtils2;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

public class MarlowAnchor extends Module implements PlayerTickListener
{
    public MarlowAnchor() {
        super("MarlowAnchor", "Anchor like marlow :3", false, Category.Combat);
    }

    @Override
    public void onEnable()
    {
        eventManager.add(PlayerTickListener.class, this);
    }

    @Override
    public void onDisable()
    {
        eventManager.remove(PlayerTickListener.class, this);
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }


    @Override
    public void onPlayerTick()
    {
        if (mc.crosshairTarget instanceof BlockHitResult hit)
        {
            BlockPos pos = hit.getBlockPos();
            if (BlockUtils2.isAnchorCharged(pos))
            {
                if (!mc.player.isHolding(Items.GLOWSTONE))
                {
                    ActionResult actionResult = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                    if (actionResult.isAccepted() && actionResult.shouldSwingHand())
                        mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}
