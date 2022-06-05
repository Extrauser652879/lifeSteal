package me.sirhenry.lifesteal.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class EntityToggleGlide implements Listener {

    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent e) {

        Entity entity = e.getEntity();
        if (!entity.getType().equals(EntityType.PLAYER)) { return; }
        Player player = (Player) entity;
        player.damage(player.getHealth());
        player.sendMessage(ChatColor.RED +
                "lmao, you spent all that time grinding an elytra only to find that they are disabled >:D");
        e.setCancelled(true);


    }
}