package me.akamex.luckapi.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

public class ChatCommand {

    private ChatCommand() {
        throw new UnsupportedOperationException();
    }

    public static void registerCommand(String label, ExecutingStrategy strategy) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(label);
        if(pluginCommand == null) {
            throw new IllegalArgumentException("Label not registered as command!");
        }
        pluginCommand.setExecutor((sender, command, s, args) -> {
            Executor executor = new ExecutorImpl(sender);
            Arguments arguments = new Arguments(args);
            strategy.onCommand(new CommandSession(executor, arguments));
            return true;
        });

        pluginCommand.setTabCompleter((sender, command, s, args) -> {
            Executor executor = new ExecutorImpl(sender);
            Arguments arguments = new Arguments(args);
            return strategy.onTabComplete(new CommandSession(executor, arguments));
        });
    }

    public static void registerCommand(String label, ExecutingStrategy.Builder.ConfirmedBuilder builder) {
        registerCommand(label, builder.create());
    }

}
