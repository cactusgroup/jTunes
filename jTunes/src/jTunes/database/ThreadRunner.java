package jTunes.database;

import javafx.application.Application;

public class ThreadRunner implements Runnable{
    private static String songName = "";
    public ThreadRunner() {
       
    }
    
    public void RunMe(String name){
        songName = name;
        (new Thread(new ThreadRunner())).start();
    }

    @Override
    public void run() {
        System.out.println("SONGNAME:" + songName);
        Application.launch(MP3Player_GUI.class, songName);
        
    }

}
