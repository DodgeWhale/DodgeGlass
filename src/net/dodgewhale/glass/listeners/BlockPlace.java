package net.dodgewhale.glass.listeners;

import net.dodgewhale.glass.data.PlayerData;
import net.dodgewhale.glass.objects.DodgePlayer;
import net.dodgewhale.glass.utils.BukkitUtil;
import net.dodgewhale.glass.utils.MessageUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		Block block = event.getBlock();
		if(block == null) return; // || player.isOp()

		DodgePlayer dPlayer = PlayerData.find(player);
		
		if(block.getType().equals(Material.TNT)) {
			if(dPlayer != null && dPlayer.tryCooldown("TNT_PLACE", 10.0)) {
				MessageUtil.broadcast("&cWARNING: " + player.getName() 
						+ " placed TNT (" + BukkitUtil.formatLocation(block.getLocation()) + ")", true);
			}
		}
	}
}