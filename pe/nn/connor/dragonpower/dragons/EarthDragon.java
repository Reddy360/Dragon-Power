package pe.nn.connor.dragonpower.dragons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import pe.nn.connor.dragonpower.DragonPower;

public class EarthDragon implements Dragon{
	
	private HashMap<String, String> dragonConfig;
	
	public EarthDragon(DragonPower dragonPower) {
		this.dragonConfig = dragonPower.getConfigEarthDragon();
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
				toBoolean(dragonConfig.getOrDefault("earthPropulsionEnabled", "true"))){
			HashSet<Material> set = new HashSet<Material>();
			set.add(Material.AIR);
			String distanceString = dragonConfig.getOrDefault("earthPropulsionTargetLimit", "100");
			int distance = Integer.valueOf(distanceString);
			Block block = player.getTargetBlock(set, distance);
			Location blockLocation = block.getLocation();
			
			String radiusString = dragonConfig.getOrDefault("earthPropulsionRadius", "2");
			int radius = Integer.valueOf(radiusString);
			for(int x = -radius; x <= radius; x++) {
		        for(int y = -radius; y <= radius; y++) {
		            for(int z = -radius; z <= radius; z++) {
		            	Location location = new Location(player.getWorld(), x, y, z);
		            	location.add(blockLocation);
		            	Block targetBlock = location.getBlock();
		            	if(targetBlock.getType() != Material.AIR && targetBlock.getType() != Material.BEDROCK){
		    				Material material = targetBlock.getType();
		    				byte data = targetBlock.getData();
		    				targetBlock.setType(Material.AIR);
		    				FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(location, material, data);
		    				//Make the falling block launch into the air
		    				String randomString = dragonConfig.getOrDefault("earthPropulsionMultiplierRandom", "1");
		    				double randomDouble = Double.parseDouble(randomString);
		    				double random = 0;
		    				if(randomDouble == 1){
		    					random = new Random().nextDouble();
		    				}else if(randomDouble > 0){
		    					random = randomDouble * new Random().nextDouble();
		    				}
		    				String multiplierString = dragonConfig.getOrDefault("earthPropulsionMultiplier", "1");
		    				double multiplier = Double.parseDouble(multiplierString);
		    				fallingBlock.setVelocity(new Vector(0, multiplier + random, 0));
		            	}
		            }
		        }
		    }
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDamage(Player player, EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL){
			//Falling = earth force effect + no damage
			if(toBoolean(dragonConfig.getOrDefault("fallEarthBoomEnabled", "true"))){
				//Get block under exactly
				Location blockUnder = player.getLocation().getBlock().getLocation().subtract(0, 1, 0);
				Location front = blockUnder.clone().add(1, 0, 0);
				Location back = blockUnder.clone().subtract(1, 0, 0);
				Location left = blockUnder.clone().add(0, 0, 1);
				Location right = blockUnder.clone().subtract(0, 0, 1);
				Location[] locations = {
					front,
					back,
					left,
					right
				};
				for(Location location : locations){
					Block block = location.getBlock();
					if(!(block.getType() == Material.CHEST ||
							block.getType() == Material.SIGN ||
							block.getType() == Material.SIGN_POST ||
							block.getType() == Material.BEDROCK)){
						//If not chest, sign or sign do this
						Material material = block.getType();
						byte data = block.getData();
						block.setType(Material.AIR);
						FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(location, material, data);
						//Make the falling block launch into the air
						String velocityString = dragonConfig.getOrDefault("fallEarthBoomVelocity", "0.5");
						double velocity = Double.parseDouble(velocityString);
						fallingBlock.setVelocity(new Vector(0, velocity, 0));
					}
				}
			}
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
		if(toBoolean(dragonConfig.getOrDefault("earthPunchEnabled", "true"))){
			Location head = entity.getLocation().clone().add(0, 1, 0);
			entity.teleport(head.getBlock().getLocation()); //So the player actually stays in the fucking dirt
			String radiusString = dragonConfig.getOrDefault("earthPunchRadius", "2");
			int radius = Integer.parseInt(radiusString);
			for(int x = -radius; x <= radius; x++) {
		        for(int y = -radius; y <= radius; y++) {
		            for(int z = -radius; z <= radius; z++) {
		            	Location location = new Location(player.getWorld(), x, y, z);
		            	location.add(head);
		            	Block block = location.getBlock();
		            	block.setType(Material.DIRT);
		            }
		        }
		    }
		}
	}
	
	private boolean toBoolean(String value){
		return value.equalsIgnoreCase("true");
	}

}
