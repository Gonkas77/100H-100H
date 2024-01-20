package me.gonkas.onehhonehh;

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

public class Listeners implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        PlayerData.createFile(player);

        if (player.getStatistic(Statistic.PLAY_ONE_MINUTE) == 0) {

            // console logs
            OneHHOneHH.CONSOLE.sendMessage("§4[SoareTimer]§r Player §a" + player.getName() + "§r joined for the first time!");
            OneHHOneHH.CONSOLE.sendMessage("§4[SoareTimer]§r Player §a" + player.getName() + "§r's HP has been set to 200.");
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
        OneHHOneHH.CONSOLE.sendMessage("§4[SoareTimer]§r Player §a" + player.getName() + "§r took §4" + event.getFinalDamage() + "§r damage.");
        OneHHOneHH.CONSOLE.sendMessage("§4[SoareTimer]§r Player §a" + player.getName() + "§r's HP has been updated.");
        // -----------

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getHealth() - event.getFinalDamage());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            // console logs
            OneHHOneHH.CONSOLE.sendMessage("§4[SoareTimer]§r Player §a" + event.getPlayer().getName() + "§4 has died!§r They survived " + PlayerPlaytime.getPlaytime(event.getPlayer()) + ".");
            OneHHOneHH.CONSOLE.sendMessage("§4[SoareTimer]§r Death title and subtitle displayed to everyone online.");
            // -----------

            player.sendTitle("§c" + event.getPlayer().getName(), "survived " + PlayerPlaytime.getPlaytime(event.getPlayer()) + "!", 10, 80, 10);
        }
    }
}
