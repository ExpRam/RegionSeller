package ru.expram.color;

import org.bukkit.ChatColor;

public class Color {

    public static String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
