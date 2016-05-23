package jTunes.database;
import java.io.File;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
/*
 * The MP3Player class creates a GUI interface that plays mp3 songs. 
 * It can load a song, and use a media player to play, pause, rewind, 
 * back, forward, and repeat a song
*/
public class MP3Player extends Application {
	
	private volatile static boolean loop = false; // Used to track the toggle loop
	private final static Duration a = new Duration(5000); // used to forward and back 5 seconds of the song
	private volatile static MediaPlayer mediaPlayer; // used to control the song
	private static Stage mainStage = new Stage(); // the window displayed to the user and have all the buttons
	
	// loads a song and returns the media player that controls the song
	public static MediaPlayer loadSong(String fileName) throws URISyntaxException{
    	Media musicFile;  // stores the media necessary for the media player to play the song
		try{
			String songPath = new File("src/mp3tracks/" + fileName + ".mp3").getAbsolutePath(); // retrieving the song path
			musicFile = new Media(new File(songPath).toURI().toString());  // using the song path to construct the media object of that song
		}
		catch(Exception e){
			musicFile = new Media(MP3Player.class.getResource("/mp3tracks/" + fileName + ".mp3").toURI().toString()); // browses to find the song
		}
		return new MediaPlayer(musicFile);  // returns the media player that plays the song
    }
	
	// Used to load new songs to the media player
	public static void loadNewMP3File(String filename) throws URISyntaxException{
		mediaPlayer.stop(); // stop playing the current song
		mediaPlayer.dispose(); // disposes all the resources (media) associated with the media player
		mediaPlayer = loadSong(filename); // loads to the song to the media player
		mediaPlayer.play(); // plays the song
		mainStage.show();  // shows the stage/GUI display
	}
	
	// Starts and runs the application
	@Override
	public void start(Stage stage) throws URISyntaxException {
		
		Parameters params = getParameters();  // gets the parameters passed to the application (song name)
		mediaPlayer = loadSong(params.getRaw().get(0));  // loads the parameter/songName to the media player
		
		mainStage.setAlwaysOnTop(true); // makes the display appear on top of all other windows
		Platform.setImplicitExit(false); // allows the platform to hide without exiting
		
		// Buttons used
		Button btn_play = new Button("Play");
		Button btn_pause = new Button("Pause");
		Button btn_stop = new Button("Stop");
		Button btn_backToBeginning = new Button("Go back to the beginning");
		Button btn_toggleLoop = new Button("Toggle Loop");
        Label loopStatus = new Label("Loop OFF"); // Will track if loop is on or off
		Button btn_back5 = new Button("Back 5 seconds");
		Button btn_fwd5 = new Button("Forward 5 seconds");
		
		// Implementation of the buttons
		btn_play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				mediaPlayer.play();  // the play button will play the song when pressed on it
			}
		});
		
		btn_pause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				mediaPlayer.pause();  // the pause button will pause the song when pressed on it
			}
		});
		
		btn_stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				mediaPlayer.stop(); // the stop button will rewind the song to the beginning and stop it
			}
		});
		
		btn_backToBeginning.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {  // the backToBeginning button plays the song from the beginning
				mediaPlayer.stop();
				mediaPlayer.play();
			}
		});
		
		btn_toggleLoop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				loop = !loop;  // Changes the loop boolean to its opposite value
				if(loop) {  // if toggle loop is set on (true)
					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // cycle count become indefinite (keeps on playing)
					loopStatus.setText("Loop ON"); // label to be displayed on GUI if ON
				}
				else {
					mediaPlayer.setCycleCount(1);  // sets cycle count to 1 (plays only once)
					loopStatus.setText("Loop OFF"); // label to be displayed on GUI if OFF
				}
			}
		});
	
		btn_back5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Duration newDuration = mediaPlayer.getCurrentTime().subtract(a);  // subtract a duration (5 sec) from current time
				mediaPlayer.seek(newDuration);   // media player makes the song go to the new duration
			}
		});
		
		btn_fwd5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Duration newDuration = mediaPlayer.getCurrentTime().add(a); // adds a duration (5 sec) to current time
				mediaPlayer.seek(newDuration);  // media player makes the song go to the new duration
			}
		});
		
		// overrides the behavior of the close button. 
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() { 
			@Override
			public void handle(WindowEvent arg0) {
				mediaPlayer.stop();  // stops playing the song
				mainStage.hide();  // hides the stage/GUI display
			}
		});
	  
		mediaPlayer.setAutoPlay(true); // Will start the song automatically when the application starts
		
		
		// Used to set up the GUI
		VBox root = new VBox();  // creates the box where the content will be layed out on
		root.getChildren().addAll(btn_play, btn_pause, btn_stop, btn_backToBeginning, btn_toggleLoop, loopStatus, btn_back5, btn_fwd5);  // adds the buttons to the box
		Scene scene = new Scene(root, 500, 500);  // sets the scene for the stage
		mainStage.setScene(scene);   // the stage sets the scene to be displayed
		mainStage.setTitle("JTunes"); // setting the title of the stage/window
		mainStage.setWidth(200); // setting the width of the display window
		mainStage.setHeight(275); // setting the height of the display window
		mainStage.show(); // makes the stage visible to the user
    	
    }	
	
}