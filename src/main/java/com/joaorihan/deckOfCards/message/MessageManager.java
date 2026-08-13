package com.joaorihan.deckOfCards.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class MessageManager {

    private static final String DEFAULT_MESSAGES_RESOURCE = "messages/en.yml";
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage(MiniMessage.Preset.FORMATTED_TEXT);

    private final JavaPlugin plugin;
    private final FileConfiguration messages;

    public MessageManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messages = loadMessages();
    }

    public void send(CommandSender sender, String key, Object... placeholders) {
        sender.sendMessage(get(key, placeholders));
    }

    public Component get(String key, Object... placeholders) {
        String message = messages.getString(key);
        if (message == null) {
            plugin.getLogger().warning("Missing message key: " + key);
            return Component.text(key);
        }

        if (placeholders.length % 2 != 0) {
            throw new IllegalArgumentException("Message placeholders must be provided as key/value pairs");
        }

        TagResolver.Builder placeholderResolvers = TagResolver.builder();
        for (int i = 0; i < placeholders.length; i += 2) {
            String placeholder = String.valueOf(placeholders[i]);
            String value = String.valueOf(placeholders[i + 1]);
            placeholderResolvers.resolver(Placeholder.unparsed(placeholder, value));
        }

        return MINI_MESSAGE.deserialize(message, placeholderResolvers.build());
    }

    private FileConfiguration loadMessages() {
        InputStream resource = plugin.getResource(DEFAULT_MESSAGES_RESOURCE);
        if (resource == null) {
            plugin.getLogger().severe("Unable to find " + DEFAULT_MESSAGES_RESOURCE + " in the plugin resources");
            return new YamlConfiguration();
        }

        try (InputStream inputStream = resource;
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return YamlConfiguration.loadConfiguration(reader);
        } catch (IOException exception) {
            plugin.getLogger().log(java.util.logging.Level.SEVERE,
                    "Unable to load " + DEFAULT_MESSAGES_RESOURCE,
                    exception);
            return new YamlConfiguration();
        }
    }
}
