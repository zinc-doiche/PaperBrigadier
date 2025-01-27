package com.devport.brigadier.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;

public interface BiCommand {
    int run(CommandContext<CommandSourceStack> context, CommandSender sender) throws CommandSyntaxException;
}
