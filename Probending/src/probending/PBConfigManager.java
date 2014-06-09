package probending;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import sun.security.krb5.Config;

public class PBConfigManager {
//    //The config file!
//    private YamlConfiguration myConfig;
//    private File configFile;
//    private boolean loaded = false;
//    private String fileName;
// 
//    public PBConfigManager(String fileName){
//        this.fileName = fileName;
//    }
//    
//    public YamlConfiguration getConfig() {
//        if (!loaded) {
//            loadConfig();
//        }
//        return myConfig;
//    }
//    
//    public void saveConfig(){
//        if (configFile.exists()) {
//            try {
//                myConfig.save(configFile);
//            } catch (FileNotFoundException ex) {
//            } catch (IOException ex) {
//            }
//        } else {
//            
//        }
//        
//    }
//    
//    public File getConfigFile() {
//        return configFile;
//    }
//    
//    public void loadConfig() {
//        configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("Probending").getDataFolder(), fileName + ".yml");
//        if (configFile.exists()) {
//            myConfig = new YamlConfiguration();
//            try {
//                myConfig.load(configFile);
//            } catch (FileNotFoundException ex) {
//            } catch (IOException ex) {
//            } catch (InvalidConfigurationException ex) {
//            }
//            loaded = true;
//        } else {
//            try {
//                Bukkit.getServer().getPluginManager().getPlugin("Probending").getDataFolder().mkdir();
//                InputStream jarURL = Config.class.getResourceAsStream("/" + fileName + ".yml");
//                copyFile(jarURL, configFile);
//                myConfig = new YamlConfiguration();
//                myConfig.load(configFile);
//                loaded = true;
//            } catch (Exception e) {
//            }
//        }
//    }
// 
//    private void copyFile(InputStream in, File out) throws Exception {
//        InputStream fis = in;
//        FileOutputStream fos = new FileOutputStream(out);
//        try {
//            byte[] buf = new byte[1024];
//            int i = 0;
//            while ((i = fis.read(buf)) != -1) {
//                fos.write(buf, 0, i);
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (fis != null) {
//                fis.close();
//            }
//            if (fos != null) {
//                fos.close();
//            }
//        }
//    }
}
