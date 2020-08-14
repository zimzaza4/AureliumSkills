package com.archyx.aureliumskillslegacy.skills.levelers;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.skills.Skill;
import com.archyx.aureliumskillslegacy.AureliumSkills;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class HealingLeveler implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsume(PlayerItemConsumeEvent event) {
		if (Options.isEnabled(Skill.HEALING)) {
			if (!event.isCancelled()) {
				//Checks if in blocked world
				if (AureliumSkills.worldManager.isInBlockedWorld(event.getPlayer().getLocation())) {
					return;
				}
				//Checks if in blocked region
				if (AureliumSkills.worldGuardEnabled) {
					if (AureliumSkills.worldGuardSupport.isInBlockedRegion(event.getPlayer().getLocation())) {
						return;
					}
				}
				if (event.getItem().getType().equals(Material.POTION)) {
					if (event.getItem().getItemMeta() instanceof PotionMeta) {
						PotionMeta meta = (PotionMeta) event.getItem().getItemMeta();
						for (PotionEffect effect : meta.getCustomEffects()) {
							Skill s = Skill.HEALING;
							/*
							FIX THIS
							*/
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onThrow(PotionSplashEvent event) {
		if (Options.isEnabled(Skill.HEALING)) {
			if (!event.isCancelled()) {
				if (event.getPotion().getEffects().size() > 0) {
					if (event.getEntity().getShooter() instanceof Player) {
						if (event.getPotion().getItem().getItemMeta() instanceof PotionMeta) {
							Player p = (Player) event.getEntity().getShooter();
							PotionMeta meta = (PotionMeta) event.getPotion().getItem().getItemMeta();
							/*
							FIX THIS
							*/
						}
					}
				}
			}
		}
	}
	
}
