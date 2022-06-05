package me.sirhenry.lifesteal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TimeleftCommand implements CommandExecutor {

    LifeSteal plugin;
    public TimeleftCommand(LifeSteal plugin) {
        this.plugin = plugin;
    }

    int hours;
    int minutes;
    int seconds;
    long ticks;

    List<String> arguments = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Only players can do that!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Usage: /timeleft");
            return false;
        }
        if (plugin.enabled) {
            player.sendMessage(ChatColor.GREEN + "There is no grace period running. Do " +
                    ChatColor.RED + ChatColor.BOLD + "/grace" +
                    ChatColor.RESET + ChatColor.GREEN + " to start a grace period.");
            return false;
        }

        ticks = plugin.getGraceCommand().getTicksLeft();
        hours = (int) Math.round(Math.floor(ticks / 72000d));
        ticks = ticks - hours * 72000L;
        minutes = (int) Math.round(Math.floor(ticks / 1200d));
        ticks = ticks - minutes * 1200L;
        seconds = (int) Math.round(Math.floor(ticks / 20d));

        if (hours == 1) {
            arguments.add(ChatColor.RED + ChatColor.BOLD.toString() +  "1 hour" +
                    ChatColor.RESET + ChatColor.GREEN);
        } else if (hours > 0) {
            arguments.add(ChatColor.RED + ChatColor.BOLD.toString() + hours + " hours" +
                    ChatColor.RESET + ChatColor.GREEN);
        }

        if (minutes == 1) {
            arguments.add(ChatColor.RED + ChatColor.BOLD.toString() +  "1 minute" +
                    ChatColor.RESET + ChatColor.GREEN);
        } else if (minutes > 0) {
            arguments.add(ChatColor.RED + ChatColor.BOLD.toString() + minutes + " minutes" +
                    ChatColor.RESET + ChatColor.GREEN);
        }

        if (seconds == 1) {
            arguments.add(ChatColor.RED + ChatColor.BOLD.toString() +  "1 second" +
                    ChatColor.RESET + ChatColor.GREEN);
        } else if (seconds > 0) {
            arguments.add(ChatColor.RED + ChatColor.BOLD.toString() + seconds + " seconds" +
                    ChatColor.RESET + ChatColor.GREEN);
        }

        switch (arguments.size()) {
            case 1:
                player.sendMessage(ChatColor.GREEN + "The grace period will end in " +
                        arguments.get(0) + ".");
                break;
            case 2:
                player.sendMessage(ChatColor.GREEN + "The grace period will end in " +
                        arguments.get(0) + " and " + arguments.get(1) + ".");
                break;
            case 3:
                player.sendMessage(ChatColor.GREEN + "The grace period will end in " +
                        arguments.get(0) + ", " + arguments.get(1) + ", and " + arguments.get(2));
                break;
        }

        arguments.clear();

        return false;

    }
}
