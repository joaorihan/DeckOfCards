package com.joaorihan.deckOfCards.command;

import com.joaorihan.deckOfCards.DeckOfCards;
import com.joaorihan.deckOfCards.DeckUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DeckDebugCommand implements CommandExecutor {

    private final DeckOfCards plugin;

    public DeckDebugCommand(DeckOfCards plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }
        if (!player.hasPermission("deckofcards.debug")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to debug your deck.");
            return true;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!DeckUtils.isDeck(itemInHand, plugin.getDeckKey())) {
            player.sendMessage(ChatColor.RED + "You must be holding a deck to debug it.");
            return true;
        }
        List<String> deckCards = DeckUtils.getDeckCards(itemInHand, plugin.getDeckKey());
        if (deckCards.isEmpty()) {
            player.sendMessage(ChatColor.YELLOW + "The deck is empty.");
        } else {
            player.sendMessage(ChatColor.AQUA + "Current deck order:");
            for (int i = 0; i < deckCards.size(); i++) {
                player.sendMessage(ChatColor.GRAY + "" + (i + 1) + ". " + deckCards.get(i));
            }
        }
        return true;
    }
}
