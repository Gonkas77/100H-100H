package me.gonkas.onehhonehh.player;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class PlayerPlaytime {
    public static String getPlaytime(Player player) {
        int playtimeInSeconds = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20; // Convert from ticks to seconds
        int seconds = playtimeInSeconds % 60;
        int minutes = playtimeInSeconds / 60;
        int hours = playtimeInSeconds / 3600;

        return hours + "h " + minutes + "min " + seconds + "s";
    }

    public static void displayTimer(Player player) {
        player.sendActionBar("§d§lPlaytime: §r" + getPlaytime(player) + ".");
    }
}
