package com.stonix.telegramchatbridge.telegram;

import com.stonix.telegramchatbridge.TelegramChatBridge;
import com.stonix.telegramchatbridge.utils.ConfigManager;
import com.stonix.telegramchatbridge.utils.MessageFormatter;
import org.bukkit.Bukkit;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.Level;
import java.util.Set;
import java.util.HashSet;

public class TelegramBot extends TelegramLongPollingBot {
    
    private final TelegramChatBridge plugin;
    private final String botToken;
    private final ConfigManager configManager;
    private final MessageFormatter messageFormatter;
    private final Set<Long> activeChatIds = new HashSet<>();
    private final TelegramBotsApi botsApi;
    private boolean isRunning = false;
    
    public TelegramBot(TelegramChatBridge plugin, String botToken) throws Exception {
        super(botToken);
        this.plugin = plugin;
        this.botToken = botToken;
        this.configManager = plugin.getConfigManager();
        this.messageFormatter = plugin.getMessageFormatter();
        this.botsApi = new TelegramBotsApi(DefaultBotSession.class);
    }
    
    public void start() throws TelegramApiException {
        if (!isRunning) {
            try {
                botsApi.registerBot(this);
                isRunning = true;
                plugin.getLogger().info("Telegram bot started successfully!");
                plugin.getLogger().info("Bot username: " + getBotUsername());
                plugin.getLogger().info("Add the bot to any group to start receiving messages!");
            } catch (TelegramApiException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to register Telegram bot", e);
                throw e;
            }
        }
    }
    
    public void stop() {
        if (isRunning) {
            isRunning = false;
            plugin.getLogger().info("Telegram bot stopped.");
        }
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        try {
            // Check if the update contains a message
            if (update.hasMessage() && update.getMessage().hasText()) {
                var message = update.getMessage();
                long chatId = message.getChatId();
                
                if (configManager.isDebugMode()) {
                    plugin.getLogger().info("Received message from chat ID: " + chatId);
                    plugin.getLogger().info("Chat type: " + message.getChat().getType());
                }
                
                // Add this chat to active chats if it's a group/supergroup
                if (message.getChat().isGroupChat() || message.getChat().isSuperGroupChat()) {
                    if (!activeChatIds.contains(chatId)) {
                        activeChatIds.add(chatId);
                        plugin.getLogger().info("Added new group to active chats: " + chatId + " (" + message.getChat().getTitle() + ")");
                    }
                }
                
                // Only process messages from groups (not private chats)
                if (!message.getChat().isGroupChat() && !message.getChat().isSuperGroupChat()) {
                    if (configManager.isDebugMode()) {
                        plugin.getLogger().info("Ignoring private message from chat ID: " + chatId);
                    }
                    return;
                }
                
                // Don't process bot messages
                if (message.getFrom().getIsBot()) {
                    if (configManager.isDebugMode()) {
                        plugin.getLogger().info("Ignoring bot message from: " + message.getFrom().getUserName());
                    }
                    return;
                }
                
                String text = message.getText();
                String username = message.getFrom().getUserName();
                
                if (username == null || username.isEmpty()) {
                    username = message.getFrom().getFirstName();
                    if (username == null || username.isEmpty()) {
                        username = "Unknown";
                    }
                }
                
                // Don't process command messages
                if (text.startsWith("/")) {
                    if (configManager.isDebugMode()) {
                        plugin.getLogger().info("Ignoring command message: " + text);
                    }
                    return;
                }
                
                // Format and send to Minecraft
                String formattedMessage = messageFormatter.formatTelegramToMinecraft(username, text);
                
                // Send to Minecraft chat asynchronously
                Bukkit.getScheduler().runTask(plugin, () -> {
                    Bukkit.broadcastMessage(formattedMessage);
                    
                    if (configManager.isDebugMode()) {
                        plugin.getLogger().info("Telegram message forwarded to Minecraft: " + formattedMessage);
                    }
                });
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error processing Telegram update", e);
        }
    }
    
    public void sendMessage(String message) {
        if (!isRunning || message == null || message.trim().isEmpty()) {
            return;
        }
        
        // Send message to all active group chats
        for (Long chatId : activeChatIds) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId.toString());
            sendMessage.setText(message);
            
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to send message to Telegram chat " + chatId, e);
            }
        }
    }
    
    public void sendMinecraftMessage(String playerName, String message) {
        if (!configManager.isChatEnabled()) {
            return;
        }
        
        String formattedMessage = messageFormatter.formatMinecraftToTelegram(playerName, message);
        sendMessage(formattedMessage);
        
        if (configManager.isDebugMode()) {
            plugin.getLogger().info("Minecraft message forwarded to Telegram: " + formattedMessage);
        }
    }
    
    @Override
    public String getBotUsername() {
        try {
            // Get bot info from Telegram API
            var me = this.getMe();
            if (me != null) {
                return me.getUserName();
            }
        } catch (TelegramApiException e) {
            plugin.getLogger().log(Level.WARNING, "Could not get bot username", e);
        }
        return "TelegramChatBridge";
    }
    
    public boolean isBotRunning() {
        return isRunning;
    }
    
    public Set<Long> getActiveChatIds() {
        return new HashSet<>(activeChatIds);
    }
    
    public int getActiveChatCount() {
        return activeChatIds.size();
    }
}

