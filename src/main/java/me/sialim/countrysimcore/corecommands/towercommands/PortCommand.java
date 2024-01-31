package me.sialim.countrysimcore.corecommands.towercommands;

import me.sialim.countrysimcore.utils.Eco;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class PortCommand implements CommandExecutor {

    // Instance variables
    Eco eco;

    public PortCommand (Eco eco) {
        this.eco = eco;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player p) { // Check if command sender is a player.
            if (args.length >= 3) {
                Player t = Bukkit.getPlayer(args[1]);
                int quantity;
                int level;

                try {
                    quantity = Integer.parseInt (args[2]);
                    level = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    p.sendMessage("Invalid value provided.");
                    return true;
                }

                if (t == null) {
                    p.sendMessage(ChatColor.RED + "Target " + args[1] + " is offline or doesn't exist.");
                    return true;
                }

                performPortAction(p, t, args[0], quantity, level);
            }
        } else {

            Bukkit.getLogger().log(Level.INFO,
                    "[CountrySimCore] "
                            + "This command can only be executed by players.");

        }
        return true;
    }

    private void performPortAction (Player sender, Player target, String action, int value, int level) {
        if(sender.hasPermission("cs.economy.towers.ports")) {
            Ports ports = new Ports(sender, dataConfig, eco); //ports <set/add/take> <target> <amount> <level>
            switch (action.toLowerCase()) {
                case "add": //ports add target value level
                    ports.addTower(target, value, level);
                    eco.addBalance(target, value);
                    break;
                case "take": //ports take target value level
                    ports.takeTower(target, value, level);
                    eco.takeBalance(target, value);
                    break;
                case "get": //ports get target value level
                    sender.sendMessage(ChatColor.GREEN + target.getName() + "'s current ports: " + ports.getTowers(target));
                    sender.sendMessage(ChatColor.GREEN + target.getName() + "'s current port income: " + ports.getTowerIncome(target));
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Invalid action. " +
                            "Usage: /eco <set/add/take/get> <player> <value> <level>");
            }
        }
    }
}
