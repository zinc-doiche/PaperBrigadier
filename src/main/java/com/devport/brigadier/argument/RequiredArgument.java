package com.zinc.kpoker.lib.brigadier.argument;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.zinc.kpoker.lib.brigadier.command.BiCommand;
import com.zinc.kpoker.lib.brigadier.command.PlayerCommand;
import com.zinc.kpoker.lib.brigadier.command.PlayerSuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class RequiredArgument<T> implements Argument<RequiredArgumentBuilder<CommandSourceStack, T>> {
    protected final String name;
    protected Predicate<CommandSourceStack> requires;
    protected Command<CommandSourceStack> executes;
    protected final List<Argument<?>> arguments = new ArrayList<>();
    protected SuggestionProvider<CommandSourceStack> suggests;

    public RequiredArgument(String name) {
        this.name = name;
    }

    protected abstract RequiredArgumentBuilder<CommandSourceStack, T> argument();

    @Override
    public ArgumentBuilder<CommandSourceStack, RequiredArgumentBuilder<CommandSourceStack, T>> get() {
        RequiredArgumentBuilder<CommandSourceStack, T> required = argument();

        if(!arguments.isEmpty()) {
            for (Argument<?> child : arguments) {
                required = required.then(child.get());
            }
        }
        if (requires != null) {
            required = required.requires(requires);
        }
        if(suggests != null) {
            required = required.suggests(suggests);
        }
        if(executes != null) {
            required = required.executes(executes);
        }

        return required;
    }

    @Override
    public RequiredArgument<T> requires(Predicate<CommandSourceStack> requires) {
        this.requires = requires;
        return this;
    }

    @Override
    public RequiredArgument<T> executes(Command<CommandSourceStack> executes) {
        this.executes = executes;
        return this;
    }

    @Override
    public <A> RequiredArgument<T> then(RequiredArgument<A> argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public RequiredArgument<T> then(LiteralArgument argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public <A> RequiredArgument<T> then(Supplier<RequiredArgumentBuilder<CommandSourceStack, A>> supplier) {
        RequiredArgumentBuilder<CommandSourceStack, A> builder = supplier.get();

        arguments.add(new RequiredArgument<A>(builder.getName()) {
            @Override
            protected RequiredArgumentBuilder<CommandSourceStack, A> argument() {
                return builder;
            }
        });

        return this;
    }

    public RequiredArgument<T> suggests(SuggestionProvider<CommandSourceStack> suggests) {
        this.suggests = suggests;
        return this;
    }

    public RequiredArgument<T> suggest(String argument) {
        return suggests((context, builder) -> builder
                .suggest(argument)
                .buildFuture());
    }

    @Override
    public RequiredArgument<T> requires(BiPredicate<CommandSourceStack, CommandSender> requires) {
        return requires(source -> requires.test(source, source.getBukkitSender()));
    }

    @Override
    public RequiredArgument<T> requiresOp() {
        return requires(source -> source.getBukkitSender().isOp());
    }

    @Override
    public RequiredArgument<T> requiresPermission(String permission) {
        return requires(source -> source.getBukkitSender().hasPermission(permission));
    }

    @Override
    public RequiredArgument<T> requiresPlayer() {
        return requires(CommandSourceStack::isPlayer);
    }

    @Override
    public RequiredArgument<T> requiresConsole() {
        return requires(source -> !source.isPlayer());
    }

    @Override
    public RequiredArgument<T> executes(BiCommand executes) {
        return executes(context -> executes.run(context, context.getSource().getBukkitSender()));
    }

    @Override
    public RequiredArgument<T> executesAsPlayer(PlayerCommand executes) {
        return executes(context -> executes.run(context, (Player) context.getSource().getBukkitSender()));
    }

    public RequiredArgument<T> suggestsWithPlayer(PlayerSuggestionProvider provider) {
        return suggests((context, builder) ->
                provider.getSuggestions((Player) context.getSource().getBukkitSender(), builder));
    }

    public RequiredArgument<T> suggestArguments(String... arguments) {
        return suggests((context, builder) -> {
            for (String argument : arguments) {
                builder.suggest(argument);
            }
            return builder.buildFuture();
        });
    }

    /**
     * 단순 {@link Collection}을 사용하면 초기 등록 이후의 {@link Collection}의 요소 변경을 반영하지 못하고
     * <p>처음 등록된 요소만 제안해주므로 {@link Supplier} 사용
     * @param arguments 제안할 요소의 {@link Supplier}
     * @return {@link RequiredArgument}
     */
    public RequiredArgument<T> suggestArguments(Supplier<Collection<String>> arguments) {
        return suggests((context, builder) -> {
            Collection<String> strings = arguments.get();
            for (String argument : strings) {
                builder.suggest(argument);
            }
            return builder.buildFuture();
        });
    }

    /**
     * @param enums adjust with {@code Enum.values()}
     */
    public RequiredArgument<T> suggestEnums(Enum<?>[] enums) {
        return suggests((context, builder) -> {
            for (Enum<?> argument : enums) {
                builder.suggest(argument.name());
            }
            return builder.buildFuture();
        });
    }
}
