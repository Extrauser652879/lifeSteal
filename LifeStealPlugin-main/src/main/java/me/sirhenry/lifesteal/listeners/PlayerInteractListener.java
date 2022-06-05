package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class PlayerInteractListener implements Listener {

    LifeSteal plugin;
    public PlayerInteractListener(LifeSteal plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent e) {

        if (!plugin.enabled) { return; }

        if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHER_STAR) && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§cHeart")) {
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);

            }

        }

    }

}
