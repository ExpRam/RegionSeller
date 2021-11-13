package ru.expram.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.expram.RegionSeller;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    private File file;
    private FileConfiguration config;

    public Config(String name) {
        try {
            this.file = new File(RegionSeller.getInstance().getDataFolder(), name + ".yml");
            if(!file.exists() && !file.createNewFile()) throw new Exception();
            this.config = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Cannot create " + name + ".yml");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Cannot save " + file.getName());
        }
    }
}
