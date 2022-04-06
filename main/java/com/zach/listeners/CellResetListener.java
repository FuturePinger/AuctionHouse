package com.zach.listeners;

import com.zach.PrisonAuctionHouse;
import net.alex9849.arm.events.RestoreRegionEvent;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class CellResetListener implements Listener {

    @EventHandler
    public void cellReset(RestoreRegionEvent e) {
        Vector min = e.getRegion().getRegion().getMinPoint();
        Vector max = e.getRegion().getRegion().getMaxPoint();
        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    Block block = e.getRegion().getRegionworld().getBlockAt(x, y, z);
                    if (block.getType().equals(Material.CHEST)) {
                        Chest chest = (Chest) block.getState();
                        if (!chest.getInventory().isEmpty()) {
                            if (chest.getInventory().getSize() <= 27) {
                                //adding all single chest into a list
                                PrisonAuctionHouse.getInstance().inventoryHandler.addCellChests(chest);
                            } else if (chest.getInventory().getSize() > 27) {
                                PrisonAuctionHouse.getInstance().inventoryHandler.addLargeCellChest(chest);
                            }
                        }
                    }
                    if (block.getType().equals(Material.BARREL)) {
                        Barrel barrel = (Barrel) block.getState();
                        if (!barrel.getInventory().isEmpty()) {
                            if (barrel.getInventory().getSize() <= 27) {
                                //adding all barrels into a list
                                PrisonAuctionHouse.getInstance().inventoryHandler.addCellBarrels(barrel);
                            }
                        }
                    }
                }
            }
        }
        PrisonAuctionHouse.getInstance().inventoryHandler.createLargeAuctionHouseChest();
        PrisonAuctionHouse.getInstance().inventoryHandler.createAuctionHouseChest();
        PrisonAuctionHouse.getInstance().inventoryHandler.createBarrelAuctionHouseChest();

    }


}
