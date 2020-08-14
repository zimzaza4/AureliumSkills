package com.archyx.aureliumskillslegacy.skills.levelers;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.skills.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class ForgingLeveler implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onForge(InventoryClickEvent event) {
		if (Options.isEnabled(Skill.FORGING)) {
	 		if (!event.isCancelled()) {
				if (event.getClickedInventory() != null) {
					if (event.getClickedInventory().getType().equals(InventoryType.ANVIL)) {
						if (event.getSlot() == 2) {
							if (event.getAction().equals(InventoryAction.PICKUP_ALL) || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
								if (event.getWhoClicked() instanceof Player) {
									ItemStack addedItem = event.getClickedInventory().getItem(1);
									ItemStack baseItem = event.getClickedInventory().getItem(0);
									Player p = (Player) event.getWhoClicked();
									Skill s = Skill.FORGING;
									AnvilInventory inventory = (AnvilInventory) event.getClickedInventory();
									/*
									TO FIX
									 */
								}
							}
						}
					}
				}
			}
		}
	}
	
}
