package me.elijuh.staffmode.staff.freeze;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListeners implements Listener {
    StaffMode plugin;
    public FreezeListeners(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(BlockPlaceEvent e) {
        if (StaffUtil.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        if (StaffUtil.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerMoveEvent e) {
        if (e.getTo() == null) return;
        if (StaffUtil.isFrozen(e.getPlayer())) {
            if (e.getTo().getX()!=e.getFrom().getX()||e.getTo().getZ()!=e.getFrom().getZ())
                e.getPlayer().teleport(e.getFrom());
        }
    }

    @EventHandler
    public void on(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (StaffUtil.isFrozen((Player)e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (StaffUtil.isFrozen((Player)e.getDamager())) {
                e.setCancelled(true);
            } else if(e.getEntity() instanceof Player) {
                if (StaffUtil.isFrozen((Player)e.getEntity())) {
                    e.setCancelled(true);
                    ((Player)e.getDamager()).sendMessage(ChatUtil.color("&cYou cannot hit that player while they are frozen!"));
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        if (StaffUtil.isFrozen(e.getPlayer())) {
            e.getPlayer().removeMetadata("frozen", plugin);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("staffmode.freeze")) {
                    p.sendMessage(" ");
                    p.sendMessage(ChatUtil.color("&c&l"+e.getPlayer().getName()+" has disconnected whilst frozen!"));
                    p.sendMessage(" ");
                }
            }
        }

    }
}
