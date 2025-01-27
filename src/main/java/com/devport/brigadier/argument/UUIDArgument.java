package com.devport.brigadier.argument;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.UuidArgument;

import java.util.UUID;

public class UUIDArgument extends RequiredArgument<UUID> {

    public UUIDArgument(String name) {
        super(name);
    }

    @Override
    protected RequiredArgumentBuilder<CommandSourceStack, UUID> argument() {
        return RequiredArgumentBuilder.argument(name, UuidArgument.uuid());
    }
}
