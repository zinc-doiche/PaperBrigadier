package com.devport.brigadier.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface PlayerSuggestionProvider {
    CompletableFuture<Suggestions> getSuggestions(Player player, SuggestionsBuilder builder) throws CommandSyntaxException;
}
