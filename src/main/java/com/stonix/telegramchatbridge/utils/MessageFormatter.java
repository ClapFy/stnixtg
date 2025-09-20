package com.stonix.telegramchatbridge.utils;

import com.stonix.telegramchatbridge.TelegramChatBridge;
import org.bukkit.ChatColor;

public class MessageFormatter {
    
    private final TelegramChatBridge plugin;
    
    public MessageFormatter(TelegramChatBridge plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Format a message from Minecraft to Telegram
     */
    public String formatMinecraftToTelegram(String playerName, String message) {
        String format = plugin.getConfigManager().getTelegramFormat();
        
        return format
                .replace("{player}", playerName)
                .replace("{message}", message)
                .replace("{server}", plugin.getConfigManager().getServerName());
    }
    
    /**
     * Format a message from Telegram to Minecraft
     */
    public String formatTelegramToMinecraft(String username, String message) {
        String format = plugin.getConfigManager().getMinecraftFormat();
        
        return ChatColor.translateAlternateColorCodes('&', format
                .replace("{username}", username)
                .replace("{message}", message)
                .replace("{chat}", "Telegram"));
    }
    
    /**
     * Format a command message with prefix
     */
    public String formatCommandMessage(String message) {
        String prefix = plugin.getConfigManager().getCommandPrefix();
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
    }
    
    /**
     * Clean message text by removing color codes and limiting length
     */
    public String cleanMessage(String message) {
        // Remove Minecraft color codes
        String cleaned = ChatColor.stripColor(message);
        
        // Remove Telegram markdown if present
        cleaned = cleaned.replaceAll("[*_`\\[\\]]", "");
        
        // Limit message length
        int maxLength = plugin.getConfigManager().getMaxMessageLength();
        if (maxLength > 0 && cleaned.length() > maxLength) {
            cleaned = cleaned.substring(0, maxLength - 3) + "...";
        }
        
        return cleaned;
    }
    
    /**
     * Check if message contains only valid characters
     */
    public boolean isValidMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return false;
        }
        
        // Check for command messages (should not be forwarded)
        if (message.startsWith("/")) {
            return false;
        }
        
        // Check for server messages (should not be forwarded)
        if (message.startsWith("[") && message.contains("]")) {
            return false;
        }
        
        return true;
    }
}

