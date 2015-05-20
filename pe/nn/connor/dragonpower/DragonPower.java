package pe.nn.connor.dragonpower;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import pe.nn.connor.dragonpower.dragons.AirDragon;
import pe.nn.connor.dragonpower.dragons.Dragon;
import pe.nn.connor.dragonpower.dragons.EarthDragon;
import pe.nn.connor.dragonpower.dragons.FireDragon;
import pe.nn.connor.dragonpower.dragons.WaterDragon;

public class DragonPower extends JavaPlugin implements Listener{
	//Dragons is a HashMap that lists what each logged in user's dragon is
	//TODO implement saving and loading
	private HashMap<UUID, Dragon> dragons;
	private FileConfiguration config;
	
	protected boolean logAPICalls;
	private HashMap<String, String> configFireDragon;
	private HashMap<String, String> configEarthDragon;
	private HashMap<String, String> configWaterDragon;
	private HashMap<String, String> configAirDragon;
	
	@Override
	public void onEnable() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(this, this);
		
		dragons = new HashMap<UUID, Dragon>();
		
		if(!new File(this.getDataFolder(), "config.yml").exists()){
			saveDefaultConfig();
		}
		
		//Config stuff
		
		config = getConfig();
		
		logAPICalls = config.getBoolean("logAPICalls", true);
		
		configFireDragon = new HashMap<String, String>();
		ConfigurationSection fireSection = config.getConfigurationSection("fireDragon");
		for(String key : fireSection.getKeys(false)){
			configFireDragon.put(key, fireSection.getString(key));
		}
		
		configEarthDragon = new HashMap<String, String>();
		ConfigurationSection earthSection = config.getConfigurationSection("earthDragon");
		for(String key : earthSection.getKeys(false)){
			configEarthDragon.put(key, earthSection.getString(key));
		}
		
		configWaterDragon = new HashMap<String, String>();
		ConfigurationSection waterSection = config.getConfigurationSection("waterDragon");
		for(String key : waterSection.getKeys(false)){
			configWaterDragon.put(key, waterSection.getString(key));
		}
		
		configAirDragon = new HashMap<String, String>();
		ConfigurationSection airSection = config.getConfigurationSection("airDragon");
		for(String key : airSection.getKeys(false)){
			configAirDragon.put(key, airSection.getString(key));
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		//This is only here until we have a better system in place for adding dragons
		UUID uuid = e.getPlayer().getUniqueId();
		if(!dragons.containsKey(uuid)){
			//An instance of the dragon for each user, now that's /r/shittyprogramming
			//Will be changed soon, just throwing this together for now
			dragons.put(uuid, new EarthDragon(this));
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		//Fuck it, no commands yet 
		//Dev 2. Now its a command.
		UUID uuid = e.getPlayer().getUniqueId();
		if(e.getMessage().equalsIgnoreCase("!fire")){
			dragons.put(uuid, new FireDragon(this));
		}else if(e.getMessage().equalsIgnoreCase("!earth")){
			dragons.put(uuid, new EarthDragon(this));
		}else if(e.getMessage().equalsIgnoreCase("!water")){
			dragons.put(uuid, new WaterDragon(this));
		}else if(e.getMessage().equalsIgnoreCase("!air")){
			dragons.put(uuid, new AirDragon(this));
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(dragons.containsKey(e.getPlayer().getUniqueId())){
			if(e.getTo().getBlockY() > e.getFrom().getBlockY()){
				dragons.get(e.getPlayer().getUniqueId()).onJump(e.getPlayer(), e);
			}else{
				dragons.get(e.getPlayer().getUniqueId()).onMove(e.getPlayer(), e);
			}
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(dragons.containsKey(e.getPlayer().getUniqueId())){
			dragons.get(e.getPlayer().getUniqueId()).onClick(e.getPlayer(), e);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			Player player = (Player) e.getEntity();	
			if(dragons.containsKey(player.getUniqueId())){
				dragons.get(player.getUniqueId()).onDamage(player, e);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFireballBoom(ProjectileHitEvent e){
		if(e.getEntityType() == EntityType.FIREBALL){
			if(e.getEntity().getShooter() instanceof Player){
				if(configFireDragon.getOrDefault("fireballPhysicsEnabled", "true").equalsIgnoreCase("true")){
					String radiusString = configFireDragon.getOrDefault("fireballRadius", "2");
					int radius = Integer.parseInt(radiusString);
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
					String powerString = configFireDragon.getOrDefault("fireballExplosionPower", "2");
					float power = Float.parseFloat(powerString);
					entity.getWorld().createExplosion(entity.getLocation(), power);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityInteract(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player player = (Player) e.getDamager();
			if(dragons.containsKey(player.getUniqueId())){
				dragons.get(player.getUniqueId()).onEntityAttack(player, e.getEntity(), e);
			}
		}
	}
	
	public HashMap<String, String> getConfigFireDragon() {
		return configFireDragon;
	}
	
	public HashMap<String, String> getConfigEarthDragon() {
		return configEarthDragon;
	}
	
	public HashMap<String, String> getConfigWaterDragon() {
		return configWaterDragon;
	}
	
	public HashMap<String, String> getConfigAirDragon() {
		return configAirDragon;
	}
	
	public DragonPowerAPI getAPI(){
		return new DragonPowerAPI(this);
	}
	
	//Methods only called by the API
	
	protected HashMap<UUID, Dragon> getDragons(){
		return dragons;
	}
	
	protected void setDragon(UUID player, Dragon dragon){
		dragons.put(player, dragon);
	}
	
}
