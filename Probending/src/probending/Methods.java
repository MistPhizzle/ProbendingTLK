package probending;

import org.bukkit.configuration.file.FileConfiguration;

public class Methods {
	
	public static Probending plugin;
	
	public Methods(Probending plugin) {
		Methods.plugin = plugin;
	}
	
	public static void configCheck() {
		FileConfiguration c = plugin.getConfig();
		
		c.addDefault("Storage.engine", "sqlite");
		c.addDefault("Storage.host", "localhost");
		c.addDefault("Storage.port", 3306);
		c.addDefault("Storage.database", "minecraft");
		c.addDefault("Storage.username", "root");
		c.addDefault("Storage.password", "");
		
		c.options().copyDefaults(true);
		plugin.saveConfig();
	}

}
