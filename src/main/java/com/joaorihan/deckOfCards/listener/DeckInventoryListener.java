package com.joaorihan.deckOfCards.listener;

import java.util.List;

import com.joaorihan.deckOfCards.DeckOfCards;
import com.joaorihan.deckOfCards.DeckUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class DeckInventoryListener implements Listener {

    private final DeckOfCards plugin;

    public DeckInventoryListener(DeckOfCards plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack currentItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        if (currentItem == null) return;
        // Check if the clicked item is a deck.
        if (!DeckUtils.isDeck(currentItem, plugin.getDeckKey())) return;

        // If the player is holding a valid card on their cursor, add it to the deck.
        if (DeckUtils.isValidCard(cursorItem)) {
            String cardName = cursorItem.getItemMeta().getDisplayName();
            List<String> deckCards = DeckUtils.getDeckCards(currentItem, plugin.getDeckKey());
            deckCards.add(cardName);
            DeckUtils.setDeckCards(currentItem, plugin.getDeckKey(), deckCards);
            player.sendMessage(ChatColor.GREEN + "Card added to deck: " + cardName);
            // Remove the card from the cursor.
            event.setCursor(null);
            event.setCancelled(true);
        }
    }
}
