package jTunes.database;

import javafx.application.Application;

// This class is a wrapper for our runnable thread for JavaFX Application.
public class ThreadRunner implements Runnable{
    private static String songName = ""; // Initalize string to hold our first song which we initialize our Application with
    public ThreadRunner() {      
    }
    
    // Function to get the filename and then start the new thread.
    public void RunMe(String name){
        songName = name;
        (new Thread(new ThreadRunner())).start();
    }

    @Override
    public void run() {
        Application.launch(MP3Player_GUI.class, songName); // Launches our application to play Mp3 files.
        
    }

}
