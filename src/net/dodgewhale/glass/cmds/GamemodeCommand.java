package net.dodgewhale.glass.cmds;

import net.dodgewhale.glass.objects.ActionBar;
import net.dodgewhale.glass.utils.MessageUtil;

import org.apache.commons.lang.WordUtils;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLbl, String[] args) {
		if(!(sender instanceof Player)) {
			MessageUtil.send(sender, "&cYou must be a player to execute this command.");
			return true;
		}

		Player player = (Player) sender;
		if(!player.isOp()) {
			MessageUtil.send(player, "&cYou don't have permission.");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("gm")) {
			Gamemode[] list = Gamemode.values();
			int length = list.length - 1;

			if(args.length > 0) {
				Gamemode gamemode = null;
				
				try {
					gamemode = list[Integer.parseInt(args[0])];
				} catch (NumberFormatException e) {
					try {
						gamemode = Gamemode.valueOf(args[0].toUpperCase());
					} catch (Exception ex) {
						
					}
				}
				
				if(gamemode != null) {
					player.setGameMode(gamemode.convert());
					
					new ActionBar("&bYour gamemode has been updated to " + gamemode.format()).send(player); 
					return true;
				}
				
				MessageUtil.send(player, "&cInvalid argument. Please supply a number from 0 to " + length + ".");
				return true;
			}

			MessageUtil.send(player, "&cUsage: /gm <0-" + length + ">");
			return true;
		}
		return false;
	}
	
	public enum Gamemode {
		
		SURVIVAL(GameMode.SURVIVAL), CREATIVE(GameMode.CREATIVE),
		ADVENTURE(GameMode.ADVENTURE), SPECTATOR(GameMode.SPECTATOR);
		
		private GameMode gamemode;
		
		Gamemode(GameMode gamemode) {
			this.gamemode = gamemode;
		}
		
		public GameMode convert() {
			return gamemode;
		}
		
		public String format() {
			return WordUtils.capitalize(gamemode.toString().toLowerCase());
		}
		
	}
	
}