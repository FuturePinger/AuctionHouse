package com.zach.handlers;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.zach.AuctionHouse;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InventoryHandler {

    public List<Chest> cellChests = new ArrayList<>();
    public List<Chest> auctionHouseChest = new ArrayList<>();
    public List<Chest> largeCellChest = new ArrayList<>();
    public List<Chest> largeAuctionHouseChest = new ArrayList<>();

    public void addLargeCellChest(Chest chest) {
        largeCellChest.add(chest);
    }

    public Integer getLargeCellChestAmount() {
        return largeCellChest.size();
    }

    public Chest getLargeCellChest(Integer integer) {
        return largeCellChest.get(integer);
    }

    public void addCellChests(Chest chest) {
        cellChests.add(chest);
    }

    public Integer getCellChestsAmount() {
        return cellChests.size();
    }

    public Chest getCellChest(Integer integer) {
        return cellChests.get(integer);
    }

    public void createAuctionHouseChest() {
        //looping through the amount of chest grabbed from the PrisonCell
        for (int i = 0; i <= getCellChestsAmount(); i++) {
            //loop through the all the locations available to place a chest at
            auctionHouseChestData(i, auctionHouseChest);
            if (i > AuctionHouse.getInstance().creationListener.saveChestData.size())
                break;
        }
        setAuctionHouseChestContents();
    }

    public void createLargeAuctionHouseChest() {
        //looping through the amount of large chest grabbed from the PrisonCell and dividing the amount of chest in half due to being two chest
        for (int i = 0; i <= getLargeCellChestAmount() / 2; i++) {
            //looping through all the locations available to place a chest at
            auctionHouseChestData(i, largeAuctionHouseChest);
        }
        setLargeCellChestContents();
    }

    private void auctionHouseChestData(int i, List<Chest> AuctionHouseChest) {
        //loop through the all the locations available to place a chest at and looping through the direction strings
        for (Map.Entry<Location, String> location : AuctionHouse.getInstance().creationListener.saveChestData.entrySet()) {
            //if the amount of chest in the auction house < then the amount of cell chest
            if (AuctionHouseChest.size() < i) {
                if (location.getKey().getBlock().isEmpty()) {
                    Block block = location.getKey().getBlock();
                    block.setType(Material.CHEST);
                    Chest chest = (Chest) block.getState();
                    AuctionHouseChest.add(chest);

                    Directional direction = (Directional) block.getBlockData();
                    direction.setFacing(BlockFace.valueOf(location.getValue().toUpperCase()));
                    block.setBlockData(direction);

                    CMIHologram holo = new CMIHologram(String.valueOf(location.getKey()), location.getKey().add(0.5, 1.5, 0.5));
                    holo.setLines(Arrays.asList("test", "test"));
                    CMI.getInstance().getHologramManager().addHologram(holo);
                    holo.update();
                }
            }
        }
    }

    public void setLargeCellChestContents() {
        for (int i = 0; i < getLargeCellChestAmount() / 2; i++) {
            for (ItemStack items : getLargeCellChest(i).getInventory()) {
                if (items == null)
                    continue;
                largeAuctionHouseChest.get(i).getInventory().addItem(items);
            }
        }
        largeCellChest.clear();
        largeAuctionHouseChest.clear();
    }

    public void setAuctionHouseChestContents() {
        for (int i = 0; i < getCellChestsAmount(); i++) {
            for (ItemStack items : getCellChest(i).getInventory()) {
                if (items == null)
                    continue;
                auctionHouseChest.get(i).getInventory().addItem(items);
            }
        }
        auctionHouseChest.clear();
        cellChests.clear();
    }
}
