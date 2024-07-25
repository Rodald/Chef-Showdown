package net.rodald.chef_showdown.gui;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class LoadingBar {
    private JavaPlugin plugin;
    private static final String FONT_NAME = "minecraft:loading_bar"; // Hier der Name der Schriftart

    public LoadingBar(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startLoadingBar(Player player) {
        new BukkitRunnable() {
            int percent = 0;
            Random random = new Random();

            @Override
            public void run() {
                if (percent > 100) {
                    this.cancel();
                    return;
                }

                /*// Simulate random stops in the loading progress
                if (random.nextInt(10) < 2) { // 20% chance to "stick"
                    return;
                }*/

                // Dynamically construct the message based on the percent
                String message = getProgressChar(percent);

                // Create the TextComponent with the message and custom font
                TextComponent component = new TextComponent(message);
                component.setFont(FONT_NAME);

                // Send the action bar message to the player
                player.sendMessage(percent + ": " + getProgressChar(percent));
                player.spigot().sendMessage(component);

                percent++;
            }
        }.runTaskTimer(plugin, 0, 2); // Run every 2 ticks (0.1 seconds)
    }

    private String getProgressChar(int percent) {
        // Convert the percent to the corresponding Unicode escape sequence
        return new String(Character.toChars(0xE100 + percent));
    }
}
