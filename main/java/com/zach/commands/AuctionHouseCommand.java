package com.zach.commands;

import com.zach.PrisonAuctionHouse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AuctionHouseCommand implements CommandExecutor {
    private static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                //add command list (help memu)
                return false;
            }
            switch (args[0]) {
                case "create":
                    if (player.hasPermission("auctionhouse.create")) {
                        PrisonAuctionHouse.getInstance().wandHandler.creation(player);
                        player.sendMessage(color("&bAuction&8-&6House &8// &7You have been provided the &7&nCreation Wand&7!"));
                    }
                    break;
            }

        }
        return false;
    }
}
