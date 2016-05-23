package jTunes.database;
import java.io.File;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MP3Player_GUI extends Application {
        
        private final static Duration a = new Duration(5000); // 5 seconds used to forward and back 5 seconds
        private static MediaPlayer mediaPlayer; // Static so that we can access it in all our static functions.
        
        // Loading function, loads our mediaPlayer object and instantiates it for the first time.
        public static MediaPlayer loadSong(String fileName) throws URISyntaxException{
        Media musicFile;
                try{    // We try loading it from within a .jar file first. 
                        musicFile = new Media(MP3Player_GUI.class.getResource("/mp3tracks/" + fileName + ".mp3").toURI().toString());
                }
                catch(Exception e){
                        // If that doesnt work, load from the relative path in our project folder in Eclipse.
                        String songPath = new File("src/mp3tracks/" + fileName + ".mp3").getAbsolutePath();
                        System.out.println(songPath);
                        musicFile = new Media(new File(songPath).toURI().toString());
                }
                // Creating the media to be played by the media player
                return new MediaPlayer(musicFile); // Returns the mediaPlayer object.
    }
        // Loads a new MP3 file to the existing mediaPlayer object.
        public static void loadNewMP3File(String filename) throws URISyntaxException{
                mediaPlayer.stop();
                mediaPlayer.dispose(); // Its important to dispose of old mediaPlayers when we get new mp3 files. Garbage Collector will deal with these.
                mediaPlayer = loadSong(filename);
        }
        
        // Plays the song from where it was left off at.
        public static void play(){
            mediaPlayer.play();
        }
        // Pauses the song.
        public static void pause() {
            mediaPlayer.pause();
        }
        // Boolean function to check if the JavaFX app is currently playing the song.
        public static boolean isPlaying(){
            if(mediaPlayer.getStatus().toString().equals("PLAYING")){
                return true;
            }
            return false;
        }
        // Stop function (name is stopp due to some error "This static method cannot hide the instance method from Application"
        public static void stopp() {
            mediaPlayer.stop();
        }
        // Closes the mediaPlayer. This runs when the application is shutting down.
        public static void close(){
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        // Goes back to the beginning of the song.
        public static void backtoBeginning() {
            mediaPlayer.stop();
            mediaPlayer.play();  
        }   
        // Boolean that will return if the song playback is completed.
        public static boolean isCompleted(){
            if(mediaPlayer.getCurrentTime().toString().equals(mediaPlayer.getStopTime().toString())){
                return true;
            }
            return false;
        }
        // Will jump ahead 5 seconds in playback
        public static void fwd5() {
            Duration newDuration = mediaPlayer.getCurrentTime().add(a);
            mediaPlayer.seek(newDuration);
        }
        // Will jump back 5 seconds in playback.
        public static void back5(){
            Duration newDuration = mediaPlayer.getCurrentTime().subtract(a);
            mediaPlayer.seek(newDuration);
        }
        
        @Override // Overrides the start function.
        public void start(Stage stage) throws URISyntaxException {
                // Getting the parameters and finding the song path
                Parameters params = getParameters();  
                // Loads song from parameters and instantiates our mediaPlayer with it.
                mediaPlayer = loadSong(params.getRaw().get(0));       
    }   
        
}