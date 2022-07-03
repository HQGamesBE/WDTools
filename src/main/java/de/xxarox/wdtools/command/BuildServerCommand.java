package de.xxarox.wdtools.command;

import de.xxarox.wdtools.manager.ServerManager;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.BedrockServerInfo;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.net.InetSocketAddress;

public class BuildServerCommand extends Command {
    public BuildServerCommand() {
        super(
                "buildserver",
                CommandSettings.builder()
                        .setUsageMessage("/buildserver")
                        .setDescription("Buildserver command.")
                        .setPermission("xxarox.wdtools.buildserver")
                        .build()
        );
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length == 0 && commandSender.isPlayer()) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            ServerInfo buildserver = ServerManager.getBuildServer();
            if (buildserver == null) {
                player.sendMessage("§8§l» §r§cBuildserver is not running.");
                return true;
            }
            player.connect(buildserver);
            return true;
        }
        switch (strings[0]) {
            case "register": {
                if (commandSender.isPlayer()) {
                    commandSender.sendMessage("§8§l» §r§cOnly for console.");
                    return true;
                }
                if (ServerManager.getBuildServer() != null) {
                    commandSender.sendMessage("§8§l» §r§cBuildserver is already registered.");
                    return true;
                }
                if (strings.length == 1) {
                    commandSender.sendMessage("§8§l» §r§cUsage: /buildserver register <ip> <port> [public_address]");
                    return true;
                }
                String id = "buildserver";
                String ip = strings[2];
                int port = Integer.parseInt(strings[3]);
                String publicAddress = strings[4] == null ? null : strings[5];

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
                serverInfo = new BedrockServerInfo(id, new InetSocketAddress(ip, port), publicAddress == null ? null : new InetSocketAddress(publicAddress, port));
                if (ProxyServer.getInstance().registerServerInfo(serverInfo)) {
                    if (ServerManager.getBuildServer() == null) ServerManager.registerBuildServer(serverInfo);
                    else commandSender.sendMessage("§8§l» §r§cServer with this id is already as buildserver.");
                    commandSender.sendMessage("§8§l» §r§aRegistered buildserver.");
                } else {
                    commandSender.sendMessage("§8§l» §r§cFailed to register buildserver.");
                }
                return true;
            }
            case "unregister": {
                if (commandSender.isPlayer()) {
                    commandSender.sendMessage("§8§l» §r§cOnly for console.");
                    return true;
                }
                if (ServerManager.getBuildServer() == null) {
                    commandSender.sendMessage("§8§l» §r§cBuildserver is not registered.");
                    return true;
                }
                if (strings.length == 1) {
                    commandSender.sendMessage("§8§l» §r§cUsage: /buildserver unregister <id>");
                    return true;
                }
                if (strings.length == 2) {
                    String id = strings[1];
                    ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(id);
                    if (serverInfo == null) {
                        commandSender.sendMessage("§8§l» §r§cServer with this id does not exist.");
                        return true;
                    }
                    if (ServerManager.getBuildServer() != null) ServerManager.unregisterBuildServer();
                    ProxyServer.getInstance().removeServerInfo(id);
                    commandSender.sendMessage("§8§l» §r§aUnregistered buildserver.");
                }
                return true;
            }
        }
        return true;
    }
}
