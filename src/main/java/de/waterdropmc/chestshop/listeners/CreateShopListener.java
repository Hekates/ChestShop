package de.waterdropmc.chestshop.listeners;

import de.waterdropmc.chestshop.ChestShop;
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


public class CreateShopListener implements Listener {
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        Sign sign = (Sign) event.getBlock().getState().getData();
        Block attachedBlock = event.getBlock().getRelative(sign.getAttachedFace());

        if (!(event.getBlock().getWorld().getName().equals(ChestShop.getPlugin(ChestShop.class).getConfig().getString("level-name")))) return;
        if (!(attachedBlock.getType().equals(Material.CHEST))) return;
        if (!(event.getLine(0).equalsIgnoreCase("chestshop"))) return;
        if (!(event.getLine(1).isEmpty())){
            int amount = Integer.parseInt(event.getLine(1));
            Chest chest = (Chest) attachedBlock;
            Inventory inv = chest.getBlockInventory();

            ItemStack item = null;

            for (int i = 0; i > 25; i++) {
                if (inv.getItem(i).getType() != Material.AIR) {
                    item = inv.getItem(i);
                }
            }

            if (item == null) {
                //Keine Items in der Kiste
                return;
            }

            if (!(chest.getBlockInventory().containsAtLeast(item, amount))) return;

            event.setLine(0, "-" + player.getName() + "'s-");
            event.setLine(1, event.getLine(1) + " " + item);
        }else {
            player.sendMessage(ChestShop.getPlugin(ChestShop.class).getConfig().getString("empty-line-error"));
        }


        player.sendMessage("Test");

    }
}
