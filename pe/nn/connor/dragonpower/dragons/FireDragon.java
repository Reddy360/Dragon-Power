package pe.nn.connor.dragonpower.dragons;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import pe.nn.connor.dragonpower.DragonPower;

public class FireDragon implements Dragon{
	
	private HashMap<String, String> dragonConfig;
	
	public FireDragon(DragonPower dragonPower) {
		dragonConfig = dragonPower.getConfigFireDragon();
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
		if(player.isSneaking() && toBoolean(dragonConfig.getOrDefault("fireballEnabled", "true"))){
			if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
				//Shift-Click = Fireball
				Fireball fireball = player.launchProjectile(Fireball.class);
				String multiplierString = dragonConfig.getOrDefault("fireballMultiplier", "1.5");
				double multiplier = Double.parseDouble(multiplierString);
				fireball.setVelocity(player.getVelocity().multiply(multiplier));
			}
		}
	}

	@Override
	public void onDamage(Player player, EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL && toBoolean(dragonConfig.getOrDefault("fallDamageReductionEnabled", "true"))){
			String damageString = dragonConfig.getOrDefault("fallDamageReduction", "0.5");
			double damage = Double.parseDouble(damageString);
			e.setDamage(damage);
		}else if((e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK) &&
				toBoolean(dragonConfig.getOrDefault("fireDamagePrevention", "true"))){
			e.setCancelled(true);
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
