package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public class WithdrawCommand implements CommandExecutor {
    LifeSteal plugin;
    public WithdrawCommand(LifeSteal plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        int amount;
        double health;
        AttributeInstance attribute;

        if (!plugin.enabled) { return false; }

        if(!(sender instanceof Player)) {
            System.out.println("Only players can do that!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /withdraw <amount>");
            return false;
        }
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Usage: /withdraw <amount>");
            return false;
        }
        if (amount <= 0) {
            sender.sendMessage(ChatColor.RED + "You cannot put negative numbers.");
            return false;
        }
        attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        health = attribute.getBaseValue();
        if (health <= amount * 2) {
            player.sendMessage(ChatColor.RED + "You cannot withdraw more than you own.");
            return false;
        }

        if (player.getGameMode() == GameMode.SURVIVAL) {

            ItemStack is = plugin.getHeart().clone();
            is.setAmount(amount);
            player.getWorld().dropItemNaturally(player.getLocation(), is);
            attribute.setBaseValue(health - amount * 2);

            player.sendMessage(ChatColor.GREEN + "You have withdrawn " +
                    ChatColor.RED + ChatColor.BOLD + amount +
                    ChatColor.RESET + ChatColor.GREEN + " heart(s).");

        } else {

            player.sendMessage(ChatColor.RED + "You can only perform this command while in survival mode");

        }
        return false;
    }
}