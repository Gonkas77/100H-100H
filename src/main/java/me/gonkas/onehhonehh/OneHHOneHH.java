package me.gonkas.onehhonehh;

import me.gonkas.onehhonehh.commands.*;
import me.gonkas.onehhonehh.player.PlayerData;
import me.gonkas.onehhonehh.player.PlayerPlaytime;
import me.gonkas.onehhonehh.player.PlayerSettings;
import me.gonkas.onehhonehh.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class OneHHOneHH extends JavaPlugin {

    public static FileConfiguration CONFIG;

    public static File PLAYERDATAFOLDER;
    public static HashMap<UUID, PlayerSettings> PLAYERSETTINGS;
    public static ConsoleCommandSender CONSOLE;

    public static HashMap<Player, BossBar> BOSSBAR;
    public static ScoreboardManager SCOREBOARDMANAGER;
    public static HashMap<Player, Scoreboard> SCOREBOARDS;
    public static HashMap<Player, Team> TEAMS;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        reloadConfig();

        CONFIG = getConfig();

        PLAYERDATAFOLDER = new File("plugins/OneHHOneHH/player_data");
        if (!PLAYERDATAFOLDER.exists()) {PLAYERDATAFOLDER.mkdirs();}
        PLAYERSETTINGS = new HashMap<>();

        antiReload(); // makes sure that even after /reload plugin settings work properly

        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        getCommand("gethp").setExecutor(new GetHP());
        getCommand("sethp").setExecutor(new SetHP());
        getCommand("timer").setExecutor(new Timer());
        getCommand("toggle").setExecutor(new Toggle());
        getCommand("syntax").setExecutor(new Syntax());

        SCOREBOARDMANAGER = Bukkit.getScoreboardManager();
        SCOREBOARDS = new HashMap<>();
        TEAMS = new HashMap<>();
        BOSSBAR = new HashMap<>();

        CONSOLE = Bukkit.getConsoleSender();
        CONSOLE.sendMessage("");
        CONSOLE.sendMessage("§4[100H 100H]§a Plugin v1.1 loaded successfully!");
        CONSOLE.sendMessage("");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {

                if (!(getConfig().getBoolean("timer.force-off"))) {
                    PlayerPlaytime.displayTimer(player);
                }

                PlayerSettings settings = PLAYERSETTINGS.get(player.getUniqueId());
                if (getConfig().getBoolean("timer.goal") && PLAYERSETTINGS.get(player.getUniqueId()).getGoalToggle()) {
                        if (settings.getHours() < Math.floor(PlayerPlaytime.getHours(player)) && PlayerPlaytime.getHours(player) < CONFIG.getInt("timer.final-goal")) {
                            settings.hours += 1;

                            if (!(CONFIG.getBoolean("sound.force-off")) && settings.getSoundToggle()) {
                                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 100f, 1.2f);
                            }
                            if (!(CONFIG.getBoolean("title.force-off")) && settings.getTitleToggle()) {
                                player.sendTitle(
                                        Title.ColorDecoder(CONFIG.getString("title.title.goal.color")) + Title.TextDecoder(CONFIG.getString("title.title.goal.text"), player),
                                        Title.ColorDecoder(CONFIG.getString("title.subtitle.goal.color")) + Title.TextDecoder(CONFIG.getString("title.subtitle.goal.text"), player),
                                        10, 40, 10);
                            }
                        }
                }
            }
        }, 0, 20);
    }

    @Override
    public void onDisable() { // update max_hp on player data file
        for (Player player : Bukkit.getOnlinePlayers()) {
            File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);
            PlayerSettings settings = PLAYERSETTINGS.get(player.getUniqueId());

            config.set("hp", settings.getHP());
            config.set("hours", settings.getHours());
            try {config.save(player_file);}
            catch (IOException e) {PlayerData.log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
        }
    }

    public void antiReload() {
        for (Player player : Bukkit.getOnlinePlayers()) {PLAYERSETTINGS.put(player.getUniqueId(), new PlayerSettings(player.getUniqueId()));}
    }
}