package pe.nn.connor.dragonpower.dragons;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import pe.nn.connor.dragonpower.DragonPower;

public class WaterDragon implements Dragon{
	
	private HashMap<String, String> dragonConfig;
	
	public WaterDragon(DragonPower dragonPower) {
		dragonConfig = dragonPower.getConfigWaterDragon();
	}
	
	@Override
	public void onJump(Player player, PlayerMoveEvent e) {
		if(player.isSneaking() && toBoolean(dragonConfig.getOrDefault("flyingEnabled", "true"))){
			//Shift-jump = flight 
			String multiplierString = dragonConfig.getOrDefault("flyingMultiplier", "4");
			int multiplier = Integer.parseInt(multiplierString);
			Vector vector = player.getLocation().getDirection().multiply(multiplier);
			player.setVelocity(vector);
			player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1F, 1F); //WING SPAM
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(Player player, PlayerInteractEvent e) {
		if((player.isSneaking() && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)) &&
				toBoolean(dragonConfig.getOrDefault("waterBlastEnabled", "true"))){
			String distanceString = dragonConfig.getOrDefault("waterBlastDistance", "50");
			int distance = Integer.parseInt(distanceString);
			BlockIterator iterator = new BlockIterator(player.getLocation(), 0.0, distance); //I hope to my Nan this works
			Block[] blocks = new Block[distance + 1]; //Just to be sure I'll give it more
			int index = 0;
			while(iterator.hasNext()){
				Block block = iterator.next();
				blocks[index] = block;
				if((!block.isEmpty() && !block.isLiquid()) || index > distance){
					break; //FUCKING STOP M8
				}
				if(index >= 2){ //Not in your face about it
					block.setType(Material.WATER);
					String dataString = dragonConfig.getOrDefault("waterBlastData", "7");
					byte data = Byte.parseByte(dataString);
					block.setData(data);
				}
				
				index++;
			}
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
	public void onEntityAttack(Player player, Entity entity,
			EntityDamageByEntityEvent e) {
		
	}
	
	private boolean toBoolean(String value){
		return value.equalsIgnoreCase("true");
	}

}
