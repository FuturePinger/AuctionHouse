package com.zach.handlers;

import com.Zrips.CMI.CMI;
import com.zach.PrisonAuctionHouse;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PurchasedHandler {
    private static String color(String string) {
        return (ChatColor.translateAlternateColorCodes('&', string));
    }

    public Map<Player, Location> playersBoughtChest = new HashMap<>();

    public void buyChest(Location location, Player player, Double amount) {
        if (EconomyHandler.hasEnoughMoney(player, location)) {
            EconomyHandler.withDrawMoney(player, amount);
            playersBoughtChest.put(player, location);
            CMI.getInstance().getHologramManager().getByName(location.toString()).setLines(Arrays.asList(color("&e&lPurchased")));
            player.sendMessage(color("&bAuction&8-&6House &8// &7You have purchased this chest. You are now allowed to open it... Enjoy!"));
            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
        } else {
            player.sendMessage(color("&bAuction&8-&6House &8// &7What do you think you're trying to do! You don't have the money for that!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }

    public void resetChestData() {
        for (Map.Entry<Player, Location> entry : playersBoughtChest.entrySet()) {
            entry.getValue().getBlock().setType(Material.AIR);
        }
        for (Map.Entry<Location, Integer> entry : PrisonAuctionHouse.getInstance().inventoryHandler.savedChestAmounts.entrySet()) {
            entry.getKey().getBlock().setType(Material.AIR);
            CMI.getInstance().getHologramManager().getByName(entry.getValue().toString());
        }
        playersBoughtChest.clear();
    }

    public void resetChestData(Player player) {
        CMI.getInstance().getHologramManager().getByName(playersBoughtChest.get(player).getBlock().getLocation().toString()).remove();
        playersBoughtChest.get(player).getBlock().setType(Material.AIR);
        playersBoughtChest.remove(player);


    }

    public boolean boughtChest(Player player) {
        return playersBoughtChest.containsKey(player);
    }
}
