package com.zach.listeners;

import com.zach.PrisonAuctionHouse;
import com.zach.utils.Button;
import com.zach.utils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import static com.zach.PrisonAuctionHouse.getMenuHandler;
import static com.zach.handlers.EconomyHandler.hasEnoughMoney;

public class PurchaseListener implements Listener {

    @EventHandler
    public void purchaseChestEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block clickedBlock = e.getClickedBlock();
        Action action = e.getAction();
        if (clickedBlock == null) return;

        Menu menu = new Menu(Bukkit.createInventory(null, 27, "AuctionHouse"));

        if (action.isRightClick()) {
            if (clickedBlock.getType().equals(Material.CHEST)) {
                Chest chest = (Chest) clickedBlock.getState();
                if (PrisonAuctionHouse.getInstance().creationListener.saveChestData.containsKey(chest.getLocation())) {
                    if (!(PrisonAuctionHouse.getInstance().purchasedHandler.boughtChest(player) && PrisonAuctionHouse.getInstance().purchasedHandler.playersBoughtChest.containsValue(chest.getLocation()))) {
                        if (PrisonAuctionHouse.getInstance().inventoryHandler.savedChestAmounts.get(chest.getLocation()) != null) {

                            menu.setButton(15, new Button(PrisonAuctionHouse.getInstance().inventoryCancelButton.cancelButton()) {
                                @Override
                                public void onClick(Menu menu, InventoryClickEvent clickEvent) {
                                    getMenuHandler().closeMenu((Player) clickEvent.getWhoClicked());
                                    clickEvent.setCancelled(true);
                                }
                            });
                            menu.setButton(11, new Button(PrisonAuctionHouse.getInstance().inventoryBuyButton.buyButton(chest)) {
                                @Override
                                public void onClick(Menu menu, InventoryClickEvent clickEvent) {
                                    if (PrisonAuctionHouse.getInstance().purchasedHandler.boughtChest(player) && hasEnoughMoney(player, chest.getLocation())) {
                                        PrisonAuctionHouse.getInstance().purchasedHandler.resetChestData(player);
                                    }
                                    PrisonAuctionHouse.getInstance().purchasedHandler.buyChest(
                                            chest.getLocation(),
                                            player,
                                            PrisonAuctionHouse.getInstance().inventoryHandler.savedChestAmounts.get(chest.getLocation()).doubleValue());
                                    getMenuHandler().closeMenu((Player) clickEvent.getWhoClicked());
                                    clickEvent.setCancelled(true);
                                }
                            });
                            Inventory i = menu.getInventory();

                            for (int slot = 0; slot < i.getSize(); slot++) {
                                if (i.getItem(slot) == null) {
                                    i.setItem(slot, PrisonAuctionHouse.getInstance().inventoryPlaceHolder.inventoryBlue());
                                }
                            }
                            getMenuHandler().openMenu(player, menu);
                            e.setCancelled(true);

                        }
                    } else {
                        if (clickedBlock == PrisonAuctionHouse.getInstance().purchasedHandler.playersBoughtChest.get(player).getBlock()) {
                            e.setCancelled(false);
                        }
                    }
                }
            }
        }

    }
}
