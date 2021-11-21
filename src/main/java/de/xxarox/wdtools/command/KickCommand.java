package de.xxarox.wdtools.command;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

public class KickCommand extends Command {
    public KickCommand(String name) {
        super(name,
                CommandSettings.builder()
                        .setUsageMessage("/wdkick <playerName|*> <reason>")
                        .setDescription("Kick players from proxy")
                        .build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.isPlayer()) {
            return true;
        }
        if (strings.length > 2 && strings[0].toLowerCase().equals("*")) {
            for (Map.Entry<UUID, ProxiedPlayer> entry: ProxyServer.getInstance().getPlayers().entrySet()) {
                StringJoiner stringJoiner = new StringJoiner(" ");
                for (String string : strings) {
                    if (!strings[0].equals(string)) {
                        stringJoiner.add(string);
                    }
                }
                entry.getValue().disconnect(stringJoiner.toString());
            }
            return true;
        } else if (strings.length > 2) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (String string : strings) {
                if (!strings[0].equals(string)) {
                    stringJoiner.add(string);
                }
            }
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(strings[0]);
            if (player != null) {
                String reason = stringJoiner.toString();
                player.disconnect(reason);
                commandSender.sendMessage(player.getName() + " was kicked. Reason: " + reason);
            }
            return true;
        }
        return false;
    }
}
