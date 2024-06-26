/*
 * Copyright (c) Jan Sohn / xxAROX
 * All rights reserved.
 * I don't want anyone to use my source code without permission.
 */

package de.xxarox.wdtools.manager;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.Collection;

public class ServerManager {
    private static ServerInfo buildServer;
    private static ServerInfo devServer;


    public static void registerBuildServer(ServerInfo serverInfo) {
        buildServer = serverInfo;
    }

    public static void registerDevServer(ServerInfo serverInfo) {
        devServer = serverInfo;
    }

    public static ServerInfo getBuildServer() {
        return buildServer;
    }

    public static ServerInfo getDevServer() {
        return devServer;
    }

    public static void unregisterBuildServer() {
        buildServer = null;
    }

    public static void unregisterDevServer() {
        devServer = null;
    }

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
            p.sendToFallback(p.getServerInfo(), "Server was stopped");
        }
        ProxyServer.getInstance().removeServerInfo(name);
    }

    public static Collection<ServerInfo> getServers() {
        return ProxyServer.getInstance().getServers();
    }
}
