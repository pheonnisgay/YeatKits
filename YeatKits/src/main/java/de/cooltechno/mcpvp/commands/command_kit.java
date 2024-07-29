package de.cooltechno.mcpvp.commands;

import de.cooltechno.mcpvp.Main;
import de.cooltechno.mcpvp.util.KitLogic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.K;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class command_kit implements CommandExecutor {
    KitLogic kit = new KitLogic();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,  String[] args){
        if (!(sender instanceof Player)){
            sender.sendMessage(Main.prefix + "You need to be a player in order to execute this command.");
            return true;
        }
        Player player = (Player) sender;

       if (args.length > 2 || args.length < 1){
           giveErrorMessage(player);
            return true;
        }

           if (args[0].equalsIgnoreCase("save")) {
               String name = args[1];
               if (name == null){
                   giveErrorMessage(player);
                   return true;
               }
               try {
                   kit.saveKit(player, name);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               return true;
           }

           if (args[0].equalsIgnoreCase("delete")) {
               String name = args[1];
               if (name == null){
                   giveErrorMessage(player);
                   return true;
               }
               kit.deleteKit(player, name);
               return true;
           }

           if (args[0].equalsIgnoreCase("load")) {
               String name = args[1];
               player.setHealthScale(20);
               player.setSaturation(20);
               if (name == null){
                   giveErrorMessage(player);
                   return true;
               }
               try {
                   kit.loadKit(player, name);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               return true;
           }

        if(args[0].equalsIgnoreCase("list")){
            String[] kits = kit.listKits(player);
            String allkits = "";


            if (kits.length == 0){
                player.sendMessage(Main.prefix + "You have no kits created yet");
                return true;
            }
            for (int i = 0; i < kits.length; i++) {

                //debug info
                System.out.println("Kit " + i + ": " + kits[i]);

                String kittemp = kits[i];
                String[] temp = kittemp.split("-");
                String[] temp2 = temp[6].split("\\.");

                String kitp = temp2[0];

                int j = i + 1;
                allkits = allkits + " §l§r[§0YeatKits§l§r] " +j + ": " + kitp + "\n";
            }
                player.sendMessage(allkits);
            return true;
        }
        giveErrorMessage(player);
        return true;
    }


    public void giveErrorMessage(Player player){

        player.sendMessage(Main.prefix + " §4Wrong Syntax: /kit <save/delete/load/list> <kit name>");


    }

}
