package de.xxarox.wdtools.handler;

import de.xxarox.wdtools.manager.LobbyManager;
import de.xxarox.wdtools.manager.ServerManager;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.IReconnectHandler;

public class ReconnectLobbyHandler implements IReconnectHandler {
    @Override
    public ServerInfo getFallbackServer(ProxiedPlayer proxiedPlayer, ServerInfo serverInfo, String reason) {
        System.out.println(reason);
        ServerInfo lobby = LobbyManager.getRandomLobby();
        if (lobby == null) {
            lobby = ServerManager.getConnector();
            proxiedPlayer.sendMessage("§cNo lobby found, connected to a fallback server");
        }
        return lobby;
    }
}
