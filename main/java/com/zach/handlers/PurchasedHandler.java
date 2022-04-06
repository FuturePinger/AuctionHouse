package com.zach.handlers;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.zach.PrisonAuctionHouse;
import com.zach.utils.Button;
import com.zach.utils.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.zach.PrisonAuctionHouse.getMenuHandler;
import static com.zach.handlers.EconomyHandler.hasEnoughMoney;

public class PurchasedHandler {
    private static String color(String string) {
        return (ChatColor.translateAlternateColorCodes('&', string));
    }

    public Map<Player, Location> playersBoughtChest = new HashMap<>();

    private void buyChest(Location location, Player player, Double amount) {
        if (EconomyHandler.hasEnoughMoney(player, location)) {
            EconomyHandler.withDrawMoney(player, amount);
            playersBoughtChest.put(player, location);
            CMIHologram hologram = CMI.getInstance().getHologramManager().getByName(location.toString());
            hologram.setLines(Arrays.asList(color("&e&lBuyer&8&l: "), color("&f&L" + player.getName())));
            hologram.update();
            player.sendMessage(color("&bAuction&8-&6House &8// &7You have purchased this chest. You are now allowed to open it... Enjoy!"));
            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
            resetChestDataTimer(player, location);
        } else {
            player.sendMessage(color("&bAuction&8-&6House &8// &7What do you think you're trying to do! You don't have the money for that!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }
    private void resetChestDataTimer(Player player, Location location) {
        final int[] countdown = {PrisonAuctionHouse.getInstance().configManager.getTimeTillExpire()};
        (new BukkitRunnable() {
            public void run() {
                if (playersBoughtChest.containsKey(player) && playersBoughtChest.containsValue(location)) {
                    countdown[0]--;
                    if (countdown[0] <= 0) {
                        resetChestData(player);
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }).runTaskTimer(PrisonAuctionHouse.getInstance(), 0L, 20L);
    }

    public void resetChestData() {
        for (Map.Entry<Player, Location> entry : playersBoughtChest.entrySet()) {
            entry.getValue().getBlock().setType(Material.AIR);
        }
        for (Map.Entry<Location, Integer> entry : PrisonAuctionHouse.getInstance().inventoryHandler.savedChestAmounts.entrySet()) {
            entry.getKey().getBlock().setType(Material.AIR);
            CMI.getInstance().getHologramManager().getByName(entry.getValue().toString());
        }
        playersBoughtChest.clear();
    }

    private void resetChestData(Player player) {
        CMI.getInstance().getHologramManager().getByName(playersBoughtChest.get(player).getBlock().getLocation().toString()).remove();
        playersBoughtChest.get(player).getBlock().setType(Material.AIR);
        playersBoughtChest.remove(player);


    }

    public boolean boughtChest(Player player) {
        return playersBoughtChest.containsKey(player);
    }
    public void setPlaceHolders(Integer startingSlot, Integer endingSlot, Menu menu, ItemStack item) {
        for (int slot = startingSlot; slot <= endingSlot; slot++) {
            menu.setButton(slot, new Button(item) {
                @Override
                public void onClick(Menu menu, InventoryClickEvent event) {
                    event.setCancelled(true);
                }
            });
        }
    }
    public void setBuyButton(Menu menu, Integer slot, Chest chest, Player player) {
        menu.setButton(slot, new Button(PrisonAuctionHouse.getInstance().inventoryBuyButton.buyButton(chest)) {
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
    }
    public void setCancelButton(Menu menu, Integer slot) {
        menu.setButton(slot, new Button(PrisonAuctionHouse.getInstance().inventoryCancelButton.cancelButton()) {
            @Override
            public void onClick(Menu menu, InventoryClickEvent clickEvent) {
                getMenuHandler().closeMenu((Player) clickEvent.getWhoClicked());
                clickEvent.setCancelled(true);
            }
        });
    }
}
