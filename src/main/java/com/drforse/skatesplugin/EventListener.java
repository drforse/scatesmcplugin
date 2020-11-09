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
        if (isPlayerOnIce(player) && playerIsEquippedWithSkates(player)) {
            speedUpPlayerIfNeeded(player);
            return;
        }

        // disable speed up when not on the ice
        float currentSpeedUp = Optional.ofNullable(speedUpPlayers.get(player)).orElse(0f);
        if (currentSpeedUp <= 0) {
            return;
        }
        player.setWalkSpeed(player.getWalkSpeed() - currentSpeedUp);
        speedUpPlayers.remove(player);
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

    private boolean playerIsEquippedWithSkates(Player player) {
        return Optional.ofNullable(player.getInventory().getBoots())
                .map(boots -> boots.isSimilar(SkatesPlugin.createSkates()))
                .orElse(false);
    }

    private void speedUpPlayerIfNeeded(Player player) {
        float currentSpeedUp = Optional.ofNullable(speedUpPlayers.get(player)).orElse(0f);
        float speedUpToAdd = SPEED_UP_VALUE - currentSpeedUp;
        if (speedUpToAdd <= 0) {
            return;
        }

        player.setWalkSpeed(SPEED_UP_VALUE + player.getWalkSpeed());
        speedUpPlayers.put(player, currentSpeedUp + speedUpToAdd);
    }
}
