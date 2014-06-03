package probending;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PBWarps {
    private JavaPlugin plugin;
    
    private Map<String, Location> teamSpawns = new HashMap<>();
    
    public PBWarps(JavaPlugin plugin){
        this.plugin = plugin;
        loadSpawns();
    }
    
    public Location getSpawn(String name) {
        name = name.toLowerCase();
        return teamSpawns.get(name);
    }

    public void setSpawn(String name, Location location) {
        name = name.toLowerCase();
        teamSpawns.put(name, location);
        saveSpawn(name);
    }
    
    private void saveSpawn(String name) {
        name = name.toLowerCase();
        plugin.getConfig().set("spawns." + name + ".game", locToString(teamSpawns.get(name)));
        plugin.saveConfig();
    }

    private void loadSpawn(String name) {
        name = name.toLowerCase();
        teamSpawns.put(name,
                stringToLoc(plugin.getConfig().getString("spawns." + name + ".game")));
    }

    private String locToString(Location location) {
        return location.getWorld().getName() + "|" + location.getX() + "|" + location.getY() + "|" + location.getZ();
    }

    private Location stringToLoc(String s) {
        String[] args = s.split("\\|");
        World world = plugin.getServer().getWorld(args[0]);
        Double x = Double.parseDouble(args[1]);
        Double y = Double.parseDouble(args[2]);
        Double z = Double.parseDouble(args[3]);
        return new Location(world, x, y, z);
    }

    private void loadSpawns() {
        loadSpawn("red1");  //The player 1 of red location on the field on start
        loadSpawn("blue1"); //The player 1 of blue location on the field on start
        loadSpawn("red2");  //The player 2 of red location on the field on start
        loadSpawn("blue2"); //The player 2 of blue location on the field on start
        loadSpawn("red3");  //The player 3 of red location on the field on start
        loadSpawn("blue3"); //The player 3 of blue location on the field on start
        loadSpawn("b1");    //Middle of blue field 1
        loadSpawn("b2");    //Middle of blue field 2
        loadSpawn("b3");    //Middle of blue field 3
        loadSpawn("r1");    //Middle of red field 1
        loadSpawn("r2");    //Middle of red field 2
        loadSpawn("r3");    //Middle of red field 3
        loadSpawn("dmb");   //DeathMatch blue
        loadSpawn("dmr");   //DeathMatch red
        loadSpawn("r4");    //Out of the game for red
        loadSpawn("b4");    //Out of the game for blue
        loadSpawn("spawn"); //The spawn location
        loadSpawn("change");//The teleport location for changing your element
    }
}
