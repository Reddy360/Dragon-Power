# API Documentation

## Getting the API
Before you can interact with the API you need to get the API object, to get the object you need to call the method in this plugins main class, below is an example of how to get the API, please note that you need to include DragonPower as a dependency in your plugin.yml

```java
Plugin plugin = Bukkit.getPluginManager().getPlugin("DragonPower");
DragonPower dragonPower = (DragonPower) plugin;
DragonPowerAPI api = dragonPower.getAPI();
```

## Setting a player's dragon
You can change what dragon a player is really easily in this API and due to the way it's strutured it also allows you to create custom dragons but we'll talk about that later
To set a dragon you need to run the following code

```java
api.setDragon(PLAYER, DRAGON);
```

Player needs to be type Player or UUID and Dragon needs to be type Dragon, although to set a internal dragon you must get the dragon instance from the API, for example for the Fire dragon we must call

```java
api.setDragon(PLAYER, api.getFireDragon());
```

## Custom Dragons
Now let's touch on custom dragons, using the method we learned above we can use a custom crafted dragon class, you just need to implement Dragon and fill in the blanks, here's an example

```java
package pe.nn.connor.dragonpowerapitest;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import pe.nn.connor.dragonpower.dragons.Dragon;

public class CustomDragon implements Dragon {

	@Override
	public void onClick(Player arg0, PlayerInteractEvent arg1) {
		arg0.sendMessage("onClick");
	}

	@Override
	public void onDamage(Player arg0, EntityDamageEvent arg1) {
		arg0.sendMessage("onDamage");
	}

	@Override
	public void onEntityAttack(Player arg0, Entity arg1,
			EntityDamageByEntityEvent arg2) {
		arg0.sendMessage("onEntityAttack");
	}

	@Override
	public void onJump(Player arg0, PlayerMoveEvent arg1) {
		arg0.sendMessage("onJump");
	}

	@Override
	public void onMove(Player arg0, PlayerMoveEvent arg1) {
		arg0.sendMessage("onMove");
	}

}
```

we can use that class in the setDragon method like this

```java
api.setDragon(PLAYER, new CustomDragon());
```

## Get a player's dragon
If a player is a dragon we can get the class of the dragon it takes the form of, to do this run the example code

```java
Dragon dragon = api.getDragon(PLAYER);
```

using this we can call methods from the dragon class to force dragon events or whatever else you can think of

## Get dragons
The getDragons method just returns a HashMap containing all the dragons on the server with their UUID and dragon type, to get this run the example code below

```java
HashMap<UUID, Dragon> dragons = api.getDragons();
```

## More examples
If you're still confused you can look at the source code of an API test, this will forever be uptodate with the API and is a full compilable plugin [click here](https://github.com/Reddy360/Dragon-Power-API-Test/)

And if you want to just read the code for the API [click here](https://github.com/Reddy360/Dragon-Power/blob/master/pe/nn/connor/dragonpower/DragonPowerAPI.java)
