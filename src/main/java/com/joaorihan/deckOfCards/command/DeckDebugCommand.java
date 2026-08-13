package com.joaorihan.deckOfCards.command;

import com.joaorihan.deckOfCards.DeckOfCards;
import com.joaorihan.deckOfCards.DeckUtils;
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
            plugin.getMessageManager().send(sender, "command.player-only");
            return true;
        }
        if (!player.hasPermission("deckofcards.debug")) {
            plugin.getMessageManager().send(player, "debug.permission");
            return true;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!DeckUtils.isDeck(itemInHand, plugin.getDeckKey())) {
            plugin.getMessageManager().send(player, "debug.must-hold-deck");
            return true;
        }
        List<String> deckCards = DeckUtils.getDeckCards(itemInHand, plugin.getDeckKey());
        if (deckCards.isEmpty()) {
            plugin.getMessageManager().send(player, "debug.empty");
        } else {
            plugin.getMessageManager().send(player, "debug.order");
            for (int i = 0; i < deckCards.size(); i++) {
                plugin.getMessageManager().send(player, "debug.card-entry",
                        "position", i + 1,
                        "card", deckCards.get(i));
            }
        }
        return true;
    }
}
