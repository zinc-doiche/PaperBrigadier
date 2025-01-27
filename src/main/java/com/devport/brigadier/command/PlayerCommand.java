package com.devport.brigadier.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface PlayerCommand {
    int run(CommandContext<CommandSourceStack> context, Player player) throws CommandSyntaxException;
}
