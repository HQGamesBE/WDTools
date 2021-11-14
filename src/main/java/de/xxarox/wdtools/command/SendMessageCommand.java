package de.xxarox.wdtools.command;

import dev.waterdog.ProxyServer;
import dev.waterdog.command.Command;
import dev.waterdog.command.CommandSender;
import dev.waterdog.command.CommandSettings;
import dev.waterdog.player.ProxiedPlayer;

import java.util.StringJoiner;

public class SendMessageCommand extends Command {
    public SendMessageCommand(String name) {
        super(name,
                CommandSettings.builder()
                        .setUsageMessage("/sendmessage")
                        .setDescription("Send messages via Proxy")
                        .setPermission("xxarox.wdtools.message")
                        .build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage("Missing arguments!");
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
                return false;
            }
        }
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(strings[0]);
        if (player == null) {
            commandSender.sendMessage("Player not found!");
            return false;
        }
        StringJoiner jibbit = new StringJoiner(" ");

        for (String string : strings) {
            if (!strings[0].equals(string)) {
                jibbit.add(string);
            }
        }
        player.sendMessage(jibbit.toString());
        return false;
    }
}
