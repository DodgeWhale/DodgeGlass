package net.dodgewhale.glass.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import net.dodgewhale.glass.GlassMain;
import net.dodgewhale.glass.objects.DodgePlayer;
import net.dodgewhale.glass.utils.MessageUtil;

import org.bukkit.entity.Player;

public class PlayerData {

	private static GlassMain plugin = GlassMain.getInstance();
	private static HashMap<String, DodgePlayer> all = new HashMap<>();
	
	public static HashMap<String, DodgePlayer> getAll() {
		return PlayerData.all;
	}
	
	public void unload() {
		// TODO Save all player data
	}
	
	public static void main(String args[]) {
		System.out.println("Hej");
		
		// For converting and parsing dates
		// http://stackoverflow.com/questions/4216745/java-string-to-date-conversion
		String date = new SimpleDateFormat("dd MMMM yyyy - h:mma").format(new Date());
		System.out.println(date);
	}
	
	// TODO add "/players/" prefix
	public void save(DodgePlayer dPlayer) {
		try {
			FileWriter writer = new FileWriter(dPlayer.getUUID() + ".json");
			
			writer.write(plugin.getGson().toJson(dPlayer));
			writer.close();
		} catch (IOException e) {
			// e.printStackTrace();
			MessageUtil.log(Level.WARNING, "Unable to save player data for " + dPlayer.getName());
		}
	}
	
	public void load(Player player) {
		File file = new File(player.getUniqueId().toString() + ".json");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			
			plugin.getGson().fromJson(br, DodgePlayer.class);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is used to find a DodgePlayer object based on the object given<br>
	 * Supported objects: Player, String, UUID
	 * @param obj
	 * @return DodgePlayer object if the UUID exists in the data HashMap
	 */
	public static DodgePlayer find(Object obj) {
		String uuid = null;
		
		if(obj instanceof Player) {
			uuid = ((Player) obj).getUniqueId().toString();
		} else if(obj instanceof String) {
			uuid = (String) obj;
		} else if(obj instanceof UUID) {
			uuid = ((UUID) obj).toString();
		}
		
		return PlayerData.getAll().get(uuid);
	}
}