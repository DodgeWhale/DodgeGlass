package net.dodgewhale.glass.listeners;

import net.dodgewhale.glass.data.PlayerData;
import net.dodgewhale.glass.objects.DodgePlayer;
import net.dodgewhale.glass.objects.GlassFirework;
import net.dodgewhale.glass.utils.MessageUtil;
import net.dodgewhale.glass.utils.StringUtil;

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
		
		// TODO Run load code in an async thread?
		
		DodgePlayer dPlayer = PlayerData.load(player, true);
		if(dPlayer != null) dPlayer.setLastLogin(StringUtil.getDate());
		
		MessageUtil.send(player, "&7Welcome back, " + player.getName());
		new GlassFirework(Color.YELLOW, Color.ORANGE, FireworkEffect.Type.BURST).launch(player);
		// player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0F, 0.4F);
	}
	
}