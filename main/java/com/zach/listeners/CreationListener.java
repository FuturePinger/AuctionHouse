package com.zach.listeners;

import com.zach.PrisonAuctionHouse;
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

public class CreationListener implements Listener {
    private static String color(String string) {
        return (ChatColor.translateAlternateColorCodes('&', string));
    }

    public List<Player> isCreating = new ArrayList<>();
    public List<Player> isCreatingDirection = new ArrayList<>();
    public List<Location> isCreatingChestLocations = new ArrayList<>();
    public List<String> chestDirection = new ArrayList<>();
    public List<String> chestLocationNames = new ArrayList<>();

    public HashMap<Location, String> saveChestData = new HashMap<>();
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
        if (!isCreatingDirection.contains(player)) {
            if (isCreating.contains(player)) {
                if (message.equals("exit")) {
                    cancelCreation(player, color("&bAuction&8-&6House &8// &7Creation canceled"));
                    e.setCancelled(true);
                } else {
                    chestLocationNames.add(e.getMessage());
                    savedChestLocationNames.add(e.getMessage());
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(color("&bAuction&8-&6House &8// &7Location '"
                            + e.getMessage() +
                            "&7' has been created! Also what way you want chest to face? " +
                            "(NORTH, EAST, WEST, SOUTH) Type 'exit' to cancel!"));

                    PrisonAuctionHouse.getInstance().dataManager.create(e.getMessage(), isCreatingChestLocations.get(0));
                    PrisonAuctionHouse.getInstance().dataManager.loadChestDataConfig();
                    isCreating.remove(e.getPlayer());
                    isCreatingChestLocations.clear();
                    chestLocationNames.clear();
                    isCreatingDirection.add(player);
                }
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
                        cancelCreation(player, color("&bAuction&8-&6House &8// &7Creation canceled!"));
                        e.setCancelled(true);
                    } else switch (e.getMessage()) {
                        case "NORTH", "EAST", "WEST", "SOUTH" -> {
                            PrisonAuctionHouse.getInstance().dataManager.setDirection(e.getMessage());
                            e.getPlayer().sendMessage(color("&bAuction&8-&6House &8// &7Chest location created successfully!"));
                            isCreatingDirection.clear();
                            e.setCancelled(true);
                            PrisonAuctionHouse.getInstance().dataManager.loadChestDataConfig();
                        }
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

    private void cancelCreation(Player player, String string) {
        player.sendMessage(string);
        isCreating.remove(player);
        chestLocationNames.clear();
        isCreatingChestLocations.clear();
    }
}
