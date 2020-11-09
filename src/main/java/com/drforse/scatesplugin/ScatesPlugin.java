package com.drforse.scatesplugin;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScatesPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // TODO Insert logic to be performed when the plugin is enabled
        getLogger().info("onEnable has been invoked!");

        Bukkit.getPluginManager().registerEvents(new Scates(this), this);
        ItemStack scates = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta scatesMeta = (LeatherArmorMeta) scates.getItemMeta();
        scatesMeta.setDisplayName("Кожаные коньки");
        scatesMeta.setColor(Color.BLUE);
        scates.setItemMeta(scatesMeta);

        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(this, "scates"), scates);
        shapedRecipe.shape("A A", " B ", " C ");
        shapedRecipe.setIngredient('A', Material.STRING);
        shapedRecipe.setIngredient('B', Material.LEATHER_BOOTS);
        shapedRecipe.setIngredient('C', Material.SHEARS);
        getServer().addRecipe(shapedRecipe);
    }

    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
        getLogger().info("onDisable has been invoked!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
        if (!cmd.getName().equalsIgnoreCase("setwalkspeed")) {
            return false;
        }
        if (args.length != 2) {
            sender.sendMessage("Wrong format! Usage: /command [player] [number]");
            return false;
        }

        String playerName = args[0];
        Player player = getServer().getPlayer(playerName);
        if (player == null) {
            sender.sendMessage("Player with name " + playerName + "not found.");
            return false;
        }

        float walkSpeed;
        try {
            walkSpeed = Float.parseFloat(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(args[1] + " cannot be converted to float");
            return false;
        }

        //max walkSpeed is 1 and min walkSpeed is -1
        if (walkSpeed > 1) {
            walkSpeed = 1;
        }
        if (walkSpeed < -1) {
            walkSpeed = -1;
        }

        player.setWalkSpeed(walkSpeed);
        sender.sendMessage(playerName + "'s walk speed is now " + walkSpeed);

        return true;
    }
}
