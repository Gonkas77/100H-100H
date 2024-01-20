package me.gonkas.onehhonehh.player;

import me.gonkas.onehhonehh.OneHHOneHH;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerSettings {

    boolean timer_toggle;
    boolean sound_toggle;
    boolean title_display;

    String timer_display;
    String hp_bars_display;

    String[] timer_color;
    String[] timer_text_type;

    public PlayerSettings(Player player) {
        File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);

        timer_toggle = config.getBoolean("timer-toggle");
        sound_toggle = config.getBoolean("sound-toggle");
        title_display = config.getBoolean("title-display");

        timer_display = config.getString("timer-display");
        hp_bars_display = config.getString("hp-bars-display");

        timer_color = colorEncoder(config.getString("timer-color").split("-"));
        timer_text_type = textTypeEncoder(config.getString("timer-text-type").split("-"));
    }

    String[][] settingsSplitter(String[] array) {
        String[] temp1 = array[0].split("_");
        String[] temp2 = array[1].split("_");
        return new String[][]{temp1, temp2};
    }

    String[] textTypeEncoder(String[] array) {

        String[][] text = settingsSplitter(array);
        String[] result = new String[text.length];

        for (int i=0; i < text.length; i++) {
            for (String setting : text[i]) {
                switch (setting) {
                    case "normal":
                        result[i] = "§r";
                        break;
                    case "bold":
                        result[i] += "§l";
                        break;
                    case "italic":
                        result[i] += "§o";
                        break;
                    case "underlined":
                        result[i] += "§n";
                        break;
                }
            }
        } return result;
    }

    String[] colorEncoder(String[] array) {

        String[] result = new String[array.length];

        for (int i=0; i < array.length; i++) {
            switch (array[i]) {
                case "black":
                    result[i] = "§0";
                    break;
                case "blue":
                    result[i] = "§1";
                    break;
                case "green":
                    result[i] = "§2";
                    break;
                case "cyan":
                    result[i] = "§3";
                    break;
                case "dark_red":
                    result[i] = "§4";
                    break;
                case "purple":
                    result[i] = "§5";
                    break;
                case "gold":
                    result[i] = "§6";
                    break;
                case "light_gray":
                    result[i] = "§7";
                    break;
                case "gray":
                    result[i] = "§8";
                    break;
                case "light_blue":
                    result[i] = "§9";
                    break;
                case "lime":
                    result[i] = "§a";
                    break;
                case "aqua":
                    result[i] = "§b";
                    break;
                case "red":
                    result[i] = "§c";
                    break;
                case "pink":
                    result[i] = "§d";
                    break;
                case "yellow":
                    result[i] = "§e";
                    break;
                case "white":
                    result[i] = "§f";
                    break;
            }
        } return result;
    }

    public boolean getTimerToggle() {return timer_toggle;}
    public boolean getSoundToggle() {return sound_toggle;}
    public boolean getTitleDisplay() {return title_display;}
    public String getTimerDisplay() {return timer_display;}
    public String getHPBarsDisplay() {return hp_bars_display;}
    public String[] getTimerColor() {return timer_color;}
    public String[] getTimerTextType() {return timer_text_type;}
}