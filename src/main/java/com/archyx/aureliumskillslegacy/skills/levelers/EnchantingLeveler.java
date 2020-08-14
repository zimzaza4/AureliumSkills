package com.archyx.aureliumskillslegacy.skills.levelers;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.skills.Skill;
import com.archyx.aureliumskillslegacy.util.ItemUtils;
import com.archyx.aureliumskillslegacy.AureliumSkills;
import com.archyx.aureliumskillslegacy.skills.Source;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantingLeveler implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEnchant(EnchantItemEvent event) {
		if (Options.isEnabled(Skill.ENCHANTING)) {
			if (event.isCancelled() == false) {
				//Checks if in blocked world
				if (AureliumSkills.worldManager.isInBlockedWorld(event.getEnchantBlock().getLocation())) {
					return;
				}
				//Checks if in blocked region
				if (AureliumSkills.worldGuardEnabled) {
					if (AureliumSkills.worldGuardSupport.isInBlockedRegion(event.getEnchantBlock().getLocation())) {
						return;
					}
				}
				Player p = event.getEnchanter();
				Material mat = event.getItem().getType();
				if (ItemUtils.isArmor(mat)) {
					Leveler.addXp(p, Skill.ENCHANTING, event.getExpLevelCost() * Options.getXpAmount(Source.ARMOR_PER_LEVEL));
				}
				else if (ItemUtils.isWeapon(mat)) {
					Leveler.addXp(p, Skill.ENCHANTING, event.getExpLevelCost() * Options.getXpAmount(Source.WEAPON_PER_LEVEL));
				}
				else if (mat.equals(Material.BOOK)) {
					Leveler.addXp(p, Skill.ENCHANTING, event.getExpLevelCost() * Options.getXpAmount(Source.BOOK_PER_LEVEL));
				}
				else {
					Leveler.addXp(p, Skill.ENCHANTING, event.getExpLevelCost() * Options.getXpAmount(Source.TOOL_PER_LEVEL));
				}
			}
		}
	}
	
}
