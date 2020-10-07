package me.elijuh.staffmode.staff.inspect;

import me.elijuh.staffmode.StaffMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateTask extends BukkitRunnable {
    StaffMode plugin;
    InspectManager manager;

    public UpdateTask(StaffMode plugin) {
        this.plugin = plugin;
        manager = plugin.getInspectManager();
    }
    @Override
    public void run() {
        for (Player p : manager.getPlayers().keySet()) {
            manager.update(p);
        }
    }
}
