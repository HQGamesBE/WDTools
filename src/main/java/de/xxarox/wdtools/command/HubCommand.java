/*
 * Copyright (c) Jan Sohn / xxAROX
 * All rights reserved.
 * I don't want anyone to use my source code without permission.
 */

package de.xxarox.wdtools.command;

import de.xxarox.wdtools.manager.LobbyManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class HubCommand extends Command {
    public HubCommand(String name) {
        super(name,
                CommandSettings.builder()
                        .setUsageMessage("/hub")
                        .setDescription("Connect to HUB-Server")
                        .build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage("§8§l» §r§cOnly for players.");
            return true;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (!player.getServerInfo().getServerName().startsWith("lobby")) {
            ServerInfo lobby = LobbyManager.getRandomLobby();
            player.connect(lobby);
        }
        return true;
    }
}
