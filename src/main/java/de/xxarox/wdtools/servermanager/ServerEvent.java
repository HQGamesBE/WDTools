package de.xxarox.wdtools.servermanager;


import dev.waterdog.waterdogpe.event.Event;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

public abstract class ServerEvent extends Event {
    private ServerInfo serverModified;

    public ServerEvent(ServerInfo serverModified) {
        this.serverModified = serverModified;
    }

    public ServerInfo getModifiedServer() {
        return serverModified;
    }
}
