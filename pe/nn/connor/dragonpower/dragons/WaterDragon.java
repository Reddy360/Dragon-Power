package pe.nn.connor.dragonpower.dragons;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BlockIterator;

public class WaterDragon implements Dragon{

	@Override
	public void onJump(Player player, PlayerMoveEvent e) {
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(Player player, PlayerInteractEvent e) {
		if(player.isSneaking() && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)){
			BlockIterator iterator = new BlockIterator(player.getLocation(), 0.0, 50); //I hope to my Nan this works
			Block[] blocks = new Block[55]; //Just to be sure I'll give it more
			int index = 0;
			while(iterator.hasNext()){
				Block block = iterator.next();
				blocks[index] = block;
				if(!block.isEmpty() && !block.isLiquid()){
					break; //FUCKING STOP M8
				}
				if(index >= 2){ //Not in your face about it
					block.setType(Material.WATER);
					block.setData((byte) 9);
				}
				
				index++;
			}
			blocks[index].setData((byte)1);
		}
	}

	@Override
	public void onDamage(Player player, EntityDamageEvent e) {
		//Not needed
	}

	@Override
	public void onMove(Player player, PlayerMoveEvent e) {
		//TODO Water healing but not overpowered
		//Like a time limit or some bullcocks
	}

	@Override
	public void onEntityInteract(Player player, Entity clicked,
			PlayerInteractEntityEvent e) {
		
	}
	

}
