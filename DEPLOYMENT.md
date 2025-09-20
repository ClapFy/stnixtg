# Quick Deployment Guide

## Your Bot Token is Ready! 🎉

Your Telegram bot token `8373530125:AAEYBMuf2nPgvMQ5sVb0WiXpFptEiOlVQFw` is already configured in the plugin.

## Steps to Deploy

### 1. Build the Plugin

Run the build script:
```bash
./build.sh
```

This will create `target/telegram-chat-bridge-1.0.0.jar`

### 2. Deploy to Server

1. Copy `target/telegram-chat-bridge-1.0.0.jar` to your Minecraft server's `plugins/` folder
2. Start your server
3. The plugin will create a config file at `plugins/TelegramChatBridge/config.yml`

### 3. Add Bot to Groups

Simply add your bot to any Telegram group where you want messages to be forwarded. The bot will automatically work with any group you add it to!

No configuration needed - the bot will:
- Automatically detect any group you add it to
- Forward messages from those groups to Minecraft
- Send Minecraft messages to all active groups

### 4. Test the Setup

1. Use `/tcb reload` to reload the configuration
2. Use `/tcb test` to send a test message to Telegram
3. Send a message in your Minecraft server chat to test forwarding to Telegram
4. Send a message in your Telegram group to test forwarding to Minecraft

## Troubleshooting

- If the bot doesn't respond, make sure it's added to the group and has permission to send messages
- Check that the bot is added to at least one group
- Check server logs for any error messages
- Use `/tcb status` to check plugin status and see how many active groups you have
- Enable debug mode in config.yml for detailed logging

## Commands

- `/tcb status` - Check plugin status
- `/tcb test` - Send test message to Telegram
- `/tcb reload` - Reload configuration
- `/tcb help` - Show all commands

That's it! Your Telegram Chat Bridge should be working! 🚀
