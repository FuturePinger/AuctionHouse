package com.zach.handlers;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.zach.AuctionHouse;
import com.zach.managers.ConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryHandler {

    public HashMap<Location, Integer> savedChestAmounts = new HashMap<>();
    public List<Barrel> cellBarrels = new ArrayList<>();
    public List<Chest> auctionHouseBarrel = new ArrayList<>();
    public List<Chest> cellChests = new ArrayList<>();
    public List<Chest> auctionHouseChest = new ArrayList<>();
    public List<Chest> largeCellChest = new ArrayList<>();
    public List<Chest> largeAuctionHouseChest = new ArrayList<>();

    public void addCellBarrels(Barrel barrel) {
        cellBarrels.add(barrel);
    }
    public Integer getCellBarrelsAmount() { return cellBarrels.size(); }

    public Barrel getCellBarrel(Integer integer) { return cellBarrels.get(integer); }

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

    public void createBarrelAuctionHouseChest() {
        for (int i = 0; i <= getCellBarrelsAmount(); i++) {
            auctionHouseChestData(i, auctionHouseBarrel, savedChestAmounts);
            if (i > AuctionHouse.getInstance().creationListener.saveChestData.size())
                break;
        }
        setAuctionHouseBarrelContents();
    }

    public void createAuctionHouseChest() {
        //looping through the amount of chest grabbed from the PrisonCell
        for (int i = 0; i <= getCellChestsAmount(); i++) {
            //loop through the all the locations available to place a chest at
            auctionHouseChestData(i, auctionHouseChest, savedChestAmounts);
            if (i > AuctionHouse.getInstance().creationListener.saveChestData.size())
                break;
        }
        setAuctionHouseChestContents();
    }

    public void createLargeAuctionHouseChest() {
        //looping through the amount of large chest grabbed from the PrisonCell and dividing the amount of chest in half due to being two chest
        for (int i = 0; i <= getLargeCellChestAmount() / 2; i++) {
            //looping through all the locations available to place a chest at
            auctionHouseChestData(i, largeAuctionHouseChest, savedChestAmounts);
            if (i > AuctionHouse.getInstance().creationListener.saveChestData.size())
                break;
        }
        setLargeCellChestContents();
    }
    public void setAuctionHouseBarrelContents() {
        for (int i = 0; i < getCellBarrelsAmount(); i++) {
            for (ItemStack items : getCellBarrel(i).getInventory()) {
                if (items == null)
                    continue;
                auctionHouseBarrel.get(i).getInventory().addItem(items);
            }
        }
        auctionHouseChest.clear();
        cellChests.clear();
    }

    public void setLargeCellChestContents() {
        //gets the large chest and turns it into one single chest and add items to its inventory
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
        //gets each single chest and adds items to its inventory
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

    private void auctionHouseChestData(int i, List<Chest> chestList, HashMap<Location, Integer> chestAmounts)  {
        //loop through the all the locations available to place a chest at and looping through the direction strings
        for (Map.Entry<Location, String> location : AuctionHouse.getInstance().creationListener.saveChestData.entrySet()) {
            //if the amount of chest in the auction house < then the amount of cell chest
            if (chestList.size() < i) {
                if (location.getKey().getBlock().isEmpty()) {

                    Random random = new Random();
                    Integer randomInt = random.nextInt((ConfigManager.getMaxPrice()- ConfigManager.getMinPrice()) + 1) + ConfigManager.getMinPrice();

                    Block block = location.getKey().getBlock();
                    block.setType(Material.CHEST);
                    Chest chest = (Chest) block.getState();

                    //temporarily saves the places chest to set the contents of it
                    chestList.add(chest);
                    //adds the chest and the price of the chest to a hashmap
                    chestAmounts.put(chest.getLocation(), randomInt);

                    //changes direction of the chest
                    Directional direction = (Directional) block.getBlockData();
                    direction.setFacing(BlockFace.valueOf(location.getValue().toUpperCase()));
                    block.setBlockData(direction);

                    Location holoLoc = location.getKey().clone();
                    //spawns a hologram above the chest with the random price
                    CMIHologram holo = new CMIHologram(String.valueOf(holoLoc), holoLoc.add(0.5, 1.5, 0.5));
                    holo.setLines(Arrays.asList("Click to Buy", "$" + randomInt));
                    CMI.getInstance().getHologramManager().addHologram(holo);
                    holo.update();


                }
            }
        }
    }
}
