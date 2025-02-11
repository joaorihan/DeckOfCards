package com.joaorihan.deckOfCards.command.tab;

import com.joaorihan.deckOfCards.DeckOfCards;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeckTabCompleter implements TabCompleter {

    private final DeckOfCards plugin;

    public DeckTabCompleter(DeckOfCards plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        // When typing the first argument, suggest "get" and "give".
        if (args.length == 1) {
            List<String> subcommands = List.of("get", "give");
            String partial = args[0].toLowerCase();
            completions = subcommands.stream()
                    .filter(sub -> sub.startsWith(partial))
                    .collect(Collectors.toList());
        }
        // When typing the second argument for "/deck give <player>".
        else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            String partial = args[1].toLowerCase();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(partial)) {
                    completions.add(player.getName());
                }
            }
        }

        return completions;
    }
}
