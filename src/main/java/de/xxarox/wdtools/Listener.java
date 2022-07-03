/*
 * Copyright (c) Jan Sohn / xxAROX
 * All rights reserved.
 * I don't want anyone to use my source code without permission.
 */

package de.xxarox.wdtools;

import com.nukkitx.protocol.bedrock.packet.ScriptCustomEventPacket;
import de.xxarox.wdtools.manager.PlayerManager;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.event.defaults.PreTransferEvent;
import dev.waterdog.waterdogpe.event.defaults.TransferCompleteEvent;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.network.session.DownstreamClient;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class Listener {
    public static void PlayerLoginEvent(PlayerLoginEvent event){
        PlayerManager.checkForVPN(event.getPlayer());
        PlayerManager.checkConnectionLimit(event.getPlayer(), false);
    }

    public static void PlayerDisconnectEvent(PlayerDisconnectEvent event){
        PlayerManager.checkConnectionLimit(event.getPlayer(), true);
    }

    public static void PreTransferEvent(PreTransferEvent event){
        ProxiedPlayer proxiedPlayer = event.getPlayer();
        ServerInfo targetServer = event.getTargetServer();

        if (proxiedPlayer.getDownstream() != null && proxiedPlayer.getDownstream().getServerInfo() == targetServer) {
            event.setCancelled();
            return;
        }
        DownstreamClient oldPendingConnection = proxiedPlayer.getPendingConnection();
        if (oldPendingConnection != null) {
            if (oldPendingConnection.getServerInfo() == targetServer) {
                event.setCancelled();
                return;
            }
        }
    }
    public static void TransferCompleteEvent(TransferCompleteEvent event){
        ProxiedPlayer proxiedPlayer = event.getPlayer();

        /* TEST FOR IP_ADDRESS TRACKING */
        ScriptCustomEventPacket packet = new ScriptCustomEventPacket();
        packet.setEventName("dataFromProxy");
        packet.setData("{" +
                "\"address\": \"" + proxiedPlayer.getLoginData().getAddress().getHostName() + "\"," +
                "\"isXboxAuthed\": \"" + proxiedPlayer.getLoginData().isXboxAuthed() + "\"," +
                "\"uuid\": \"" + proxiedPlayer.getLoginData().getUuid().toString() + "\"," +
                "\"xuid\": \"" + proxiedPlayer.getLoginData().getXuid() + "\"" +
                "}");
        proxiedPlayer.sendPacketImmediately(packet);
        /* TEST END */
    }
}
