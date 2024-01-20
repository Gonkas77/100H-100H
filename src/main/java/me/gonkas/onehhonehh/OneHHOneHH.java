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
import java.util.Map;
import java.util.UUID;

public class OneHHOneHH extends JavaPlugin {

    public static File PLAYERDATAFOLDER;
    public static Map<UUID, PlayerSettings> PLAYERSETTINGS;
    public static ConsoleCommandSender CONSOLE;

    public static BossBar BOSSBAR;
    public static ScoreboardManager SCOREBOARDMANAGER;
    public static Scoreboard MAINSCOREBOARD;

    @Override
    public void onEnable() {

        PLAYERDATAFOLDER = new File("plugins/OneHHOneHH/player_data");
        if (!PLAYERDATAFOLDER.exists()) {PLAYERDATAFOLDER.mkdirs();}
        PLAYERSETTINGS = new 

        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        getCommand("sethp").setExecutor(new SetHP());
        getCommand("settings").setExecutor(new Settings());

        SCOREBOARDMANAGER = Bukkit.getScoreboardManager();
        MAINSCOREBOARD = SCOREBOARDMANAGER.getMainScoreboard();

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
}