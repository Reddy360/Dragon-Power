package pe.nn.connor.dragonpower.dragons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class EarthDragon implements Dragon{

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
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDamage(Player player, EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL){
			//Falling = earth force effect + no damage
			//Get block under exactly
			Location blockUnder = player.getLocation().getBlock().getLocation().subtract(0, 1, 0);
			Location front = blockUnder.add(1, 0, 0);
			Location back = blockUnder.subtract(1, 0, 0);
			Location left = blockUnder.add(0, 0, 1);
			Location right = blockUnder.subtract(0, 0, 1);
			Location[] locations = {
				front,
				back,
				left,
				right
			};
			for(Location location : locations){
				System.out.println(location.toString());
				Block block = location.getBlock();
				if(!(block.getType() == Material.CHEST ||
						block.getType() == Material.SIGN ||
						block.getType() == Material.SIGN_POST)){
					//If not chest, sign or sign do this
					Material material = block.getType();
					byte data = block.getData();
					block.setType(Material.AIR);
					FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(location, material, data);
					//Make the falling block launch into the air
					fallingBlock.setVelocity(new Vector(0, 0.5, 0));
				}
			}
			e.setCancelled(true);
			
		}
	}

}
