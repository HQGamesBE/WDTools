package de.xxarox.wdtools.handler;

import de.xxarox.wdtools.manager.LobbyManager;
import de.xxarox.wdtools.manager.WhitelistManager;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.IJoinHandler;

public class JoinLobbyHandler implements IJoinHandler {
    @Override
    public ServerInfo determineServer(ProxiedPlayer proxiedPlayer) {
        if (WhitelistManager.isEnabled() && !WhitelistManager.isWhitelisted(proxiedPlayer.getName())) {
            ServerInfo connector = ProxyServer.getInstance().getServerInfo("connector");
            if (connector == null) {
                proxiedPlayer.disconnect("§cServer is under §4maintenance\n§fMore information's: §9discord.gg/nVayp7KDNZ");
                return null;
            } else {
                proxiedPlayer.sendMessage("§cServer is under §4maintenance");
                proxiedPlayer.sendMessage("§fMore information's: §9discord.gg/nVayp7KDNZ");
                return connector;
            }
        }
        return LobbyManager.getRandomLobby();
    }
}
