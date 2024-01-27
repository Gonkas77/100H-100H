package me.gonkas.onehhonehh.commands;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Syntax implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 1) {
            commandSender.sendMessage("§4[100HP 100H]§c Command <syntax> requires only arguments <setting>!");
            return true;
        }

        if (!(args[0].equals("timer_toggle") || args[0].equals("timer_display") || args[0].equals("timer_color") || args[0].equals("timer_text_type") || args[0].equals("sound_toggle") || args[0].equals("title_toggle") || args[0].equals("goal_toggle") || args[0].equals("timer_time_units") || args[0].equals("hp_bars_display"))) {
            commandSender.sendMessage("§4[100HP 100H]§c Invalid setting provided!");
            return true;
        }

        switch (args[0]) {

            case "timer_toggle", "sound_toggle", "title_toggle", "goal_toggle":
                commandSender.sendMessage("§4[100HP 100H]§a Syntax for setting §c" + args[0] + "§a is §c<setting> <on/off>§a!");
                break;

            case "timer_display":
                commandSender.sendMessage("§4[100HP 100H]§a Syntax for setting §c" + args[0] + "§a is §c<setting> <action_bar/boss_bar/title/scoreboard>§a!");
                commandSender.sendMessage("§4[100HP 100H]§a OR if §c\"boss_bar\"§a is the current selected display: §c<setting> <boss_bar> <color> <style> <on/off (whether the boss_bar increases with timer or not)>§a!");
                break;

            case "timer_color":
                commandSender.sendMessage("§4[100HP 100H]§a Syntax for setting §c" + args[0] + "§a is §c<setting> <\"Playtime:\" color> <numbers color>§a!");
                break;

            case "timer_text_type":
                commandSender.sendMessage("§4[100HP 100H]§a Syntax for setting §c" + args[0] + "§a is §c<setting> <\"Playtime:\" text type> <numbers text type>§a!");
                break;

            case "timer_time_units":
                commandSender.sendMessage("§4[100HP 100H]§a Syntax for setting §c" + args[0] + "§a is §c<setting> <colon/single_character/shortened/full>§a!");
                break;

            case "hp_bars_display":
                commandSender.sendMessage("§4[100HP 100H]§a Syntax for setting §c" + args[0] + "§a is §c<setting> <all/minimized>§a!");
                break;

        } return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.stream(new String[]{"timer_toggle", "timer_display", "timer_color", "timer_text_type", "sound_toggle", "title_toggle", "timer_time_units", "hp_bars_display"}).toList();
        } else {
            return new ArrayList<>(0);
        }
    }
}