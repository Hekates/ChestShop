package de.waterdropmc.chestshop;

import de.waterdropmc.chestshop.listeners.CreateShopListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChestShop extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new CreateShopListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
