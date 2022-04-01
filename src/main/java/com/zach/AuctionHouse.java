package com.zach;

import com.zach.commands.AuctionHouseCommand;
import com.zach.handlers.InventoryHandler;
import com.zach.handlers.WandHandler;
import com.zach.items.WandCreation;
import com.zach.listeners.CellResetListener;
import com.zach.listeners.CreationListener;
import com.zach.managers.ConfigManager;
import com.zach.managers.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.DataSource;

public final class AuctionHouse extends JavaPlugin {
    private static AuctionHouse instance;
    public static AuctionHouse getInstance() {return instance;}

    public WandCreation wandCreation;
    public DataManager dataManager;
    public CreationListener creationListener;
    public WandHandler wandHandler;
    public InventoryHandler inventoryHandler;


    @Override
    public void onEnable() {
        System.out.println("Enabled");
        instance = this;
        this.wandCreation = new WandCreation();
        this.dataManager = new DataManager();
        this.creationListener = new CreationListener();
        this.wandHandler = new WandHandler();
        this.inventoryHandler = new InventoryHandler();

        ConfigManager.setupConfig(this);

        Bukkit.getPluginCommand("auctionhouse").setExecutor(new AuctionHouseCommand());

        Bukkit.getPluginManager().registerEvents(new CellResetListener(), this);
        Bukkit.getPluginManager().registerEvents(new CreationListener(), this);

        dataManager.loadChestDataConfig();


    }

    @Override
    public void onDisable() {

    }
}
