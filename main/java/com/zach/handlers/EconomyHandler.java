package com.zach.handlers;

import com.zach.AuctionHouse;
import com.zach.managers.ConfigManager;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static com.zach.AuctionHouse.getEconomy;

public class EconomyHandler {


    private static Double getPlayerBalance(Player player)  {
        return getEconomy().getBalance(player);
    }

    public static boolean hasEnoughMoney(Player player, Location location) {
        return getPlayerBalance(player) >= AuctionHouse.getInstance().inventoryHandler.savedChestAmounts.get(location);
    }
    public static void withDrawMoney(Player player, Double amount) {
        getEconomy().withdrawPlayer(player, amount);
    }



}
