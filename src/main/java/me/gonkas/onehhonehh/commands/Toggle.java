package me.gonkas.onehhonehh.commands;

import me.gonkas.onehhonehh.OneHHOneHH;
import me.gonkas.onehhonehh.player.PlayerData;
import me.gonkas.onehhonehh.player.PlayerSettings;
import me.gonkas.onehhonehh.util.Array;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Toggle implements CommandExecutor, TabCompleter {

    String[] TOGGLES = {"timer", "goal", "sound", "title", "minimize_health","reset"};

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = ((Player) commandSender).getPlayer();
        if (args.length == 0) {
            commandSender.sendMessage("§4[100H 100H]§c Command <toggle> requires one argument <timer/title/sound/goal/minimize_health/reset>!");
            return true;
        } else if (args.length > 1) {
            commandSender.sendMessage("§4[100H 100H]§c Command <toggle> only requires one argument <timer/title/sound/goal/minimize_health/reset>.");
            return true;
        } else if (!(args[0].equals("timer") || args[0].equals("goal") || args[0].equals("sound") || args[0].equals("title") || args[0].equals("minimize_health") || args[0].equals("reset"))) {
            commandSender.sendMessage("§4[100H 100H]§c Invalid setting given! Use <timer/titles/sounds/goal/minimize_health/reset>.");
            return true;

        } else if (args[0].equals("reset")) {

            File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);

            PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

            config.set("timer-toggle", true);
            config.set("sound-toggle", true);
            config.set("title-toggle", true);
            config.set("goal-toggle", false);
            config.set("minimize-health", false);

            settings.timer_toggle = true;
            settings.sound_toggle = true;
            settings.title_toggle = true;
            settings.goal_toggle = false;
            settings.minimize_health = false;

            try {config.save(player_file);}
            catch (IOException e) {PlayerData.log("§4[100HP 100H] Error occurred when trying to save player §r§c<" + player.getName() + ">§r§4's data.");}

            commandSender.sendMessage("§4[100HP 100H]§a Successfully reset all toggles.");
            return true;
        }

        boolean state = switch (args[0]) {
            default -> false;
            case "timer" -> !OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getTimerToggle();
            case "sound" -> !OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getSoundToggle();
            case "title" -> !OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getTitleToggle();
            case "goal" -> !OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getGoalToggle();
            case "minimize_health" -> !OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getHPBarsDisplay();
        };

        String string;
        if (state) {string = "on";}
        else {string = "off";}

        commandSender.sendMessage("§4[100HP 100H]§a Successfully toggled §2" + args[0] + "§a to §2" + string + "§a.");

        args[0] += "_toggle";
        PlayerData.updateFile((Player) commandSender, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            if (!args[0].isEmpty()) {return compareStrings(args[0], TOGGLES);}
            else {return Arrays.stream(TOGGLES).toList();}
        } return new ArrayList<String>(0);
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