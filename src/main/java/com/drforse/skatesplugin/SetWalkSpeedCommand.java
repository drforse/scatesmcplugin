package com.drforse.skatesplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWalkSpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            return false;
        }

        String playerName = args[0];
        Player player = sender.getServer().getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player with name " + playerName + " not found.");
            return true;
        }

        float walkSpeed;
        try {
            walkSpeed = Float.parseFloat(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + args[1] + " cannot be converted to float");
            return false;
        }

        // max walkSpeed is 1 and min walkSpeed is -1
        if (walkSpeed > 1) {
            walkSpeed = 1;
        } else if (walkSpeed < -1) {
            walkSpeed = -1;
        }

        player.setWalkSpeed(walkSpeed);
        sender.sendMessage(ChatColor.YELLOW + playerName + "'s walk speed is now " + walkSpeed);

        return true;
    }
}
