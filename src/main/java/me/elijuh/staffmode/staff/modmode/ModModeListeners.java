package me.elijuh.staffmode.staff.modmode;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.staff.scoreboard.ScoreboardManager;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class ModModeListeners implements Listener {
    StaffMode plugin;
    ModMode modMode;
    ScoreboardManager scoreboardManager;
    Player previous;
    public ModModeListeners(StaffMode plugin) {
        this.plugin = plugin;
        this.modMode = plugin.getModMode();
        scoreboardManager = plugin.getScoreboardManager();
        previous = null;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        if (plugin.getConfig().getBoolean("allow-inventory-use") && ((Player)e.getWhoClicked()).hasPermission("staffmode.inventory")) return;
        if (e.getInventory().getTitle().equals(ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"))+"Staff")) return;
        if (StaffUtil.isModMode((Player) e.getWhoClicked())) {
            e.getWhoClicked().closeInventory();
            e.setCancelled(true);
            ((Player) e.getWhoClicked()).updateInventory();
        }
    }

    @EventHandler
    public void on(PlayerDropItemEvent e) {
        if (StaffUtil.isModMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerPickupItemEvent e) {
        if (StaffUtil.isModMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (StaffUtil.isModMode((Player) e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        if (StaffUtil.isModMode(e.getPlayer()) && (!e.getPlayer().hasPermission("staffmode.interactblocks") || !plugin.getConfig().getBoolean("interact-blocks-in-mod"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockPlaceEvent e) {
        if (StaffUtil.isModMode(e.getPlayer()) && (!e.getPlayer().hasPermission("staffmode.interactblocks") || !plugin.getConfig().getBoolean("interact-blocks-in-mod"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (!StaffUtil.isModMode(e.getPlayer()) || e.getItem() == null) return;
        if (!e.getAction().toString().contains("RIGHT")) return;
        ItemStack item = e.getItem();
        Player p = e.getPlayer();
        if (item.equals(modMode.getVANISH())||item.equals(modMode.getUN_VANISH())) {
            plugin.getVanishManager().getByPlayer(p).toggle(true);
        } else if(item.equals(modMode.getSTAFF())) {
            plugin.getStaffGUI().open(p);
        }
        if (item.hasItemMeta()) {
            if (item.getItemMeta().hasDisplayName()) {
                if (item.getItemMeta().getDisplayName().startsWith(String.valueOf(ChatColor.GOLD)))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void on(PlayerInteractEntityEvent e) {
        if (!StaffUtil.isModMode(e.getPlayer()) || e.getPlayer().getItemInHand() == null || !(e.getRightClicked() instanceof Player)) return;
        Player p = e.getPlayer();
        Player other = (Player) e.getRightClicked();
        ItemStack item = p.getItemInHand();
        if (item.equals(modMode.getINSPECT())) {
            plugin.getInspectManager().open(p, other);
        } else if(item.equals(modMode.getFREEZE())) {
            plugin.getFreeze().freeze(p, other);
        } else return;
        e.setCancelled(true);
    }
}
