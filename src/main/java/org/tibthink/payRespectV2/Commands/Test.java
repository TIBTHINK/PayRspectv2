package org.tibthink.payRespectV2.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Test implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.BLUE + "don't test ME " + player.getDisplayName() + "!!!");
        } else {
            sender.sendMessage("Dont test ME ADMIN!!!");
        }
        return true;
    }
}