package de.xxarox.wdtools.handler;

import de.xxarox.wdtools.WDTools;
import de.xxarox.wdtools.manager.LobbyManager;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.IReconnectHandler;

public class ReconnectLobbyHandler implements IReconnectHandler {
    @Override
    public ServerInfo getFallbackServer(ProxiedPlayer proxiedPlayer, ServerInfo serverInfo, String reason) {
        if (reason.equals("xxarox.banned")) {
            proxiedPlayer.disconnect(WDTools.getMessage("banned"));
            return null;
        }
        if (reason.equals("xxarox.kicked")) {
            proxiedPlayer.disconnect(WDTools.getMessage("kicked"));
            return null;
        }
        ServerInfo lobby = LobbyManager.getRandomLobby(serverInfo);
        if (lobby == null) {
            proxiedPlayer.disconnect(WDTools.getMessage("lobby_not_found"));
            return null;
        }
        return lobby;
    }
}
