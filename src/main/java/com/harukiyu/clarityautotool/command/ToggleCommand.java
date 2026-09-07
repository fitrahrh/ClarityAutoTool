package com.harukiyu.clarityautotool.command;

import com.harukiyu.clarityautotool.Main;
import com.harukiyu.clarityautotool.manager.ConfigManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleCommand implements CommandExecutor {

    private final Main plugin;

    public ToggleCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ConfigManager configManager = plugin.getConfigManager();

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("autotool.reload")) {
                configManager.loadConfig();
                sendMessage(sender, configManager.getMessage("reloaded"));
                return true;
            } else {
                sendMessage(sender, configManager.getMessage("no_permission"));
                return true;
            }
        }

        if (sender instanceof Player player) {
            if (!player.hasPermission("autotool.use")) {
                sendMessage(sender, configManager.getMessage("no_permission"));
                return true;
            }

            boolean currentState = configManager.isAutoToolEnabled(player);
            configManager.setAutoToolEnabled(player, !currentState);

            if (!currentState) {
                sendMessage(player, configManager.getMessage("autotool_on"));
            } else {
                sendMessage(player, configManager.getMessage("autotool_off"));
            }

            return true;
        }

        sendMessage(sender, "&cOnly players can toggle autotool. Use /autotool reload from console.");
        return true;
    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }
}
