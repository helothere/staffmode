package me.elijuh.staffmode.staff.inspect;

import me.elijuh.staffmode.StaffMode;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InspectListener implements Listener {
    StaffMode plugin;
    InspectManager manager;

    public InspectListener(StaffMode plugin) {
        this.plugin = plugin;
        manager = plugin.getInspectManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(InventoryCloseEvent e) {
        manager.getPlayers().remove((Player) e.getPlayer());
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        if (e.getInventory().getTitle().startsWith(ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"))+"Inspect")) e.setCancelled(true);
    }
}
