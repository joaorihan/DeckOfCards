package com.joaorihan.deckOfCards.listener;

import java.util.Collections;
import java.util.List;

import com.joaorihan.deckOfCards.DeckOfCards;
import com.joaorihan.deckOfCards.DeckUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DeckInteractListener implements Listener {

    private final DeckOfCards plugin;

    public DeckInteractListener(DeckOfCards plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Only process main-hand interactions.
        if (event.getHand() != EquipmentSlot.HAND) return;
        ItemStack item = event.getItem();
        if (item == null) return;
        // Check if the held item is our deck.
        if (!DeckUtils.isDeck(item, plugin.getDeckKey())) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true); // Prevent normal right-click behavior.
            List<String> deckCards = DeckUtils.getDeckCards(item, plugin.getDeckKey());

            if (player.isSneaking()) {
                // Prevent shuffling if the deck is empty.
                if (deckCards.isEmpty()) {
                    player.sendMessage(ChatColor.RED + "The deck is empty; cannot shuffle.");
                    return;
                }
                // Shuffle the deck.
                Collections.shuffle(deckCards);
                DeckUtils.setDeckCards(item, plugin.getDeckKey(), deckCards);
                player.sendMessage(ChatColor.AQUA + "The deck has been shuffled!");
                playSoundNearby(player, plugin.getConfigManager().getShuffleSound());
            } else {
                // Deal (remove) a card from the deck.
                if (deckCards.isEmpty()) {
                    player.sendMessage(ChatColor.RED + "The deck is empty!");
                    return;
                }
                String card = deckCards.removeFirst(); // Remove the top card.
                DeckUtils.setDeckCards(item, plugin.getDeckKey(), deckCards);

                // Create an item representing the dealt card.
                ItemStack cardItem = new ItemStack(plugin.getConfigManager().getCardMaterial());
                ItemMeta meta = cardItem.getItemMeta();
                meta.setDisplayName(card);
                cardItem.setItemMeta(meta);

                // Throw the card in the direction the player is facing.
                Location eyeLocation = player.getEyeLocation();
                Item droppedItem = player.getWorld().dropItem(eyeLocation, cardItem);
                droppedItem.setVelocity(eyeLocation.getDirection().multiply(0.5));

                // Play deal sound for nearby players.
                playSoundNearby(player, plugin.getConfigManager().getDealSound());

                // Send deal message only if enabled in config.
                if (plugin.getConfigManager().isShowDealMessage()) {
                    player.sendMessage(ChatColor.YELLOW + "You dealt: " + card);
                }
            }
        }
    }

    private void playSoundNearby(Player player, Sound sound) {
        Location loc = player.getLocation();
        // Play the sound for every player within 5 blocks.
        for (Player p : player.getWorld().getPlayers()) {
            if (p.getLocation().distance(loc) <= 5) {
                p.playSound(loc, sound, plugin.getConfigManager().getSoundVolume(), plugin.getConfigManager().getSoundPitch());
            }
        }
    }
}
