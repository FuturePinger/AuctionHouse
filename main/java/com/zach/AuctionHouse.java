package com.zach;

import com.zach.commands.AuctionHouseCommand;
import com.zach.handlers.EconomyHandler;
import com.zach.handlers.InventoryHandler;
import com.zach.handlers.PurchasedHandler;
import com.zach.handlers.WandHandler;
import com.zach.items.WandCreation;
import com.zach.listeners.CellResetListener;
import com.zach.listeners.CreationListener;
import com.zach.listeners.PurchaseListener;
import com.zach.managers.ConfigManager;
import com.zach.managers.DataManager;
import net.alex9849.arm.commands.BuyCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.DataSource;
import java.util.logging.Logger;


public final class AuctionHouse extends JavaPlugin {
    private static AuctionHouse instance;
    public static AuctionHouse getInstance() {return instance;}

    private static Economy economy = null;
    public WandCreation wandCreation;
    public DataManager dataManager;
    public CreationListener creationListener;
    public WandHandler wandHandler;
    public InventoryHandler inventoryHandler;
    public PurchasedHandler purchasedHandler;
    private static final Logger log = Logger.getLogger("Minecraft");


    @Override
    public void onEnable() {
        System.out.println("Enabled");
        instance = this;
        this.wandCreation = new WandCreation();
        this.dataManager = new DataManager();
        this.creationListener = new CreationListener();
        this.wandHandler = new WandHandler();
        this.inventoryHandler = new InventoryHandler();
        this.purchasedHandler = new PurchasedHandler();

        ConfigManager.setupConfig(this);

        Bukkit.getPluginCommand("auctionhouse").setExecutor(new AuctionHouseCommand());

        Bukkit.getPluginManager().registerEvents(new CellResetListener(), this);
        Bukkit.getPluginManager().registerEvents(new CreationListener(), this);
        Bukkit.getPluginManager().registerEvents(new PurchaseListener(), this);

        dataManager.loadChestDataConfig();

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {

    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
    public static Economy getEconomy() {
        return economy;
    }

}
