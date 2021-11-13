package ru.expram.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.expram.config.Messages;
import ru.expram.menu.RegionsMenu;

public class RegionsBuy extends CommandAbstract {

    public RegionsBuy() {
        super("regionsbuy");
    }

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
        Player p = (Player) sender;
        new RegionsMenu().open(p);
    }
}
