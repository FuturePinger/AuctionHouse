package com.zach.managers;

import com.zach.PrisonAuctionHouse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataManager {
    static File chestLocationsFile;
    static File chestFolder;
    static FileConfiguration config;

    public FileConfiguration getConfig() { return config; }

    public void save() {
        try {
            config.save(chestLocationsFile);
        } catch (Exception e) {
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            console.sendMessage(ChatColor.RED + "Error saving " + chestLocationsFile.getName() + "!");
        }
    }
    public void create(String string, Location location) {
        chestFolder = new File(PrisonAuctionHouse.getInstance().getDataFolder(), "chestlocations" + File.separator);
        chestLocationsFile = new File(chestFolder, File.separator + string + ".yml");
        if (!chestFolder.exists()) {
            chestFolder.mkdir();
        }
        if (!chestLocationsFile.exists()) {
            try {
                chestLocationsFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(chestLocationsFile);
        getConfig().set("chest-locations", location);
        save();
    }
    public void setDirection(String str) {
        config = YamlConfiguration.loadConfiguration(chestLocationsFile);
        getConfig().set("chest-direction", str);
        save();
    }
    public void loadChestDataConfig() {
        chestFolder = new File(PrisonAuctionHouse.getInstance().getDataFolder(), "chestlocations" + File.separator);
        try {
            for (File file : chestFolder.listFiles()) {
                config = YamlConfiguration.loadConfiguration(file);
                PrisonAuctionHouse.getInstance().creationListener.saveChestData.put(config.getLocation("chest-locations"), config.getString("chest-direction"));
            }
        } catch (NullPointerException ignored) {

        }
    }

}
