package com.drforse.skatesplugin;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SkatesPlugin extends JavaPlugin {
    public static ItemStack createSkates() {
        ItemStack skates = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta skatesMeta = (LeatherArmorMeta) skates.getItemMeta();
        skatesMeta.setDisplayName("Кожаные коньки");
        skatesMeta.setColor(Color.BLUE);
        skates.setItemMeta(skatesMeta);
        return skates;
    }

    @Override
    public void onEnable() {

        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(this, "skates"), createSkates());
        shapedRecipe.shape("~ ~", " B ", " V ");
        shapedRecipe.setIngredient('~', Material.STRING);
        shapedRecipe.setIngredient('B', Material.LEATHER_BOOTS);
        shapedRecipe.setIngredient('V', Material.SHEARS);

        getServer().addRecipe(shapedRecipe);
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        Objects.requireNonNull(getCommand("setWalkSpeed")).setExecutor(new SetWalkSpeedCommand());
    }
}
