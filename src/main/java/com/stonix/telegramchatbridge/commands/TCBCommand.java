package com.stonix.telegramchatbridge.commands;

import com.stonix.telegramchatbridge.TelegramChatBridge;
import com.stonix.telegramchatbridge.utils.ConfigManager;
import com.stonix.telegramchatbridge.utils.MessageFormatter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TCBCommand implements CommandExecutor {
    
    private final TelegramChatBridge plugin;
    private final ConfigManager configManager;
    private final MessageFormatter messageFormatter;
    
    public TCBCommand(TelegramChatBridge plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.messageFormatter = plugin.getMessageFormatter();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("tcb.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }
        
        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                handleReload(sender);
                break;
                
            case "status":
                handleStatus(sender);
                break;
                
            case "test":
                handleTest(sender);
                break;
                
            case "help":
            default:
                sendHelpMessage(sender);
                break;
        }
        
        return true;
    }
    
    private void sendHelpMessage(CommandSender sender) {
        String prefix = messageFormatter.formatCommandMessage("");
        
        sender.sendMessage(ChatColor.GOLD + "=== Telegram Chat Bridge Commands ===");
        sender.sendMessage(ChatColor.YELLOW + "/tcb reload" + ChatColor.WHITE + " - Reload plugin configuration");
        sender.sendMessage(ChatColor.YELLOW + "/tcb status" + ChatColor.WHITE + " - Show plugin status");
        sender.sendMessage(ChatColor.YELLOW + "/tcb test" + ChatColor.WHITE + " - Send test message to Telegram");
        sender.sendMessage(ChatColor.YELLOW + "/tcb help" + ChatColor.WHITE + " - Show this help message");
    }
    
    private void handleReload(CommandSender sender) {
        try {
            plugin.reloadPlugin();
            sender.sendMessage(messageFormatter.formatCommandMessage(ChatColor.GREEN + "Plugin configuration reloaded successfully!"));
        } catch (Exception e) {
            sender.sendMessage(messageFormatter.formatCommandMessage(ChatColor.RED + "Failed to reload plugin: " + e.getMessage()));
            plugin.getLogger().severe("Failed to reload plugin: " + e.getMessage());
        }
    }
    
    private void handleStatus(CommandSender sender) {
        String prefix = messageFormatter.formatCommandMessage("");
        
        sender.sendMessage(ChatColor.GOLD + "=== Telegram Chat Bridge Status ===");
        sender.sendMessage(ChatColor.YELLOW + "Plugin Enabled: " + (plugin.isPluginEnabled() ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No"));
        sender.sendMessage(ChatColor.YELLOW + "Chat Forwarding: " + (configManager.isChatEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
        sender.sendMessage(ChatColor.YELLOW + "Telegram Bot: " + (plugin.getTelegramBot() != null && plugin.getTelegramBot().isBotRunning() ? ChatColor.GREEN + "Connected" : ChatColor.RED + "Disconnected"));
        sender.sendMessage(ChatColor.YELLOW + "Active Groups: " + ChatColor.WHITE + (plugin.getTelegramBot() != null ? plugin.getTelegramBot().getActiveChatCount() : 0));
        sender.sendMessage(ChatColor.YELLOW + "Server Name: " + ChatColor.WHITE + configManager.getServerName());
        sender.sendMessage(ChatColor.YELLOW + "Debug Mode: " + (configManager.isDebugMode() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
    }
    
    private void handleTest(CommandSender sender) {
        if (plugin.getTelegramBot() == null || !plugin.getTelegramBot().isBotRunning()) {
            sender.sendMessage(messageFormatter.formatCommandMessage(ChatColor.RED + "Telegram bot is not running!"));
            return;
        }
        
        String testMessage = "Test message from " + sender.getName() + " via Telegram Chat Bridge";
        plugin.getTelegramBot().sendMessage(testMessage);
        
        sender.sendMessage(messageFormatter.formatCommandMessage(ChatColor.GREEN + "Test message sent to Telegram!"));
    }
}

