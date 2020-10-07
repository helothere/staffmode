package me.elijuh.staffmode.staff.modmode;

import me.elijuh.staffmode.StaffMode;
import me.elijuh.staffmode.staff.scoreboard.ScoreboardManager;
import me.elijuh.staffmode.staff.vanish.VanishManager;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.ItemBuilder;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class ModMode {
    StaffMode plugin;
    ScoreboardManager scoreboardManager;
    VanishManager vanishManager;
    HashMap<Player, ItemStack[]> contents = new HashMap<>();
    HashMap<Player, ItemStack[]> armorContents = new HashMap<>();
    ItemStack COMPASS;
    ItemStack INSPECT;
    ItemStack FREEZE;
    ItemStack CARPET;
    ItemStack STAFF;
    ItemStack VANISH;
    ItemStack UN_VANISH;

    public ModMode(StaffMode plugin) {
        this.plugin = plugin;
        scoreboardManager = plugin.getScoreboardManager();
        vanishManager = plugin.getVanishManager();
        updateItems();
        }

    public void updateItems() {
        COMPASS = ItemBuilder.create(Material.COMPASS, ChatUtil.color(plugin.getConfig().getString("mod-mode.compass.name")), ChatUtil.color(plugin.getConfig().getString("mod-mode.compass.lore")));
        INSPECT = ItemBuilder.create(Material.BOOK, ChatUtil.color(plugin.getConfig().getString("mod-mode.book.name")), ChatUtil.color(plugin.getConfig().getString("mod-mode.book.lore")));
        FREEZE = ItemBuilder.create(Material.ICE, ChatUtil.color(plugin.getConfig().getString("mod-mode.freeze.name")), ChatUtil.color(plugin.getConfig().getString("mod-mode.freeze.lore")));
        CARPET = ItemBuilder.create(Material.CARPET, 1, ChatUtil.color(plugin.getConfig().getString("mod-mode.carpet.name")), ChatUtil.color(plugin.getConfig().getString("mod-mode.carpet.lore")));
        STAFF = ItemBuilder.create(Material.SKULL_ITEM, 3, ChatUtil.color(plugin.getConfig().getString("mod-mode.online-staff.name")), ChatUtil.color(plugin.getConfig().getString("mod-mode.online-staff.lore")));
        VANISH = ItemBuilder.create(Material.INK_SACK, 10, ChatUtil.color(plugin.getConfig().getString("mod-mode.vanish.vanish-name")), ChatUtil.color(plugin.getConfig().getString("mod-mode.vanish.vanish-lore")));
        UN_VANISH = ItemBuilder.create(Material.INK_SACK, 8, ChatUtil.color(plugin.getConfig().getString("mod-mode.vanish.unvanish-name")), ChatUtil.color(plugin.getConfig().getString("mod-mode.vanish.unvanish-lore")));
    }

    public void setInventory(Player p) {
        PlayerInventory inv = p.getInventory();
        contents.put(p, inv.getContents());
        armorContents.put(p, inv.getArmorContents());
        inv.clear();
        inv.setArmorContents(null);
        int compass_slot = plugin.getConfig().getInt("mod-mode.compass.slot")-1;
        int inspect_slot = plugin.getConfig().getInt("mod-mode.book.slot")-1;
        int freeze_slot = plugin.getConfig().getInt("mod-mode.freeze.slot")-1;
        int carpet_slot = plugin.getConfig().getInt("mod-mode.carpet.slot")-1;
        int staff_slot = plugin.getConfig().getInt("mod-mode.online-staff.slot")-1;
        int vanish_slot = plugin.getConfig().getInt("mod-mode.vanish.slot")-1;
        if (compass_slot > -1) inv.setItem(compass_slot, COMPASS);
        if (inspect_slot > -1) inv.setItem(inspect_slot, INSPECT);
        if (p.hasPermission("staffmode.freeze")) {
            if (freeze_slot > -1) inv.setItem(freeze_slot, FREEZE);
            if (carpet_slot > -1) inv.setItem(carpet_slot, CARPET);
        } else if(plugin.getConfig().getBoolean("mod-mode.freeze.replace-with-carpet-if-no-perm")) {
            if (carpet_slot > -1 && freeze_slot > -1) inv.setItem(freeze_slot, CARPET);
        }
        if (staff_slot > -1) inv.setItem(staff_slot, STAFF);
        if (vanish_slot > -1) inv.setItem(vanish_slot, (StaffUtil.isVanished(p) ? UN_VANISH : VANISH));
    }

    public void enter(Player p) {
        p.setMetadata("modmode", new FixedMetadataValue(plugin, true));
        setInventory(p);
        p.playSound(p.getLocation(), Sound.CLICK, 0.2f, 1f);
        p.sendMessage(ChatUtil.color(plugin.getConfig().getString("mod-message").replaceAll("%toggle%", ChatUtil.color("&aEnabled"))));
        if (!scoreboardManager.getScoreboard(p).isEnabled()) {
            scoreboardManager.getScoreboard(p).enable(true);
        }
        if (!StaffUtil.isVanished(p)) {
            vanishManager.getByPlayer(p).toggle(true);
        }
        p.getScoreboard().getTeam("modmode").setSuffix(ChatUtil.color("&aEnabled"));
        p.setGameMode(GameMode.CREATIVE);
    }

    public void leave(Player p) {
        p.removeMetadata("modmode", plugin);
        p.getInventory().setContents(contents.get(p));
        contents.remove(p);
        p.getInventory().setArmorContents(armorContents.get(p));
        armorContents.remove(p);
        p.playSound(p.getLocation(), Sound.CLICK, 0.2f, 1f);
        p.sendMessage(ChatUtil.color(plugin.getConfig().getString("mod-message").replaceAll("%toggle%", ChatUtil.color("&cDisabled"))));
        if (scoreboardManager.getScoreboard(p).isEnabled()) {
            p.getScoreboard().getTeam("modmode").setSuffix(ChatUtil.color("&cDisabled"));
        }
        p.setGameMode(GameMode.SURVIVAL);
    }

    public ItemStack getINSPECT() {
        return INSPECT;
    }

    public ItemStack getFREEZE() {
        return FREEZE;
    }

    public ItemStack getSTAFF() {
        return STAFF;
    }

    public ItemStack getVANISH() {
        return VANISH;
    }

    public ItemStack getUN_VANISH() {
        return UN_VANISH;
    }
}
