package org.tibthink.payRespectV2.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PayRespectCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private final PVersion versionCommand;

    public PayRespectCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.versionCommand = new PVersion(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GRAY + "PayRespect V2 - Use " + ChatColor.YELLOW + "/payrespect version" + ChatColor.GRAY + " to check for updates");
            return true;
        }

        if (args[0].equalsIgnoreCase("version")) {
            // Check permission
            if (!sender.hasPermission("payrespectv2.version")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                return true;
            }
            return versionCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /payrespect version");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            if (sender.hasPermission("payrespectv2.version")) {
                completions.add("version");
            }
            return completions;
        }
        
        return completions;
    }
}
