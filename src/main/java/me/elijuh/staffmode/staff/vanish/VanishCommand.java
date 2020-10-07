package me.elijuh.staffmode.staff.vanish;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    StaffMode plugin;
    VanishManager manager;

    public VanishCommand(StaffMode plugin) {
        this.plugin = plugin;
        manager = plugin.getVanishManager();
        plugin.getCommand("vanish").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("staffmode.staff")) {
            p.sendMessage(ChatUtil.color("&cNo permission."));
            return true;
        }
        plugin.getVanishManager().getByPlayer(p).toggle(false);
        return true;
    }
}
