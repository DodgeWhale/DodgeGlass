package net.dodgewhale.glass.listeners;

import net.dodgewhale.glass.GlassMain;
import net.dodgewhale.glass.objects.HealthBar;

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
				
				HealthBar bar = GlassMain.getHealthBars().get(player.getName());
				if(bar != null) bar.updateBar();
			}
		}.runTaskLater(GlassMain.getInstance(), 1L);
	}
	
}