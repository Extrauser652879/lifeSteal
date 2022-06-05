package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {

    LifeSteal plugin;
    public MuteCommand(LifeSteal plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Only players can do that!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Usage: /mute");
            return false;
        }
        if (plugin.enabled) {
            player.sendMessage(ChatColor.GREEN + "There is no grace period running. Do " +
                    ChatColor.RED + ChatColor.BOLD + "/grace" +
                    ChatColor.RESET + ChatColor.GREEN + " to start a grace period.");
            return false;
        }
        if (plugin.getGraceCommand().muted.contains(player)) {
            player.sendMessage(ChatColor.GREEN + "You have already muted the alert. Do " +
                    ChatColor.RED + ChatColor.BOLD + "/unmute" +
                    ChatColor.RESET + ChatColor.GREEN + " to unmute the alert.");
            return false;
        }

        plugin.getGraceCommand().muted.add(player);
        player.sendMessage(ChatColor.GREEN + "You have muted the alert. Do " +
                ChatColor.RED + ChatColor.BOLD + "/unmute" +
                ChatColor.RESET + ChatColor.GREEN + " to unmute the alert.");

        return false;
    }

}
