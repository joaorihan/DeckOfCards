package com.joaorihan.deckOfCards;

import com.joaorihan.deckOfCards.command.DeckCommand;
import com.joaorihan.deckOfCards.command.DeckDebugCommand;
import com.joaorihan.deckOfCards.command.DeckResetCommand;
import com.joaorihan.deckOfCards.command.tab.DeckTabCompleter;
import com.joaorihan.deckOfCards.config.ConfigManager;
import com.joaorihan.deckOfCards.listener.DeckInteractListener;
import com.joaorihan.deckOfCards.listener.DeckInventoryListener;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class DeckOfCards extends JavaPlugin {

    private NamespacedKey deckKey;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Initialize the configuration manager.
        configManager = new ConfigManager(this);
        DeckUtils.setConfigManager(configManager);
        DeckUtils.updateValidCards();

        // Create a namespaced key for storing deck data.
        deckKey = new NamespacedKey(this, "deck_cards");

        // Register event listeners.
        getServer().getPluginManager().registerEvents(new DeckInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new DeckInventoryListener(this), this);

        // Register commands.
        getCommand("deck").setExecutor(new DeckCommand(this));
        getCommand("deck").setTabCompleter(new DeckTabCompleter(this));

        getCommand("deckreset").setExecutor(new DeckResetCommand(this));
        getCommand("deckdebug").setExecutor(new DeckDebugCommand(this));
    }

}
