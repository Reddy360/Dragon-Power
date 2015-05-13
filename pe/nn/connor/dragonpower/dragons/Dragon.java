package pe.nn.connor.dragonpower.dragons;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public interface Dragon{
	public void onJump(Player player, PlayerMoveEvent e);
	public void onClick(Player player, PlayerInteractEvent e);
	public void onDamage(Player player, EntityDamageEvent e);
}
