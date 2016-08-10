package net.dodgewhale.glass.cmds;

import java.util.HashMap;

import net.dodgewhale.glass.GlassMain;
import net.dodgewhale.glass.objects.ActionBar;
import net.dodgewhale.glass.objects.HealthBar;
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
		String playerName = player.getName();
		
		if (cmd.getName().equalsIgnoreCase("health")) {
			HashMap<String, HealthBar> healthBars = GlassMain.getHealthBars();
			
			HealthBar bar = healthBars.get(playerName);
			if(bar != null) {
				bar.getBar().removeAll();
				healthBars.remove(playerName);
				
				new ActionBar("&bHealth bar has been hidden").send(player);
			} else {
				healthBars.put(playerName, new HealthBar(player));
				new ActionBar("&bHealth bar is now shown").send(player);
			}
			
			return true;
		}
		return false;
	}
	
}