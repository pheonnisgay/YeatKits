package de.cooltechno.mcpvp.util;

import de.cooltechno.mcpvp.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KitLogic {
    public int countFilesStartsWithPlayerName(String UUID, File directory) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(UUID + "-kit");
            }
        };

        String[] children = directory.list(filter);

        // If children array is null, the directory doesn't exist or an I/O error occurred
        if (children == null) {
            return 0;
        } else {
            // Return the count of files starting with the player's name
            return children.length;
        }
    }

    public void saveKit(Player player, String name) throws IOException {
        File directory = new File(Main.getPlugin().getDataFolder().getAbsolutePath());
        File f = new File(Main.getPlugin().getDataFolder().getAbsolutePath(), player.getUniqueId() + "-kit-" + name.toLowerCase() + ".yml");

        if (f.exists()){
            player.sendMessage(Main.prefix + "§4A kit with the name " + name + " exists already");
            return;
        }

        int playerKitCount = countFilesStartsWithPlayerName(player.getUniqueId().toString(), directory);

        if (playerKitCount < 4) {
            player.sendMessage(Main.prefix + name + ChatColor.GREEN +" was successfully created");
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);
            c.set("inventory.armor", player.getInventory().getArmorContents());
            c.set("inventory.content", player.getInventory().getContents());
            c.save(f);
        } else {
            player.sendMessage(Main.prefix + "You have already saved 4 kits");
        }
    }

    public void loadKit(Player player, String name) throws IOException {
        File f = new File(Main.getPlugin().getDataFolder().getAbsolutePath(), player.getUniqueId() + "-kit-" + name.toLowerCase() + ".yml");
        if (!f.exists()){
            player.sendMessage(Main.prefix + "§4There is no kit with that name");
        }else {
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);

            List<ItemStack> armorContents = (List<ItemStack>) c.getList("inventory.armor", new ArrayList<>());
            ItemStack[] armor = armorContents.toArray(new ItemStack[0]);
            player.getInventory().setArmorContents(armor);

            List<ItemStack> inventoryContents = (List<ItemStack>) c.getList("inventory.content", new ArrayList<>());
            ItemStack[] contents = inventoryContents.toArray(new ItemStack[0]);
            player.getInventory().setContents(contents);
            player.sendMessage(Main.prefix + name + ChatColor.GREEN + " was successfully loaded");
        }
    }

    public void deleteKit(Player player, String name){
        File f = new File(Main.getPlugin().getDataFolder().getAbsolutePath(), player.getUniqueId() + "-kit-" + name.toLowerCase() + ".yml");
        f.delete();
        if (!f.exists()) {
            player.sendMessage(Main.prefix + name + ChatColor.GREEN + " was succesfully deleted");
        }
    }


    public String[] listKits(Player player){
        File directory = new File(Main.getPlugin().getDataFolder().getAbsolutePath());
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(String.valueOf(player.getUniqueId()) + "-kit");
            }
        };

        String[] children = directory.list(filter);

        return children;
    }

}
