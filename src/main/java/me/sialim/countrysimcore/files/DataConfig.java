package me.sialim.countrysimcore.files;

import me.sialim.countrysimcore.CountrySimCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class DataConfig {
    private File file;
    private FileConfiguration customFile;

    public void setup () {
        CountrySimCore plugin = CountrySimCore.getPlugin (CountrySimCore.class);
        file = new File (plugin.getDataFolder (), "data.yml");

        if (!file.exists ()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

        customFile = YamlConfiguration.loadConfiguration (file);
    }

    public FileConfiguration get () {
        return customFile;
    }

    public void save () {
        try {
            customFile.save(file);
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public void reload () {
        customFile = YamlConfiguration.loadConfiguration (file);
    }

    public void addDefaults () {
        if(!get ().contains ("current-month")) {
            get ().addDefault ("current-month", 0);
        }
    }

    public void addUser (Player p) {
        get ().set (p.getUniqueId() + ".balance",0.0);
        get ().set (p.getUniqueId() + ".username",p.getName());

        // Government setup
        get ().set (p.getUniqueId() + ".government.type","default");
        get ().set (p.getUniqueId() + ".government.age","medieval");
        get ().set (p.getUniqueId() + ".government.economy","open");
        get ().set (p.getUniqueId() + ".government.political-freedom","default");
        get ().set (p.getUniqueId() + ".government.income",0.0);

        // Farms setup
        get ().set (p.getUniqueId() + ".government.farms.amount",0);
        get ().set (p.getUniqueId() + ".government.farms.income",0.0);

        // Ports setup
        get ().set (p.getUniqueId() + ".government.ports.amount",0);
        get ().set (p.getUniqueId() + ".government.ports.income",0.0);

        // Mines setup
            // Coal mines
            get ().set (p.getUniqueId() + ".government.mines.coal.amount",0);
            get ().set (p.getUniqueId() + ".government.mines.coal.income",0);

            // Iron mines
            get ().set (p.getUniqueId() + ".government.mines.iron.amount",0);
            get ().set (p.getUniqueId() + ".government.mines.iron.income",0);

            // Gold mines
            get ().set (p.getUniqueId() + ".government.mines.gold.amount",0);
            get ().set (p.getUniqueId() + ".government.mines.gold.income",0);

        // Settlements setup
        get ().set (p.getUniqueId() + ".government.settlements.amount",0);
        get ().set (p.getUniqueId() + ".government.settlements.income",0.0);

        // Labs setup
        get ().set (p.getUniqueId() + ".government.labs.amount",0);
        get ().set (p.getUniqueId() + ".government.labs.income",0.0);
    }
}
