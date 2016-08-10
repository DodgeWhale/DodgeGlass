package net.dodgewhale.glass.objects;

import net.dodgewhale.glass.utils.StringUtil;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {

	private String message;
	
	public ActionBar(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return StringUtil.color(this.message);
	}
	
	public void send(Player player) {
		IChatBaseComponent barmsg = ChatSerializer.a("{\"text\":\"" + this.getMessage() + "\"}");
		
		PacketPlayOutChat bar = new PacketPlayOutChat(barmsg, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
	}

}