package net.dodgewhale.glass.listeners;

import net.dodgewhale.glass.GlassMain;
import net.dodgewhale.glass.data.PlayerData;
import net.dodgewhale.glass.objects.DodgePlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityRegainHealth implements Listener {

	@EventHandler
	public void onRegain(EntityRegainHealthEvent event) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(!(event.getEntity() instanceof Player)) return;
				Player player = (Player) event.getEntity();
				
				DodgePlayer dPlayer = PlayerData.find(player);
				if(dPlayer != null) dPlayer.getHealthBar().update(player);
			}
		}.runTaskLater(GlassMain.getInstance(), 1L);
	}
	
}