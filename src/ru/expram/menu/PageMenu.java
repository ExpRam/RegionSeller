package ru.expram.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.expram.color.Color;
import ru.expram.config.Messages;

public abstract class PageMenu extends Menu {

    protected int maxItems = 45;
    protected int currPage = 0;
    protected int index = 0;

    public void addMenuItems() {
        ItemStack glass = ItemStackManager.ItemStackManager(new ItemStack(Material.GRAY_STAINED_GLASS_PANE),
                " ", " ");

        ItemStack close = ItemStackManager.ItemStackManager(new ItemStack(Material.BARRIER),
                Color.colorize(Messages.INVENTORY_CLOSE.getFirstItem()), "");

        ItemStack right = ItemStackManager.ItemStackManager(new ItemStack(Material.DARK_OAK_BUTTON),
                Color.colorize(Messages.INVENTORY_RIGHT.getFirstItem()), "");

        ItemStack left = ItemStackManager.ItemStackManager(new ItemStack(Material.DARK_OAK_BUTTON),
                Color.colorize(Messages.INVENTORY_LEFT.getFirstItem()), "");

        for (int i = 45; i < 54; i++) {
            if (super.getInventory().getItem(i) == null) {
                super.getInventory().setItem(i, glass);
            }
        }

        super.getInventory().setItem(48, left);
        super.getInventory().setItem(49, close);
        super.getInventory().setItem(50, right);
    }
}
