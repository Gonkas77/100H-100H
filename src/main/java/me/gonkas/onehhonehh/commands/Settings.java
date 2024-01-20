package me.gonkas.onehhonehh.commands;

import me.gonkas.onehhonehh.player.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Settings implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            if (args.length < 2) {
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

                case "timer_toggle", "sound_toggle", "title_display":

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

                case "hp_bars_display":

                    if (!(args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("minimized"))) {
                        commandSender.sendMessage("§4[100HP 100H]§c Setting " + args[0] + " requires syntax <setting> <all/minimized>!");
                        return true;
                    } break;
            }

            Player player = Bukkit.getPlayer(commandSender.getName());

            if (args.length == 2) {PlayerData.updateFile(player, args);}
            if (args.length == 3) {PlayerData.updateFile(player, args);}

            commandSender.sendMessage("§4[100HP 100H]§a Successfully updated specified setting §2" + args[0] + "§a.");
        } return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length < 2) {
            String[] list = {"timer_toggle", "timer_display", "timer_color", "timer_text_type", "sound_toggle", "title_display", "hp_bars_display"};
            return Arrays.stream(list).toList();

        } else if (args.length == 2) {

            return switch (args[0]) {
                default -> new ArrayList<String>(0);
                case "timer_toggle", "sound_toggle", "title_display" -> {
                    String[] list = {"on", "off"};
                    yield Arrays.stream(list).toList();
                }
                case "timer_display" -> {
                    String[] list1 = {"action_bar", "boss_bar", "scoreboard", "title"};
                    yield Arrays.stream(list1).toList();
                }
                case "timer_color" -> {
                    String[] list2 = {"aqua", "black", "blue", "dark_aqua", "dark_blue", "dark_gray", "dark_green", "dark_purple", "dark_red", "gold", "gray", "green", "light_purple", "red", "yellow", "white"};
                    yield Arrays.stream(list2).toList();
                }
                case "timer_text_type" -> {
                    String[] list3 = {"normal", "bold", "italic", "underlined", "bold_italic", "bold_underlined", "italic_underlined", "bold_italic_underlined"};
                    yield Arrays.stream(list3).toList();
                }
                case "hp_bars_display" -> {
                    String[] list4 = {"all", "minimized"};
                    yield Arrays.stream(list4).toList();
                }
            };

        } else if (args.length == 3) {

            return switch (args[0]) {
                default -> new ArrayList<String>(0);
                case "timer_color" -> {
                    String[] list2 = {"aqua", "black", "blue", "dark_aqua", "dark_blue", "dark_gray", "dark_green", "dark_purple", "dark_red", "gold", "gray", "green", "light_purple", "red", "yellow", "white"};
                    yield Arrays.stream(list2).toList();
                }
                case "timer_text_type" -> {
                    String[] list3 = {"normal", "bold", "italic", "underlined", "bold_italic", "bold_underlined", "italic_underlined", "bold_italic_underlined"};
                    yield Arrays.stream(list3).toList();
                }
            };

        } else {return new ArrayList<String>(0);}
    }



    static boolean isValidColor(String arg) {
        String[] colors = {"black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray", "dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white"};
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