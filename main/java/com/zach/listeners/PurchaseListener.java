package com.zach.listeners;

import com.zach.PrisonAuctionHouse;
import com.zach.utils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

import static com.zach.PrisonAuctionHouse.getMenuHandler;

public class PurchaseListener implements Listener {
    private static String color(String string) {
        return (ChatColor.translateAlternateColorCodes('&', string));
    }

    @EventHandler
    public void purchaseChestEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block clickedBlock = e.getClickedBlock();
        Action action = e.getAction();
        Menu menu = new Menu(Bukkit.createInventory(null, 27, "AuctionHouse"));
        Inventory i = menu.getInventory();

        if (clickedBlock == null) return;

        if (action.isRightClick()) {
            if (clickedBlock.getType().equals(Material.CHEST)) {
                Chest chest = (Chest) clickedBlock.getState();
                if (PrisonAuctionHouse.getInstance().creationListener.saveChestData.containsKey(chest.getLocation())) {
                    e.setCancelled(true);
                    if (!(PrisonAuctionHouse.getInstance().purchasedHandler.playersBoughtChest.containsKey(player)) && PrisonAuctionHouse.getInstance().purchasedHandler.playersBoughtChest.containsValue(chest.getLocation())) {
                        e.setCancelled(true);
                        player.sendMessage(color("&bAuction&8-&6House &8// That chest is already bought!"));
                        return;
                    }
                    if (!(PrisonAuctionHouse.getInstance().purchasedHandler.boughtChest(player) && PrisonAuctionHouse.getInstance().purchasedHandler.playersBoughtChest.containsValue(chest.getLocation()))) {
                        if (PrisonAuctionHouse.getInstance().inventoryHandler.savedChestAmounts.get(chest.getLocation()) != null) {
                            PrisonAuctionHouse.getInstance().purchasedHandler.setPlaceHolders(0, 9, menu, PrisonAuctionHouse.getInstance().inventoryPlaceHolder.inventoryBlue());
                            PrisonAuctionHouse.getInstance().purchasedHandler.setPlaceHolders(10, 16, menu, PrisonAuctionHouse.getInstance().inventoryPlaceHolder.inventoryOrange());
                            PrisonAuctionHouse.getInstance().purchasedHandler.setPlaceHolders(17, 26, menu, PrisonAuctionHouse.getInstance().inventoryPlaceHolder.inventoryBlue());
                            PrisonAuctionHouse.getInstance().purchasedHandler.setCancelButton(menu, 15);
                            PrisonAuctionHouse.getInstance().purchasedHandler.setBuyButton(menu, 11, chest, player);

                            getMenuHandler().openMenu(player, menu);
                            e.setCancelled(true);

                        }
                    } else if (Objects.equals(clickedBlock.getLocation().toString(), PrisonAuctionHouse.getInstance().purchasedHandler.playersBoughtChest.get(player).toString())) {
                        e.setCancelled(false);
                    }
                }
            }
        }
    }
}
