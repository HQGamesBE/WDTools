package de.xxarox.wdtools.manager;

import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

import java.util.Collection;
import java.util.HashSet;

public class LobbyManager {
    private static Collection<ServerInfo> lobbies = new HashSet<>();

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

    public static boolean isLobby(String serverName){
        boolean found = false;
        for (ServerInfo lobby:
             lobbies) {
            if (lobby.getServerName().equals(serverName)) {
                found = true;
            }
        };
        return found;
    }

    public static ServerInfo getLobby(String serverName){
        if (isLobby(serverName)) {
            for (ServerInfo lobby:
                    lobbies) {
                if (lobby.getServerName().equals(serverName)) {
                    return lobby;
                }
            }
        }
        return null;
    }

    public static String getLobbyList(){
        StringBuilder list = new StringBuilder();
        for (ServerInfo lobby:
            lobbies) {
            list.append("§8§l» §r§aLobby-").append(lobby.getServerName().split("-")[1]).append(" §7- §f").append(lobby.getPlayers().size()).append(" player").append(lobby.getPlayers().size() == 1 ? "" : "s").append(" online\n");
        }
        return list.toString();
    }
}
