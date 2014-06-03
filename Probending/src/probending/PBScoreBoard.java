package probending;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class PBScoreBoard{
    private PBDatabase pbdatabase;
    private JavaPlugin plugin;
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    public Scoreboard board = manager.getNewScoreboard();
    public Objective objectiveWaiting = board.registerNewObjective("Waiting", "dummy");
    Team red = board.registerNewTeam("red");
    Team blue = board.registerNewTeam("blue");
    
    public PBScoreBoard(JavaPlugin plugin, PBDatabase pbdatabase) {
        this.plugin = plugin;
        this.pbdatabase = pbdatabase;
        red.setAllowFriendlyFire(false);
        blue.setAllowFriendlyFire(false);
    }

    public void removePlayersFromTeam(){
        for (Team team : board.getTeams()){
            for (OfflinePlayer offPlayer : team.getPlayers()){
                Player player = offPlayer.getPlayer();
                if ("red".equals(team.getName()))
                    red.removePlayer(player);
                if ("blue".equals(team.getName()))
                    blue.removePlayer(player);
            }
        }
    }
    
    public void createScoreboard(){
        for (Player p : Bukkit.getOnlinePlayers()) {
           addPlayerToScoreboard(p);
        }
     }
        
     public void addPlayerToScoreboard(Player player) {
        Scoreboard s = manager.getNewScoreboard();
        player.setScoreboard(s);
        Objective objectiveWins = s.registerNewObjective("#", "dummy");
        objectiveWins.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objectiveWins.setDisplayName(ChatColor.DARK_RED + "#");
        Objective objectiveSidebar = s.registerNewObjective("Probending", "dummy");
        objectiveSidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        objectiveSidebar.setDisplayName(ChatColor.BLUE + "Probending");
        updatePlayerScoreboard();
    }
    
    public void makeDatabaseForPlayer(Player player) {
        pbdatabase.insertPlayerToDatabase(player);
    }
    
    public void addWin1(Player player) {
        pbdatabase.addWin1(player);
    }
    
    public void addWin3(Player player) {
        pbdatabase.addWin3(player);
    }
     
    public void updatePlayerScoreboard() {
        for (Player player : plugin.getServer().getOnlinePlayers()){
            Scoreboard s = player.getScoreboard();
            Objective objectiveSidebar = s.getObjective(DisplaySlot.SIDEBAR);
            Objective objectiveWins = s.getObjective(DisplaySlot.BELOW_NAME);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "Your profile:")).setScore(13);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Wins1: " + ChatColor.AQUA + pbdatabase.getWinScore1v1(player))).setScore(12);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Wins3: " + ChatColor.AQUA + pbdatabase.getWinScore3v3(player))).setScore(11);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Rating: " + ChatColor.AQUA + pbdatabase.getRating(player))).setScore(10);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "Team profile:")).setScore(9);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Team: " + ChatColor.AQUA + pbdatabase.getTeam(player))).setScore(8);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Games: " + ChatColor.AQUA + pbdatabase.getTeamGames(player))).setScore(7);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Wins: " + ChatColor.AQUA + pbdatabase.getTeamWins(player))).setScore(6);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "Your balances:")).setScore(5);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Coins: " + ChatColor.AQUA + pbdatabase.getCoins(player))).setScore(4);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "")).setScore(3);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "Games:")).setScore(2);
            objectiveSidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Game: " + ChatColor.DARK_RED + "none")).setScore(1);
            objectiveWins.getScore(player).setScore(pbdatabase.getRating(player));
            
            player.getScoreboard().resetScores(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Wins1: " + ChatColor.AQUA + (pbdatabase.getWinScore1v1(player)-1)));
            player.getScoreboard().resetScores(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Wins3: " + ChatColor.AQUA + (pbdatabase.getWinScore3v3(player)-1)));
        }
    }
    
    public void broadcast(String string){
        plugin.getServer().broadcastMessage(string);
    }
}