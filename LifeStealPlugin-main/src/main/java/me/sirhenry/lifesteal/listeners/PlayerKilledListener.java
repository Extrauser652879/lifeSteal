package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import javax.security.auth.login.Configuration;
import java.util.Calendar;
import java.util.Date;

public class PlayerKilledListener implements Listener {

    LifeSteal lifeSteal;
    public PlayerKilledListener(LifeSteal lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);
    Calendar cal = Calendar.getInstance();

    @EventHandler
    public void onPlayerKilled(PlayerDeathEvent e) {

        if (!lifeSteal.enabled) { return; }

        Player victim = e.getEntity();
        cal.add(Calendar.HOUR, 168);
        Date date = cal.getTime();

        if(victim.getKiller() instanceof Player) {

            Player killer = victim.getKiller();
            if(killer != victim) {

                double vHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double kHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));
                killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(kHealth + plugin.getConfig().getDouble("HealthGainedOnKill"));

                if(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {

                    victim.kickPlayer("u ded lol");
                    Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), "u ded lol", date, null);

                }
            }

            e.setDeathMessage(ChatColor.LIGHT_PURPLE + victim.getDisplayName() + ChatColor.GOLD + " ("  + victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + ")" + ChatColor.GRAY + " Was Killed By " + ChatColor.LIGHT_PURPLE + killer.getDisplayName() + ChatColor.GOLD + " ("  + killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + ")");

        }

        else {

            if(plugin.getConfig().getBoolean("LoseLifeIfNotKilledByPlayer")) {
                double vHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));

                if (victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {

                    victim.kickPlayer("u ded lol");
                    Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), "u ded lol", date, null);

                }

                if(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {

                    victim.kickPlayer("u ded lol");
                    Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), "u ded lol", date, null);

                }

            }
        }

    }

}
