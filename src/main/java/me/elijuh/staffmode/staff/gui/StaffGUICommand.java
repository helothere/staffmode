package me.elijuh.staffmode.staff.gui;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffGUICommand implements CommandExecutor {
    StaffMode plugin;
    StaffGUI staffGUI;

    public StaffGUICommand(StaffMode plugin) {
        this.plugin = plugin;
        plugin.getCommand("stafflist").setExecutor(this);
        staffGUI = plugin.getStaffGUI();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("staffmode.staff")) {
            p.sendMessage(ChatUtil.color("&cNo permission."));
            return true;
        }
        staffGUI.open(p);
        return true;
    }
}
