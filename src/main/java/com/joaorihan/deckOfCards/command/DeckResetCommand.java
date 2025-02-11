package com.joaorihan.deckOfCards.command;

import com.joaorihan.deckOfCards.DeckOfCards;
import com.joaorihan.deckOfCards.DeckUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DeckResetCommand implements CommandExecutor {

    private final DeckOfCards plugin;

    public DeckResetCommand(DeckOfCards plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }
        if (!player.hasPermission("deckofcards.reset")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to reset your deck.");
            return true;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!DeckUtils.isDeck(itemInHand, plugin.getDeckKey())) {
            player.sendMessage(ChatColor.RED + "You must be holding a deck to reset it.");
            return true;
        }
        DeckUtils.resetDeck(itemInHand, plugin.getDeckKey());
        player.sendMessage(ChatColor.GREEN + "Your deck has been reset to full 52 cards.");
        return true;
    }
}
