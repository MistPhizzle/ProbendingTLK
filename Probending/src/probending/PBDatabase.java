package probending;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PBDatabase {
    private MySQL mysql;
    private Connection c = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Statement statement = null;
    
    private String address;
    private String port;
    private String databasename;
    private String username;
    private String password;

    public PBDatabase(Plugin plugin, String address, String port, String databasename, String username, String password){
        this.address = address;
        this.port = port;
        this.databasename = databasename;
        this.username = username;
        this.password = password;        
        mysql = new MySQL(plugin, address, port, databasename, username, password);
        c = mysql.openConnection();
        createDatabase();
    }
    
    private void createDatabase(){
        try {
            statement = c.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Players` (`playername` text NOT NULL, `wins1` int(10) NOT NULL, `wins3` int(10) NOT NULL, `teamname` text NOT NULL, `coins` int(10) NOT NULL, `rating` int(10) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Teams` (`teamname` text NOT NULL, `player1` text NOT NULL, `player2` text NOT NULL, `player3` text NOT NULL, `player4` text NOT NULL, `games` int(10) NOT NULL, `wins` int(10) NOT NULL, `rating` int(10) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        } catch (Exception ex) {
            
        }
    }
    
    //Everything the database can do \/    
    public void insertPlayerToDatabase(Player player){
        try {
            statement = c.createStatement();
            statement.executeUpdate("INSERT INTO Players (`playername`, `wins1`, `wins3`, `teamname`, `coins`, `rating`) VALUES ('" + player.getName() + "', '0', '0', 'none', '0', '1000');");
        } catch (Exception ex) {
            
        }
    }
    
    public int getWinScore1v1(Player player){
        int i = 0;
        try {
            pst = c.prepareStatement("SELECT * FROM Players WHERE playername = '" + player.getName() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                i = rs.getInt(2);
            }
        } catch (Exception ex) {
            
        }
        return i;
    }
    
    public int getWinScore3v3(Player player){
        int i = 0;
        try {
            pst = c.prepareStatement("SELECT * FROM Players WHERE playername = '" + player.getName() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                i = rs.getInt(3);
            }
        } catch (Exception ex) {
            
        }
        return i;
    }
    
    public int getCoins(Player player){
        int i = 0;
        try {
            pst = c.prepareStatement("SELECT * FROM Players WHERE playername = '" + player.getName() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                i = rs.getInt(5);
            }  
        } catch (Exception ex) {
            
        }
        return i;
    }
    
    public int getRating(Player player){
        int i = 0;
        try {
            pst = c.prepareStatement("SELECT * FROM Players WHERE playername = '" + player.getName() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                i = rs.getInt(6);
            }  
        } catch (Exception ex) {
            
        }
        return i;
    }
    
    public String getTeam(Player player){
        String team = "none";
        try {
            pst = c.prepareStatement("SELECT * FROM Players WHERE playername = '" + player.getName() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                team = rs.getString(4);
            }
        } catch (Exception ex) {
            
        }
        return team;
    }
    
    public int getTeamGames(Player player){
        String team = "none";
        int teamGames = 0;
        try {
            pst = c.prepareStatement("SELECT * FROM Players WHERE playername = '" + player.getName() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                team = rs.getString(4);
            }
            
            pst = c.prepareStatement("SELECT * FROM Teams WHERE teamname = '" + team.toString() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                teamGames = rs.getInt(6);
            }
        } catch (Exception ex) {
            
        }
        return teamGames;
    }
    
    public int getTeamWins(Player player){
        String team = "none";
        int teamWins = 0;
        try {
            pst = c.prepareStatement("SELECT * FROM Players WHERE playername = '" + player.getName() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                team = rs.getString(4);
            }
            
            pst = c.prepareStatement("SELECT * FROM Teams WHERE teamname = '" + team.toString() + "'");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                teamWins = rs.getInt(7);
            }
        } catch (Exception ex) {
            
        }
        return teamWins;
    }
    
    public void addWin1(Player player){
        try {
            Statement statement = c.createStatement();
            statement.executeUpdate("UPDATE Players SET wins1=1+(SELECT wins1) WHERE playername='" + player.getName() + "'");
        } catch (Exception ex) {
            
        }
    }
    
    public void addWin3(Player player){
        try {
            Statement statement = c.createStatement();
            statement.executeUpdate("UPDATE Players SET wins3=1+(SELECT wins3) WHERE playername='" + player.getName() + "'");
        } catch (Exception ex) {
            
        }
    }
}
