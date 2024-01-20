package me.gonkas.onehhonehh.player;

import me.gonkas.onehhonehh.OneHHOneHH;
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

            updateFile(player, new String[]{"timer_toggle", "on"});
            updateFile(player, new String[]{"timer_display", "action_bar"});
            updateFile(player, new String[]{"timer_color", "white", "white"});
            updateFile(player, new String[]{"timer_text_type", "normal", "normal"});
            updateFile(player, new String[]{"sound_toggle", "on"});
            updateFile(player, new String[]{"title_display", "on"});
            updateFile(player, new String[]{"hp_bars_display", "all"});
        }
    }

    public static void updateFile(Player player, String[] args) {
        File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");

        String setting = args[0];
        String arg = args[1];
        String arg2 = "";
        if (args.length == 3) {arg2 = args[2];}

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

                        config.set("timer-color", arg + "-" + arg2);
                        try {
                            config.save(player_file);
                        } catch (IOException e) {
                            log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");
                        }
                        break;

                    case "timer_text_type":

                        config.set("timer-text-type", arg + "-" + arg2);
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

                    case "title_display":

                        state = arg.equalsIgnoreCase("on");
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