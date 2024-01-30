package me.gonkas.onehhonehh.commands;

import me.gonkas.onehhonehh.OneHHOneHH;
import me.gonkas.onehhonehh.util.Array;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetHP implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            commandSender.sendMessage("§4[100HP 100H]§a Your HP is currently §2" + Math.round(OneHHOneHH.PLAYERSETTINGS.get(((Player) commandSender).getUniqueId()).getHP()) + "§a.");
        } else if (args.length == 1) {

            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                commandSender.sendMessage("§4[100HP 100H]§c " + target.getName() + "§a's HP is currently §2" + Math.round(OneHHOneHH.PLAYERSETTINGS.get(target.getUniqueId()).getHP()) + "§a.");
            } else {
                commandSender.sendMessage("§4[100HP 100H]§c Player not found!");
            }

        } return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1 && commandSender instanceof ConsoleCommandSender) {
            return compareStrings(args[0], Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
        } else {return new ArrayList<>(0);}
    }

    static List<String> compareStrings(String input, List<String> strings) {
        String[] matches = new String[0];
        String[] candidates = new String[0];

        for (String s : strings) {
            try {candidates = Array.arrayAppend(candidates, s.substring(0, input.length()));}
            catch (StringIndexOutOfBoundsException ignored) {candidates = Array.arrayAppend(candidates, null);}
        }
        for (int i=0; i < candidates.length; i++) {
            if (candidates[i] != null) {
                if (candidates[i].equals(input)) {matches = Array.arrayAppend(matches, strings.get(i));}
            }
        } return Arrays.stream(matches).toList();
    }
}