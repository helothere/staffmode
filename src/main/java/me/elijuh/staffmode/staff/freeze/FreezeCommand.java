package me.elijuh.staffmode.staff.freeze;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {
    StaffMode plugin;
    Freeze freeze;
    public FreezeCommand(StaffMode plugin) {
        this.plugin = plugin;
        freeze = plugin.getFreeze();
        plugin.getCommand("freeze").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("staffmode.freeze")) {
            p.sendMessage(ChatUtil.color("&cNo permission."));
            return true;
        }
        if (args.length > 0) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target==null) {
                p.sendMessage(ChatUtil.color("&cCould not find player: "+args[0]));
                return true;
            }
            if (label.equalsIgnoreCase("unfreeze")) {
                if (!StaffUtil.isFrozen(target)) {
                    p.sendMessage(ChatUtil.color("&cThat player is not frozen!"));
                    return true;
                }
            }
            if (!target.hasPermission("staffmode.exemptfreeze")) {
                freeze.freeze(p, target);
            } else {
                p.sendMessage(ChatUtil.color(plugin.getConfig().getString("freeze.exempt")));
                return true;
            }
        } else {
            p.sendMessage(ChatUtil.color("&cUsage: /"+label+" <player>"));
        }
        return true;
    }
}
