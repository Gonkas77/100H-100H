package me.gonkas.onehhonehh;

import me.gonkas.onehhonehh.commands.*;
import me.gonkas.onehhonehh.player.PlayerPlaytime;
import me.gonkas.onehhonehh.player.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class OneHHOneHH extends JavaPlugin {

    public static File PLAYERDATAFOLDER;
    public static HashMap<UUID, PlayerSettings> PLAYERSETTINGS;
    public static ConsoleCommandSender CONSOLE;

    public static HashMap<Player, BossBar> BOSSBAR;
    public static ScoreboardManager SCOREBOARDMANAGER;
    public static Scoreboard MAINSCOREBOARD;

    @Override
    public void onEnable() {

        PLAYERDATAFOLDER = new File("plugins/OneHHOneHH/player_data");
        if (!PLAYERDATAFOLDER.exists()) {PLAYERDATAFOLDER.mkdirs();}
        PLAYERSETTINGS = new HashMap<UUID, PlayerSettings>();

        antiReload(); // makes sure that even after /reload plugin settings work properly

        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        getCommand("sethp").setExecutor(new SetHP());
        getCommand("settings").setExecutor(new Settings());

        SCOREBOARDMANAGER = Bukkit.getScoreboardManager();
        MAINSCOREBOARD = SCOREBOARDMANAGER.getMainScoreboard();
        BOSSBAR = new HashMap<Player, BossBar>();

        CONSOLE = Bukkit.getConsoleSender();
        CONSOLE.sendMessage("");
        CONSOLE.sendMessage("§4[100H 100H]§a Plugin v0.9 loaded successfully!");
        CONSOLE.sendMessage("");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerPlaytime.displayTimer(player);
                if (PLAYERSETTINGS.get(player.getUniqueId()).getHPBarsDisplay().equals("minimized")) {
                    player.sendHealthUpdate(
                            20 + (player.getHealth() % 20),
                            player.getFoodLevel(),
                            player.getSaturation()
                    );
                    // add more settings to action bar and timer text
                }
            }
        }, 0, 20);
    }

    @Override
    public void onDisable() { // deletes old boss bars to prevent accumulation on /reload
        for (Player player : Bukkit.getOnlinePlayers()) {BOSSBAR.get(player).removePlayer(player);}
    }

    public void antiReload() {
        for (Player player : Bukkit.getOnlinePlayers()) {PLAYERSETTINGS.put(player.getUniqueId(), new PlayerSettings(player.getUniqueId()));}
    }
}

// fix hp bars display
// finish boss bar configuration (and command)
// finish polishing timer text
// add a /help command or /syntax
// add a /setdefaults command