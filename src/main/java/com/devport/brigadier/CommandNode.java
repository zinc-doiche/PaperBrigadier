package com.zinc.kpoker.lib.brigadier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.zinc.kpoker.lib.brigadier.argument.LiteralArgument;
import com.zinc.kpoker.lib.brigadier.argument.RequiredArgument;
import com.zinc.kpoker.lib.brigadier.command.BiCommand;
import com.zinc.kpoker.lib.brigadier.command.PlayerCommand;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface CommandNode<T extends ArgumentBuilder<CommandSourceStack, T>> {

    CommandNode<T> requires(Predicate<CommandSourceStack> requires);

    CommandNode<T> executes(Command<CommandSourceStack> executes);

    <A> CommandNode<T> then(RequiredArgument<A> argument);

    CommandNode<T> then(LiteralArgument argument);

    <A> CommandNode<T> then(Supplier<RequiredArgumentBuilder<CommandSourceStack, A>> supplier);

    //utility methods

    CommandNode<T> requires(BiPredicate<CommandSourceStack, CommandSender> requires);
    CommandNode<T> requiresOp();
    CommandNode<T> requiresPermission(String permission);
    CommandNode<T> requiresPlayer();
    CommandNode<T> requiresConsole();

    CommandNode<T> executes(BiCommand executes);
    CommandNode<T> executesAsPlayer(PlayerCommand executes);

}
