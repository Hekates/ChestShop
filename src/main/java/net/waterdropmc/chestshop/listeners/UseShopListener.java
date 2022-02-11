package net.waterdropmc.chestshop.listeners;

import net.waterdropmc.chestshop.ChestShop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class UseShopListener implements Listener {

    @EventHandler
    public void onShopUse(PlayerInteractAtEntityEvent event){

        Configuration config = ChestShop.getPlugin(ChestShop.class).getConfig();

        Player player = event.getPlayer();

        if (!player.getInventory().getItemInHand().getType().equals(Material.AIR)) return;

        ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
        ItemStack item = itemFrame.getItem();

        ItemStack itemStackMat = new ItemStack(item.getType());

        Block attachedBlock = itemFrame.getLocation().getBlock().getRelative(itemFrame.getAttachedFace());
        if (attachedBlock.getType().equals(Material.CHEST)) {
            Chest chest = (Chest) attachedBlock.getLocation().getBlock().getState();

            int amount = Integer.parseInt(item.getItemMeta().getLore().get(0));
            int price = Integer.parseInt(item.getItemMeta().getLore().get(1));
            String currency = item.getItemMeta().getLore().get(2);

            ItemStack currancyStack = new ItemStack(Material.matchMaterial(currency));

            if (!event.getRightClicked().equals(EntityType.ITEM_FRAME)) return;

            if (itemFrame.getItem().getType().equals(Material.AIR)) return;
            if (!itemFrame.getItem().getItemMeta().getLore().contains("shop")) return;
            player.sendMessage("45");

            //Checks if the Chest has enough Items to sell
            if (!chest.getBlockInventory().containsAtLeast(itemStackMat, amount)) {
                player.sendMessage(config.getString("not-enough-items-in-chest-buy-error"));
                return;
            }
            player.sendMessage("52");
            //Checks if player has the permission
            if (!player.hasPermission("cs.buy")) {
                player.sendMessage(config.getString("boy-permission-error"));
                return;
            }
            player.sendMessage("58");
            //Checks if player has enough playment
            if (!player.getInventory().containsAtLeast(currancyStack, price)) {
                player.sendMessage(config.getString("not-enough-payment-error"));
                return;
            }
            player.sendMessage("64");
            //Checks if has an empty slot and then executes the transaction
            if (player.getInventory().firstEmpty() != -1) {
                itemStackMat.setAmount(amount);
                currancyStack.setAmount(price);

                player.getInventory().addItem(itemStackMat);
                chest.getInventory().remove(itemStackMat);
                player.getInventory().remove(currancyStack);
                player.sendMessage("73");
            } else {
                player.sendMessage(config.getString("no-space-error"));
                player.sendMessage("76");
            }
        }
    }
}
