package me.sialim.countrysimcore.towers;

public class Ports implements Tower{

    String type;
    int level;
    DataConfig dataConfig;
    Eco eco;
    Player sender;

    public Ports (Player sender, DataConfig dataConfig, Eco eco) {
        this.dataConfig = dataConfig;
        this.eco = eco;
        this.sender = sender;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getIncome() {
        return income;
    }

    public int getTowers (Player target) { // returns amount
        FileConfiguration config = dataConfig.get();
        String path = target.getUniqueId() + ".government.ports.amount";

        if (config.contains(path)) {
            return config.getInt(path);
        } else {
            dataConfig.addUser(target);
            return 0;
        }
    }

    public double getTowerIncome(Player target) {
        FileConfiguration config = dataConfig.get();
        String path = target.getUniqueId() + ".government.ports.income";
        if (config.contains(path)) {
            return config.getInt(path);
        } else {
            dataConfig.addUser(target);
            return 0;
        }
    }

    public void addTower(Player target, int amount, int level) {
        UUID UUID = target.getUniqueId();
        String amountPath = UUID + ".government.ports.amount";
        String incomePath = UUID + ".government.ports.income";

        double currentBalance = eco.getBalance(target);

        if (level == 1) {
            double requiredCost = amount * getPrice(1);

            if (currentBalance >= requiredCost) {
                if (dataConfig.get().contains(amountPath)) {
                    dataConfig.get().set(amountPath, amount);
                    dataConfig.get().set(incomePath, getIncome(1) * amount);
                    dataConfig.save();
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " was successfully given " + amount
                            + " level " + level + " ports.");
                } else {
                    dataConfig.addUser(target);
                    addTower(target, amount, level);
                }
            } else {
                sender.sendMessage(ChatColor.RED + target.getName() + " cannot afford " + amount
                        + " level " + level + " ports.");
                sender.sendMessage(ChatColor.RED + target.getName() + " requires $" + requiredCost);
            }
        } else {
            double requiredCost = amount * getPrice(level);
            int requiredTowers = amount * 2;
            double requiredIncome = getIncome(level - 1) * amount;

            if (currentBalance >= requiredCost
            && getTowers(target) >= requiredTowers
            && getTowerIncome(target) >= requiredIncome) {
                if(dataConfig.get().contains(amountPath)) {
                    dataConfig.get().set(amountPath, amount);
                    dataConfig.get().set(incomePath, getIncome(level) * amount);
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " was successfully given " + amount
                            + " level " + level + " ports.");
                    dataConfig.save();
                    takeTower(target, requiredTowers, level - 1);
                } else {
                    dataConfig.addUser(target);
                    addTower(target, amount, level);
                }
            } else {
                sender.sendMessage(ChatColor.RED + target.getName() + " cannot afford " + amount
                        + " level " + level + " ports.");
                sender.sendMessage(ChatColor.RED + "Ensure " + target.getName() + " has"
                + requiredTowers + " ports and $" + requiredIncome + " port income.");
            }
        }
    }

    public void takeTower(Player target, int amount, int level) {
        UUID UUID = target.getUniqueId();
        String amountPath = UUID + ".government.ports.amount";
        String incomePath = UUID + ".government.ports.income";

        if (dataConfig.get().contains(amountPath)) {
            int currentAmount = getTowers(target);
            double currentIncome = getTowerIncome(target);
            double requiredIncome = getIncome(level) * amount;

            if (currentAmount >= amount && currentIncome >= requiredIncome) {
                dataConfig.get().set(amountPath, currentAmount - amount);
                dataConfig.get().set(incomePath, currentIncome - requiredIncome);
                dataConfig.save();
            }
        } else {
            dataConfig.addUser(target);
            takeTower(target, amount, level);
        }
    }

    public double getPrice (int level) {

        double portPrice = 100;
        double portTwoUpgradeFee = 150;
        double portThreeUpgradeFee = 275;

        return switch (level) {
            // reminder that the level "prices" after level one are essentially upgrading fees
            // ^ why the prices are so low

            case 2 -> portTwoUpgradeFee;
            case 3 -> portThreeUpgradeFee;
            // level prices

            default -> portPrice;
        };
    }

    public double getIncome (int level) {

        double portOneProfit = 50;
        double portTwoProfit = 75;
        double portThreeProfit = 100;

        return switch (level) {
            // reminder that the level "prices" after level one are essentially upgrading fees
            // ^ why the prices are so low

            case 2 -> portTwoProfit;
            case 3 -> portThreeProfit;
            default -> portOneProfit;
        };
    }
}
