package com.zach.managers;

import com.zach.AuctionHouse;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public static FileConfiguration config;

    public static void setupConfig(AuctionHouse auctionHouse) {
        ConfigManager.config = auctionHouse.getConfig();
        auctionHouse.saveDefaultConfig();
    }
}
