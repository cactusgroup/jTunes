package main;

import net.codejava.sound.*;


public class Driver {
	
public static void main(String[] args) { // Test file for testing audioplayer. 
	
	AudioPlayer a = new AudioPlayer();
	
	String song = "test2";
	
	a.load(song); // Loads song 1. 
	while(!a.isCompleted()){
	System.out.println(a.getSongProgress());
	a.menu();
	}
	
	song = "ghost";
	
	a.load(song); // Loads song 2.
	while(!a.isCompleted()){
	a.menu();
	}
	//a.play();
	//a.pause();
	//a.play();
 
    
	}
}