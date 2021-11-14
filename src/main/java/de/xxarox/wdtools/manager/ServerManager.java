package de.xxarox.wdtools.manager;

import dev.waterdog.ProxyServer;
import dev.waterdog.network.ServerInfo;
import dev.waterdog.player.ProxiedPlayer;

import java.util.Collection;

public class ServerManager {
    public static boolean serverExists(String name) {
        return getServerInfo(name) != null;
    }

    public static ServerInfo getServerInfo(String name) {
        return ProxyServer.getInstance().getServerInfo(name);
    }

    public static void registerServer(ServerInfo serverInfo) {
        if (serverExists(serverInfo.getServerName())) {
            return;
        }
        ProxyServer.getInstance().registerServerInfo(serverInfo);
    }

    public static void unregisterServer(String name) {
        if (!serverExists(name)) {
            return;
        }
        ServerInfo info = getServerInfo(name);

        for (ProxiedPlayer p : info.getPlayers()) {
            p.sendToFallback(p.getServer().getInfo(), "Server was stopped");
        }
        ProxyServer.getInstance().removeServerInfo(name);
    }

    public static Collection<ServerInfo> getServers() {
        return ProxyServer.getInstance().getServers();
    }
}
