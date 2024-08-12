package net.rodald.chef_showdown.interactions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sauces {

    // adds ketchup
    public static void addKetchup(Player player) {
        if (player.getInventory().getItemInMainHand().getType() == Material.BAKED_POTATO) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.COOKED_COD));
        }
    }

    // adds mayo
    public static void addMayo(Player player) {
        if (player.getInventory().getItemInMainHand().getType() == Material.BAKED_POTATO) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.COD));
        }
    }
}
