package com.archyx.aureliumskillslegacy.skills.levelers;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.skills.Skill;
import com.archyx.aureliumskillslegacy.skills.abilities.ForagingAbilities;
import com.archyx.aureliumskillslegacy.AureliumSkills;
import com.archyx.aureliumskillslegacy.skills.Source;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ForagingLeveler implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (Options.isEnabled(Skill.FORAGING)) {
			if (!event.isCancelled()) {
				//Checks if in blocked world
				if (AureliumSkills.worldManager.isInBlockedWorld(event.getBlock().getLocation())) {
					return;
				}
				//Checks if in blocked region
				if (AureliumSkills.worldGuardEnabled) {
					if (AureliumSkills.worldGuardSupport.isInBlockedRegion(event.getBlock().getLocation())) {
						return;
					}
				}
				//Check block replace
				if (Options.checkBlockReplace) {
					if (event.getBlock().hasMetadata("skillsPlaced")) {
						return;
					}
				}
				Player p = event.getPlayer();
				Block b = event.getBlock();
				Skill s = Skill.FORAGING;
				Material mat = event.getBlock().getType();
				//If legacy material LOG
				if (mat.equals(Material.LOG)) {
					switch (event.getBlock().getData()) {
						case 0:
							Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.OAK_LOG));
							applyAbilities(p, b);
							break;
						case 1:
							Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.SPRUCE_LOG));
							applyAbilities(p, b);
							break;
						case 2:
							Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.BIRCH_LOG));
							applyAbilities(p, b);
							break;
						case 3:
							Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.JUNGLE_LOG));
							applyAbilities(p, b);
							break;
					}
				}
				//If legacy material LOG_2
				else if (mat.equals(Material.LOG_2)) {
					switch (event.getBlock().getData()) {
						case 0:
							Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.ACACIA_LOG));
							applyAbilities(p, b);
							break;
						case 1:
							Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.DARK_OAK_LOG));
							applyAbilities(p, b);
							break;
					}
				}
				//If legacy material LEAVES
				else if (mat.equals(Material.LEAVES)) {
					byte data = event.getBlock().getData();
					if (data == 0 || data == 8) {
						Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.OAK_LEAVES));
						applyAbilities(p, b);
					} else if (data == 1 || data == 9) {
						Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.SPRUCE_LEAVES));
						applyAbilities(p, b);
					} else if (data == 2 || data == 10) {
						Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.BIRCH_LEAVES));
						applyAbilities(p, b);
					} else if (data == 3 || data == 11) {
						Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.JUNGLE_LEAVES));
						applyAbilities(p, b);
					}
				}
				//If legacy material LEAVES_2
				else if (mat.equals(Material.LEAVES_2)) {
					byte data = event.getBlock().getData();
					if (data == 0 || data == 8) {
						Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.ACACIA_LEAVES));
						applyAbilities(p, b);
					} else if (data == 1 || data == 9) {
						Leveler.addXp(p, s, ForagingAbilities.getModifiedXp(p, Source.DARK_OAK_LEAVES));
						applyAbilities(p, b);
					}
				}
			}
		}
	}
	
	private void applyAbilities(Player p, Block b) {
		ForagingAbilities.lumberjack(p, b);
	}
}
