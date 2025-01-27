package com.devport.brigadier;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

public class CommandRegistrar {
    private static final String PREFIX = "kpoker";

    public static void register(Command... commands) {
        CommandMap commandMap = Bukkit.getCommandMap();
        for (Command command : commands) {
            commandMap.register(PREFIX, command);
        }
    }

    public static void unregister(Command... commands) {
        CommandMap commandMap = Bukkit.getCommandMap();
        for (Command command : commands) {
            command.unregister(commandMap);
        }
    }
}
