package pe.nn.connor.dragonpower.dragons;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public interface Dragon{
	public void onJump(Player player, PlayerMoveEvent e);
	public void onClick(Player player, PlayerInteractEvent e);
	public void onDamage(Player player, EntityDamageEvent e);
	public void onMove(Player player, PlayerMoveEvent e);
	public void onEntityInteract(Player player, Entity clicked, PlayerInteractEntityEvent e);
}
