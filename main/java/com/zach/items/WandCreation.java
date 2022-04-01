package com.zach.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WandCreation {
    private static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public ItemStack creationWand() {
        List<String> lore = new ArrayList<>();
        lore.add(color(""));
        lore.add(color("&7Use this tool for the creation"));
        lore.add(color("&7of the Auction House."));
        lore.add(color(""));
        lore.add(color("&7Left-Click a block to &acreate &7a location"));
        lore.add(color("&7or Right-Click to &cremove &7a location!"));


        ItemStack creationWand = new ItemStack(Material.GOLDEN_SHOVEL);
        ItemMeta creationWandMeta = creationWand.getItemMeta();

        creationWandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        creationWandMeta.setLore(lore);
        creationWandMeta.setDisplayName(color("&fCreation Wand"));
        creationWand.setItemMeta(creationWandMeta);
        return creationWand;
    }
}
