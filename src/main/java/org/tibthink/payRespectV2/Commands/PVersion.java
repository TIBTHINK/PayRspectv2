package org.tibthink.payRespectV2.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class PVersion implements CommandExecutor {
    private final JavaPlugin plugin;
    private final String projectId = "payrespectv2"; // Your Modrinth project slug

    public PVersion(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "Checking for updates...");

        // Run async to prevent freezing
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://api.modrinth.com/v2/project/" + projectId + "/version");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "PayRespectV2/1.0 (Spigot Plugin)");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
                in.close();

                JSONArray versions = new JSONArray(response.toString());
                JSONObject latest = versions.getJSONObject(0);
                String latestVersion = latest.getString("version_number");
                String currentVersion = plugin.getDescription().getVersion();

                if (latestVersion.equalsIgnoreCase(currentVersion)) {
                    sender.sendMessage(ChatColor.GREEN + "You’re running the latest version: " + currentVersion);
                } else {
                    sender.sendMessage(ChatColor.GOLD + "Update available! Latest: " + latestVersion +
                            " | Current: " + currentVersion);
                    sender.sendMessage(ChatColor.AQUA + "Download it on Modrinth: https://modrinth.com/plugin/" + projectId);
                }

            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Failed to check for updates: " + e.getMessage());
            }
        });

        return true;
    }
}
