package de.waterdropmc.chestshop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;


public class CreateShopListener implements Listener {
    @EventHandler
    public void onSignChange(SignChangeEvent event){
        String item;
        Player player = event.getPlayer();
        if (!(event.getLine(0).equalsIgnoreCase("chestshop"))) return;
        if (!(event.getLine(1).isEmpty())){
            item = event.getLine(1).toUpperCase();
            event.setLine(0, item);
            event.setLine(1, "------------");
        }
    }
}
