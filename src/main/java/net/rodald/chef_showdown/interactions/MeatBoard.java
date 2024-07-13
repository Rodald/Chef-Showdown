package net.rodald.chef_showdown.interactions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MeatBoard {

    public static void givePlayerMeat(Player player) {
        if (Interactions.isMainSlotEmpty(player)) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.BEEF));
        }
    }
}
