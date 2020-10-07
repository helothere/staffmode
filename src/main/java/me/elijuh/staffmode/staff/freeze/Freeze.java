package me.elijuh.staffmode.staff.freeze;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Freeze {
    StaffMode plugin;
    String[] msg = {
            " ",
            "&f████&c█&f████",
            "&f███&c█&6█&c█&f███ &4&lDo NOT log out!",
            "&f██&c█&6█&0█&6█&c█&f██ &cIf you do, you will be banned!",
            "&f██&c█&6█&0█&6█&c█&f██ &ePlease join TeamSpeak at",
            "&f█&c█&6██&0█&6██&c█&f█    &e%ts%",
            "&f█&c█&6█████&c█&f█",
            "&c█&6███&0█&6███&c█",
            "&c█████████",
            " "
    };

    public Freeze(StaffMode plugin) {
        this.plugin = plugin;
    }

    public void freeze(Player executor, Player target) {
        if (StaffUtil.isFrozen(target)) {
            target.removeMetadata("frozen", plugin);
            executor.sendMessage(ChatUtil.color(plugin.getConfig().getString("freeze.staff-unfreeze-message").replaceAll("%player%", target.getName())));
            target.sendMessage(plugin.getUnfreezeMessage());
        } else {
            target.setMetadata("frozen", new FixedMetadataValue(plugin, true));
            executor.sendMessage(ChatUtil.color(plugin.getConfig().getString("freeze.staff-freeze-message").replaceAll("%player%", target.getName())));
            for (String s : msg) {
                target.sendMessage(ChatUtil.color(s).replaceAll("%ts%", plugin.getConfig().getString("freeze.teamspeak")));
            }
        }
    }
}
