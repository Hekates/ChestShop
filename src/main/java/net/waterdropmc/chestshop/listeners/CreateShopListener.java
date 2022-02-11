package net.waterdropmc.chestshop.listeners;

import net.waterdropmc.chestshop.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class CreateShopListener implements Listener {

    @EventHandler
    public void createShop(PlayerInteractEntityEvent event) {

        Entity clicked = event.getRightClicked();
        Player player = event.getPlayer();

        ItemFrame itemFrame = (ItemFrame) clicked;
        Block attachedBlock = itemFrame.getLocation().getBlock().getRelative(itemFrame.getAttachedFace());
        Chest chest = (Chest) attachedBlock.getLocation().getBlock().getState();
        Location location = chest.getLocation();

        ItemStack item = player.getInventory().getItemInHand();

        Material itemMat = item.getType();
        ItemStack itemMatStack = new ItemStack(itemMat);

        Configuration config = Main.getPlugin(Main.class).getConfig();

        if (player.getItemInHand().getAmount() == 0) return;

        //Checks if Item Frame is empty
        if (!itemFrame.getItem().equals(Material.AIR)) return;

        //Check world
        if (!(player.getWorld().getName().equalsIgnoreCase(config.getString("level-name")))) return;
        //Check gamemode
        if (!(player.getGameMode().equals(GameMode.SURVIVAL))) return;
        //Checks tag on Item
        if (!player.getInventory().getItemInHand().getItemMeta().hasDisplayName()) return;
        if (!player.getInventory().getItemInHand().getItemMeta().getDisplayName().contains("shop")) return;
        //Checks permission
        if (!(player.hasPermission("cs.create"))) {
            player.sendMessage(config.getString("create-permission-error"));
            return;
        }
        //Splits display name
        String[] splitStr = item.getItemMeta().getDisplayName().toLowerCase().split("\\s+");
        int amount = Integer.parseInt(splitStr[0]);
        int price = Integer.parseInt(splitStr[1]);
        String currency = splitStr[2];

        //Corrects item amount in players hand
        if (player.getInventory().getItemInHand().getAmount() > 1) {
            player.getInventory().getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        } else if (player.getInventory().getItemInHand().getAmount() == 1) {
            player.getInventory().clear(player.getInventory().getHeldItemSlot());
        }

        //Removes -s from currancy to convert to material
        if (splitStr[2].endsWith("s")) {
            currency = currency.substring(0, currency.length() - 1).toUpperCase();
        } else {
            currency = splitStr[2].toUpperCase();
        }

        //Converts currancy String to Material
        Material currencyM = Material.matchMaterial(currency);

        //Checks if the chest contains enough items to sell
        if (!chest.getBlockInventory().containsAtLeast(itemMatStack, amount)) {
            player.sendMessage(config.getString("not-enough-items-in-chest-error"));
            return;
        }

        //summons armorstand with creators name
        ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location.add(0.5, -0.5, 0.5), EntityType.ARMOR_STAND); //Spawn the ArmorStand

        as.setGravity(false);
        as.setCanPickupItems(false);
        as.setCustomName("-- " + player.getName() + "'s shop -- ");
        as.setCustomNameVisible(true);
        as.setVisible(false);

        //Sets informations to lore
        List<String> lore = new ArrayList<String>();
        lore.add(splitStr[0]);
        lore.add(splitStr[1]);
        lore.add(currencyM.toString());
        lore.add("shop");
        lore.add(as.getUniqueId().toString());

        //applies lore + display name
        ItemStack newItem = new ItemStack(Material.matchMaterial(item.getType().toString()));
        ItemMeta meta = newItem.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(amount + " -> " + price + " " + currency);
        newItem.setItemMeta(meta);
        //anything, that should be executed, when the Item is placed
        itemFrame.setItem(newItem);
        itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE_45);
    }
}