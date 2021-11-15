package de.xxarox.wdtools;

import de.xxarox.wdtools.command.*;
import de.xxarox.wdtools.handler.JoinLobbyHandler;
import de.xxarox.wdtools.handler.ReconnectLobbyHandler;
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
        this.getProxy().setJoinHandler(new JoinLobbyHandler());
        this.getProxy().setReconnectHandler(new ReconnectLobbyHandler());

        // Commands
        getProxy().getCommandMap().registerCommand(new ServerManagerCommand());
        getProxy().getCommandMap().registerCommand(new SendMessageCommand("sendmessage"));
        getProxy().getCommandMap().registerCommand(new TransferPlayerCommand("transfer"));
        getProxy().getCommandMap().registerCommand(new HubCommand("hub"));
        getProxy().getCommandMap().registerCommand(new LobbyCommand("lobby"));

        getLogger().info("WDTools enabled");
    }

    public static WDTools getInstance() {
        return INSTANCE;
    }

    public static String getPrefix() {
        return PREFIX;
    }
}
