package com.drforse.scatesplugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class Scates implements Listener {
    private final Logger logger;
    private final Map<UUID, Float> speedUpPlayers = new HashMap<>();

    public Scates(JavaPlugin plugin) {
        this.logger = plugin.getLogger();
    }

    @EventHandler
    public void playerMoveHandler(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        logger.config(player.getDisplayName() + " moved");
        if (playerIsOnIce(player) && playerIsEquippedWithScates(player)){
            speedUpPlayerIfNeeded(player);
            return;
        }

        Float currentSpeedUp = speedUpPlayers.get(player.getUniqueId());
        if (currentSpeedUp == null || currentSpeedUp <= 0){
            logger.config("no speed up currently present");
            return;
        }
        logger.config("current speed is " + player.getWalkSpeed());
        logger.config("removing speed up");
        player.setWalkSpeed(player.getWalkSpeed() - currentSpeedUp);
        speedUpPlayers.put(player.getUniqueId(), 0F);
        logger.config("new speed is " + player.getWalkSpeed());
    }

    private boolean playerIsOnIce(Player player) {
        Block onBlock = player
                .getLocation()
                .getBlock()
                .getRelative(BlockFace.DOWN);

        return onBlock.getType() == Material.ICE;
    }

    private boolean playerIsEquippedWithScates(Player player) {
        ItemStack currentBoots = player.getInventory().getBoots();
        if (currentBoots == null) {
            return false;
        }
        ItemMeta itemMeta = currentBoots.getItemMeta();
        if (itemMeta == null) {
            return false;
        }

        return "Кожаные коньки".equals(itemMeta.getDisplayName());
    }

    public void speedUpPlayerIfNeeded(Player player) {
        logger.config("speeding up...");
        Float currentSpeedUp = speedUpPlayers.get(player.getUniqueId());
        if (currentSpeedUp == null) {
            currentSpeedUp = 0F;
        }
        logger.config("current speed up is " + currentSpeedUp);
        float speedUpToAdd = 0.2F - currentSpeedUp;
        logger.config("speed up to add is " + speedUpToAdd);
        if (speedUpToAdd <= 0) {
            return;
        }

        logger.config("current speed is " + player.getWalkSpeed());
        logger.config(String.format("setting speed %s", 0.2F + player.getWalkSpeed()));
        player.setWalkSpeed(0.2F + player.getWalkSpeed());
        logger.config(String.format("storing speedUp %s", currentSpeedUp + speedUpToAdd));
        speedUpPlayers.put(player.getUniqueId(), currentSpeedUp + speedUpToAdd);
    }
}
