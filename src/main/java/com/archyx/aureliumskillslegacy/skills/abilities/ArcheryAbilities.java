package com.archyx.aureliumskillslegacy.skills.abilities;

import com.archyx.aureliumskillslegacy.skills.Skill;
import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.skills.PlayerSkill;
import com.archyx.aureliumskillslegacy.skills.SkillLoader;
import com.archyx.aureliumskillslegacy.skills.Source;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class ArcheryAbilities implements Listener {

    private static Random r = new Random();

    private Plugin plugin;

    public ArcheryAbilities(Plugin plugin) {
        this.plugin = plugin;
    }

    public static double getModifiedXp(Player player, Source source) {
        PlayerSkill skill = SkillLoader.playerSkills.get(player.getUniqueId());
        double output = Options.getXpAmount(source);
        double modifier = 1;
        modifier += Ability.ARCHER.getValue(skill.getAbilityLevel(Ability.ARCHER)) / 100;
        output *= modifier;
        return output;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void applyCrit(EntityDamageByEntityEvent event) {
        if (Options.isEnabled(Skill.ARCHERY)) {
            //Checks if damage is from arrow
            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                //Checks if player shot the arrow
                if (arrow.getShooter() instanceof Player) {
                    Player player = (Player) arrow.getShooter();
                    //Applies damage multiplier
                    if (SkillLoader.playerSkills.containsKey(player.getUniqueId())) {
                        if (Critical.isCrit(player)) {
                            event.setDamage(event.getDamage() * Critical.getCritMultiplier(player));
                            player.setMetadata("skillsCritical", new FixedMetadataValue(plugin, true));
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.removeMetadata("skillsCritical", plugin);
                                }
                            }.runTaskLater(plugin, 1L);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void bowMaster(EntityDamageByEntityEvent event) {
        if (Options.isEnabled(Skill.ARCHERY)) {
            //Checks if damage is from arrow
            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                //Checks if player shot the arrow
                if (arrow.getShooter() instanceof Player) {
                    Player player = (Player) arrow.getShooter();
                    //Applies damage multiplier
                    if (SkillLoader.playerSkills.containsKey(player.getUniqueId())) {
                        PlayerSkill skill = SkillLoader.playerSkills.get(player.getUniqueId());
                        double multiplier = 1 + (Ability.BOW_MASTER.getValue(skill.getAbilityLevel(Ability.BOW_MASTER)) / 100);
                        event.setDamage(event.getDamage() * multiplier);
                    }
                }
            }
        }
    }

    @EventHandler
    public void stun(EntityDamageByEntityEvent event) {
        if (Options.isEnabled(Skill.ARCHERY)) {
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                //Checks if damage is from arrow
                if (event.getDamager() instanceof Arrow) {
                    Arrow arrow = (Arrow) event.getDamager();
                    //Checks if player shot the arrow
                    if (arrow.getShooter() instanceof Player) {
                        Player player = (Player) arrow.getShooter();
                        if (SkillLoader.playerSkills.containsKey(player.getUniqueId())) {
                            PlayerSkill skill = SkillLoader.playerSkills.get(player.getUniqueId());
                            if (r.nextDouble() < (Ability.STUN.getValue(skill.getAbilityLevel(Ability.STUN)) / 100)) {
                                //Applies stun
                                float originalSpeed = player.getWalkSpeed();
                                player.setWalkSpeed(originalSpeed * 0.8f);
                                player.setMetadata("skillsStun", new FixedMetadataValue(plugin, true));
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.removeMetadata("skillsStun", plugin);
                                    }
                                }.runTaskLater(plugin, 1L);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.setWalkSpeed(originalSpeed);
                                    }
                                }.runTaskLater(plugin, 40L);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void piercing(EntityDamageByEntityEvent event) {
        if (Options.isEnabled(Skill.ARCHERY)) {
            //Checks if damage is from arrow
            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                //Checks if player shot the arrow
                if (arrow.getShooter() instanceof Player) {
                    Player player = (Player) arrow.getShooter();
                    if (SkillLoader.playerSkills.containsKey(player.getUniqueId())) {
                        PlayerSkill skill = SkillLoader.playerSkills.get(player.getUniqueId());
                        if (r.nextDouble() < (Ability.PIERCING.getValue(skill.getAbilityLevel(Ability.PIERCING)) / 100)) {
                            arrow.setBounce(false);
                            Vector velocity = arrow.getVelocity();
                            Arrow newArrow = event.getEntity().getWorld().spawnArrow(arrow.getLocation(), velocity, (float) velocity.length(), 0.0f);
                            newArrow.setShooter(player);
                            newArrow.setKnockbackStrength(arrow.getKnockbackStrength());
                            newArrow.setFireTicks(arrow.getFireTicks());
                        }
                    }
                }
            }
        }
    }

}
