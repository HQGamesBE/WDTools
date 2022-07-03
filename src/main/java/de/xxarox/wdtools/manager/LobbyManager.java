/*
 * Copyright (c) Jan Sohn / xxAROX
 * All rights reserved.
 * I don't want anyone to use my source code without permission.
 */

package de.xxarox.wdtools.manager;

import de.xxarox.wdtools.WDTools;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class LobbyManager {
    private static Priority priority = Priority.RANDOM;
    private static Collection<ServerInfo> lobbies = new HashSet<>();
    private static SecureRandom secureRandom = new SecureRandom("xxarox_ist_der_beste_developer_der_welt".getBytes());

    public static void setPriority(Priority priority) {
        setPriority(priority, false);
    }
    public static void setPriority(Priority priority, boolean store) {
        if (store) {
            WDTools.getInstance().getConfig().set("lobby.priority", priority.name());
            WDTools.getInstance().getConfig().save();
        }
        LobbyManager.priority = priority;
    }

    public static Priority getPriority() {
        return priority;
    }

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
        return decideLobby(null);
    }

    public static ServerInfo getRandomLobby(ServerInfo targetServer){
        return decideLobby(targetServer);
    }

    public static boolean isLobby(String id){
        boolean found = false;
        for (ServerInfo lobby:
             lobbies) {
            if (lobby.getServerName().equals(id)) {
                found = true;
            }
        };
        return found;
    }

    public static ServerInfo getLobby(String id){
        if (isLobby(id)) {
            for (ServerInfo lobby:
                    lobbies) {
                if (lobby.getServerName().equals(id)) {
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

    public static Collection<ServerInfo> getLobbies() {
        return lobbies;
    }

    private static ServerInfo decideLobby(ServerInfo targetServer) {
        List<ServerInfo> results = new ArrayList<>();
        ServerInfo betterServer = null;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        if (lobbies.size() == 0) return null;

        switch (priority) {
            case RANDOM:
                results.addAll(lobbies);
            case LOW_PLAYER_COUNT: {
                for (ServerInfo serverInfo : lobbies) {
                    if (betterServer == null) {
                        betterServer = serverInfo;
                        continue;
                    }
                    if (serverInfo.getPlayers().size() > betterServer.getPlayers().size()) continue;
                    betterServer = serverInfo;
                }
                break;
            }
            case HIGH_PLAYER_COUNT: {
                for (ServerInfo serverInfo : lobbies) {
                    if (betterServer == null) {
                        betterServer = serverInfo;
                        continue;
                    }
                    if (betterServer.getPlayers().size() > serverInfo.getPlayers().size()) continue;
                    betterServer = serverInfo;
                }
                break;
            }
            case HIGH_PLAYER_COUNT_RANDOM: {
                for (ServerInfo server : lobbies) {
                    if (targetServer != null && server.getServerName().equals(targetServer.getServerName())) continue;
                    int count = server.getPlayers().size();

                    if (count >= max) {
                        if (count > max) {
                            max = count;
                            results.clear();
                        }
                        results.add(server);
                    }
                }
            }
            case LOW_PLAYER_COUNT_RANDOM: {
                for (ServerInfo server : lobbies) {
                    if (targetServer != null && server.getServerName().equals(targetServer.getServerName())) continue;

                    int count = server.getPlayers().size();

                    if (count <= min) {
                        if (count < min) {
                            min = count;

                            results.clear();
                        }

                        results.add(server);
                    }
                }
            }
        }
        return betterServer == null ? results.get(secureRandom.nextInt(results.size())) : betterServer;
    }

    public enum Priority {
        LOW_PLAYER_COUNT("low"),
        HIGH_PLAYER_COUNT("high"),
        RANDOM("random"),
        LOW_PLAYER_COUNT_RANDOM("random_low"),
        HIGH_PLAYER_COUNT_RANDOM("random_high"),
        ;
        private final String value;

        Priority(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
