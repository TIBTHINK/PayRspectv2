package org.tibthink.payRespectV2.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import org.tibthink.payRespectV2.PayRespectV2;

public class PlayerDeathListener implements Listener {
    private static PayRespectV2 plugin;

    public PlayerDeathListener(PayRespectV2 plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getPlayer() instanceof Player) {

            Bukkit.broadcastMessage("[Server] §l§4F");
        }
    }
}
