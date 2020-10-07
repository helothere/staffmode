package me.elijuh.staffmode.staff.scoreboard;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreboardCommand implements CommandExecutor {
    ScoreboardManager scoreboardManager;
    Scoreboard scoreboard;
    StaffMode plugin;

    public ScoreboardCommand(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getCommand("sb").setExecutor(this);
        scoreboardManager = plugin.getScoreboardManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("staffmode.staff")) {
            p.sendMessage(ChatUtil.color("&cNo permission."));
            return true;
        }
        scoreboard = scoreboardManager.getScoreboard(p);
        if (scoreboard.isEnabled()) {
            scoreboard.disable(false);
        } else {
            scoreboard.enable(false);
        }
        return true;
    }
}
