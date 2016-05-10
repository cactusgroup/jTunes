package net.codejava.sound;
 
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 
/**
 * This code handles audio file playback and facilliates user control
 * implementing LineListener to listen to events and act upon them.
 */

public class AudioPlayerExample1 implements LineListener {
    

    private boolean playCompleted;
    private boolean loop;
    private final long FIVESECONDS = 5000000;
    private final int START = 0;
    private Clip audioClip;
    private Scanner reader = new Scanner(System.in);
    //private AudioPlayerExample1 player = new AudioPlayerExample1();
     
    
    public void load(String songName){
    	String audioFilePath = "./audiofiles/" + songName + ".wav";
    	File audioFile = new File(audioFilePath);
    	playCompleted = false;
    	loop = false;
    	try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            System.out.println("Song Loaded");
    	}
        catch (UnsupportedAudioFileException ex) {
        System.out.println("The specified audio file is not supported.");
        ex.printStackTrace();
        }
    	catch (LineUnavailableException ex) {
        System.out.println("Audio line for playing back is unavailable.");
        ex.printStackTrace();
    	}
    	catch (IOException ex) {
        System.out.println("Error playing the audio file.");
        ex.printStackTrace();
    	}
    }
    	


    private void play(){
        //File audioFile = new File(audioFilePath);
    	if(audioClip.isOpen()  && !(audioClip.isActive())){
    		if(audioClip.getMicrosecondPosition() == audioClip.getMicrosecondLength()){
    			audioClip.setFramePosition(START);
    		}
	        audioClip.start();
	        System.out.println("Song Playing");
	        
	        /*while (!playCompleted) {
	            // wait for the playback completes
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException ex) {
	                ex.printStackTrace();
	            }
	        }
	        
	        audioClip.close();*/	        
    	} 
   }
   
   public boolean isCompleted(){
	   return playCompleted;
   }
    
   private void end(){
	   audioClip.close();
	   playCompleted = true;
	   System.out.println("Song Ended");
   }
    
   private void pause(){
	   if(audioClip.isActive()){
		   System.out.println("Paused");
		   audioClip.stop();
	   }
   }
   
   private void beginning(){
	   audioClip.setFramePosition(START);
	   System.out.println("Song back to beginning");
   }
   
   private void toggleLoop(){
	   loop = !loop;
   }
   
   private void back5(){
	   if(audioClip.getMicrosecondPosition() > FIVESECONDS) audioClip.setMicrosecondPosition(audioClip.getMicrosecondPosition() - FIVESECONDS);
	   else audioClip.setMicrosecondPosition(START);
   }
   
   private void fwd5(){
	   if(audioClip.getMicrosecondPosition() < (audioClip.getMicrosecondLength()-FIVESECONDS)) audioClip.setMicrosecondPosition(audioClip.getMicrosecondPosition() + FIVESECONDS);
	   else audioClip.setMicrosecondPosition(audioClip.getMicrosecondLength());
   }
   
   public double getSongProgress(){
	   return ((double)audioClip.getMicrosecondPosition()/(double)audioClip.getMicrosecondLength())*100.0;
   }
   
   
    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");   
        } 
        else if (type == LineEvent.Type.STOP) {
        	System.out.println("Playback stopped");
        	if (audioClip.getMicrosecondLength() == audioClip.getMicrosecondPosition()){
        		if(loop) {
        			play();
        		}
        	}  
        else if (type == LineEvent.Type.CLOSE) {
                System.out.println("Playback completed.");
        	}
        }
    }
    
    public void menu(){
    	
    		int n = 0;

	    	System.out.print("Play - 1\nPause - 2\nExit -3\nGo back to the beginning - 4\nToggle Loop - 5\nBack 5 seconds - 6\nForward 5 seconds - 7\n");
	    	if(loop) System.out.println("Looping is enabled");
	    	try{ n = reader.nextInt();}
	    	catch(InputMismatchException ex){
	    		if(playCompleted) return;
	    		System.out.println("This is an invalid choice.");
	    		reader.next();
	    		return;
	    	}    	
	    	if(playCompleted) return;
	    	switch (n){
	    	case 1: play();
	    			break;
	    	case 2: pause();
	    			break;
	    	case 3: end();
	    			break;
	    	case 4: beginning();
	    			break;
	    	case 5: toggleLoop();
	    			break;
	    	case 6: back5();
	    			break;
	    	case 7: fwd5();
	    			break;
	    	default: break;
    	}
    }
    
 
}
    
    
    
            
             
 