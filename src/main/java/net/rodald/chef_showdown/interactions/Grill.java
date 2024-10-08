package net.rodald.chef_showdown.interactions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Grill {
    private static final int COOKING_TIME = 200; // 10 seconds (20 ticks per second)
    private static final int BURNING_TIME = 400; // 20 seconds

    private static JavaPlugin plugin;

    public Grill(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void placeMeat(Player player, Block grill) {
        if (player.getInventory().getItemInMainHand().getType() == Material.BEEF) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            grill.setType(Material.PURPUR_SLAB);

            // Start the cooking timer
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (grill.getType() == Material.PURPUR_SLAB) {
                        grill.setType(Material.MUD_BRICK_SLAB);

                        // Start the burning timer
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (grill.getType() == Material.MUD_BRICK_SLAB) {
                                    grill.setType(Material.BLACKSTONE_SLAB);
                                }
                            }
                        }.runTaskLater(plugin, BURNING_TIME);
                    }
                }
            }.runTaskLater(plugin, COOKING_TIME);
        }
    }

    public static void takeCookedMeat(Player player, Block grill) {
        if (Interactions.isMainSlotEmpty(player)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.COOKED_BEEF));
            grill.setType(Material.POLISHED_DEEPSLATE_SLAB);
        }
    }

    public static void takeBurnedMeat(Player player, Block grill) {
        if (Interactions.isMainSlotEmpty(player)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.COAL));
            grill.setType(Material.POLISHED_DEEPSLATE_SLAB);
        }
    }
}
