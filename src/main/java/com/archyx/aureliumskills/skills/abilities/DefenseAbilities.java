package com.archyx.aureliumskills.skills.abilities;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.Options;
import com.archyx.aureliumskills.skills.PlayerSkill;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.SkillLoader;
import com.archyx.aureliumskills.skills.Source;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class DefenseAbilities implements Listener {

    private final Random r = new Random();
    private final Plugin plugin;

    public DefenseAbilities(Plugin plugin) {
        this.plugin = plugin;
    }

    public static double getModifiedXp(Player player, Source source) {
        PlayerSkill skill = SkillLoader.playerSkills.get(player.getUniqueId());
        double output = Options.getXpAmount(source);
        if (AureliumSkills.abilityOptionManager.isEnabled(Ability.DEFENDER)) {
            double modifier = 1;
            modifier += Ability.DEFENDER.getValue(skill.getAbilityLevel(Ability.DEFENDER)) / 100;
            output *= modifier;
        }
        return output;
    }

    public static double getModifiedXp(Player player, double base) {
        PlayerSkill skill = SkillLoader.playerSkills.get(player.getUniqueId());
        double output = base;
        if (AureliumSkills.abilityOptionManager.isEnabled(Ability.DEFENDER)) {
            double modifier = 1;
            modifier += Ability.DEFENDER.getValue(skill.getAbilityLevel(Ability.DEFENDER)) / 100;
            output *= modifier;
        }
        return output;
    }

    public void shielding(EntityDamageByEntityEvent event, PlayerSkill playerSkill, Player player) {
        if (player.isSneaking()) {
            double damageReduction = 1 - (Ability.SHIELDING.getValue(playerSkill.getAbilityLevel(Ability.SHIELDING)) / 100);
            event.setDamage(event.getDamage() * damageReduction);
        }
    }

    public void mobMaster(EntityDamageByEntityEvent event, PlayerSkill playerSKill) {
        if (event.getDamager() instanceof LivingEntity && !(event.getDamager() instanceof Player)) {
            double damageReduction = 1 - (Ability.MOB_MASTER.getValue(playerSKill.getAbilityLevel(Ability.MOB_MASTER)) / 100);
            event.setDamage(event.getDamage() * damageReduction);
        }
    }

    public void immunity(EntityDamageByEntityEvent event, PlayerSkill playerSkill) {
        double chance = Ability.IMMUNITY.getValue(playerSkill.getAbilityLevel(Ability.IMMUNITY)) / 100;
        if (r.nextDouble() < chance) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void noDebuff(PotionSplashEvent event) {
        if (Options.isEnabled(Skill.DEFENSE)) {
            if (AureliumSkills.abilityOptionManager.isEnabled(Ability.NO_DEBUFF)) {
                for (LivingEntity entity : event.getAffectedEntities()) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        for (PotionEffect effect : event.getPotion().getEffects()) {
                            PotionEffectType type = effect.getType();
                            if (type.equals(PotionEffectType.POISON) || type.equals(PotionEffectType.UNLUCK) || type.equals(PotionEffectType.WITHER) ||
                                    type.equals(PotionEffectType.WEAKNESS) || type.equals(PotionEffectType.SLOW_DIGGING) || type.equals(PotionEffectType.SLOW) ||
                                    type.equals(PotionEffectType.HUNGER) || type.equals(PotionEffectType.HARM) || type.equals(PotionEffectType.CONFUSION) ||
                                    type.equals(PotionEffectType.BLINDNESS)) {
                                if (SkillLoader.playerSkills.containsKey(player.getUniqueId())) {
                                    PlayerSkill skill = SkillLoader.playerSkills.get(player.getUniqueId());
                                    double chance = Ability.NO_DEBUFF.getValue(skill.getAbilityLevel(Ability.NO_DEBUFF)) / 100;
                                    if (r.nextDouble() < chance) {
                                        if (!player.hasPotionEffect(type)) {
                                            event.setIntensity(entity, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void noDebuffFire(EntityDamageByEntityEvent event, PlayerSkill playerSkill, Player player, LivingEntity entity) {
        if (entity.getEquipment() != null) {
            if (entity.getEquipment().getItemInMainHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT) > 0) {
                double chance = Ability.NO_DEBUFF.getValue(playerSkill.getAbilityLevel(Ability.NO_DEBUFF)) / 100;
                if (r.nextDouble() < chance) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setFireTicks(0);
                        }
                    }.runTaskLater(plugin, 1L);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void defenseListener(EntityDamageByEntityEvent event) {
        if (Options.isEnabled(Skill.DEFENSE)) {
            if (!event.isCancelled()) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    //Check Permission
                    if (!player.hasPermission("aureliumskills.defense")) {
                        return;
                    }
                    if (SkillLoader.playerSkills.containsKey(player.getUniqueId())) {
                        PlayerSkill playerSkill = SkillLoader.playerSkills.get(player.getUniqueId());
                        AbilityOptionManager options = AureliumSkills.abilityOptionManager;
                        if (options.isEnabled(Ability.MOB_MASTER)) {
                            mobMaster(event, playerSkill);
                        }
                        if (options.isEnabled(Ability.SHIELDING)) {
                            shielding(event, playerSkill, player);
                        }
                        if (options.isEnabled(Ability.NO_DEBUFF)) {
                            if (event.getDamager() instanceof LivingEntity) {
                                LivingEntity entity = (LivingEntity) event.getDamager();
                                noDebuffFire(event, playerSkill, player, entity);
                            }
                        }
                        if (options.isEnabled(Ability.IMMUNITY)) {
                            immunity(event, playerSkill);
                        }
                    }
                }
            }
        }
    }
}
