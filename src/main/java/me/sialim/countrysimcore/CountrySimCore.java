package me.sialim.countrysimcore;

import me.sialim.countrysimcore.corecommands.EcoCommand;
import me.sialim.countrysimcore.files.DataConfig;
import me.sialim.countrysimcore.utils.Eco;
import org.bukkit.plugin.java.JavaPlugin;

public final class CountrySimCore extends JavaPlugin {

    @Override
    public void onEnable () {
        // Default config.yml setup
        getConfig ().options ().copyDefaults ();
        saveDefaultConfig ();

        // Default data.yml setup
        DataConfig dataConfig = new DataConfig ();
        dataConfig.setup ();
        dataConfig.get ().options ().copyDefaults (true);
        dataConfig.addDefaults ();
        dataConfig.save ();

        // Instantiate classes
        Eco eco = new Eco(dataConfig);

        // Command executor setup
        getCommand("eco").setExecutor(new EcoCommand(eco));
    }
}
