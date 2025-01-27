package com.zinc.kpoker.lib.brigadier.argument.primitive;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.zinc.kpoker.lib.brigadier.argument.RequiredArgument;
import net.minecraft.commands.CommandSourceStack;

public class IntArgument extends RequiredArgument<Integer> {
    private final int[] bound = { Integer.MIN_VALUE, Integer.MAX_VALUE };

    public IntArgument(String name) {
        super(name);
    }

    public IntArgument(String name, int min, int max) {
        this(name);
        this.bound[0] = min;
        this.bound[1] = max;
    }

    @Override
    public RequiredArgumentBuilder<CommandSourceStack, Integer> argument() {
        return RequiredArgumentBuilder.argument(this.name, IntegerArgumentType.integer(bound[0], bound[1]));
    }
}