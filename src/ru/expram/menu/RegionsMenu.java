package ru.expram.menu;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.expram.Region;
import ru.expram.RegionSeller;
import ru.expram.config.Messages;
import ru.expram.config.RegionsConfig;
import ru.expram.utils.wg;

import java.util.ArrayList;
import java.util.UUID;

public class RegionsMenu extends PageMenu {

    private RegionsConfig regionsConfig = RegionSeller.getInstance().getRegionsConfig();

    @Override
    public void event(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        if(e.getCurrentItem().getType().equals(Material.BARRIER)) {
            p.closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Messages.INVENTORY_LEFT.getFirstItem())) {
            if(super.currPage == 0) {
                Messages.ALREADY_ON_FIRST_PAGE.send(p);
            } else {
                super.currPage -= 1;
                super.open(p);
            }
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Messages.INVENTORY_RIGHT.getFirstItem())) {
            ArrayList<Region> regions = regionsConfig.getRegions();
            if(!((super.index + 1) >= regions.size())) {
                super.currPage += 1;
                super.open(p);
            } else {
                Messages.ON_LAST_PAGE.send(p);
            }
        }
        if(e.getCurrentItem().getType().equals(Material.PAPER)) {
            String rg_name = getRegionName(e.getCurrentItem().getItemMeta().getDisplayName());
            ProtectedRegion region = wg.getRegion(regionsConfig.getWorld(rg_name), rg_name);
            if(region == null) {
                Messages.REGION_WAS_DELETED.send(p);
                regionsConfig.remove(rg_name);
                p.closeInventory();
                return;
            }
            if(regionsConfig.alreadySelling(rg_name)) {
                if (e.getClick().isRightClick()) {
                    if (!RegionSeller.getInstance().getConfig().getBoolean("tp_to_region")) return;

                    Location loc = getLocation(regionsConfig.getWorld(rg_name), rg_name, p);

                    if(!regionsConfig.getWorld(rg_name).getBlockAt(loc).getType().equals(Material.AIR) &&
                    RegionSeller.getInstance().getConfig().getBoolean("safe_tp")) {
                        Messages.CANNOT_TELEPORT.send(p);
                        return;
                    }

                    p.teleport(loc);
                }
                if(e.getClick().isLeftClick()) {
                    Economy eco = RegionSeller.getInstance().getEconomy();
                    int price = regionsConfig.getPrice(rg_name);

                    if(eco.getBalance(p) >= price) {
                        eco.withdrawPlayer(p, price);


                        region.getMembers().removeAll();
                        region.getOwners().removeAll();

                        region.getOwners().addPlayer(p.getUniqueId());

                        regionsConfig.remove(rg_name);
                        p.closeInventory();
                        Messages.REGION_PURCHASED.replace("{region}", rg_name).replace("{price}", String.valueOf(price)).send(p);

                    } else {
                        Messages.NOTENOUGHMONEY.send(p);
                    }
                }
            } else {
                Messages.ALREADY_SOLD.replace("{region}", rg_name).send(p);
            }
        }
    }

    @Override
    public void setMenuItems() {
        super.addMenuItems();

        ArrayList<Region> regions = regionsConfig.getRegions();

        int max = super.maxItems;
        int page = super.currPage;

        if(regions != null && !regions.isEmpty()) {
            for(int i = 0; i < max; i++) {
                index = max * page + i;
                if(index >= regions.size()) break;
                if (regions.get(index) != null) {

                    String rg_name = regions.get(index).getRg_name();
                    String seller = regions.get(index).getSeller();
                    int price = regions.get(index).getPrice();

                    ArrayList<String> lore = Messages.INVENTORY_RGITEMLORE.replace("{region}", rg_name).
                            replace("{seller}", Bukkit.getOfflinePlayer(UUID.fromString(seller)).getName()).
                            replace("{price}", String.valueOf(price)).getAllItems();

                    if(RegionSeller.getInstance().getConfig().getBoolean("tp_to_region")) {
                        lore.add(Messages.INVENTORY_TPTOREGION.getFirstItem());
                    }

                    lore.add(Messages.INVENTORY_CLICKTOBUY.getFirstItem());

                    ItemStack item = ItemStackManager.ItemStackManager(new ItemStack(Material.PAPER),
                            Messages.INVENTORY_RGITEMTITLE.replace("{region}", rg_name).getFirstItem(),
                            lore.stream().toArray(String[]::new));
                    super.getInventory().addItem(item);

                }
            }
        }

    }

    @Override
    public int getInventorySlots() {
        return 54;
    }

    @Override
    public String getInventoryName() {
        return Messages.INVENTORY_INVTITLE.getFirstItem();
    }

    private String getRegionName(String name) {
        String msg = Messages.INVENTORY_RGITEMTITLE.getFirstItem();
        msg = msg.replace("{region}", "");
        return name.substring(msg.length());
    }

    private Location getLocation(World world, String rg_name, Player p) {
        ProtectedRegion region = wg.getRegion(world, rg_name);

        BlockVector3 vector3 = region.getMaximumPoint();

        Location loc = new Location(
                regionsConfig.getWorld(rg_name),
                vector3.getX(),
                vector3.getY(),
                vector3.getZ(),
                p.getLocation().getYaw(),
                p.getLocation().getPitch()
        );
        return loc;
    }

}
