package me.elijuh.staffmode.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    public static ItemStack create(Material mat, String name, String... loreLines) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatUtil.color(name));
        for (String line : loreLines) {
            lore.add(ChatUtil.color(line));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material mat, int dura, String name, String... loreLines) {
        ItemStack item = new ItemStack(mat, 1, (short)dura);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatUtil.color(name));
        for (String line : loreLines) {
            lore.add(ChatUtil.color(line));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
