import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import net.codejava.sound.AudioPlayer;

public class Menu {
    public enum ValueType {
        genre, artist, album, song
    }
    
    private String query;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int genreID;                        // used in a compound query
    
    private static final Map<String, String> QUERIES;
    static {
        Map<String, String> aMap = new HashMap<String, String>();
        aMap.put("genres",  "SELECT genreName FROM Genre;");
        aMap.put("artists", "SELECT artistName FROM Artist;");
        aMap.put("albums",  "SELECT albumName FROM Album;");
        aMap.put("songs",   "SELECT songName FROM Songs;");
        QUERIES = Collections.unmodifiableMap(aMap);
    }
    
    public Menu() throws SQLException {
        try {
            connection = DatabaseSetup.getConnection();
        } catch (MySQLSyntaxErrorException e) {
            System.out.println("ERROR IN SQL DB, DATABASE CORRUPT");
            e.printStackTrace();
        }
    }
    
    public List<String> getValues(ValueType type) {
        ArrayList<String> list = new ArrayList<>(5);
        
        try {
            resultSet = connection.createStatement().executeQuery(
                    QUERIES.get(type.name() + "s"));
            
            while (resultSet.next()) {
                String column = type.name() + "Name";
                list.add(resultSet.getString(column));
                System.out.println(resultSet.getString(column));
            }
        } catch (SQLException e) {
            System.out.println("Error occured when reading database.");
            e.printStackTrace();
        }
        return list;
    }
    
    public void getArtistsInGenre(String genreName) {
        try {
            query = "SELECT genreID FROM Genre WHERE genreName = '" + genreName + "';";
            resultSet = statement.executeQuery(query);
            resultSet.next();
            genreID = resultSet.getInt("genreID");
            printArtistsInGenre(genreID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void printArtistsInGenre(int genreID) {
        try {
            query = "SELECT artistName FROM Artist WHERE genreID = " + genreID + ";";
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                System.out.println(resultSet.getString("artistName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAlbumsByArtist(String artistName) {
        int artistID = 0;
        try {
            query = "SELECT artistID FROM Artist WHERE artistName = '" + artistName + "';";
            resultSet = statement.executeQuery(query);
            resultSet.next();
            artistID = resultSet.getInt("artistID");
            printAlbumsByArtist(artistID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void printAlbumsByArtist(int artistID) {
        try {
            query = "SELECT albumName FROM Album "
                    + "WHERE artistID = " + artistID 
                    + " AND genreID = " + genreID + ";";
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                System.out.println(resultSet.getString("albumName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }    
    }
    
    public void getSongsInAlbum(String albumName) {
        int albumID = 0;
        try {
            query = "SELECT albumID FROM Album WHERE albumName = '" + albumName + "';";
            resultSet = statement.executeQuery(query);
            resultSet.next();
            albumID = resultSet.getInt("albumID");
            printSongsInAlbum(albumID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void printSongsInAlbum(int albumID) {
        try {
            query = "SELECT songTitle FROM Songs WHERE songID = " + albumID + ";";
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                System.out.println(resultSet.getString("songTitle"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }    
    }
    
    public void activateSong(String song) {
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.load(song);
        while(!audioPlayer.isCompleted()) {
            // System.out.println(audioPlayer.getSongProgress()); // not necessary but can be useful for GUI
            audioPlayer.menu();
        }
    }
}
