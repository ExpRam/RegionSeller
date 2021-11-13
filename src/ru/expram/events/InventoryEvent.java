package ru.expram.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import ru.expram.menu.Menu;

public class InventoryEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        InventoryHolder holder = e.getInventory().getHolder();

        if(holder instanceof Menu) {
            Menu menu = (Menu) holder;
            if(e.getCurrentItem() == null) return;
            if(e.getCurrentItem().getItemMeta() == null) return;

            e.setCancelled(true);

            if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;

            menu.event(e);
        }

    }

}

