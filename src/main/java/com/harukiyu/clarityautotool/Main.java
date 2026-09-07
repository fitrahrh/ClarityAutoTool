package com.harukiyu.clarityautotool;

import com.harukiyu.clarityautotool.command.ToggleCommand;
import com.harukiyu.clarityautotool.listener.DamageListener;
import com.harukiyu.clarityautotool.manager.ConfigManager;
import com.harukiyu.clarityautotool.manager.ToolSwapper;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    private static Main instance;
    private ConfigManager configManager;
    private ToolSwapper toolSwapper;

    @Override
    public void onEnable() {
        instance = this;
        
        // Setup Config
        saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        this.configManager.loadConfig();
        
        this.toolSwapper = new ToolSwapper();
        
        // Register Listeners
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        
        // Register Commands
        var command = getCommand("autotool");
        if (command != null) {
            command.setExecutor(new ToggleCommand(this));
        }
        
        getLogger().info("ClarityAutoTool has been enabled! Running clean architecture for 1.21+.");
    }

    @Override
    public void onDisable() {
        if (configManager != null) {
            configManager.saveConfigSafely();
        }
        getLogger().info("ClarityAutoTool has been disabled!");
    }
    
    public static Main getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ToolSwapper getToolSwapper() {
        return toolSwapper;
    }
}
