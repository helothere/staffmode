package me.elijuh.staffmode.staff.vanish;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.staff.modmode.ModMode;
import me.elijuh.staffmode.staff.scoreboard.ScoreboardManager;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class Vanish {
    StaffMode plugin;
    ScoreboardManager scoreboardManager;
    VanishManager manager;
    ModMode modMode;
    Player p;

    public static List<Player> team = new ArrayList<>();

    public Vanish(StaffMode plugin, Player p) {
        this.plugin = plugin;
        scoreboardManager = plugin.getScoreboardManager();
        manager = plugin.getVanishManager();
        modMode = plugin.getModMode();
        this.p = p;
        manager.addInstance(this.p, this);
    }

    public void toggle(boolean silent) {
        if (StaffUtil.isVanished(p)) show();
        else hide();
        refreshTags();
        if (StaffUtil.isModMode(p)) {
            for (int i=0; i<p.getInventory().getSize(); i++) {
                ItemStack item = p.getInventory().getItem(i);
                if (item==null) continue;
                if (!item.hasItemMeta()) continue;
                if (!item.getItemMeta().hasDisplayName()) continue;
                if (item.getItemMeta().getDisplayName().startsWith(ChatColor.GOLD+"Become")) {
                    p.getInventory().setItem(i, (StaffUtil.isVanished(p)?modMode.getUN_VANISH():modMode.getVANISH()));
                }
            }
        }
        if (!silent)
            p.sendMessage(ChatUtil.color(plugin.getConfig().getString("vanish-message").replaceAll("%toggle%", ChatUtil.getState(StaffUtil.isVanished(p)))));
    }

    private void hide() {
        Vanish.team.add(p);
        p.setMetadata("vanished", new FixedMetadataValue(plugin, true));
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("staffmode.staff")) {
                p.hidePlayer(this.p);
            }
        }
        if (scoreboardManager.getScoreboard(p).isEnabled())
            p.getScoreboard().getTeam("vanish").setSuffix(ChatUtil.getState(true));
        if (StaffUtil.isModMode(p))
            p.getInventory().setItem(8, plugin.getModMode().getUN_VANISH());
    }

    private void show() {
        Vanish.team.remove(p);
        p.removeMetadata("vanished", plugin);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.canSee(this.p))
                p.showPlayer(this.p);
        }
        if (scoreboardManager.getScoreboard(p).isEnabled())
            p.getScoreboard().getTeam("vanish").setSuffix(ChatUtil.getState(false));
        if (StaffUtil.isModMode(p))
            p.getInventory().setItem(8, plugin.getModMode().getVANISH());
    }

    public void refreshTags() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getScoreboard().getTeam("vanished")!=null)
                p.getScoreboard().getTeam("vanished").unregister();
            p.getScoreboard().registerNewTeam("vanished");
            p.getScoreboard().getTeam("vanished").setPrefix(ChatUtil.color("&7[V] "));
            for (Player vanished : Vanish.team)
                p.getScoreboard().getTeam("vanished").addPlayer(vanished);
        }
    }
}
