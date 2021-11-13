package ru.expram;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.expram.commands.Regions;
import ru.expram.commands.RegionsADM;
import ru.expram.commands.RegionsBuy;
import ru.expram.config.Messages;
import ru.expram.config.RegionsConfig;
import ru.expram.events.InventoryEvent;

public class RegionSeller extends JavaPlugin {

    private static RegionSeller instance;

    private Economy economy;
    private WorldGuardPlugin wg;
    private RegionsConfig regionsConfig;

    @Override
    public void onEnable() {
        wg = getWorldGuard();
        if(wg == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "You must have WorldGuard installed! " +
                    "Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if(!initVault()) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "You must have vault and an " +
                    "economy plugin installed! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;

        saveDefaultConfig();
        regionsConfig = new RegionsConfig();
        Messages.load(getConfig());

        new Regions();
        new RegionsBuy();
        new RegionsADM();

        Bukkit.getServer().getPluginManager().registerEvents(new InventoryEvent(), this);

        PluginDescriptionFile pluginDescriptionFile = getDescription();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + pluginDescriptionFile.getName() + " " + pluginDescriptionFile.getVersion() +
                " Enabled");
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pluginDescriptionFile = getDescription();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + pluginDescriptionFile.getName() + " " + pluginDescriptionFile.getVersion() +
                " Disabled");
    }

    public static RegionSeller getInstance() {
        return instance;
    }

    public static FileConfiguration getConfigFile() {
        return getInstance().getConfig();
    }

    public Economy getEconomy() {
        return economy;
    }

    public RegionsConfig getRegionsConfig() {
        return regionsConfig;
    }

    private boolean initVault() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;

        economy = rsp.getProvider();

        return economy != null;
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }
}