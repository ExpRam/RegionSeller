package ru.expram.commands;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.command.*;
import ru.expram.RegionSeller;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandAbstract implements CommandExecutor, TabCompleter {

    public CommandAbstract(String cmd) {
        PluginCommand command = RegionSeller.getInstance().getCommand(cmd);
        if(command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
        }
    }

    public abstract void onCommand(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        onCommand(sender, label, args);
        return true;
    }

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return filter(getArgument(args), args);
    }

    public List<String> getArgument(String[] args) {
        return null;
    }

    public List<String> filter(List<String> stArgs, String[] curArgs) {
        if(stArgs == null) return null;
        String lastArg = curArgs[curArgs.length - 1];
        List<String> args = new ArrayList<>();
        for(String arg : stArgs) {
            if(arg.toLowerCase().startsWith(lastArg.toLowerCase())) args.add(arg);
        }
        return args;
    }

}
