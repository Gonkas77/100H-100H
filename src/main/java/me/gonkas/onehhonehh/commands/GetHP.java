package me.gonkas.onehhonehh.commands;

import me.gonkas.onehhonehh.OneHHOneHH;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        } else {return new ArrayList<>(0);}
    }
}