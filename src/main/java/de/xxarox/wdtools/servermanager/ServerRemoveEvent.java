/*
 * Copyright (c) Jan Sohn / xxAROX
 * All rights reserved.
 * I don't want anyone to use my source code without permission.
 */

package de.xxarox.wdtools.servermanager;

import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.event.CancellableEvent;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

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
