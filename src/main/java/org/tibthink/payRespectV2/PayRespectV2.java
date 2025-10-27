package org.tibthink.payRespectV2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.tibthink.payRespectV2.Commands.*;
import org.tibthink.payRespectV2.Listeners.PlayerDeathListener;

import java.util.HashMap;
import java.util.UUID;

public final class PayRespectV2 extends JavaPlugin implements Listener {

    private final HashMap<UUID, Integer> deathCounts = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        new PlayerDeathListener(this);
        getCommand("freedom").setExecutor(new Freedom());
        getCommand("test").setExecutor(new Test());
        getCommand("firingsquad").setExecutor(new FiringSquad());
        getCommand("deathcount").setExecutor(new DeathsCommand());
//        getCommand("version").setExecutor(new DeathVersionCommand());
        getLogger().info("Testing one, two, three");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Good night world");
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        int deaths = player.getStatistic(Statistic.DEATHS);

        String message = ChatColor.RED + player.getName() + ChatColor.GRAY + " has died "
                + ChatColor.YELLOW + deaths + ChatColor.GRAY + " times!";
        Bukkit.broadcastMessage(message);
    }
}