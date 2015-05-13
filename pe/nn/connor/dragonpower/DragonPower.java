package pe.nn.connor.dragonpower;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pe.nn.connor.dragonpower.dragons.Dragon;
import pe.nn.connor.dragonpower.dragons.EarthDragon;

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
	public void chat(PlayerJoinEvent e){
		//This is only here until we have a better system in place for adding dragons
		UUID uuid = e.getPlayer().getUniqueId();
		if(!dragons.containsKey(uuid)){
			//An instance of the dragon for each user, now that's /r/shittyprogramming
			//Will be changed soon, just throwing this together for now
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
	
	
}
