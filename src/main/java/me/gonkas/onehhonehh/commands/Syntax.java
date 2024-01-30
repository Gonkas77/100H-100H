package me.gonkas.onehhonehh.commands;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Syntax implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            commandSender.sendMessage("§4[100HP 100H]§c Command <syntax> requires no arguments!");
            return true;
        }

        Player player = ((Player) commandSender).getPlayer();
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 100f, 1.2f);

        commandSender.sendMessage("");
        commandSender.sendMessage("§a-------------- §4[100H 100H]§2 Commands syntax§a --------------");
        commandSender.sendMessage("");
        commandSender.sendMessage("  §2/toggle <timer/sound/title/goal/minimize_health>");
        commandSender.sendMessage("  §ainverts the specified setting");
        commandSender.sendMessage("  §7§o(if, for example, timer is set to true: will invert to false)");
        commandSender.sendMessage("");
        commandSender.sendMessage("  §2/toggle reset");
        commandSender.sendMessage("  §asets all the toggle values back to their original state");
        commandSender.sendMessage("  §7§o(defined by the plugin's config.yml file)");
        commandSender.sendMessage("");
        commandSender.sendMessage("  §2/timer color <color1> <color2>");
        commandSender.sendMessage("  §a<color1> defines the <\"Playtime:\">'s color");
        commandSender.sendMessage("  §a<color2> defines the actual timer's color");
        commandSender.sendMessage("");
        commandSender.sendMessage("  §2/timer display <display> [<args>]");
        commandSender.sendMessage("  §a<display> defines where the timer is displayed");
        commandSender.sendMessage("  §ain case the display is \"boss_bar\" [<args>] defines, in order,");
        commandSender.sendMessage("  §a<color> <style> <progress> but only if the display is \"boss_bar\"");
        commandSender.sendMessage("  §7§o<progress> defines whether or not the boss bar");
        commandSender.sendMessage("  §7§oprogresses along with your playtime or not");
        commandSender.sendMessage("");
        commandSender.sendMessage("  §2/timer text_type <text_type1> <text_type2>");
        commandSender.sendMessage("  §a<text_type1> defines the <\"Playtime:\">'s text type (bold, italic, etc.)");
        commandSender.sendMessage("  §a<text_type2> defines the actual timer's text type");
        commandSender.sendMessage("");
        commandSender.sendMessage("  §2/timer time_units <unit_appearance>");
        commandSender.sendMessage("  §athis will alter how the units show in the timer");
        commandSender.sendMessage("  §7§o<colon> changes units to \":\" and remove the space between them");
        commandSender.sendMessage("  §7§o<single_character> changes units to \"h\", \"m\" and \"s\"");
        commandSender.sendMessage("  §7§o<shortened> changes units to \"h\", \"min\" and \"s\"");
        commandSender.sendMessage("  §7§o<full> changes units to \"hours\", \"minutes\" and \"seconds\"");
        commandSender.sendMessage("");
        commandSender.sendMessage("  §2/timer reset [confirm]");
        commandSender.sendMessage("  §cresets ALL timer settings");
        commandSender.sendMessage("  §ccan only be used with <confirm> after the command");
        commandSender.sendMessage("");
        commandSender.sendMessage("§a--------------------------------------------------------");
        commandSender.sendMessage("");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return new ArrayList<>(0);
    }
}