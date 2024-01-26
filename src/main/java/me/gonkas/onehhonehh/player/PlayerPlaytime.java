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
        int minutes = (playtimeInSeconds / 60) % 60;
        int hours = playtimeInSeconds / 3600;

        PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

        if (settings.getTimerDisplay().equals("colon")) {
            return hours + settings.getTimerTimeUnits()[0] + minutes + settings.getTimerTimeUnits()[1] + seconds;
        } else {
            return hours + settings.getTimerTimeUnits()[0] + " " + minutes + settings.getTimerTimeUnits()[1] + " " + seconds + settings.getTimerTimeUnits()[2];
        }
    }

    public static String resetOldTimer(Player player, String time) {
        int playtimeInSeconds = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20; // Convert from ticks to seconds
        int seconds = playtimeInSeconds % 60;
        int minutes = (playtimeInSeconds / 60) % 60;
        int hours = playtimeInSeconds / 3600;

        PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

        return switch (time) {
            case "hours" ->
                    hours + settings.getTimerTimeUnits()[0] + 59 + settings.getTimerTimeUnits()[1] + 59 + settings.getTimerTimeUnits()[2];
            case "minutes" ->
                    hours + settings.getTimerTimeUnits()[0] + (minutes - 1) + settings.getTimerTimeUnits()[1] + 59 + settings.getTimerTimeUnits()[2];
            case "seconds" ->
                    hours + settings.getTimerTimeUnits()[0] + minutes + settings.getTimerTimeUnits()[1] + (seconds - 1) + settings.getTimerTimeUnits()[2];
            default -> "";
        };
    }

    public static double getHours(Player player) {
        return (double) player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 3600;
    }


    public static void displayTimer(Player player) {
        PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

        String playtime_color = settings.getTimerColor()[0];
        String playtime_type = settings.getTimerTextType()[0];
        String timer_color = settings.getTimerColor()[1];
        String timer_type = settings.getTimerTextType()[1];

        if (!(settings.getTimerDisplay().equals("scoreboard")) && (OneHHOneHH.MAINSCOREBOARD.getObjective(player.getName() + "_timer") != null)) {
            OneHHOneHH.MAINSCOREBOARD.getObjective(player.getName() + "_timer").unregister();
        }

        if (settings.getTimerToggle()) {
            switch (settings.getTimerDisplay()) {

                case "action_bar":
                    player.sendActionBar(playtime_type + playtime_color + "Playtime: §r" + timer_type + timer_color + getPlaytime(player));
                    break;

                case "boss_bar":

                    BarColor bar_color = settings.getTimerBossbarColor();
                    BarStyle bar_style = settings.getTimerBossbarStyle();

                    if (OneHHOneHH.BOSSBAR.get(player) != null) {OneHHOneHH.BOSSBAR.get(player).removePlayer(player);} // -> removes player from old bossbar
                    OneHHOneHH.BOSSBAR.put(player, Bukkit.createBossBar(playtime_type + playtime_color + "Playtime: §r" + timer_type + timer_color + getPlaytime(player), bar_color, bar_style));

                    // boss bar progress/not
                    if (settings.getTimerBossbarProgression()) {OneHHOneHH.BOSSBAR.get(player).setProgress(getHours(player) / 100);}
                    else {OneHHOneHH.BOSSBAR.get(player).setProgress(100);}

                    OneHHOneHH.BOSSBAR.get(player).addPlayer(player);
                    break;

                case "scoreboard":
                    Scoreboard board = OneHHOneHH.MAINSCOREBOARD;
                    player.setScoreboard(board);

                    Objective objective = board.getObjective(player.getName() + "_timer");
                    if (objective == null) {objective = board.registerNewObjective(player.getName() + "_timer", Criteria.DUMMY, playtime_type + playtime_color + "Playtime");}
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    Score score = objective.getScore(timer_type + timer_color + getPlaytime(player));
                    score.setScore(0);

                    objective.getScore(timer_type + timer_color + resetOldTimer(player, "hours")).resetScore(); // reset old
                    objective.getScore(timer_type + timer_color + resetOldTimer(player, "minutes")).resetScore(); // reset old
                    objective.getScore(timer_type + timer_color + resetOldTimer(player, "seconds")).resetScore(); // reset old
                    break;

                case "title":
                    player.sendTitle("", playtime_type + playtime_color + "Playtime: §r" + timer_type + timer_color + getPlaytime(player), 0, 30, 0);
                    break;
            }
        }
    }
}