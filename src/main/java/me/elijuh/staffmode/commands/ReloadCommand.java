package me.elijuh.staffmode.commands;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    StaffMode plugin;

    public ReloadCommand(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getCommand("staffreload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("staffmode.reload")) {
                p.sendMessage(ChatUtil.color("&cNo permission."));
                return true;
            }
        }
        plugin.reloadConfig();
        plugin.saveDefaultConfig();
        plugin.disableFeatures();
        plugin.getModMode().updateItems();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("staffmode.staff")) {
                plugin.getModMode().enter(p);
                p.updateInventory();
            }
        }
        sender.sendMessage(ChatUtil.color("&4StaffMode &f» &7Configuration has been reloaded."));
        return true;
    }
}
