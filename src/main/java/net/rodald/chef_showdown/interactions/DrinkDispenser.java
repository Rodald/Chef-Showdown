package net.rodald.chef_showdown.interactions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DrinkDispenser {
    private static final int COOKING_TIME = 200; // 10 seconds
    private static final int BURNING_TIME = 400; // 20 seconds

    private static JavaPlugin plugin;

    public DrinkDispenser(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void startFries(Block deepFryer) {
        deepFryer.setType(Material.RED_STAINED_GLASS);

        // Start the cooking timer
        new BukkitRunnable() {
            @Override
            public void run() {
                if (deepFryer.getType() == Material.RED_STAINED_GLASS) {
                    deepFryer.setType(Material.ORANGE_STAINED_GLASS);

                    // Start the burning timer
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (deepFryer.getType() == Material.ORANGE_STAINED_GLASS) {
                                deepFryer.setType(Material.BLACK_STAINED_GLASS);
                            }
                        }
                    }.runTaskLater(plugin, BURNING_TIME);
                }
            }
        }.runTaskLater(plugin, COOKING_TIME);

    }

    public static void takeFries(Player player, Block deepFryer) {
        if (Interactions.isMainSlotEmpty(player)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.BAKED_POTATO));
            deepFryer.setType(Material.YELLOW_STAINED_GLASS);
        }
    }

    public static void takeBurnedFries(Player player, Block deepFryer) {
        if (Interactions.isMainSlotEmpty(player)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.POISONOUS_POTATO));
            deepFryer.setType(Material.YELLOW_STAINED_GLASS);
        }
    }
}
