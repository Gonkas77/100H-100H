package me.gonkas.onehhonehh;

import me.gonkas.onehhonehh.commands.*;
import me.gonkas.onehhonehh.player.PlayerPlaytime;
import me.gonkas.onehhonehh.player.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
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
        CONSOLE.sendMessage("§4[100H 100H]§a Plugin v1.0 loaded successfully!");
        CONSOLE.sendMessage("");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {

                PlayerPlaytime.displayTimer(player);
                if (PLAYERSETTINGS.get(player.getUniqueId()).getHPBarsDisplay().equals("minimized")) {
                    player.sendHealthUpdate(
                            PLAYERSETTINGS.get(player.getUniqueId()).getMaxHP() % 20 + 20,
                            player.getFoodLevel(),
                            player.getSaturation()
                    );
                }

                if (PLAYERSETTINGS.get(player.getUniqueId()).getSoundToggle()) {
                    if (player.getStatistic(Statistic.PLAY_ONE_MINUTE) % 72000 == 0) {
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 100f, 1.2f);
                    }
                }
            }
        }, 0, 20);
    }

    @Override
    public void onDisable() { // deletes old boss bars to prevent accumulation on /reload + update max_hp on player data file
        for (Player player : Bukkit.getOnlinePlayers()) {
            BOSSBAR.get(player).removePlayer(player);

            File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);

            config.set("max-hp", PLAYERSETTINGS.get(player.getUniqueId()).getMaxHP());
        }
    }

    public void antiReload() {
        for (Player player : Bukkit.getOnlinePlayers()) {PLAYERSETTINGS.put(player.getUniqueId(), new PlayerSettings(player.getUniqueId()));}
    }
}

// config file