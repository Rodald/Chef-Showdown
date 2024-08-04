package net.rodald.chef_showdown.customer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;

import java.util.*;

public class Order implements Listener {
    private final static int MAX_ORDER_POINTS = 8;
    private static JavaPlugin plugin;
    private static final Map<Material, Integer> orderMap = new HashMap<>();
    static {
        orderMap.put(Material.GOLDEN_APPLE, 4);
        orderMap.put(Material.BAKED_POTATO, 2);
        orderMap.put(Material.COOKED_COD, 3);
        orderMap.put(Material.COD, 3);
        orderMap.put(Material.DRIED_KELP, 3);
    }
    private static final String ORDER_METADATA_KEY = "orderList";

    public Order(JavaPlugin plugin) {
        Order.plugin = plugin;
    }

    public static Material[] generateDisplay(Entity entity) {
        Material[] randomOrder = generateRandomOrder();
        entity.setMetadata(ORDER_METADATA_KEY, new FixedMetadataValue(plugin, randomOrder));
        generateDisplay(entity.getLocation().clone().add(.25, entity.getHeight() + .6, 0), randomOrder);
        return randomOrder;
    }
    public static void generateDisplay(Location location, Material[] order) {
        World world = location.getWorld();
        ItemDisplay speechBubble = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);

        speechBubble.setItemStack(new ItemStack(Material.SMALL_AMETHYST_BUD));

        for (int i = 0; i < order.length; i++) {
            generateItem(location.clone().add(i*.75 - .05, 0, .06), order[i]);
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
        } else if (material == Material.BEEF || material == Material.COOKED_BEEF || material == Material.COAL) {
            orderTransformation.getRightRotation().set(0.13f, 0.02f, -0.16f,0.98f );
        } else if (material == Material.DRIED_KELP) {
            orderTransformation.getScale().set(.75f, .75f, .062f);
            orderTransformation.getRightRotation().set(0.10f, 0.03f, 0.01f,0.99f );
            orderTransformation.getTranslation().add(0.0f, 0.25f, 0.0f);
        } else if (material == Material.BAKED_POTATO || material == Material.COD || material == Material.COOKED_COD) {
            orderTransformation.getLeftRotation().set(0.067031115f, 0.7039258f, 0.7038698f, 0.06754982f);
            orderTransformation.getScale().set(.8, 0.062, .8);
            orderTransformation.getTranslation().add(0.0f, 0.08f, 0.01f);
        } else {
            orderTransformation.getRightRotation().set(0, 1, 0, 0);
            orderTransformation.getScale().set(.55f, .55f, .062f);
            orderTransformation.getTranslation().add(0.0f, 0.0625f, 0.0f);
        }
        order.setTransformation(orderTransformation);
    }

    private static Material[] generateRandomOrder() {
        int randomOrderValue = 0;
        List orderList = new ArrayList<Material>();

        Random random = new Random();
        while (randomOrderValue < MAX_ORDER_POINTS) {
            List<Material> keysArray = new ArrayList<Material>(orderMap.keySet());
            Material randomOrder = keysArray.get(random.nextInt(keysArray.size()));
            int orderValue = orderMap.get(randomOrder);
            if (randomOrderValue + orderValue >= MAX_ORDER_POINTS) {
                break;
            }
            randomOrderValue += orderValue;
            orderList.add(randomOrder);
        }
        return (Material[]) orderList.toArray(new Material[orderList.size()]);
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        Material itemType = itemInHand.getType();

        if (entity.hasMetadata(ORDER_METADATA_KEY)) {
            List<MetadataValue> metadataValues = entity.getMetadata(ORDER_METADATA_KEY);
            Material[] orderList = (Material[]) metadataValues.get(0).value();

            List<Material> orderListMutable = new ArrayList<>(Arrays.asList(orderList));

            if (orderListMutable.contains(itemType)) {
                orderListMutable.remove(itemType);

                orderList = orderListMutable.toArray(new Material[0]);

                if (orderList.length == 0) {
                    player.sendMessage("All items collected!");
                    player.sendMessage("World:" + player.getWorld().getName());
                    new NPC(plugin).walkNPC((LivingEntity) entity, NPC.OUTSIDE);
                } else {
                    entity.setMetadata(ORDER_METADATA_KEY, new FixedMetadataValue(plugin, orderList));
                }

                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
        }
    }
}
