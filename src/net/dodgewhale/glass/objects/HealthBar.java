package net.dodgewhale.glass.objects;

import net.dodgewhale.glass.utils.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class HealthBar {

	private String playerName;
	private BossBar bar;

	public HealthBar(Player player) {
		this.setPlayer(player);
		
		this.bar = this.updateBar();
		this.getBar().addPlayer(this.getPlayer());
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.playerName);
	}
	
	public void setPlayer(Player player) {
		this.playerName = player.getName();
	}

	public BossBar getBar() {
		return bar;
	}

	public BossBar updateBar() {
		BossBar bar = this.getBar();
		double health = this.getPlayer().getHealth();
		
		String title = StringUtil.color("&c&lHP &c" + (int) health + " &l/ &c20");
		if(bar == null)
			return Bukkit.createBossBar(title, BarColor.RED, BarStyle.SEGMENTED_20, new BarFlag[] {});

		bar.setTitle(title);
		bar.setProgress((double) health / (double) 20.0);
		return bar;
	}

}