package net.rodald.chef_showdown.interactions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Optional;

public class Tablet {

    public static void swapItem(Player player, Block tablet) {
        ItemStack playerItem = player.getInventory().getItemInMainHand();

        Optional<Entity> itemFrameEntity = tablet.getWorld().getNearbyEntities(tablet.getLocation(), 1, 1, 1).stream()
                .filter(entity -> entity instanceof ItemFrame)
                .findFirst();
        Location tabletLocation = tablet.getLocation().toCenterLocation();
        ItemFrame nearestItemFrame = player.getWorld().getEntities().stream()

                .filter(entity -> entity instanceof ItemFrame && !entity.equals(player))
                .map(entity -> (ItemFrame) entity)
                .min((entity1, entity2) -> Double.compare(tabletLocation.distance(entity1.getLocation()), tabletLocation.distance(entity2.getLocation())))
                .orElse(null);

            if (nearestItemFrame != null) {
                ItemStack frameItem = nearestItemFrame.getItem();
                player.sendMessage("You have " + playerItem.toString());
                nearestItemFrame.setItem(playerItem);
                player.getInventory().setItemInMainHand(frameItem);
            } else {
                player.sendMessage("No item frame found on the tablet.");
            }
    }

}
