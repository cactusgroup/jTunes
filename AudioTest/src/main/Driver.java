package main;

import net.codejava.sound.*;


public class Driver {
public static void main(String[] args) {
	
	AudioPlayerExample1 a = new AudioPlayerExample1();
	
	String song = "test2";
	
	a.load(song);
	while(!a.isCompleted()){
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