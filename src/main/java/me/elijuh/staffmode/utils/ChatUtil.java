package me.elijuh.staffmode.utils;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getState(boolean bool) {
        return ChatUtil.color((bool)?"&aEnabled":"&cDisabled");
    }
}
