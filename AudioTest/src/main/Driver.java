package main;

import net.codejava.sound.*;


public class Driver {
public static void main(String[] args) {
	
	AudioPlayer a = new AudioPlayer();
	
	String song = "test2";
	
	a.load(song);
	while(!a.isCompleted()){
	System.out.println(a.getSongProgress());
	a.menu();
	}
	
	song = "ghost";
	
	a.load(song);
	while(!a.isCompleted()){
	a.menu();
	}
	//a.play();
	//a.pause();
	//a.play();
 
    
	}
}