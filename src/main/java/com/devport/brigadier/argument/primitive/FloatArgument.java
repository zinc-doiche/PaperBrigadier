package com.devport.brigadier.argument.primitive;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.devport.brigadier.argument.RequiredArgument;
import net.minecraft.commands.CommandSourceStack;

public class FloatArgument extends RequiredArgument<Float> {
    private final float min;
    private final float max;

    public FloatArgument(String name) {
        this(name, Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public FloatArgument(String name, float min, float max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public RequiredArgumentBuilder<CommandSourceStack, Float> argument() {
        return RequiredArgumentBuilder.argument(name, FloatArgumentType.floatArg(min, max));
    }
}
