package com.devport.brigadier.argument;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import org.bukkit.entity.Player;

public class PlayerArgument extends RequiredArgument<EntitySelector> {

    public PlayerArgument(String name) {
        super(name);
    }

    public static Player getPlayer(CommandSourceStack source, EntitySelector selector) throws CommandSyntaxException {
        return selector.findSinglePlayer(source).getBukkitEntity();
    }

    public static Player getPlayer(CommandContext<CommandSourceStack> context, String argument) throws CommandSyntaxException {
        return getPlayer(context.getSource(), context.getArgument(argument, EntitySelector.class));
    }

    @Override
    public RequiredArgumentBuilder<CommandSourceStack, EntitySelector> argument() {
        return RequiredArgumentBuilder.argument(name, EntityArgument.player());
    }
}
