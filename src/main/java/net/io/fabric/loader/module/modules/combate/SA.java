package net.io.fabric.loader.module.modules.combate;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.keybind.Keybind;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.setting.BooleanSetting;
import net.io.fabric.loader.module.setting.KeybindSetting;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.event.events.PlayerTickListener;
import net.io.fabric.util.AnchorUtils;
import net.io.fabric.util.BlockUtils2;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.io.fabric.util.BlockUtils;
import net.io.fabric.util.InventoryUtils;
import org.lwjgl.glfw.GLFW;

public class SA extends Module implements PlayerTickListener {

    public SA(){
        super("SilentAnchor", "", false, Category.Combat);
    }

    private final BooleanSetting place = BooleanSetting.Builder.newInstance()
            .setName("place")
            .setDescription("place automatically")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();
    private final BooleanSetting bind = BooleanSetting.Builder.newInstance()
            .setName("bind")
            .setDescription("should activate when pressing a key")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();
    private final Keybind activateKeybind = new Keybind(
            "SilentAnchor_activateKeybind",
            GLFW.GLFW_KEY_UNKNOWN,
            false,
            false,
            null
    );

    private final KeybindSetting activateKey = new KeybindSetting.Builder()
            .setName("activateKey")
            .setDescription("the key to activate it")
            .setModule(this)
            .setValue(activateKeybind)
            .build();

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

    public int item() {
        if (InventoryUtils.selectItemFromHotbar(Items.TOTEM_OF_UNDYING)) {
            return InventoryUtils.getHotbarSlotForItem(Items.TOTEM_OF_UNDYING);
        } else {
            return (int) Math.floor(Math.random() * 9);
        }
    }

    private void anchor() {
        if (mc.crosshairTarget instanceof BlockHitResult blockResult) {
            if (place.get()) {
                if (!BlockUtils2.isBlock(Blocks.RESPAWN_ANCHOR, blockResult.getBlockPos()) && !BlockUtils.isBlock(Blocks.AIR, blockResult.getBlockPos())) {
                    if (!mc.player.isHolding(Items.RESPAWN_ANCHOR)) {
                        int hotbarSlot = InventoryUtils.getHotbarSlotForItem(Items.RESPAWN_ANCHOR);
                        mc.player.getInventory().selectedSlot = hotbarSlot;
                    }
                    ActionResult result2 = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockResult);
                    int hotbarSlot = item();
                    mc.player.getInventory().selectedSlot = hotbarSlot;
                    if (result2 == ActionResult.SUCCESS) {
                        mc.player.swingHand(Hand.MAIN_HAND);
                        int hotbarSlot2 = item();
                        mc.player.getInventory().selectedSlot = hotbarSlot2;
                    }
                }
            }
            if (AnchorUtils.isBlock(Blocks.RESPAWN_ANCHOR, blockResult.getBlockPos())) {
                if (!mc.player.isHolding(Items.GLOWSTONE)) {
                    if (!AnchorUtils.isAnchorCharged(blockResult.getBlockPos())) {
                        int hotbarSlot = InventoryUtils.getHotbarSlotForItem(Items.GLOWSTONE);
                        mc.player.getInventory().selectedSlot = hotbarSlot;
                    }
                    if (BlockUtils2.isBlock(Blocks.RESPAWN_ANCHOR, blockResult.getBlockPos())) {
                        if (mc.player.isHolding(Items.GLOWSTONE)) {
                            if (!AnchorUtils.isAnchorCharged(blockResult.getBlockPos())) {
                                ActionResult result2 = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockResult);
                                if (result2 == ActionResult.SUCCESS) {
                                    mc.player.swingHand(Hand.MAIN_HAND);
                                }
                                if (AnchorUtils.isAnchorCharged(blockResult.getBlockPos())) {
                                    if (mc.player.isHolding(Items.GLOWSTONE)) {
                                        int hotbarSlot = item();
                                        mc.player.getInventory().selectedSlot = hotbarSlot;
                                        ActionResult result3 = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockResult);
                                        if (result3 == ActionResult.SUCCESS) {
                                            mc.player.swingHand(Hand.MAIN_HAND);
                                        }
                                        if (AnchorUtils.isAnchorCharged(blockResult.getBlockPos())) {
                                            if (!mc.player.isHolding(Items.GLOWSTONE)) {
                                                ActionResult result4 = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockResult);
                                                if (result4 == ActionResult.SUCCESS) {
                                                    mc.player.swingHand(Hand.MAIN_HAND);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onPlayerTick() {
        if (activateKeybind.isDown() && bind.get()) {
            anchor();
        } else if (!bind.get()) {
            anchor();
        }
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }
}
