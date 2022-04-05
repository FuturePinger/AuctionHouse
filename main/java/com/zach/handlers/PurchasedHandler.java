package com.zach.handlers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PurchasedHandler {
    public Map<Player, Location> playersBoughtChest = new HashMap<>();

    public void buyChest(Location location, Player player, Double amount) {
        if (EconomyHandler.hasEnoughMoney(player, location)) {
            EconomyHandler.withDrawMoney(player, amount);
            playersBoughtChest.put(player, location);
            player.sendMessage("You have bought the chest");
        } else {
            player.sendMessage("You dont have enough money silly");
        }
    }

    public void resetChestData() {
        for (Map.Entry<Player, Location> entry : playersBoughtChest.entrySet()) {
            entry.getValue().getBlock().setType(Material.AIR);
        }
        playersBoughtChest.clear();
    }

    public void resetChestData(Player player) {
        playersBoughtChest.get(player).getBlock().setType(Material.AIR);
        playersBoughtChest.remove(player);

    }

    public boolean boughtChest(Player player) {
        return playersBoughtChest.containsKey(player);
    }
}
