package pe.nn.connor.dragonpower;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import pe.nn.connor.dragonpower.dragons.Dragon;
import pe.nn.connor.dragonpower.dragons.EarthDragon;
import pe.nn.connor.dragonpower.dragons.FireDragon;

public class DragonPower extends JavaPlugin implements Listener{
	//Dragons is a HashMap that lists what each logged in user's dragon is
	//TODO implement saving and loading
	private HashMap<UUID, Dragon> dragons;
	@Override
	public void onEnable() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(this, this);
		
		dragons = new HashMap<UUID, Dragon>();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		//This is only here until we have a better system in place for adding dragons
		UUID uuid = e.getPlayer().getUniqueId();
		if(!dragons.containsKey(uuid)){
			//An instance of the dragon for each user, now that's /r/shittyprogramming
			//Will be changed soon, just throwing this together for now
			dragons.put(uuid, new EarthDragon());
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		//Fuck it, no commands yet 
		//Dev 2. Now its a command.
		UUID uuid = e.getPlayer().getUniqueId();
		if(e.getMessage().equalsIgnoreCase("!fire")){
			dragons.put(uuid, new FireDragon());
		}else if(e.getMessage().equalsIgnoreCase("!earth")){
			dragons.put(uuid, new EarthDragon());
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(e.getTo().getBlockY() > e.getFrom().getBlockY()){
			dragons.get(e.getPlayer().getUniqueId()).onJump(e.getPlayer(), e);
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		dragons.get(e.getPlayer().getUniqueId()).onClick(e.getPlayer(), e);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			Player player = (Player) e.getEntity();
			dragons.get(player.getUniqueId()).onDamage(player, e);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFireballBoom(ProjectileHitEvent e){
		if(e.getEntityType() == EntityType.FIREBALL){
			if(e.getEntity().getShooter() instanceof Player){
				int radius = 2;
				Projectile entity = e.getEntity();
				for(int x = -radius; x <= radius; x++) {
			        for(int y = -radius; y <= radius; y++) {
			            for(int z = -radius; z <= radius; z++) {
			            	Location location = new Location(entity.getWorld(), x, y, z);
			            	location.add(entity.getLocation());
			            	Block targetBlock = location.getBlock();
			            	if(targetBlock.getType() != Material.AIR && targetBlock.getType() != Material.BEDROCK){
			    				Material material = targetBlock.getType();
			    				byte data = targetBlock.getData();
			    				targetBlock.setType(Material.AIR);
			    				FallingBlock fallingBlock = entity.getWorld().spawnFallingBlock(location, material, data);
			    				//Make the falling block launch into the air
			    				double random = new Random().nextDouble();
			    				fallingBlock.setVelocity(new Vector(0, 1 + random, 0));
			            	}
			            }
			        }
			    }
				
				entity.getWorld().createExplosion(entity.getLocation(), 2F);
			}
		}
	}
	
	
}
