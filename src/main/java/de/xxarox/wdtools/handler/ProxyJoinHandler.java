package de.xxarox.wdtools.handler;

import de.xxarox.wdtools.WDTools;
import de.xxarox.wdtools.manager.LobbyManager;
import de.xxarox.wdtools.manager.WhitelistManager;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.IJoinHandler;

public class ProxyJoinHandler implements IJoinHandler {
    @Override
    public ServerInfo determineServer(ProxiedPlayer proxiedPlayer) {
        if (WhitelistManager.isEnabled() && !WhitelistManager.isWhitelisted(proxiedPlayer.getName())) {
            proxiedPlayer.disconnect(WhitelistManager.getKickMessage());
            return null;
        }
        ServerInfo lobby = LobbyManager.getRandomLobby();
        if (lobby == null) {
            proxiedPlayer.disconnect(WDTools.getMessage("lobby_not_found"));
            return null;
        }
        return lobby;
    }
}
