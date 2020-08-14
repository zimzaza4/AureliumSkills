package com.archyx.aureliumskillslegacy.stats;

import com.archyx.aureliumskillslegacy.magic.ManaManager;
import com.archyx.aureliumskillslegacy.AureliumSkills;
import org.bukkit.entity.Player;

public class StatLeveler {

	public static void reloadStat(Player player, Stat stat) {
		if (stat.equals(Stat.HEALTH)) {
			Health.reload(player);
		}
		else if (stat.equals(Stat.WISDOM)) {
			ManaManager manaManager = AureliumSkills.manaManager;
			if (manaManager.getMana(player.getUniqueId()) > manaManager.getMaxMana(player.getUniqueId())) {
				manaManager.setMana(player.getUniqueId(), manaManager.getMaxMana(player.getUniqueId()));
			}
		}
	}
	
}
