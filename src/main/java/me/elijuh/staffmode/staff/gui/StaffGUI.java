package me.elijuh.staffmode.staff.gui;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.ItemBuilder;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class StaffGUI {
    StaffMode plugin;
    private Inventory inv;
    private final ItemStack filler;

    public StaffGUI(StaffMode plugin) {
        this.plugin = plugin;
        inv = Bukkit.createInventory(null, 54, ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"))+"Staff");
        filler = ItemBuilder.create(Material.STAINED_GLASS_PANE, 15, " ");
    }

    public void update() {
        inv = Bukkit.createInventory(null, 54, ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"))+"Staff");
        inv.clear();
        for (int i=0; i<9; i++) {
            inv.setItem(i, filler);
            inv.setItem(i+45, filler);
        }
        ChatColor mainColor = ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"));
        ChatColor secondColor = ChatColor.valueOf(plugin.getConfig().getString("scoreboard.second-color"));
        int staff = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("staffmode.staff")) {
                String name = plugin.getConfig().getString("staff-gui.item-names").replaceAll("%displayname%", p.getDisplayName()).replaceAll("%name%", p.getName());
                ItemStack item = ItemBuilder.create(Material.SKULL_ITEM, 3, ChatUtil.color("&r"+name));
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(ChatUtil.color("&7&m                                    &r"));
                lore.add(ChatUtil.color(mainColor+"» "+secondColor+"Vanish: "+ChatUtil.getState(StaffUtil.isVanished(p))));
                lore.add(ChatUtil.color(mainColor+"» "+secondColor+"Mod Mode: "+ChatUtil.getState(StaffUtil.isModMode(p))));
                lore.add(ChatUtil.color(mainColor+"» "+secondColor+"Gamemode: &a"+(p.getGameMode().toString().toLowerCase().substring(0, 1).toUpperCase() + p.getGameMode().toString().toLowerCase().substring(1))));
                lore.add(" ");
                lore.add(ChatUtil.color("&6Click to teleport."));
                lore.add(ChatUtil.color("&7&m                                    &r"));
                meta.setLore(lore);
                meta.setOwner(p.getName());
                item.setItemMeta(meta);
                inv.setItem(staff+9, item);
                staff++;
            }
            if (staff > 35) return;
        }
    }

    public void open(Player p) {
        update();
        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.CLICK, 0.2f, 1f);
    }
}
