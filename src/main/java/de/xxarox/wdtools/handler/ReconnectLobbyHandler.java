package de.xxarox.wdtools.handler;

import de.xxarox.wdtools.manager.LobbyManager;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.IReconnectHandler;

public class ReconnectLobbyHandler implements IReconnectHandler {
    @Override
    public ServerInfo getFallbackServer(ProxiedPlayer proxiedPlayer, ServerInfo serverInfo, String s) {
        return LobbyManager.getRandomLobby();
    }
}
