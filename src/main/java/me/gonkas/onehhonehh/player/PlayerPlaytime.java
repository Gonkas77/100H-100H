package me.gonkas.onehhonehh.player;

import me.gonkas.onehhonehh.OneHHOneHH;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerPlaytime {

    public static String getPlaytime(Player player) {
        int playtimeInSeconds = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20; // Convert from ticks to seconds
        int seconds = playtimeInSeconds % 60;
        int minutes = playtimeInSeconds / 60;
        int hours = playtimeInSeconds / 3600;

        return hours + "h " + minutes + "min " + seconds + "s";
    }

    public static void displayTimer(Player player) {
        PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

        String playtime_color = settings.getTimerColor()[0];
        String playtime_type = settings.getTimerTextType()[0];
        String timer_color = settings.getTimerColor()[1];
        String timer_type = settings.getTimerTextType()[1];

        Scoreboard board = OneHHOneHH.MAINSCOREBOARD;
        Objective objective;
        Score score;

        if (settings.getTimerToggle()) {
            switch (settings.getTimerDisplay()) {

                case "action_bar":
                    player.sendActionBar(playtime_color + playtime_type + "Playtime: §r" + timer_color + timer_type + getPlaytime(player));
                    break;

                case "boss_bar":
                    OneHHOneHH.BOSSBAR = Bukkit.createBossBar(playtime_color + playtime_type + "Playtime: §r" + timer_color + timer_type + getPlaytime(player), BarColor.RED, BarStyle.SOLID);
                    OneHHOneHH.BOSSBAR.addPlayer(player);
                    break;

                case "scoreboard":
                    objective = board.getObjective(player.getName() + "_timer");
                    if (objective == null) {objective = board.registerNewObjective(player.getName() + "_timer", Criteria.DUMMY, playtime_color + playtime_type + "Playtime");}

                    score = objective.getScore(timer_color + timer_type + getPlaytime(player));
                    score.setScore(0);
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    player.setScoreboard(board);
                    break;

                case "title":
                    player.sendTitle("", playtime_color + playtime_type + "Playtime: §r" + timer_color + timer_type + getPlaytime(player));
                    break;
            }
        }
    }
}