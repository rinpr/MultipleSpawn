package net.java_rin.multiplespawn.config;

import net.java_rin.multiplespawn.MultipleSpawn;
import net.java_rin.multiplespawn.model.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigurationManager {

    private static final File configPath = new File(MultipleSpawn.getInstance().getDataFolder() + File.separator + "config.yml");
    private static FileConfiguration config;

    public static boolean IGNORE_OP;

    public static void load() {
        if (!configPath.exists()) {
            MultipleSpawn.getInstance().saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configPath);
        IGNORE_OP = config.getBoolean("ignore-op");
        Spawn.loadData(loadSpawnLocations());
    }

    /**
     * Loads spawn locations from the configuration.
     *
     * @return A map of spawn locations, where the key is the spawn ID and the value is the location.
     */
    public static HashMap<String, Location> loadSpawnLocations() {
        Bukkit.getConsoleSender().sendMessage("Loading spawn locations...");
        HashMap<String, Location> spawnLocations = new HashMap<>();
        List<String> raw_spawn = config.getStringList("spawn");

        raw_spawn.forEach(data -> {
            String[] parts = data.split(", ");
            if (parts.length == 7) {
                String id = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                String worldName = parts[4];
                float yaw = Float.parseFloat(parts[5]);
                float pitch = Float.parseFloat(parts[6]);

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
                spawnLocations.put(id, location);
                Bukkit.getConsoleSender().sendMessage("Loaded spawn: " + id);
            } else {
                Bukkit.getConsoleSender().sendMessage("Invalid spawn: " + data);
            }
        });

        return spawnLocations;
    }


    /**
     * Saves spawn locations to the configuration.
     *
     * @param spawnLocations A map of spawn locations, where the key is the spawn ID and the value is the location.
     * @throws IOException If there's an error saving the configuration.
     */
    public static void saveSpawnLocations(HashMap<String, Location> spawnLocations) throws IOException {
        List<String> serializedLocations = new ArrayList<>();

        for (Map.Entry<String, Location> entry : spawnLocations.entrySet()) {
            String id = entry.getKey();
            Location location = entry.getValue();

            String serializedLocation = String.format(
                    "%s, %.1f, %.1f, %.1f, %s, %.1f, %.1f",
                    id, location.getX(), location.getY(), location.getZ(),
                    Objects.requireNonNull(location.getWorld()).getName(), location.getYaw(), location.getPitch()
            );

            serializedLocations.add(serializedLocation);
        }

        config.set("spawn", serializedLocations);
        config.save(configPath);
    }
}
