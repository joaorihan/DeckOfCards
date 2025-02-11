package com.joaorihan.deckOfCards.config;

import com.joaorihan.deckOfCards.DeckOfCards;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;

public class ConfigManager {

    private final DeckOfCards plugin;
    @Getter private Material deckMaterial;
    @Getter private Material cardMaterial;
    @Getter private Sound dealSound;
    @Getter private Sound shuffleSound;
    @Getter private float soundVolume;
    @Getter private float soundPitch;
    @Getter private boolean showDealMessage;

    @Getter private String deckName;

    @Getter private String ace;
    @Getter private String king;
    @Getter private String queen;
    @Getter private String jack;
    @Getter private String of;

    @Getter private String spades;
    @Getter private String hearts;
    @Getter private String diamonds;
    @Getter private String clubs;

    public ConfigManager(DeckOfCards plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        // Save default config if none exists.
        plugin.saveDefaultConfig();

        // Load deck material.
        String deckMaterialName = plugin.getConfig().getString("deck-material", "BOOK").toUpperCase();
        String cardMaterialName = plugin.getConfig().getString("card-material", "PAPER").toUpperCase();

        deckMaterial = Material.matchMaterial(deckMaterialName);
        cardMaterial = Material.matchMaterial(cardMaterialName);

        if (deckMaterial == null) {
            plugin.getLogger().warning("Invalid deck material in config: " + deckMaterialName + ". Using default BOOK.");
            deckMaterial = Material.BOOK;
        }

        if (cardMaterial == null){
            plugin.getLogger().warning("Invalid card material in config: " + cardMaterialName + ". Using default PAPER.");
            cardMaterial = Material.PAPER;
        }
        // Load sound settings.
        String dealSoundName = plugin.getConfig().getString("deal-sound", "ITEM_BOOK_PAGE_TURN").toUpperCase();
        String shuffleSoundName = plugin.getConfig().getString("shuffle-sound", "ITEM_BOOK_PAGE_TURN").toUpperCase();
        try {
            dealSound = Sound.valueOf(dealSoundName);
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("Invalid deal-sound in config: " + dealSoundName + ". Using default ITEM_BOOK_PAGE_TURN.");
            dealSound = Sound.ITEM_BOOK_PAGE_TURN;
        }
        try {
            shuffleSound = Sound.valueOf(shuffleSoundName);
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("Invalid shuffle-sound in config: " + shuffleSoundName + ". Using default ITEM_BOOK_PAGE_TURN.");
            shuffleSound = Sound.ITEM_BOOK_PAGE_TURN;
        }
        soundVolume = (float) plugin.getConfig().getDouble("sound-volume", 1.0);
        soundPitch = (float) plugin.getConfig().getDouble("sound-pitch", 1.0);
        showDealMessage = plugin.getConfig().getBoolean("show-deal-message", false);

        deckName = plugin.getConfig().getString("deck.name", "Deck of Cards");

        ace = plugin.getConfig().getString("cards.ace", "Ace");
        king = plugin.getConfig().getString("cards.king", "King");
        queen = plugin.getConfig().getString("cards.queen", "Queen");
        jack = plugin.getConfig().getString("cards.jack", "Jack");
        of = plugin.getConfig().getString("cards.of", "of");

        spades = plugin.getConfig().getString("suits.spades", "Spades");
        hearts = plugin.getConfig().getString("suits.hearts", "Hearts");
        diamonds = plugin.getConfig().getString("suits.diamonds", "Diamonds");
        clubs = plugin.getConfig().getString("suits.clubs", "Clubs");

    }

}
