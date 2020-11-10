package com.drforse.skatesplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetWalkSpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            return false;
        }

        String playerName = args[0];
        Player player = sender.getServer().getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player with name " + playerName + " not found.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + playerName + "'s walk speed is now " + player.getWalkSpeed());
        return true;
    }
}
