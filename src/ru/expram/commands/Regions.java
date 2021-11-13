package ru.expram.commands;

import com.google.common.collect.Lists;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.expram.RegionSeller;
import ru.expram.config.Messages;
import ru.expram.config.RegionsConfig;
import ru.expram.utils.wg;

import java.util.List;

public class Regions extends CommandAbstract {

    public Regions() {
        super("regions");
    }

    private RegionsConfig regionsConfig = RegionSeller.getInstance().getRegionsConfig();

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Messages.NOT_A_PLAYER.send(sender);
            return;
        }
        if(!sender.hasPermission("regions.player_command")) {
            Messages.NO_PERMS.send(sender);
            return;
        }
        if(args.length == 0) {
            Messages.USAGE_SELL.send(sender);
            Messages.USAGE_REMOVE.send(sender);
            return;
        }
        if(args.length < 3 && args[0].equalsIgnoreCase("sell")) {
            Messages.USAGE_SELL.send(sender);
            return;
        }
        if(args.length < 2 && args[0].equalsIgnoreCase("remove")) {
            Messages.USAGE_REMOVE.send(sender);
            return;
        }
        Player p = (Player) sender;
        String rg_name = args[1];
        if (args[0].equalsIgnoreCase("sell")) {
            int price;
            
            if(isInt(args[2]) || args[2].equals("0")) {
                price = Integer.valueOf(args[2]);
            } else {
                Messages.INVALID_NUMBER.send(sender);
                return;
            }

            WorldGuardPlugin worldGuard = RegionSeller.getInstance().getWorldGuard();
            LocalPlayer localPlayer = worldGuard.wrapPlayer(p);
            ProtectedRegion region = wg.getRegion(p.getWorld(), rg_name);

            if(region != null) {
                if(region.isOwner(localPlayer)) {
                    if (!regionsConfig.alreadySelling(rg_name)) {
                        regionsConfig.sell(rg_name, p.getUniqueId(), price, p.getWorld());
                        Messages.REGION_SOLD.replace("{region}", rg_name).replace("{price}", String.valueOf(price)).send(sender);
                    } else {
                        Messages.ALREADY_FOR_SALE.replace("{region}", rg_name).send(sender);
                    }
                } else {
                    Messages.NOT_AN_OWNER.replace("{region}", rg_name).send(sender);
                }
            } else {
                Messages.REGION_NOT_FOUND.replace("{region}", rg_name).send(sender);
            }
            return;
        } else if(args[0].equalsIgnoreCase("remove")) {
            if(regionsConfig.alreadySelling(rg_name)) {
                if(regionsConfig.getSeller(rg_name).equals(p.getUniqueId().toString())) {
                    regionsConfig.remove(rg_name);
                    Messages.REMOVED_FROM_SALE.replace("{region}", rg_name).send(sender);
                } else {
                    Messages.NOT_AN_OWNER.send(sender);
                }
            } else {
                Messages.REGION_NOT_SALE.replace("{region}", rg_name).send(sender);
            }
            return;
        }
    }

    public List<String> getArgument(String[] args) {
        if(args.length == 1) return Lists.newArrayList("sell", "remove");
        return Lists.newArrayList();
    }

    private boolean isInt(String integer) {
        try {
            Integer.parseInt(integer);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
