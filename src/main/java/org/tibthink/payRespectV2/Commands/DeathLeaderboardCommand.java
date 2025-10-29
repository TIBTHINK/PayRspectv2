package org.tibthink.payRespectV2.Commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.FileReader;
import java.util.*;

public class DeathLeaderboardCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public DeathLeaderboardCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        File statsFolder = new File(plugin.getServer().getWorldContainer(),
                plugin.getServer().getWorlds().get(0).getName() + "/stats");
        if (!statsFolder.exists() || !statsFolder.isDirectory()) {
            sender.sendMessage(ChatColor.RED + "Could not find stats folder!");
            return true;
        }

        Map<String, Integer> deathMap = new HashMap<>();

        for (File file : Objects.requireNonNull(statsFolder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                try (FileReader reader = new FileReader(file)) {
                    JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                    JsonObject stats = root.getAsJsonObject("stats");
                    if (stats != null) {
                        JsonObject custom = stats.getAsJsonObject("minecraft:custom");
                        if (custom != null) {
                            JsonElement deaths = custom.get("minecraft:deaths");
                            if (deaths != null && deaths.isJsonPrimitive()) {
                                int count = deaths.getAsInt();
                                String uuid = file.getName().replace(".json", "");
                                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                                deathMap.put(player.getName() != null ? player.getName() : uuid, count);
                            }
                        }
                    }
                } catch (Exception e) {
                    plugin.getLogger().warning("Error reading stats for " + file.getName() + ": " + e.getMessage());
                }
            }
        }

        if (deathMap.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No death data found!");
            return true;
        }

        // Sort by deaths descending
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(deathMap.entrySet());
        sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        sender.sendMessage(ChatColor.GOLD + "==== Death Leaderboard ====");
        int rank = 1;
        for (Map.Entry<String, Integer> entry : sorted) {
            sender.sendMessage(ChatColor.YELLOW + "" + rank + ". " + ChatColor.BOLD +
                    entry.getKey() + ": " + ChatColor.RED + entry.getValue() + ChatColor.GRAY + " deaths");
            if (rank++ >= 10) break; // limit to top 10 for readability
        }
        sender.sendMessage(ChatColor.GOLD + "===========================");

        return true;
    }
}