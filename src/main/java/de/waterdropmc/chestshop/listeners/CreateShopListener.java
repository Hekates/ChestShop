package de.waterdropmc.chestshop.listeners;

import de.waterdropmc.chestshop.ChestShop;
import de.waterdropmc.chestshop.utils.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sign;

import java.util.Arrays;
import java.util.Locale;


public class CreateShopListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();

        org.bukkit.material.Sign signMaterial = (org.bukkit.material.Sign) event.getBlock().getState().getData();
        Block attachedBlock = (Block) event.getBlock().getRelative(signMaterial.getAttachedFace());
        Chest chest = (org.bukkit.block.Chest) attachedBlock.getLocation().getBlock().getState();

        if (!(attachedBlock.getType().equals(Material.CHEST))) return;
        if (!(event.getLine(0).equalsIgnoreCase("chestshop"))) return;
        if (event.getLine(1).isEmpty() || event.getLine(2).isEmpty() || event.getLine(3).isEmpty()) return;

            int amount = Integer.parseInt(event.getLine(1));
            ItemStack item = Arrays.stream(chest.getBlockInventory().getContents()).findFirst().get();
            int price = Integer.parseInt(event.getLine(2));
            String currency = event.getLine(3).toUpperCase();

            if (event.getLine(3).endsWith("s")){
                currency = currency.substring(0,currency.length() -1).toUpperCase();
            }else{
                currency = event.getLine(3).toUpperCase();
            }

            Material currencyM = Material.matchMaterial(currency);
            if (!(chest.getBlockInventory().containsAtLeast(item, amount))) return;

            event.setLine(0, price + " " + currency);
            event.setLine(1, " ");
            event.setLine(2, amount + "x          ");
            event.setLine(3, " ");

            player.sendMessage("Test");

    }
}
