package pe.nn.connor.dragonpower;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import pe.nn.connor.dragonpower.dragons.AirDragon;
import pe.nn.connor.dragonpower.dragons.Dragon;
import pe.nn.connor.dragonpower.dragons.EarthDragon;
import pe.nn.connor.dragonpower.dragons.FireDragon;
import pe.nn.connor.dragonpower.dragons.WaterDragon;

public class DragonPowerAPI {
	
	private DragonPower dragonPower;
	boolean logAPICalls;
	public DragonPowerAPI(DragonPower dragonPower) {
		this.dragonPower = dragonPower;
		logAPICalls = dragonPower.logAPICalls;
	}
	
	private void log(String message){
		if(logAPICalls){
			try {
			    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dragonPower.getDataFolder() + "/APICALLS.LOG", true)));
			    out.println(message);
			    out.close();
			} catch (IOException e) {
			    //Shit
				e.printStackTrace();
			}
		}
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
		log("Method setDragon called with arguments UUID:" + player.toString() + ", Dragon:" + dragon.getClass().getName());
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
		Dragon dragon = dragonPower.getDragons().get(player);
		log("Method getDragon called with arguments UUID:" + player.toString());
		return dragon;
	}
	
	/**
	 * Gets the list of all dragons on the server
	 * @return A HashHap<UUID, Dragon> of all dragons on the server
	 */
	public HashMap<UUID, Dragon> getDragons(){
		log("Method getDragons called");
		return dragonPower.getDragons();
	}
	
	/**
	 * Creates an instance of the FireDragon class
	 * @return FireDragon
	 */
	public FireDragon getFireDragon(){
		return new FireDragon(dragonPower);
	}
	
	/**
	 * Creates an instance of the EarthDragon class
	 * @return EarthDragon
	 */
	public EarthDragon getEarthDragon(){
		return new EarthDragon(dragonPower);
	}
	
	/**
	 * Creates an instance of the WaterDragon class
	 * @return WaterDragon
	 */
	public WaterDragon getWaterDragon(){
		return new WaterDragon(dragonPower);
	}
	
	/**
	 * Creates an instance of the AirDragon class
	 * @return AirDragon
	 */
	public AirDragon getAirDragon(){
		return new AirDragon(dragonPower);
	}
	
}
