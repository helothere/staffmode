package me.elijuh.staffmode.events;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.staff.scoreboard.Scoreboard;
import me.elijuh.staffmode.staff.vanish.Vanish;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    StaffMode plugin;

    public PlayerJoin(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (e.getPlayer().hasPermission("staffmode.staff")) {
            final Vanish vanish = (plugin.getVanishManager().getByPlayer(p)==null) ? new Vanish(plugin, p) : plugin.getVanishManager().getByPlayer(p);
            vanish.toggle(true);
            plugin.getModMode().enter(p);
            Scoreboard scoreboard = plugin.getScoreboardManager().getScoreboard(p);
            scoreboard.enable(true);
            Bukkit.getOnlinePlayers().forEach((staff)-> {
                if (staff.hasPermission("staffmode.staff")) staff.sendMessage(ChatUtil.color("&9(Staff) &b"+p.getName())+" joined silently.");
            });
            e.setJoinMessage(null);
        } else {
            for (Player vanished : Bukkit.getOnlinePlayers()) {
                if (StaffUtil.isVanished(vanished))
                    p.hidePlayer(vanished);
            }
        }
    }
}
