package probending;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Probending extends JavaPlugin{
    public final Logger logger = Logger.getLogger("minecraft");
    PBGameStart gamestart;
    PBScoreBoard scoreboard;
    PBTeleporter setplaces;
    public static Probending plugin;
    WorldEditPlugin worldEdit;
    WorldGuardPlugin worldGuard;
    PBDatabase pbdatabase;
    
    //Database
    private String address = "localhost";
    private String port = "3306";
    private String databasename = "Probending";
    private String username = "root";
    private String password = "";
    
    @Override
    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.log(Level.INFO, ChatColor.GREEN + "{0} has been enabled!", pdfFile.getName());
        worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        worldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        pbdatabase = new PBDatabase(this, address, port, databasename, username, password);
        scoreboard = new PBScoreBoard(this, pbdatabase);
        gamestart = new PBGameStart(this, scoreboard);
        setplaces = new PBTeleporter(this, scoreboard, gamestart);
        getServer().getPluginManager().registerEvents(new PBListener(scoreboard, gamestart, setplaces), this);
        scoreboard.createScoreboard();
        
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) { 
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("spawn")) {
                gamestart.tryLeaveQueue(player);
                setplaces.teleportSpawn(player);
            } else if (cmd.getName().equalsIgnoreCase("probending")) {
                for(int i = 0; i < args.length; i++){
                    args[i] = args[i].toLowerCase();
                }
//============================================================================= 0 arguments! ===========================================================
                if (args.length == 0) {
                     showChatInfoBegin(player);
//============================================================================= 1 arguments! ===========================================================
                } else if (args.length == 1) {
                    switch (args[0]) {
                        case "team":
                            showChatInfoTeam(player);
                            break;
                        case "ranking":
                            showChatInfoRanking(player);
                            break;
                        case "join":
                            if (scoreboard.objectiveWaiting.getScore(player).getScore() >= 1){
                                player.sendMessage(ChatColor.RED + "You have already been queued or are in game!");
                            } else {
                                gamestart.tryJoinTeam(player, "");
                            }
                            break;
                        case "leave":
                            gamestart.tryLeaveQueue(player);
                            break;
                        case "test":
                            break;
                        case "change":
                            gamestart.tryLeaveQueue(player);
                            setplaces.teleportElementChange(player);
                            break;
                        default:
                            showChatInfoBegin(player);
                            break;
                    }
                } else if (args.length == 2) {
                    switch (args[0]) {
                        case "team":
                            if ("create".equals((args[1]))){
                                player.sendMessage(ChatColor.DARK_RED + "oOo " + ChatColor.DARK_AQUA + "Probending Team Configuration" + ChatColor.DARK_RED + " oOo");
                                player.sendMessage(ChatColor.AQUA + "Try: " + ChatColor.BLUE + "/probending team create " + ChatColor.RED + "[name]");
                            } else if ("invite".equals((args[1]))){
                                player.sendMessage(ChatColor.DARK_RED + "oOo " + ChatColor.DARK_AQUA + "Probending Team Configuration" + ChatColor.DARK_RED + " oOo");
                                player.sendMessage(ChatColor.AQUA + "Try: " + ChatColor.BLUE + "/probending team invite " + ChatColor.RED + "[player]");
                            }else if ("kick".equals((args[1]))){
                                player.sendMessage(ChatColor.DARK_RED + "oOo " + ChatColor.DARK_AQUA + "Probending Team Configuration" + ChatColor.DARK_RED + " oOo");
                                player.sendMessage(ChatColor.AQUA + "Try: " + ChatColor.BLUE + "/probending team kick " + ChatColor.RED + "[player]");
                            }else{
                                showChatInfoTeam(player);
                            }
                            break;
                        case "join":
                            switch (args[1]) {
                                case "1v1":
                                    gamestart.tryJoinTeam(player, "1v1");
                                    break;
                                case "3v3":
                                    gamestart.tryJoinTeam(player, "3v3");
                                    break;
                            }
                            break;
                        case "ranking":
                            if ("you".equals((args[1]))){
                                
                            } else {
                                showChatInfoRanking(player);
                            }
                            break;
                        case "setspawn":
                            if (player.isOp() || player.hasPermission("probending.setspawn")){
                                setplaces.setSpawn(args[1], player.getLocation());
                                player.sendMessage(ChatColor.BLUE + "SetSpawn for: " + args[1]);
                            } else
                                player.sendMessage(ChatColor.DARK_RED + "Sorry but your name is not runefist, is it?!");
                            break;
                        default:
                            showChatInfoBegin(player);
                            break;
                    }
                } else if (args.length == 3) {
                    if ("team".equals(args[0])) {
                        switch (args[1]) {
                            case "create":
                                break;
                            case "invite":
                                break;
                            case "kick":
                                break;
                            default:
                                showChatInfoTeam(player);
                                break;
                        }
                    } else {
                        showChatInfoBegin(player);
                    }
                }
//============================================================================= + arguments! ===========================================================
                else {
                    player.sendMessage(ChatColor.DARK_RED + "To many arguments!");
                    player.sendMessage(ChatColor.AQUA + "Need help? Try: " + ChatColor.BLUE + "/probending");
                }
            }
        }
        return false;
    }
    
    private void showChatInfoBegin(Player player) {
        player.sendMessage(ChatColor.DARK_RED + "oOo " + ChatColor.RED + "Bending Heroes Probending");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending team");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending ranking");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending join" + ChatColor.AQUA + " [1v1/3v3]");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending leave");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending change");
        player.sendMessage(ChatColor.GRAY + "=" + ChatColor.DARK_GRAY + "=" + ChatColor.GRAY + "=" + ChatColor.DARK_GRAY + " Thanks for choosing Bending Heroes!");
    }
    
    private void showChatInfoTeam(Player player) {
        player.sendMessage(ChatColor.DARK_RED + "oOo " + ChatColor.DARK_AQUA + "Probending Team Configuration" + ChatColor.DARK_RED + " oOo");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending team "
                + "create " + ChatColor.AQUA + "[name]");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending team "
                + "invite " + ChatColor.AQUA + "[player]");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending team "
                + "info " + ChatColor.GRAY + "[teamname]");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending team "
                + "kick " + ChatColor.AQUA + "[player]");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending team "
                + "leave");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending team "
                + "disband " + ChatColor.DARK_GRAY + "[teamname]");
    }
    
    private void showChatInfoRanking(Player player) {
        player.sendMessage(ChatColor.DARK_RED + "oOo " + ChatColor.DARK_AQUA + "Probending Rankings" + ChatColor.DARK_RED + " oOo");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending ranking "
                + "you");
        player.sendMessage(ChatColor.DARK_AQUA + "=" + ChatColor.AQUA + "=" + ChatColor.DARK_AQUA + "= " + ChatColor.BLUE + "/probending ranking "
                + "[name] ");
    }
    
    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.log(Level.INFO, ChatColor.RED + "{0} has been disabled!", pdfFile.getName());
    }
 }
