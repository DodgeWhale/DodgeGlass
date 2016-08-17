package net.dodgewhale.glass.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;

import lombok.Getter;
import net.dodgewhale.glass.GlassMain;
import net.dodgewhale.glass.objects.DodgePlayer;
import net.dodgewhale.glass.objects.ProcessTimer;
import net.dodgewhale.glass.utils.MessageUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerData {
	// https://sites.google.com/site/gson/gson-user-guide#TOC-Using-Gson
	
	private static GlassMain plugin = GlassMain.getInstance();
	@Getter
	private static HashMap<String, DodgePlayer> all = new HashMap<>();
	
	public static void enable() {
		// TODO Move into the save method incase the folder is deleted while the servers running
		new File(plugin.getDataFolder() + "/players").mkdir();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			PlayerData.load(player, true);
		}
	}
	
	public static void disable() {
		for(Entry<String, DodgePlayer> entry : PlayerData.getAll().entrySet()) {
			PlayerData.save(entry.getValue(), true);
		}
	}
	
	public static void main(String args[]) {
		System.out.println("Hej");
		
		// For converting and parsing dates
		// http://stackoverflow.com/questions/4216745/java-string-to-date-conversion
		String date = new SimpleDateFormat("dd MMMM yyyy - h:mma").format(new Date());
		System.out.println(date);
	}
	
	// TODO https://www.mkyong.com/java/how-to-enable-pretty-print-json-output-gson/s
	public static boolean save(DodgePlayer dPlayer, boolean remove) {
		if(dPlayer == null) return false;
		
		ProcessTimer timer = new ProcessTimer();
		try {
			FileWriter writer = new FileWriter(PlayerData.getFilePath(dPlayer.getUUID()));
			
			writer.write(plugin.getGson().toJson(dPlayer));
			writer.flush();
			writer.close();
			
			if(remove)
				PlayerData.getAll().remove(dPlayer.getUUID());			
			MessageUtil.log(Level.INFO, "Player data saved for " + dPlayer.getName() + " (" + timer.calculate() + ")");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			
			MessageUtil.log(Level.WARNING, "Unable to save player data for " + dPlayer.getName());
			return false;
		}
	}
	
	public static DodgePlayer load(Player player, boolean save) {
		File file = new File(PlayerData.getFilePath(player.getUniqueId().toString()));
		
		ProcessTimer timer = new ProcessTimer();
		if(file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
				
				DodgePlayer dPlayer = plugin.getGson().fromJson(br, DodgePlayer.class);
				br.close();
				
				// Can't add to the map in the object constructor because of problems with fromJson
				if(save)
					PlayerData.getAll().put(dPlayer.getUUID(), dPlayer);
				
				MessageUtil.log(Level.INFO, "Player data loaded for " + dPlayer.getName() + " (" + timer.calculate() + ")");
				return dPlayer;
			} catch (IOException e) {
				e.printStackTrace();
				
				MessageUtil.log(Level.WARNING, "Unable to load player data for " + player.getName());
				return null;
			}
		} else {
			return new DodgePlayer(player);
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
	
	// TODO Test the plugin on the server and change file path if necessary
	public static String getFilePath(String uuid) {
		return plugin.getDataFolder() + "/players/" + uuid + ".json";
	}
}