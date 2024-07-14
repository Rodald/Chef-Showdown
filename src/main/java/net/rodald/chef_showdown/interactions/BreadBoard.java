package net.rodald.chef_showdown.interactions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BreadBoard {

    public static void givePlayerBread(Player player) {
        if (player.getInventory().getItemInMainHand().getType() == Material.COOKED_BEEF) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.GOLDEN_APPLE));
        }
    }
}
