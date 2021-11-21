package de.xxarox.wdtools.command;

import de.xxarox.wdtools.manager.WhitelistManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;

public class WhitelistCommand extends Command {
    public WhitelistCommand(String name) {
        super(name,
                CommandSettings.builder()
                        .setUsageMessage("/" + name + " <list|<add|remove> <playerName>|on|off>")
                        .setDescription("Whitelist command")
                        .setAliases(new String[]{"wdwl"})
                        .build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (
                strings.length == 1 && strings[0].toLowerCase().equals("list")
                && (commandSender.hasPermission("xxarox.wdtools.whitelist") || commandSender.hasPermission("xxarox.wdtools.whitelist.list"))
        ) {
            commandSender.sendMessage("Whitelist: " + WhitelistManager.getAll());
            return true;
        } else if (
                strings.length == 1 && strings[0].toLowerCase().equals("on")
                && (commandSender.hasPermission("xxarox.wdtools.whitelist") || commandSender.hasPermission("xxarox.wdtools.whitelist.enable"))
        ) {
            if (!WhitelistManager.isEnabled()) {
                WhitelistManager.setEnabled(true);
                commandSender.sendMessage("Whitelist enabled");
            } else {
                commandSender.sendMessage("Whitelist is already enabled");
            }
            return true;
        } else if (
                strings.length == 1 && strings[0].toLowerCase().equals("off")
                && (commandSender.hasPermission("xxarox.wdtools.whitelist") || commandSender.hasPermission("xxarox.wdtools.whitelist.enable"))
        ) {
            if (WhitelistManager.isEnabled()) {
                WhitelistManager.setEnabled(false);
                commandSender.sendMessage("Whitelist disabled");
            } else {
                commandSender.sendMessage("Whitelist is already disabled");
            }
            return true;
        } else if (
                strings.length == 2 && strings[0].toLowerCase().equals("add")
                && (commandSender.hasPermission("xxarox.wdtools.whitelist") || commandSender.hasPermission("xxarox.wdtools.whitelist.manage"))
        ) {
            if (!WhitelistManager.isWhitelisted(strings[1])) {
                commandSender.sendMessage("Added " + strings[1].toLowerCase() + " to whitelist.");
                WhitelistManager.add(strings[1]);
            } else {
                commandSender.sendMessage(strings[1].toLowerCase() + " is already whitelisted.");
            }
            return true;
        } else if (
                strings.length == 2 && strings[0].toLowerCase().equals("remove")
                && (commandSender.hasPermission("xxarox.wdtools.whitelist") || commandSender.hasPermission("xxarox.wdtools.whitelist.manage"))
        ) {
            if (WhitelistManager.isWhitelisted(strings[1])) {
                commandSender.sendMessage("Removed " + strings[1].toLowerCase() + " from whitelist.");
                WhitelistManager.remove(strings[1]);
            } else {
                commandSender.sendMessage(strings[1].toLowerCase() + " isn't whitelisted.");
            }
            return true;
        }
        return false;
    }
}
