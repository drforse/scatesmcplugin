package com.drforse.skatesplugin;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventListener implements Listener {
    private static final float SPEED_UP_VALUE = 0.2F;
    private final Map<Player, Float> speedUpPlayers = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        float neededSpeedUp = 0f;
        if (isPlayerOnIce(player) && isPlayerEquippedWithSkates(player)) {
            neededSpeedUp = SPEED_UP_VALUE;
        }

        // disable speed up when not on the ice
        float currentSpeedUp = Optional.ofNullable(speedUpPlayers.get(player)).orElse(0f);
        if (currentSpeedUp == neededSpeedUp) {
            return;
        }
        setSpeedUp(player, neededSpeedUp);
    }

    private boolean isPlayerOnIce(Player player) {
        Material blockMaterial = player
                .getLocation()
                .getBlock()
                .getRelative(BlockFace.DOWN)
                .getType();

        return blockMaterial == Material.ICE ||
                blockMaterial == Material.FROSTED_ICE ||
                blockMaterial == Material.BLUE_ICE ||
                blockMaterial == Material.PACKED_ICE;
    }

    private boolean isPlayerEquippedWithSkates(Player player) {
        return Optional.ofNullable(player.getInventory().getBoots())
                .map(boots -> boots.isSimilar(SkatesPlugin.createSkates()))
                .orElse(false);
    }

    private void setSpeedUp(Player player, Float value){
        float currentSpeedUp = Optional.ofNullable(speedUpPlayers.get(player)).orElse(0f);
        float speedUpToAdd = value - currentSpeedUp;
        if (speedUpToAdd == 0) {
            return;
        }

        float newWalkSpeed = speedUpToAdd + player.getWalkSpeed();
        if (newWalkSpeed > SpeedLimits.MAX_SPEED) {
            speedUpToAdd = SpeedLimits.MAX_SPEED - player.getWalkSpeed();
        } else if (newWalkSpeed < SpeedLimits.MIN_SPEED) {
            speedUpToAdd = -(SpeedLimits.MIN_SPEED - player.getWalkSpeed());
        }
        newWalkSpeed = speedUpToAdd + player.getWalkSpeed();

        player.setWalkSpeed(newWalkSpeed);
        if (currentSpeedUp + speedUpToAdd == 0f) {
            speedUpPlayers.remove(player);
        } else {
            speedUpPlayers.put(player, currentSpeedUp + speedUpToAdd);
        }
    }
}
