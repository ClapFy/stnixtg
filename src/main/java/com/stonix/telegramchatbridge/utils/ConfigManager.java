package com.stonix.telegramchatbridge.utils;

import com.stonix.telegramchatbridge.TelegramChatBridge;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    
    private final TelegramChatBridge plugin;
    private FileConfiguration config;
    
    public ConfigManager(TelegramChatBridge plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }
    
    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }
    
    public String getString(String path, String defaultValue) {
        return config.getString(path, defaultValue);
    }
    
    public boolean getBoolean(String path, boolean defaultValue) {
        return config.getBoolean(path, defaultValue);
    }
    
    public int getInt(String path, int defaultValue) {
        return config.getInt(path, defaultValue);
    }
    
    public double getDouble(String path, double defaultValue) {
        return config.getDouble(path, defaultValue);
    }
    
    public long getLong(String path, long defaultValue) {
        return config.getLong(path, defaultValue);
    }
    
    public String getTelegramBotToken() {
        return getString("telegram.bot-token", "");
    }
    
    
    public String getTelegramFormat() {
        return getString("telegram.telegram-format", "[MC] {player}: {message}");
    }
    
    public String getMinecraftFormat() {
        return getString("telegram.minecraft-format", "&6[TG] &f{username}: &7{message}");
    }
    
    public boolean isChatEnabled() {
        return getBoolean("chat.enabled", true);
    }
    
    public int getMaxMessageLength() {
        return getInt("chat.max-message-length", 256);
    }
    
    public String getCommandPrefix() {
        return getString("chat.command-prefix", "&8[&6TCB&8]&r");
    }
    
    public boolean isDebugMode() {
        return getBoolean("chat.debug", false);
    }
    
    public String getServerName() {
        return getString("server.name", "Minecraft Server");
    }
    
    public boolean areNotificationsEnabled() {
        return getBoolean("server.notifications.enabled", true);
    }
    
    public String getStartMessage() {
        return getString("server.notifications.start-message", "🟢 Server started!");
    }
    
    public String getStopMessage() {
        return getString("server.notifications.stop-message", "🔴 Server stopped!");
    }
}

