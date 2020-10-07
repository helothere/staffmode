package me.elijuh.staffmode.staff.inspect;

import lombok.Getter;
import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
public class InspectManager {
    StaffMode plugin;
    Map<Player, Player> players;

    public InspectManager(StaffMode plugin) {
        this.plugin = plugin;
        players = new HashMap<>();
    }

    public void open(Player viewer, Player target) {
        players.put(viewer, target);
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.valueOf(plugin.getConfig().getString("scoreboard.main-color"))
                +"Inspect: "+ChatColor.valueOf(plugin.getConfig().getString("scoreboard.second-color"))+target.getName());
        for (int i=36; i<45; i++) gui.setItem(i, ItemBuilder.create(Material.STAINED_GLASS_PANE, 14, " "));
        for (int i=0; i<9; i++) gui.setItem(i+27, target.getInventory().getItem(i));
        for (int i=9; i<36; i++) gui.setItem(i-9, target.getInventory().getItem(i));
        ItemStack empty = ItemBuilder.create(Material.STAINED_GLASS_PANE, 15, " ");
        ItemStack effects = ItemBuilder.create(Material.BREWING_STAND_ITEM, ChatColor.YELLOW+"Effects");
        ItemMeta meta = effects.getItemMeta();
        List<String> lore = new ArrayList<>();
        target.getActivePotionEffects().forEach((effect)-> lore.add(effect.getType().toString()+" "+effect.getAmplifier()+": "+effect.getDuration()));
        meta.setLore(lore);
        effects.setItemMeta(meta);
        ItemStack health = ItemBuilder.create(Material.SPECKLED_MELON, ChatColor.GREEN+("Health: "+(int)target.getHealth()+"/"+20));
        health.setAmount((int) target.getHealth());
        ItemStack hunger = ItemBuilder.create(Material.COOKED_BEEF, ChatColor.GOLD+("Hunger: "+target.getFoodLevel()+"/"+"20"));
        hunger.setAmount(target.getFoodLevel());
        gui.setItem(49, ItemBuilder.create(Material.STAINED_GLASS_PANE, 14, " "));
        gui.setItem(50, ItemBuilder.create(Material.STAINED_GLASS_PANE, 14, " "));
        gui.setItem(51, effects);
        gui.setItem(52, health);
        gui.setItem(53, hunger);
        gui.setItem(45, target.getInventory().getHelmet() == null ? empty : target.getInventory().getHelmet());
        gui.setItem(46, target.getInventory().getChestplate() == null ? empty : target.getInventory().getChestplate());
        gui.setItem(47, target.getInventory().getLeggings() == null ? empty : target.getInventory().getLeggings());
        gui.setItem(48, target.getInventory().getBoots() == null ? empty : target.getInventory().getBoots());
        viewer.openInventory(gui);
    }

    public void update(Player viewer) {
        Player target = players.get(viewer);
        InventoryView gui = viewer.getOpenInventory();
        for (int i=36; i<45; i++) gui.setItem(i, ItemBuilder.create(Material.STAINED_GLASS_PANE, 14, " "));
        for (int i=0; i<9; i++) gui.setItem(i+27, target.getInventory().getItem(i));
        for (int i=9; i<36; i++) gui.setItem(i-9, target.getInventory().getItem(i));
        ItemStack empty = ItemBuilder.create(Material.STAINED_GLASS_PANE, 15, " ");
        ItemStack effects = ItemBuilder.create(Material.BREWING_STAND_ITEM, ChatColor.AQUA+"Effects");
        ItemMeta meta = effects.getItemMeta();
        List<String> lore = new ArrayList<>();
        target.getActivePotionEffects().forEach((effect)-> lore.add(ChatColor.YELLOW+
                effect.getType().getName().toLowerCase().substring(0, 1).toUpperCase()+effect.getType().getName().toLowerCase().substring(1)+
                " "+roman(effect.getAmplifier()+1)+" » "+ChatColor.GOLD+format(effect.getDuration())));
        if (lore.size()<1) lore.add(ChatColor.GRAY+"None");
        meta.setLore(lore);
        effects.setItemMeta(meta);
        ItemStack health = ItemBuilder.create(Material.SPECKLED_MELON, ChatColor.GREEN+("Health » "+(int)target.getHealth()+"/"+20));
        health.setAmount((int) target.getHealth());
        ItemStack hunger = ItemBuilder.create(Material.COOKED_BEEF, ChatColor.GOLD+("Hunger » "+target.getFoodLevel()+"/"+"20"));
        hunger.setAmount(target.getFoodLevel());
        gui.setItem(49, ItemBuilder.create(Material.STAINED_GLASS_PANE, 14, " "));
        gui.setItem(50, ItemBuilder.create(Material.STAINED_GLASS_PANE, 14, " "));
        gui.setItem(51, effects);
        gui.setItem(52, health);
        gui.setItem(53, hunger);
        gui.setItem(45, target.getInventory().getHelmet() == null ? empty : target.getInventory().getHelmet());
        gui.setItem(46, target.getInventory().getChestplate() == null ? empty : target.getInventory().getChestplate());
        gui.setItem(47, target.getInventory().getLeggings() == null ? empty : target.getInventory().getLeggings());
        gui.setItem(48, target.getInventory().getBoots() == null ? empty : target.getInventory().getBoots());
    }

    private String format(int ticks) {
        StringBuilder builder = new StringBuilder();
        int time = ticks/20;
        int minutes = 0;
        while(time>60) {
            time-=60;
            minutes++;
        }
        builder.append(minutes).append(":");
        if (time<10) builder.append(0);
        builder.append(time);
        return builder.toString();
    }

    private String roman(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        StringBuilder s = new StringBuilder();
        while (input >= 1000) {
            s.append("M");
            input -= 1000;        }
        while (input >= 900) {
            s.append("CM");
            input -= 900;
        }
        while (input >= 500) {
            s.append("D");
            input -= 500;
        }
        while (input >= 400) {
            s.append("CD");
            input -= 400;
        }
        while (input >= 100) {
            s.append("C");
            input -= 100;
        }
        while (input >= 90) {
            s.append("XC");
            input -= 90;
        }
        while (input >= 50) {
            s.append("L");
            input -= 50;
        }
        while (input >= 40) {
            s.append("XL");
            input -= 40;
        }
        while (input >= 10) {
            s.append("X");
            input -= 10;
        }
        while (input >= 9) {
            s.append("IX");
            input -= 9;
        }
        while (input >= 5) {
            s.append("V");
            input -= 5;
        }
        while (input >= 4) {
            s.append("IV");
            input -= 4;
        }
        while (input >= 1) {
            s.append("I");
            input -= 1;
        }
        return s.toString();
    }
}
