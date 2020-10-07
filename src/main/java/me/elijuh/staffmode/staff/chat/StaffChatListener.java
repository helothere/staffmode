package me.elijuh.staffmode.staff.chat;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListener implements Listener {
    StaffMode plugin;

    public StaffChatListener(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent e) {
        String prefix = plugin.getConfig().getString("staffchat-prefix");
        if (e.getMessage().startsWith(prefix)) {
            e.setCancelled(true);
            String msg = e.getMessage().replaceFirst(prefix, "");
            Bukkit.getOnlinePlayers().forEach((p) -> {
                if (p.hasPermission("staffmode.staff")) {
                    p.sendMessage(ChatUtil.color("&9(Staff Chat) &b" + e.getPlayer().getName() + "&7: ")+(ChatColor.stripColor(msg).trim()));
                }
            });
        }
    }
}
