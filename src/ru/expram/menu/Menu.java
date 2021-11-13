package ru.expram.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {

    public abstract void event(InventoryClickEvent e);
    public abstract void setMenuItems();
    public abstract int getInventorySlots();
    public abstract String getInventoryName();

    protected Inventory inventory;

    public void open(Player p) {
        inventory = Bukkit.createInventory(this, getInventorySlots(), getInventoryName());
        setMenuItems();
        p.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
