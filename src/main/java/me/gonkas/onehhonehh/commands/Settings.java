package me.gonkas.onehhonehh.commands;

import me.gonkas.onehhonehh.OneHHOneHH;
import me.gonkas.onehhonehh.player.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.gonkas.onehhonehh.player.PlayerData.updateFile;

public class Settings implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            if (args.length < 2 && !(args.length == 1 && args[0].equals("reset"))) {
                commandSender.sendMessage("§4[100HP 100H]§c Command <settings> requires arguments <setting> <value> OR <setting> <value> <value>!");
                return true;
            }

            if ((args[0].equals("timer_color") || args[0].equals("timer_text_type")) && args.length < 3) {
                commandSender.sendMessage("§4[100HP 100H]§c Setting " + args[0] + " requires syntax <setting> <value> <value>!");
                return true;
            }

            switch (args[0]) {

                default:
                    commandSender.sendMessage("§4[100HP 100H]§c Invalid setting.");
                    return true;

                case "reset":

                    if (args.length < 2) {
                        commandSender.sendMessage("§4[100HP 100H]§c Are you SURE you want to reset your settings?");
                        commandSender.sendMessage("§4[100HP 100H]§c Run the command §\"/settings reset confirm\"§c to confirm.");
                    } else {
                        commandSender.sendMessage("§4[100HP 100H]§a Settings reset to default successfully.");

                        Player player = ((Player) commandSender).getPlayer();

                        PlayerData.setDefaults(player, new File(OneHHOneHH.PLAYERDATAFOLDER, (player.getUniqueId() + ".yml")));

                        try {OneHHOneHH.SCOREBOARDS.get(player).getObjective(player + "_timer").unregister();}
                        catch (NullPointerException ignored) {}

                        try {OneHHOneHH.BOSSBAR.get(player).removePlayer(player);}
                        catch (NullPointerException ignored) {}

                    } return true;

                case "timer_toggle", "sound_toggle", "title_toggle", "goal_toggle":

                    if (!(args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("off"))) {
                        commandSender.sendMessage("§4[100HP 100H]§c Setting " + args[0] + " requires syntax <setting> <on/off>!");
                        return true;
                    } break;

                case "timer_display":

                    if (!(args[1].equalsIgnoreCase("action_bar") || args[1].equalsIgnoreCase("boss_bar") || args[1].equalsIgnoreCase("title") || args[1].equalsIgnoreCase("scoreboard"))) {
                        commandSender.sendMessage("§4[100HP 100H]§c Setting " + args[0] + " requires syntax <setting> <action_bar/boss_bar/title/scoreboard>!");
                        return true;
                    } break;

                case "timer_color":

                    if (!isValidColor(args[1]) && !isValidColor(args[2])) {
                        commandSender.sendMessage("§4[100HP 100H]§c Invalid colors!");
                        return true;
                    } break;

                case "timer_text_type":

                    if (!isValidFormat(args[1]) && !isValidFormat(args[2])) {
                        commandSender.sendMessage("§4[100HP 100H]§c Invalid formats!");
                        return true;
                    } break;

                case "timer_time_units":

                    if (!(args[1].equalsIgnoreCase("colon") || args[1].equalsIgnoreCase("single_character") || args[1].equalsIgnoreCase("shortened") || args[1].equalsIgnoreCase("full"))) {
                        commandSender.sendMessage("§4[100HP 100H]§c Setting " + args[0] + " requires syntax <setting> <colon/single_character/shortened/full>!");
                        return true;
                    } break;

                case "hp_bars_display":

                    if (!(args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("minimized"))) {
                        commandSender.sendMessage("§4[100HP 100H]§c Setting " + args[0] + " requires syntax <setting> <all/minimized>!");
                        return true;
                    } break;
            }

            Player player = Bukkit.getPlayer(commandSender.getName());
            updateFile(player, args);
            commandSender.sendMessage("§4[100HP 100H]§a Successfully updated setting §2" + args[0] + "§a.");

        } return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length < 2) {
            String[] list = {"timer_toggle", "timer_display", "timer_color", "timer_text_type", "sound_toggle", "title_toggle", "timer_time_units", "hp_bars_display", "reset", "goal_toggle"};
            return Arrays.stream(list).toList();

        } else if (args.length == 2) {

            return switch (args[0]) {
                default -> new ArrayList<String>(0);
                case "timer_toggle", "sound_toggle", "title_toggle", "goal_toggle" -> {
                    String[] list = {"on", "off"};
                    yield Arrays.stream(list).toList();
                }
                case "timer_display" -> {
                    String[] list = {"action_bar", "boss_bar", "scoreboard", "title"};
                    yield Arrays.stream(list).toList();
                }
                case "timer_color" -> {
                    String[] list = {"black", "cyan", "blue", "green", "dark_red", "purple", "gold", "light_gray", "gray", "light_blue", "lime", "aqua", "red", "pink", "yellow", "white"};
                    yield Arrays.stream(list).toList();
                }
                case "timer_text_type" -> {
                    String[] list = {"normal", "bold", "italic", "underlined", "bold_italic", "bold_underlined", "italic_underlined", "bold_italic_underlined"};
                    yield Arrays.stream(list).toList();
                }
                case "timer_time_units" -> {
                    String[] list = {"colon", "single_character", "shortened", "full"};
                    yield Arrays.stream(list).toList();
                }
                case "hp_bars_display" -> {
                    String[] list = {"all", "minimized"};
                    yield Arrays.stream(list).toList();
                }
            };

        } else if (args.length == 3) {

            return switch (args[0]) {
                default -> new ArrayList<String>(0);
                case "timer_display" -> {
                    if (args[1].equals("boss_bar")) {
                        String[] list = {"blue", "green", "pink", "purple", "red", "white", "yellow"};
                        yield Arrays.stream(list).toList();
                    } else {yield new ArrayList<>(0);}
                }
                case "timer_color" -> {
                    String[] list = {"black", "cyan", "blue", "green", "dark_red", "purple", "gold", "light_gray", "gray", "light_blue", "lime", "aqua", "red", "pink", "yellow", "white"};
                    yield Arrays.stream(list).toList();
                }
                case "timer_text_type" -> {
                    String[] list = {"normal", "bold", "italic", "underlined", "bold_italic", "bold_underlined", "italic_underlined", "bold_italic_underlined"};
                    yield Arrays.stream(list).toList();
                }
            };

        } else if (args.length == 4) {

            if (args[0].equals("timer_display") && args[1].equals("boss_bar")) {
                String[] list = {"solid", "segmented_6", "segmented_10", "segmented_12", "segmented_20"};
                return Arrays.stream(list).toList();

            } else {return new ArrayList<String>(0);}

        } else if (args.length == 5) {

            if (args[0].equals("timer_display") && args[1].equals("boss_bar")) {
                String[] list = {"on", "off"};
                return Arrays.stream(list).toList();

            } else {return new ArrayList<String>(0);}

        } else {return new ArrayList<String>(0);}
    }



    static boolean isValidColor(String arg) {
        String[] colors = {"black", "cyan", "blue", "green", "dark_red", "purple", "gold", "light_gray", "gray", "light_blue", "lime", "aqua", "red", "pink", "yellow", "white"};
        for (String color : colors) {
            if (arg.equals(color)) {return true;}
        } return false;
    }

    static boolean isValidFormat(String arg) {

        String[] arg_array = arg.split("_", 0);
        String[] formats = {"normal", "bold", "italic", "underlined"};
        boolean[] booleans = new boolean[arg_array.length];

        for (int i=0; i < arg_array.length; i++) {
            for (String format : formats) {
                if (arg_array[i].equals(format)) {
                    booleans[i] = true;
                    break;
                }
            }
        }

        for (boolean bool : booleans) {if (!bool) {return false;}}
        return true;
    }
}