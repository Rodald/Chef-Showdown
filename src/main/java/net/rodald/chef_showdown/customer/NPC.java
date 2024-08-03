package net.rodald.chef_showdown.customer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class NPC implements Listener {
    private JavaPlugin plugin;
    public static final Location OUTSIDE = new Location(Bukkit.getWorld("world"),317, 124, 95);
    public static final Location CASH_REGISTER = new Location(Bukkit.getWorld("world"), 306, 124, 115);

    public NPC(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void walkNPC(LivingEntity entity, Location end) {
        Location start = entity.getLocation();
        // avoids crashes
        if (start.getY() != end.getY()) {
            start.setY(end.getY());
        }
        entity.setAI(true);
        new BukkitRunnable() {
            private final List<Location> path = findPath(start, end);
            private int pathIndex = 0;
            private Location lastLocation = start.clone();
            private double stepSize = 0.2; // Adjusted to make the movement even smoother

            @Override
            public void run() {
                if (pathIndex >= path.size()) {
                    this.cancel();
                    Bukkit.broadcastMessage("Customer reached his destination!");
                    if (end == NPC.OUTSIDE) {
                        entity.remove();
                    } else if (end.distance(CASH_REGISTER) <= 2) {
                        entity.setAI(false);
                        entity.setRotation(0, 0);
                        Order.generateDisplay(entity);
                    }
                    return;
                }

                Location nextLocation = path.get(pathIndex);

                // Calculate the direction vector between the current position and the next path point
                double dx = nextLocation.getX() - lastLocation.getX();
                double dy = nextLocation.getY() - lastLocation.getY();
                double dz = nextLocation.getZ() - lastLocation.getZ();
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (distance > stepSize) {
                    double ratio = stepSize / distance;
                    double newX = lastLocation.getX() + ratio * dx;
                    double newY = lastLocation.getY() + ratio * dy;
                    double newZ = lastLocation.getZ() + ratio * dz;
                    Location stepLocation = new Location(lastLocation.getWorld(), newX, newY, newZ);

                    // Set the Villager's yaw to face the direction of movement
                    float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
                    stepLocation.setYaw(yaw);
                    nextLocation.setYaw(yaw);
                    entity.teleport(stepLocation.clone().add(.5, 0, .5));

                    lastLocation = stepLocation;
                } else {
                    entity.teleport(nextLocation.clone().add(.5, 0, .5));
                    lastLocation = nextLocation;
                    pathIndex++;
                }
            }
        }.runTaskTimer(plugin, 0, 1); // Move every tick (0.05 seconds)
    }

    private List<Location> findPath(Location start, Location end) {
        Set<Location> closedSet = new HashSet<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        Map<Location, Location> cameFrom = new HashMap<>();

        openSet.add(new Node(start, 0, heuristic(start, end)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Location currentLocation = current.location;

            if (currentLocation.equals(end)) {
                return reconstructPath(cameFrom, currentLocation);
            }

            closedSet.add(currentLocation);

            for (Location neighbor : getNeighbors(currentLocation)) {
                if (closedSet.contains(neighbor) || !isWalkable(neighbor)) {
                    continue;
                }

                double tentativeG = current.g + currentLocation.distance(neighbor);

                if (openSet.stream().noneMatch(node -> node.location.equals(neighbor) && tentativeG >= node.g)) {
                    cameFrom.put(neighbor, currentLocation);
                    openSet.add(new Node(neighbor, tentativeG, tentativeG + heuristic(neighbor, end)));
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Location> reconstructPath(Map<Location, Location> cameFrom, Location current) {
        List<Location> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }

        Collections.reverse(path);
        return path;
    }

    private List<Location> getNeighbors(Location location) {
        List<Location> neighbors = new ArrayList<>();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        neighbors.add(new Location(location.getWorld(), x + 1, y, z));
        neighbors.add(new Location(location.getWorld(), x - 1, y, z));
        neighbors.add(new Location(location.getWorld(), x, y, z + 1));
        neighbors.add(new Location(location.getWorld(), x, y, z - 1));
        return neighbors;
    }

    private boolean isWalkable(Location location) {
        Material type = location.getBlock().getType();
        return type == Material.AIR || type == Material.LIGHT || type == Material.BARRIER;
    }

    private double heuristic(Location start, Location end) {
        return start.distance(end);
    }

    private static class Node {
        Location location;
        double g;
        double f;

        Node(Location location, double g, double f) {
            this.location = location;
            this.g = g;
            this.f = f;
        }

        double getF() {
            return f;
        }
    }


    @EventHandler
    public static void EntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity().getType() == EntityType.VILLAGER) {
            event.setCancelled(true);
        }
    }
}