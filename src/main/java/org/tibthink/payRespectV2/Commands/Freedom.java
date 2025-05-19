package org.tibthink.payRespectV2.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Freedom implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't kill a God.");
            return true;
        }

        Player player = (Player) sender;
        player.setHealth(0.0);
        player.sendMessage(ChatColor.RED + "You have chosen death.");
        return true;
    }
}