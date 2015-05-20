package pe.nn.connor.dragonpower;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import pe.nn.connor.dragonpower.dragons.Dragon;

public class DragonPowerAPI {
	
	private DragonPower dragonPower;
	boolean logAPICalls = true; //This will be a config option, I swear!
	public DragonPowerAPI(DragonPower dragonPower) {
		this.dragonPower = dragonPower;
	}
		
	/**
	 * Sets a player's dragon to another type of dragon, this will also work will custom crafted dragons
	 * @param player The player you want to set
	 * @param dragon The dragon it will become
	 */
	public void setDragon(Player player, Dragon dragon){
		setDragon(player.getUniqueId(), dragon);
	}
	
	/**
	 * Sets a player's dragon to another type of dragon, this will also work will custom crafted dragons
	 * @param player The UUID of the player you want to set
	 * @param dragon The dragon it will become
	 */
	public void setDragon(UUID player, Dragon dragon){
		dragonPower.setDragon(player, dragon);
	}
	
	/**
	 * Gets the dragon a player is taking the form of
	 * @param player The player
	 * @return The dragon class or null if not found
	 */
	public Dragon getDragon(Player player){
		return getDragon(player.getUniqueId());
	}
	
	/**
	 * Gets the dragon a player is taking the form of
	 * @param player The UUID of the player
	 * @return The dragon class or null if not found
	 */
	public Dragon getDragon(UUID player){
		return dragonPower.getDragons().get(player);
	}
	
	/**
	 * Gets the list of all dragons on the server
	 * @return A HashHap<UUID, Dragon> of all dragons on the server
	 */
	public HashMap<UUID, Dragon> getDragons(){
		return dragonPower.getDragons();
	}
}
