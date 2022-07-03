/*
 * Copyright (c) Jan Sohn / xxAROX
 * All rights reserved.
 * I don't want anyone to use my source code without permission.
 */

package de.xxarox.wdtools.command;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class TransferPlayerCommand extends Command {
    public TransferPlayerCommand(String name) {
        super(name,
                CommandSettings.builder()
                        .setUsageMessage("/transfer")
                        .setDescription("Transfer players via Proxy.")
                        .setPermission("xxarox.wdtools.transfer")
                        .build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage("Missing arguments!");
            return false;
        }

        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(strings[0]);
        if (player == null) {
            commandSender.sendMessage("Player not found!");
            return false;
        }

        ServerInfo targetServer = ProxyServer.getInstance().getServerInfo(strings[1]);
        if (targetServer == null) {
            commandSender.sendMessage("Server not found");
            return false;
        }
        player.connect(targetServer);
        return false;
    }
}
