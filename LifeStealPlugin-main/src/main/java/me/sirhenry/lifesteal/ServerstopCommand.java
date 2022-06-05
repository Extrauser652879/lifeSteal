package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerstopCommand implements CommandExecutor {

    LifeSteal plugin;
    public ServerstopCommand(LifeSteal plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {

        if (sender instanceof Player) {
            if (!sender.hasPermission("lifesteal.use")) {
                sender.sendMessage(ChatColor.RED + "You can't do that!");
                return false;
            }
        }

        AtomicInteger seconds = new AtomicInteger(60);

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (seconds.get() == 0) { Bukkit.getServer().shutdown(); }
            Bukkit.broadcastMessage("[" + ChatColor.GOLD + "GOD" + ChatColor.RESET + "] " +
                    ChatColor.GREEN + "The server will restart in " +
                    ChatColor.RED + ChatColor.BOLD + seconds.get() +
                    ChatColor.RESET + ChatColor.GREEN + " seconds.");
            seconds.getAndDecrement();
        }, 0, 20);

        return false;

    }
}
