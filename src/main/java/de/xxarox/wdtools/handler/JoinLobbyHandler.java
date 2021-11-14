package de.xxarox.wdtools.handler;

import de.xxarox.wdtools.manager.LobbyManager;
import dev.waterdog.network.ServerInfo;
import dev.waterdog.player.ProxiedPlayer;
import dev.waterdog.utils.types.IJoinHandler;

public class JoinLobbyHandler implements IJoinHandler {
    @Override
    public ServerInfo determineServer(ProxiedPlayer proxiedPlayer) {
        return LobbyManager.getRandomLobby();
    }
}
