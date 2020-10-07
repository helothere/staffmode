package me.elijuh.staffmode.events;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.staff.modmode.ModMode;
import me.elijuh.staffmode.staff.scoreboard.ScoreboardManager;
import me.elijuh.staffmode.staff.vanish.VanishManager;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    StaffMode plugin;
    ModMode modMode;
    ScoreboardManager scoreboardManager;
    VanishManager vanishManager;

    public PlayerQuit(StaffMode plugin) {
        this.plugin = plugin;
        modMode = plugin.getModMode();
        scoreboardManager = plugin.getScoreboardManager();
        vanishManager = plugin.getVanishManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (StaffUtil.isModMode(p)) modMode.leave(e.getPlayer());
        if (scoreboardManager.getScoreboard(p).isEnabled()) scoreboardManager.getScoreboard(p).disable(true);

        if (StaffUtil.isVanished(p)) {
            vanishManager.getByPlayer(p).toggle(true);
            Bukkit.getOnlinePlayers().forEach((staff)-> {
                if (staff.hasPermission("staffmode.staff")) staff.sendMessage(ChatUtil.color("&9(Staff) &b"+p.getName())+" left silently.");
            });
            e.setQuitMessage(null);
        }
    }
}
