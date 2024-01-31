package me.sialim.countrysimcore.towers;

public interface Tower {
    String getType ();
    double getPrice () ;
    double getIncome ();
    double getPrice (int level);
    double getIncome (int level);
    double getTowerIncome (Player target);

    // Quantifiable Tower methods
    int getTowers (Player target);
    void addTower (Player target, int amount, int level);
    void takeTower (Player target, int amount, int level);
}
