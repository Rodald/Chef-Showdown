package net.rodald.chef_showdown.interactions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Interactions implements Listener {

    public final static Material TABLET = Material.DEAD_HORN_CORAL_FAN;
    public final static Material MEAT = Material.CHERRY_TRAPDOOR;
    public final static Material GRILL = Material.POLISHED_DEEPSLATE_SLAB;
    public final static Material GRILL_RAW_MEAT = Material.PURPUR_SLAB;
    public final static Material GRILL_COOKED_MEAT = Material.MUD_BRICK_SLAB;
    public final static Material GRILL_BURNED_MEAT = Material.BLACKSTONE_SLAB;

    public final static Material DEEP_FRYER = Material.YELLOW_STAINED_GLASS;
    public final static Material FRYING_DEEP_FRYER = Material.RED_STAINED_GLASS;
    public final static Material FINISHED_DEEP_FRYER = Material.ORANGE_STAINED_GLASS;
    public final static Material BURNED_DEEP_FRYER = Material.BLACK_STAINED_GLASS;

    public final static Material BURGER = Material.BAMBOO_TRAPDOOR;
    public final static Material SALAD = Material.WARPED_TRAPDOOR;
    public final static Material DRINK_DISPENSER = Material.BROWN_STAINED_GLASS;
    public final static Material TRASH_CAN = Material.CAULDRON;
    public static boolean isMainSlotEmpty(Player player) {
        return player.getInventory().getItemInMainHand().getType() == Material.AIR;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getHand() != EquipmentSlot.HAND) return;
        // Check if the action is right-clicking a block
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Check if the clicked block is of a specific type
            if (event.getClickedBlock() != null) {
                Player player = event.getPlayer();
                Block clickedBlock = event.getClickedBlock();
                int test = 2;
                test = 4;
                Material clickedBlockType = clickedBlock.getType();
                if (clickedBlockType == Material.IRON_DOOR) {
                    clickedBlock = player.getWorld().getBlockAt(clickedBlock.getLocation().clone().subtract(0, 0, 1));
                    clickedBlockType = clickedBlock.getType();
                    player.sendMessage("iron_door");
                    player.sendMessage(clickedBlockType.toString());
                }
                if (clickedBlockType == TABLET) {
                    Tablet.swapItem(player, clickedBlock);
                    event.setCancelled(true);
                } else if (clickedBlockType == MEAT) {
                    MeatBoard.givePlayerMeat(player);
                    player.sendMessage("meat");
                    event.setCancelled(true);
                } else if (clickedBlockType == GRILL) {
                    player.sendMessage("grill");
                    Grill.placeMeat(player, clickedBlock);
                } else if (clickedBlockType == DEEP_FRYER) {
                    DeepFryer.startFries(clickedBlock);
                } else if (clickedBlockType == FINISHED_DEEP_FRYER) {
                    DeepFryer.takeFries(player, clickedBlock);
                } else if (clickedBlockType == BURNED_DEEP_FRYER) {
                    DeepFryer.takeBurnedFries(player, clickedBlock);
                } else if (clickedBlockType == GRILL_COOKED_MEAT) {
                    Grill.takeCookedMeat(player, clickedBlock);
                } else if (clickedBlockType == GRILL_BURNED_MEAT) {
                    Grill.takeBurnedMeat(player, clickedBlock);
                } else if (clickedBlockType == BURGER) {
                    BreadBoard.givePlayerBread(player);
                    event.setCancelled(true);
                } else if (clickedBlockType == SALAD) {
                    SaladBoard.givePlayerSalad(player);
                    event.setCancelled(true);
                } else if (clickedBlockType == DRINK_DISPENSER) {

                } else if (clickedBlockType == TRASH_CAN) {
                    TrashCan.deleteItem(player);
                    event.setCancelled(true);
                }

            }
        }
    }
}
