package de.cooltechno.mcpvp.commands;

import de.cooltechno.mcpvp.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class command_rtp implements CommandExecutor {

    private final Random random = new Random();
    private static final HashSet<Material> bad_blocks = new HashSet<>();
    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static final int COOLDOWN_TIME = 15 * 1000; // 10 seconds in milliseconds

    static {
        bad_blocks.add(Material.LAVA);
        bad_blocks.add(Material.FIRE);
        bad_blocks.add(Material.WATER);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId)) {
            long lastUsed = cooldowns.get(playerId);
            if (currentTime - lastUsed < COOLDOWN_TIME) {
                long timeLeft = (COOLDOWN_TIME - (currentTime - lastUsed)) / 1000;
                player.sendMessage(Main.prefix + "You must wait " + timeLeft + " seconds before using /rtp again.");
                return true;
            }
        }

        cooldowns.put(playerId, currentTime);
        teleportPlayer(player);
        return true;
    }

    private void teleportPlayer(Player player) {
        Location loc = genLoc(player);
        int counter = 1;

        while (!isLocationSafe(loc)) {
            loc = genLoc(player);
            counter++;
        }

        player.teleport(loc);
        player.sendMessage(Main.prefix + "You have been teleported to a random location in " + counter + " trie(s).");
    }

    public Location genLoc(Player player) {
        int x = random.nextInt(1001) - 500; // Random x coordinate between -500 and 500
        int z = random.nextInt(1001) - 500; // Random z coordinate between -500 and 500
        int y = player.getWorld().getHighestBlockYAt(x, z); // Get highest Y coordinate at the given X and Z

        return new Location(player.getWorld(), x, y + 1, z);
    }

    public static boolean isLocationSafe(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        // Get instances of the blocks around where the player would spawn
        Block block = location.getWorld().getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y - 1, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        // Check to see if the surroundings are safe or not
        return !(bad_blocks.contains(below.getType())) || (block.getType().isSolid()) || (above.getType().isSolid());
    }
}