package de.xxarox.wdtools.network.serverinfo;

import dev.waterdog.waterdogpe.network.serverinfo.BedrockServerInfo;

import java.net.InetSocketAddress;

public class CloudServerInfo extends BedrockServerInfo {
    String display_name;

    public CloudServerInfo(String serverName, String identifier, InetSocketAddress address, InetSocketAddress publicAddress) {
        super(identifier, address, publicAddress);
        this.display_name = serverName;
    }

    public String getIdentifier() {
        return getServerName();
    }

    public String getDisplayName() {
        return display_name;
    }
}
