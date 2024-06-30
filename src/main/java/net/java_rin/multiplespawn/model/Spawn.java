package net.java_rin.multiplespawn.model;

import net.java_rin.multiplespawn.config.ConfigurationManager;
import org.bukkit.Location;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Represents spawn locations for the plugin.
 */
public class Spawn {

    private static HashMap<String, Location> spawns = new HashMap<>();

    /**
     * Loads spawn data into the class.
     *
     * @param spawn A map of spawn locations, where the key is the spawn ID and the value is the location.
     */
    public static void loadData(HashMap<String, Location> spawn) {
        Spawn.spawns = spawn;
    }

    /**
     * Gets the set of spawn IDs.
     *
     * @return A set of spawn IDs.
     */
    public static Set<String> getSpawnIds() {
        return spawns.keySet();
    }

    /**
     * Gets the spawn location for a given ID.
     *
     * @param id The spawn ID.
     * @return The corresponding spawn location.
     */
    public static Location getSpawn(String id) {
        return spawns.get(id);
    }

    /**
     * Gets all spawn locations.
     *
     * @return A map of spawn locations.
     */
    public static HashMap<String, Location> getSpawns() {
        return spawns;
    }

    /**
     * Sets a spawn location.
     *
     * @param id    The spawn ID.
     * @param spawn The location to set.
     * @throws IOException If there's an error saving the configuration.
     */
    public static void setSpawn(String id, Location spawn) throws IOException {
        spawns.put(id, spawn);
        save();
    }

    /**
     * Removes a spawn location.
     *
     * @param id The spawn ID to remove.
     * @throws IOException If there's an error saving the configuration.
     */
    public static void removeSpawn(String id) throws IOException {
        spawns.remove(id);
        save();
    }

    /**
     * Saves spawn locations to the configuration.
     *
     * @throws IOException If there's an error saving the configuration.
     */
    public static void save() throws IOException {
        ConfigurationManager.saveSpawnLocations(spawns);
    }

    /**
     * Gets a random spawn location.
     *
     * @return A random spawn location.
     */
    public static Location getRandomLocation() {
        Object[] values = spawns.values().toArray();
        Object randomValue = values[new Random().nextInt(values.length)];
        return (Location) randomValue;
    }
}
