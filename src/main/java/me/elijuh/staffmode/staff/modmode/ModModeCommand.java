package me.elijuh.staffmode.staff.modmode;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModModeCommand implements CommandExecutor {
    StaffMode plugin;
    ModMode modMode;
    public ModModeCommand(StaffMode plugin) {
        this.plugin = plugin;
        this.modMode = plugin.getModMode();
        plugin.getCommand("mod").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("staffmode.staff")) {
            p.sendMessage(ChatUtil.color("&cNo permission."));
            return true;
        }
        if (StaffUtil.isModMode(p)) {
            modMode.leave(p);
        } else {
            modMode.enter(p);
        }
        return true;
    }
}
