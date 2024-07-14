package net.rodald.chef_showdown.interactions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TrashCan {
    public static void deleteItem(Player player) {
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
    }
}
