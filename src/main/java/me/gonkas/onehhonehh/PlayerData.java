package me.gonkas.onehhonehh;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PlayerData {

    public static void createFile(Player player) {
        String path = player.getUniqueId() + ".yml";
        File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, path);
        if (!player_file.exists()) {
            player_file.mkdirs();

            YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);
            config.set("account-name", player.getName());
        }
    }

    public static void updateFile(Player player, String[] args) {
        File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");

        String setting = args[0];
        String arg = args[1];
        if (args.length == 3) {String arg2 = args[2];}

        if (player_file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);

            if (!config.contains("account-name")) {
                config.set("account-name", player.getName());
                try {
                    config.save(player_file);
                } catch (IOException error) {
                    OneHHOneHH.CONSOLE.sendMessage("");
                    OneHHOneHH.CONSOLE.sendMessage("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                    OneHHOneHH.CONSOLE.sendMessage("");
                }
            } else if (!Objects.equals(config.getString("account-name"), player.getName())) {
                config.set("account-name", player.getName());
                try {
                    config.save(player_file);
                } catch (IOException error) {
                    OneHHOneHH.CONSOLE.sendMessage("");
                    OneHHOneHH.CONSOLE.sendMessage("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                    OneHHOneHH.CONSOLE.sendMessage("");
                }
            }

            if (setting != null) {
                boolean state;
                switch (setting) {

                    case "timer_toggle":

                        state = arg.equalsIgnoreCase("on");
                        config.set("timer-toggle", state);
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "timer_display":

                        config.set("timer-display", arg);
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "timer_color":

                        config.set("timer-color", arg);
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "timer_text_type":

                        config.set("timer-text-type", arg);
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "sound_toggle":

                        state = arg.equalsIgnoreCase("on");
                        config.set("sound-toggle", state);
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "title_diplay":

                        state = arg.equalsIgnoreCase("title");
                        config.set("title-display", state);
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "hp_bars_display":

                        state = arg.equalsIgnoreCase("all");
                        config.set("hp_bars_display", state);
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