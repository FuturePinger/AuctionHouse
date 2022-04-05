package com.zach.listeners;

import com.zach.AuctionHouse;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PurchaseListener implements Listener {

    @EventHandler
    public void purchaseChestEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block clickedBlock = e.getClickedBlock();
        Action action = e.getAction();
        if (action.isRightClick()) {
            if (clickedBlock != null) {
                if (clickedBlock.getType().equals(Material.CHEST)) {
                    Chest chest = (Chest) clickedBlock.getState();
                    if (AuctionHouse.getInstance().creationListener.saveChestData.containsKey(chest.getLocation())) {
                        if (!(AuctionHouse.getInstance().purchasedHandler.boughtChest(player) && AuctionHouse.getInstance().purchasedHandler.playersBoughtChest.containsValue(chest.getLocation()))) {
                            if (AuctionHouse.getInstance().inventoryHandler.savedChestAmounts.get(chest.getLocation()) != null) {
                                if (AuctionHouse.getInstance().purchasedHandler.boughtChest(player)) {
                                    AuctionHouse.getInstance().purchasedHandler.resetChestData(player);
                                }
                                AuctionHouse.getInstance().purchasedHandler.buyChest(
                                        chest.getLocation(),
                                        player,
                                        AuctionHouse.getInstance().inventoryHandler.savedChestAmounts.get(chest.getLocation()).doubleValue());
                                e.setCancelled(true);
                            }
                        } else {
                            e.setCancelled(false);
                        }
                    }
                }
            }
        }
    }
}
