package de.xxarox.wdtools.manager;

import de.xxarox.wdtools.WDTools;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerManager {
    private static ArrayList<String> blockedIps = new ArrayList<>();
    private static HashMap<String, Integer> connections = new HashMap<>();

    public static void checkForVPN(ProxiedPlayer proxiedPlayer){
        if (blockedIps.contains(proxiedPlayer.getAddress().getHostName())) {
            proxiedPlayer.disconnect(WDTools.getMessage("vpn-detected"));
            return;
        }
        ProxyServer.getInstance().getScheduler().scheduleAsync(() -> {
            //TODO: VPN stuff by @Steamarino
            //proxiedPlayer.disconnect(WDTools.getMessage("vpn-detected"));
        });
    }

    public static void setBlockedIps(ArrayList<String> blockedIps) {
        PlayerManager.blockedIps = blockedIps;
    }

    public static void checkConnectionLimit(ProxiedPlayer proxiedPlayer, @NonNull Boolean quit){
        if (!connections.containsKey(proxiedPlayer.getAddress().getHostName())) {
            connections.put(proxiedPlayer.getAddress().getHostName(), 1);
            return;
        }
        if (connections.get(proxiedPlayer.getAddress().getHostName()) == 4) {
            proxiedPlayer.disconnect(WDTools.getMessage("connection-limit"));
            return;
        }
        if (connections.containsKey(proxiedPlayer.getAddress().getHostName())) {
            if (quit && connections.get(proxiedPlayer.getAddress().getHostName()) == 1) {
                connections.remove(proxiedPlayer.getAddress().getHostName());
                return;
            }
            connections.replace(proxiedPlayer.getAddress().getHostName(), connections.get(proxiedPlayer.getAddress().getHostName()) +(quit ? -1 : 1));
        } else {
            connections.put(proxiedPlayer.getAddress().getHostName(), 1);
        }
    }
}
