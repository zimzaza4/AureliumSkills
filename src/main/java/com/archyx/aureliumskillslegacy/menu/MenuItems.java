package com.archyx.aureliumskillslegacy.menu;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.archyx.aureliumskillslegacy.lang.Lang;
import com.archyx.aureliumskillslegacy.lang.Message;

public class MenuItems {

	public static ItemStack getEmptyPane() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1 ,(short) 15);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getBackButton(String backTo) {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + Lang.getMessage(Message.GO_BACK));
		List<String> lore = new LinkedList<String>();
		lore.add(ChatColor.GRAY + backTo);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getCloseButton() {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RED + Lang.getMessage(Message.CLOSE));
		item.setItemMeta(meta);
		return item;
	}
	
}
