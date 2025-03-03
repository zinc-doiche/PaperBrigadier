package com.devport.brigadier.argument;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.devport.brigadier.CommandNode;
import net.minecraft.commands.CommandSourceStack;

public interface Argument<T extends ArgumentBuilder<CommandSourceStack, T>> extends CommandNode<T> {

    ArgumentBuilder<CommandSourceStack, T> get();
}
