package me.sialim.countrysimcore.corecommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CoreCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player p) { // Check if command sender is a player.
            if (args.length >= 3) {
                Player t = Bukkit.getPlayer(args[1]);
                double value;

                try {
                    value = Double.parseDouble (args[2]);
                } catch (NumberFormatException e) {
                    p.sendMessage("Invalid value provided.");
                    return true;
                }

                if (t == null) {
                    p.sendMessage(ChatColor.RED + "Target " + args[1] + " is offline or doesn't exist.");
                    return true;
                }
            }
        } else {

            Bukkit.getLogger().log(Level.INFO,
                    "[CountrySimCore] "
                            + "This command can only be executed by players.");

        }
        return true;
    }
}
