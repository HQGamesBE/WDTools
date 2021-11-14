package de.xxarox.wdtools.manager;

import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

import java.util.Collection;
import java.util.HashSet;

public class LobbyManager {
    private static Collection<ServerInfo> lobbies = new HashSet<ServerInfo>();

    public static void registerLobby(ServerInfo serverInfo){
        if (serverInfo.getServerName().toLowerCase().startsWith("lobby")) {
            lobbies.add(serverInfo);
        }
    }

    public static void unregisterLobby(ServerInfo serverInfo){
        if (serverInfo.getServerName().toLowerCase().startsWith("lobby")) {
            lobbies.remove(serverInfo);
        }
    }

    public static ServerInfo getRandomLobby(){
        ServerInfo smallestLobby = null;
        for (ServerInfo serverInfo : lobbies) {
            if (smallestLobby == null || smallestLobby.getPlayers().size() >= serverInfo.getPlayers().size()) {
                smallestLobby = serverInfo;
            }
        }
        return smallestLobby;
    }
}
