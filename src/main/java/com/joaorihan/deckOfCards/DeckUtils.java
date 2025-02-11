package com.joaorihan.deckOfCards;

import com.joaorihan.deckOfCards.config.ConfigManager;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeckUtils {

    @Setter
    private static ConfigManager configManager;

    // A set of all valid card names. We'll update this from the configuration.
    private static final Set<String> validCards = new HashSet<>();


    /**
     * Populates the validCards set with all card names based on the current configuration.
     */
    public static void updateValidCards() {
        validCards.clear();
        validCards.addAll(getCards());
    }

    /**
     * Returns true if the given item is our deck (identified by its display name and persistent data).
     */
    public static boolean isDeck(ItemStack item, NamespacedKey deckKey) {
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return false;
        if (!meta.getDisplayName().equals(configManager.getDeckName())) return false;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(deckKey, PersistentDataType.STRING);
    }

    /**
     * Creates a new deck item (using the configured deck material) that contains the standard 52 cards.
     */
    public static ItemStack createNewDeck(NamespacedKey deckKey) {
        ItemStack deck = new ItemStack(configManager.getDeckMaterial());
        ItemMeta meta = deck.getItemMeta();
        meta.setDisplayName(configManager.getDeckName());
        List<String> cards = getCards();

        // Save the list as a semicolon-separated string.
        String cardData = String.join(";", cards);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(deckKey, PersistentDataType.STRING, cardData);

        // Set lore to show how many cards remain.
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Cards remaining: " + cards.size());
        meta.setLore(lore);
        deck.setItemMeta(meta);
        return deck;
    }

    /**
     * Returns a list of card names based on the configuration.
     */
    private static List<String> getCards() {
        List<String> cards = new ArrayList<>();
        // Order the cards by suit then rank.
        String[] suits = {
                configManager.getSpades(),
                configManager.getHearts(),
                configManager.getDiamonds(),
                configManager.getClubs()
        };
        String[] ranks = {
                configManager.getAce(), "2", "3", "4", "5", "6", "7", "8", "9", "10",
                configManager.getJack(), configManager.getQueen(), configManager.getKing()
        };
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(rank + " " + configManager.getOf() + " " + suit);
            }
        }
        return cards;
    }

    /**
     * Reads the deck’s card list from its persistent data.
     */
    public static List<String> getDeckCards(ItemStack deck, NamespacedKey deckKey) {
        List<String> cards = new ArrayList<>();
        if (deck == null || !deck.hasItemMeta()) return cards;

        ItemMeta meta = deck.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (!container.has(deckKey, PersistentDataType.STRING)) return cards;
        String cardData = container.get(deckKey, PersistentDataType.STRING);

        if (cardData == null || cardData.isEmpty()) return cards;
        cards.addAll(Arrays.asList(cardData.split(";")));
        return cards;
    }

    /**
     * Writes the card list back into the deck’s persistent data and updates its lore.
     */
    public static void setDeckCards(ItemStack deck, NamespacedKey deckKey, List<String> cards) {
        if (deck == null || !deck.hasItemMeta()) return;
        ItemMeta meta = deck.getItemMeta();
        String cardData = String.join(";", cards);
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(deckKey, PersistentDataType.STRING, cardData);
        // Update lore to show the remaining count.
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Cards remaining: " + cards.size());

        meta.setLore(lore);
        deck.setItemMeta(meta);
    }

    /**
     * Resets the deck to contain the full 52 cards in standard order.
     */
    public static void resetDeck(ItemStack deck, NamespacedKey deckKey) {
        if (deck == null || !deck.hasItemMeta()) return;
        ItemMeta meta = deck.getItemMeta();

        List<String> cards = getCards();

        String cardData = String.join(";", cards);
        meta.getPersistentDataContainer().set(deckKey, PersistentDataType.STRING, cardData);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Cards remaining: " + cards.size());
        meta.setLore(lore);
        deck.setItemMeta(meta);
    }

    /**
     * Checks if the given item is a valid card item.
     * It simply checks if the card's plain name is contained in the validCards set.
     */
    public static boolean isValidCard(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != configManager.getCardMaterial()) return false;
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return false;

        String name = meta.getDisplayName();
        // Remove any color codes before checking.
        String plainName = ChatColor.stripColor(name);
        return validCards.contains(plainName);
    }

}
