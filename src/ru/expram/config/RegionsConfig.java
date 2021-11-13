package ru.expram.config;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import ru.expram.Region;

import java.util.ArrayList;
import java.util.UUID;

public class RegionsConfig extends Config {

    public RegionsConfig() {
        super("regions");
    }

    public boolean alreadySelling(String rg_name) {
        return super.getConfig().get("regions." + rg_name) == null ? false : true;
    }

    public void sell(String rg_name, UUID seller, int price, World world) {
        String base_path = "regions." + rg_name + ".";
        super.getConfig().set(base_path + "seller", seller.toString());
        super.getConfig().set(base_path + "price", price);
        super.getConfig().set(base_path + "world", world.getName());
        super.save();
    }

    public void remove(String rg_name) {
        super.getConfig().set("regions." + rg_name, null);
        super.save();
    }

    public ArrayList<Region> getRegions() {
        ArrayList<Region> list = Lists.newArrayList();
        ConfigurationSection section = super.getConfig().getConfigurationSection("regions");
        if(section == null) return list;
        for(String rg_name : section.getKeys(false)) {
            list.add(new Region(rg_name, getSeller(rg_name), getPrice(rg_name)));
        }
        return list;
    }

    public String getSeller(String rg_name) {
        return super.getConfig().getString("regions." + rg_name + ".seller");
    }

    public int getPrice(String rg_name) {
        return super.getConfig().getInt("regions." + rg_name + ".price");
    }

    public World getWorld(String rg_name) {
        return Bukkit.getWorld(super.getConfig().getString("regions." + rg_name + ".world"));
    }

}
