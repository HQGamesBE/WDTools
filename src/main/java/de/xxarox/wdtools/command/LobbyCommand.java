package de.xxarox.wdtools.command;

import de.xxarox.wdtools.manager.LobbyManager;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class LobbyCommand extends Command {
    public LobbyCommand(String name) {
        super(name,
                CommandSettings.builder()
                        .setUsageMessage("/lobby [<number>|list]")
                        .setDescription("Connect to lobby.")
                        .build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            if (strings.length == 1) {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(strings[0]);
                player.connect(LobbyManager.getRandomLobby());
                return true;
            }
            commandSender.sendMessage("§8§l» §r§cOnly for players.");
            return true;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (strings.length > 0) {
            if (strings[0].equals("list") || strings[0].equals("ls")) {
                player.sendMessage(LobbyManager.getLobbyList());
                return true;
            }
            String lobbyNumber = strings[0];
            if (!player.getServerInfo().getServerName().equals("lobby-" + lobbyNumber)) {
                ServerInfo lobby = LobbyManager.getLobby("lobby-" + lobbyNumber);
                if (lobby != null) {
                    player.connect(lobby);
                    player.sendPopup("§8§l» §r§aConnected to §c" + lobby.getServerName().toUpperCase(), "SUBTITLE.TEXT");
                } else {
                    player.sendMessage("§8§l» §r§cLobby §6" + lobbyNumber + "§c not found.");
                }
            } else {
                player.sendMessage("");
            }
        } else {
            player.sendMessage("§8§l» §r§aConnected to §c" + player.getServerInfo().getServerName().toUpperCase());
        }
        return true;
    }
}
