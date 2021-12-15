package de.xxarox.wdtools.handler;

import de.xxarox.wdtools.manager.LobbyManager;
import de.xxarox.wdtools.manager.WhitelistManager;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.IJoinHandler;

public class JoinLobbyHandler implements IJoinHandler {
    @Override
    public ServerInfo determineServer(ProxiedPlayer proxiedPlayer) {
        if (WhitelistManager.isEnabled() && !WhitelistManager.isWhitelisted(proxiedPlayer.getName())) {
            proxiedPlayer.disconnect(WhitelistManager.getKickMessage());
            return null;
        }
        return LobbyManager.getRandomLobby();
    }
}
