package net.dodgewhale.glass.utils;

import org.bukkit.Location;

public class BukkitUtil {

	public static String formatLocation(Location loc) {
		return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
	}
	
}