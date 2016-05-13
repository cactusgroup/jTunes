import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class Menu {
	private String query;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private int genreID;
	public Menu() throws SQLException {
		try {
			connection = DatabaseSetup.getConnection();
			statement = connection.createStatement();
			query = "SELECT genreName FROM Genre;";
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println(resultSet.getString("genreName"));
			}
		} catch (MySQLSyntaxErrorException e) {
			System.out.println("ERROR IN SQL DB, DATABASE CORRUPT");
			e.printStackTrace();
		}
	}
	
	public void getArtists(String genreName) {
		try {
			query = "SELECT genreID FROM Genre WHERE genreName = '" + genreName + "';";
			resultSet = statement.executeQuery(query);
			resultSet.next();
			genreID = resultSet.getInt("genreID");
			printArtistsList(genreID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void printArtistsList(int genreID) {
		try {
			query = "SELECT artistName FROM Artist WHERE genreID = " + genreID;
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println(resultSet.getString("artistName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void getAlbums(String artistName) {
		int artistID = 0;
		try {
			query = "SELECT artistID FROM Artist WHERE artistName = '" + artistName + "';";
			resultSet = statement.executeQuery(query);
			resultSet.next();
			artistID = resultSet.getInt("artistID");
			printAlbumsList(artistID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void printAlbumsList(int artistID) {
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
	public void getSongs(String albumName) {
		int albumID = 0;
		try {
			query = "SELECT albumID FROM Album WHERE albumName = '" + albumName + "';";
			resultSet = statement.executeQuery(query);
			resultSet.next();
			albumID = resultSet.getInt("albumID");
			printSongsList(albumID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void printSongsList(int albumID) {
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
	/* Just missing code that will play song
	void playSong(Song) {
		audio.play(Song);
	}
	void stopSong() {
		audio.stop(Song);
	}
	*/

}
