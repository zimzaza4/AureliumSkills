package com.archyx.aureliumskillslegacy.listeners;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.AureliumSkills;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class BlockPlace implements Listener {

	private Plugin plugin;
	
	public BlockPlace(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		//Checks if world is blocked
		if (AureliumSkills.worldManager.isInBlockedCheckWorld(event.getBlock().getLocation())) {
			return;
		}
		//Checks if region is blocked
		if (AureliumSkills.worldGuardEnabled) {
			if (AureliumSkills.worldGuardSupport.isInBlockedCheckRegion(event.getBlock().getLocation())) {
				return;
			}
		}
		//Checks if check block replace is enabled
		if (Options.checkBlockReplace) {
			Material mat = event.getBlock().getType();
			if (mat.equals(Material.LOG) || mat.equals(Material.LOG_2) || mat.equals(Material.PUMPKIN) ||
					mat.equals(Material.MELON) || mat.equals(Material.COAL_ORE) || mat.equals(Material.IRON_ORE) ||
					mat.equals(Material.GOLD_ORE) || mat.equals(Material.DIAMOND_ORE) || mat.equals(Material.EMERALD_ORE) ||
					mat.equals(Material.STONE) || mat.equals(Material.DIRT) || mat.equals(Material.SAND) ||
					mat.equals(Material.GRAVEL) || mat.equals(Material.COBBLESTONE) || mat.equals(Material.REDSTONE_ORE) ||
					mat.equals(Material.LAPIS_ORE) || mat.equals(Material.CLAY) || mat.equals(Material.GRASS) ||
					mat.equals(Material.MYCEL) || mat.equals(Material.SOUL_SAND) || mat.equals(Material.QUARTZ_ORE) ||
					mat.equals(Material.SUGAR_CANE_BLOCK) || mat.equals(Material.HARD_CLAY) || mat.equals(Material.STAINED_CLAY)) {
				event.getBlock().setMetadata("skillsPlaced", new FixedMetadataValue(plugin, true));
			}
		}
	}
	
}
