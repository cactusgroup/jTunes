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
        
        private volatile static boolean loop; // Used to track the toggle loop
        private final static Duration a = new Duration(5000); // 5 seconds used to forward and back 5 seconds
        private volatile static MediaPlayer mediaPlayer;
        
        public static MediaPlayer loadSong(String fileName) throws URISyntaxException{
        Media musicFile;
                try{
                        String songPath = new File("src/mp3tracks/" + fileName + ".mp3").getAbsolutePath();
                        System.out.println(songPath);
                        musicFile = new Media(new File(songPath).toURI().toString());
                }
                catch(Exception e){
                        musicFile = new Media(MP3Player_GUI.class.getResource("/mp3tracks/" + fileName + ".mp3").toURI().toString());
                }
                // Creating the media to be played by the media player
                return new MediaPlayer(musicFile);
    }
        
        public static void loadNewMP3File(String filename) throws URISyntaxException{
                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaPlayer = loadSong(filename);
        }
        
        public static void play(){
            mediaPlayer.play();
        }
        public static void pause() {
            mediaPlayer.pause();
        }
        
        public static boolean isPlaying(){
            if(mediaPlayer.getStatus().toString().equals("PLAYING")){
                return true;
            }
            return false;
        }
        
        public static void stopp() {
            mediaPlayer.stop();
        }
        
        public static void backtoBeginning() {
            mediaPlayer.stop();
            mediaPlayer.play();  
    }
        public static boolean isCompleted(){
            if(mediaPlayer.getCurrentTime() == mediaPlayer.getStopTime()){
                return true;
            }
            return false;
        }
        
        public static void fwd5() {
            Duration newDuration = mediaPlayer.getCurrentTime().add(a);
            mediaPlayer.seek(newDuration);
    }
        
        public static void back5(){
            Duration newDuration = mediaPlayer.getCurrentTime().subtract(a);
            mediaPlayer.seek(newDuration);
    }
        
        @Override
        public void start(Stage stage) throws URISyntaxException {
                // Getting the parameters and finding the song path
                Parameters params = getParameters(); 
                mediaPlayer = loadSong(params.getRaw().get(0));
                // Implementation of the buttons
                
                
 
                /* The close button the top will be used instead
                btn_exit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                                mediaPlayer.stop();
                                mainStage.hide();
                                //mediaPlayer.dispose();
                                //stage.close();
                        }
                });
                */
                   /*
                btn_toggleLoop.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                                loop = !loop;
                                if(loop)
                                        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                                else
                                        mediaPlayer.setCycleCount(1);
                                loopStatus.setText(Boolean.toString(loop));
                        }
                });
        
                btn_back5.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                                Duration newDuration = mediaPlayer.getCurrentTime().subtract(a);
                                mediaPlayer.seek(newDuration);
                        }
                });
                
                btn_fwd5.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                                Duration newDuration = mediaPlayer.getCurrentTime().add(a);
                                mediaPlayer.seek(newDuration);
                        }
                });
                
                mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent arg0) {
                                mediaPlayer.stop();
                                mainStage.hide();
                        }
                });
            */
                //mediaPlayer.setAutoPlay(true); // Will start the song automatically
        
    }   
        
}