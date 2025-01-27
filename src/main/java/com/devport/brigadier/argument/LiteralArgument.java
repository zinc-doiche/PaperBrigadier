package com.zinc.kpoker.lib.brigadier.argument;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.zinc.kpoker.lib.brigadier.CommandBuilder;
import com.zinc.kpoker.lib.brigadier.command.BiCommand;
import com.zinc.kpoker.lib.brigadier.command.PlayerCommand;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LiteralArgument implements Argument<LiteralArgumentBuilder<CommandSourceStack>>, CommandBuilder {

    protected final String name;
    protected Predicate<CommandSourceStack> requires;
    protected com.mojang.brigadier.Command<CommandSourceStack> executes;
    protected final List<Argument<?>> arguments = new ArrayList<>();

    public LiteralArgument(String name) {
        this.name = name;
    }
    
    @Override
    public LiteralArgument requires(Predicate<CommandSourceStack> requires) {
        this.requires = requires;
        return this;
    }

    @Override
    public LiteralArgument executes(com.mojang.brigadier.Command<CommandSourceStack> executes) {
        this.executes = executes;
        return this;
    }

    @Override
    public <A> LiteralArgument then(RequiredArgument<A> argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public <A> LiteralArgument then(Supplier<RequiredArgumentBuilder<CommandSourceStack, A>> supplier) {
        RequiredArgumentBuilder<CommandSourceStack, A> builder = supplier.get();

        arguments.add(new RequiredArgument<A>(builder.getName()) {
            @Override
            protected RequiredArgumentBuilder<CommandSourceStack, A> argument() {
                return builder;
            }
        });

        return this;
    }

    @Override
    public LiteralArgument then(LiteralArgument argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public LiteralArgument requires(BiPredicate<CommandSourceStack, CommandSender> requires) {
        return requires(source -> requires.test(source, source.getBukkitSender()));
    }

    @Override
    public LiteralArgument requiresOp() {
        return requires(source -> source.getBukkitSender().isOp());
    }

    @Override
    public LiteralArgument requiresPermission(String permission) {
        return requires(source -> source.getBukkitSender().hasPermission(permission));
    }

    @Override
    public LiteralArgument requiresPlayer() {
        return requires(CommandSourceStack::isPlayer);
    }

    @Override
    public LiteralArgument requiresConsole() {
        return requires(source -> !source.isPlayer());
    }

    @Override
    public LiteralArgument executes(BiCommand executes) {
        return executes(context -> executes.run(context, context.getSource().getBukkitSender()));
    }

    @Override
    public LiteralArgument executesAsPlayer(PlayerCommand executes) {
        return executes(context -> executes.run(context, (Player) context.getSource().getBukkitSender()));
    }
    
    @Override
    public LiteralArgumentBuilder<CommandSourceStack> get() {
        LiteralArgumentBuilder<CommandSourceStack> literal = LiteralArgumentBuilder.literal(name);

        if(!arguments.isEmpty()) {
            for (Argument<?> argument : arguments) {
                literal = literal.then(argument.get());
            }
        }
        if (requires != null) {
            literal = literal.requires(requires);
        }
        if(executes != null) {
            literal = literal.executes(executes);
        }

        return literal;
    }

    @Override
    public Command build() {
        return CommandBuilder.command(get());
    }
}
