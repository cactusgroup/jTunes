package jTunes.database;

import java.io.File;
import java.net.URISyntaxException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JfxMP3Player {

    public static MediaPlayer loadSong(String fileName) throws URISyntaxException{
    	Media musicFile;
		try{
			String songPath = new File("src/mp3tracks/" + fileName + ".mp3").getAbsolutePath();
			musicFile = new Media(new File(songPath).toURI().toString());
		}
		catch(Exception e){
			musicFile = new Media(JfxMP3Player.class.getResource("/mp3tracks/" + fileName + ".mp3").toURI().toString());
		}
		// Creating the media to be played by the media player
		//Media musicFile = new Media(new File(songPath).toURI().toString());
		MediaPlayer player = new MediaPlayer(musicFile);
		return player;
        //player.play();
    }

}