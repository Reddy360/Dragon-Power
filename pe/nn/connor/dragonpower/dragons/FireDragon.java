package pe.nn.connor.dragonpower.dragons;

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

public class FireDragon implements Dragon{

	@Override
	public void onJump(Player player, PlayerMoveEvent e) {
		if(player.isSneaking()){
			//Shift-jump = flight 
			Vector vector = player.getLocation().getDirection().multiply(4);
			player.setVelocity(vector);
		}
	}

	@Override
	public void onClick(Player player, PlayerInteractEvent e) {
		if(player.isSneaking()){
			if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
				//Shift-Click = Fireball
				Fireball fireball = player.launchProjectile(Fireball.class);
				fireball.setVelocity(player.getVelocity().multiply(1.5));
			}
		}
	}

	@Override
	public void onDamage(Player player, EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL){
			e.setDamage(0.5); // 1/2 a heart
		}else if(e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK){
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

}
