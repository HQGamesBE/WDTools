package de.xxarox.wdtools.handler;

import de.xxarox.wdtools.manager.LobbyManager;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.IJoinHandler;

public class JoinLobbyHandler implements IJoinHandler {
    @Override
    public ServerInfo determineServer(ProxiedPlayer proxiedPlayer) {
        return LobbyManager.getRandomLobby();
    }
}
