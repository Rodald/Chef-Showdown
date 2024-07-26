package net.rodald.chef_showdown.Customers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class NPC {
    private JavaPlugin plugin;

    public NPC(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void spawnNPC(Location start, Location end) {
        new BukkitRunnable() {
            private final List<Location> path = findPath(start, end);
            private int pathIndex = 0;

            @Override
            public void run() {
                if (pathIndex >= path.size()) {
                    this.cancel();
                    return;
                }

                Location nextLocation = path.get(pathIndex);
                nextLocation.getBlock().setType(Material.LIME_CONCRETE); // Set block to Concrete
                pathIndex++;
            }
        }.runTaskTimer(plugin, 0, 2); // Move every 2 ticks (0.1 seconds)
    }

    private List<Location> findPath(Location start, Location end) {
        List<Location> path = new ArrayList<>();

        int startX = start.getBlockX();
        int startZ = start.getBlockZ();
        int endX = end.getBlockX();
        int endZ = end.getBlockZ();
        int y = start.getBlockY();

        int dx = Math.abs(endX - startX);
        int dz = Math.abs(endZ - startZ);

        int sx = (startX < endX) ? 1 : -1;
        int sz = (startZ < endZ) ? 1 : -1;

        int err = dx - dz;

        while (true) {
            path.add(new Location(start.getWorld(), startX, y, startZ));

            if (startX == endX && startZ == endZ) {
                break;
            }

            int e2 = 2 * err;

            if (e2 > -dz) {
                err -= dz;
                startX += sx;
            }

            if (e2 < dx) {
                err += dx;
                startZ += sz;
            }
        }

        return path;
    }
}