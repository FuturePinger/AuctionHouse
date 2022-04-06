package com.zach.items;

import com.zach.PrisonAuctionHouse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryBuyButton {
    private static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public ItemStack buyButton(Chest chest) {

        List<String> lore = new ArrayList<>();

        lore.add(color(" "));
        lore.add(color("&7When you buy this chest. All the"));
        lore.add(color("&7contents within the chest are yours!"));
        lore.add(color("&7Make sure to take the items quick..."));
        lore.add(color("&7Before the chest disappears!"));
        lore.add(color(" "));
        lore.add(color("&c&lWARNING&8&l: &7If you already own a chest."));
        lore.add(color("&7That chest will be deleted on purchase!"));
        lore.add(color(" "));
        lore.add(color("&8(&f&lClick me&8&l) &8&lto purchase!"));

        ItemStack item = new ItemStack(Material.LIME_DYE);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(color("&e&lPRICE&8&l: &a&l$&f&l" + PrisonAuctionHouse.getInstance().inventoryHandler.savedChestAmounts.get(chest.getLocation()).doubleValue() + "0"));
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(itemMeta);

        return item;
    }
}
