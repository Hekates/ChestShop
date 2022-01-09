package de.waterdropmc.chestshop;

import de.waterdropmc.chestshop.listeners.CreateShopListener;
import de.waterdropmc.chestshop.listeners.RemoveShopListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChestShop extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new CreateShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new RemoveShopListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
