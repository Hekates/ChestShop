package net.waterdropmc.chestshop;

import net.waterdropmc.chestshop.listeners.CreateShopListener;
import net.waterdropmc.chestshop.listeners.RemoveShopListener;
import net.waterdropmc.chestshop.listeners.UseShopListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        //Events
        Bukkit.getPluginManager().registerEvents(new CreateShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new RemoveShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new UseShopListener(), this);

    }
}
