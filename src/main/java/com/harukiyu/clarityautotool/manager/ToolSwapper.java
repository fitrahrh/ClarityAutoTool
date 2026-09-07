package com.harukiyu.clarityautotool.manager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ToolSwapper {

    public ToolSwapper() {}

    public void autoTool(Player player, Block block) {
        PlayerInventory inventory = player.getInventory();
        
        ItemStack handItem = inventory.getItemInMainHand();
        if (handItem.getType() == Material.AIR) {
            handItem = new ItemStack(Material.STONE); // Mock item to avoid null/air errors
        }
        
        float currentSpeed = getBlockDestroySpeed(block, handItem);
        boolean currentPreferred = isPreferred(block, handItem);
        
        float bestSpeed = currentSpeed;
        boolean bestPreferred = currentPreferred;
        int bestSlot = -1;

        for (int i = 0; i < 9; i++) {
            if (i == inventory.getHeldItemSlot()) continue;
            
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            
            float speed = getBlockDestroySpeed(block, itemStack);
            boolean preferred = isPreferred(block, itemStack);
            
            if (preferred && !bestPreferred) {
                bestSpeed = speed;
                bestPreferred = true;
                bestSlot = i;
            } else if (preferred == bestPreferred) {
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestPreferred = preferred;
                    bestSlot = i;
                }
            }
        }

        if (bestSlot != -1) {
            inventory.setHeldItemSlot(bestSlot);
        }
    }

    private float getBlockDestroySpeed(Block block, ItemStack item) {
        try {
            return block.getDestroySpeed(item, true); 
        } catch (NoSuchMethodError e) {
            try {
                return block.getDestroySpeed(item);
            } catch (NoSuchMethodError ex) {
                return 1.0f;
            }
        }
    }

    private boolean isPreferred(Block block, ItemStack item) {
        try {
            return block.isPreferredTool(item);
        } catch (NoSuchMethodError e) {
            return false;
        }
    }
}
