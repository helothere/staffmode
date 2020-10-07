package me.elijuh.staffmode.staff.gui;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class StaffGUIListener implements Listener {
    StaffMode plugin;
    StaffGUI staffGUI;

    public StaffGUIListener(StaffMode plugin) {
        this.plugin = plugin;
        staffGUI = plugin.getStaffGUI();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(InventoryClickEvent e) {
        if (!e.getInventory().getTitle().equals(ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"))+"Staff")) return;
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        if (!p.hasPermission("staffmode.staff")) {
            e.setCancelled(true);
            p.closeInventory();
            return;
        }
        if (item.getType().equals(Material.SKULL_ITEM)) {
            if (item.getItemMeta().getDisplayName().equals(ChatUtil.color("&6Online Staff"))) {
                e.setCancelled(true);
                return;
            }
            Player staff = Bukkit.getPlayerExact(((SkullMeta) item.getItemMeta()).getOwner());
            if (staff == null) {
                p.sendMessage(ChatUtil.color("&cThat player is no longer online."));
            } else {
                p.teleport(staff);
            }
        }
        e.setCancelled(true);
    }
}
