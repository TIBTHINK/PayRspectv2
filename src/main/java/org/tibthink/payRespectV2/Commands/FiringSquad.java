package org.tibthink.payRespectV2.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FiringSquad implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Disallow console or non-player senders
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSorry, only the living can commit murder.");
            return true;
        }

        Player player = (Player) sender;

        // Only allow ops
        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You must be an admin (operator) to use this command.");
            return true;
        }

        // Countdown task
        new BukkitRunnable() {
            int countdown = 3;

            @Override
            public void run() {
                if (countdown > 0) {
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.sendTitle("§c" + countdown, "", 20, 20, 20);
                    }
                    countdown--;
                } else {
                    // Execute firing squad
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.setHealth(0.0);
                    }
                    Bukkit.broadcastMessage(ChatColor.RED + "The firing squad has executed everyone!");
                    cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(getClass()), 0L, 20L); // 20 ticks = 1 second

        return true;
    }
}
