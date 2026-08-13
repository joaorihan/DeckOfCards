package com.joaorihan.deckOfCards.command;

import com.joaorihan.deckOfCards.DeckOfCards;
import com.joaorihan.deckOfCards.DeckUtils;
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
            plugin.getMessageManager().send(sender, "command.player-only");
            return true;
        }
        if (!player.hasPermission("deckofcards.reset")) {
            plugin.getMessageManager().send(player, "reset.permission");
            return true;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!DeckUtils.isDeck(itemInHand, plugin.getDeckKey())) {
            plugin.getMessageManager().send(player, "reset.must-hold-deck");
            return true;
        }
        DeckUtils.resetDeck(itemInHand, plugin.getDeckKey());
        plugin.getMessageManager().send(player, "reset.success");
        return true;
    }
}
