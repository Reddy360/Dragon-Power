package pe.nn.connor.dragonpower.dragons;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import pe.nn.connor.dragonpower.DragonPower;

public class AirDragon implements Dragon{
	
	HashMap<String, String> dragonConfig;
	
	public AirDragon(DragonPower dragonPower) {
		dragonConfig = dragonPower.getConfigAirDragon();
	}

	@Override
	public void onJump(Player player, PlayerMoveEvent e) {
		
	}

	@Override
	public void onClick(Player player, PlayerInteractEvent e) {
		
	}

	@Override
	public void onDamage(Player player, EntityDamageEvent e) {
		
	}

	@Override
	public void onMove(Player player, PlayerMoveEvent e) {
		
	}

	@Override
	public void onEntityAttack(Player player, Entity entity,
			EntityDamageByEntityEvent e) {
		
	}

}
