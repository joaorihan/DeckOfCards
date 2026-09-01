package com.joaorihan.deckOfCards.command;

import com.joaorihan.deckOfCards.DeckOfCards;
import com.joaorihan.deckOfCards.DeckUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DeckCommand implements CommandExecutor {

    private final DeckOfCards plugin;

    public DeckCommand(DeckOfCards plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Require at least one argument.
        if (args.length == 0) {
            plugin.getMessageManager().send(sender, "command.deck.usage");
            return true;
        }

        String subcommand = args[0].toLowerCase();

        // /deck get - Give a deck to the sender.
        if (subcommand.equals("get")) {
            if (!(sender instanceof Player)) {
                plugin.getMessageManager().send(sender, "command.player-only");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("deckofcards.get")) {
                plugin.getMessageManager().send(player, "command.deck.permission-get");
                return true;
            }
            ItemStack deck = DeckUtils.createNewDeck(plugin.getDeckKey());
            player.getInventory().addItem(deck);
            plugin.getMessageManager().send(player, "command.deck.received");
            return true;
        }
        // /deck give <player> - Give a deck to another player.
        else if (subcommand.equals("give")) {
            if (args.length < 2) {
                plugin.getMessageManager().send(sender, "command.deck.give-usage");
                return true;
            }
            // Check permission if the sender is a player.
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("deckofcards.give")) {
                    plugin.getMessageManager().send(player, "command.deck.permission-give");
                    return true;
                }
            }
            Player target = plugin.getServer().getPlayerExact(args[1]);
            if (target == null) {
                plugin.getMessageManager().send(sender, "command.player-not-found", "player", args[1]);
                return true;
            }
            ItemStack deck = DeckUtils.createNewDeck(plugin.getDeckKey());
            target.getInventory().addItem(deck);
            plugin.getMessageManager().send(target, "command.deck.received");
            plugin.getMessageManager().send(sender, "command.deck.given", "player", target.getName());
            return true;
        }
        else {
            plugin.getMessageManager().send(sender, "command.deck.usage");
            return true;
        }
    }
}
