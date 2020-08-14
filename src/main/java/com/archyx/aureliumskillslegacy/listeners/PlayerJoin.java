package com.archyx.aureliumskillslegacy.listeners;

import com.archyx.aureliumskillslegacy.skills.PlayerSkill;
import com.archyx.aureliumskillslegacy.skills.SkillLoader;
import com.archyx.aureliumskillslegacy.stats.PlayerStat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayJoin(PlayerJoinEvent event) {
		if (!SkillLoader.playerSkills.containsKey(event.getPlayer().getUniqueId())) {
			SkillLoader.playerSkills.put(event.getPlayer().getUniqueId(), new PlayerSkill(event.getPlayer().getUniqueId(), event.getPlayer().getName()));
		}
		else {
			SkillLoader.playerSkills.get(event.getPlayer().getUniqueId()).setPlayerName(event.getPlayer().getName());
		}
		if (!SkillLoader.playerStats.containsKey(event.getPlayer().getUniqueId())) {
			SkillLoader.playerStats.put(event.getPlayer().getUniqueId(), new PlayerStat(event.getPlayer().getUniqueId()));
		}
	}
}
