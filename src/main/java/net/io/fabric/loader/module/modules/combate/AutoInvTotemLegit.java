// 
// Decompiled by Procyon v0.5.36
// 

package net.io.fabric.loader.module.modules.combate;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.io.fabric.antarctica;
import net.io.fabric.loader.event.events.PlayerTickListener;
import net.io.fabric.keybind.Keybind;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;
import net.io.fabric.loader.module.setting.BooleanSetting;
import net.io.fabric.loader.module.setting.IntegerSetting;
import net.io.fabric.loader.module.setting.KeybindSetting;
import net.io.fabric.util.AccessorUtils;

import java.util.Objects;

public class AutoInvTotemLegit extends Module implements PlayerTickListener
{
    private BooleanSetting autoSwitch;
    private IntegerSetting delay;
    private IntegerSetting totemSlot;
    private final BooleanSetting forceTotem;
    private BooleanSetting activateOnKey;
    private Keybind activateKeybind;
    private KeybindSetting activateKey;
    private int invClock;
    
    public AutoInvTotemLegit() {
        super("InventoryTotemLegit", "Automatically puts on totems for you when you are in inventory", false, Category.Combat);
        this.autoSwitch = new BooleanSetting.Builder().setName("Auto Inventory Switch").setDescription("automatically switches to your totem slot").setModule(this).setValue(true).setAvailability(() -> true).build();
        IntegerSetting.Builder setMax = new IntegerSetting.Builder().setName("Delay").setDescription("the delay for auto switch after opening inventory").setModule(this).setValue(0).setMin(0).setMax(20);
        BooleanSetting autoSwitch = this.autoSwitch;
        Objects.requireNonNull(autoSwitch);
        this.delay = setMax.setAvailability(autoSwitch::get).build();
        IntegerSetting.Builder setMax2 = new IntegerSetting.Builder().setName("Totem Slot").setDescription("your totem slot").setModule(this).setValue(3).setMin(0).setMax(8);
        BooleanSetting autoSwitch2 = this.autoSwitch;
        Objects.requireNonNull(autoSwitch2);
        this.totemSlot = setMax2.setAvailability(autoSwitch2::get).build();
        this.forceTotem =BooleanSetting.Builder.newInstance().setName("Replace Junk Items").setDescription("replace your main hand item (if there is one)").setModule(this).setValue(true).setAvailability(() -> true).build();
        this.activateOnKey = BooleanSetting.Builder.newInstance().setName("Activation Key").setDescription("whether or not to activate it only when pressing the selected key").setModule(this).setValue(false).setAvailability(() -> true).build();
        this.activateKeybind = new Keybind("AutoInventoryTotem_activateKeybind", 67, false, false, null);
        this.activateKey = new KeybindSetting.Builder().setName("Binding").setDescription("the key to activate it").setModule(this).setValue(this.activateKeybind).build();
        this.invClock = -1;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.invClock = -1;
        AutoInvTotemLegit.eventManager.add(PlayerTickListener.class, this);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        AutoInvTotemLegit.eventManager.remove(PlayerTickListener.class, this);
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }

    @Override
    public void onPlayerTick() {
        PlayerInventory inv = AutoInvTotemLegit.mc.player.getInventory();
        if (antarctica.MC.currentScreen != null) {
            inv.selectedSlot = this.totemSlot.get();
        }
        if (!(AutoInvTotemLegit.mc.currentScreen instanceof InventoryScreen)) {
            this.invClock = -1;
            return;
        }
        if (this.invClock == -1) {
            this.invClock = this.delay.get();
        }
        if (this.invClock > 0) {
            --this.invClock;
            return;
        }
        if (this.autoSwitch.get()) {
            inv.selectedSlot = this.totemSlot.get();
        }
        if (this.activateOnKey.get() && !this.activateKeybind.isDown()) {
            return;
        }
        if (((ItemStack)inv.offHand.get(0)).getItem() != Items.TOTEM_OF_UNDYING) {
            Screen screen = MinecraftClient.getInstance().currentScreen;
            HandledScreen<?> gui = (HandledScreen<?>)screen;
            Slot slot = AccessorUtils.getSlotUnderMouse(gui);
            if (slot == null) {
                return;
            }
            int SlotUnderMouse = AccessorUtils.getSlotUnderMouse(gui).getIndex();
            if (SlotUnderMouse > 35) {
                return;
            }
            if (SlotUnderMouse < 0) {
                return;
            }
            if (SlotUnderMouse == 40) {
                return;
            }
            if (((ItemStack)inv.main.get(SlotUnderMouse)).getItem() == Items.TOTEM_OF_UNDYING) {
                AutoInvTotemLegit.mc.interactionManager.clickSlot(((PlayerScreenHandler)((InventoryScreen) AutoInvTotemLegit.mc.currentScreen).getScreenHandler()).syncId, SlotUnderMouse, 40, SlotActionType.SWAP, (PlayerEntity) AutoInvTotemLegit.mc.player);
            }
        }
        else {
            ItemStack mainHand = (ItemStack)inv.main.get(inv.selectedSlot);
            if (!mainHand.isEmpty() && (!this.forceTotem.get() || mainHand.getItem() == Items.TOTEM_OF_UNDYING)) {
                return;
            }
            Screen screen2 = MinecraftClient.getInstance().currentScreen;
            HandledScreen<?> gui2 = (HandledScreen<?>)screen2;
            Slot slot2 = AccessorUtils.getSlotUnderMouse(gui2);
            if (slot2 == null) {
                return;
            }
            int SlotUnderMouse2 = AccessorUtils.getSlotUnderMouse(gui2).getIndex();
            if (SlotUnderMouse2 > 35) {
                return;
            }
            if (SlotUnderMouse2 < 0) {
                return;
            }
            if (SlotUnderMouse2 == 40) {
                return;
            }
            if (SlotUnderMouse2 == this.totemSlot.get()) {
                return;
            }
            if (((ItemStack)inv.main.get(SlotUnderMouse2)).getItem() == Items.TOTEM_OF_UNDYING) {
                AutoInvTotemLegit.mc.interactionManager.clickSlot(((PlayerScreenHandler)((InventoryScreen) AutoInvTotemLegit.mc.currentScreen).getScreenHandler()).syncId, SlotUnderMouse2, inv.selectedSlot, SlotActionType.SWAP, (PlayerEntity) AutoInvTotemLegit.mc.player);
            }
        }
    }
}
