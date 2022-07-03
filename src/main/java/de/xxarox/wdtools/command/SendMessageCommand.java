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
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.StringJoiner;

public class SendMessageCommand extends Command {
    public SendMessageCommand(String name) {
        super(name,
                CommandSettings.builder()
                        .setUsageMessage("/sendmessage <player> <message>")
                        .setDescription("Send messages via Proxy")
                        .setPermission("xxarox.wdtools.message")
                        .build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length < 2) {
            return false;
        }
        if (strings[0].equals("*")) {
            for (ProxiedPlayer p: ProxyServer.getInstance().getPlayers().values()) {
                StringJoiner jibbit = new StringJoiner(" ");

                for (String string : strings) {
                    if (!strings[0].equals(string)) {
                        jibbit.add(string);
                    }
                }
                p.sendMessage(jibbit.toString());
                return true;
            }
        }
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(strings[0]);
        if (player == null) {
            commandSender.sendMessage("Player not found!");
            return true;
        }
        StringJoiner jibbit = new StringJoiner(" ");

        for (String string : strings) {
            if (!strings[0].equals(string)) {
                jibbit.add(string);
            }
        }
        player.sendMessage(jibbit.toString());
        return true;
    }
}
