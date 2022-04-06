package com.zach.handlers;

import com.zach.PrisonAuctionHouse;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.zach.PrisonAuctionHouse.getEconomy;

public class EconomyHandler {


    private static Double getPlayerBalance(Player player)  {
        return getEconomy().getBalance(player);
    }

    public static boolean hasEnoughMoney(Player player, Location location) {
        return getPlayerBalance(player) >= PrisonAuctionHouse.getInstance().inventoryHandler.savedChestAmounts.get(location);
    }
    public static void withDrawMoney(Player player, Double amount) {
        getEconomy().withdrawPlayer(player, amount);
    }



}
