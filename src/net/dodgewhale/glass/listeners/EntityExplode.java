package net.dodgewhale.glass.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		if(event.getEntity().getType().equals(EntityType.CREEPER))
			event.blockList().clear();
	}
	
}