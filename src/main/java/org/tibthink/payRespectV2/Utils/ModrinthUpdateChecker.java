package org.tibthink.payRespectV2.Utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class ModrinthUpdateChecker {

    private final JavaPlugin plugin;
    private final String projectId; // Modrinth slug or id

    public ModrinthUpdateChecker(JavaPlugin plugin, String projectId) {
        this.plugin = plugin;
        this.projectId = projectId;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://api.modrinth.com/v2/project/" + projectId + "/version");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONArray versions = new JSONArray(response.toString());
                if (versions.length() == 0) {
                    plugin.getLogger().warning("No versions found on Modrinth!");
                    return;
                }

                JSONObject latest = versions.getJSONObject(0);
                String latestVersion = latest.getString("version_number");
                String currentVersion = plugin.getDescription().getVersion();

                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    plugin.getLogger().warning("A new version of PayRespectV2 is available!");
                    plugin.getLogger().warning("Latest: " + latestVersion + " | Current: " + currentVersion);
                    plugin.getLogger().warning("Download: https://modrinth.com/plugin/" + projectId);
                } else {
                    plugin.getLogger().info("PayRespectV2 is up to date.");
                }

            } catch (Exception e) {
                plugin.getLogger().warning("Failed to check Modrinth for updates.");
            }
        });
    }
}