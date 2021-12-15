package de.xxarox.wdtools.manager;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.utils.config.Configuration;
import dev.waterdog.waterdogpe.utils.config.YamlConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class WhitelistManager {
    private static final Configuration whitelistConfig = new YamlConfig(new File(ProxyServer.getInstance().getDataPath().toString() + "/whitelist.yml"));
    private static boolean enabled;
    private static String kickMessage;
    private static List<String> list;

    static{
        boolean saveConfig = false;
        if (!whitelistConfig.exists("enabled")) {
            whitelistConfig.setBoolean("enabled", true);
            saveConfig = true;
        }
        if (!whitelistConfig.exists("kick-message")) {
            whitelistConfig.setString("kick-message", "§cServer is under §4maintenance\n§fMore information join our Discord: §9discord.gg/nVayp7KDNZ");
            saveConfig = true;
        }
        if (!whitelistConfig.exists("players")) {
            whitelistConfig.setStringList("players", new ArrayList<>());
            saveConfig = true;
        }
        if (saveConfig) {
            whitelistConfig.save();
        }
        enabled = whitelistConfig.getBoolean("enabled", true);
        kickMessage = whitelistConfig.getString("kick-message", "Server is whitelisted");
        list = whitelistConfig.getStringList("players", new ArrayList<>());
    }

    public static void setEnabled(boolean value){
        enabled = value;
        whitelistConfig.setBoolean("enabled", value);
        whitelistConfig.save();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static String getKickMessage() {
        return kickMessage;
    }

    public static void add(String playerName){
        if (!list.contains(playerName.toLowerCase())) {
            list.add(playerName.toLowerCase());
        }
        whitelistConfig.setStringList("players", list);
        whitelistConfig.save();
    }

    public static void remove(String playerName){
        if (list.contains(playerName.toLowerCase())) {
            list.remove(playerName.toLowerCase());
            whitelistConfig.setStringList("players", list);
            whitelistConfig.save();
        }
    }

    public static String getAll(){
        StringJoiner str = new StringJoiner(", ");
        for (String playerName: list) {
            str.add(playerName.toLowerCase());
        }
        return str.toString();
    }

    public static boolean isWhitelisted(String playerName){
        return list.contains(playerName.toLowerCase());
    }
}
