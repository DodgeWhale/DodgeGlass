package net.dodgewhale.glass.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.md_5.bungee.api.ChatColor;

import org.apache.commons.lang.WordUtils;

public class StringUtil {

	public static String format(String string) {
		return WordUtils.capitalizeFully(string.trim().toLowerCase().replaceAll("_", " "));
	}
	
	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static String getDate() {
		return new SimpleDateFormat("dd MMMM yyyy - h:mma").format(new Date());
	}
	
}