package com.archyx.aureliumskillslegacy.skills.levelers;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.skills.Skill;
import com.archyx.aureliumskillslegacy.skills.Source;
import com.google.common.collect.Sets;
import com.archyx.aureliumskillslegacy.AureliumSkills;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;
import java.util.UUID;

public class AgilityLeveler implements Listener {
	
	private Set<UUID> prevPlayersOnGround = Sets.newHashSet();
	private Plugin plugin;
	
	public AgilityLeveler(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onFall(EntityDamageEvent event) {
		if (Options.isEnabled(Skill.AGILITY)) {
			if (!event.isCancelled()) {
				if (event.getCause().equals(DamageCause.FALL)) {
					if (event.getEntity() instanceof Player) {
						Player player = (Player) event.getEntity();
						//Checks if in blocked region
						if (AureliumSkills.worldGuardEnabled) {
							if (AureliumSkills.worldGuardSupport.isInBlockedRegion(player.getLocation())) {
								return;
							}
						}
						if (event.getFinalDamage() < player.getHealth()) {
							Leveler.addXp(player, Skill.AGILITY, event.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE) * Options.getXpAmount(Source.FALL_DAMAGE));
						}
					}
				}
			}
		}
	}
	
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
    	if (Options.isEnabled(Skill.AGILITY)) {
			Player player = e.getPlayer();
			//Checks if in blocked world
			if (AureliumSkills.worldManager.isInBlockedWorld(player.getLocation())) {
				return;
			}
			//Checks if in blocked region
			if (AureliumSkills.worldGuardEnabled) {
				if (AureliumSkills.worldGuardSupport.isInBlockedRegion(player.getLocation())) {
					return;
				}
			}
	        if (player.getVelocity().getY() > 0) {
	            double jumpVelocity = (double) 0.42F;
	            if (player.hasPotionEffect(PotionEffectType.JUMP)) {
	            	int jumpAmplifier = 0;
	            	for (PotionEffect effect : player.getActivePotionEffects()) {
	            		if (effect.getType().equals(PotionEffectType.JUMP)) {
	            			jumpAmplifier = effect.getAmplifier();
						}
					}
	                jumpVelocity += (double) ((float) (jumpAmplifier + 1) * 0.1F);
	            }
	            if (e.getPlayer().getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player.getUniqueId())) {
	                if (!player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0) {
	                	if (player.hasMetadata("skillsJumps")) {
	                		player.setMetadata("skillsJumps", new FixedMetadataValue(plugin, player.getMetadata("skillsJumps").get(0).asInt() + 1));
	                		if (player.getMetadata("skillsJumps").get(0).asInt() >= 100) {
	                			Leveler.addXp(player, Skill.AGILITY, Source.JUMP_PER_100);
	                			player.removeMetadata("skillsJumps", plugin);
	                		}
	                	}
	                	else {
	                		player.setMetadata("skillsJumps", new FixedMetadataValue(plugin, 1));
	                	}
	                }
	            }
	        }
	        if (player.isOnGround()) {
	            prevPlayersOnGround.add(player.getUniqueId());
	        } else {
	            prevPlayersOnGround.remove(player.getUniqueId());
	        }
    	}
    }
	
}
