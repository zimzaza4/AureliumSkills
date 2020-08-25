package com.archyx.aureliumskillslegacy.skills.levelers;

import com.archyx.aureliumskillslegacy.skills.Skill;
import com.archyx.aureliumskillslegacy.skills.abilities.MiningAbilities;
import com.archyx.aureliumskillslegacy.AureliumSkills;
import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.skills.Source;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningLeveler implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (Options.isEnabled(Skill.MINING)) {
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
			Skill s = Skill.MINING;
			Material mat = event.getBlock().getType();
			//Check for permission
			if (!p.hasPermission("aureliumskills.mining")) {
				return;
			}
			if (mat.equals(Material.STONE)) {
				switch (b.getData()) {
					case 0:
						Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.STONE));
						break;
					case 1:
						Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.GRANITE));
						break;
					case 2:
						Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.DIORITE));
						break;
					case 3:
						Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.ANDESITE));
						break;
				}

			}
			else if (mat.equals(Material.COBBLESTONE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.COBBLESTONE));
			}
			else if (mat.equals(Material.COAL_ORE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.COAL_ORE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.QUARTZ_ORE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.QUARTZ_ORE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.IRON_ORE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.IRON_ORE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.REDSTONE_ORE) || mat.equals(Material.GLOWING_REDSTONE_ORE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.REDSTONE_ORE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.LAPIS_ORE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.LAPIS_ORE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.GOLD_ORE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.GOLD_ORE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.DIAMOND_ORE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.DIAMOND_ORE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.EMERALD_ORE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.EMERALD_ORE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.NETHERRACK)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.NETHERRACK));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.ENDER_STONE)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.END_STONE));
				applyAbilities(p, b);
			}
			else if (mat.equals(Material.OBSIDIAN)) {
				Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.OBSIDIAN));
				applyAbilities(p, b);
			}
			else {
				if (mat.equals(Material.HARD_CLAY)) {
					Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.TERRACOTTA));
					applyAbilities(p, b);
				}
				else if (mat.equals(Material.STAINED_CLAY)) {
					switch (b.getData()) {
						case 0:
							Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.WHITE_TERRACOTTA));
							applyAbilities(p, b);
							break;
						case 1:
							Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.ORANGE_TERRACOTTA));
							applyAbilities(p, b);
							break;
						case 4:
							Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.YELLOW_TERRACOTTA));
							applyAbilities(p, b);
							break;
						case 8:
							Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.LIGHT_GRAY_TERRACOTTA));
							applyAbilities(p, b);
							break;
						case 12:
							Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.BROWN_TERRACOTTA));
							applyAbilities(p, b);
							break;
						case 14:
							Leveler.addXp(p, s, MiningAbilities.getModifiedXp(p, Source.RED_TERRACOTTA));
							applyAbilities(p, b);
							break;
					}
				}
			}
		}
	}

	private void applyAbilities(Player p, Block b) {
		MiningAbilities.luckyMiner(p, b);
	}
}