package net.rodald.chef_showdown.interactions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DrinkDispenser {
    
    private static final int COOKING_TIME = 200;
    
    private static JavaPlugin plugin;

    public DrinkDispenser(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void startDrink(Block drinkDispenser) {
        drinkDispenser.setType(Material.RED_STAINED_GLASS); // cooking block

        // Start the cooking timer
        new BukkitRunnable() {
            @Override
            public void run() {
                if (drinkDispenser.getType() == Material.RED_STAINED_GLASS) { // cooking block
                    drinkDispenser.setType(Material.ORANGE_STAINED_GLASS); // drink block
                }
            }
        }.runTaskLater(plugin, COOKING_TIME);

    }

    public static void takeDrink(Player player, Block drinkDispenser) {
        if (Interactions.isMainSlotEmpty(player)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.POTION));
            drinkDispenser.setType(Material.YELLOW_STAINED_GLASS); // normal block
        }
    }
}
