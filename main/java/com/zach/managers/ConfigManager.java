package com.zach.managers;

import com.zach.PrisonAuctionHouse;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public static FileConfiguration config;

    public static void setupConfig(PrisonAuctionHouse auctionHouse) {
        ConfigManager.config = auctionHouse.getConfig();
        auctionHouse.saveDefaultConfig();
    }
    public static Integer getMinPrice() { return config.getInt("min-price"); }
    public static Integer getMaxPrice() { return config.getInt("max-price"); }
}
