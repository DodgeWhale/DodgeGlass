package net.dodgewhale.glass;

import java.util.logging.Level;

import net.dodgewhale.glass.cmds.GamemodeCommand;
import net.dodgewhale.glass.cmds.HealthCommand;
import net.dodgewhale.glass.data.PlayerData;
import net.dodgewhale.glass.listeners.AsyncPlayerChat;
import net.dodgewhale.glass.listeners.BlockBreak;
import net.dodgewhale.glass.listeners.BlockPlace;
import net.dodgewhale.glass.listeners.EntityDamage;
import net.dodgewhale.glass.listeners.EntityExplode;
import net.dodgewhale.glass.listeners.EntityRegainHealth;
import net.dodgewhale.glass.listeners.PlayerJoin;
import net.dodgewhale.glass.listeners.PlayerQuit;
import net.dodgewhale.glass.utils.MessageUtil;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

public class GlassMain extends JavaPlugin {

	// TODO Remove health bar on player quit
	
	private static GlassMain instance;
	private Gson gson = new Gson();
	// private static HashMap<String, HealthBar> healthBars = new HashMap<>();
	
	@Override
	public void onEnable() {
		GlassMain.instance = this;
		
		PlayerData.enable();
		this.registerListeners();
		this.registerCommands();

		MessageUtil.log(Level.INFO, "Glass enabled.");
	}

	@Override
	public void onDisable() {
		PlayerData.disable();
		MessageUtil.log(Level.INFO, "Glass disabled.");
	}

	public static GlassMain getInstance() {
		return GlassMain.instance;
	}

	public Gson getGson() {
		return this.gson;
	}
	
	public void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		
		manager.registerEvents(new AsyncPlayerChat(), this);
		manager.registerEvents(new BlockBreak(), this);
		manager.registerEvents(new BlockPlace(), this);
		manager.registerEvents(new EntityDamage(), this);
		manager.registerEvents(new EntityExplode(), this);
		manager.registerEvents(new EntityRegainHealth(), this);
		manager.registerEvents(new PlayerJoin(), this);
		manager.registerEvents(new PlayerQuit(), this);
	}
	
	public void registerCommands() {
		this.getCommand("gm").setExecutor(new GamemodeCommand());
		this.getCommand("health").setExecutor(new HealthCommand());
	}

}