package de.cooltechno.mcpvp;

import de.cooltechno.mcpvp.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static Main plugin;
    public static String prefix = "§l§r[§0YeatKits§l§r]: ";


    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("Loading YeatKits Plugin");

        //register commands
        getCommand("kit").setExecutor(new command_kit());
        getCommand("rtp").setExecutor(new command_rtp());
        getCommand("ping").setExecutor(new command_ping());
        getCommand("ec").setExecutor(new command_ec());

        //register listener
    }

    public static Main getPlugin(){
        return plugin;
    }

}
