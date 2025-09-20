# 🔧 Troubleshooting Guide for readyplugin.jar

## ✅ Plugin Fixed and Ready!

Your plugin `readyplugin.jar` has been fixed and is now ready for deployment!

## 🚀 What Was Fixed:

1. **Bot Registration**: Fixed Telegram bot registration with proper API initialization
2. **Error Handling**: Added comprehensive error handling and logging
3. **Debug Mode**: Enabled debug mode by default for better troubleshooting
4. **PaperMC Compatibility**: Ensured compatibility with PaperMC 1.21.8
5. **Multi-Group Support**: Bot now works with ANY group you add it to

## 📦 Deployment Instructions:

1. **Copy the Plugin**:
   ```bash
   cp readyplugin.jar /path/to/your/papermc/server/plugins/
   ```

2. **Start Your Server**:
   - Start your PaperMC 1.21.8 server
   - Check the logs for plugin startup messages

3. **Add Bot to Groups**:
   - Add your bot to any Telegram group where you want messages
   - The bot will automatically detect and start working

## 🔍 Troubleshooting Steps:

### 1. Check Server Logs

Look for these messages in your server logs:

**✅ Success Messages:**
```
[INFO] Telegram bot started successfully!
[INFO] Bot username: YourBotName
[INFO] Add the bot to any group to start receiving messages!
[INFO] Bot token: 8373530125...
```

**❌ Error Messages:**
```
[SEVERE] Failed to start Telegram bot
[SEVERE] Failed to register Telegram bot
```

### 2. Bot Not Responding

**Check these:**
- ✅ Bot token is correct: `8373530125:AAEYBMuf2nPgvMQ5sVb0WiXpFptEiOlVQFw`
- ✅ Bot is added to at least one group
- ✅ Bot has permission to read and send messages in the group
- ✅ Server has internet access
- ✅ No firewall blocking outbound connections

**Test Bot Token:**
Visit: `https://api.telegram.org/bot8373530125:AAEYBMuf2nPgvMQ5sVb0WiXpFptEiOlVQFw/getMe`

You should see bot information if the token is valid.

### 3. Messages Not Forwarding

**Check these:**
- ✅ Use `/tcb status` to check plugin status
- ✅ Make sure debug mode is enabled (it is by default)
- ✅ Check that players have `tcb.chat` permission
- ✅ Verify the bot is added to at least one group

### 4. PaperMC 1.21.8 Compatibility

**Requirements:**
- ✅ Java 21+ installed
- ✅ PaperMC 1.21.8 server
- ✅ Plugin API version 1.21

## 🎮 Commands Available:

- `/tcb status` - Check plugin status and active groups
- `/tcb test` - Send test message to all connected groups
- `/tcb reload` - Reload plugin configuration
- `/tcb help` - Show all commands

## 📊 Debug Information:

With debug mode enabled, you'll see detailed logs:

```
[INFO] Received message from chat ID: -123456789
[INFO] Chat type: supergroup
[INFO] Added new group to active chats: -123456789 (My Group Name)
[INFO] Telegram message forwarded to Minecraft: [TG] Username: Hello!
```

## 🔄 How It Works:

1. **Minecraft → Telegram**: All player messages are sent to ALL active groups
2. **Telegram → Minecraft**: Messages from ANY group are forwarded to Minecraft
3. **Auto-Detection**: Bot automatically detects new groups when added
4. **Multi-Group**: Supports unlimited number of groups

## 🆘 Still Not Working?

If the plugin still doesn't work:

1. **Check Server Version**: Ensure you're using PaperMC 1.21.8
2. **Check Java Version**: Must be Java 21 or higher
3. **Check Bot Token**: Verify the token is correct and the bot exists
4. **Check Permissions**: Ensure bot has proper permissions in groups
5. **Check Logs**: Look for any error messages in server logs

## 📞 Support:

The plugin includes comprehensive logging. Check your server logs for detailed error messages and debug information.

**Your bot token is pre-configured**: `8373530125:AAEYBMuf2nPgvMQ5sVb0WiXpFptEiOlVQFw`

Just deploy and add the bot to any group! 🎉
