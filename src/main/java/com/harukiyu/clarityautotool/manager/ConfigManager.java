package com.harukiyu.clarityautotool.manager;

import com.harukiyu.clarityautotool.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ConfigManager {
    
    private final Main plugin;
    private final Set<String> disabledPlayers = new HashSet<>();
    private final List<String> disabledWorlds = new ArrayList<>();
    private final List<String> enabledWorlds = new ArrayList<>();
    private boolean useWhitelist = false;
    
    public ConfigManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.reloadConfig();
        
        disabledPlayers.clear();
        List<String> list = plugin.getConfig().getStringList("autotool_disabled");
        disabledPlayers.addAll(list);
        
        disabledWorlds.clear();
        disabledWorlds.addAll(plugin.getConfig().getStringList("disabled_worlds"));
        
        enabledWorlds.clear();
        enabledWorlds.addAll(plugin.getConfig().getStringList("enabled_worlds"));
        
        useWhitelist = plugin.getConfig().getBoolean("use_whitelist", false);
    }

    public boolean isWorldDisabled(String worldName) {
        if (useWhitelist) {
            return !matchesWorld(worldName, enabledWorlds);
        } else {
            return matchesWorld(worldName, disabledWorlds);
        }
    }

    private boolean matchesWorld(String worldName, List<String> list) {
        for (String pattern : list) {
            if (pattern.endsWith("*")) {
                String prefix = pattern.substring(0, pattern.length() - 1);
                if (worldName.startsWith(prefix)) {
                    return true;
                }
            } else if (worldName.equalsIgnoreCase(pattern)) {
                return true;
            }
        }
        return false;
    }

    public String getMessage(String key) {
        return plugin.getConfig().getString("messages." + key, "&cMessage not found: " + key);
    }

    public boolean isAutoToolEnabled(Player player) {
        return !disabledPlayers.contains(player.getUniqueId().toString());
    }

    public void setAutoToolEnabled(Player player, boolean enabled) {
        String uuid = player.getUniqueId().toString();
        
        if (enabled) {
            if (disabledPlayers.remove(uuid)) {
                saveDisabledPlayers();
            }
        } else {
            if (disabledPlayers.add(uuid)) {
                saveDisabledPlayers();
            }
        }
    }

    private void saveDisabledPlayers() {
        plugin.getConfig().set("autotool_disabled", new ArrayList<>(disabledPlayers));
        saveConfigSafely();
    }

    public void saveConfigSafely() {
        if (isFolia()) {
            try {
                Object asyncScheduler = Bukkit.class.getMethod("getAsyncScheduler").invoke(null);
                java.lang.reflect.Method runNow = asyncScheduler.getClass().getMethod("runNow", org.bukkit.plugin.Plugin.class, Consumer.class);
                runNow.invoke(asyncScheduler, plugin, (Consumer<Object>) task -> plugin.saveConfig());
            } catch (ReflectiveOperationException | SecurityException | IllegalArgumentException e) {
                plugin.saveConfig();
            }
        } else {
            plugin.saveConfig();
        }
    }
    
    private boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }
}
