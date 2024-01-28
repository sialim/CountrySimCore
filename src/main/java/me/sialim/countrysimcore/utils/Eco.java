package me.sialim.countrysimcore.utils;

import me.sialim.countrysimcore.files.DataConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Eco {

    // Instance variables
    private DataConfig dataConfig;

    // Constructors
    public Eco(DataConfig dataConfig) {
        this.dataConfig = dataConfig;
    }

            // Currency ============================= <<<
    // Getters
    public double getBalance(Player p) {
        FileConfiguration config = dataConfig.get();
        String path = p.getUniqueId() + ".balance";

        if (config.contains(path)) { // If target is registered in configuration
            return config.getDouble(path); // return target balance
        } else {
            dataConfig.addUser(p);
            return 0.0;
        }
    }

    public boolean checkBalance (Player p, double price) {
        return getBalance(p) >= price;
    }

    public boolean checkMaterial (Player p, Material material, int materialAmount) {
        int playerMaterialAmount = 0;
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null && item.getType() == material) {
                playerMaterialAmount += item.getAmount();
            }
        }
        return playerMaterialAmount >= materialAmount;
    }

    // Setters
    public void setBalance(Player p, double value) { // Set target balance to value
        String path = p.getUniqueId() + ".balance";
        dataConfig.get().set(path, value);
        dataConfig.save();
        dataConfig.reload();
    }

    public void addBalance(Player p, double value) { // Deposit value to target balance
        double currentBalance = getBalance(p);
        setBalance(p, currentBalance + value);
    }

    public void takeBalance(Player p, double value) { // Withdraw value from target balance
        double currentBalance = getBalance(p);
        setBalance(p, currentBalance - value);
    }

            // Time ============================= <<<
    // Getters
    public int getMonth() {
        return dataConfig.get().getInt("current-month");
    }

    // Setters
    public void setMonth(int value) {
        dataConfig.get().set("current-month", value);
        dataConfig.save();
        dataConfig.reload();
    }

    public void addMonth(int value) {
        int currentMonth = getMonth();
        setMonth(currentMonth + value);
    }

    public void pay (Player p) {
        if (dataConfig.get().contains(p.getUniqueId() + ".balance")) { // If target is registered in configuration
            double income = dataConfig.get().getDouble(p.getUniqueId() + ".government.income");
            addBalance(p, income);
        } else {
            dataConfig.addUser (p);
            pay (p);
        }
    }


}
