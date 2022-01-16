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

        String[] splitStr = player.getInventory().getItemInHand().getItemMeta().getDisplayName().toLowerCase().split("\\s+");
        if (splitStr.length == 3) return;

        ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
        ItemStack item = itemFrame.getItem();

        ItemStack itemStackMat = new ItemStack(item.getType());

        Block attachedBlock = itemFrame.getLocation().getBlock().getRelative(itemFrame.getAttachedFace());
        Chest chest = (Chest) attachedBlock.getLocation().getBlock().getState();

        int amount = Integer.parseInt(item.getItemMeta().getLore().get(0));
        int price = Integer.parseInt(item.getItemMeta().getLore().get(1));
        String currency = item.getItemMeta().getLore().get(2);

        ItemStack currancyStack = new ItemStack(Material.matchMaterial(currency));

        if (!event.getRightClicked().equals(EntityType.ITEM_FRAME)) return;

        if (itemFrame.getItem().getType().equals(Material.AIR)) return;

        if (!item.getItemMeta().getLore().contains("shop")) return;

        if (!chest.getBlockInventory().containsAtLeast(itemStackMat, amount)) {
            player.sendMessage(config.getString("not-enough-items-in-chest-buy-error"));
            return;
        }

        if (!player.hasPermission("cs.buy")){
            player.sendMessage(config.getString("boy-permission-error"));
            return;
        }

        if (!player.getInventory().containsAtLeast(currancyStack, price)){
            player.sendMessage(config.getString("not-enough-payment-error"));
            return;
        }
        if (player.getInventory().firstEmpty() != -1) {
            itemStackMat.setAmount(amount);
            currancyStack.setAmount(price);

            player.getInventory().addItem(itemStackMat);
            chest.getInventory().remove(itemStackMat);
            player.getInventory().remove(currancyStack);
        } else {
            player.sendMessage(config.getString("no-space-error"));
        }
    }
}
