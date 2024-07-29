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

public class EnderChestLogic {

    public void saveEnderChest(Player player) throws IOException {
        File f = new File(Main.getPlugin().getDataFolder().getAbsolutePath(), player.getUniqueId() + "-ec.yml");
            player.sendMessage(Main.prefix + ChatColor.GREEN + "Enderchest was successfully created");
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);
            c.set("enderchest.content", player.getEnderChest().getContents());
            c.save(f);
    }


    public void loadEnderchest(Player player) throws IOException {
        File f = new File(Main.getPlugin().getDataFolder().getAbsolutePath(), player.getUniqueId() + "-ec.yml");
        if (!f.exists()){
            player.sendMessage(Main.prefix + "§4You have no enderchest saved yet!\nuse /ec <save/load> to save/load your enderchest");
        }else {
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);

            List<ItemStack> inventoryContents = (List<ItemStack>) c.getList("enderchest.content", new ArrayList<>());
            ItemStack[] contents = inventoryContents.toArray(new ItemStack[0]);
            player.getEnderChest().setContents(contents);
            player.sendMessage(Main.prefix +ChatColor.GREEN + "Enderchest was successfully loaded");
        }
    }

}
