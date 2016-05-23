package jTunes.database;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import javafx.application.Platform;
/*
 * The Menu class interacts with the database connection, and retrieves data such as: genre
 * artists, albums, and songs using SQL statements, which creates the basis for the menus
*/
public class Menu implements Runnable {
    
    private String query;   // Will store the SQL statements
    private volatile static String songName;  // Stores the songName to be used by the application
    private volatile static boolean AppRun = false;  // Checks if the application is running
    private Connection connection; // Used to connect to the database
    private Statement statement;  // Used to execute the SQL statements
    private PreparedStatement pStatement; // Used to have a prepared statement for later execution
    private ResultSet resultSet;  // Stores the results set from the SQL statements
    
    // The map contains SQL statements that retrieves all of the genres, artists, albums, or songs.
    private static final Map<String, String> QUERIES;
    static {
        Map<String, String> aMap = new HashMap<String, String>();
        // basic queries
        aMap.put("genres",  "SELECT genreName FROM Genre;");
        aMap.put("artists", "SELECT artistName FROM Artist;");
        aMap.put("albums",  "SELECT albumName FROM Album;");
        aMap.put("songs",   "SELECT songName FROM Songs;");
        QUERIES = Collections.unmodifiableMap(aMap); 
    }
    
    // The Menu constructor connects to the database
    public Menu() {
        try {
            connection = DatabaseSetup.getConnection();  // connects to the database
        } catch (MySQLSyntaxErrorException e) {
            System.out.println("ERROR IN SQL DB, DATABASE CORRUPT");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Unable to make connection to the database.");
            e.printStackTrace();
        }
    }
    
    // Receives a type (genre, album, artist, or songs) and returns the complete list of them
    public List<String> getValues(ValueType type) {
        List<String> list = new ArrayList<>(5);  // will store the output list
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
            		QUERIES.get(type.name() + "s"));  // executes the corresponding SQL statement
            
            while (resultSet.next()) {  // loops through the result from the SQL statement
                String column = type.name() + "Name";
                list.add(resultSet.getString(column));  // add each item in the column to the list
                System.out.println(resultSet.getString(column)); // prints each item to the console
            }
        } catch (SQLException e) {
            System.out.println("Error occured when reading database.");
            e.printStackTrace();
        } finally { // closing the connections and catching any exceptions
            try { resultSet.close(); } catch (Exception e) {}
            try { statement.close(); } catch (Exception e) {}
        }
        return list;  // returns a list of the type in the argument
    }
    
    // Returns and prints a list of the artists that are in the provided genre
    public List<String> getArtistsInGenre(String genreName) {
        List<String> list = new ArrayList<>(5);  // stores the list of artists
        try {
            query = "SELECT artistName FROM Artist "
                  + "WHERE genreID = (SELECT genreID FROM Genre "  // SQL statements to retrieve the 
                                   + "WHERE genreName = ?);";  		// corresponding artist names
            pStatement = connection.prepareStatement(query);  // prepare statement to be executed
            pStatement.setString(1, genreName);  // provides the argument to the prepared statement
            resultSet = pStatement.executeQuery(); 
            
            while (resultSet.next()) {  // loops through the column of artists
                list.add(resultSet.getString("artistName"));  // adds the artist to the list
                System.out.println(resultSet.getString("artistName")); // prints artist to console
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (Exception e) {}
            try { pStatement.close(); } catch (Exception e) {}
        }
        
        return list;  // returns list of artists
    }
    
    // Returns and prints a list of albums that have the given genre and artist
    public List<String> getAlbumsByArtistInGenre(String genreName, String artistName) {
    	List<String> list = new ArrayList<>(5);  // stores the list of albums
        try {
            query = "SElECT albumName FROM Album "
                  + "WHERE  genreID = (SELECT genreID FROM Genre "
                                    + "WHERE  genreName = ?) "     
                  + "  AND artistID = (SELECT artistID FROM Artist "
                                    + "WHERE  artistName = ?);"; // SQL statement to retrieve albums
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, genreName);
            pStatement.setString(2, artistName);
            resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {  // loops through the albums 
            	list.add(resultSet.getString("albumName"));  // adds them to the list
                System.out.println(resultSet.getString("albumName"));  // prints to console
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (Exception e) {}
            try { pStatement.close(); } catch (Exception e) {}
        }
        return list;  // returns the list of albums
    }
    
    // Returns and prints a list of songs that are in the provided album
    public List<String> getSongsInAlbumByArtistInGenre(String albumName) {
    	List<String> list = new ArrayList<>(5);  // stores the list of songs
        try {
            query = "SELECT songTitle FROM Songs "
                  + "WHERE  albumID = (SELECT albumID FROM Album "
                                    + "WHERE  albumName = ?);";  // SQL statement to retrieve the songs
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, albumName);
            resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {  // loops through the songs
            	list.add(resultSet.getString("songTitle"));  // adds the songs to the list
                System.out.println(resultSet.getString("songTitle"));  // prints the songs to the console
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (Exception e) {}
            try { pStatement.close(); } catch (Exception e) {}
        }
        return list;  // returns the list of songs
    }
    
    // Closes the database connection
    public void closeConnection() {
        try {
            DatabaseSetup.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Couldn't close connection...");
            e.printStackTrace();
        }
    }
    
    // Takes in the song name and activates the song to be played
    public void activateSong(String song) throws SQLException, URISyntaxException {
    	songName = song;  // stores the song name to be processed by the MP3Player application 
    	if(AppRun) {  // checks if the application was already running
    		Platform.runLater(() -> {  // Allows the platform to run again when exiting
    			try {
				MP3Player.loadNewMP3File(songName);  // loads the new song to the application
				} catch (Exception e) {
					e.printStackTrace();
				}
    		});
    	}
    	else{
    		AppRun = true;  // signals that the application is running
    		(new Thread(new Menu())).start(); // starts a new thread of the menu 
    	}

    }
	
    // Allows to run the platform multiple times
    @Override
	public void run() {
		MP3Player.launch(MP3Player.class, songName);  // launches the application with the song provided
	}
 }

