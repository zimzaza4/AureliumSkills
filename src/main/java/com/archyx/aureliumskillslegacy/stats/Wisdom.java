package com.archyx.aureliumskillslegacy.stats;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.Setting;
import com.archyx.aureliumskillslegacy.skills.SkillLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class Wisdom implements Listener {

	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent event) {
		if (SkillLoader.playerStats.containsKey(event.getPlayer().getUniqueId())) {
			PlayerStat stat = SkillLoader.playerStats.get(event.getPlayer().getUniqueId());
			event.setAmount((int) (event.getAmount() * (1 + (stat.getStatLevel(Stat.WISDOM) * Options.getDoubleOption(Setting.EXPERIENCE_MODIFIER)))));
		}
	}
}
