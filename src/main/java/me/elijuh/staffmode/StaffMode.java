package me.elijuh.staffmode;

import lombok.Getter;
import me.elijuh.staffmode.commands.ReloadCommand;
import me.elijuh.staffmode.events.PlayerGameModeChange;
import me.elijuh.staffmode.events.PlayerJoin;
import me.elijuh.staffmode.events.PlayerQuit;
import me.elijuh.staffmode.staff.chat.ClearChat;
import me.elijuh.staffmode.staff.chat.StaffChat;
import me.elijuh.staffmode.staff.chat.StaffChatListener;
import me.elijuh.staffmode.staff.freeze.Freeze;
import me.elijuh.staffmode.staff.freeze.FreezeCommand;
import me.elijuh.staffmode.staff.freeze.FreezeListeners;
import me.elijuh.staffmode.staff.gui.StaffGUI;
import me.elijuh.staffmode.staff.gui.StaffGUICommand;
import me.elijuh.staffmode.staff.gui.StaffGUIListener;
import me.elijuh.staffmode.staff.inspect.InspectCommand;
import me.elijuh.staffmode.staff.inspect.InspectListener;
import me.elijuh.staffmode.staff.inspect.InspectManager;
import me.elijuh.staffmode.staff.inspect.UpdateTask;
import me.elijuh.staffmode.staff.modmode.ModMode;
import me.elijuh.staffmode.staff.modmode.ModModeCommand;
import me.elijuh.staffmode.staff.modmode.ModModeListeners;
import me.elijuh.staffmode.staff.scoreboard.ScoreboardCommand;
import me.elijuh.staffmode.staff.scoreboard.ScoreboardManager;
import me.elijuh.staffmode.staff.vanish.VanishCommand;
import me.elijuh.staffmode.staff.vanish.VanishListeners;
import me.elijuh.staffmode.staff.vanish.VanishManager;
import me.elijuh.staffmode.utils.ChatUtil;
import me.elijuh.staffmode.utils.StaffUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class StaffMode extends JavaPlugin {
    StaffGUI staffGUI;
    ScoreboardManager scoreboardManager;
    VanishManager vanishManager;
    InspectManager inspectManager;
    ModMode modMode;
    Freeze freeze;
    String unfreezeMessage;
    String freezeStaffMessage;
    String unfreezeStaffMessage;
    String leftFrozen;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        staffGUI = new StaffGUI(this);
        scoreboardManager = new ScoreboardManager(this);
        vanishManager = new VanishManager(this);
        inspectManager = new InspectManager(this);
        modMode = new ModMode(this);
        freeze = new Freeze(this);

        new ModModeListeners(this);
        new ModModeCommand(this);
        new FreezeCommand(this);
        new FreezeListeners(this);
        new ScoreboardCommand(this);
        new PlayerGameModeChange(this);
        new StaffGUICommand(this);
        new StaffGUIListener(this);
        new ReloadCommand(this);
        new VanishCommand(this);
        new PlayerQuit(this);
        new PlayerJoin(this);
        new VanishListeners(this);
        new StaffChat(this);
        new StaffChatListener(this);
        new InspectCommand(this);
        new InspectListener(this);
        new ClearChat(this);

        new UpdateTask(this).runTaskTimerAsynchronously(this, 0, 10);

        unfreezeMessage = ChatUtil.color(getConfig().getString("freeze.unfreeze-message"));
        freezeStaffMessage = ChatUtil.color(getConfig().getString("freeze.staff-freeze-message"));
        unfreezeStaffMessage = ChatUtil.color(getConfig().getString("freeze.staff-unfreeze-message"));
    }

    @Override
    public void onDisable() {
        disableFeatures();
    }

    public void disableFeatures() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (StaffUtil.isVanished(p)) vanishManager.getByPlayer(p).toggle(true);
            if (StaffUtil.isModMode(p)) modMode.leave(p);
            if (scoreboardManager.getScoreboard(p).isEnabled()) scoreboardManager.getScoreboard(p).disable(true);
        }
    }
}
