package net.rodald.chef_showdown.interactions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
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

    public static final BlockData DEEP_FRYER;
    static {

        Material terracotta = Material.YELLOW_GLAZED_TERRACOTTA;
        DEEP_FRYER = terracotta.createBlockData();
        Directional rotation = (Directional) DEEP_FRYER;
        rotation.setFacing(BlockFace.SOUTH);
    }
    public final static BlockData FRYING_DEEP_FRYER;
    static {
        Material terracotta = Material.RED_GLAZED_TERRACOTTA;
        FRYING_DEEP_FRYER = terracotta.createBlockData();
        Directional rotation = (Directional) DEEP_FRYER;
        rotation.setFacing(BlockFace.SOUTH);
    }
    public final static BlockData FINISHED_DEEP_FRYER;
    static {

        Material terracotta = Material.ORANGE_GLAZED_TERRACOTTA;
        FINISHED_DEEP_FRYER = terracotta.createBlockData();
        Directional rotation = (Directional) DEEP_FRYER;
        rotation.setFacing(BlockFace.SOUTH);
    }
    public final static BlockData BURNED_DEEP_FRYER;
    static {

        Material terracotta = Material.BLACK_GLAZED_TERRACOTTA;
        BURNED_DEEP_FRYER = terracotta.createBlockData();
        Directional rotation = (Directional) DEEP_FRYER;
        rotation.setFacing(BlockFace.SOUTH);
    }
    public final static Material BURGER = Material.BAMBOO_TRAPDOOR;
    public final static Material SALAD = Material.WARPED_TRAPDOOR;
    public final static Material DRINK_DISPENSER = Material.BROWN_STAINED_GLASS;
    public final static Material DRINK_DISPENSER_READY = Material.BROWN_STAINED_GLASS; // add drinkDispenser ready
    public final static Material TRASH_CAN = Material.CAULDRON;
    public final static Material KETCHUP = Material.REDSTONE_TORCH;
    public final static Material MAYO = Material.TORCH;
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
                player.sendMessage("test");
                Material clickedBlockType = clickedBlock.getType();
                BlockData clickedBlockData = clickedBlock.getBlockData();
                if (clickedBlockType == Material.IRON_DOOR) {
                    clickedBlock = player.getWorld().getBlockAt(clickedBlock.getLocation().clone().subtract(0, 0, 1));
                    clickedBlockType = clickedBlock.getType();
                    player.sendMessage(clickedBlockType.toString());
                }
                if (clickedBlockType == TABLET) {
                    Tablet.swapItem(player, clickedBlock);
                    event.setCancelled(true);
                } else if (clickedBlockType == MEAT) {
                    MeatBoard.givePlayerMeat(player);
                    event.setCancelled(true);
                } else if (clickedBlockType == GRILL) {
                    Grill.placeMeat(player, clickedBlock);
                } else if (clickedBlockType == DEEP_FRYER.getMaterial()) {
                    DeepFryer.startFries(clickedBlock);
                } else if (clickedBlockType == FINISHED_DEEP_FRYER.getMaterial()) {
                    DeepFryer.takeFries(player, clickedBlock);
                } else if (clickedBlockType == BURNED_DEEP_FRYER.getMaterial()) {
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
                    DrinkDispenser.startDrink(clickedBlock);
                    event.setCancelled(true);
                } else if (clickedBlockType == DRINK_DISPENSER_READY) {
                    DrinkDispenser.takeDrink(player, clickedBlock);
                    event.setCancelled(true);
                } else if (clickedBlockType == TRASH_CAN) {
                    TrashCan.deleteItem(player);
                    event.setCancelled(true);
                } else if (clickedBlockType == KETCHUP) {
                    Sauces.addKetchup(player);
                    event.setCancelled(true);
                } else if (clickedBlockType == MAYO) {
                    Sauces.addMayo(player);
                    event.setCancelled(true);
                }

            }
        }
    }
}
