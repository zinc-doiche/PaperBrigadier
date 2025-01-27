package com.devport.brigadier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.devport.brigadier.argument.LiteralArgument;
import com.devport.brigadier.argument.PlayerArgument;
import com.devport.brigadier.argument.UUIDArgument;
import com.devport.brigadier.argument.primitive.FloatArgument;
import com.devport.brigadier.argument.primitive.IntArgument;
import com.devport.brigadier.argument.primitive.StringArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.command.VanillaCommandWrapper;

/**
 * {@link com.mojang.brigadier}의 Wrapper.
 * @see CommandBuilder#literal
 */
public interface CommandBuilder {
    int SINGLE_SUCCESS = com.mojang.brigadier.Command.SINGLE_SUCCESS;

    Command build();

    static Command command(LiteralArgumentBuilder<CommandSourceStack> builder) {
        CraftServer server = (CraftServer) Bukkit.getServer();
        MinecraftServer minecraftServer = server.getServer();
        Commands commands = minecraftServer.vanillaCommandDispatcher;
        CommandDispatcher<CommandSourceStack> dispatcher = commands.getDispatcher();

        return new VanillaCommandWrapper(commands, dispatcher.register(builder));
    }

    static LiteralArgument literal(String name) {
        return new LiteralArgument(name);
    }

    static StringArgument string(String name) {
        return new StringArgument(name);
    }

    static StringArgument word(String name) {
        return new StringArgument(name, StringArgumentType.word());
    }

    static StringArgument quotablePhrase(String name) {
        return new StringArgument(name, StringArgumentType.string());
    }

    static StringArgument greedyPhrase(String name) {
        return new StringArgument(name, StringArgumentType.greedyString());
    }

    static IntArgument integer(String name) {
        return new IntArgument(name);
    }

    static IntArgument integer(String name, int min, int max) {
        return new IntArgument(name, min, max);
    }

    static FloatArgument floatArg(String name) {
        return new FloatArgument(name);
    }

    static FloatArgument floatArg(String name, float min, float max) {
        return new FloatArgument(name, min, max);
    }

    static PlayerArgument player(String name) {
        return new PlayerArgument(name);
    }

    static UUIDArgument uuid(String name) {
        return new UUIDArgument(name);
    }
}
