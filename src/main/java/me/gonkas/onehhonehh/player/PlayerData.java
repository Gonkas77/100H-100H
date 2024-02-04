package me.gonkas.onehhonehh.player;

import me.gonkas.onehhonehh.OneHHOneHH;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
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

            // player joining for first time
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r joined for the first time!");
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r's HP has been set to 200.");

            if (player.getStatistic(Statistic.PLAY_ONE_MINUTE) <= 10) {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(OneHHOneHH.CONFIG.getDouble("health.initial-health"));
                player.setHealth(OneHHOneHH.CONFIG.getDouble("health.initial-health"));
            }

            setDefaults(player, player_file);
        }
    }

    public static void setDefaults(Player player, File player_file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);
        config.set("account-name", player.getName());
        config.set("hp", player.getHealth());
        config.set("hours", Math.floor(PlayerPlaytime.getHours(player)));
        config.set("timer-toggle", false);
        config.set("timer-display.display", "action_bar");
        config.set("timer-display.boss-bar.color", "white");
        config.set("timer-display.boss-bar.style", "solid");
        config.set("timer-display.boss-bar.progression", true);
        config.set("timer-color", "white-white");
        config.set("timer-time-units", "shortened");
        config.set("sound-toggle", true);
        config.set("title-toggle", true);
        config.set("goal-toggle", false);
        config.set("minimize-health", false);
        try {config.save(player_file);}
        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
        OneHHOneHH.PLAYERSETTINGS.put(player.getUniqueId(), new PlayerSettings(player.getUniqueId()));
    }

    public static void updateFile(Player player, String[] args) {
        File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");

        String setting = args[0];

        String arg = "";
        String arg2 = "";
        String arg3 = "";
        String arg4 = "";

        if (args.length >= 2) {arg = args[1];}
        if (args.length >= 3) {arg2 = args[2];}
        if (args.length >= 4) {arg3 = args[3];}
        if (args.length >= 5) {arg4 = args[4];}

        if (player_file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);

            if (!config.contains("account-name")) {
                config.set("account-name", player.getName());
                try {config.save(player_file);}
                catch (IOException error) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}

            } else if (!Objects.equals(config.getString("account-name"), player.getName())) {
                config.set("account-name", player.getName());
                try {config.save(player_file);}
                catch (IOException error) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
            }

            if (setting != null) {
                boolean state;
                switch (setting) {

                    case "hp":

                        double hp = Double.parseDouble(arg);

                        config.set("hp", hp);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).hp = hp;
                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "hours":

                        double hours = Double.parseDouble(arg);

                        config.set("hours", hours);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).hours = hours;
                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "goal_toggle":

                        state = !config.getBoolean("goal-toggle");
                        config.set("goal-toggle", state);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).goal_toggle = state;

                        updateFile(player, new String[]{"hours", String.valueOf(Math.floor(PlayerPlaytime.getHours(player)))});

                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "timer_toggle":

                        state = !config.getBoolean("timer-toggle");
                        config.set("timer-toggle", state);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_toggle = state;

                        try {OneHHOneHH.SCOREBOARDS.get(player).getObjective(player.getName() + "_timer").unregister();}
                        catch (NullPointerException ignored) {}

                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "timer_display":

                        config.set("timer-display.display", arg);

                        if (arg2.isBlank()) {arg2 = "white";}
                        if (arg3.isBlank()) {arg3 = "solid";}

                        config.set("timer-display.boss-bar.color", arg2);
                        config.set("timer-display.boss-bar.style", arg3);

                        if (arg4.isBlank()) {state = true;}
                        else {state = arg4.equalsIgnoreCase("on");}

                        config.set("timer-display.boss-bar.progression", state);

                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_display = arg;
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_bossbar_color = PlayerSettings.BossBarColor(arg2);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_bossbar_style = PlayerSettings.BossBarStyle(arg3);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_bossbar_progression = state;

                        try {OneHHOneHH.SCOREBOARDS.get(player).getObjective(player.getName() + "_timer").unregister();}
                        catch (NullPointerException ignored) {}

                        try {OneHHOneHH.BOSSBAR.get(player).removePlayer(player);}
                        catch (NullPointerException ignored) {}

                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "timer_color":

                        config.set("timer-color", arg + "-" + arg2);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_color = PlayerSettings.colorEncoder(new String[]{arg, arg2});

                        try {OneHHOneHH.SCOREBOARDS.get(player).getObjective(player.getName() + "_timer").unregister();}
                        catch (NullPointerException ignored) {}

                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "timer_time_units":

                        config.set("timer-time-units", arg);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_time_units = PlayerSettings.timeUnitsEncoder(arg);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).timer_time_unit_type = arg;

                        try {OneHHOneHH.SCOREBOARDS.get(player).getObjective(player.getName() + "_timer").unregister();}
                        catch (NullPointerException ignored) {}

                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "sound_toggle":

                        state = !config.getBoolean("sound-toggle");
                        config.set("sound-toggle", state);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).sound_toggle = state;
                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "title_toggle":

                        state = !config.getBoolean("title-toggle");
                        config.set("title-toggle", state);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).title_toggle = state;
                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;

                    case "minimize_health_toggle":

                        state = !config.getBoolean("minimize-health");
                        config.set("minimize-health", state);
                        OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).minimize_health = state;

                        if (!state) {
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
                            player.setHealth(OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getHP() / 5);
                        } else {
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getHP());
                            player.setHealth(OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getHP());
                        }

                        try {config.save(player_file);}
                        catch (IOException e) {log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}
                        break;
                }
            }
        }
    }

    public static void log(String string) {
        OneHHOneHH.CONSOLE.sendMessage("");
        OneHHOneHH.CONSOLE.sendMessage(string);
        OneHHOneHH.CONSOLE.sendMessage("");
    }
}