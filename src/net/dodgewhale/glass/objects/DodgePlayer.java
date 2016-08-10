package net.dodgewhale.glass.objects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import net.dodgewhale.glass.data.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class DodgePlayer {
	// transient makes it so Gson wont serialize the variable
	
	private String uuid, name, lastLogin;
	private transient HealthBar healthBar;
	
	private HashMap<String, Cooldown> cooldowns = new HashMap<>();
	
	public DodgePlayer(Player player) {
		this.uuid = player.getUniqueId().toString();
		this.name = player.getName();
		
		this.lastLogin = new SimpleDateFormat("dd MMMM yyyy - h:mma").format(new Date());
		this.healthBar = new HealthBar(player);
		
		// I might not want to store the object in the map if I add a command to check the player's
		// data when they're offline in which case this would probably have to be moved
		PlayerData.getAll().put(this.getUUID(), this);
	}

	// A constructor with no args might be needed for the Gson.fromJson method to work
	public DodgePlayer() {
		
	}
	
	public String getUUID() {
		return uuid;
	}

	public String getName() {
		return name;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(this.getName());
	}
	
	public String getLastLogin() {
		return this.lastLogin;
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
	
	public HealthBar getHealthBar() {
		return healthBar;
	}
	
	public HashMap<String, Cooldown> getCooldowns() {
		return this.cooldowns;
	}
	
	public void createCooldown(String key, double duration) {
		this.getCooldowns().put(key, new Cooldown(duration));
	}
	
	/**
	 * Checks if the cooldown has expired or creates<br>
	 * a new one if it doesn't exist yet
	 * @return Whether or not the attempted task can proceed
	 */
	public boolean checkCooldown(String key, double duration) {
		if(this.hasCooldown(key)) {
			return this.checkCooldown(key);
		}
		this.createCooldown(key, duration);
		return true;
	}
	
	/**
	 * Used to check if a cooldown has expired
	 * @param key Name of the cooldown
	 * @return Whether or not the cooldown has finished
	 */
	public boolean checkCooldown(String key) {
		if(this.hasCooldown(key)) {
			if(this.getCooldowns().get(key).hasExpired()) {
				this.removeCooldown(key);
				return true;
			}
		}
		return false;
	}
	
	public boolean hasCooldown(String key) {
		return this.getCooldowns().containsKey(key);
	}
	
	public void removeCooldown(String key) {
		this.getCooldowns().remove(key);
	}
	
}