package com.zach.managers;

import com.zach.PrisonAuctionHouse;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(PrisonAuctionHouse auctionHouse) {
        ConfigManager.config = auctionHouse.getConfig();
        auctionHouse.saveDefaultConfig();
    }
    public Integer getMinPrice() { return config.getInt("min-price"); }
    public Integer getMaxPrice() { return config.getInt("max-price"); }
    public Integer getTimeTillExpire() { return config.getInt("time-till-expire"); }
}
