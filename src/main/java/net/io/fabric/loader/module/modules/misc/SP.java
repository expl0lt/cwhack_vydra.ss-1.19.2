package net.io.fabric.loader.module.modules.misc;
import net.io.fabric.loader.event.events.ItemUseListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import net.io.fabric.loader.event.events.PlayerTickListener;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.setting.BooleanSetting;
import net.io.fabric.util.InventoryUtils;

import static net.io.fabric.antarctica.MC;

public class SP extends Module implements PlayerTickListener {
    private final BooleanSetting sword = BooleanSetting.Builder.newInstance()
            .setName("Sword")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();
    private final BooleanSetting totem = BooleanSetting.Builder.newInstance()
            .setName("Totem")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();
    private final BooleanSetting all = BooleanSetting.Builder.newInstance()
            .setName("Anything")
            .setDescription("")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();
    private final BooleanSetting throwPearl = BooleanSetting.Builder.newInstance()
            .setName("Throw Pearl")
            .setDescription("")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();
    private final BooleanSetting equipBack = BooleanSetting.Builder.newInstance()
            .setName("Equip Item After")
            .setDescription("Equip the item you held before pearling")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();
    public SP(){
        super("AutoPearl", "sdf", false, Category.Misc);
    }
    private int findSwordHotbarSlot(PlayerEntity player) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() instanceof SwordItem) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public void onEnable() { //a
        eventManager.add(PlayerTickListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        eventManager.remove(PlayerTickListener.class, this);
        super.onDisable();
    }
    @Override
    public void onPlayerTick () {
        if (MC.player != null) {
            if (sword.get()) {
                assert mc.player != null;
                PlayerEntity player = mc.player;
                if ((GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS) && player.getMainHandStack().getItem() instanceof SwordItem && InventoryUtils.hasItemInHotbar(Items.ENDER_PEARL) && mc.currentScreen == null ) {
                    InventoryUtils.selectItemFromHotbar(Items.ENDER_PEARL);
                    if (player.getMainHandStack().getItem() == Items.ENDER_PEARL && throwPearl.get()) {
                        assert mc.interactionManager != null;
                        mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());
                        if (player.getMainHandStack().getItem() == Items.ENDER_PEARL) {
                            mc.player.swingHand(Hand.MAIN_HAND);
                            if (equipBack.get()) {
                                int swordSlot = findSwordHotbarSlot(player);
                                if (swordSlot >= 0) {
                                    player.getInventory().selectedSlot = swordSlot;
                                }
                            }
                        }
                    }
                }
            }
            if (totem.get()) {
                assert mc.player != null;
                PlayerEntity player = mc.player;
                if ((GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS) && player.getMainHandStack().getItem() == Items.TOTEM_OF_UNDYING && InventoryUtils.hasItemInHotbar(Items.ENDER_PEARL) && mc.currentScreen == null) {
                    InventoryUtils.selectItemFromHotbar(Items.ENDER_PEARL);
                    if (player.getMainHandStack().getItem() == Items.ENDER_PEARL && throwPearl.get()) {
                        assert mc.interactionManager != null;
                        assert mc.player != null;
                        mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());
                        if (player.getMainHandStack().getItem() == Items.ENDER_PEARL) {
                            mc.player.swingHand(Hand.MAIN_HAND);
                            if (InventoryUtils.isHolding(Items.ENDER_PEARL) && throwPearl.get()) {
                                if (InventoryUtils.hasItemInHotbar(Items.TOTEM_OF_UNDYING) && equipBack.get()) {
                                    InventoryUtils.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
                                }
                            }
                        }

                    }
                }
            }
            if (all.get()) {
                assert mc.player != null;
                if ((GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS) && mc.player.getMainHandStack().getItem() != null && InventoryUtils.hasItemInHotbar(Items.ENDER_PEARL) && mc.currentScreen == null) {
                    InventoryUtils.selectItemFromHotbar(Items.ENDER_PEARL);
                    if ((mc.player.getMainHandStack().getItem() == Items.ENDER_PEARL) && throwPearl.get()) {
                        assert mc.interactionManager != null;
                        assert mc.player != null;
                        mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());
                        if (mc.player.isHolding(Items.ENDER_PEARL)) {
                            mc.player.swingHand(Hand.MAIN_HAND);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {
    }

}
