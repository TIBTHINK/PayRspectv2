package org.tibthink.payRespectV2;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.tibthink.payRespectV2.Commands.Freedom;
import org.tibthink.payRespectV2.Commands.Test;
import org.tibthink.payRespectV2.Listeners.PlayerDeathListener;

public final class PayRespectV2 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new PlayerDeathListener(this);
        getCommand("freedom").setExecutor(new Freedom());
        getCommand("test").setExecutor(new Test());
        getLogger().info("Testing one, two, three");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Good night world");
    }
}