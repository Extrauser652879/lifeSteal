package me.sirhenry.lifesteal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor {

    LifeSteal plugin;
    public UnmuteCommand(LifeSteal plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Only players can do that!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Usage: /unmute");
            return false;
        }
        if (plugin.enabled) {
            player.sendMessage(ChatColor.GREEN + "There is no grace period running. Do " +
                    ChatColor.RED + ChatColor.BOLD + "/grace" +
                    ChatColor.RESET + ChatColor.GREEN + " to start a grace period.");
            return false;
        }
        if (!plugin.getGraceCommand().muted.contains(player)) {
            player.sendMessage(ChatColor.GREEN + "You have not muted the alert yet. Do " +
                    ChatColor.RED + ChatColor.BOLD + "/mute" +
                    ChatColor.RESET + ChatColor.GREEN + " to mute the alert.");
            return false;
        }

        plugin.getGraceCommand().muted.remove(player);
        player.sendMessage(ChatColor.GREEN + "You have unmuted the alert. Do " +
                ChatColor.RED + ChatColor.BOLD + "/mute" +
                ChatColor.RESET + ChatColor.GREEN + " to mute the alert again.");

        return false;
    }
}
