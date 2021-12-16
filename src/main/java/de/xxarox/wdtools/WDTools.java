package de.xxarox.wdtools;

import de.xxarox.wdtools.command.*;
import de.xxarox.wdtools.handler.ProxyJoinHandler;
import de.xxarox.wdtools.handler.ReconnectLobbyHandler;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.event.defaults.PreTransferEvent;
import dev.waterdog.waterdogpe.event.defaults.TransferCompleteEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;

public class WDTools extends Plugin {
    private static WDTools INSTANCE = null;
    private static String PREFIX = "§dWDTools §8» §r";


    @Override
    public void onEnable() {
        INSTANCE = this;

        this.saveResource("config.yml", false);
        PREFIX = getConfig().getString("prefix", "§dWDTools §8» §r");


        // Handlers
        this.getProxy().setJoinHandler(new ProxyJoinHandler());
        this.getProxy().setReconnectHandler(new ReconnectLobbyHandler());

        // Listeners
        this.getProxy().getEventManager().subscribe(PreTransferEvent.class, Listener::PreTransferEvent);
        this.getProxy().getEventManager().subscribe(TransferCompleteEvent.class, Listener::TransferCompleteEvent);
        this.getProxy().getEventManager().subscribe(PlayerLoginEvent.class, Listener::PlayerLoginEvent);
        this.getProxy().getEventManager().subscribe(PlayerDisconnectEvent.class, Listener::PlayerDisconnectEvent);

        // Commands
        getProxy().getCommandMap().registerCommand(new ServerManagerCommand());
        getProxy().getCommandMap().registerCommand(new SendMessageCommand("sendmessage"));
        getProxy().getCommandMap().registerCommand(new TransferPlayerCommand("transfer"));
        getProxy().getCommandMap().registerCommand(new HubCommand("hub"));
        getProxy().getCommandMap().registerCommand(new LobbyCommand("lobby"));
        getProxy().getCommandMap().registerCommand(new WhitelistCommand("wdwhitelist"));
        getProxy().getCommandMap().registerCommand(new KickCommand("wdkick"));

        getLogger().info("WDTools enabled");
    }

    public static String getMessage(String key){
        return INSTANCE.getConfig().getString(key, "n/a");
    }

    public static WDTools getInstance() {
        return INSTANCE;
    }

    public static String getPrefix() {
        return PREFIX;
    }
}
