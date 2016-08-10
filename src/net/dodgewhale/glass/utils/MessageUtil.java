package net.dodgewhale.glass.utils;

import java.util.Collection;
import java.util.logging.Level;

import net.dodgewhale.glass.GlassMain;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {
	
	public static void send(Player player, String message) {
		player.sendMessage(StringUtil.color(message));
	}
	
	public static void send(CommandSender sender, String message) {
		sender.sendMessage(StringUtil.color(message));
	}
	
	public static void broadcast(String message, boolean toOperators) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		if(players.size() == 0) return; // Remove?
		
		if(toOperators) {
			for(Player player : players) {
				if(player.isOp())
					MessageUtil.send(player, message);
			} return;
		} Bukkit.broadcastMessage(StringUtil.color(message));
	}
	
	public static void log(Level level, String message) {
		GlassMain.getInstance().getLogger().log(level, message);
	}
	
}