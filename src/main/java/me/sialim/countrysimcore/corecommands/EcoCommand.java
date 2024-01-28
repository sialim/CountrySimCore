package me.sialim.countrysimcore.corecommands;

import me.sialim.countrysimcore.utils.Eco;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class EcoCommand implements CommandExecutor {

    // Instance variables
    Eco eco;

    // Constructors
    public EcoCommand (Eco eco) {
        this.eco = eco;
    }

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

                performEcoAction(p, t, args[0], value);
            }
        } else {

            Bukkit.getLogger().log(Level.INFO,
                    "[CountrySimCore] "
                            + "This command can only be executed by players.");

        }
        return true;
    }

    private void performEcoAction (Player sender, Player target, String action, double value) {
        if(sender.hasPermission("cs.economy")) {
            switch (action.toLowerCase()) {
                case "set": //eco set target value
                    eco.setBalance(target, value);
                    sender.sendMessage(ChatColor.GREEN + "Successfully set "
                            + target.getName() + "'s account balance: " + eco.getBalance(target));
                    break;
                case "add": //eco add target value
                    eco.addBalance(target, value);
                    sender.sendMessage(ChatColor.GREEN + "Successfully added " + value
                            + " to " + target.getName() + "'s account."
                            + " New balance: " + eco.getBalance(target));
                    break;
                case "take": //eco take target value
                    eco.takeBalance(target, value);
                    sender.sendMessage(ChatColor.GREEN + "Successfully took " + value
                            + " from " + target.getName() + "'s account."
                            + " New balance: " + eco.getBalance(target));
                    break;
                case "get": //eco get target
                    sender.sendMessage(ChatColor.GREEN + target.getName() + "'s current balance: " + eco.getBalance(target));
                    break;
                case "check": //eco check target value
                    if (eco.checkBalance(target, value)) {
                        sender.sendMessage ( ChatColor.GREEN + target.getName() + " has " + value);
                    } else {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " does not have " + value);
                    }
                    break;
                case "check-iron": //eco check-iron target value
                    if (eco.checkMaterial(target, Material.IRON_INGOT, (int) Math.round(value))) {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " has " + value + " iron ingots.");
                    } else {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " does not have " + value + " iron ingots.");
                    }
                    break;
                case "check-gold": //eco check-iron target value
                    if (eco.checkMaterial(target, Material.GOLD_INGOT, (int) Math.round(value))) {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " has " + value + " gold ingots.");
                    } else {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " does not have " + value + " gold ingots.");
                    }
                    break;
                case "check-coal": //eco check-iron target value
                    if (eco.checkMaterial(target, Material.COAL, (int) Math.round(value))) {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " has " + value + " coal.");
                    } else {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " does not have " + value + " coal.");
                    }
                    break;
                case "check-flint": //eco check-iron target value
                    if (eco.checkMaterial(target, Material.FLINT, (int) Math.round(value))) {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " has " + value + " flint.");
                    } else {
                        sender.sendMessage (ChatColor.GREEN +target.getName() + " does not have " + value + " flint.");
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Invalid action. " +
                            "Usage: /eco <set/add/take/get> <player> <value>");
            }
        }
    }
}
