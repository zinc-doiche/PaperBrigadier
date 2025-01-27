package com.zinc.kpoker.lib.brigadier.argument.primitive;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.zinc.kpoker.lib.brigadier.argument.RequiredArgument;
import net.minecraft.commands.CommandSourceStack;

public class StringArgument extends RequiredArgument<String> {
    private final StringArgumentType argumentType;

    public StringArgument(String name) {
        this(name, StringArgumentType.word());
    }

    public StringArgument(String name, StringArgumentType argumentType) {
        super(name);
        this.argumentType = argumentType;
    }

    @Override
    public RequiredArgumentBuilder<CommandSourceStack, String> argument() {
        return RequiredArgumentBuilder.argument(name, argumentType);
    }
}
