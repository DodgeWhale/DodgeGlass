package net.dodgewhale.glass.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import net.dodgewhale.glass.utils.StringUtil;

public class AsyncPlayerChat implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if(event.getPlayer().isOp())
			event.setMessage(StringUtil.color(event.getMessage()));
	}
	
}