package net.dodgewhale.glass.objects;

import net.dodgewhale.glass.utils.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class HealthBar {

	private BossBar bar;

	public HealthBar(Player player) {
		this.bar = this.update(player);
	}

	public BossBar getBar() {
		return bar;
	}

	public BossBar update(Player player) {
		BossBar bar = this.getBar();
		double health = player.getHealth();
		
		String title = StringUtil.color("&c&lHP &c" + (int) health + " &l/ &c20");
		if(bar == null)
			return Bukkit.createBossBar(title, BarColor.RED, BarStyle.SEGMENTED_20, new BarFlag[] {});

		bar.setTitle(title);
		bar.setProgress((double) health / (double) 20.0);
		return bar;
	}

}