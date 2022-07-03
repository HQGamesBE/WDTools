/*
 * Copyright (c) Jan Sohn / xxAROX
 * All rights reserved.
 * I don't want anyone to use my source code without permission.
 */

package de.xxarox.wdtools.command;

import de.xxarox.wdtools.WDTools;
import de.xxarox.wdtools.manager.LobbyManager;
import de.xxarox.wdtools.manager.ServerManager;
import de.xxarox.wdtools.servermanager.ServerAddEvent;
import de.xxarox.wdtools.servermanager.ServerRemoveEvent;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.BedrockServerInfo;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

import java.net.InetSocketAddress;

public class ServerManagerCommand extends Command {
    public ServerManagerCommand() {
        super(
                "servermanager",
                CommandSettings.builder()
                        .setUsageMessage("/servermanager")
                        .setDescription("Manager servers.")
                        .setPermission("xxarox.wdtools.servermanager")
                        .build()
        );
    }

    @Override
    public boolean onExecute(CommandSender sender, String s, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("add")) {
                sendUsage(sender, "/svm add <server>");
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                sendUsage(sender, "/svm remove <server>");
                return true;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                if (!ServerManager.serverExists(args[1])) {
                    sender.sendMessage(WDTools.getPrefix() + "The server §a" + args[1] + "§7 does not exist.");
                    return true;
                }
                ServerInfo info = ServerManager.getServerInfo(args[1]);

                if (info == null) {
                    sender.sendMessage(WDTools.getPrefix() + "The server §a" + args[1] + "§7 does not exist.");
                    return true;
                }
                sender.sendMessage("§7--- §a" + info.getServerName() + " Info§7 ---");
                sender.sendMessage("§aName: §7" + info.getServerName());
                sender.sendMessage("§aAddress: §7" + info.getAddress().getAddress().getHostAddress() + ":" + info.getAddress().getPort());
                sender.sendMessage("§aPlayer Count: §7" + info.getPlayers().size());
                sender.sendMessage("§7--------");
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (!ServerManager.serverExists(args[1])) {
                    sender.sendMessage(WDTools.getPrefix() + "The server §a" + args[1] + "§7 does not exist.");
                    return true;
                }
                ServerRemoveEvent ev = new ServerRemoveEvent(ServerManager.getServerInfo(args[1]), sender);
                ProxyServer.getInstance().getEventManager().callEvent(ev);

                if (ev.isCancelled()) {
                    return true;
                }
                LobbyManager.unregisterLobby(ev.getModifiedServer());
                ServerManager.unregisterServer(ev.getModifiedServer().getServerName());
                sender.sendMessage(WDTools.getPrefix() + "Removed the server §a" + ev.getModifiedServer().getServerName());
                return true;
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                sendUsage(sender, "/svm info <server>");
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (ServerManager.serverExists(args[1])) {
                    sender.sendMessage(WDTools.getPrefix() + "The server §a" + args[1] + "§7 already exists.");
                    return true;
                }
                try {InetSocketAddress address = getIp(args[2]);

                    if (address == null) {
                        sender.sendMessage(WDTools.getPrefix() + "Invalid ip address §a" + args[2] + "§7. Here's an example: §a127.0.0.1:12345");
                        return false;
                    }
                    ServerInfo info = new BedrockServerInfo(args[1], address, address);
                    ServerAddEvent addEvent = new ServerAddEvent(info, sender);
                    ProxyServer.getInstance().getEventManager().callEvent(addEvent);

                    if (addEvent.isCancelled()) {
                        return true;
                    }
                    LobbyManager.registerLobby(addEvent.getModifiedServer());
                    ServerManager.registerServer(addEvent.getModifiedServer());
                    sender.sendMessage(WDTools.getPrefix() + "Added a server with the name §a" + addEvent.getModifiedServer().getServerName() + "§7 and ip address §a" + args[2] + "§a.");
                } catch (StackOverflowError overflowError) {
                    overflowError.printStackTrace();
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                sendUsage(sender, "/svm remove <server>");
                return true;
            }
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                sendUsage(sender, "/svm add <server>");
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                sendUsage(sender, "/svm remove <server>");
                return true;
            }
        }
        return false;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(WDTools.getPrefix() + "Help:");
        sender.sendMessage(getHelpString("/svm help", "Display this help menu"));
        sender.sendMessage(getHelpString("/svm list", "List servers"));
        sender.sendMessage(getHelpString("/svm info <server>", "Display info about a server"));
        sender.sendMessage(getHelpString("/svm add <name> [ip:port]", "Add a server to BungeeCord"));
        sender.sendMessage(getHelpString("/svm remove <server>", "Remove a server from BungeeCord"));
        sender.sendMessage(getHelpString("/svm edit <server>", "Edit a server's information"));
    }

    private String getHelpString(String command, String info) {
        return "§a" + command + "§7 - " + info;
    }

    private void sendUsage(CommandSender sender, String usage) {
        sender.sendMessage(WDTools.getPrefix() + "Correct usage: §a" + usage);
    }

    private void sendEditMenu(CommandSender sender, String serverName) {
        sender.sendMessage(WDTools.getPrefix() + " Edit Help:");
        sender.sendMessage(getHelpString("/svm edit " + serverName + " name <name>", "Change this server's name"));
        sender.sendMessage(getHelpString("/svm edit " + serverName + " ip <ip>", "Change this server's ip address"));
        sender.sendMessage(getHelpString("/svm edit " + serverName + " motd <motd>", "Change this server's motd"));
    }

    private InetSocketAddress getIp(String input) {
        if (!input.contains(":") || !input.contains(".")) {
            return null;
        }
        String[] parts = input.split(":");

        if (input.split(":").length != 2) {
            return null;
        }
        if (input.split("\\.").length != 4) {
            return null;
        }
        for (char c : parts[0].replace(".", "").toCharArray()) {
            if (!Character.isDigit(c)) {
                return null;
            }
        }
        for (char c : parts[1].toCharArray()) {
            if (!Character.isDigit(c)) {
                return null;
            }
        }
        return new InetSocketAddress(parts[0], Integer.parseInt(parts[1]));
    }
}
