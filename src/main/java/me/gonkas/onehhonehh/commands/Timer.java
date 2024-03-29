package me.gonkas.onehhonehh.commands;

import me.gonkas.onehhonehh.OneHHOneHH;

import me.gonkas.onehhonehh.player.PlayerSettings;
import me.gonkas.onehhonehh.util.Array;
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

import static me.gonkas.onehhonehh.player.PlayerData.updateFile;

public class Timer implements CommandExecutor, TabCompleter {

    String[] SETTINGS = {"color", "display", "text_type", "time_units", "reset"};
    String[] COLORS = {"black", "cyan", "blue", "green", "dark_red", "purple", "gold", "light_gray", "gray", "light_blue", "lime", "aqua", "red", "pink", "yellow", "white"};
    String[] DISPLAYS = {"action_bar", "boss_bar", "scoreboard", "title"};
    String[] TIMEUNITS = {"colon", "single_character", "shortened", "full"};

    String[] BOSSBARCOLORS = {"blue", "green", "pink", "purple", "red", "white", "yellow"};
    String[] BOSSBARSTYLES = {"solid", "segmented_6", "segmented_10", "segmented_12", "segmented_20"};

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2 && !args[0].equals("reset")) {
            commandSender.sendMessage("§4[100H 100H]§c Command <timer> requires syntax <setting> <argument>!");
            return true;
        } else if (!(args[0].equals("color") || args[0].equals("display") || args[0].equals("time_units") || args[0].equals("reset"))) {
            commandSender.sendMessage("§4[100H 100H]§c Invalid setting given! Use <color/display/time_units/reset>.");
            return true;
        }

        switch (args[0]) {

            case "color":

                if (!isValidColor(args[1])) {
                    commandSender.sendMessage("§4[100H 100H]§c Invalid first color!");
                    return true;
                } else if (args.length == 3) {
                    if (!isValidColor(args[2])) {
                        commandSender.sendMessage("§4[100H 100H]§c Invalid second color!");
                        return true;
                    }
                } else if (args.length > 3) {
                    commandSender.sendMessage("§4[100H 100H]§c Setting <color> only requires arguments <color> <color>!");
                    return true;
                } else {
                    args = Array.arrayAppend(args, OneHHOneHH.PLAYERSETTINGS.get(((Player) commandSender).getUniqueId()).getTimerColor()[1]);
                } break;

            case "display":

                if (!(args[1].equals("action_bar") || args[1].equals("boss_bar") || args[1].equals("scoreboard") || args[1].equals("title"))) {
                    commandSender.sendMessage("§4[100H 100H]§c Invalid display given! Use <action_bar/boss_bar/scoreboard/title>.");
                    return true;
                } else if (!args[1].equals("boss_bar") && args.length > 2) {
                    commandSender.sendMessage("§4[100H 100H]§c Setting <display> only requires one argument! Use <action_bar/boss_bar/scoreboard/title>.");
                    return true;
                } else if (args[1].equals("boss_bar") && args.length > 5) {
                    commandSender.sendMessage("§4[100H 100H]§c Display <boss_bar> only requires arguments <color> <style> <progress>.");
                    return true;
                } else if (args[1].equals("boss_bar") && args.length == 2) {
                    PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(((Player) commandSender).getUniqueId());

                    String string;
                    if (settings.getTimerBossbarProgression()) {string = "on";}
                    else {string = "off";}

                    args = Array.arrayAppend(args, settings.getTimerBossbarColorType());
                    args = Array.arrayAppend(args, settings.getTimerBossbarStyleType());
                    args = Array.arrayAppend(args, string);
                } break;

            case "time_units":

                if (!(args[1].equals("colon") || args[1].equals("single_character") || args[1].equals("shortened") || args[1].equals("full"))) {
                    commandSender.sendMessage("§4[100H 100H]§c Invalid display given! Use <colon/single_character/shortened/full>.");
                    return true;
                } else if (args.length > 2) {
                    commandSender.sendMessage("§4[100H 100H]§c Setting <text_type> only requires arguments <format> <format>!");
                    return true;
                } break;

            case "reset":

                if (args.length <= 2) {
                    if (args.length < 2) {
                        commandSender.sendMessage("§4[100HP 100H]§c Are you SURE you want to reset your timer settings?");
                        commandSender.sendMessage("§4[100HP 100H]§c Run the command §4\"/timer reset confirm\"§c to confirm.");
                    } else if (args[1].equals("confirm")) {
                        Player player = ((Player) commandSender).getPlayer();
                        updateFile(player, new String[]{"timer_color", "white", "white"});
                        updateFile(player, new String[]{"timer_display", "action_bar"});
                        updateFile(player, new String[]{"timer_text_type", "normal", "normal"});
                        updateFile(player, new String[]{"timer_time_units", "shortened"});
                    }
                } else {
                    commandSender.sendMessage("§4[100H 100H]§c Reset argument only has 1 sub-argument: <confirm>!");
                } return true;
        }

        args[0] = "timer_" + args[0];
        updateFile((Player) commandSender, args);
        commandSender.sendMessage("§4[100HP 100H]§a Successfully updated §2" + args[0] + "§a.");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 1) {
            if (!args[0].isEmpty()) {return compareStrings(args[0], SETTINGS);
            } else {return Arrays.stream(SETTINGS).toList();}
        }

        else if (args.length == 2) {
            return switch (args[0]) {
                default -> Arrays.stream(new String[0]).toList();
                case "color" -> {
                    if (!args[1].isEmpty()) {
                        yield compareStrings(args[1], COLORS);
                    } else {yield Arrays.stream(COLORS).toList();}
                }
                case "display" -> {
                    if (!args[1].isEmpty()) {
                        yield compareStrings(args[1], DISPLAYS);
                    } else {yield Arrays.stream(DISPLAYS).toList();}
                }
                case "time_units" -> {
                    if (!args[1].isEmpty()) {
                        yield compareStrings(args[1], TIMEUNITS);
                    } else {yield Arrays.stream(TIMEUNITS).toList();}
                }
                case "reset" -> {
                    if (!args[1].isEmpty()) {
                        yield compareStrings(args[1], new String[]{"confirm"});
                    } else {yield Arrays.stream(new String[]{"confirm"}).toList();}
                }
            };
        }

        else if (args.length == 3) {
            return switch (args[0]) {
                default -> new ArrayList<String>(0);
                case "color" -> {
                    if (!args[2].isEmpty()) {
                        yield compareStrings(args[2], COLORS);
                    } else {yield Arrays.stream(COLORS).toList();}
                }
                case "display" -> {
                    if (!args[2].isEmpty() && args[1].equals("boss_bar")) {
                        yield compareStrings(args[2], BOSSBARCOLORS);
                    } else {yield Arrays.stream(BOSSBARCOLORS).toList();}
                }
            };
        }

        else if (args.length == 4) {
            if (args[0].equals("display")) {
                if (!args[3].isEmpty() && args[1].equals("boss_bar")) {
                    return compareStrings(args[3], BOSSBARSTYLES);
                } else {return Arrays.stream(BOSSBARSTYLES).toList();}
            } else {return new ArrayList<String>(0);}
        }

        else if (args.length == 5) {
            if (args[0].equals("display")) {
                if (!args[4].isEmpty() && args[1].equals("boss_bar")) {
                    return compareStrings(args[4], new String[]{"on", "off"});
                } else {return Arrays.stream(new String[]{"on", "off"}).toList();}
            } else {return new ArrayList<String>(0);}
        } else {return new ArrayList<String>(0);}
    }



    static boolean isValidColor(String arg) {
        String[] colors = {"black", "cyan", "blue", "green", "dark_red", "purple", "gold", "light_gray", "gray", "light_blue", "lime", "aqua", "red", "pink", "yellow", "white"};
        for (String color : colors) {
            if (arg.equals(color)) {return true;}
        } return false;
    }

    static List<String> compareStrings(String input, String[] strings) {
        String[] matches = new String[0];
        String[] candidates = new String[0];

        for (String s : strings) {
            try {candidates = Array.arrayAppend(candidates, s.substring(0, input.length()));}
            catch (StringIndexOutOfBoundsException ignored) {candidates = Array.arrayAppend(candidates, null);}
        }
        for (int i=0; i < candidates.length; i++) {
            if (candidates[i] != null) {
                if (candidates[i].equals(input)) {matches = Array.arrayAppend(matches, strings[i]);}
            }
        } return Arrays.stream(matches).toList();
    }
}