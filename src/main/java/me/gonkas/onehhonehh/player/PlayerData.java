package me.gonkas.onehhonehh.player;

import me.gonkas.onehhonehh.OneHHOneHH;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PlayerData {

    public static void createFile(Player player) throws IOException {
        File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");
        if (!player_file.exists()) {
            player_file.createNewFile();
            setDefaults(player, player_file);
        }
    }

    public static void setDefaults(Player player, File player_file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);
        config.set("account-name", player.getName());
        config.set("timer-toggle", true);
        config.set("timer-display.display", "action_bar");
        config.set("timer-display.boss-bar.color", "white");
        config.set("timer-display.boss-bar.style", "solid");
        config.set("timer-display.boss-bar.progression", true);
        config.set("timer-color", "white-white");
        config.set("timer-text-type", "normal-normal");
        config.set("timer-time-units", "shortened");
        config.set("sound-toggle", true);
        config.set("title-toggle", true);
        config.set("hp-bars-display", "all");
        try {
            config.save(player_file);
        } catch (IOException e) {
            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
        }
    }

    public static void updateFile(Player player, String[] args) {
        File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");

        String setting = args[0];
        String arg = args[1];

        String arg2 = "";
        String arg3 = "";
        String arg4 = "";

        if (args.length >= 3) {arg2 = args[2];}
        if (args.length >= 4) {arg3 = args[3];}
        if (args.length >= 5) {arg4 = args[4];}

        if (player_file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);

            if (!config.contains("account-name")) {
                config.set("account-name", player.getName());
                try {
                    config.save(player_file);
                } catch (IOException error) {
                    log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                }
            } else if (!Objects.equals(config.getString("account-name"), player.getName())) {
                config.set("account-name", player.getName());
                try {
                    config.save(player_file);
                } catch (IOException error) {
                    log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                }
            }

            if (setting != null) {
                boolean state;
                switch (setting) {

                    case "timer_toggle":

                        state = arg.equalsIgnoreCase("on");
                        config.set("timer-toggle", state);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_toggle = state;
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "timer_display":

                        config.set("timer-display.display", arg);
                        config.set("timer-display.boss-bar.color", arg2);
                        config.set("timer-display.boss-bar.style", arg3);

                        state = arg4.equalsIgnoreCase("on");
                        config.set("timer-display.boss-bar.progression", state);

                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_display = arg;
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_bossbar_color = PlayerSettings.BossBarColor(arg2);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_bossbar_style = PlayerSettings.BossBarStyle(arg3);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_bossbar_progression = state;

                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "timer_color":

                        config.set("timer-color", arg + "-" + arg2);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_color = PlayerSettings.colorEncoder(new String[]{arg, arg2});
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "timer_text_type":

                        config.set("timer-text-type", arg + "-" + arg2);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_text_type = PlayerSettings.textTypeEncoder(new String[]{arg, arg2});
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "timer_time_units":

                        config.set("timer-time-units", arg);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_time_units = PlayerSettings.timeUnitsEncoder(arg);
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "sound_toggle":

                        state = arg.equalsIgnoreCase("on");
                        config.set("sound-toggle", state);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).sound_toggle = state;
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "title_toggle":

                        state = arg.equalsIgnoreCase("on");
                        config.set("title-toggle", state);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).title_toggle = state;
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "hp_bars_display":

                        config.set("hp-bars-display", arg);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).hp_bars_display = arg;

                        player.sendHealthUpdate();

                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;
                }
            }
        }
    }

    static void log(String string) {
        OneHHOneHH.CONSOLE.sendMessage("");
        OneHHOneHH.CONSOLE.sendMessage(string);
        OneHHOneHH.CONSOLE.sendMessage("");
    }
}