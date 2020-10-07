package me.elijuh.staffmode.staff.inspect;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InspectCommand implements CommandExecutor {
    StaffMode plugin;
    InspectManager manager;
    public InspectCommand(StaffMode plugin) {
        this.plugin = plugin;
        manager = plugin.getInspectManager();
        plugin.getCommand("inspect").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("staffmode.inspect")) {
            p.sendMessage(ChatUtil.color("&cNo permission."));
            return true;
        }
        if (args.length > 0) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                p.sendMessage(ChatUtil.color("&cCould not find player: "+args[0]));
            } else {
                manager.open(p, target);
            }
        } else {
            p.sendMessage(ChatUtil.color("&cUsage: /"+label+" <player>"));
        }
        return true;
    }
}
