package me.elijuh.staffmode.staff.vanish;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class VanishListeners implements Listener {
    StaffMode plugin;

    public VanishListeners(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (StaffUtil.isVanished((Player)e.getEntity()))
                    e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerPickupItemEvent e) {
        if (StaffUtil.isVanished(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void on(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player) {
            if (StaffUtil.isVanished((Player)e.getTarget()))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (StaffUtil.isVanished((Player) e.getDamager())) {
                e.setCancelled(true);
                ((Player)e.getDamager()).sendMessage(ChatUtil.color("&cYou cannot hit whilst vanished."));
            }
        }
    }
}
