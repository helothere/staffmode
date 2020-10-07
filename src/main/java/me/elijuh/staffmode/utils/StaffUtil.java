package me.elijuh.staffmode.utils;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class StaffUtil {
    public static boolean isVanished(Player p) {
        for (MetadataValue value : p.getMetadata("vanished")) {
            if (value.asBoolean()) return true;
        }
        return false;
    }

    public static boolean isModMode(Player p) {
        for (MetadataValue value : p.getMetadata("modmode")) {
            if (value.asBoolean()) return true;
        }
        return false;
    }

    public static boolean isFrozen(Player p) {
        for (MetadataValue value : p.getMetadata("frozen")) {
            if (value.asBoolean()) return true;
        }
        return false;
    }

    public static boolean isStaffChat(Player p) {
        for (MetadataValue value : p.getMetadata("staffchat")) {
            if (value.asBoolean()) return true;
        }
        return false;
    }
}
