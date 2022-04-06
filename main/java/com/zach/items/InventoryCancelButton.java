package com.zach.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryCancelButton {
    private static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public ItemStack cancelButton( ) {

        List<String> lore = new ArrayList<>();

        lore.add(color(" "));
        lore.add(color("&8&l(&f&lClick me&8&l) &8&lto cancel the purchase!"));

        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(color("&4&lCANCEL"));
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(itemMeta);

        return item;
    }
}
