package me.akamex.luckapi.command;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.command.CommandSender;

class ExecutorImpl implements Executor {

    private CommandSender sender;

    ExecutorImpl(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public CommandSender getHandle() {
        return sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutorImpl executor = (ExecutorImpl) o;
        return new EqualsBuilder().append(sender, executor.sender).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(sender).toHashCode();
    }
}
