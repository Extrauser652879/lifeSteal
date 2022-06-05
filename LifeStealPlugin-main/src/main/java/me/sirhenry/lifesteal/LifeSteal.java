/*
50% nothing
2% Elytra
1% Heart
3% totem
10% golden appl 1-10 (equal chance/spread)
10%  golden carrot  10-20 (equal spread)
10% cobweb 8-32
3% 4 ancient debris
2% stack of obsidian
9% 1-32 daimonds
 */

package me.sirhenry.lifesteal;

import me.sirhenry.lifesteal.listeners.EntityToggleGlide;
import me.sirhenry.lifesteal.listeners.PlayerInteractListener;
import me.sirhenry.lifesteal.listeners.PlayerJoinListener;
import me.sirhenry.lifesteal.listeners.PlayerKilledListener;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Material.*;
import static org.bukkit.event.inventory.InventoryType.SlotType.ARMOR;

public final class LifeSteal extends JavaPlugin implements Listener {

    GraceCommand graceCommand = new GraceCommand(this);
    ItemStack heart;
    public boolean enabled = true;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerKilledListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new EntityToggleGlide(), this);
        getCommand("withdraw").setExecutor(new WithdrawCommand(this));
        getCommand("grace").setExecutor(graceCommand);
        getCommand("timeleft").setExecutor(new TimeleftCommand(this));
        getCommand("ungrace").setExecutor(new UngraceCommand(this));
        getCommand("mute").setExecutor(new MuteCommand(this));
        getCommand("unmute").setExecutor(new UnmuteCommand(this));
        getCommand("serverstop").setExecutor(new ServerstopCommand(this));
        getCommand("asgod").setExecutor(new AsgodCommand());
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.addRecipe(heartRecipe());

        Bukkit.broadcastMessage(ChatColor.GREEN + "Life Steal Plugin has Finished Loading!");

    }

    public ShapedRecipe heartRecipe() {

        heart = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Heart");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_RED + "Max was here...");
        lore.add(ChatColor.DARK_RED + "hehe");
        meta.setLore(lore);
        heart.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(this, "Heart");
        ShapedRecipe sr = new ShapedRecipe(key, heart);

        sr.shape("SDS", "TET", "SDS");

        sr.setIngredient('S', Material.NETHERITE_SCRAP);
        sr.setIngredient('D', Material.DIAMOND_BLOCK);
        sr.setIngredient('T', Material.TOTEM_OF_UNDYING);
        sr.setIngredient('E', Material.ELYTRA);

        return sr;
    }

    public void cancelGraceTasks() {

        graceCommand.timeLeftAlert.cancel();
        graceCommand.ticksLeftTimer.cancel();
        graceCommand.hoursTimer.cancel();
        graceCommand.minutesTimer.cancel();
        graceCommand.secondsTimer.cancel();

        enabled = true;
        graceCommand.muted.clear();
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 5, 1);
                p.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "BLOOD!",
                        ChatColor.GREEN + "Go kill some people or something",
                        10, 70, 20);
            }
                Bukkit.getScheduler().runTaskLater(this, () -> {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 5, 1);
                    }
                Bukkit.broadcastMessage("\n \n \n \n \n[" + ChatColor.GOLD + "GOD" + ChatColor.RESET + "] " +
                        ChatColor.GREEN + "Sooooo... You guys go do ur thing and stab people or something. You could be like " +
                        ChatColor.RED + ChatColor.BOLD + "Andrew" +
                        ChatColor.RESET + ChatColor.GREEN + " and put " +
                        ChatColor.RED + ChatColor.BOLD + "50+ hours" +
                        ChatColor.RESET + ChatColor.GREEN + " into Max Brennan's blok game server, or you could be like " +
                        ChatColor.RED + ChatColor.BOLD + "Rhism" +
                        ChatColor.RESET + ChatColor.GREEN + " and and hire me to " +
                        ChatColor.RED + ChatColor.BOLD + "code plugins" +
                        ChatColor.RESET + ChatColor.GREEN + " for you. But that would be " +
                        ChatColor.RED + ChatColor.BOLD + "admin abuse" +
                        ChatColor.RESET + ChatColor.GREEN + " which isn't cool at all. So maybe just be a " +
                        ChatColor.RED + ChatColor.BOLD + "normal person" +
                        ChatColor.RESET + ChatColor.GREEN + " that " +
                        ChatColor.RED + ChatColor.BOLD + "touches grass.");
                Bukkit.getScheduler().runTaskLater(this, () -> {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 5, 1);
                }
                    Bukkit.broadcastMessage("\n \n \n \n \n[" + ChatColor.GOLD + "GOD" + ChatColor.RESET + "] " +
                            ChatColor.GREEN + "Anyways, if you want to withdraw hearts, you can do " +
                            ChatColor.RED + ChatColor.BOLD + "/withdraw <amount>" +
                            ChatColor.RESET + ChatColor.GREEN + " , where " +
                            ChatColor.RED + ChatColor.BOLD + "<amount>" +
                            ChatColor.RESET + ChatColor.GREEN + " , is the amount of hearts you want to withdraw. " +
                            ChatColor.RED + ChatColor.BOLD + "IMPORTANT: " +
                            ChatColor.RESET + ChatColor.GREEN + " <amount> represents the number of hearts. For example, if you did " +
                            ChatColor.RED + ChatColor.BOLD + "/withdraw 1" +
                            ChatColor.RESET + ChatColor.GREEN + " , that would take 1 heart from you, and give you 1 heart as an item. " +
                            ChatColor.RED + ChatColor.BOLD + "NOT" +
                            ChatColor.RESET + ChatColor.GREEN + " half a heart. " +
                            ChatColor.RED + ChatColor.BOLD + "Peace out puny mortals.");
            }, 200);
        }, 200);

    }

    public GraceCommand getGraceCommand() { return graceCommand; }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent e) {
        if (e.getEnchantsToAdd().containsKey(Enchantment.RIPTIDE)) {
            Player player = e.getEnchanter();
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 5, 1);
            player.sendMessage("\n \n \n \n \n[" + ChatColor.GOLD + "GOD" + ChatColor.RESET + "] " +
                    ChatColor.GREEN + "Ehh... I don't feel like giving you riptide, sorry.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        ItemStack is = e.getResult();
        if (is == null) return;
        if (is.getEnchantments().containsKey(Enchantment.RIPTIDE)) {
            for (HumanEntity h : e.getViewers()) {
                if (h.getType() == EntityType.PLAYER) {
                    Player player = (Player) h;
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 5, 1);
                    player.sendMessage("\n \n \n \n \n[" + ChatColor.GOLD + "GOD" + ChatColor.RESET + "] " +
                            ChatColor.GREEN + "Ehh... I don't feel like giving you riptide, sorry.");
                    player.closeInventory();
                }
            }
            is.removeEnchantment(Enchantment.RIPTIDE);
        }
    }

    @EventHandler
    public void onExpBottle(ExpBottleEvent e) {
        e.setExperience(e.getExperience() * 2);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        List<Material> types = Arrays.asList(
                NETHERITE_HELMET,
                NETHERITE_CHESTPLATE,
                NETHERITE_LEGGINGS,
                NETHERITE_BOOTS,
                NETHERITE_SWORD);
        ItemStack cursor = e.getCurrentItem();

        if (cursor == null) return;
        if (e.getSlotType().equals(ARMOR) && (types.contains(cursor.getType()))) {

            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.RED + "Netherite is disabled lmao");

        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        Entity damager = e.getDamager();

        if (e.getEntity().getType().equals(EntityType.PLAYER) && damager.getType().equals(EntityType.PLAYER) && !enabled) {

            damager.sendMessage(ChatColor.RED + "PVP is disabled!");
            e.setCancelled(true);

        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        List<Action> actions = Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);
        List<Material> materials = Arrays.asList(
                NETHERITE_HELMET,
                NETHERITE_CHESTPLATE,
                NETHERITE_LEGGINGS,
                NETHERITE_BOOTS,
                NETHERITE_SWORD);

        if (actions.contains(e.getAction()) && materials.contains(e.getMaterial())) {

            e.getPlayer().sendMessage(ChatColor.RED + "Netherite is disabled lmao");
            e.setCancelled(true);

        }
    }
    
    @EventHandler
    public void onBlockDispenseArmor(BlockDispenseArmorEvent e) {

        List<Material> materials = Arrays.asList(
                NETHERITE_HELMET,
                NETHERITE_CHESTPLATE,
                NETHERITE_LEGGINGS,
                NETHERITE_BOOTS,
                NETHERITE_SWORD);
        
        if (materials.contains(e.getItem().getType())) {

            e.getTargetEntity().sendMessage(ChatColor.RED + "Netherite is disabled lmao");
            e.setCancelled(true);

        }
        
    }
    
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent e) {

        List<Material> materials = Arrays.asList(
                NETHERITE_HELMET,
                NETHERITE_CHESTPLATE,
                NETHERITE_LEGGINGS,
                NETHERITE_BOOTS,
                NETHERITE_SWORD);
        
        if (materials.contains(e.getItem().getItemStack().getType())) {

            e.getEntity().sendMessage(ChatColor.RED + "Netherite is disabled lmao");
            e.setCancelled(true);

        }
    }

    @EventHandler
    public void onSmithItem(SmithItemEvent e) {

        List<Material> materials = Arrays.asList(
                NETHERITE_HELMET,
                NETHERITE_CHESTPLATE,
                NETHERITE_LEGGINGS,
                NETHERITE_BOOTS,
                NETHERITE_SWORD);

        if (materials.contains(Objects.requireNonNull(e.getCurrentItem()).getType())) {

            e.getWhoClicked().sendMessage(ChatColor.RED + "Netherite is disabled lmao");
            e.setCancelled(true);

        }
    }

    public ItemStack getHeart() { return heart; }

}

