package me.sirhenry.lifesteal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UngraceCommand implements CommandExecutor {

    LifeSteal plugin;
    public UngraceCommand(LifeSteal plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Only players can do that!");
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("lifesteal.use")) {
            player.sendMessage(ChatColor.RED + "You can't do that!");
            return false;
        }
        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Usage: /ungrace");
            return false;
        }
        if (plugin.enabled) {
            player.sendMessage(ChatColor.GREEN + "There is no grace period running. Do " +
                    ChatColor.RED + ChatColor.BOLD + "/grace" +
                    ChatColor.RESET + ChatColor.GREEN + " to start a grace period.");
            return false;
        }

        plugin.cancelGraceTasks();
        return false;

    }
}
