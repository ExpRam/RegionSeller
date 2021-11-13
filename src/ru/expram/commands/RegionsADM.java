package ru.expram.commands;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.expram.RegionSeller;
import ru.expram.config.Messages;

import java.util.List;

public class RegionsADM extends CommandAbstract {

    public RegionsADM() {
        super("adminregions");
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Messages.NOT_A_PLAYER.send(sender);
            return;
        }
        if(!sender.hasPermission("regions.admin_command")) {
            Messages.NO_PERMS.send(sender);
            return;
        }
        if(args.length == 0) {
            Messages.USAGE_RELOAD.send(sender);
            return;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            RegionSeller.getInstance().reloadConfig();
            Messages.load(RegionSeller.getConfigFile());

            Messages.CONFIG_RELOADED.send(sender);
        }

    }

    public List<String> getArgument(String[] args) {
        if(args.length == 1) return Lists.newArrayList("reload");
        return Lists.newArrayList();
    }
}
