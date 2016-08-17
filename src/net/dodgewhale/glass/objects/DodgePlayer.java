package net.dodgewhale.glass.objects;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import net.dodgewhale.glass.data.PlayerData;
import net.dodgewhale.glass.utils.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class DodgePlayer {
	// transient makes it so Gson wont serialize the variable
	// http://stackoverflow.com/questions/4216745/java-string-to-date-conversion
	
	@Getter
	private String UUID, name;
	
	@Getter @Setter
	private String lastLogin;
	
	private transient HealthBar healthBar;
	
	@Getter
	private HashMap<String, Cooldown> cooldowns = new HashMap<>();
	
	public DodgePlayer(Player player) {
		this.UUID = player.getUniqueId().toString();
		this.name = player.getName();
		
		// Bukkit.broadcastMessage(StringUtil.getDate());
		this.lastLogin = StringUtil.getDate();
		
		// I might not want to store the object in the map if I add a command to check the player's
		// data when they're offline in which case this would probably have to be moved
		PlayerData.getAll().put(this.getUUID(), this);
	}

	// A constructor with no args might be needed for the Gson.fromJson method to work
	public DodgePlayer() { }
	
	public Player getPlayer() {
		return Bukkit.getPlayer(this.getName());
	}
	
	/**
	 * Shows or hides the health bar based on whether<br>
	 * they already have it toggled on
	 */
	public void toggleHealthBar() {
		Player player = this.getPlayer();
		BossBar bar = this.getHealthBar().getBar();
		
		if(bar.getPlayers().contains(player)) {
			bar.removePlayer(player);
			new ActionBar("&bHealth bar has been hidden").send(player);
		} else {
			bar.addPlayer(player);
			new ActionBar("&bHealth bar is now shown").send(player);
		}
	}
	
	// Added init health bar if null to avoid loading the bar in the constructor
	public HealthBar getHealthBar() {
		if(this.healthBar == null)
			this.healthBar = new HealthBar(this.getPlayer());
			
		return this.healthBar;
	}
	
	public long getCooldown(String key) {
		if(!this.getCooldowns().containsKey(key))
			return 0;
		return this.getCooldowns().get(key).calculateRemainder();
	}
	
	public long setCooldown(String key, double duration) {
		Cooldown cooldown = new Cooldown(duration);
		
		this.getCooldowns().put(key, cooldown);
		return cooldown.calculateRemainder();
	}
	
	/**
	 * Used to check if a previous cooldown has expired or create a new
	 * @param key Cooldown ID
	 * @param duration How long the cooldown is in seconds
	 * @return True if the cooldown expired or has been created
	 */
	public boolean tryCooldown(String key, double duration) {
		if(this.getCooldown(key) <= 0) {
			this.setCooldown(key, duration);
			return true;
		}
		return false;
	}
}