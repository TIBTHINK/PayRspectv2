package org.tibthink.payRespectV2.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null && target.isOnline()) {
                int deaths = target.getStatistic(Statistic.DEATHS);
                player.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.GRAY +
                        " has died " + ChatColor.RED + deaths + ChatColor.GRAY + " times.");
            } else {
                player.sendMessage(ChatColor.RED + "Player not found or offline.");
            }
        } else {
            int deaths = player.getStatistic(Statistic.DEATHS);
            player.sendMessage(ChatColor.GRAY + "You have died " +
                    ChatColor.RED + deaths + ChatColor.GRAY + " times.");
        }
        return true;
    }
}