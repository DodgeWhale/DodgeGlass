package net.dodgewhale.glass.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
	
	// Timber
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if(player == null) return;
		if(player.isSneaking() || player.getGameMode().equals(GameMode.CREATIVE)) return;
		
		Block block = event.getBlock();
		if(block == null) return;
		
		World world = block.getWorld();
		int block_x = block.getX(), block_z = block.getZ();
		
		if(isLog(block)) {
			boolean valid = true;
			
			for(int x = block_x - 1; x <= block_x + 1; x ++) {
				for(int z = block_z - 1; z <= block_z + 1; z ++) {
					Block target = world.getBlockAt(x, block.getY(), z);
					
					if(!block.getLocation().equals(target.getLocation()) && target.getType().isSolid()) {
						valid = false;
						break;
					}
				}
			}
			
			// TODO Add remove item durability? (Support unbreaking)
			if(valid) {
				while(true) {
					Block target = block.getRelative(BlockFace.UP, 1);
					if(!isLog(target)) break;
					
					target.breakNaturally();
					block = target;
				}
			}
		}
	}
	
	/**
	 * Checks whether a block material is a log type
	 * @param block Target block
	 * @return Is log boolean
	 */
	public boolean isLog(Block block) {
		if(block == null) return false;
		
		Material type = block.getType();
		return type.equals(Material.LOG) || type.equals(Material.LOG_2);
	}
	
}