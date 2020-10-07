package me.elijuh.staffmode.staff.scoreboard;

import me.elijuh.staffmode.StaffMode;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ScoreboardManager {
    StaffMode plugin;
    HashMap<Player, Scoreboard> scoreboards = new HashMap<>();

    public ScoreboardManager(StaffMode plugin) {
        this.plugin = plugin;
    }

    public void addScoreboard(Player p, Scoreboard scoreboard) {
        scoreboards.put(p, scoreboard);
    }

    @Nullable
    public Scoreboard getScoreboard(Player p) {
        if (!scoreboards.containsKey(p))
            scoreboards.put(p, new Scoreboard(p, plugin));

        return scoreboards.get(p);
    }
}
