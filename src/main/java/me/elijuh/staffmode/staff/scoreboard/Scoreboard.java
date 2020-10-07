package me.elijuh.staffmode.staff.scoreboard;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public class Scoreboard {
    StaffMode plugin;
    Player p;
    org.bukkit.scoreboard.Scoreboard board;
    Objective objective;
    Team TOP_BORDER;
    Team LOW_BORDER;
    Team category;
    Team vanish;
    Team modmode;
    Team staffchat;
    Team gamemode;
    Team ip;
    boolean enabled;
    boolean isSetup;

    ChatColor mainColor;
    ChatColor secondColor;

    public Scoreboard(Player p, StaffMode plugin) {
        this.plugin = plugin;
        this.p = p;
        enabled = false;
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective("staff_sb", "dummy");
        plugin.getScoreboardManager().addScoreboard(p, this);
        setupScoreboard();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setupScoreboard() {
        mainColor = ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"));
        secondColor = ChatColor.valueOf(plugin.getConfig().getString("scoreboard.second-color"));

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatUtil.color(plugin.getConfig().getString("scoreboard.title")));

        TOP_BORDER = board.registerNewTeam("top_border");
        category = board.registerNewTeam("category");
        vanish = board.registerNewTeam("vanish");
        modmode = board.registerNewTeam("modmode");
        staffchat = board.registerNewTeam("staffchat");
        gamemode = board.registerNewTeam("gamemode");
        ip = board.registerNewTeam("ip");
        LOW_BORDER = board.registerNewTeam("low_border");

        TOP_BORDER.addEntry(ChatUtil.color("&8"));
        category.addEntry(ChatUtil.color("&7"));
        vanish.addEntry(ChatUtil.color("&6"));
        modmode.addEntry(ChatUtil.color("&5"));
        staffchat.addEntry(ChatUtil.color("&4"));
        gamemode.addEntry(ChatUtil.color("&3"));
        ip.addEntry(ChatUtil.color("&1"));
        LOW_BORDER.addEntry(ChatUtil.color("&0"));

        TOP_BORDER.setPrefix(ChatUtil.color("&7&m------------"));
        category.setPrefix(mainColor+ChatUtil.color("Staff Modules:"));
        vanish.setPrefix(mainColor+ChatUtil.color("» "+secondColor+"Vanish: "));
        modmode.setPrefix(mainColor+ChatUtil.color("» "+secondColor+"Mod Mode: "));
        staffchat.setPrefix(mainColor+ChatUtil.color("» "+secondColor+"StaffChat:"));
        gamemode.setPrefix(mainColor+ChatUtil.color("» "+secondColor+"Gamemode: "));
        ip.setPrefix(ChatUtil.color(plugin.getConfig().getString("scoreboard.ip")));
        LOW_BORDER.setPrefix(ChatUtil.color("&7&m------------"));

        TOP_BORDER.setSuffix(ChatUtil.color("&7&m------------"));
        vanish.setSuffix(ChatUtil.getState(StaffUtil.isVanished(p)));
        modmode.setSuffix(ChatUtil.getState(StaffUtil.isModMode(p)));
        staffchat.setSuffix(" "+ChatUtil.getState(StaffUtil.isStaffChat(p)));
        gamemode.setSuffix(ChatUtil.color("&a")+(p.getGameMode().toString().toLowerCase().substring(0, 1).toUpperCase() + p.getGameMode().toString().toLowerCase().substring(1)));
        LOW_BORDER.setSuffix(ChatUtil.color("&7&m------------"));

        objective.getScore(ChatUtil.color("&8")).setScore(8);
        objective.getScore(ChatUtil.color("&7")).setScore(7);
        objective.getScore(ChatUtil.color("&6")).setScore(6);
        objective.getScore(ChatUtil.color("&5")).setScore(5);
        objective.getScore(ChatUtil.color("&4")).setScore(4);
        objective.getScore(ChatUtil.color("&3")).setScore(3);
        objective.getScore(ChatUtil.color("&2")).setScore(2);
        objective.getScore(ChatUtil.color("&1")).setScore(1);
        objective.getScore(ChatUtil.color("&0")).setScore(0);
        isSetup = true;
    }

    public void update() {
        mainColor = ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"));
        secondColor = ChatColor.valueOf(plugin.getConfig().getString("scoreboard.second-color"));
        category.setPrefix(mainColor+ChatUtil.color("Staff Modules:"));
        vanish.setPrefix(mainColor+ChatUtil.color("» "+secondColor+"Vanish: "));
        modmode.setPrefix(mainColor+ChatUtil.color("» "+secondColor+"Mod Mode: "));
        staffchat.setPrefix(mainColor+ChatUtil.color("» "+secondColor+"StaffChat:"));
        gamemode.setPrefix(mainColor+ChatUtil.color("» "+secondColor+"Gamemode: "));
        ip.setPrefix(ChatUtil.color(plugin.getConfig().getString("scoreboard.ip")));
        objective.setDisplayName(ChatUtil.color(plugin.getConfig().getString("scoreboard.title")));
        vanish.setSuffix(ChatUtil.getState(StaffUtil.isVanished(p)));
        modmode.setSuffix(ChatUtil.getState(StaffUtil.isModMode(p)));
        staffchat.setSuffix(" "+ChatUtil.getState(StaffUtil.isStaffChat(p)));
        gamemode.setSuffix(ChatUtil.color("&a")+(p.getGameMode().toString().toLowerCase().substring(0, 1).toUpperCase() + p.getGameMode().toString().toLowerCase().substring(1)));
        plugin.getVanishManager().getByPlayer(p).refreshTags();
    }

    public void disable(boolean silent) {
        enabled = false;
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        plugin.getVanishManager().getByPlayer(p).refreshTags();
        if (!silent) {
            p.sendMessage(ChatUtil.color(plugin.getConfig().getString("scoreboard-message").replaceAll("%toggle%", "&cDisabled")));
            p.playSound(p.getLocation(), Sound.CLICK, 0.2f, 1f);
        }
    }

    public void enable(boolean silent) {
        p.setScoreboard(board);
        enabled = true;
        if (isSetup) update();
        else setupScoreboard();
        plugin.getVanishManager().getByPlayer(p).refreshTags();
        if (!silent) {
            p.sendMessage(ChatUtil.color(plugin.getConfig().getString("scoreboard-message").replaceAll("%toggle%", "&aEnabled")));
            p.playSound(p.getLocation(), Sound.CLICK, 0.2f, 1f);
        }
    }
}
