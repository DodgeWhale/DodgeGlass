package net.dodgewhale.glass.objects;

import org.bukkit.entity.Player;

public class DodgePlayer {
	// transient makes it so Gson wont serialize the variable
	
	private String uuid, name;
	private transient HealthBar healthBar;
	
	public DodgePlayer(Player player) {
		this.uuid = player.getUniqueId().toString();
		this.name = player.getName();
	}

	public String getUUID() {
		return uuid;
	}

	public String getName() {
		return name;
	}
	
	public HealthBar getHealthBar() {
		return healthBar;
	}
	
	public boolean hasHealthBar() {
		return this.getHealthBar() != null;
	}
	
	public void setHealthBar(HealthBar bar) {
		this.healthBar = bar;
	}
	
}