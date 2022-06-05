package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.nio.charset.StandardCharsets;

public class AsgodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("lifesteal.use")) {
            sender.sendMessage(ChatColor.RED + "You can't do that!");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /asgod <message>");
            return false;
        }

        String message = "[" + ChatColor.GOLD + "GOD" + ChatColor.RESET + "]" + ChatColor.GREEN;
        boolean red = false;

        for (String s : args) {
            while (s.contains("~")) {
                if (red) {
                    s = s.replaceFirst("~", ChatColor.RESET.toString() + ChatColor.GREEN);
                    red = false;
                } else {
                    s = s.replaceFirst("~", ChatColor.RED.toString() + ChatColor.BOLD);
                    red = true;
                }
            }
            message = message + " " + s;
        }

        Bukkit.broadcastMessage(message);

        return false;

    }
}
