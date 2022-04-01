package com.zach.handlers;

import com.zach.AuctionHouse;
import org.bukkit.entity.Player;

public class WandHandler {
    public void creation(Player player) {
        if (!player.getInventory().contains(AuctionHouse.getInstance().wandCreation.creationWand())) {
            player.getInventory().addItem(AuctionHouse.getInstance().wandCreation.creationWand());
        }
    }
}
