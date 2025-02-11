package com.joaorihan.deckOfCards.command;

import com.joaorihan.deckOfCards.DeckOfCards;
import com.joaorihan.deckOfCards.DeckUtils;
import org.bukkit.ChatColor;
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
            sender.sendMessage(ChatColor.RED + "Usage: /deck <get|give> [player]");
            return true;
        }

        String subcommand = args[0].toLowerCase();

        // /deck get - Give a deck to the sender.
        if (subcommand.equals("get")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("deckofcards.get")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to get a deck.");
                return true;
            }
            ItemStack deck = DeckUtils.createNewDeck(plugin.getDeckKey());
            player.getInventory().addItem(deck);
            player.sendMessage(ChatColor.GREEN + "You have received a new deck of cards!");
            return true;
        }
        // /deck give <player> - Give a deck to another player.
        else if (subcommand.equals("give")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /deck give <player>");
                return true;
            }
            // Check permission if the sender is a player.
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("deckofcards.give")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to give decks.");
                    return true;
                }
            }
            Player target = plugin.getServer().getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found.");
                return true;
            }
            ItemStack deck = DeckUtils.createNewDeck(plugin.getDeckKey());
            target.getInventory().addItem(deck);
            target.sendMessage(ChatColor.GREEN + "You have received a new deck of cards!");
            sender.sendMessage(ChatColor.GREEN + "You have given a new deck of cards to " + target.getName() + "!");
            return true;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Usage: /deck <get|give> [player]");
            return true;
        }
    }
}
