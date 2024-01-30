package me.gonkas.onehhonehh;

import me.gonkas.onehhonehh.player.PlayerData;
import me.gonkas.onehhonehh.player.PlayerPlaytime;
import me.gonkas.onehhonehh.player.PlayerSettings;
import me.gonkas.onehhonehh.util.Title;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

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
        PlayerData.updateFile(player, new String[]{"hours", String.valueOf(OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId()).getHours())});
        OneHHOneHH.PLAYERSETTINGS.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if ((event.getEntity().getType()) != EntityType.PLAYER) {return;}
        if (event.getFinalDamage() < 0) {return;}

        Player player = (Player) event.getEntity();
        if (player.isBlocking()) {return;}

        // console logs
        OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r took §4" + event.getFinalDamage() + "§r damage.");
        OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + player.getName() + "§r's HP has been updated to §4" + (player.getHealth() - event.getFinalDamage()) + "§r.");
        // -----------

        PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

        settings.hp -= event.getFinalDamage() + 0.1;
        double hp = settings.hp;

        event.setDamage(0.1);

        if (hp > 0) {

            if (OneHHOneHH.CONFIG.getBoolean("health.force-minimize")) {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
                player.setHealth(hp / 5);

            } else {
                if (settings.getHPBarsDisplay()) {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
                    player.setHealth(hp / 5);
                } else {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
                }
            }
        } else {
            PlayerDeath(player);
        }
    }

    public void PlayerDeath(Player dead) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            String playtime = PlayerPlaytime.getPlaytime(dead);

            // console logs
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Player §a" + dead.getName() + "§4 has died!§r They survived for §a" + playtime + "§r.");
            OneHHOneHH.CONSOLE.sendMessage("§4[100H 100H]§r Death title and subtitle displayed to everyone online.");
            // -----------

            PlayerSettings settings = OneHHOneHH.PLAYERSETTINGS.get(player.getUniqueId());

            player.sendMessage("");
            player.sendMessage("§c" + dead.getName() + "§r survived " + playtime + "!");
            player.sendMessage("");

            if (!(OneHHOneHH.CONFIG.getBoolean("title.force-off"))) {
                if (settings.getTitleToggle() && OneHHOneHH.CONFIG.getBoolean("title.display.death")) {
                    player.sendTitle(
                            Title.ColorDecoder(OneHHOneHH.CONFIG.getString("title.title.death.color")) + Title.TextDecoder(OneHHOneHH.CONFIG.getString("title.title.death.text"), dead),
                            Title.ColorDecoder(OneHHOneHH.CONFIG.getString("title.subtitle.death.color")) + Title.TextDecoder(OneHHOneHH.CONFIG.getString("title.subtitle.death.text"), dead),
                            10, 80, 10);
                }
            }

            if (!(OneHHOneHH.CONFIG.getBoolean("sound.force-off"))) {
                if (settings.getSoundToggle()) {
                    player.playSound(player, Sound.BLOCK_END_PORTAL_SPAWN, 100f, 1.2f);
                }
            }
        } dead.setHealth(0);
    }
}