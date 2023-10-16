package net.io.fabric.loader.module.modules.misc;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.setting.BooleanSetting;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.io.fabric.loader.event.events.PlayerTickListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;
import org.lwjgl.glfw.GLFW;

import static net.io.fabric.antarctica.MC;
import static net.io.fabric.util.ChatUtils.plainMessageWithPrefix;
import static net.io.fabric.util.ChatUtils.sendPlainMessage;

public class MiddleClickPing extends Module implements PlayerTickListener {
    private final BooleanSetting includePrefix = BooleanSetting.Builder.newInstance()
            .setName("Include Prefix")
            .setDescription("whether or not to include the prefix in the ping message")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();
    private boolean isMiddleClicking = false;

    public MiddleClickPing() {
        super("MidClickPing", "Middle Click a player to get their ping.", false, Category.Misc);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(PlayerTickListener.class, this);
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }

    @Override
    public void onPlayerTick() {


        HitResult hit = MC.crosshairTarget;
        if (hit.getType() != HitResult.Type.ENTITY)
            return;
        Entity target = ((EntityHitResult) hit).getEntity();
        if (!(target instanceof PlayerEntity))
            return;
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_3) == GLFW.GLFW_PRESS && !isMiddleClicking) {
            isMiddleClicking = true;
            if (includePrefix.get()) {
                plainMessageWithPrefix(target.getEntityName() + "'s ping is " + getPing(target));
            } else {
                sendPlainMessage(target.getEntityName() + "'s ping is " + getPing(target));
            }
        }
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_3) == GLFW.GLFW_RELEASE && isMiddleClicking) {
            isMiddleClicking = false;
        }
    }

    public static int getPing(Entity player) {
        if (mc.getNetworkHandler() == null) return 0;

        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        if (playerListEntry == null) return 0;
        return playerListEntry.getLatency();
    }
}
