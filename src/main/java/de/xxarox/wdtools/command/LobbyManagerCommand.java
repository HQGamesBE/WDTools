package de.xxarox.wdtools.command;

import de.xxarox.wdtools.WDTools;
import de.xxarox.wdtools.manager.LobbyManager;
import de.xxarox.wdtools.network.serverinfo.CloudServerInfo;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

import java.net.InetSocketAddress;
import java.util.stream.Collectors;

public class LobbyManagerCommand extends Command {
    public LobbyManagerCommand() {
        super(
                "lobbymanager",
                CommandSettings.builder()
                        .setUsageMessage("/lobbymanager")
                        .setDescription("Manager lobby servers.")
                        .setPermission("xxarox.wdtools.lobbymanager")
                        .setAliases(new String[]{"lm"})
                        .build()
        );
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length == 0) {
            sendHelp(commandSender);
            return true;
        }
        switch (strings[0]) {
            case "priority": {
                if (strings.length == 1) {
                    commandSender.sendMessage("§8§l» §r§c" + "/lobbymanager priority <low|high|random|random_low|random_high>");
                    return true;
                }
                switch (strings[1].toLowerCase()) {
                    case "low": {
                        LobbyManager.setPriority(LobbyManager.Priority.LOW_PLAYER_COUNT, true);
                        commandSender.sendMessage(WDTools.getPrefix() + "Lobby-Server priority set to §aLOW_PLAYER_COUNT§7.");
                        return true;
                    }
                    case "high": {
                        LobbyManager.setPriority(LobbyManager.Priority.HIGH_PLAYER_COUNT, true);
                        commandSender.sendMessage(WDTools.getPrefix() + "Lobby-Server priority set to §aHIGH_PLAYER_COUNT§7.");
                        return true;
                    }
                    case "random": {
                        LobbyManager.setPriority(LobbyManager.Priority.RANDOM, true);
                        commandSender.sendMessage(WDTools.getPrefix() + "Lobby-Server priority set to §aRANDOM§7.");
                        return true;
                    }
                    case "random_low": {
                        LobbyManager.setPriority(LobbyManager.Priority.LOW_PLAYER_COUNT_RANDOM, true);
                        commandSender.sendMessage(WDTools.getPrefix() + "Lobby-Server priority set to §aLOW_PLAYER_COUNT_RANDOM§7.");
                        return true;
                    }
                    case "random_high": {
                        LobbyManager.setPriority(LobbyManager.Priority.HIGH_PLAYER_COUNT_RANDOM, true);
                        commandSender.sendMessage(WDTools.getPrefix() + "Lobby-Server priority set to §aHIGH_PLAYER_COUNT_RANDOM§7.");
                        return true;
                    }
                    default: {
                        commandSender.sendMessage("§8§l» §r§c" + "/lobbymanager priority <low|high|random|random_low|random_high>");
                        return true;
                    }
                }
            }
            case "register": {
                if (commandSender.isPlayer()) {
                    commandSender.sendMessage("§8§l» §r§cOnly for console.");
                    return true;
                }
                if (strings.length == 1) {
                    commandSender.sendMessage("§8§l» §r§cUsage: /lobbymanager register <id> <server_name> <ip> <port> [public_address]");
                    return true;
                }
                if (strings.length < 4) {
                    commandSender.sendMessage("§8§l» §r§cUsage: /lobbymanager register <id> <server_name> <ip> <port> [public_address]");
                    return true;
                }
                String id = strings[1];
                String serverName = strings[2];
                String ip = strings[3];
                int port = Integer.parseInt(strings[4]);
                String publicAddress = strings[5] == null ? null : strings[5];

                if (!ip.matches("^(\\d{1,3}\\.){3}\\d{1,3}$")) {
                    commandSender.sendMessage("§8§l» §r§cIp must be a valid ipv4 address.");
                    return true;
                }
                try {
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
                    inetSocketAddress.isUnresolved();
                } catch (Exception e) {
                    commandSender.sendMessage("§8§l» §r§cAddress is not valid.");
                    return true;
                }
                if (port > 0 && port < 65535) {
                    commandSender.sendMessage("§8§l» §r§cPort must be between 0 and 65535.");
                    return true;
                }


                ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(id);
                if (serverInfo != null) {
                    commandSender.sendMessage("§8§l» §r§cServer with this id already exists.");
                    return true;
                }
                serverInfo = new CloudServerInfo(serverName, id, new InetSocketAddress(ip, port), publicAddress == null ? null : new InetSocketAddress(publicAddress, port));
                if (ProxyServer.getInstance().registerServerInfo(serverInfo)) {
                    if (!LobbyManager.isLobby(id)) LobbyManager.registerLobby(serverInfo);
                    else commandSender.sendMessage("§8§l» §r§cServer with this id is already a lobby.");
                    commandSender.sendMessage("§8§l» §r§aRegistered Lobby-Server.");
                } else {
                    commandSender.sendMessage("§8§l» §r§cFailed to register Lobby-Server.");
                }
                return true;
            }
            case "unregister": {
                if (commandSender.isPlayer()) {
                    commandSender.sendMessage("§8§l» §r§cOnly for console.");
                    return true;
                }
                if (strings.length == 1) {
                    commandSender.sendMessage("§8§l» §r§cUsage: /lobbymanager unregister <id>");
                    return true;
                }
                if (strings.length == 2) {
                    String id = strings[1];
                    ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(id);
                    if (serverInfo == null) {
                        commandSender.sendMessage("§8§l» §r§cServer with this id does not exist.");
                        return true;
                    }
                    if (LobbyManager.isLobby(id)) LobbyManager.unregisterLobby(serverInfo);
                    ProxyServer.getInstance().removeServerInfo(id);
                    commandSender.sendMessage("§8§l» §r§aUnregistered Lobby-Server.");
                }
                return true;
            }
            case "list": case "ls": {
                commandSender.sendMessage(LobbyManager.getLobbies().stream().map(serverInfo -> "§8§l» §r§a" + serverInfo.getServerName() + " §8§l» §r§7" + serverInfo.getAddress().getHostString() + ":" + serverInfo.getAddress().getPort()).collect(Collectors.joining("\n")));
                return true;
            }
        }
        return true;
    }

    private void sendHelp(CommandSender commandSender) {
        if (commandSender.isPlayer()) {
            commandSender.sendMessage("§8§l» §r§cUsage: /lobbymanager <priority|list>");
        } else {
            commandSender.sendMessage("§8§l» §r§cUsage: /lobbymanager <priority|register|unregister|list>");
        }
    }
}
