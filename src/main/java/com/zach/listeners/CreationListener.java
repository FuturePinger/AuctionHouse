package com.zach.listeners;

import com.zach.AuctionHouse;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreationListener implements Listener {
    private static String color(String string) {
        return (ChatColor.translateAlternateColorCodes('&', string));
    }

    public List<Player> isCreating = new ArrayList<>();
    public List<Player> isCreatingDirection = new ArrayList<>();
    public List<Location> isCreatingChestLocations = new ArrayList<>();
    public List<String> chestDirection = new ArrayList<>();
    public List<String> chestLocationNames = new ArrayList<>();

    public HashMap<Location, String> saveChestDirections = new HashMap<>();
    public List<String> savedChestLocationNames = new ArrayList<>();



    @EventHandler
    public void blockSelection(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta() != null
                && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(color("&fCreation Wand"))
                && e.getHand() == EquipmentSlot.HAND) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock() != null) {
                    isCreating.add(player);
                    isCreatingChestLocations.add(e.getClickedBlock().getLocation());
                    player.sendMessage(color("&bAuction&8-&6House &8// &7Please input a location name, using English-only alphabet. Type 'exit' to cancel!"));

                }
            }
        }
    }

    @EventHandler
    public void creationEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();
        if (isCreating.contains(player)) {
            if (message.equals("exit")) {
                isCreating.remove(e.getPlayer());
                chestLocationNames.clear();
                isCreatingChestLocations.clear();
                e.setCancelled(true);
            } else {
                chestLocationNames.add(e.getMessage());
                savedChestLocationNames.add(e.getMessage());
                e.setCancelled(true);
                e.getPlayer().sendMessage(color("&bAuction&8-&6House &8// &7Location '" + e.getMessage() + "&7' has been created! Also what way you want chest to face"));

                AuctionHouse.getInstance().dataManager.create(e.getMessage(), isCreatingChestLocations.get(0));
                isCreatingDirection.add(e.getPlayer());
                isCreating.remove(e.getPlayer());
                isCreatingChestLocations.clear();
                chestLocationNames.clear();
            }
        }
    }
    @EventHandler
    public void directionStage(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (chestDirection.isEmpty()) {
            if (!isCreating.contains(player)) {
                if (isCreatingDirection.contains(player)) {
                    if (e.getMessage().equals("exit")) {
                        isCreating.remove(e.getPlayer());
                        chestLocationNames.clear();
                        isCreatingChestLocations.clear();
                        e.setCancelled(true);
                    } else {
                        AuctionHouse.getInstance().dataManager.setDirection(e.getMessage());
                        e.getPlayer().sendMessage("done");
                        isCreatingDirection.clear();

                    }
                }
            }
        }
    }
}
