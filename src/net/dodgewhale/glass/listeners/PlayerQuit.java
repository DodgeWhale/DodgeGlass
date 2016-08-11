package net.dodgewhale.glass.listeners;

import java.util.logging.Level;

import net.dodgewhale.glass.data.PlayerData;
import net.dodgewhale.glass.objects.DodgePlayer;
import net.dodgewhale.glass.utils.MessageUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		DodgePlayer dPlayer = PlayerData.find(event.getPlayer());
		
		if(dPlayer != null) {
			if(PlayerData.save(dPlayer, true))
				MessageUtil.log(Level.INFO, "Player data saved for " + dPlayer.getName());
		}
	}
	
}