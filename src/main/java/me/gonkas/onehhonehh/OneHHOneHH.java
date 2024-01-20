package me.gonkas.onehhonehh;

import me.gonkas.onehhonehh.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;

public class OneHHOneHH extends JavaPlugin {

    public static File PLAYERDATAFOLDER;
    public static ConsoleCommandSender CONSOLE;

    @Override
    public void onEnable() {

        PLAYERDATAFOLDER = new File("plugins/OneHHOneHH/player_data");
        if (!PLAYERDATAFOLDER.exists()) {PLAYERDATAFOLDER.mkdirs();}

        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        getCommand("sethp").setExecutor(new SetHP());
        getCommand("settings").setExecutor(new Settings());

        CONSOLE = Bukkit.getConsoleSender();
        CONSOLE.sendMessage("");
        CONSOLE.sendMessage("§4[100H 100H]§a Plugin v0.8 loaded successfully!");
        CONSOLE.sendMessage("");
    }
}