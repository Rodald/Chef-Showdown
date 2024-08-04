package net.rodald.chef_showdown.gui;

import com.destroystokyo.paper.Title;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class LoadingBar {
    private JavaPlugin plugin;
    private static final String FONT_NAME = "minecraft:loading_bar";
    private static final int STICK_CHANCE = 30;
    private static final String LOADING_CHARS = "\uE100\uE101\uE102\uE103\uE104\uE105\uE106\uE107\uE108\uE109\uE110\uE111\uE112\uE113\uE114\uE115\uE116\uE117\uE118\uE119\uE120\uE121\uE122\uE123\uE124\uE125\uE126\uE127\uE128\uE129\uE130\uE131\uE132\uE133\uE134\uE135\uE136\uE137\uE138\uE139\uE140\uE141\uE142\uE143\uE144\uE145\uE146\uE147\uE148\uE149\uE150\uE151\uE152\uE153\uE154\uE155\uE156\uE157\uE158\uE159\uE160\uE161\uE162\uE163\uE164\uE165\uE166\uE167\uE168\uE169\uE170\uE171\uE172\uE173\uE174\uE175\uE176\uE177\uE178\uE179\uE180\uE181\uE182\uE183\uE184\uE185\uE186\uE187\uE188\uE189\uE190\uE191\uE192\uE193\uE194\uE195\uE196\uE197\uE198\uE199\uE200";

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

                if (random.nextInt(100) < STICK_CHANCE) {
                    return;
                }

                String message = getProgressChar(percent);

                TextComponent component = new TextComponent(message);
                TextComponent background = new TextComponent("\uE014");
                component.setFont(FONT_NAME);
                //background.setFont(BACKGROUND_FONT_NAME);

                //player.sendTitle(" ", String.valueOf(component.getText()), 1, 1, 1);
                player.sendTitle(new Title(component, background, 0, 20, 40));

                percent++;
            }
        }.runTaskTimer(plugin, 0, 2);
    }

    private String getProgressChar(int percent) {
        return String.valueOf(LOADING_CHARS.charAt(percent));
    }
}