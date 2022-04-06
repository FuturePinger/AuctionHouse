package com.zach;

import com.zach.commands.AuctionHouseCommand;
import com.zach.commands.AuctionHouseTabCompleter;
import com.zach.handlers.InventoryHandler;
import com.zach.handlers.PurchasedHandler;
import com.zach.handlers.WandHandler;
import com.zach.items.InventoryBuyButton;
import com.zach.items.InventoryCancelButton;
import com.zach.items.InventoryPlaceHolder;
import com.zach.items.WandCreation;
import com.zach.listeners.CellResetListener;
import com.zach.listeners.CreationListener;
import com.zach.listeners.PurchaseListener;
import com.zach.managers.ConfigManager;
import com.zach.managers.DataManager;
import com.zach.utils.MenuHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public final class PrisonAuctionHouse extends JavaPlugin {
    private static PrisonAuctionHouse instance;
    public static PrisonAuctionHouse getInstance() {return instance;}

    private static Economy economy = null;
    public DataManager dataManager;
    static MenuHandler menuHandler;
    public InventoryBuyButton inventoryBuyButton;
    public InventoryCancelButton inventoryCancelButton;
    public InventoryPlaceHolder inventoryPlaceHolder;
    public WandCreation wandCreation;
    public CreationListener creationListener;
    public WandHandler wandHandler;
    public InventoryHandler inventoryHandler;
    public PurchasedHandler purchasedHandler;
    private static final Logger log = Logger.getLogger("Minecraft");


    @Override
    public void onEnable() {

        instance = this;
        menuHandler = new MenuHandler();
        this.dataManager = new DataManager();
        this.inventoryBuyButton = new InventoryBuyButton();
        this.inventoryCancelButton = new InventoryCancelButton();
        this.inventoryPlaceHolder = new InventoryPlaceHolder();
        this.wandCreation = new WandCreation();
        this.creationListener = new CreationListener();
        this.wandHandler = new WandHandler();
        this.inventoryHandler = new InventoryHandler();
        this.purchasedHandler = new PurchasedHandler();

        ConfigManager.setupConfig(this);

        Bukkit.getPluginCommand("auctionhouse").setExecutor(new AuctionHouseCommand());

        Bukkit.getPluginCommand("auctionhouse").setTabCompleter(new AuctionHouseTabCompleter());

        Bukkit.getPluginManager().registerEvents(new CellResetListener(), this);
        Bukkit.getPluginManager().registerEvents(new CreationListener(), this);
        Bukkit.getPluginManager().registerEvents(new PurchaseListener(), this);
        Bukkit.getPluginManager().registerEvents(menuHandler.getListeners(), this);

        dataManager.loadChestDataConfig();

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

    }

    @Override
    public void onDisable() {
        purchasedHandler.resetChestData();
        menuHandler.closeAll();
    }

    public static MenuHandler getMenuHandler() {
        return menuHandler;
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
