package net.waterdropmc.chestshop.utils;

import org.bukkit.Material;

public class GeneralUtils {
    public static String idToName(int id)
    {
        return Material.getMaterial(id).name();
    }
}
