package net.java_rin.multiplespawn;

import net.java_rin.multiplespawn.commands.MultipleSpawnCommand;
import net.java_rin.multiplespawn.config.ConfigurationManager;
import net.java_rin.multiplespawn.listeners.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MultipleSpawn extends JavaPlugin {

    private static MultipleSpawn instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("------------------------------");
        getServer().getConsoleSender().sendMessage("|     MultipleSpawn Plugin    |");
        getServer().getConsoleSender().sendMessage("|          by Java_Rin        |");
        getServer().getConsoleSender().sendMessage("------------------------------");
        instance = this;
        ConfigurationManager.load();
        Objects.requireNonNull(this.getCommand("multiplespawn")).setExecutor(new MultipleSpawnCommand());
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static MultipleSpawn getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage( ChatColor.RED + "Disabling MultipleSpawn Plugin...");
    }
}
