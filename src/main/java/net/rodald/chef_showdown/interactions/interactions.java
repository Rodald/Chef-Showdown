package net.rodald.chef_showdown.interactions;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class interactions implements Listener {

    public static Material tablet;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the action is right-clicking a block
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Check if the clicked block is of a specific type
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.GOLD_BLOCK) {
                // Perform your logic here
                event.getPlayer().sendMessage("You clicked on a Gold Block!");
            }
        }
    }

}
