package pe.nn.connor.dragonpower.dragons;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import pe.nn.connor.dragonpower.DragonPower;

public class AirDragon implements Dragon{
	
	HashMap<String, String> dragonConfig;
	
	public AirDragon(DragonPower dragonPower) {
		dragonConfig = dragonPower.getConfigAirDragon();
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

	@Override
	public void onClick(Player player, PlayerInteractEvent e) {
		
	}

	@Override
	public void onDamage(Player player, EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL){
			if(toBoolean(dragonConfig.getOrDefault("fallDamageReductionEnabled", "true"))){
				String reductionString = dragonConfig.getOrDefault("fallDamageReduction", "0");
				double reduction = Double.parseDouble(reductionString);
				e.setDamage(reduction);
			}
		}
	}

	@Override
	public void onMove(Player player, PlayerMoveEvent e) {
		
	}

	@Override
	public void onEntityAttack(Player player, Entity entity,
			EntityDamageByEntityEvent e) {
		
	}
	
	private boolean toBoolean(String value){
		return value.equalsIgnoreCase("true");
	}

}
