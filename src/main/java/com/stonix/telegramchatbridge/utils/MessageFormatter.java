package com.stonix.telegramchatbridge.utils;

import com.stonix.telegramchatbridge.TelegramChatBridge;
import org.bukkit.ChatColor;

public class MessageFormatter {
    
    private final TelegramChatBridge plugin;
    
    public MessageFormatter(TelegramChatBridge plugin) {
        this.plugin = plugin;
    }
    
    public String formatMinecraftToTelegram(String playerName, String message) {
        String format = plugin.getConfigManager().getTelegramFormat();
        
        return format
                .replace("{player}", playerName)
                .replace("{message}", message)
                .replace("{server}", plugin.getConfigManager().getServerName());
    }
    
    public String formatTelegramToMinecraft(String username, String message) {
        String format = plugin.getConfigManager().getMinecraftFormat();
        
        return ChatColor.translateAlternateColorCodes('&', format
                .replace("{username}", username)
                .replace("{message}", message)
                .replace("{chat}", "Telegram"));
    }
    
    public String formatCommandMessage(String message) {
        String prefix = plugin.getConfigManager().getCommandPrefix();
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
    }
    
    public String cleanMessage(String message) {
        String cleaned = ChatColor.stripColor(message);
        cleaned = cleaned.replaceAll("[*_`\\[\\]]", "");
        
        int maxLength = plugin.getConfigManager().getMaxMessageLength();
        if (maxLength > 0 && cleaned.length() > maxLength) {
            cleaned = cleaned.substring(0, maxLength - 3) + "...";
        }
        
        return cleaned;
    }
    
    public boolean isValidMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return false;
        }
        
        if (message.startsWith("/")) {
            return false;
        }
        
        if (message.startsWith("[") && message.contains("]")) {
            return false;
        }
        
        return true;
    }
}

