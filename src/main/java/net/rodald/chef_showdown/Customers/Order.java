package net.rodald.chef_showdown.Customers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class Order {
    public static void generateDisplay(Location location, int orderSize, Material[] order) {
        if (orderSize <= 0 || orderSize >= 5) {
            return;
        }
        World world = location.getWorld();
        ItemDisplay speechBubble = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);

        speechBubble.setItemStack(new ItemStack(Material.SMALL_AMETHYST_BUD));

        for (int i = 0; i < order.length; i++) {
            generateItem(location.clone().add(i*0.75, 0, 0.06), order[i]);
            if (i > 0 && i < order.length) {
                ItemDisplay speechBubbleMiddle = (ItemDisplay) world.spawnEntity(location.clone().add(i*.75, 0, 0), EntityType.ITEM_DISPLAY);
                speechBubbleMiddle.setItemStack(new ItemStack(Material.LARGE_AMETHYST_BUD));
            }

        }
        ItemDisplay speechBubbleEnd = (ItemDisplay) world.spawnEntity(location.clone().add(order.length*.75, 0, 0), EntityType.ITEM_DISPLAY);
        speechBubbleEnd.setItemStack(new ItemStack(Material.MEDIUM_AMETHYST_BUD));
    }

    private static void generateItem(Location location, Material material) {
        World world = location.getWorld();
        ItemDisplay order = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        Transformation orderTransformation = order.getTransformation();
        order.setItemStack(new ItemStack(material));
        orderTransformation.getScale().set(1, 1, 0.062);
        if (material == Material.GOLDEN_APPLE) {
            orderTransformation.getRightRotation().set(0.13f, 0.02f, -0.16f,0.98f );
            orderTransformation.getTranslation().add(0.1f, 0.3f, 0.0f);
        } else if (material == Material.BEEF || material == Material.COOKED_BEEF) {
            orderTransformation.getRightRotation().set(0.13f, 0.02f, -0.16f,0.98f );
        } else if (material == Material.DRIED_KELP) {
            orderTransformation.getRightRotation().set(0.10f, 0.03f, 0.01f,0.99f );
            orderTransformation.getTranslation().add(0.0f, 0.3f, 0.0f);
        } else {
            orderTransformation.getScale().set(.55f, .55f, .062f);
            orderTransformation.getTranslation().add(0.0f, 0.0625f, 0.0f);
        }
        order.setTransformation(orderTransformation);
    }
}
