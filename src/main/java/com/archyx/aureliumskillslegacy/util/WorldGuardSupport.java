package com.archyx.aureliumskillslegacy.util;

import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.List;

public class WorldGuardSupport {

    private RegionContainer container;
    private Plugin plugin;
    private List<String> blockedRegions;
    private List<String> blockedCheckBlockReplaceRegions;

    public WorldGuardSupport(Plugin plugin) {
        this.plugin = plugin;
    }

    public void loadRegions() {
        container = ((WorldGuardPlugin) plugin.getServer().getPluginManager().getPlugin("WorldGuard")).getRegionContainer();
        FileConfiguration config = plugin.getConfig();
        blockedRegions = new LinkedList<>();
        blockedRegions.addAll(config.getStringList("blocked-regions"));
        blockedCheckBlockReplaceRegions = new LinkedList<>();
        blockedCheckBlockReplaceRegions.addAll(config.getStringList("blocked-check-block-replace-regions"));
    }

    public boolean isInBlockedRegion(Location location) {
        RegionManager regions = container.get(location.getWorld());
        if (regions == null) {
            return false;
        }
        ApplicableRegionSet set = regions.getApplicableRegions(BukkitUtil.toVector(location));
        for (ProtectedRegion region : set) {
            if (blockedRegions.contains(region.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isInBlockedCheckRegion(Location location) {
        RegionManager regions = container.get(location.getWorld());
        if (regions == null) {
            return false;
        }
        ApplicableRegionSet set = regions.getApplicableRegions(BukkitUtil.toVector(location));
        for (ProtectedRegion region : set) {
            if (blockedCheckBlockReplaceRegions.contains(region.getId())) {
                return true;
            }
        }
        return false;
    }

}
