package me.elijuh.staffmode.staff.vanish;

import me.elijuh.staffmode.StaffMode;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class VanishManager {
    StaffMode plugin;

    private final HashMap<Player, Vanish> instances = new HashMap<>();

    public VanishManager(StaffMode plugin) {
        this.plugin = plugin;
    }

    public Vanish getByPlayer(Player p) {
        if (!instances.containsKey(p)) {
            instances.put(p, new Vanish(plugin, p));
        }
        return instances.get(p);
    }

    public void addInstance(Player p, Vanish instance) {
        instances.put(p, instance);
    }
}
