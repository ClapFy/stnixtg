package com.stonix.telegramchatbridge.listeners;

import com.stonix.telegramchatbridge.TelegramChatBridge;
import com.stonix.telegramchatbridge.utils.ConfigManager;
import com.stonix.telegramchatbridge.utils.MessageFormatter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {
    
    private final TelegramChatBridge plugin;
    private final ConfigManager configManager;
    private final MessageFormatter messageFormatter;
    
    public ChatListener(TelegramChatBridge plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.messageFormatter = plugin.getMessageFormatter();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Check if chat forwarding is enabled
        if (!configManager.isChatEnabled()) {
            return;
        }
        
        // Check if the plugin is properly enabled
        if (!plugin.isPluginEnabled()) {
            return;
        }
        
        // Check if player has permission to send messages to Telegram
        if (!event.getPlayer().hasPermission("tcb.chat")) {
            return;
        }
        
        String message = event.getMessage();
        
        // Validate the message
        if (!messageFormatter.isValidMessage(message)) {
            return;
        }
        
        // Clean the message
        String cleanedMessage = messageFormatter.cleanMessage(message);
        
        // Send to Telegram asynchronously
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.getTelegramBot().sendMinecraftMessage(event.getPlayer().getName(), cleanedMessage);
        });
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Optional: Send join notification to Telegram
        if (configManager.getBoolean("chat.join-notifications", false)) {
            String message = "🟢 " + event.getPlayer().getName() + " joined the server";
            plugin.getTelegramBot().sendMessage(message);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Optional: Send quit notification to Telegram
        if (configManager.getBoolean("chat.quit-notifications", false)) {
            String message = "🔴 " + event.getPlayer().getName() + " left the server";
            plugin.getTelegramBot().sendMessage(message);
        }
    }
}

