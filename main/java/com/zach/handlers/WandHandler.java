package com.zach.handlers;

import com.zach.PrisonAuctionHouse;
import org.bukkit.entity.Player;

public class WandHandler {
    public void creation(Player player) {
        if (!player.getInventory().contains(PrisonAuctionHouse.getInstance().wandCreation.creationWand(player))) {
            player.getInventory().addItem(PrisonAuctionHouse.getInstance().wandCreation.creationWand(player));
        }
    }
}
