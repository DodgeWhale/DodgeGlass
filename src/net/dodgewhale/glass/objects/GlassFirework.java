package net.dodgewhale.glass.objects;

import lombok.Getter;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class GlassFirework {

	@Getter
	private Color color, fade;
	@Getter
	private FireworkEffect.Type type;

	public GlassFirework(Color color, Color fade, FireworkEffect.Type type) {
		this.color = color;
		this.fade = fade;
		this.type = type;
	}

	public void launch(Player player) {
		Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);

		FireworkMeta fireworkMeta = firework.getFireworkMeta();
		FireworkEffect.Type type = this.getType();

		FireworkEffect effect = FireworkEffect.builder().withColor(this.getColor()).withFade(this.getFade()).with(type).trail(true).build();

		fireworkMeta.addEffect(effect);
		fireworkMeta.setPower(0);
		firework.setFireworkMeta(fireworkMeta);
	}
}