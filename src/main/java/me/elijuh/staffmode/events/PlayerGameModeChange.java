package me.elijuh.staffmode.events;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGameModeChange implements Listener {
    StaffMode plugin;

    public PlayerGameModeChange(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void on(PlayerGameModeChangeEvent e) {
        if (plugin.getScoreboardManager().getScoreboard(e.getPlayer()).isEnabled()) {
            e.getPlayer().getScoreboard().getTeam("gamemode").setSuffix(ChatUtil.color("&a")+(e.getNewGameMode().toString().toLowerCase().substring(0, 1).toUpperCase() + e.getNewGameMode().toString().toLowerCase().substring(1)));
        }
    }
}
