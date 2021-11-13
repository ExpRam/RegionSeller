package ru.expram.menu;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemStackManager {

    public static ItemStack ItemStackManager(ItemStack stack, String name, String... lore) {
        ItemStack item = stack;
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

}
