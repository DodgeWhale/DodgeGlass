package net.dodgewhale.glass.cmds;

import net.dodgewhale.glass.data.PlayerData;
import net.dodgewhale.glass.objects.DodgePlayer;
import net.dodgewhale.glass.utils.MessageUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealthCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLbl, String[] args) {
		if(!(sender instanceof Player)) {
			MessageUtil.send(sender, "&cYou must be a player to execute this command.");
			return true;
		}

		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("health")) {
			DodgePlayer dPlayer = PlayerData.find(player);
			
			if(dPlayer != null) dPlayer.toggleHealthBar();
			return true;
		}
		return false;
	}
	
}