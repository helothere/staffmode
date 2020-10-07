package me.elijuh.staffmode.staff.chat;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {
    StaffMode plugin;

    public ClearChat(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getCommand("clearchat").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("staffmode.clearchat")) {
            p.sendMessage(ChatUtil.color("&cNo permission."));
            return true;
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            for (int i=0; i<250; i++) {
                all.sendMessage(" ");
            }
            all.sendMessage(ChatUtil.color("&aChat has been cleared by "+p.getDisplayName()+"&a."));
        }
        return true;
    }
}
