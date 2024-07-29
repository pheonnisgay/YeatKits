package de.cooltechno.mcpvp.commands;

import de.cooltechno.mcpvp.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class command_ping implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){


        if (!(sender instanceof Player)){
            sender.sendMessage(Main.prefix + "You need to be a player in order to execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1){
            Player target = Bukkit.getPlayerExact(args[0]);

            if (target == null){
                player.sendMessage(Main.prefix + "Player does not exist");
                return true;
            }
            int pingtempt = target.getPing();

            String ping = "";

            if (pingtempt < 40){
                ping = ChatColor.GREEN + String.valueOf(pingtempt);
            }

            if (pingtempt < 80 && pingtempt > 40){
                ping = ChatColor.YELLOW + String.valueOf(pingtempt);
            }

            if (pingtempt > 80){
                ping = ChatColor.RED+ String.valueOf(pingtempt);
            }

            player.sendMessage(Main.prefix + target.getName() + "'s ping is " + ping);

            return true;
        }


        int pingtempp = player.getPing();


        String ping = "";

        if (pingtempp < 40){
            ping = ChatColor.GREEN + String.valueOf(pingtempp);
        }

        if (pingtempp < 80 && pingtempp > 40){
            ping = ChatColor.YELLOW + String.valueOf(pingtempp);
        }

        if (pingtempp > 80){
            ping = ChatColor.RED+ String.valueOf(pingtempp);
        }

        player.sendMessage(Main.prefix + "Your Ping Is " + ping);

        return true;
    }

}
