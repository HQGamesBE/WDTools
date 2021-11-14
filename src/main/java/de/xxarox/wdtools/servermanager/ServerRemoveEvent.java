package de.xxarox.wdtools.servermanager;

import dev.waterdog.command.CommandSender;
import dev.waterdog.event.CancellableEvent;
import dev.waterdog.network.ServerInfo;

public class ServerRemoveEvent extends ServerEvent implements CancellableEvent {
    private CommandSender sender;
    private boolean cancelled;

    public ServerRemoveEvent(ServerInfo serverModified, CommandSender sender) {
        super(serverModified);
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
