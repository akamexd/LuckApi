package me.akamex.luckapi.message;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.command.CommandSender;

public class StringMessage implements Message {

    private String message;

    public StringMessage(String message) {
        this.message = message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toRawText() {
        return message;
    }

    @Override
    public void send(CommandSender sender) {
        if(message.isEmpty()) {
            return;
        }
        sender.sendMessage(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringMessage that = (StringMessage) o;
        return new EqualsBuilder().append(message, that.message).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(message).toHashCode();
    }
}
