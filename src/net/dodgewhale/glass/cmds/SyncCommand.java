package net.dodgewhale.glass.cmds;

import net.dodgewhale.glass.data.PlayerData;
import net.dodgewhale.glass.objects.DodgePlayer;
import net.dodgewhale.glass.utils.MessageUtil;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SyncCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLbl, String[] args) {
		if(!(sender instanceof Player)) {
			MessageUtil.send(sender, "&cYou must be a player to execute this command.");
			return true;
		}

		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("sync")) {
			DodgePlayer dPlayer = PlayerData.find(player);
			if(dPlayer == null) return true;
			
			// TODO Make a Cooldowns enum?
			String key = "SYNC_COMMAND";
			
			if(dPlayer.tryCooldown(key, 60.0)) {
				PlayerData.save(dPlayer, false);
				
				MessageUtil.send(player, "&aYour data has been synced.");
				player.playSound(player.getLocation(), Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.0F);
				return true;
			}
			
			MessageUtil.send(player, "&cCommand on cooldown (" + dPlayer.getCooldown(key) / 1000 + "s remaining)");
			return true;
		}
		return false;
	}
	
}