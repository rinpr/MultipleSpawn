package net.java_rin.multiplespawn.commands;

import net.java_rin.multiplespawn.config.ConfigurationManager;
import net.java_rin.multiplespawn.model.Spawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleSpawnCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
        }
        assert sender instanceof Player;
        Player player = (Player) sender;
        switch (args[0].toLowerCase()) {
            case "set":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please specify spawn id!");
                    break;
                }
                try {
                    Spawn.setSpawn(args[1], player.getLocation());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                player.sendMessage( ChatColor.GREEN + "Successfully set spawn id: " + ChatColor.WHITE + args[1]);
                break;
            case "remove":
                player.sendMessage(ChatColor.GREEN + "Successfully removed spawn id: " + ChatColor.WHITE + args[1]);
                try {
                    Spawn.removeSpawn(args[1]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "reload":
                ConfigurationManager.load();
                player.sendMessage(ChatColor.YELLOW + "Reloaded all spawns and configurations.");
                break;
            case "spawn":
                player.teleport(Spawn.getRandomLocation());
                player.sendMessage(ChatColor.GREEN + "Successfully teleported you to spawn!");
                break;
            case "debug":
                player.sendMessage(Spawn.getSpawns().toString());
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown command.");
                break;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> argument = new ArrayList<>();
        if (args.length == 1) {
            argument.add("set");
            argument.add("remove");
            argument.add("reload");
            argument.add("spawn");
            argument.add("debug");
            Collections.sort(argument);
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove")) {
                argument.addAll(Spawn.getSpawnIds());
                Collections.sort(argument);
            }
        }
        return argument.stream().filter(completion -> completion.startsWith(args[args.length - 1])).collect(Collectors.toList());
    }
}
