package de.cooltechno.mcpvp.commands;

import de.cooltechno.mcpvp.Main;
import de.cooltechno.mcpvp.util.EnderChestLogic;
import org.bukkit.block.EnderChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class command_ec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){


        EnderChestLogic EcL = new EnderChestLogic();

        if (!(sender instanceof Player)){
            sender.sendMessage(Main.prefix + "You need to be a player in order to execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1){
            player.sendMessage(Main.prefix + " §4Wrong Syntax: /ec <save/load>");
            return true;
        }

        if (args[0].equalsIgnoreCase("save")){
            try {
                EcL.saveEnderChest(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("load")){
            try {
                EcL.loadEnderchest(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        player.sendMessage(Main.prefix + " §4Wrong Syntax: /ec <save/load>");
        return false;
    }

}
