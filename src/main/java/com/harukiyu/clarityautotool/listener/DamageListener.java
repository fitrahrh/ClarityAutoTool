package com.harukiyu.clarityautotool.listener;

import com.harukiyu.clarityautotool.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class DamageListener implements Listener {

    private final Main plugin;

    public DamageListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockDamage(BlockDamageEvent e) {
        if (e.getInstaBreak()) return;
        
        if (!e.getPlayer().hasPermission("autotool.use")) return;
        
        if (!plugin.getConfigManager().isAutoToolEnabled(e.getPlayer())) return;
        
        plugin.getToolSwapper().autoTool(e.getPlayer(), e.getBlock());
    }
}
