package me.gonkas.onehhonehh;

import me.gonkas.onehhonehh.player.PlayerData;
import me.gonkas.onehhonehh.player.PlayerPlaytime;
import me.gonkas.onehhonehh.player.PlayerSettings;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Listeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();

        PlayerData.createFile(player);
        OneHHOneHH.PLAYERSETTINGS.put(player.getUniqueId(), new PlayerSettings(player.getUniqueId()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerData.updateFile(player, new String[]{"hp", String.valueOf(OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getHP())});
        OneHHOneHH.PLAYERSETTINGS.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if ((event.getEntity().getType()) != EntityType.PLAYER) {return;}

        Player player = (Player) event.getEntity();

        // console logs
        OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r took §4" + event.getFinalDamage() + "§r damage.");
        OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r's HP has been updated to §4" + (player.getHealth() - event.getFinalDamage()) + "§r.");
        // -----------

        PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

        settings.hp -= event.getFinalDamage() + 0.1;
        double hp = settings.hp;

        event.setDamage(0.1);

        if (hp > 0) {
            if (settings.getHPBarsDisplay().equals("minimized")) {

                double value;
                if (hp % 20 == 0) {value = 40;}
                else {value = hp % 20 + 20;}

                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
                player.setHealth(value);

            } else {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
                player.setHealth(hp);
            }
        } else {
            player.setHealth(0);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            // console logs
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + event.getPlayer().getName() + "§4 has died!§r They survived for " + PlayerPlaytime.getPlaytime(event.getPlayer()) + ".");
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Death title and subtitle displayed to everyone online.");
            // -----------

            PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

            if (settings.getTitleToggle()) {
                player.sendTitle("§c" + event.getPlayer().getName(), "survived " + PlayerPlaytime.getPlaytime(event.getPlayer()) + "!", 10, 80, 10);
            }

            player.sendMessage("");
            player.sendMessage("§c" + event.getPlayer().getName() + "§r survived " + PlayerPlaytime.getPlaytime(event.getPlayer()) + "!");
            player.sendMessage("");

            if (settings.getSoundToggle()) {
                player.playSound(player, Sound.BLOCK_END_PORTAL_SPAWN, SoundCategory.MASTER, 100f, 1.2f);
            }
        }
    }
}