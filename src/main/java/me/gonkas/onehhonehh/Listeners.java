package me.gonkas.onehhonehh;

import me.gonkas.onehhonehh.player.PlayerData;
import me.gonkas.onehhonehh.player.PlayerPlaytime;
import me.gonkas.onehhonehh.player.PlayerSettings;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.IOException;

public class Listeners implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) throws IOException {
        Player player = event.getPlayer();

        PlayerData.createFile(player);
        OneHHOneHH.PLAYERSETTINGS.put(player.getUniqueId(), new PlayerSettings(player.getUniqueId()));

        if (player.getStatistic(Statistic.PLAY_ONE_MINUTE) == 0) {

            // console logs
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r joined for the first time!");
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r's HP has been set to 200.");
            // -----------

            // actual logic
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
            player.setHealth(200);

        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if ((event.getEntity().getType()) != EntityType.PLAYER) {return;}

        Player player = (Player) event.getEntity();

        // console logs
        OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r took §4" + event.getFinalDamage() + "§r damage.");
        OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r's HP has been updated.");
        // -----------

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getHealth() - event.getFinalDamage());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            // console logs
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + event.getPlayer().getName() + "§4 has died!§r They survived " + PlayerPlaytime.getPlaytime(event.getPlayer()) + ".");
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Death title and subtitle displayed to everyone online.");
            // -----------

            PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

            if (settings.getTitleDisplay()) {
                player.sendTitle("§c" + event.getPlayer().getName(), "survived " + PlayerPlaytime.getPlaytime(event.getPlayer()) + "!", 10, 80, 10);
            }

            if (settings.getSoundToggle()) {
                player.playSound(player, Sound.BLOCK_END_PORTAL_SPAWN, SoundCategory.MASTER, 100f, 1.2f);
            }
        }
    }
}