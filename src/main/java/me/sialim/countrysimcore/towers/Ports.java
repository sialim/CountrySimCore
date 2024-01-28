package me.sialim.countrysimcore.towers;

public class Ports implements Tower{

    String type;
    int level;
    double price, income;

    public Ports () {

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
}
