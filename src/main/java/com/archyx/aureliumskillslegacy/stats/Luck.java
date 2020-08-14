package com.archyx.aureliumskillslegacy.stats;

import java.util.Random;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.Setting;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.archyx.aureliumskillslegacy.skills.SkillLoader;

public class Luck implements Listener {

	private final Random r = new Random();

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			if (event.getBlock().getType().isSolid()) {
				if (!event.getBlock().hasMetadata("skillsPlaced")) {
					if (SkillLoader.playerStats.containsKey(event.getPlayer().getUniqueId())) {
						PlayerStat stat = SkillLoader.playerStats.get(event.getPlayer().getUniqueId());
						Material mat = event.getBlock().getType();
						if (mat.equals(Material.STONE) || mat.equals(Material.COBBLESTONE) || mat.equals(Material.SAND) || mat.equals(Material.GRAVEL)
								|| mat.equals(Material.DIRT) || mat.equals(Material.GRASS)) {
							if ((double) stat.getStatLevel(Stat.LUCK) * Options.getDoubleOption(Setting.DOUBLE_DROP_MODIFIER) < Options.getDoubleOption(Setting.DOUBLE_DROP_PERCENT_MAX) / 100) {
								if (r.nextDouble() < ((double) stat.getStatLevel(Stat.LUCK) * Options.getDoubleOption(Setting.DOUBLE_DROP_MODIFIER))) {
									for (ItemStack item : event.getBlock().getDrops()) {
										event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.5, 0.5), item);
									}
								}
							}
							else {
								if (r.nextDouble() < Options.getDoubleOption(Setting.DOUBLE_DROP_PERCENT_MAX) / 100) {
									for (ItemStack item : event.getBlock().getDrops()) {
										event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.5, 0.5), item);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
}
