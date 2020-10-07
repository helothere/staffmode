package me.elijuh.staffmode.staff.chat;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class StaffChat implements CommandExecutor, Listener {
    StaffMode plugin;

    public StaffChat(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getCommand("staffchat").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("staffmode.staff")) {
            p.sendMessage(ChatUtil.color("&cNo permission."));
            return true;
        }
        if (args.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String s : args) {
                builder.append(s).append(" ");
            }
            String msg = builder.toString().trim().replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "&");
            Bukkit.getOnlinePlayers().forEach((staff) -> {
                if (staff.hasPermission("staffmode.staff")) {
                    staff.sendMessage(ChatUtil.color("&9(Staff Chat) &b" + p.getName() + "&7: ")+msg);
                }
            });
        } else {
            toggle(p);
        }
        return true;
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent e) {
        if (StaffUtil.isStaffChat(e.getPlayer())) {
            e.setCancelled(true);
            Bukkit.getOnlinePlayers().forEach((p) -> {
                if (p.hasPermission("staffmode.staff")) {
                    p.sendMessage(ChatUtil.color("&9(Staff Chat) &b" + e.getPlayer().getName() + "&7: ")+(e.getMessage().replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "&")));
                }
            });
        }
    }

    public void toggle(Player p) {
        if (StaffUtil.isStaffChat(p)) {
            p.removeMetadata("staffchat", plugin);
        } else {
            p.setMetadata("staffchat", new FixedMetadataValue(plugin, true));
        }
        if (plugin.getScoreboardManager().getScoreboard(p).isEnabled())
            p.getScoreboard().getTeam("staffchat").setSuffix(" "+ChatUtil.getState(StaffUtil.isStaffChat(p)));
        p.sendMessage(ChatUtil.color(plugin.getConfig().getString("staff-chat-message").replaceAll("%toggle%", ChatUtil.getState(StaffUtil.isStaffChat(p)))));
    }
}
