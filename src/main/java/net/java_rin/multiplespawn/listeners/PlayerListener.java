package net.java_rin.multiplespawn.listeners;

import net.java_rin.multiplespawn.config.ConfigurationManager;
import net.java_rin.multiplespawn.model.Spawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Spawn.getSpawns().isEmpty()) return;
        if (ConfigurationManager.IGNORE_OP && player.hasPermission("*")) return;
        player.teleport(Spawn.getRandomLocation());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (Spawn.getSpawns().isEmpty()) return;
        if (ConfigurationManager.IGNORE_OP && player.hasPermission("*")) return;
        player.teleport(Spawn.getRandomLocation());
    }
}
