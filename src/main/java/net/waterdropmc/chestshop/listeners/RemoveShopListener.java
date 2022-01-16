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
        if (!(item.getItemMeta().getLore().contains("shop"))) return;

        ItemStack newItem = new ItemStack(item.getType());
        itemFrame.getLocation().getWorld().dropItem(itemFrame.getLocation(), newItem);
        itemFrame.setItem(new ItemStack(Material.AIR));

        UUID uniqueId = UUID.fromString(item.getItemMeta().getLore().get(4));
        for (Entity entity : itemFrame.getWorld().getEntities()){

            if (entity.getUniqueId().equals(uniqueId)){
                entity.remove();
            }

        }
    }
}