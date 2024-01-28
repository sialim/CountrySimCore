package me.sialim.countrysimcore.towers;

public class TradeCenter implements Tower{

    String type;
    int level;
    double price, income;

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