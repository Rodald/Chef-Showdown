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
        switch (orderSize) {
            case 1: speechBubble.setItemStack(new ItemStack(Material.SMALL_AMETHYST_BUD));
                break;
            case 2: speechBubble.setItemStack(new ItemStack(Material.MEDIUM_AMETHYST_BUD));
                break;
            case 3: speechBubble.setItemStack(new ItemStack(Material.LARGE_AMETHYST_BUD));
                break;
            case 4: speechBubble.setItemStack(new ItemStack(Material.AMETHYST_CLUSTER));
                break;
        }
        Transformation speechBubbleTransformation = speechBubble.getTransformation();
        generateItem(location.add(0, 0, 0.1), Material.GOLDEN_APPLE);
    }

    private static void generateItem(Location location, Material material) {
        World world = location.getWorld();
        ItemDisplay order = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        Transformation orderTransformation = order.getTransformation();
        order.setItemStack(new ItemStack(material));
        orderTransformation.getScale().set(1, 1, 0.062);
        orderTransformation.getRightRotation().set(0.3957091f, 0, -0.2706716f, 0.8775826f );
        order.setTransformation(orderTransformation);
    }
}
