package net.waterdropmc.chestshop.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class RemoveShopListener implements Listener {

    @EventHandler
    public void onFrameBreak(HangingBreakEvent event){
        if (!(event.getCause().equals(HangingBreakEvent.RemoveCause.PHYSICS))) return;
        ItemFrame itemFrame = (ItemFrame) event.getEntity();
        ItemStack item = itemFrame.getItem();

        //Checks if the shop item has the tag
        if (!(item.getItemMeta().getLore().contains("shop"))) return;

        ItemStack newItem = new ItemStack(item.getType());
        //Drops the original item
        itemFrame.getLocation().getWorld().dropItem(itemFrame.getLocation(), newItem);
        //Clears the Itemframe
        itemFrame.setItem(new ItemStack(Material.AIR));

        //Gets the UUID from the shop item and deletes the armorstand
        UUID uniqueId = UUID.fromString(item.getItemMeta().getLore().get(4));
        for (Entity entity : itemFrame.getWorld().getEntities()){

            if (entity.getUniqueId().equals(uniqueId)){
                entity.remove();
            }

        }
    }
}