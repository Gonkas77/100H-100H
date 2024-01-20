package me.gonkas.onehhonehh.player;

import me.gonkas.onehhonehh.OneHHOneHH;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerSettings {

    PlayerSettings(Player player) {
        File player_file = new File(OneHHOneHH.PLAYERDATAFOLDER, player.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(player_file);

        boolean timer_toggle = config.getBoolean("timer-toggle");
        boolean sound_toggle = config.getBoolean("sound-toggle");
        boolean title_display = config.getBoolean("title-display");

        String timer_display = config.getString("timer-display");
        String[] timer_color = config.getString("timer-color").split("-");
    }
}
