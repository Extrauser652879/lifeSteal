package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraceCommand implements CommandExecutor {

    LifeSteal plugin;
    public GraceCommand(LifeSteal plugin) { this.plugin = plugin; }

    int hoursPassed = 0;
    int minutesPassed = 0;
    int secondsPassed = 0;
    long ticksLeft = 576000;

    public BukkitTask timeLeftAlert;
    public BukkitTask ticksLeftTimer;
    public BukkitTask hoursTimer;
    public BukkitTask minutesTimer;
    public BukkitTask secondsTimer;
    public List<Player> muted = new ArrayList<>();

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
            player.sendMessage(ChatColor.RED + "Usage: /grace");
            return false;
        }
        if (!plugin.enabled) {
            player.sendMessage(ChatColor.GREEN + "There is already a grace period running. Do " +
                    ChatColor.RED + ChatColor.BOLD + "/ungrace" +
                    ChatColor.RESET + ChatColor.GREEN + " to end the grace period.");
            return false;
        }

        plugin.enabled = false;
        player.sendMessage(ChatColor.GREEN + "Grace period has started! Wait " +
                ChatColor.RED + ChatColor.BOLD + "24 hours" +
                ChatColor.RESET + ChatColor.GREEN + " or do " +
                ChatColor.RED + ChatColor.BOLD + "/ungrace" +
                ChatColor.RESET + ChatColor.GREEN + " to end the grace period.");

        timeLeftAlert = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!muted.contains(p)) {
                    p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "ALERT: " +
                            ChatColor.RESET + ChatColor.GREEN + " Use the command " +
                            ChatColor.RED + ChatColor.BOLD + "/timeleft" +
                            ChatColor.RESET + ChatColor.GREEN + " to view the time left until the grace period ends. Type " +
                            ChatColor.RED + ChatColor.BOLD + "/mute" +
                            ChatColor.RESET + ChatColor.GREEN + " to mute this alert.");
                }
            }

        }, 1200, 1200);

        ticksLeftTimer = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            ticksLeft--;
        }, 1, 1);

        hoursTimer = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            hoursPassed++;
            if (hoursPassed < 7) {

                Bukkit.getServer().broadcastMessage(
                        ChatColor.RED + ChatColor.BOLD.toString() + "ALERT: " +
                        ChatColor.RESET + ChatColor.GREEN + "The grace period will end in " +
                        ChatColor.RED + ChatColor.BOLD + (8 - hoursPassed) +
                        ChatColor.RESET + ChatColor.GREEN + " hours.");

            } else if (hoursPassed == 7) {

                Bukkit.getServer().broadcastMessage(
                        ChatColor.RED + ChatColor.BOLD.toString() + "ALERT: " +
                        ChatColor.RESET + ChatColor.GREEN + "The grace period will end in " +
                        ChatColor.RED + ChatColor.BOLD + "1" +
                        ChatColor.RESET + ChatColor.GREEN + " hour.");

            }
        }, 72000, 72000);

        minutesTimer = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (hoursPassed < 7) { return; }
            minutesPassed++;
            if (minutesPassed < 59) {

                Bukkit.getServer().broadcastMessage(
                        ChatColor.RED + ChatColor.BOLD.toString() + "ALERT: " +
                        ChatColor.RESET + ChatColor.GREEN + "The grace period will end in " +
                        ChatColor.RED + ChatColor.BOLD + (60 - minutesPassed) +
                        ChatColor.RESET + ChatColor.GREEN + " minutes.");

            } else if (minutesPassed == 59) {

                Bukkit.getServer().broadcastMessage(
                        ChatColor.RED + ChatColor.BOLD.toString() + "ALERT: " +
                        ChatColor.RESET + ChatColor.GREEN + "The grace period will end in " +
                        ChatColor.RED + ChatColor.BOLD + "1" +
                        ChatColor.RESET + ChatColor.GREEN + " minute.");

            }
        }, 1200, 1200);

        secondsTimer = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (minutesPassed < 59) { return; }
            secondsPassed++;
            if (secondsPassed < 59) {

                Bukkit.getServer().broadcastMessage(
                        ChatColor.RED + ChatColor.BOLD.toString() + "ALERT: " +
                        ChatColor.RESET + ChatColor.GREEN + "The grace period will end in " +
                        ChatColor.RED + ChatColor.BOLD + (60 - minutesPassed) +
                        ChatColor.RESET + ChatColor.GREEN + " seconds.");

            } else if (secondsPassed == 59) {

                Bukkit.getServer().broadcastMessage(
                        ChatColor.RED + ChatColor.BOLD.toString() + "ALERT: " +
                        ChatColor.RESET + ChatColor.GREEN + "The grace period will end in " +
                        ChatColor.RED + ChatColor.BOLD + "1" +
                        ChatColor.RESET + ChatColor.GREEN + " second.");
                plugin.cancelGraceTasks();

            }
        }, 20, 20);

        return false;

    }

    public long getTicksLeft() { return ticksLeft; }

}
