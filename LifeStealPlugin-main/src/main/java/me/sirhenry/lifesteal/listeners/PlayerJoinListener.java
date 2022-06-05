package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoinListener implements Listener {

    LifeSteal lifeSteal;
    public PlayerJoinListener(LifeSteal lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        if (!lifeSteal.enabled) { return; }

        Player player = e.getPlayer();

        if(!player.hasPlayedBefore()) {

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfig().getDouble("DefaultHealth"));

        }

    }

}
