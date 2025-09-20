# Telegram Chat Bridge

A PaperMC plugin for Minecraft server version 1.21.8 that bridges chat between your Minecraft server and a Telegram group.

## Features

- **Bidirectional Chat**: Messages from Minecraft players are forwarded to Telegram and vice versa
- **Configurable Formats**: Customize how messages appear in both platforms
- **Admin Commands**: Manage the plugin with in-game commands
- **Server Notifications**: Optional start/stop notifications
- **Permission System**: Control who can send messages to Telegram
- **Message Filtering**: Automatically filters out commands and system messages

## Requirements

- **Minecraft Server**: PaperMC 1.21.8 or compatible
- **Java**: Version 21 or higher
- **Telegram Bot**: You need to create a Telegram bot and get its token

## Setup Instructions

### 1. Create a Telegram Bot

1. Open Telegram and search for `@BotFather`
2. Send `/newbot` command
3. Follow the instructions to create your bot
4. Save the bot token you receive
5. Add your bot to the Telegram group where you want messages to be forwarded

### 2. Add Bot to Groups

Simply add your bot to any Telegram group where you want messages to be forwarded. The bot will automatically detect and work with any group you add it to - no configuration needed!

### 3. Install the Plugin

1. Download the compiled JAR file
2. Place it in your server's `plugins` folder
3. Start your server
4. The plugin will create a `config.yml` file in the `plugins/TelegramChatBridge/` folder

### 4. Configure the Plugin

Your bot token is already configured in the plugin! No additional configuration needed - just add the bot to any group and it will work automatically.

The plugin will automatically:
- Detect any group you add the bot to
- Forward messages from those groups to Minecraft
- Send Minecraft messages to all active groups

### 5. Restart or Reload

Restart your server or use the reload command:
```
/tcb reload
```

## Configuration

The plugin comes with a comprehensive configuration file. Here are the main options:

### Telegram Settings
- `bot-token`: Your Telegram bot token
- `chat-id`: The Telegram group/chat ID
- `telegram-format`: Format for messages sent to Telegram
- `minecraft-format`: Format for messages sent to Minecraft

### Chat Settings
- `enabled`: Enable/disable message forwarding
- `max-message-length`: Maximum message length (0 = unlimited)
- `debug`: Enable debug mode for detailed logging

### Server Settings
- `name`: Server name displayed in Telegram messages
- `notifications`: Enable start/stop notifications

## Commands

### `/tcb` - Admin Commands
- `/tcb reload` - Reload plugin configuration
- `/tcb status` - Show plugin status
- `/tcb test` - Send test message to Telegram
- `/tcb help` - Show help message

## Permissions

- `tcb.admin` - Access to admin commands (default: OP)
- `tcb.chat` - Send messages to Telegram (default: true)

## Message Formatting

### Placeholders

**For messages sent to Telegram:**
- `{player}` - Player name
- `{message}` - The message content
- `{server}` - Server name

**For messages sent to Minecraft:**
- `{username}` - Telegram username
- `{message}` - The message content
- `{chat}` - Chat ID

### Example Formats

**Default Telegram format:**
```
[MC] PlayerName: Hello from Minecraft!
```

**Default Minecraft format:**
```
[TG] TelegramUser: Hello from Telegram!
```

## Building from Source

### Quick Build (Recommended)

1. Run the build script: `./build.sh`
2. The JAR file will be created in the `target/` folder

### Manual Build

If you want to build the plugin manually:

1. Make sure you have Maven installed
2. Run: `mvn clean package`
3. The JAR file will be in the `target/` folder

### Installing Maven (if needed)

On macOS:
```bash
# Install Homebrew (if not already installed)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Maven
brew install maven
```

## Troubleshooting

### Bot Not Responding
- Check if the bot token is correct
- Make sure the bot is added to the group
- Ensure the bot has permission to read and send messages in the group

### Messages Not Forwarding
- Check if chat forwarding is enabled in config
- Verify players have the `tcb.chat` permission
- Make sure the bot is added to at least one group
- Check server logs for error messages
- Enable debug mode for more detailed logging

### Plugin Not Loading
- Ensure you're using PaperMC 1.21.8 or compatible
- Check that Java 21+ is installed
- Verify the plugin.yml is correct

## Support

If you encounter any issues:

1. Check the server logs for error messages
2. Enable debug mode in the configuration
3. Verify your bot token and chat ID are correct
4. Make sure the bot has permission to send messages in the group

## License

This project is open source. Feel free to modify and distribute according to your needs.

## Contributing

Contributions are welcome! Please feel free to submit issues and pull requests.

