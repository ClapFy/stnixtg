#!/bin/bash

# Build script for Telegram Chat Bridge Plugin
echo "Building Telegram Chat Bridge Plugin..."

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven first:"
    echo "1. Install Homebrew (if not already installed):"
    echo "   /bin/bash -c \"\$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)\""
    echo ""
    echo "2. Install Maven:"
    echo "   brew install maven"
    echo ""
    echo "3. Run this script again"
    exit 1
fi

# Clean and build the project
echo "Cleaning previous build..."
mvn clean

echo "Building project..."
mvn package

# Check if build was successful
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo "📦 Plugin JAR file created: target/telegram-chat-bridge-1.0.0.jar"
    echo ""
    echo "🚀 To deploy:"
    echo "1. Copy target/telegram-chat-bridge-1.0.0.jar to your server's plugins/ folder"
    echo "2. Start your server"
    echo "3. Configure the chat-id in plugins/TelegramChatBridge/config.yml"
    echo "4. Use /tcb reload to reload the configuration"
else
    echo "❌ Build failed! Please check the error messages above."
    exit 1
fi
