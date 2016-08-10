package net.dodgewhale.glass.listeners;

import net.dodgewhale.glass.objects.GlassFirework;
import net.dodgewhale.glass.utils.MessageUtil;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		MessageUtil.send(player, "&7Welcome back, " + player.getName());
		new GlassFirework(Color.YELLOW, Color.ORANGE, FireworkEffect.Type.BURST).launch(player);
	}
	
}