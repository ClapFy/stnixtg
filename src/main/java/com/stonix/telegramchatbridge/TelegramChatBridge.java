package com.stonix.telegramchatbridge;

import com.stonix.telegramchatbridge.commands.TCBCommand;
import com.stonix.telegramchatbridge.listeners.ChatListener;
import com.stonix.telegramchatbridge.telegram.TelegramBot;
import com.stonix.telegramchatbridge.utils.ConfigManager;
import com.stonix.telegramchatbridge.utils.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class TelegramChatBridge extends JavaPlugin {
    
    private static TelegramChatBridge instance;
    private ConfigManager configManager;
    private TelegramBot telegramBot;
    private MessageFormatter messageFormatter;
    private boolean isEnabled = false;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        messageFormatter = new MessageFormatter(this);
        if (!initializeTelegramBot()) {
            getLogger().severe("Failed to initialize Telegram bot. Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getCommand("tcb").setExecutor(new TCBCommand(this));
        if (configManager.getBoolean("server.notifications.enabled", true)) {
            String startMessage = configManager.getString("server.notifications.start-message", "🟢 Server started!");
            telegramBot.sendMessage(startMessage);
        }
        
        isEnabled = true;
        getLogger().info("Telegram Chat Bridge has been enabled successfully!");
    }

    @Override
    public void onDisable() {
        if (isEnabled) {
            if (configManager != null && configManager.getBoolean("server.notifications.enabled", true)) {
                String stopMessage = configManager.getString("server.notifications.stop-message", "🔴 Server stopped!");
                if (telegramBot != null) {
                    telegramBot.sendMessage(stopMessage);
                }
            }
            if (telegramBot != null) {
                telegramBot.stop();
            }
            
            getLogger().info("Telegram Chat Bridge has been disabled.");
        }
    }
    
    private boolean initializeTelegramBot() {
        String botToken = configManager.getString("telegram.bot-token", "");
        
        if (botToken.isEmpty() || botToken.equals("YOUR_BOT_TOKEN_HERE")) {
            getLogger().severe("Telegram bot token is not configured!");
            getLogger().info("Please set your bot token in config.yml");
            return false;
        }
        
        try {
            telegramBot = new TelegramBot(this, botToken);
            telegramBot.start();
            getLogger().info("Telegram bot started! Add it to any group to start receiving messages.");
            getLogger().info("Bot token: " + botToken.substring(0, 10) + "...");
            return true;
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to start Telegram bot", e);
            getLogger().severe("Please check your bot token and ensure the bot is valid.");
            return false;
        }
    }
    
    public void reloadPlugin() {
        reloadConfig();
        configManager.reload();
        
        if (telegramBot != null) {
            telegramBot.stop();
        }
        
        if (!initializeTelegramBot()) {
            getLogger().severe("Failed to reload Telegram bot configuration!");
            return;
        }
        
        getLogger().info("Plugin configuration reloaded successfully!");
    }
    
    public boolean isPluginEnabled() {
        return isEnabled;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public TelegramBot getTelegramBot() {
        return telegramBot;
    }
    
    public MessageFormatter getMessageFormatter() {
        return messageFormatter;
    }
    
    public static TelegramChatBridge getInstance() {
        return instance;
    }
}

