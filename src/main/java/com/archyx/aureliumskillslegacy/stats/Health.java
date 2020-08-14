package com.archyx.aureliumskillslegacy.stats;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.Setting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.archyx.aureliumskillslegacy.skills.SkillLoader;

public class Health implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		setHealth(event.getPlayer());
	}
	
	public static void reload(Player player) {
		setHealth(player);
	}
	
	private static void setHealth(Player player) {
		//Calculates the amount of health to add
		double modifier = ((double) SkillLoader.playerStats.get(player.getUniqueId()).getStatLevel(Stat.HEALTH)) * Options.getDoubleOption(Setting.HEALTH_MODIFIER);
		player.resetMaxHealth();
		player.setMaxHealth(player.getMaxHealth() + modifier);
		//Applies health scaling
		if (Options.getBooleanOption(Setting.HEALTH_SCALING)) {
			double health = player.getMaxHealth();
			if (health < 23) {
				player.setHealthScale(20.0);
				player.setHealthScaled(true);
			}
			else if (health < 28) {
				player.setHealthScale(22.0);
				player.setHealthScaled(true);
			}
			else if (health < 36) {
				player.setHealthScale(24.0);
				player.setHealthScaled(true);
			}
			else if (health < 49) {
				player.setHealthScale(26.0);
				player.setHealthScaled(true);
			}
			else if (health < 70) {
				player.setHealthScale(28.0);
				player.setHealthScaled(true);
			}
			else if (health < 104) {
				player.setHealthScale(30.0);
				player.setHealthScaled(true);
			}
			else if (health < 159) {
				player.setHealthScale(32.0);
				player.setHealthScaled(true);
			}
			else if (health < 248) {
				player.setHealthScale(34.0);
				player.setHealthScaled(true);
			}
			else if (health < 392) {
				player.setHealthScale(36.0);
				player.setHealthScaled(true);
			}
			else if (health < 625) {
				player.setHealthScale(38.0);
				player.setHealthScaled(true);
			}
			else {
				player.setHealthScale(40.0);
				player.setHealthScaled(true);
			}
		}
		else {
			player.setHealthScaled(false);
		}
	}
}
