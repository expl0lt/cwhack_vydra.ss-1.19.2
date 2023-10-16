package net.io.fabric.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.function.Predicate;

import static net.io.fabric.antarctica.MC;

public enum InventoryUtils {
    ;
    public static boolean selectItemFromHotbar(Predicate<Item> item) {
        PlayerInventory inv = MC.player.getInventory();

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (!item.test(itemStack.getItem()))
                continue;
            inv.selectedSlot = i;
            return true;
        }
        return false;
    }
    public static int getHotbarSlotForItem(Item item) {
        ItemStack stack;
        for (int i = 0; i < 9; i++) {
            stack = MC.player.getInventory().getStack(i);
            if (stack.getItem() == item) {
                if (selectItemFromHotbar(item)) {
                    return i;
                }
            }
        }
        return -1;
    }
    public static boolean selectItemFromHotbar(Item item) {
        return selectItemFromHotbar(i -> i == item);
    }

    public static boolean hasItemInHotbar(Predicate<Item> item) {
        PlayerInventory inv = MC.player.getInventory();

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (item.test(itemStack.getItem()))
                return true;
        }
        return false;
    }

    public static boolean hasItemInHotbar(Item item) {
        return hasItemInHotbar(i -> i == item);
    }

    public static int countItem(Predicate<Item> item) {
        PlayerInventory inv = MC.player.getInventory();

        int count = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (item.test(itemStack.getItem()))
                count += itemStack.getCount();
        }

        return count;
    }

    public static int countItem(Item item) {
        return countItem(i -> i == item);
    }
    public static boolean has(Item item) {
        final PlayerInventory inv = MC.player.getInventory();
        for (int i = 0; i <= 8; i ++) {
            if (inv.getStack(i).isOf(item)) return true;
        }
        return false;
    }

    /**
     * If the player's held item matches the provided item type
     * @param item item type
     * @return match
     */
    public static boolean isHolding(Item item) {
        return isHolding(item, Hand.MAIN_HAND);
    }

    /**
     * If the players held item in the specified hand matches the provided type
     * @param item item type
     * @param hand hand to check
     * @return match
     */
    public static boolean isHolding(Item item, Hand hand) {
        return MC.player.getStackInHand(hand).isOf(item);
    }
}
