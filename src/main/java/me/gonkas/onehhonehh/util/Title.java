package me.gonkas.onehhonehh.util;

import me.gonkas.onehhonehh.OneHHOneHH;
import me.gonkas.onehhonehh.player.PlayerPlaytime;
import org.bukkit.entity.Player;

public class Title {

    public static String TextDecoder(String string, Player player) {
        String[] array = string.split("%");

        if (array.length > 1) {
            for (int i=0; i < array.length; i++) {
                switch (array[i]) {
                    case "player":
                        array[i] = player.getName();
                        break;
                    case "playtime":
                        array[i] = PlayerPlaytime.getPlaytime(player);
                        break;
                    case "remaining":
                        array[i] = String.valueOf(100 - OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getHours());
                        break;
                }
            }
        } return ArrayPrinter(array);
    }

    public static String ColorDecoder(String string) {
        return switch (string) {
            case "black" -> "§0";
            case "blue" -> "§1";
            case "green" -> "§2";
            case "cyan" -> "§3";
            case "dark_red" -> "§4";
            case "purple" -> "§5";
            case "gold" -> "§6";
            case "light_gray" -> "§7";
            case "gray" -> "§8";
            case "light_blue" -> "§9";
            case "lime" -> "§a";
            case "aqua" -> "§b";
            case "red" -> "§c";
            case "pink" -> "§d";
            case "yellow" -> "§e";
            case "white" -> "§f";
            default -> "";
        };
    }

    public static String ArrayPrinter(String[] array) {
        StringBuilder result = new StringBuilder();
        for (String string : array) {result.append(string);}
        return result.toString();
    }
}