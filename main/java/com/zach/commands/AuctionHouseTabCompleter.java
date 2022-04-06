package com.zach.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AuctionHouseTabCompleter implements TabCompleter {
    public AuctionHouseTabCompleter() {

    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> arguments = new ArrayList<>();
        Player player = (Player) sender;
        if (args.length == 1) {
            if (player.hasPermission("auctionhouse.create")) {
                arguments.add("create");
            }
        }
        return arguments;
    }
}
