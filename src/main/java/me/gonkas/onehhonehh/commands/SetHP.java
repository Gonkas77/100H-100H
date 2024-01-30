package me.gonkas.onehhonehh.commands;

import me.gonkas.onehhonehh.OneHHOneHH;
import me.gonkas.onehhonehh.player.PlayerData;
import me.gonkas.onehhonehh.util.Array;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
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

public class SetHP implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            commandSender.sendMessage("§4[100HP 100H]§c Command <sethp> requires arguments <player> <value>!");
            return true;
        }

        if (Double.parseDouble(args[1]) > 2048) {
            commandSender.sendMessage("§4[100HP 100H]§c The maximum HP value is 2048!");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        double hp = Double.parseDouble(args[1]);

        if (target == null) {
            commandSender.sendMessage("§4[100HP 100H]§c Player not found.");
            return true;
        }

        if (hp > 0) {
            if (OneHHOneHH.PLAYERSETTINGS.get(target.getUniqueId()).getHPBarsDisplay()) {
                target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
                target.setHealth(hp / 5);
            } else {
                target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
                target.setHealth(hp);
            }
        } else {
            target.setHealth(0);
        }

        PlayerData.updateFile(target, new String[]{"hp", String.valueOf(hp)});

        commandSender.sendMessage("§4[100HP 100H]§a Successfully set §2" + target.getName() + "§a's HP to §2" + hp + "§a.");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 2) {
            return compareStrings(args[0], Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
        } else if (args.length == 2){
            return Arrays.stream(new String[]{"<value>"}).toList();
        } else {return new ArrayList<String>(0);}
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