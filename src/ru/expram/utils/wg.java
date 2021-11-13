package ru.expram.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.World;

public class wg {

    public static ProtectedRegion getRegion(World world, String rg_name) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager = container.get(BukkitAdapter.adapt(world));
        ProtectedRegion region = manager.getRegion(rg_name);
        return region;
    }
}
