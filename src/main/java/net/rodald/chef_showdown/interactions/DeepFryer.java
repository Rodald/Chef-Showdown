package net.rodald.chef_showdown.interactions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DeepFryer {
    private static final int COOKING_TIME = 200; // 10 seconds
    private static final int BURNING_TIME = 400; // 20 seconds

    private static JavaPlugin plugin;

    public DeepFryer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void startFries(Block deepFryer) {
        Bukkit.broadcastMessage("stated frying");
        deepFryer.setBlockData(Interactions.FRYING_DEEP_FRYER);

        // Start the cooking timer
        new BukkitRunnable() {
            @Override
            public void run() {
                if(deepFryer.getType() == Interactions.DEEP_FRYER.getMaterial()) {
                    cancel();
                    return;
                }
                if (deepFryer.getType() == Interactions.FRYING_DEEP_FRYER.getMaterial()) {
                    deepFryer.setBlockData(Interactions.FINISHED_DEEP_FRYER);

                    // Start the burning timer
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (deepFryer.getType() == Interactions.FINISHED_DEEP_FRYER.getMaterial()) {
                                deepFryer.setBlockData(Interactions.BURNED_DEEP_FRYER);
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
            deepFryer.setBlockData(Interactions.DEEP_FRYER);
        }
    }

    public static void takeBurnedFries(Player player, Block deepFryer) {
        if (Interactions.isMainSlotEmpty(player)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.POISONOUS_POTATO));
            deepFryer.setBlockData(Interactions.DEEP_FRYER);
        }
    }
}
