package com.archyx.aureliumskillslegacy.skills.abilities;

import java.util.Random;

import com.archyx.aureliumskillslegacy.Options;
import com.archyx.aureliumskillslegacy.skills.Skill;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.archyx.aureliumskillslegacy.AureliumSkills;
import com.archyx.aureliumskillslegacy.skills.PlayerSkill;
import com.archyx.aureliumskillslegacy.skills.SkillLoader;
import com.archyx.aureliumskillslegacy.skills.Source;
import com.archyx.aureliumskillslegacy.skills.levelers.Leveler;

public class FishingAbilities implements Listener {

	private static Random r = new Random();
	
	public static double getModifiedXp(Player player, Source source) {
		PlayerSkill skill = SkillLoader.playerSkills.get(player.getUniqueId());
		double output = Options.getXpAmount(source);
		double modifier = 1;
		modifier += Ability.FISHER.getValue(skill.getAbilityLevel(Ability.FISHER)) / 100;
		output *= modifier;
		return output;
	}
	
	@EventHandler
	public void luckyCatch(PlayerFishEvent event) {
		if (Options.isEnabled(Skill.FISHING)) {
			if (event.getCaught() instanceof Item) {
				if (SkillLoader.playerSkills.containsKey(event.getPlayer().getUniqueId())) {
					PlayerSkill skill = SkillLoader.playerSkills.get(event.getPlayer().getUniqueId());
					if (r.nextDouble() < (Ability.LUCKY_CATCH.getValue(skill.getAbilityLevel(Ability.LUCKY_CATCH)) / 100)) {
						Item item = (Item) event.getCaught();
						ItemStack drop = item.getItemStack();
						if (drop.getMaxStackSize() > 1) {
							drop.setAmount(drop.getAmount() * 2);
							item.setItemStack(drop);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void treasureHunterAndEpicCatch(PlayerFishEvent event) {
		if (Options.isEnabled(Skill.FISHING)) {
			if (event.getCaught() instanceof Item) {
				if (SkillLoader.playerSkills.containsKey(event.getPlayer().getUniqueId())) {
					PlayerSkill skill = SkillLoader.playerSkills.get(event.getPlayer().getUniqueId());
					if (r.nextDouble() < (Ability.EPIC_CATCH.getValue(skill.getAbilityLevel(Ability.EPIC_CATCH)) / 100)) {
						Item item = (Item) event.getCaught();
						ItemStack drop = AureliumSkills.lootTableManager.getLootTable("fishing-epic").getLoot().get(r.nextInt(AureliumSkills.lootTableManager.getLootTable("fishing-epic").getLoot().size())).getDrop();
						item.setItemStack(drop);
						Leveler.addXp(event.getPlayer(), Skill.FISHING, getModifiedXp(event.getPlayer(), Source.FISHING_EPIC));
					}
					else if (r.nextDouble() < (Ability.TREASURE_HUNTER.getValue(skill.getAbilityLevel(Ability.TREASURE_HUNTER)) / 100)) {
						Item item = (Item) event.getCaught();
						ItemStack drop = AureliumSkills.lootTableManager.getLootTable("fishing-rare").getLoot().get(r.nextInt(AureliumSkills.lootTableManager.getLootTable("fishing-rare").getLoot().size())).getDrop();
						item.setItemStack(drop);
						Leveler.addXp(event.getPlayer(), Skill.FISHING, getModifiedXp(event.getPlayer(), Source.FISHING_RARE));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void grappler(PlayerFishEvent event) {
		if (Options.isEnabled(Skill.FISHING)) {
			if (event.getCaught() != null) {
				if (!(event.getCaught() instanceof Item)) {
					if (SkillLoader.playerSkills.containsKey(event.getPlayer().getUniqueId())) {
						PlayerSkill skill = SkillLoader.playerSkills.get(event.getPlayer().getUniqueId());
						Player player = event.getPlayer();
						Vector vector = player.getLocation().toVector().subtract(event.getCaught().getLocation().toVector());
						event.getCaught().setVelocity(vector.multiply(0.004 + (Ability.GRAPPLER.getValue(skill.getAbilityLevel(Ability.GRAPPLER)) / 25000)));
					}
				}
			}
		}
	}
}
