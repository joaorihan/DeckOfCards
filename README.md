# DeckOfCards

---

### About

**DeckOfCards** is a Paper plugin that introduces a physical, fully functional, customizable deck of 52 cards into Minecraft. Players can obtain, manage, shuffle, and debug decks directly in-game, making it perfect for integrating card games or interactive mechanics on your server.

### Purpose

DeckOfCards provides a flexible framework for card-based interactions on Paper servers. Whether you’re creating mini-games, role-playing scenarios, or gambling systems, this plugin allows you to easily incorporate card mechanics with fully configurable card names, deck items, and sound effects.

---

## Usage

`< >` - Mandatory arguments  
`( )` - Optional arguments

- **`/deck get`**  
  Gives the command sender a new, unshuffled deck of 52 cards.

- **`/deck give <player>`**  
  Gives a new deck of cards to the specified player.

- **`/deckreset`**  
  Resets the deck in the player’s main hand to a full, standard 52-card order.

- **`/deckdebug`**  
  Displays the current order of cards in the deck held by the player for debugging purposes.

---

## Permissions

- **deckofcards.use**  
  Allows players to use the `/deck` command.

- **deckofcards.get**  
  Allows players to obtain a deck using `/deck get`.

- **deckofcards.give**  
  Allows players to give a deck to another player with `/deck give <player>`.

- **deckofcards.reset**  
  Allows players to reset their held deck using `/deckreset`.

- **deckofcards.debug**  
  Allows players to view the debug information of their deck using `/deckdebug`.

---

### Requirements

- Paper 26.2 or newer
- Java 25 or newer

---

### Installation

1. **Download the latest release:**  
   Download the JAR file from the [Releases](https://github.com/joaorihan/DeckOfCards/releases) page.

2. **Place the JAR in your plugins folder:**  
   Simply drop the JAR file into your server’s `plugins` directory.

3. **Restart or reload your server:**  
   On first run, the plugin will generate a default configuration file (`config.yml`).

---

## Configuration

Upon first run, a default `config.yml` is generated in the plugins folder. Customize the following settings to tailor the plugin to your needs:

```yaml
# The material used for the deck, and card items. Change this to any valid Material name.
deck-material: BOOK
card-material: PAPER

# The sound played when dealing a card.
deal-sound: minecraft:item.book.page_turn

# The sound played when shuffling the deck.
shuffle-sound: minecraft:item.book.page_turn

# Sound volume and pitch.
sound-volume: 1.0
sound-pitch: 1.5

# Whether to send the player a message telling him what card he just dealt.
show-deal-message: false

# Displayed name of the deck
deck:
  name: "Deck of Cards"

# Displayed name of card ranks
cards:
  ace: "Ace"
  king: "King"
  queen: "Queen"
  jack: "Jack"
  of: "of" # The word between the rank and the suit. (can be blank)

# Displayed name of card suits
# Spades: ♠  Hearts: ♡  Diamonds: ♦  Clubs: ♧
suits:
  spades: "Spades"
  hearts: "Hearts"
  diamonds: "Diamonds"
  clubs: "Clubs"

```

- **deck-material:** Defines the item used as the deck (default is `BOOK`).
- **deal-sound & shuffle-sound:** The sounds played when dealing or shuffling cards.
- **sound-volume & sound-pitch:** Adjust the audio feedback.
- **show-deal-message:** If `true`, a message will display the dealt card to the player.
- **cards & suits:** Customize the card rank names and suit names used in your deck.

---

## Message formatting

User-facing messages are stored in `src/main/resources/messages/en.yml` and use MiniMessage formatting.

Examples:

```yaml
deck:
  shuffled: "<aqua>The deck has been shuffled!"
  dealt: "<yellow>You dealt: <card>"
```

Supported message placeholders include `<card>`, `<player>`, and `<position>`. Placeholder values are inserted as plain text, so formatting tags inside dynamic values are not interpreted. To display a literal angle bracket, escape it with a backslash, such as `\\<player>`.

Only text formatting is enabled for message catalogs; interactive tags such as click and hover actions are not processed.

---

## Troubleshooting

**Deck Commands Not Working?**

- Ensure you are holding the correct deck item in your main hand.
- Verify that your `config.yml` is correctly set up.
- Confirm that your server is running Paper 26.2 or newer.
- Double-check that your permissions are correctly assigned.

---

## Building

1. **Clone the repository:**
   ```bash
   git clone https://github.com/joaorihan/DeckOfCards.git && cd DeckOfCards
   ```

2. **Build the plugin:**
   ```bash
   ./gradlew clean build
   ```

3. **Deploy the JAR:**
   On Windows, run `gradlew.bat clean build` instead. Find the generated JAR in the `build/libs` directory and place it in your server's `plugins` folder.

---

## Contributing

Contributions are welcome! To contribute:

1. **Fork the Repository:**  
   Click the **Fork** button at the top right of this page.

2. **Clone Your Fork:**
   ```bash
   git clone https://github.com/your-username/DeckOfCards.git
   cd DeckOfCards
   ```

3. **Create a New Branch:**
   ```bash
   git checkout -b feature/your-feature-name
   ```

4. **Implement Your Changes:**  
   Follow the existing coding conventions and document your code.

5. **Commit and Push:**
   ```bash
   git commit -m "Add feature/bug fix description"
   git push origin feature/your-feature-name
   ```

6. **Open a Pull Request:**  
   Submit your pull request to the original repository with a detailed explanation of your changes.

Thank you for contributing!

---

## License

This project is licensed under the GNU Affero General Public License. See the [LICENSE](LICENSE) file for more details.

