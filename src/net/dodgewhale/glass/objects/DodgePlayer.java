package net.dodgewhale.glass.objects;

import java.util.HashMap;

import net.dodgewhale.glass.data.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class DodgePlayer {
	// transient makes it so Gson wont serialize the variable
	
	private String uuid, name;
	private transient HealthBar healthBar;
	
	private HashMap<String, Cooldown> cooldowns = new HashMap<>();
	
	public DodgePlayer(Player player) {
		this.uuid = player.getUniqueId().toString();
		this.name = player.getName();
		
		this.healthBar = new HealthBar(player);
		
		// Store object in global map
		PlayerData.getAll().put(this.getUUID(), this);
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